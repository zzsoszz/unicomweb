package com.tdt.unicom.test.cq;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.xerces.impl.dv.util.Base64;


/**
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
			String phoneNumber ="13014862330"; ////重庆联通18523019931(不用这个接口了)    广西联通18607717225   我的13014862330
			String spNumber="10655827";//SP的接入号码，字符   重庆联通1065586304(不用这个接口了)  广西联通10655827
			String servcieType="9059066900";////ServiceType	业务代码，由SP定义，字符
			String linkId = "MOODDDS";
			String messageContent ="hello world!!";
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
			Socket socket = new Socket("127.0.0.1", 8805);
			OutputStream out = socket.getOutputStream();
			for (int i =0 ; i < 1; i ++) { //1
				out.write(xmlbody.getBytes());
				out.flush();
			}
			out.close();
			socket.close();
	}
}
