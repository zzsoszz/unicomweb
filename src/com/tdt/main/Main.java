package com.tdt.main;

import java.io.IOException;
import com.tdt.unicom.serverforsgip.UnicomSPMonitor;

/**
 * @project UNICOM
 * @author sunnylocus
 * @vresion 1.0 2009-8-15
 * @description  系统启动类
 * 
 *  -agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:4815
 *  
 *  java.exe -verbose -Dfile.encoding=gbk -cp  -classpath D:\bxdev\trunck\UNICOM\bin;D:\bxdev\trunck\UNICOM\lib\*.jar com.tdt.main.Main
 *   
 *   
 *   
 *  /u01/tomcat-unicomweb/apache-tomcat-6.0.37/bin/catalina.sh jpda start
 *    
 */
public class Main  {
	
	public void lauchSystem()  {
		 new Thread(new launchUnicomSPMonitor()).start();
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.lauchSystem();
	}
	
	
	/*
	 * 服务端监听器，监听来自SMG的数据
	 */
	static class launchUnicomSPMonitor implements Runnable {
		public void run() {
			 try {
				new UnicomSPMonitor().startSvr();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
