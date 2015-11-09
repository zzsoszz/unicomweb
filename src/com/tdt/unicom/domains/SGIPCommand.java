package com.tdt.unicom.domains;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;


/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-1
 * @description SGIP������࣬����sgip������Ҫ�̳д���
 */
public class SGIPCommand {

	public SGIPHeader header = new SGIPHeader(); // ��Ϣͷ
	public byte[] bodybytes = new byte[0]; // ��Ϣ������

	public Logger log = Logger.getLogger(this.getClass());

	/**
	 * ��������ȫ��ת�����ֽ�����
	 * @return byte[] �����ֽ�
	 */
	public  byte[] getCommandBodybytes() {
		byte[] commandbytes = new byte[SGIPCommandDefine.LEN_SGIP_HEADER+ this.bodybytes.length];
		// ����head�ֽ�
		System.arraycopy(header.getCommandHeadBytes(), 0, commandbytes, 0,SGIPCommandDefine.LEN_SGIP_HEADER);
		// ����body�ֽ�
		System.arraycopy(bodybytes, 0, commandbytes,SGIPCommandDefine.LEN_SGIP_HEADER, header.getTotalmsglen()- SGIPCommandDefine.LEN_SGIP_HEADER);
		return commandbytes;
	}

	
	
	public SGIPHeader getHeader() {
		return header;
	}



	public void setHeader(SGIPHeader header) {
		this.header = header;
	}



	/**
	 * ����ͨ�������ط�������
	 * 
	 * @param out
	 *            ����ͨ���ؽ�����������
	 * @throws IOException
	 */
	public void  write(OutputStream out) throws IOException {
		// ������������ֽ�(ͷ+��Ϣ��)
		byte[] commandBytes = this.getCommandBodybytes();
		out.write(commandBytes);
		out.flush();
		
		log.info("��" + Thread.currentThread().getName() + "����SP "
				+ SGIPCommandDefine.getCommandName(this.header.getCommandId()) + "���,{����="+this.header.getTotalmsglen()+",���к�="+this.header.getSequenceNumber()+"}");
	}

	
	public SGIPCommand read(InputStream in) throws IOException {
		
		this.header.readHead(in);
		readDataIntoBody(in);
		
		switch (Bytes4ToInt(this.header.getCommandId())) {
		case 0x1: // ������
			return new Bind(this);
		case 0x80000001: // ����Ӧ
			return new BindResp(this);
		case 0x2: // ע��������
			return new Unbind(this);
		case 0x80000002: // ע������Ӧ
			return new UnbindResp(this);
		case 0x80000003: // Submit��Ӧ
			return new SubmitResp(this);
		case 0x4: // MO����
			return new Deliver(this);
		case 0x5:
			return new Report(this);
		case -2147479552:
			// return new TraceResp(this);
		case 0x11:
			 return new UserRpt(this);
		}
		return null;
	}

	/**
	 * �������ֽڶ�ȡbodybytes����
	 * 
	 * @param in
	 * @throws IOException
	 */
	public void readDataIntoBody(InputStream in) throws IOException {
		this.bodybytes = new byte[this.header.getTotalmsglen()- SGIPCommandDefine.LEN_SGIP_HEADER];
		in.read(bodybytes);
	}

	public static int ByteToInt(byte mybyte) {
		return mybyte;
	}

	public static byte IntToByte(int i) {
		return (byte) i;
	}

	public static int BytesToInt(byte[] mybytes) {
		return (((0xFF & mybytes[0]) << 8) + mybytes[1]);
	}

	public static byte[] IntToBytes(int i) {
		byte[] mybytes = new byte[2];
		mybytes[1] = (byte) (0xFF & i);
		mybytes[0] = (byte) ((0xFF00 & i) >> 8);
		return mybytes;
	}

