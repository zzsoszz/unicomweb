package com.tdt.unicom.test;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

import org.apache.xerces.impl.dv.util.Base64;


public class TestRecvSms {
	public static void send() {
		try {
			String xmlbody = "{<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<gwsmip>\n" + "  <message_header>\n"
					+ "    <command_id>0x3</command_id>\n"
					+ "    <sequence_number/>\n" + "  </message_header>\n"
					+ "  <message_body>\n" + "    <pk_total>1</pk_total>\n"
					+ "    <pk_number>1</pk_number>\n" + "    <user_numbers>\n"
					+ "       <user_number>13075364025</user_number>\n"
					+ "    </user_numbers>\n"
					+ "    <sp_number>1062836516</sp_number>\n"
					+ "    <service_type>DXAC</service_type>\n"
					+ "    <message_content>" +Base64.encode("(CTR,000)".getBytes())
					+ "</message_content>\n"
					+ "    <report_flag>0</report_flag>\n"
					+ "   </message_body>\n" 
					+ "</gwsmip>\n}";

			Socket socket = SocketFactory.getDefault().createSocket("192.168.0.23", 8805);
			OutputStream out = socket.getOutputStream();
			while (true) {
				System.out.println(xmlbody);
				out.write(xmlbody.getBytes());
				Thread.currentThread();
				Thread.sleep(1000 * 30);
			}
		} catch (ConnectException e) {
			System.err.println("ConnectException:"+e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch(InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void recResp() throws IOException{
		ServerSocket server = ServerSocketFactory.getDefault().createServerSocket(7750);
		
		class Worker implements Runnable {
			private Socket socket;
			
			public Worker(Socket socket) {
				this.socket = socket;
				
			}
			public void run() {
				try {
					InputStream in = socket.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					String temp ="";
					String result="";
					while((temp = reader.readLine())!=null) {
						System.out.println(temp);
						result += temp;
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		while(!Thread.interrupted()) {
			Socket socket = server.accept();
			System.out.println("New connection accepted from "+socket.getInetAddress()+":"+socket.getPort());
			new Thread(new Worker(socket)).start();
		}
		
	}
	public static void main(String[] args)  {
		new Thread(new Runnable() {
			public void run() {
				send();//发送
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				try {
					recResp();
				} catch (IOException e) {
					e.printStackTrace();
				}//接受
			}
		}).start();
	}
}




---------------------------------------



package com.tdt.unicom.test;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.xerces.impl.dv.util.Base64;


/**
 * 
 * @author Tank
 *
 *3.3通信节点编号规则
	在整个网关系统中，所有的通信节点(SMG、GNS、SP和SMSC)都有一个唯一的数字编号，不同的SP或SMSC或SMG或GNS编号不能相同，编号由系统管理人员负责分配。编号规则如下：
	SMG的编号规则：1AAAAX
	SMSC的编号规则：	2AAAAX
	SP的编号规则：3AAAAQQQQQ
	GNS的编号规则：4AAAAX
	其中, AAAA表示四位长途区号(不足四位的长途区号，左对齐，右补零),X表示1位序号,QQQQQ表示5位企业代码。
	
	
	<unicomconf>
		<ipaddr>127.0.0.1</ipaddr>
		<addrport>8802</addrport>
		<spNodeid>3053141211</spNodeid>
		<spListenPort>8081</spListenPort>
		<spUserName>10628365</spUserName>
		<spPassword>10628365</spPassword>
		<smgUserName>10628365</smgUserName>
		<smgPassword>10628365</smgPassword>
	</unicomconf>
 */
public class TestSendSms {
	
	public static void main(String[] args) throws Exception {
			String phoneNumber ="13014862330";  //重庆联通18523019931(不用这个接口了)    广西联通18607717225   我的13014862330
			String spNumber="10655827";//SP的接入号码，字符   重庆联通1065586304(不用这个接口了)  广西联通10655827
			String servcieType="9059066900";//ServiceType	业务代码，由SP定义，字符
			String linkId = "MOODDDS";
			String messageContent ="hello w" +
					"orld!!";
			char reportFlag ='1';
			String xmlbody = "{<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<gwsmip>\n" + "  <message_header>\n"
					+ "    <command_id>0x3</command_id>\n"
					+ "    <sequence_number/>\n" + "  </message_header>\n"
					+ "  <message_body>\n" + "    <pk_total>1</pk_total>\n"
					+ "    <pk_number>1</pk_number>\n" + "    <user_numbers>\n"
					+ "       <user_number>"+phoneNumber+"</user_number>\n"
					+ "    </user_numbers>\n"
					+ "    <sp_number>"+spNumber+"</sp_number>\n"
					+ "    <service_type>"+servcieType+"</service_type>\n"
					+ "    <link_id>"+linkId+"</link_id>\n"
					+ "    <message_content>" + Base64.encode(messageContent.getBytes())
					+ "</message_content>\n"
					+ "    <report_flag>"+reportFlag+"</report_flag>\n"
					+ "   </message_body>\n" + "</gwsmip>\n}";
			//Socket socket = new Socket("119.254.84.182", 8806);//8805重庆  8806广西
			Socket socket = new Socket("127.0.0.1", 8805);//8805重庆  8806广西
			OutputStream out = socket.getOutputStream();
			for (int i =0 ; i < 1; i ++) { //1
				out.write(xmlbody.getBytes());
				out.flush();
			}
			out.close();
			socket.close();
	}
}
