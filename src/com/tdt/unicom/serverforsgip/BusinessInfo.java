package com.tdt.unicom.serverforsgip;

public class BusinessInfo {
	String servicetype;
	String dgcode;//��������
	String tdcode;//�˶�����
//feetype	
//	0	������Ϣ���͡�Ϊ�����͡����ԡ��Ʒ��û����롱������Ϣ�ѣ����໰�������ں˼�SP�ԳƵ��ŵ���
//	1	�ԡ��Ʒ��û����롱���
//	2	�ԡ��Ʒ��û����롱��������Ϣ��
//	3	�ԡ��Ʒ��û����롱��������ȡ��Ϣ��
//	4	�ԡ��Ʒ��û����롱���շ�����SPʵ��
	String feetype;
	String feevalue;//ȡֵ��Χ0-99999����������Ϣ���շ�ֵ����λΪ�֣���SP���壬�ַ�  ���ڰ������շѵ��û�����ֵΪ����ѵ�ֵ
	public BusinessInfo(String buscode,String dgcode,String feetype,String feevalue)
	{
		this.servicetype=buscode;
		this.dgcode=dgcode;
		this.tdcode="TD"+dgcode;
		this.feetype=feetype;
		this.feevalue=feevalue;
	}
	
	public String getServicetype() {
		return servicetype;
	}
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}


	public String getDgcode() {
		return dgcode;
	}
	public void setDgcode(String dgcode) {
		this.dgcode = dgcode;
	}
	public String getTdcode() {
		return tdcode;
	}
	public void setTdcode(String tdcode) {
		this.tdcode = tdcode;
	}
	public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	public String getFeevalue() {
		return feevalue;
	}
	public void setFeevalue(String feevalue) {
		this.feevalue = feevalue;
	}
}
