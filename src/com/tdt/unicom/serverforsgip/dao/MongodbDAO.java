package com.tdt.unicom.serverforsgip.dao;

import static com.mongodb.client.model.Filters.lt;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;

import org.bson.Document;

import com.bxtel.service.lt.zx.OrderRelationUpdateNotifyRequest;
import com.bxtel.service.lt.zx.SyncNotifySPServiceServiceLocator;
import com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.tdt.unicom.serverforsgip.BusinessConfig;

import dinamica.db.BeanUtil;
import dinamica.db.MongoHelper;
import dinamica.util.DateHelper;
import dinamica.util.Guid;
import dinamica.util.ReqHandler;

/*
 * http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/quick-tour/
 */
public class MongodbDAO {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException {
		test3();
	}
	
	
	public static void test3() throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException
	{
		MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("submitreq");		
		FindIterable<Document> ft = collection.find(
				com.mongodb.client.model.Filters.and(
//						lt("createdate",DateHelper.addDay(DateHelper.getDate(),-3)),
//						com.mongodb.client.model.Filters.eq("chargeType", "0"),
//						com.mongodb.client.model.Filters.eq("dealflag", "1"),
						com.mongodb.client.model.Filters.where("function() {  if(new Date().getDate()>10 && new Date().getDate()<23) return true;  }")
				)
		);
		
		for (Document cur : ft) {
		    System.out.println(cur);
		}
	}
	
