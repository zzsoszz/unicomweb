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
//	 * 向队列添加任务
//	 * @param mtreq 短信下发请求
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
////					// 发送短信异常,重新放回发送队列
////					e.printStackTrace();
////				}
////			}
////		};
////		poolExecutor.execute(sendTask);
//	}
//	
//	
//}
