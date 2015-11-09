package com.tdt.unicom.serverforsgip;

import java.io.DataInputStream;



import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bxtel.service.lt.zx.OrderRelationUpdateNotifyRequest;
import com.bxtel.service.lt.zx.SyncNotifySPServiceServiceLocator;
import com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub;
import com.tdt.unicom.domains.Bind;
import com.tdt.unicom.domains.BindResp;
import com.tdt.unicom.domains.Deliver;
import com.tdt.unicom.domains.DeliverResp;
import com.tdt.unicom.domains.Report;
import com.tdt.unicom.domains.ReportResp;
import com.tdt.unicom.domains.SGIPCommand;
import com.tdt.unicom.domains.SGIPCommandDefine;
import com.tdt.unicom.domains.SGIPHeader;
import com.tdt.unicom.domains.UnbindResp;
import com.tdt.unicom.domains.UserRpt;
import com.tdt.unicom.domains.UserRptResp;
import com.tdt.unicom.serverforsgip.dao.MTReq;
import com.tdt.unicom.serverforsgip.dao.MongodbDAO;
import com.tdt.unicom.serverforsgip.dao.ReportModel;
import com.tdt.unicom.util.FileHelper;
import dinamica.util.DateHelper;
import dinamica.util.Guid;
import dinamica.util.ReqHandler;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-17
 * @description 服务端监听器，监听来自SMG的数据
 * 
 * https://github.com/ewcmsfree/ewcms/wiki/Help-mongo-java-driver-date
 */
public class UnicomSPMonitor {
	protected static int spListenPort;
	protected static String smgLoginUserName; // SMG登陆SP短信网关使用的用户名
	protected static String smgLoginPassword; // SMG登陆SP短信网关使用的密码
	private final static Logger log = Logger.getLogger(UnicomSPMonitor.class);
	private ServerSocket spsvrSocket = null;
	private ExecutorService exec = Executors.newSingleThreadExecutor();
	public UnicomSPMonitor() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			String osname = System.getProperty("os.name");
			Pattern ospn = Pattern.compile("^windows.*$",Pattern.CASE_INSENSITIVE); // 大小写不敏感
			
			//String confpath = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
			String confpath =FileHelper.getClassesPath();
			if (ospn.matcher(osname).matches()) { // windows操作系统
				log.info("操作系统名称:" + osname + ",配置文件路径:"	+ System.getProperty("user.dir")+ "\\productionConf.xml");
				doc = builder.build(confpath + "\\productionConf.xml");
			} else {// Linux系统
				log.info("操作系统名称:" + osname + ",配置文件路径:"+ System.getProperty("user.dir")+ "/productionConf.xml");
				doc = builder.build(confpath + "/productionConf.xml");
			}
			// 判断操作系统
			// 根目录
			Element productionConf = doc.getRootElement();
			Element uncomConf = productionConf.getChild("unicomconf");
			// 获取联通短信网关ip及port
			SPSender.unicomIp = uncomConf.getChildText("ipaddr");
			SPSender.unicomPort = Integer.valueOf(uncomConf.getChildText("addrport"));
			String srcNodeId = uncomConf.getChildText("spNodeid");
			SGIPHeader.setSrcNodeId(srcNodeId);
			SPSender.spCorpId = srcNodeId.substring(5, 10);
			SPSender.spLoginName = uncomConf.getChildText("spUserName");
			SPSender.spLogPassword = uncomConf.getChildText("spPassword");
			UnicomSPMonitor.spListenPort = Integer.parseInt(uncomConf.getChildText("spListenPort"));
			UnicomSPMonitor.smgLoginUserName = uncomConf.getChildText("smgUserName");
			UnicomSPMonitor.smgLoginPassword = uncomConf.getChildText("smgPassword");
			// 配置实例
			log.info("***************** 加载网关配置  ********************");
			log.info("联通SMG地址:" + SPSender.unicomIp);
			log.info("联通SMG端口:" + SPSender.unicomPort);
			log.info("源节点编号：" + SGIPHeader.getSrcNodeId());
			log.info("SMG连接SP端口:" + UnicomSPMonitor.spListenPort);
			log.info("SP登陆SMG用户名：" + SPSender.spLoginName);
			log.info("SP登陆SMG密码：" + SPSender.spLogPassword);
			log.info("SMG登陆SP用户名：" + UnicomSPMonitor.smgLoginUserName);
			log.info("SMG登陆SP密码：" + UnicomSPMonitor.smgLoginPassword);
			
