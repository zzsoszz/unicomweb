//package com.tdt.unicom.serverforsgip;
//
//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import com.tdt.unicom.serverforsgip.dao.MTReq;
//public class SenderManager {
//	static private ExecutorService poolExecutor = Executors.newSingleThreadExecutor();
//	
//	/**
//	 * ������������
//	 * @param mtreq �����·�����
//	 */
//	public static void addTask(final MTReq mtreq) {
//		
//		try {
//		  	new SPSender().sendMTReq(mtreq);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
////		 final Runnable sendTask = new Runnable() {
////			public void run() {
////				try {
////					new SPSender().sendMTReq(mtreq);
////				} catch (Exception e) {
////					// ���Ͷ����쳣,���·Żط��Ͷ���
////					e.printStackTrace();
////				}
////			}
////		};
////		poolExecutor.execute(sendTask);
//	}
//	
//	
//}
