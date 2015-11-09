package com.tdt.unicom.serverforsgip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;

import org.apache.log4j.Logger;

import com.tdt.unicom.domains.Bind;
import com.tdt.unicom.domains.BindResp;
import com.tdt.unicom.domains.SGIPCommand;
import com.tdt.unicom.domains.SGIPCommandDefine;
import com.tdt.unicom.domains.SGIPException;
import com.tdt.unicom.domains.Submit;
import com.tdt.unicom.domains.SubmitResp;
import com.tdt.unicom.domains.Unbind;
import com.tdt.unicom.domains.UnbindResp;
import com.tdt.unicom.serverforsgip.dao.MTReq;
import com.tdt.unicom.util.SGIPCodeHelper;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description  短信下发类
 * 
 * 一个请求一个发送器,首先绑定,然后发送数据，然后解除绑定
 */
public final class SPSender {
	
	protected static String unicomIp;     //联通SMG的IP地址
	protected static int unicomPort;      //联通SMG监听的端口号
	protected static String spLoginName;  //登陆SMG所用到的用户名
	protected static String spLogPassword;//登陆SMG所用到的密码
	protected static String spCorpId;        //5位企业代码 33054
	
	
	
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private Logger log = Logger.getLogger(getClass());

	
	/**
	 * 与联通SMG建立短信通道
	 * @throws IOException
	 */
	public  void bind() throws IOException{
			    // --------------------连接
				System.out.println("--------------:"+unicomIp+"  "+unicomPort);
				socket = SocketFactory.getDefault().createSocket(unicomIp,unicomPort);
				socket.setTcpNoDelay(true);// 数据立即发送
				socket.setTrafficClass(0x04 | 0x10);//高可靠性和最小延迟
				
				
//				SocketAddress addr = new InetSocketAddress("119.254.84.182", 8888);
//				Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
//				Socket socket = new Socket(proxy);
//				InetSocketAddress dest = new InetSocketAddress(unicomIp, unicomPort);
//				socket.setTcpNoDelay(true);// 数据立即发送
//				socket.setTrafficClass(0x04 | 0x10);//高可靠性和最小延迟
//				socket.connect(dest);
				
				
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				log.info(Thread.currentThread().getName() + "已与SMG建立Socket连接");
				// --------------------绑定
				Bind bind = new Bind();
				//1：SP向SMG建立的连接，用于发送命令
				//2：SMG向SP建立的连接，用于发送命令
				//3：SMG之间建立的连接，用于转发命令
				bind.setLoginType((byte) 1); 
				bind.setLoginName(spLoginName);
				bind.setLoginPassword(spLogPassword);
				bind.write(out);
				log.info(Thread.currentThread().getName() + "向SMG发送bind请求");
				//--------------------绑定响应
				
				
				BindResp res = (BindResp) bind.read(in);
				if (res.getResult() != 0) {
					log.fatal("SMG拒绝连接。错误码：" + res.getResult()+"原因："+SGIPCodeHelper.getDescription(res.getResult()));
					throw new SGIPException(res.getResult());
				}
				log.info(Thread.currentThread().getName() + "已成功与SMG建立短消息下发通道。");
	}

	
	
	
	//向联通递交短信下发
	//此地方用synchronized防护的原因是防止计时器线程因空闲时间已到向SMG发送unbind命令
	//将SMG与SP连接断开，而此时刚好有下发实例进入队列，如果不加同步控制，那么计时器请求断开连接，而下发线程则向断开的连接发送
	//数据导致IO流异常
	public  String sendMTReq(MTReq mtreq) throws IOException {
		log.info("send ---------------------------start");
		bind();
		
		// --------------------下发
		Submit submit = new Submit();
		submit.setSPNumber(mtreq.getSpNumber());
		if(mtreq.getChargeNumber()==null || "".equals(mtreq.getChargeNumber()))
		{
			submit.setChargeNumber("000000000000000000000");// 长度21,如果全0表示由SP支付该条短信费用
		}else
		{
			submit.setChargeNumber(mtreq.getChargeNumber());
		}
		
		submit.setUserNumber(new String[] { mtreq.getMobile() });
		submit.setCorpId(spCorpId); //四位企业代码
		
		if(mtreq.getChargeType().equals("0"))
		{
			submit.setFeeType((byte) 0);
			submit.setFeeValue("0");
		}else{
			submit.setFeeType((byte)Integer.parseInt(BusinessConfig.servicetype2BusMao.get(mtreq.getServiceType()).getFeetype()));
			submit.setFeeValue(BusinessConfig.servicetype2BusMao.get(mtreq.getServiceType()).getFeevalue());
		}
		
		submit.setGivenValue("0");//取值范围0-99999，赠送用户的话费，单位为分，由SP定义，特指由SP向用户发送广告时的赠送话费，字符
		submit.setAgentFlag((byte) 0);//0：应收；1：实收，字符
		submit.setMorelatetoMTFlag(Byte.valueOf(mtreq.getMorelatetoMTFlag())); // 0-MO点播引起的第一条MT信息submit.setMorelatetoMTFlag((byte) 2);
		// 1-MO点播引起的非第一条信息
		// 2-非MO点播引引起的MT消息(定购业务)
		// 3系统反馈引起的MT消息
		submit.setPriority((byte) 0);//优先级0-9从低到高，默认为0，十六进制数字
		submit.setExpireTime("");
		submit.setScheduleTime("");
		submit.setReportFlag(Byte.valueOf(mtreq.getReportFlag())); // 是否向SP报告状态
		submit.setTP_pid((byte) 0);
		submit.setTP_udhi((byte) 0);
		submit.setMessageCoding((byte) 15);//短消息的编码格式。 0：纯ASCII字符串	3：写卡操作 	4：二进制编码	8：UCS2编码 	15：GBK编码  其它参见GSM3.38第4节：SMS Data Coding Scheme  	十六进制数字
		submit.setMessageType((byte) 0);//信息类型：	0-短消息信息	其它：待定
		submit.setMessageContent(mtreq.getMessageContent());
		submit.setUserCoun((byte) 1); // 根据sgip1.2扩展协议必须填1,否则视为业务非法包处理
		submit.setServiceType(mtreq.getServiceType());
		submit.setLinkID(mtreq.getLinkId());
		
		
		System.out.println("SequenceNumber:"+submit.getHeader().getSequenceNumber());
		System.out.println("UserNumber:"+submit.getUserNumber()[0]);
		System.out.println("SPNumber:"+submit.getSPNumber());
		System.out.println("ChargeNumber:"+submit.getChargeNumber());
		System.out.println("corpId:"+submit.getCorpId());
		System.out.println("serviceType:"+submit.getServiceType());
		System.out.println("feeType:"+submit.getFeeType());
		System.out.println("feeValue:"+submit.getFeeValue());
		System.out.println("givenValue:"+submit.getGivenValue());
		System.out.println("expireTime:"+submit.getExpireTime());
		System.out.println("scheduleTime:"+submit.getScheduleTime());
		System.out.println("messageContent:"+submit.getMessageContent());
		System.out.println("linkID:"+submit.getLinkID());
		System.out.println("ReportFlag:"+submit.getReportFlag());
		System.out.println("MorelatetoMTFlag:"+submit.getMorelatetoMTFlag());
		
		
		log.info("SequenceNumber:"+submit.getHeader().getSequenceNumber());
		log.info("UserNumber:"+submit.getUserNumber()[0]);
		log.info("SPNumber:"+submit.getSPNumber());
		log.info("ChargeNumber:"+submit.getChargeNumber());
		log.info("corpId:"+submit.getCorpId());
		log.info("serviceType:"+submit.getServiceType());
		log.info("feeType:"+submit.getFeeType());
		log.info("feeValue:"+submit.getFeeValue());
		log.info("givenValue:"+submit.getGivenValue());
		log.info("expireTime:"+submit.getExpireTime());
		log.info("scheduleTime:"+submit.getScheduleTime());
		log.info("messageContent:"+submit.getMessageContent());
		log.info("linkID:"+submit.getLinkID());
		log.info("ReportFlag:"+submit.getReportFlag());
		log.info("MorelatetoMTFlag:"+submit.getMorelatetoMTFlag());
		
		
		submit.write(out);
		SubmitResp submitres = (SubmitResp) submit.read(in);
		if (submitres.getResult() == 0) {
			log.info("【" + Thread.currentThread().getName()+ " 发送的MT请求成功递交到SMG 】");
			//将下发实例添加到已发送容器中
			//mtSendedMap.put(submit.header.getSequenceNumber(),submit);
		}
		log.warn("【" + Thread.currentThread().getName()+ " 发送的MT请求递交到SMG失败!,错误码 " + submitres.getResult() + "】");
		unbind();
		
		
		log.info("send ---------------------------end");
		return  submit.header.getSequenceNumber();
	}
	
	
	
	public void unbind()
	{
		Unbind unbind = new Unbind();
		UnbindResp resp = null;
		try {
			unbind.write(out);
			log.info(Thread.currentThread().getName()+" 向SMG发送unbind命令");
			resp = (UnbindResp) unbind.read(in);
		}catch (IOException ex) 
		{ 
			log.error("向SMG发送unbind命令时IO异常", ex); 
		}
		if (Arrays.equals(resp.header.getCommandId(),SGIPCommandDefine.SGIP_UNBIND_RESP)) {
			log.info("SMG收到unbind命令，SP关闭连接");
			try {
				if(in !=null) in.close();
				if(out !=null) out.close();
				if(socket !=null) socket.close();
			} catch(IOException e) {
				log.warn("释放socket资源发生异常");
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Byte.valueOf("3"));
		System.out.println(Byte.valueOf("3"));
	}
}