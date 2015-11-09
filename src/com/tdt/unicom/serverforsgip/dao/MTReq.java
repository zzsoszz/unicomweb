package com.tdt.unicom.serverforsgip.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.xerces.impl.dv.util.Base64;
import org.jdom.Document;
import org.jdom.Element;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-15
 * @description 短信下行请求  
 */
public class MTReq{
	private  String sequenceNumber;//发送请求的唯一标识
	private  String chargeNumber;//计费号码
	private  String spNumber;             //产品服务商号
	private  String serviceType;          //产品服务代码
	private  String mobile; //发送手机号
	private  String messageContent;       //短信息内容
	private  String reportFlag;           //是否需要报告短信发送的状态
	private  String morelatetoMTFlag;     // 1-MO点播引起的非第一条信息  2-非MO点播引引起的MT消息(定购业务) 3系统反馈引起的MT消息 (计费用)
	private  String linkId;               //linkid,向用户下发短信时的签权
	private  String chargeType;//0 免费   1.收费
	private  Date  createdate;
	private String dealflag;//处理标志  0.未处理    1.已处理  3.已发送计费   2.作废
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getChargeNumber() {
		return chargeNumber;
	}
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}
	public String getMorelatetoMTFlag() {
		return morelatetoMTFlag;
	}
	public void setMorelatetoMTFlag(String morelatetoMTFlag) {
		this.morelatetoMTFlag = morelatetoMTFlag;
	}
	public String getDealflag() {
		return dealflag;
	}
	public void setDealflag(String dealflag) {
		this.dealflag = dealflag;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getSpNumber() {
		return spNumber;
	}
	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getReportFlag() {
		return reportFlag;
	}
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
}
