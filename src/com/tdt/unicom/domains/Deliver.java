package com.tdt.unicom.domains;

import java.nio.charset.Charset;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description��deliver����,����SMG��SP����һ������
 */
public class Deliver extends SGIPCommand{

	private String userNumber;     //���Ͷ���Ϣ���û��ֻ���,�ֻ��������ǰ��"86"�����־
	private String SPNumber;       //SP�Ľ����
	private byte TP_pid;		   //GSMЭ������
	private byte TP_udhi;	       //GSMЭ������
	private byte messageCoding;	   //����Ϣ�ı��뷽ʽ
	                               //0:��ASCII�ַ���
		                           //3:д������
	                               //4:�����Ʊ���
		                           //8: UCS2����
	                               //15:GBK����
	private int messageLength;     //����Ϣ�ĳ���
	private String messageContent; //����Ϣ������ 
	private String linkID="";	   //��������չ��
	
	public Deliver(SGIPCommand command) {
		this.header = command.header;
		this.bodybytes = command.bodybytes;
		//-------------------------�û��ֻ���
		this.userNumber = new String(this.bodybytes,0,21).trim();
		//-------------------------SP�����
		this.SPNumber = new String(this.bodybytes,21,21).trim();
		//--------------------------TP_pid
		this.TP_pid = command.bodybytes[42];
		//--------------------------TP_udhi
		this.TP_udhi= command.bodybytes[43];
		//--------------------------���뷽ʽ
		this.messageCoding = command.bodybytes[44];
		//--------------------------����Ϣ����
		byte[] messageLengthBytes = new byte[4];
		System.arraycopy(command.bodybytes, 45, messageLengthBytes, 0, 4);
		this.messageLength = Bytes4ToInt(messageLengthBytes);
		if(this.messageLength > 160) {
			this.messageLength=160;
		}
		//--------------------------����Ϣ����
		byte[] messageContentBytes = new byte[messageLength];
		System.arraycopy(command.bodybytes, 49, messageContentBytes, 0, messageLength);
		if(messageCoding==8) {//ucs2
			this.messageContent=new String(messageContentBytes,Charset.forName("UnicodeBigUnmarked"));
		} else if(messageCoding==15) {
			this.messageContent=new String(messageContentBytes,Charset.forName("gbk"));
		} else  {
			this.messageContent= new String(messageContentBytes);
		}
		//--------------------------����
		byte[] linkIDBytes = new byte[8];
		System.arraycopy(command.bodybytes, 49+messageLength, linkIDBytes, 0, 8);
		this.linkID = new String(linkIDBytes);
	}
	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public String getSPNumber() {
		return SPNumber;
	}

	public void setSPNumber(String sPNumber) {
		SPNumber = sPNumber;
	}

	public byte getTP_pid() {
		return TP_pid;
	}
	
	public void setTP_pid(byte tPPid) {
		TP_pid = tPPid;
	}


	public byte getTP_udhi() {
		return TP_udhi;
	}

	public void setTP_udhi(byte tPUdhi) {
		TP_udhi = tPUdhi;
	}

	public byte getMessageCoding() {
		return messageCoding;
	}

	public void setMessageCoding(byte messageCoding) {
		this.messageCoding = messageCoding;
	}
	
	public int getMessageLength() {
		return messageLength;
	}
	
	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getLinkID() {
		return linkID;
	}

	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}
}
