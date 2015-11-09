package com.tdt.unicom.domains;

import java.math.BigInteger;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description ����SMG��SP����֮ǰһ�����ŵ�״̬
 */
public class Report extends SGIPCommand{
	private String submitSequenceNumber;  //���������漰��Submit��deliver��������к�
	private byte reportType;           //0:����ǰ��һ��Submit�����״̬����
		                               //1:����ǰһ��ǰתDeliver�����״̬����
	private String userNumber;         //���ն���Ϣ���ֻ�����
	private byte state;                //0:���ͳɹ�����1:�ȴ����͡�2:����ʧ��
	private byte errorCode;            //��state=2ʱΪ������ֵ������Ϊ0
	private String reverse;            //����
	
	public Report(SGIPCommand command) {
		this.header = command.header;
		this.bodybytes = command.bodybytes;
		
		int curr_index =0;
		//-----------------���к�
		byte[] tmpBytes = null;
		//Դ�ڵ����
		tmpBytes = new byte[5];
		System.arraycopy(this.bodybytes, curr_index, tmpBytes, 1, 4);
		submitSequenceNumber = new BigInteger(tmpBytes).toString();
		curr_index +=4;
		//����ʱ��
		tmpBytes = new byte[4];
		System.arraycopy(this.bodybytes, curr_index, tmpBytes, 0, 4);
		submitSequenceNumber += SGIPCommand.Bytes4ToInt(tmpBytes);
		curr_index +=4;
		//���
		tmpBytes = new byte[4];
		System.arraycopy(this.bodybytes, curr_index, tmpBytes, 0, 4);
		submitSequenceNumber += SGIPCommand.Bytes4ToInt(tmpBytes);
		curr_index +=4;
		//-----------------reportType
		this.reportType=this.bodybytes[curr_index];
		curr_index ++;
		//-----------------UserNumber
		byte[] userNumberBytes = new byte[21];
		System.arraycopy(this.bodybytes, curr_index, userNumberBytes, 0, 21);
		this.userNumber = new String(userNumberBytes).trim();
		curr_index += 21;
		//-----------------state
		this.state = this.bodybytes[curr_index];
		curr_index ++;
		//-----------------ErrorCode
		this.errorCode = this.bodybytes[curr_index];
		curr_index ++;
		//-----------------reverse
		byte[] reverseBytes = new byte[8];
		System.arraycopy(this.bodybytes, curr_index, reverseBytes, 0, 8);
		this.reverse = new String(reverseBytes).trim();
	}
	public String getSubmitSequenceNumber() {
		return submitSequenceNumber;
	}
	public byte getReportType() {
		return reportType;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public byte getState() {
		return state;
	}
	public byte getErrorCode() {
		return errorCode;
	}
	public String getReverse() {
		return reverse;
	}
	
}