	/**
	 * ��intת��Ϊbyte����
	 * @param i ��ת����int����
	 * @return byte[] ת�����byte����
	 */
	public static byte[] IntToBytes4(int i) {
		byte[] mybytes = new byte[4];
		mybytes[3] = (byte) (0xFF & i);
		mybytes[2] = (byte) ((0xFF00 & i) >> 8);
		mybytes[1] = (byte) ((0xFF0000 & i) >> 16);
		mybytes[0] = (byte) ((0xFF000000 & i) >> 24);
		return mybytes;
	}

	public static byte[] LongToBytes4(long i) {
		byte[] mybytes = new byte[4];
		mybytes[3] = (byte) (int) (0xFF & i);
		mybytes[2] = (byte) (int) ((0xFF00 & i) >> 8);
		mybytes[1] = (byte) (int) ((0xFF0000 & i) >> 16);
		mybytes[0] = (byte) (int) ((0xFF000000 & i) >> 24);
		return mybytes;
	}

	public static void LongToBytes4(long i, byte[] mybytes) {
		mybytes[3] = (byte) (int) (0xFF & i);
		mybytes[2] = (byte) (int) ((0xFF00 & i) >> 8);
		mybytes[1] = (byte) (int) ((0xFF0000 & i) >> 16);
		mybytes[0] = (byte) (int) ((0xFF000000 & i) >> 24);
	}

	public static void IntToBytes(int i, byte[] mybytes) {
		mybytes[1] = (byte) (0xFF & i);
		mybytes[0] = (byte) ((0xFF00 & i) >> 8);
	}

	/**
	 * ��byte����תΪʮ������
	 * 
	 * @param byteArray
	 * @return
	 */
	public static final String BytesToHexString(byte[] byteArray) {
		StringBuffer sBuffer = new StringBuffer(byteArray.length);
		String sTemp = "";
		for (int i = 0; i < byteArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & byteArray[i]);
			if (sTemp.length() < 2)
				sBuffer.append(0);
			sBuffer.append(sTemp.toUpperCase());
		}
		return sBuffer.toString();
	}

	/**
	 * ��int����ת��Ϊ�ֽ����飬���鳤��Ϊ4
	 * 
	 * @param i
	 *            ׼��ת��������
	 * @param mybytes
	 *            intת�����byte[]
	 */
	public static void IntToBytes4(int i, byte[] mybytes) {
		mybytes[3] = (byte) (0xFF & i);
		mybytes[2] = (byte) ((0xFF00 & i) >> 8);
		mybytes[1] = (byte) ((0xFF0000 & i) >> 16);
		mybytes[0] = (byte) (int) ((0xFF000000 & i) >> 24);
	}

	/**
	 * ���ֽ�����ת��Ϊint����
	 * 
	 * @param mybytes
	 * @return int �ֽ�����ת����int����
	 */
	public static int Bytes4ToInt(byte[] mybytes) {
		return ((0xFF & mybytes[0]) << 24 | (0xFF & mybytes[1]) << 16
				| (0xFF & mybytes[2]) << 8 | 0xFF & mybytes[3]);
	}

	/**
	 * ���ֽ�����ת��Ϊlong����
	 * 
	 * @param mybytes
	 *            ׼��ת��������
	 * @return long �ֽ�����ת�����long����
	 */
	public static long Bytes4ToLong(byte[] mybytes) {
		return ((0xFF & mybytes[0]) << 24 | (0xFF & mybytes[1]) << 16
				| (0xFF & mybytes[2]) << 8 | 0xFF & mybytes[3]);
	}

	/**
	 * ���鿽��
	 * @param source Դ����
	 * @param dest Ŀ�����顡
	 * @param sourceBegin��Դ��ʼλ��
	 * @param sourceEnd    Դ����λ�á�
	 * @param destBegin����Ŀ�����鿪ʼλ��
	 */
	public static void BytesCopy(byte[] source, byte[] dest, int sourceBegin,
			int sourceEnd, int destBegin) {
		int index = 0;
		for (int i = sourceBegin; i <= sourceEnd; ++i) {
			dest[(destBegin + index)] = source[i];
			++ index;
		}
	}
	
}
