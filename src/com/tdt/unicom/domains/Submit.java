package com.tdt.unicom.domains;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-21
 * @description  �����·���
 */




public class Submit extends SGIPCommand{
	private String SPNumber;         //SP�������(21�ֽ�)
	
	private String ChargeNumber;     //���Ѻ���,�ֻ�����ǰ��"86"�����־,���ҽ���Ⱥ���Ҷ��û��շ���Ϊ��;��
	                                 //��Ϊ�գ������������Ϣ�����ķ�����UserNumber������û�֧��;���Ϊȫ�ַ���
	                                 //"000000000000000000000",��ʾ������Ϣ�����ķ�����SP֧�� (21�ֽ�)
	private byte userCoun;           //���ն���Ϣ���ֻ�������ȡֵ ��Χ1��100(1�ֽ�)
	private String[] userNumber;       //���ոö���Ϣ���ֻ��ţ����ֶ��ظ�UserCountָ���Ĵ������ֻ��ż�86�����־(21�ֽ�)
	private String corpId;           //��ҵ����(5�ֽ�)
	private String serviceType;      //ҵ�����(10�ֽ�)
	private byte feeType;			 //�Ʒ�����(6�ֽ�)
	private String feeValue;           //�������ŵ��շ�ֵ����SP���壬���ڰ������շѵ��û�����ֵ��Ϊ����ѵ�ֵ��
	private String givenValue;       //���ͻ���
	private byte agentFlag;          //���շѱ��־(0Ӧ�� 1ʵ��)
	private byte morelatetoMTFlag;   //����MT ��Ϣ��ԭ��
	                                 //0-MO�㲥����ĵ�һ��MT��Ϣ
	                                 //1-MO�㲥����ķǵ�һ�� MT��Ϣ 
	                                 //2-��MO�㲥 �����MT��Ϣ
	                                 //3-ϵͳ���������MT��Ϣ
	private byte priority;           //���ȼ�(0-9),�ӵ͵��ߣ�Ĭ��Ϊ0
	private String expireTime;		 //����Ϣ������ֹʱ��  ����ʽΪ"yymmddhhmmsstnnp"
	private String scheduleTime;     //��ʱ����ʱ��,���Ϊ�շ�ʾ�������ͣ���ʽ��"yymmddhhmmsstnnp"
	private byte reportFlag;		 //״̬������
	                                 //0-������Ϣֻ��������ʱҪ����״̬����
	                                 //1-������Ϣ��������Ƿ�ɹ���Ҫ����״̬����
	                                 //2-������Ϣ����Ҫ����״̬����
	                                 //3-������Ϣ��Я�����¼Ʒ���Ϣ�����·����û�
	                                 //����������ȱʡΪ0
    private byte TP_pid;             //GSMЭ������
	private byte TP_udhi;            //GSMЭ������
	private byte messageCoding;      //����Ϣ�ı����ʽ
                                     //0:��ASCII�ַ���
                                     //3:д������
                                     //4:�����Ʊ���
                                     //8:UCS2����
                                     //15:GBK����
    private byte messageType;        //��Ϣ����
                                     //0-����Ϣ��Ϣ  ���� :����
    private int messageLength;       //����Ϣ�ĳ��� 
    private String messageContent;   //����Ϣ������
    private String linkID;           //linkID

	public Submit() {
		
	}
	
