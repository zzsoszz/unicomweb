package com.tdt.unicom.serverforsgip.dao;


public class ReportModel {
	private String submitSequenceNumber;  //���������漰��Submit��deliver��������к�
	private String reportType;            //0:����ǰ��һ��Submit�����״̬����    //1:����ǰһ��ǰתDeliver�����״̬����
	private String userNumber;            //���ն���Ϣ���ֻ�����
	private String state;                 //0:���ͳɹ�����1:�ȴ����͡�2:����ʧ��
	private String errorCode;             //��state=2ʱΪ������ֵ������Ϊ0
	private String reverse;               //����
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
