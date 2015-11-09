package com.tdt.unicom.domains;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-4
 * @description bind�������  
 */
public class BindResp extends SGIPCommand {
	private byte result; //0��ʾִ�гɹ�
	private String reserve="";
	
	public BindResp(byte[] unicomSN) {
		this.header = new SGIPHeader(SGIPCommandDefine.SGIP_BIND_RESP);
		this.header.setUnicomSN(unicomSN);
		this.bodybytes = new byte[SGIPCommandDefine.LEN_SGIP_BIND_RESP];
		//-------------------------------------------
		byte[] reserveByte = new byte[8];
		System.arraycopy(this.reserve.getBytes(), 0, reserveByte, 0, this.reserve.length());
		System.arraycopy(reserveByte, 0, this.bodybytes,1, 8);
	}
	public BindResp(SGIPCommand command) {
		this.header = command.header;
		this.bodybytes = command.bodybytes;
		//--------------------------------------------
		result =command.bodybytes[0];
		//--------------------------------------------
		byte[] reserveBytes = new byte[8];
		System.arraycopy(command.bodybytes, 1, reserveBytes, 0, 8);
		reserve = new String(reserveBytes);
		//--------------------------------------------
	}
	public int getResult() {
		return result;
	}
	public String getReserve() {
		return reserve;
	}
	public void setResult(byte result) {
		this.result = result;
		this.bodybytes[0] =result;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
}
