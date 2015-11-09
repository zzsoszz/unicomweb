package com.tdt.unicom.serverforsgip;

public class BusinessInfo {
	String servicetype;
	String dgcode;//订购代码
	String tdcode;//退订代码
//feetype	
//	0	“短消息类型”为“发送”，对“计费用户号码”不计信息费，此类话单仅用于核减SP对称的信道费
//	1	对“计费用户号码”免费
//	2	对“计费用户号码”按条计信息费
//	3	对“计费用户号码”按包月收取信息费
//	4	对“计费用户号码”的收费是由SP实现
	String feetype;
	String feevalue;//取值范围0-99999，该条短消息的收费值，单位为分，由SP定义，字符  对于包月制收费的用户，该值为月租费的值
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
