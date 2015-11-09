package com.bxtel.system;
import javax.servlet.http.HttpServlet;

/*
 * Eclipse  Tomcat
 * http://blog.csdn.net/jarfield/article/details/5250915
 * /u01/tomcat-portal/apache-tomcat-6.0.37-two/bin/catalina.sh jpda start
 * 
 * /u01/tomcat-unicomweb/apache-tomcat-6.0.37/bin/catalina.sh   jpda start
 * 
 */

import com.tdt.main.Main;


public class SystemInitServlet extends HttpServlet{
	
	public void init() {
		Main main = new Main();
		main.lauchSystem();
	}
	
	public void destroy() {
		
	}
}