	public static void test2() throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException
	{
		MTReq req=new MTReq();
		req.setLinkId("linkid");
		req.setMobile("13730666347");
		req.setCreatedate(DateHelper.getTime());
		req.setServiceType("FLBDB");
		req.setMessageContent("FBD");
		req.setSpNumber("11111");
		req.setChargeType("1");
		req.setDealflag("0");
		insertMTReq(req);
		//queryReq(null);
		//updateReqForCancel("18615751325", "FLBQX");
	}
	
	
	public static void insertReport(ReportModel req) throws IllegalArgumentException, IllegalAccessException
	{
		MongoCollection<Document> collection=null;
		collection = MongoHelper.getDatabase().getCollection("report");
		collection.insertOne(BeanUtil.bean2Document(req));
	}
	
	
	public static void insertMTReq(MTReq req) throws IllegalArgumentException, IllegalAccessException
	{
		MongoCollection<Document> collection=null;
		collection = MongoHelper.getDatabase().getCollection("submitreq");
		collection.insertOne(BeanUtil.bean2Document(req));
	}
	
//当前日期-createdate>3 
//并且当前是10-23号，才可以计费
//	
//	db.getCollection('submitreq').find(
//		    {
//		        "createdate":{$lt:ISODate("2015-08-18T00:00:00.000Z")  },
//		        "chargeType":"0",
//		        "dealflag":"1",
//		        "$where":function() {  if(new Date().getDate()>10 && new Date().getDate()<23) return true;  }
//		    }
//		)
	public static void dealJFReq(ReqHandler handler) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException
	{
		
		//根据已经成功的订购消息下发计费消息
		MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("submitreq");		
		FindIterable<Document> ft = collection.find(
				com.mongodb.client.model.Filters.and(
						lt("createdate",DateHelper.addDay(DateHelper.getDate(),-3)),
						com.mongodb.client.model.Filters.eq("chargeType", "0"),
						com.mongodb.client.model.Filters.eq("dealflag", "1"),
						com.mongodb.client.model.Filters.where("function() {  if(new Date().getDate()>10 && new Date().getDate()<20) return true;  }")
				)
		);
		
		for (Document cur : ft) {
		    System.out.println(cur);
		    MTReq jfreq = BeanUtil.document2Bean(cur,new MTReq());
		    if(handler!=null)
		    {
		    		 try {
		    			
		    			jfreq.setChargeType("1");//计费
			    	    jfreq.setReportFlag("3");
					    jfreq.setMorelatetoMTFlag("3");
					    jfreq.setChargeNumber(cur.getString("mobile"));
					     
		    			//send jfreq and get sequence
						handler.doAny(jfreq);
						
						//更新订购请求
						Document updatedoc= new Document("dealflag", "3");
			    		collection.updateOne(
						    		com.mongodb.client.model.Filters.and(
						    				com.mongodb.client.model.Filters.eq("sequenceNumber", cur.get("sequenceNumber"))
						    		)
				    				, new Document("$set",updatedoc )
				    	);
			    		//插入计费请求
			    		collection.insertOne(BeanUtil.bean2Document(jfreq));
			    		
		    		}
		    		catch(Exception e) {
						e.printStackTrace();
						Document updatedoc= new Document("dealflag", "2");
						updatedoc.append("respcode","");
						updatedoc.append("respmsg",e.getMessage());
			    		collection.updateOne(
						    		com.mongodb.client.model.Filters.and(
						    				com.mongodb.client.model.Filters.eq("sequenceNumber", cur.get("sequenceNumber"))
						    		)
				    				, new Document("$set",updatedoc )
				    	);
					}
		    		 //更新订购消息处理标志
		    		
		    		
		    		
		    }
		}
	}
	
	
	public static void updateMTReqStatus(String seq,String status) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException
	{
		
		System.out.println("updateSubmitStatus start");
		MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("submitreq");
		FindIterable<Document> ft = collection.find(
				com.mongodb.client.model.Filters.and(
						com.mongodb.client.model.Filters.eq("sequenceNumber", seq)
				)
		);
		for (Document cur : ft) {
				MTReq req = BeanUtil.document2Bean(cur, new MTReq());
				if(req.getDealflag().equals("0"))  // 0.未处理 
				{
					try{
							
							Document updatedoc=null;
							System.out.println("chargetype:"+req.getChargeType());
							if("1".equals(req.getChargeType())) 
							{
								
								//计费请求上报处理
								updatedoc= new Document("dealflag","status:"+status);
								
							}else if("0".equals(req.getChargeType())){
								
								//订购请求上报处理，订购成功
								if("100".equals(status))
								{
									updatedoc= new Document("dealflag", "1");
								    String date=DateHelper.getDateString("yyyyMMddHHmmss");
									SyncNotifySPServiceServiceLocator service = new SyncNotifySPServiceServiceLocator();
									SyncNotifySPSoapBindingStub stub = new SyncNotifySPSoapBindingStub(new URL("http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService"),service);
									OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
									orderRelationUpdateNotifyRequest.setContent(req.getMessageContent());
									orderRelationUpdateNotifyRequest.setEffectiveDate(date);
									orderRelationUpdateNotifyRequest.setEncodeStr("LTZX");
									orderRelationUpdateNotifyRequest.setExpireDate("20351231000000");
									orderRelationUpdateNotifyRequest.setLinkId(req.getLinkId());
									orderRelationUpdateNotifyRequest.setProductId("LTZX");
									orderRelationUpdateNotifyRequest.setRecordSequenceId(DateHelper.getDateString("yyyyMMddHHmmss")+Guid.genRandom(4));
									orderRelationUpdateNotifyRequest.setServiceType(req.getServiceType());
									orderRelationUpdateNotifyRequest.setSpId(req.getSpNumber());
									orderRelationUpdateNotifyRequest.setTime_stamp(date);
									orderRelationUpdateNotifyRequest.setUpdateDesc("LTZX");
									orderRelationUpdateNotifyRequest.setUpdateTime(date);
									orderRelationUpdateNotifyRequest.setUserId("86"+req.getMobile());
									orderRelationUpdateNotifyRequest.setUserIdType(1);
									orderRelationUpdateNotifyRequest.setUpdateType(1);//订购
									stub.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
								}else 
								{
									throw new Exception("confirm msg report error:"+status);
								}
								
							}
							
							
							collection.updateOne(
									com.mongodb.client.model.Filters.and(
												com.mongodb.client.model.Filters.eq("sequenceNumber", seq)
									)
									,new Document("$set",updatedoc )
							);
						    
							
					}catch(Exception ex)
					{
							Document updatedoc= new Document("dealflag", "2");
							updatedoc.append("respcode",status);
							updatedoc.append("respmsg",ex.getMessage());
							collection.updateOne(
								com.mongodb.client.model.Filters.and(
											com.mongodb.client.model.Filters.eq("sequenceNumber", seq)
								)
								,new Document("$set",updatedoc )
							);
					}
				}
				
		}
		System.out.println("updateSubmitStatus end");
	}
	
	
	/*
	 *没有计费的执行取消
	 */
	public static void updateReqForCancel(String mobile,String servicetype) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException
	{
		
		MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("submitreq");		
		FindIterable<Document> ft = collection.find(
				com.mongodb.client.model.Filters.and(
						lt("createdate",DateHelper.addDay(DateHelper.getDate(),-3)),
						com.mongodb.client.model.Filters.eq("dealflag", "0"),
						com.mongodb.client.model.Filters.eq("chargeType", "0")
				)
		);
		for (Document cur : ft) {
		    System.out.println(cur);
		    collection.updateMany(
			    		com.mongodb.client.model.Filters.and(
			    				com.mongodb.client.model.Filters.eq("mobile", cur.get("mobile")),
			    				com.mongodb.client.model.Filters.eq("serviceType", cur.get("serviceType"))
			    		)
	    				, new Document("$set", new Document("dealflag", "2"))
	    	);
		}
	}
	
	
	//reqresult.setDealflag("1");
	//collection.deleteOne(cur);
//			    		 
//			    		 updatedoc.append("chargeType", req.getChargeType());
//			    		 updatedoc.append("reportFlag", req.getReportFlag());
//			    		 updatedoc.append("morelatetoMTFlag", req.getMorelatetoMTFlag());
//			    		 updatedoc.append("chargeNumber", req.getChargeNumber());
//			    		 updatedoc.append("sequenceNumber",req.getSequenceNumber());
//			    		 
			    		  
//	
//	
//	public static void insertForSubmitReq(MTReq req) throws IllegalArgumentException, IllegalAccessException
//	{
//		MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("submitreq");
//		collection.insertOne(BeanUtil.bean2Document(req));
//	}
//	public static void queryReqForSubmitReq(ReqHandler handler) throws IllegalArgumentException, IllegalAccessException, ParseException, InvocationTargetException, NoSuchMethodException
//	{
//		MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("submitreq");		
//		FindIterable<Document> ft = collection.find(lt("createdate",DateHelper.addDay(DateHelper.getDate(),-3)));
//		for (Document cur : ft) {
//		    System.out.println(cur);
//		    MTReq req=new MTReq();
//		    MTReq reqresult = BeanUtil.document2Bean(cur,req);
//		    if(handler!=null)
//		    {
//		    		 handler.doAny(reqresult);
//		    }
//		}
//	}
	
}
