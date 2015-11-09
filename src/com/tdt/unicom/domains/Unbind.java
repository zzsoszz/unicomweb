package com.tdt.unicom.domains;


/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description ����֪ͨserver�ˣ�����ѽ���������
 */
public class Unbind extends SGIPCommand{
	public Unbind() {
		this.header = new SGIPHeader(SGIPCommandDefine.SGIP_UNBIND);
	}
	
	public Unbind(SGIPCommand command) {
		this.header = command.header;
		this.bodybytes = command.bodybytes;
	}
}
