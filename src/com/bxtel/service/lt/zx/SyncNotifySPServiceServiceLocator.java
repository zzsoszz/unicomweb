/**
 * SyncNotifySPServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bxtel.service.lt.zx;

public class SyncNotifySPServiceServiceLocator extends org.apache.axis.client.Service implements com.bxtel.service.lt.zx.SyncNotifySPServiceService {

    public SyncNotifySPServiceServiceLocator() {
    }


    public SyncNotifySPServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SyncNotifySPServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SyncNotifySP
    private java.lang.String SyncNotifySP_address = "http://119.254.84.182:9071/axiswebservice/services/SyncNotifySPServiceService";

    public java.lang.String getSyncNotifySPAddress() {
        return SyncNotifySP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SyncNotifySPWSDDServiceName = "SyncNotifySP";

    public java.lang.String getSyncNotifySPWSDDServiceName() {
        return SyncNotifySPWSDDServiceName;
    }

    public void setSyncNotifySPWSDDServiceName(java.lang.String name) {
        SyncNotifySPWSDDServiceName = name;
    }

    public com.bxtel.service.lt.zx.SyncNotifySPService getSyncNotifySP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SyncNotifySP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSyncNotifySP(endpoint);
    }

    public com.bxtel.service.lt.zx.SyncNotifySPService getSyncNotifySP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub _stub = new com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub(portAddress, this);
            _stub.setPortName(getSyncNotifySPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSyncNotifySPEndpointAddress(java.lang.String address) {
        SyncNotifySP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.bxtel.service.lt.zx.SyncNotifySPService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub _stub = new com.bxtel.service.lt.zx.SyncNotifySPSoapBindingStub(new java.net.URL(SyncNotifySP_address), this);
                _stub.setPortName(getSyncNotifySPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SyncNotifySP".equals(inputPortName)) {
            return getSyncNotifySP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.bossagent.vac.unicom.com", "SyncNotifySPServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soap.bossagent.vac.unicom.com", "SyncNotifySP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SyncNotifySP".equals(portName)) {
            setSyncNotifySPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