	public Submit(int contentLen) {
	}
	public String getSPNumber() {
		return SPNumber;
	}
	public void setSPNumber(String sPNumber) {
		SPNumber = sPNumber;
	}
	public String getChargeNumber() {
		return ChargeNumber;
	}
	public void setChargeNumber(String chargeNumber) {
		ChargeNumber = chargeNumber;
	}
	public byte getUserCoun() {
		return userCoun;
	}
	public void setUserCoun(byte userCoun) {
		this.userCoun = userCoun;
	}
	public String[] getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String[] userNumber) {
		this.userNumber = userNumber;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getServiceType() {
		return serviceType;
	}
	//ҵ�����
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public byte getFeeType() {
		return feeType;
	}
	//�Ʒ�����
	public void setFeeType(byte feeType) {
		this.feeType = feeType;
	}
	public String getFeeValue() {
		return feeValue;
	}
	//һ�����ŵ��շ�ֵ��
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}
	
	public String getGivenValue() {
		return givenValue;
	}
	//���ͻ��ѡ�
	public void setGivenValue(String givenValue) {
		this.givenValue = givenValue;
	}
	public byte getAgentFlag() {
		return agentFlag;
	}
	//���շѱ�־(0:Ӧ�ա�1:ʵ��)
	public void setAgentFlag(byte agentFlag) {
		this.agentFlag = agentFlag;
	}
	public byte getMorelatetoMTFlag() {
		return morelatetoMTFlag;
	}
	//����MT��Ϣ��ԭ��
	public void setMorelatetoMTFlag(byte morelatetoMTFlag) {
		this.morelatetoMTFlag = morelatetoMTFlag;
	}
	public byte getPriority() {
		return priority;
	}
	//���ȼ�
	public void setPriority(byte priority) {
		this.priority = priority;
	}
	public String getExpireTime() {
		return expireTime;
	}
	//����Ϣ������ֹʱ�䡡
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	//����Ϣ��ʱ����ʱ�䡡
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public byte getReportFlag() {
		return reportFlag;
	}
	//״̬������
	public void setReportFlag(byte reportFlag) {
		this.reportFlag = reportFlag;
	}
	public byte getTP_pid() {
		return TP_pid;
	}
	public void setTP_pid(byte tPPid) {
		TP_pid = tPPid;
	}
	public byte getTP_udhi() {
		return TP_udhi;
	}
	public void setTP_udhi(byte tPUdhi) {
		TP_udhi = tPUdhi;
	}
	public byte getMessageCoding() {
		return messageCoding;
	}
	//����Ϣ�����ʽ
	public void setMessageCoding(byte messageCoding) {
		this.messageCoding = messageCoding;
	}
	public byte getMessageType() {
		return messageType;
	}
	//��Ϣ����
	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}
	public int getMessageLength() {
		return messageLength;
	}
	public String getMessageContent() {
		return messageContent;
	}
	//����Ϣ����
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getLinkID() {
		return linkID;
	}
	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}
	@Override
	public byte[] getCommandBodybytes() {
		 byte[] temp_chn = null;
	     if (this.messageCoding == 15) {
	        try {
				temp_chn = this.messageContent.getBytes("GBK");
			} catch (UnsupportedEncodingException e) {e.printStackTrace();}
	        this.messageLength = temp_chn.length;
	     } else {
	        this.messageLength = this.messageContent.length();
	     }
	     
		this.header = new SGIPHeader(SGIPCommandDefine.SGIP_SUBMIT);
		this.header.setTotalmsglen(143+21*this.userCoun+this.messageLength);
		this.bodybytes = new byte[this.header.getTotalmsglen() - SGIPCommandDefine.LEN_SGIP_HEADER];
		//---------------------SPNumber
		byte[] tempbytes = new byte[21];
		if(this.SPNumber.length() >21)
			throw new SGIPException(String.valueOf("SPNumber Longer than 21 bytes:").concat(String.valueOf(this.SPNumber)));
		this.SPNumber.getBytes(0,this.SPNumber.length(),tempbytes,0);
		BytesCopy(tempbytes, this.bodybytes, 0, 20, 0);
		//---------------------ChargeNumber
		tempbytes = new byte[21];
		if(this.ChargeNumber.length() >21)
			throw new SGIPException(String.valueOf("ChargeNumber longer than 21 bytes:").concat(String.valueOf(this.ChargeNumber)));
		this.ChargeNumber.getBytes(0,this.ChargeNumber.length(),tempbytes, 0);
		BytesCopy(tempbytes, this.bodybytes, 0, 20, 21);
		//----------------------UserCount
		this.bodybytes[42] = this.userCoun;
		//----------------------UserNumbers
		int current_pos = 43;
		for(int i=0; i < this.userCoun; i++) {
			tempbytes = new byte[21];
			String mobile = String.valueOf(this.userNumber[i]);
			if(mobile.length() >21)
				throw new SGIPException(String.valueOf("UserNumber longer than 21 bytes:").concat(String.valueOf(mobile)));
			mobile.getBytes(0,mobile.length(),tempbytes,0);
			BytesCopy(tempbytes, this.bodybytes, 0, 20, current_pos);
			current_pos += 21;
		}
		//------------------------CorpId
		tempbytes = new byte[5];
		if(this.corpId.length() >5) 
			throw new SGIPException(String.valueOf("CorpId longer than 5 bytes:").concat(String.valueOf(this.corpId)));
		this.corpId.getBytes(0,this.corpId.length(),tempbytes,0);
		BytesCopy(tempbytes, this.bodybytes, 0, 4, current_pos);
		current_pos +=5;
		//-------------------------ServiceType
		tempbytes = new byte[10];
		if(this.serviceType.length() > 10)
			throw new SGIPException(String.valueOf("ServiceType longer than 10 bytes:").concat(String.valueOf(this.serviceType)));
		this.serviceType.getBytes(0,this.serviceType.length(),tempbytes,0);
		BytesCopy(tempbytes, this.bodybytes, 0, 9, current_pos);
		current_pos +=10;
		//--------------------------feeType
		this.bodybytes[current_pos] = this.feeType;
		current_pos++;
		//--------------------------feeValue
		tempbytes = new byte[6];
		if(this.feeValue.length() > 6)
			throw new SGIPException(String.valueOf("feeValue longer than 6 bytes:").concat(String.valueOf(this.feeValue)));
		this.feeValue.getBytes(0,this.feeValue.length(),tempbytes,0);
		BytesCopy(tempbytes, this.bodybytes, 0, 5, current_pos);
		current_pos +=6;
		//--------------------------givenValue
		tempbytes = new byte[6];
		if(this.givenValue.length() > 6)
			throw new SGIPException(String.valueOf("GivenValue longer than 6 bytes:").concat(String.valueOf(this.givenValue)));
		this.givenValue.getBytes(0, this.givenValue.length(), tempbytes, 0);
		BytesCopy(tempbytes, this.bodybytes, 0, 5, current_pos);
		current_pos +=6;
		//---------------------------agentFlag
		this.bodybytes[current_pos] = this.agentFlag;
		current_pos++;
		//---------------------------MOrelateMTFlag
		this.bodybytes[current_pos] = this.morelatetoMTFlag;
		current_pos++;
		//---------------------------priority
		this.bodybytes[current_pos] = this.priority;
		current_pos++;
		//---------------------------expireTime
		tempbytes = new byte[16];
		if(this.expireTime.length()>16)
			throw new SGIPException(String.valueOf("EXpireTime longer than 16 bytes:").concat(String.valueOf(this.expireTime)));
		this.expireTime.getBytes(0, this.expireTime.length(), tempbytes, 0);
		BytesCopy(tempbytes, this.bodybytes, 0, 15, current_pos);
		current_pos +=16;
		//----------------------------scheduleTime
		tempbytes = new byte[16];
		if(this.scheduleTime.length()>16)
			throw new SGIPException(String.valueOf("ScheduleTime longer than 16 bytes:").concat(String.valueOf(this.scheduleTime)));
		BytesCopy(tempbytes, this.bodybytes, 0, 15, current_pos);
		current_pos +=16;
		//-----------------------------reportFlag
		this.bodybytes[current_pos] = this.reportFlag;
		current_pos++;
		//-----------------------------TP_pid
		this.bodybytes[current_pos] = this.TP_pid;
		current_pos++;
		//-----------------------------TP_udhi
		this.bodybytes[current_pos] = this.TP_udhi;
		current_pos++;
		//-----------------------------MessageCoding
		this.bodybytes[current_pos] = this.messageCoding;
		current_pos++;
		//-----------------------------MessageType
		this.bodybytes[current_pos] = this.messageType;
		current_pos++;
		//-----------------------------MessageLength
		BytesCopy(IntToBytes4(this.messageLength), this.bodybytes, 0, 3, current_pos);
		current_pos+=4;
		//-----------------------------MessageContent
		if(this.messageCoding==15) {
			BytesCopy(temp_chn, this.bodybytes, 0, this.messageLength-1, current_pos);
		} else {
			 this.messageContent.getBytes(0, this.messageLength, this.bodybytes, current_pos);
		}
		current_pos+=this.messageLength;
		//-----------------------------LinkID
		tempbytes = new byte[8];
		if(this.linkID.length()>8)
			throw new SGIPException(String.valueOf("linkid longer than 8 bytes:").concat(String.valueOf(this.linkID)));
		this.linkID.getBytes(0, this.linkID.length(), tempbytes, 0);
		BytesCopy(tempbytes, this.bodybytes, 0, 7, current_pos);
		
		return super.getCommandBodybytes();
	}
}
