package com.tdt.unicom.domains;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description ���������ڶ�һ���û��ļ�Ȩ�����û��ֻ��Ƿ�ע����Ƿ��ͣ��
 */
public class UserRpt extends SGIPCommand{
	private String SPNumber;    //SP�����
	private String userNumber;  //�����õ��ֻ��ţ��ֻ�����ǰ���"86"�����־
	private byte userCondition; //0:ע�� 1��Ƿ��ͣ����2:�ָ�����
	private String reserve;     //��������չ��
	
	public UserRpt(SGIPCommand command) {
		this.header = command.header;
		this.bodybytes = command.bodybytes;
		//--------------------------------
		this.SPNumber = new String(this.bodybytes,0,21).trim();
		this.userNumber = new String(this.bodybytes,21,21).trim();
		this.userCondition = this.bodybytes[42];
		this.reserve = new String(this.bodybytes,43,8);
	}

	public String getSPNumber() {
		return SPNumber;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public byte getUserCondition() {
		return userCondition;
	}

	public String getReserve() {
		return reserve;
	}
	
	
}
