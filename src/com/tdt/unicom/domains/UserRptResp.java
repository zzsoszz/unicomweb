package com.tdt.unicom.domains;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description  ������userpt������Ӧ
 */
public class UserRptResp extends SGIPCommand{
	private byte result;  //0:�ɹ���������������
	private String reserve;
	
	public UserRptResp(byte[] unicomSN) {
		this.header = new SGIPHeader(SGIPCommandDefine.SGIP_USERRPT_RESP);
		this.header.setUnicomSN(unicomSN);
		this.bodybytes = new byte[SGIPCommandDefine.LEN_SGIP_USERRPT_RESP];
	}

	public byte getResult() {
		return result;
	}

	//���ý��
	public void setResult(byte result) {
		this.result = result;
		this.bodybytes[0]=result;
		//--------------------------
		byte[] reserveByte = new byte[8];
		System.arraycopy(this.reserve.getBytes(), 0, reserveByte, 0, this.reserve.length());
		System.arraycopy(reserveByte, 0, this.bodybytes,1, 8);
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
}
