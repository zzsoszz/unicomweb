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
 * @description ������������  
 */
public class MTReq{
	private  String sequenceNumber;//���������Ψһ��ʶ
	private  String chargeNumber;//�ƷѺ���
	private  String spNumber;             //��Ʒ�����̺�
	private  String serviceType;          //��Ʒ�������
	private  String mobile; //�����ֻ���
	private  String messageContent;       //����Ϣ����
	private  String reportFlag;           //�Ƿ���Ҫ������ŷ��͵�״̬
	private  String morelatetoMTFlag;     // 1-MO�㲥����ķǵ�һ����Ϣ  2-��MO�㲥�������MT��Ϣ(����ҵ��) 3ϵͳ���������MT��Ϣ (�Ʒ���)
	private  String linkId;               //linkid,���û��·�����ʱ��ǩȨ
	private  String chargeType;//0 ���   1.�շ�
	private  Date  createdate;
	private String dealflag;//�����־  0.δ����    1.�Ѵ���  3.�ѷ��ͼƷ�   2.����
	
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
