package com.bxtel.service.lt.zx;
import java.net.MalformedURLException;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.bxtel.service.lt.zx.OrderRelationUpdateNotifyRequest;
import com.bxtel.service.lt.zx.SyncNotifySPServiceServiceLocator;
import com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub;

import dinamica.util.DateHelper;

public class Test {
public static void main(String[] args) throws ServiceException, MalformedURLException {
	try {
		
		SyncNotifySPServiceServiceLocator service = new SyncNotifySPServiceServiceLocator();
		SyncNotifySPSoapBindingStub stub = new SyncNotifySPSoapBindingStub(
				new URL(
						"http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService"),
				service);
		OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
		orderRelationUpdateNotifyRequest.setContent("111");
		orderRelationUpdateNotifyRequest.setEffectiveDate("111");
		orderRelationUpdateNotifyRequest.setEncodeStr("111");
		orderRelationUpdateNotifyRequest.setExpireDate("1111");
		orderRelationUpdateNotifyRequest.setLinkId("111");
		orderRelationUpdateNotifyRequest.setProductId("111");
		orderRelationUpdateNotifyRequest.setRecordSequenceId("111");
		orderRelationUpdateNotifyRequest.setServiceType("111");
		orderRelationUpdateNotifyRequest.setSpId("111");
		orderRelationUpdateNotifyRequest.setTime_stamp("111");
		orderRelationUpdateNotifyRequest.setUpdateDesc("111");
		orderRelationUpdateNotifyRequest.setUpdateTime("111");
		orderRelationUpdateNotifyRequest.setUpdateType(1);
		orderRelationUpdateNotifyRequest.setUserId("111");
		orderRelationUpdateNotifyRequest.setUserIdType(1);
		stub.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
		 
		
	} catch (RemoteException e1) {
		 e1.printStackTrace();
	}
}
}
