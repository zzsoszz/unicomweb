package com.tdt.unicom.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunnylocus
 * ��ͨ����������
 */
public class SGIPCodeHelper {
	private final static Map<Integer, String> SGIP_Code_Map = new HashMap<Integer, String>();
	
	static {
			//Ӧ�𳣼�������
			SGIP_Code_Map.put(1, "�Ƿ���¼�����¼�������������¼����������");
			SGIP_Code_Map.put(2, "�ظ���¼������ͬһTCP/IP�����������������������¼");
			SGIP_Code_Map.put(3, "���ӹ��ָ࣬�����ڵ�Ҫ��ͬʱ����������������");
			SGIP_Code_Map.put(4, "��¼���ʹ�ָbind�����е�logintype�ֶγ���");
			SGIP_Code_Map.put(5, "������ʽ��ָ�����в���ֵ��������Ͳ�������Э��涨�ķ�Χ����");
			SGIP_Code_Map.put(6, "�Ƿ��ֻ����룬Э���������ֻ������ֶγ��ַ�86130������ֻ�����ǰδ�ӡ�86��ʱ��Ӧ����");
			SGIP_Code_Map.put(7, "��ϢID�� ");
			SGIP_Code_Map.put(8, "��Ϣ���ȴ� ");
			SGIP_Code_Map.put(9, "�Ƿ����кţ��������к��ظ������кŸ�ʽ�����  ");
			SGIP_Code_Map.put(10, " �Ƿ�����GNS ���������û���  ");
			SGIP_Code_Map.put(11, " �ڵ�æ��ָ���ڵ�洢������������ԭ����ʱ�����ṩ��������   ");
			SGIP_Code_Map.put(21, "  Ŀ�ĵ�ַ���ɴָ·�ɱ����·������Ϣ·����ȷ����·�ɵĽڵ���ʱ�����ṩ��������    ");
			SGIP_Code_Map.put(22, "  ·�ɴ�ָ·�ɱ����·�ɵ���Ϣ·�ɳ�����������ת��SMG��     ");
			SGIP_Code_Map.put(23, "  ·�ɲ����ڣ�ָ��Ϣ·�ɵĽڵ���·�ɱ��в�����     ");
			SGIP_Code_Map.put(24, "  �ƷѺ�����Ч����Ȩ���ɹ�ʱ�����Ĵ�����Ϣ      ");
			SGIP_Code_Map.put(25, "  �û�����ͨ�ţ��粻�ڷ�������δ���������      ");
			SGIP_Code_Map.put(26, "  �ֻ��ڴ治��      ");
			SGIP_Code_Map.put(27, "  �ֻ���֧�ֶ���Ϣ      ");
			SGIP_Code_Map.put(28, "  �ֻ����ն���Ϣ���ִ���       ");
			SGIP_Code_Map.put(29, "  ��֪�����û�       ");
			SGIP_Code_Map.put(30, "  ���ṩ�˹���        ");
			SGIP_Code_Map.put(31, "  �Ƿ��豸        ");
			SGIP_Code_Map.put(32, "   ϵͳʧ��        ");
			SGIP_Code_Map.put(33, "   �������Ķ�����         ");
			SGIP_Code_Map.put(34, "   ������ƽ̨�۷�ʧ��         ");
			SGIP_Code_Map.put(57, "   ��¼ip����sp�������ص�ip��sp�������������õ�ip��һ��         ");
			SGIP_Code_Map.put(93, "   SGIPЭ��SP�ڵ��Ŵ�����Ҫ�ǽڵ�������ҵ�������SGIPЭ���ύ��Ϣ������Ӧ�������˵��         ");
			SGIP_Code_Map.put(64, "   ��Ϣ���͵�Դ��ַ����         ");
			SGIP_Code_Map.put(93, "   SGIPЭ��SP�ڵ��Ŵ����ύ��Ϣ�е���ҵ�������         ");
			//���´���ָSMG�յ�SPMS��Ȩ�����ԭ�� 
			SGIP_Code_Map.put(101, "   SPNumber��SP��ҵ����ƥ���         ");
			SGIP_Code_Map.put(102, "   SPNumberδ����򲻺Ϸ�         ");
			SGIP_Code_Map.put(103, "   ��Ʒ���벻����          ");
			SGIP_Code_Map.put(104, "   ҵ���ʷ��������          ");
			SGIP_Code_Map.put(105, "   ҵ����Ϣ������д����           ");
			SGIP_Code_Map.put(106, "   �û�δ���Ƶ�SP�Ƿ��·�����û�ж�����ϵ������ʱ�㲥��ϵ            ");
			SGIP_Code_Map.put(107, "   LinkID��ƥ�䣨MT��MO��           ");
			SGIP_Code_Map.put(108, "   �û�״̬������,��ͣ���û���Ȩ��ͨ�����޷��·�           ");
			SGIP_Code_Map.put(109, "   �ƷѺ�����Ч           ");
			SGIP_Code_Map.put(110, "   �������û���Ȩ��ͨ��������δ����״̬��Ԥ����״̬������״̬����������ͨ��״̬��ͣ��״̬�������������״̬           ");
			SGIP_Code_Map.put(111, "   MT��ϢSPMS��Ȩ��ʱ           ");
			SGIP_Code_Map.put(112, "   �·���MT��������������Ե㲥��ҵ��ʹ��ͬһ��LINKID�·���MT�������ܳ����걨������           ");
			SGIP_Code_Map.put(113, "   �·�MT��������Я����Ϣ�ѵ�MT��Ϣ����           ");
			SGIP_Code_Map.put(114, "   USERCOUNT�ֶβ�Ϊ1�����ֶα�����д1           ");
			SGIP_Code_Map.put(115, "   SP���ܽ�MOFLAG��Ϊ3��SP�·���MT���������»�������MOFLAG�ֶβ�����3           ");
			SGIP_Code_Map.put(116, "   ����NOTIUSER����ԭ������Ҫ��ֹ��SPMS��֪ͨ��Ϣ���û���MO�������з��͸�SP           ");
			SGIP_Code_Map.put(117, "   WEB�㲥�����ֲ�����            ");
			SGIP_Code_Map.put(118, "   ���»���������           ");
			SGIP_Code_Map.put(119, "   �ظ����»���            ");
			SGIP_Code_Map.put(120, "   ����ҵ��ʧ��            ");
			SGIP_Code_Map.put(121, "   �˶�ҵ��ʧ��           ");
			SGIP_Code_Map.put(123, "   �ظ�����            ");
			SGIP_Code_Map.put(124, "   �ظ��㲥            ");
			SGIP_Code_Map.put(162, "   �����û������޶�             ");
	        SGIP_Code_Map.put(163, "   δ�����û���������Ϣ�����ڰ����гͷ�SP             ");
	        SGIP_Code_Map.put(170, "   �û�����ȷ�ϻظ���ʾ�����              ");
	        SGIP_Code_Map.put(171, "   ������ϵ�����ڣ��˶�ʱ��              ");
	        SGIP_Code_Map.put(172, "   ������ϵ�����ڣ�SP��������HELP              ");
	        SGIP_Code_Map.put(173, "   ������ϵ�����м�״̬(�����Ч)��ҵ������               ");
	        SGIP_Code_Map.put(175, "   QX�����˶�֮ǰû�в�ѯ              ");
	        SGIP_Code_Map.put(176, "   ������ϵ������ͣ״̬              ");
	        SGIP_Code_Map.put(177, "   Sp�·��ļƷ��û���VASP�ж�����ϵ�ļƷ��û���һ�£�����sp�°��»�����             ");
	        SGIP_Code_Map.put(179, "   Spͬ��������ϵ�Ĳ�Ʒ�������ʱ������ϵ��һ��              ");
	        SGIP_Code_Map.put(180, "   ��������ȷ�ϳɹ������룻 0000��qxn��00000 ���͵�ƽ̨����ķ����룻0000��qxn��00000 ���͵�sp����ķ����롣 ��Щ��������ز�������ͨѶ�ѡ�ҵ�����               ");
	        SGIP_Code_Map.put(181, "   ����ҵ����ʱ���             ");
	        SGIP_Code_Map.put(182, "   ����ҵ�����·�����              ");
	        SGIP_Code_Map.put(183, "   �㲥��ҵ��֧����������            ");
	        SGIP_Code_Map.put(185, "   ��֧�ֵ���������              ");
	        SGIP_Code_Map.put(186, "   ҵ���ڲ���״̬���ǲ����û�ʹ�ò���ҵ��             ");
	        SGIP_Code_Map.put(187, "   ҵ�����쳣״̬             ");
	        SGIP_Code_Map.put(188, "   �����ö�ҵ��             ");
	        SGIP_Code_Map.put(190, "   �û��㲥�Ĳ���SP���е�ҵ�񣬼�SP�㲥����ҵ�������㲥���в�һ��SP���             ");   
	        SGIP_Code_Map.put(211, "   SP�����쳣״̬             ");
	        SGIP_Code_Map.put(212, "   SP����ҵ���벻����             ");
	        SGIP_Code_Map.put(213, "   SPû��Ȩ���·����¿۷���Ϣ             ");
	        SGIP_Code_Map.put(214, "   Spû�б���Ȩ�ڸ��û����ڵ��п�չҵ��             ");
	        SGIP_Code_Map.put(215, "   Sp�ڸ��û����ڵ��б���ͣ�˿�             ");
	        SGIP_Code_Map.put(216, "   Sp�ڸ��û����ڵ��б���ͣҵ����շ�             ");
	        SGIP_Code_Map.put(221, "   SP��IP��ַ���Ϸ�(������HTTP�ӿڷ������)              ");
	        SGIP_Code_Map.put(222, "   SP����Կ��Ч(������HTTP�ӿڷ������)               ");
	        SGIP_Code_Map.put(225, "   SPģ��mo����δ���ֶ���ָ��ϵͳ���������ô���                ");
	        SGIP_Code_Map.put(231, "   ����ͬ�������ֶηǷ�                 ");
	        SGIP_Code_Map.put(248, "   ϵͳ��֧�ָ÷���û�и�ϵͳָ��                 ");
	        SGIP_Code_Map.put(249, "   ϵͳ��֧�ְ����·�                ");
	        SGIP_Code_Map.put(251, "   ����������ҵ�����˶�                 ");
	        SGIP_Code_Map.put(254, "   ϵͳ����                 ");
	}
	
	/**
	 * @param errorCode����ͨ���صĴ�����
	 * @return �������Ӧ������
	 */
	public static String getDescription(int errorCode) {
		return SGIP_Code_Map.get(errorCode) != null
				? SGIP_Code_Map.get(errorCode) : "δ�ҵ�������"+errorCode+"��Ӧ������������ϵ��ͨ��Ա��ѯ";
	}
}
