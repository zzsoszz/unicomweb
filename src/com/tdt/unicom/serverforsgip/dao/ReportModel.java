package com.tdt.unicom.serverforsgip.dao;


public class ReportModel {
	private String submitSequenceNumber;  //该命令所涉及的Submit或deliver命令的弃列号
	private String reportType;            //0:对先前的一条Submit命令的状态报告    //1:对先前一条前转Deliver命令的状态报告
	private String userNumber;            //接收短消息的手机号码
	private String state;                 //0:发送成功　　1:等待发送　2:发送失败
	private String errorCode;             //当state=2时为错误码值，否则为0
	private String reverse;               //保留
	public String getSubmitSequenceNumber() {
		return submitSequenceNumber;
	}
	public void setSubmitSequenceNumber(String submitSequenceNumber) {
		this.submitSequenceNumber = submitSequenceNumber;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getReverse() {
		return reverse;
	}
	public void setReverse(String reverse) {
		this.reverse = reverse;
	}
}