			try {
				spsvrSocket = ServerSocketFactory.getDefault().createServerSocket(spListenPort);
				log.info("短消息上行(MO))接收端启动,监听端口:" + spListenPort);
			} catch (IOException e) {
				log.error("launch local server error!", e);
				throw new ExceptionInInitializerError(e);
			}
			new Thread(new JFThread()).start();
			log.info("***************** 网关配置完成  ********************\n");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 启动服务，监听端口用于接收联通上行信息
	 */
	public void startSvr() throws IOException {
		while (true) {
			Socket unicomSocket = null;
			unicomSocket = spsvrSocket.accept();
			unicomSocket.setSoLinger(true, 0); // socket关闭时，不再发送缓冲区里的数据，立即释放低层资源
			unicomSocket.setTcpNoDelay(true); // 不使用缓冲区，立即发送数据
			unicomSocket.setTrafficClass(0x04 | 0x10);
			exec.execute(new DealThread(unicomSocket));
		}
	}
	
	
	// 负责与联通的服务器的通信并将MO信息转发相应的业务层
	class DealThread extends SGIPCommand implements Runnable {
		private Socket socket = null;
		private DataInputStream unicomIn = null;
		private DataOutputStream spout = null;

		public DealThread(Socket socket) {
			this.socket = socket;
			log.info("New connection accepted from " + socket.getInetAddress()+ ":" + socket.getPort());
		}
		
		
		public void run() {
			try {
				this.executeMO();
			} catch (java.net.SocketException e) {
				log.info("socket reset and  close:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			} finally {
				try {
					if (socket != null) {
						spout.flush();
						spout.close();
						
						unicomIn.close();
						
						socket.close();
						log.info("SMG与SP通信结束,链路关闭.\n");
					}
				} catch (Exception e) {
					log.warn("释放Socket资源时异常", e);
				}
			}
		}

		
		/**
		 * 处理联通上行信息
		 * 
		 * @throws IOException
		 *             接收联通上行时有可能出现的IO流异常
		 */
		private void executeMO() throws IOException {
			boolean isUnbind = false; // 收到unbind命令后，退出循环
			unicomIn = new DataInputStream(socket.getInputStream());
			spout = new DataOutputStream(socket.getOutputStream());
			// 读取联通发送来的字节流
			while (!isUnbind && !socket.isClosed() && socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown()) {
				 SGIPCommand  command= null;
				try{
					command = read(unicomIn);
				}catch(IOException ex)
				{
					log.info("socket.getInputStream().available():"+socket.getInputStream().available());
					log.info("socket.isClosed:"+socket.isClosed());//false
					log.info("socket.isConnected:"+socket.isConnected());//true
					log.info("socket.isInputShutdown:"+socket.isInputShutdown());//false
					log.info("socket.isOutputShutdown:"+socket.isOutputShutdown());//false
					throw ex;
				}
				
				log.info("【"
						+ Thread.currentThread().getName()
						+ "收到SMG "
						+ SGIPCommandDefine.getCommandName(this.header.getCommandId()) + "命令】,{长度="
						+ command.header.getTotalmsglen() + "序列="
						+ command.header.getSequenceNumber() + "}");
				
				switch (Bytes4ToInt(command.header.getCommandId())) {
						//-----------------------------------
						case 0x1: // 联通向SP发送的绑定命令
							log.info("收到SMG ->Bind命令");
							Bind bind = (Bind) command;
							log.info("LoginType:" + bind.getLoginType());
							log.info("LoginName:" + bind.getLoginName());
							log.info("LoginPassword:" + bind.getLoginPassword());
							if (bind.getLoginType() == 2) { // 登陆类型2为SMG向SP建立的连接，用于发送命令
								BindResp bindresp = new BindResp(command.header.getUnicomSN()); // 绑定响应命令
								//System.out.println("smg loginfo:" + bind.getLoginName()+ "   " + bind.getLoginPassword());
								//System.out.println("spcheck loginfo:"+ UnicomSPMonitor.smgLoginUserName + "   "+ UnicomSPMonitor.smgLoginPassword);
								if (bind.getLoginName().equals(UnicomSPMonitor.smgLoginUserName)&& bind.getLoginPassword().equals(UnicomSPMonitor.smgLoginPassword)) {
									log.info("SMG登陆SP,验证通过！");
									bindresp.setResult((byte) 0);
								} else {
									log.info("SMG登陆SP验证失败,SMG使用的用户名与密码与SP配置的参数不匹配！");
									bindresp.setResult((byte) 1);
								}
								bindresp.write(spout);
								log.info("Bind_Resp响应码：" + bindresp.getResult());
							}
							break;
						// ------------------------------------
						case 0x2: // 联通向SP发送的注销绑定命令
							// 响应
							log.info("收到SMG ->Unbind命令");
							UnbindResp resp = new UnbindResp(
									command.header.getUnicomSN());
							resp.write(spout);
							isUnbind = true;
							break;
						// ------------------------------------
						case 0x4: // 联通向SP上行一条用户短信
							log.info("收到SMG ->Deliver命令");
							Deliver deliver = (Deliver) command;
							log.info("SPNumber:" + deliver.getSPNumber());
							log.info("UserNumber:" + deliver.getUserNumber());
							log.info("MessageContent:" + deliver.getMessageContent());
							log.info("LinkID:" + deliver.getLinkID());
							// 收到响应
							DeliverResp deliverresp = new DeliverResp(
									command.header.getUnicomSN());
							deliverresp.setResult((byte) 0);
							deliverresp.write(spout);
							try {
								deliver(deliver); // 上行转发
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							break;
						// -------------------------------------
						case 0x5: // 联通向SP报告之前一条MT的状态
							log.info("收到SMG ->Report命令");
							final Report report = (Report) command;
							log.info("ReportType:" + report.getReportType());
							log.info("UserNumber:" + report.getUserNumber());
							log.info("State:" + report.getState());
							log.info("ErrorCode:" + report.getErrorCode());
							// 返回响应
							ReportResp reportResp = new ReportResp(command.header.getUnicomSN());
							reportResp.setResult((byte) 0);
							reportResp.write(spout);
							if (report.getReportType() == 0) {// 对先前的一条Submit命令的状态报告
								try {
									report(report);
								}catch (Exception ex){
									ex.printStackTrace();
								}
							}
							break;
						// --------------------------------------
						case 0x11: // 联通向SP报告一条手机用户的状态信息
							log.info("收到SMG ->UserRpt命令");
							UserRpt userRpt = (UserRpt) command;
							log.info("SPNumber:" + userRpt.getSPNumber());
							log.info("UserNumber:" + userRpt.getUserNumber());
							log.info("UserCondition:" + userRpt.getUserCondition());
							// 响应
							UserRptResp userRptresp = new UserRptResp(command.header.getUnicomSN());
							userRptresp.setResult((byte) 0);
							userRptresp.write(spout);
							
							break;
						default:
							log.error("error!! -->default:"+ Bytes4ToInt(command.header.getCommandId()));
							break;
				}
			}
			
			log.info("socket.isClosed:"+socket.isClosed());
			log.info("socket.isClosed:"+socket.isConnected());
			log.info("socket.isInputShutdown:"+socket.isInputShutdown());
			log.info("socket.isOutputShutdown:"+socket.isOutputShutdown());
		}
		
		/*
		 * 状态报告
		 * 
		 * 我们下发了submit订购成功消息后会上报一个状态
		 * 订购成功:State=2  ErrorCode:100
		 * 
		 * 
		 */
		private void report(Report command) {
			log.info("report:" + command.getErrorCode());
			log.info("report:" + command.getReportType());
			log.info("report:" + command.getReverse());
			log.info("report:" + command.getState());
			log.info("report:" + command.getSubmitSequenceNumber());
			log.info("report:" + command.getUserNumber());
			
			
			ReportModel req=new ReportModel();
			req.setErrorCode(""+command.getErrorCode());
			req.setReportType(""+command.getReportType());
			req.setReverse(""+command.getReverse());
			req.setState(""+command.getState());
			req.setSubmitSequenceNumber(""+command.getSubmitSequenceNumber());
			req.setUserNumber(""+command.getUserNumber());
			try{
				MongodbDAO.insertReport(req);
				//第二步，更新订购状态，并通知portal已经订购成功
				MongodbDAO.updateMTReqStatus(command.getSubmitSequenceNumber(),Byte.toString(command.getErrorCode()));
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 将收到的短信转发给相应的业务逻辑处理层 转发方式Socket、Http、WebService
		 * 
		 * @param command
		 * @throws IOException 
		 * @throws UnknownHostException 
		 */
		
		
		public void deliver(final Deliver command) throws UnknownHostException, IOException {
				log.info("deliver.getLinkID:"+ command.getLinkID());
				log.info("deliver.getMessageCoding:"+ command.getMessageCoding());
				log.info("deliver.getMessageLength:"+ command.getMessageLength());
				log.info("deliver.getMessageContent:"+ command.getMessageContent());
				log.info("deliver.getSPNumber:"+ command.getSPNumber());
				log.info("deliver.getTP_pid:"+ command.getTP_pid());
				log.info("deliver.getTP_udhi:"+ command.getTP_udhi());
				log.info("deliver.getUserNumber:"+ command.getUserNumber());
				
				
				try {
						if(command.getMessageContent().trim().toUpperCase().startsWith("TD")||command.getMessageContent().trim().equals("0000")||command.getMessageContent().trim().equals("00000"))
						{
								 	String servicetype=BusinessConfig.msg2BusMao.get(command.getMessageContent().trim().toUpperCase()).getServicetype();
								    String date=DateHelper.getDateString("yyyyMMddHHmmss");
									SyncNotifySPServiceServiceLocator service = new SyncNotifySPServiceServiceLocator();
									SyncNotifySPSoapBindingStub stub = new SyncNotifySPSoapBindingStub(new URL("http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService"),service);
									OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
									orderRelationUpdateNotifyRequest.setContent(command.getMessageContent().trim().toUpperCase());
									orderRelationUpdateNotifyRequest.setEffectiveDate(date);
									orderRelationUpdateNotifyRequest.setEncodeStr("LTZX");
									orderRelationUpdateNotifyRequest.setExpireDate("20351231000000");
									orderRelationUpdateNotifyRequest.setLinkId(command.getLinkID());
									orderRelationUpdateNotifyRequest.setProductId("LTZX");
									orderRelationUpdateNotifyRequest.setRecordSequenceId(DateHelper.getDateString("yyyyMMddHHmmss")+Guid.genRandom(4));
									orderRelationUpdateNotifyRequest.setServiceType(servicetype);
									orderRelationUpdateNotifyRequest.setSpId(command.getSPNumber());
									orderRelationUpdateNotifyRequest.setTime_stamp(date);
									orderRelationUpdateNotifyRequest.setUpdateDesc("LTZX");
									orderRelationUpdateNotifyRequest.setUpdateTime(date);
									orderRelationUpdateNotifyRequest.setUpdateType(2);
									orderRelationUpdateNotifyRequest.setUserId(command.getUserNumber());
									orderRelationUpdateNotifyRequest.setUserIdType(1);
									stub.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
									//cancel mtreq
									MongodbDAO.updateReqForCancel(command.getUserNumber().substring(2),servicetype);
						}else{
								
								MTReq mtreq=new MTReq();
								mtreq.setChargeType("0");
								mtreq.setLinkId(command.getLinkID());
								mtreq.setMessageContent("恭喜您，订购成功!");
								mtreq.setMobile(command.getUserNumber().substring(2));
								mtreq.setCreatedate(DateHelper.getTime());
								mtreq.setReportFlag("1");
								mtreq.setSpNumber(command.getSPNumber());
								mtreq.setServiceType(BusinessConfig.msg2BusMao.get(command.getMessageContent().trim().toUpperCase()).getServicetype());
								mtreq.setDealflag("0");//待处理
								mtreq.setMorelatetoMTFlag("2");//订购消息
								
								//发送订购确认请求
								String seq= new SPSender().sendMTReq(mtreq);//confirm subscription
								mtreq.setSequenceNumber(seq);
								
								//第一步,插入订购请求（待处理），等待状态上报的时候更新状态
								MongodbDAO.insertMTReq(mtreq);
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
		}
	}
	
	
//	try {
//    String date=DateHelper.getDateString("yyyyMMddHHmmss");
//	SyncNotifySPServiceServiceLocator service = new SyncNotifySPServiceServiceLocator();
//	SyncNotifySPSoapBindingStub stub = new SyncNotifySPSoapBindingStub(new URL("http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService"),service);
//	OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
//	orderRelationUpdateNotifyRequest.setContent(command.getMessageContent().trim().toUpperCase());
//	orderRelationUpdateNotifyRequest.setEffectiveDate(date);
//	orderRelationUpdateNotifyRequest.setEncodeStr("LTZX");
//	orderRelationUpdateNotifyRequest.setExpireDate("20351231000000");
//	orderRelationUpdateNotifyRequest.setLinkId(command.getLinkID());
//	orderRelationUpdateNotifyRequest.setProductId("LTZX");
//	orderRelationUpdateNotifyRequest.setRecordSequenceId(DateHelper.getDateString("yyyyMMddHHmmss")+Guid.genRandom(4));
//	orderRelationUpdateNotifyRequest.setServiceType(BusinessConfig.msg2BusMao.get(command.getMessageContent().trim().toUpperCase()).getServicetype());
//	orderRelationUpdateNotifyRequest.setSpId(command.getSPNumber());
//	orderRelationUpdateNotifyRequest.setTime_stamp(date);
//	orderRelationUpdateNotifyRequest.setUpdateDesc("LTZX");
//	orderRelationUpdateNotifyRequest.setUpdateTime(date);
//	orderRelationUpdateNotifyRequest.setUserId(command.getUserNumber());
//	orderRelationUpdateNotifyRequest.setUserIdType(1);
//	orderRelationUpdateNotifyRequest.setUpdateType(1);//订购
//	stub.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
//} catch (RemoteException e1) {
// e1.printStackTrace();
//}
	
	class JFThread implements Runnable{
		@Override
		public void run() {
			System.out.println("计费线程启动---------------");
			try {
				while(true)
				{
					MongodbDAO.dealJFReq(new ReqHandler()
						{
							@Override
							public void doAny(MTReq req) throws Exception {
									String seq= new SPSender().sendMTReq(req);
									req.setSequenceNumber(seq);
							}
						}
					);
					Thread.sleep(300000);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("计费线程结束---------------");
		}
	}
	
}


//
//
//http://blog.csdn.net/semillon/article/details/7514858
//
//Java socket中isClose()和isConnected()   .
//
//分类： android/NDK  2012-04-26 16:20 7026人阅读 评论(1) 收藏 举报 
//
//socketjava网络nullstringjvm
//
//
//可以引起网络连接关闭的情况有以下4种：
//
//1.  直接调用Socket类的close方法。
//
//2.  只要Socket类的InputStream和OutputStream有一个关闭，网络连接自动关闭（必须通过调用InputStream和OutputStream的 close方法关闭流，才能使网络可爱接自动关闭）。
//
//3.  在程序退出时网络连接自动关闭。
//
//4.  将Socket对象设为null或未关闭最使用new Socket(…)建立新对象后，由JVM的垃圾回收器回收为Socket对象分配的内存空间后自动关闭网络连接。   
//
//虽然这4种方法都可以达到同样的目的，但一个健壮的网络程序最好使用第1种或第2种方法关闭网络连接。这是因为第3种和第4种方法一般并不会马上关闭网络连接，如果是这样的话，对于某些应用程序，将会遗留大量无用的网络连接，这些网络连接会占用大量的系统资源。
//
//在Socket对象被关闭后，我们可以通过isClosed方法来判断某个Socket对象是否处于关闭状态。然而使用isClosed方法所返回的只是Socket对象的当前状态，也就是说，不管Socket对象是否曾经连接成功过，只要处于关闭状态，isClosde就返回true。如果只是建立一个未连接的Socket对象，isClose也同样返回true。如下面的代码将输出false。
//
//
//
//Socket socket = new Socket();
//System.out.println(socket.isClosed()); 
//
//
//
//除了isClose方法，Socket类还有一个isConnected方法来判断Socket对象是否连接成功。看到这个名字，也许读者会产生误解。其实isConnected方法所判断的并不是Socket对象的当前连接状态，而是Socket对象是否曾经连接成功过，如果成功连接过，即使现在isClose返回true，isConnected仍然返回true。因此，要判断当前的Socket对象是否处于连接状态，必须同时使用isClose和isConnected方法，即只有当isClose返回false，isConnected返回true的时候Socket对象才处于连接状态。下面的代码演示了上述Socket对象的各种状态的产生过程。
//
//
//
//
//
//
//package mysocket;
//
//import java.net.*;
//
//public class MyCloseConnection
//{
//   public static void printState(Socket socket, String name)
//   {
//       System.out.println(name + ".isClosed():" + socket.isClosed());
//       System.out.println(name + ".isConnected():" + socket.isConnected());
//       if (socket.isClosed() == false && socket.isConnected() == true)
//           System.out.println(name + "处于连接状态!");
//       else
//           System.out.println(name + "处于非连接状态!");
//       System.out.println();
//   }
//
//   public static void main(String[] args) throws Exception
//   {
//       Socket socket1 = null, socket2 = null;
//
//       socket1 = new Socket("www.ptpress.com.cn", 80);
//       printState(socket1, "socket1");
//
//       socket1.getOutputStream().close();
//       printState(socket1, "socket1");
//
//       socket2 = new Socket();
//       printState(socket2, "socket2");
//
//       socket2.close();
//       printState(socket2, "socket2");
//   }
//}
//
//
//
//
//运行上面的代码后，将有如下的输出结果：
//
//
//
//     socket1.isClosed():false
//
//socket1.isConnected():true
//
//socket1处于连接状态!
//
//
//
//socket1.isClosed():true
//
//socket1.isConnected():true
//
//socket1处于非连接状态!
//
//
//
//socket2.isClosed():false
//
//socket2.isConnected():false
//
//socket2处于非连接状态!
//
//
//
//socket2.isClosed():true
//
//socket2.isConnected():false
//
//socket2处于非连接状态!
//
//
//
//从输出结果可以看出，在socket1的OutputStream关闭后，socket1也自动关闭了。而在上面的代码我们可以看出，对于一个并未连接到服务端的Socket对象socket2，它的isClosed方法为false，而要想让socket2的isClosed方法返回true，必须使用socket2.close显示地调用close方法。


//
//
//Help mongo java driver date
//
//wangwei edited this page on Mar 31 2012 ·  2 revisions  
//
//
//
//
//
//  Pages 10 
//
//
//Home
// 
//
//Design generator
// 
//
//Design home
// 
//
//Help
// 
//
//Help convert
// 
//
//Help directive
// 
//
//Help mongo java driver date
// 
//
//Help mongo logging
// 
//
//Help query
// 
//
//Help slf4j
// 
//
//
//Clone this wiki locally
//
// Copy to clipboard  
// Clone in Desktop  
//
//
//
//
//mongo java driver日期转换问题
//mongo保存日期是一个64-bit整形数。java driver保存Date时会把他自动转换为标准时间GMT。如中国在GMT+8时区，保存2012-01-20 00:00:00到库中，查询后结果竟然是2012-01-19 16:00:00跟想要结果不一致。 可以在com.mongodb.util.JSON找到问题根源：
//
// if (o instanceof Date) {
//            Date d = (Date) o;
//            SimpleDateFormat format = 
//        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
//        serialize(new BasicDBObject("$date", format.format(d)), buf);
//            return;
//  }
//
//找到问就好办，自己完成日期的转换：
//
//
//
//SimpleDateFormat
// SimpleDateFormat format = 
//        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
// format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
// Date date = format.parse("2012-01-20 00:00:00");
//
//date日期变成2012-01-20 08:00:00+08也就是在GMT+8时区下自动加8个小时
//
