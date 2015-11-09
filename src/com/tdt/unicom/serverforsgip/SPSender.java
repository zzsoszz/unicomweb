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
 * @description  �����·���
 * 
 * һ������һ��������,���Ȱ�,Ȼ�������ݣ�Ȼ������
 */
public final class SPSender {
	
	protected static String unicomIp;     //��ͨSMG��IP��ַ
	protected static int unicomPort;      //��ͨSMG�����Ķ˿ں�
	protected static String spLoginName;  //��½SMG���õ����û���
	protected static String spLogPassword;//��½SMG���õ�������
	protected static String spCorpId;        //5λ��ҵ���� 33054
	
	
	
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private Logger log = Logger.getLogger(getClass());

	
	/**
	 * ����ͨSMG��������ͨ��
	 * @throws IOException
	 */
	public  void bind() throws IOException{
			    // --------------------����
				System.out.println("--------------:"+unicomIp+"  "+unicomPort);
				socket = SocketFactory.getDefault().createSocket(unicomIp,unicomPort);
				socket.setTcpNoDelay(true);// ������������
				socket.setTrafficClass(0x04 | 0x10);//�߿ɿ��Ժ���С�ӳ�
				
				
//				SocketAddress addr = new InetSocketAddress("119.254.84.182", 8888);
//				Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
//				Socket socket = new Socket(proxy);
//				InetSocketAddress dest = new InetSocketAddress(unicomIp, unicomPort);
//				socket.setTcpNoDelay(true);// ������������
//				socket.setTrafficClass(0x04 | 0x10);//�߿ɿ��Ժ���С�ӳ�
//				socket.connect(dest);
				
				
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				log.info(Thread.currentThread().getName() + "����SMG����Socket����");
				// --------------------��
				Bind bind = new Bind();
				//1��SP��SMG���������ӣ����ڷ�������
				//2��SMG��SP���������ӣ����ڷ�������
				//3��SMG֮�佨�������ӣ�����ת������
				bind.setLoginType((byte) 1); 
				bind.setLoginName(spLoginName);
				bind.setLoginPassword(spLogPassword);
				bind.write(out);
				log.info(Thread.currentThread().getName() + "��SMG����bind����");
				//--------------------����Ӧ
				
				
				BindResp res = (BindResp) bind.read(in);
				if (res.getResult() != 0) {
					log.fatal("SMG�ܾ����ӡ������룺" + res.getResult()+"ԭ��"+SGIPCodeHelper.getDescription(res.getResult()));
					throw new SGIPException(res.getResult());
				}
				log.info(Thread.currentThread().getName() + "�ѳɹ���SMG��������Ϣ�·�ͨ����");
	}

	
	
	
	//����ͨ�ݽ������·�
	//�˵ط���synchronized������ԭ���Ƿ�ֹ��ʱ���߳������ʱ���ѵ���SMG����unbind����
	//��SMG��SP���ӶϿ�������ʱ�պ����·�ʵ��������У��������ͬ�����ƣ���ô��ʱ������Ͽ����ӣ����·��߳�����Ͽ������ӷ���
	//���ݵ���IO���쳣
	public  String sendMTReq(MTReq mtreq) throws IOException {
		log.info("send ---------------------------start");
		bind();
		
		// --------------------�·�
		Submit submit = new Submit();
		submit.setSPNumber(mtreq.getSpNumber());
		if(mtreq.getChargeNumber()==null || "".equals(mtreq.getChargeNumber()))
		{
			submit.setChargeNumber("000000000000000000000");// ����21,���ȫ0��ʾ��SP֧���������ŷ���
		}else
		{
			submit.setChargeNumber(mtreq.getChargeNumber());
		}
		
		submit.setUserNumber(new String[] { mtreq.getMobile() });
		submit.setCorpId(spCorpId); //��λ��ҵ����
		
		if(mtreq.getChargeType().equals("0"))
		{
			submit.setFeeType((byte) 0);
			submit.setFeeValue("0");
		}else{
			submit.setFeeType((byte)Integer.parseInt(BusinessConfig.servicetype2BusMao.get(mtreq.getServiceType()).getFeetype()));
			submit.setFeeValue(BusinessConfig.servicetype2BusMao.get(mtreq.getServiceType()).getFeevalue());
		}
		
		submit.setGivenValue("0");//ȡֵ��Χ0-99999�������û��Ļ��ѣ���λΪ�֣���SP���壬��ָ��SP���û����͹��ʱ�����ͻ��ѣ��ַ�
		submit.setAgentFlag((byte) 0);//0��Ӧ�գ�1��ʵ�գ��ַ�
		submit.setMorelatetoMTFlag(Byte.valueOf(mtreq.getMorelatetoMTFlag())); // 0-MO�㲥����ĵ�һ��MT��Ϣsubmit.setMorelatetoMTFlag((byte) 2);
		// 1-MO�㲥����ķǵ�һ����Ϣ
		// 2-��MO�㲥�������MT��Ϣ(����ҵ��)
		// 3ϵͳ���������MT��Ϣ
		submit.setPriority((byte) 0);//���ȼ�0-9�ӵ͵��ߣ�Ĭ��Ϊ0��ʮ����������
		submit.setExpireTime("");
		submit.setScheduleTime("");
		submit.setReportFlag(Byte.valueOf(mtreq.getReportFlag())); // �Ƿ���SP����״̬
		submit.setTP_pid((byte) 0);
		submit.setTP_udhi((byte) 0);
		submit.setMessageCoding((byte) 15);//����Ϣ�ı����ʽ�� 0����ASCII�ַ���	3��д������ 	4�������Ʊ���	8��UCS2���� 	15��GBK����  �����μ�GSM3.38��4�ڣ�SMS Data Coding Scheme  	ʮ����������
		submit.setMessageType((byte) 0);//��Ϣ���ͣ�	0-����Ϣ��Ϣ	����������
		submit.setMessageContent(mtreq.getMessageContent());
		submit.setUserCoun((byte) 1); // ����sgip1.2��չЭ�������1,������Ϊҵ��Ƿ�������
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
			log.info("��" + Thread.currentThread().getName()+ " ���͵�MT����ɹ��ݽ���SMG ��");
			//���·�ʵ����ӵ��ѷ���������
			//mtSendedMap.put(submit.header.getSequenceNumber(),submit);
		}
		log.warn("��" + Thread.currentThread().getName()+ " ���͵�MT����ݽ���SMGʧ��!,������ " + submitres.getResult() + "��");
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
			log.info(Thread.currentThread().getName()+" ��SMG����unbind����");
			resp = (UnbindResp) unbind.read(in);
		}catch (IOException ex) 
		{ 
			log.error("��SMG����unbind����ʱIO�쳣", ex); 
		}
		if (Arrays.equals(resp.header.getCommandId(),SGIPCommandDefine.SGIP_UNBIND_RESP)) {
			log.info("SMG�յ�unbind���SP�ر�����");
			try {
				if(in !=null) in.close();
				if(out !=null) out.close();
				if(socket !=null) socket.close();
			} catch(IOException e) {
				log.warn("�ͷ�socket��Դ�����쳣");
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Byte.valueOf("3"));
		System.out.println(Byte.valueOf("3"));
	}
}