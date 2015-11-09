package com.tdt.unicom.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.bxtel.service.lt.zx.OrderRelationUpdateNotifyRequest;
import com.bxtel.service.lt.zx.SyncNotifySPServiceServiceLocator;
import com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub;
import com.tdt.unicom.serverforsgip.BusinessConfig;

import dinamica.util.DateHelper;

public class Test {
public static void main(String[] args) throws ServiceException, MalformedURLException {
	try {
		 
		 SyncNotifySPServiceServiceLocator service = new SyncNotifySPServiceServiceLocator();
		 SyncNotifySPSoapBindingStub stub=new SyncNotifySPSoapBindingStub(new URL("http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService"),service);
		 //stub._getCall().setTargetEndpointAddress("");
		 OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest=new  OrderRelationUpdateNotifyRequest();
		 orderRelationUpdateNotifyRequest.setUpdateType(1);
		 orderRelationUpdateNotifyRequest.setContent("");
		 orderRelationUpdateNotifyRequest.setEffectiveDate(DateHelper.getDateString());
		 orderRelationUpdateNotifyRequest.setServiceType("111111");
		 orderRelationUpdateNotifyRequest.setSpId("111111");
		 orderRelationUpdateNotifyRequest.setLinkId("111111");
		 orderRelationUpdateNotifyRequest.setProductId("LTZX");
		 orderRelationUpdateNotifyRequest.setRecordSequenceId(DateHelper.getTime3String());
		 orderRelationUpdateNotifyRequest.setUserId("111111");
		 stub.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
		// service.getSyncNotifySP(new URL("http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService?wsdl")).orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
		 
		 
		 
	} catch (RemoteException e1) {
		 e1.printStackTrace();
	}
}
}
