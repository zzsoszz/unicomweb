package com.tdt.unicom.serverforsgip;

import java.util.HashMap;

public class BusinessConfig {
	
	/*
	 * 订购代码，业务信息
	 * 
	 *  
	 *  UserNumber:[Ljava.lang.String;@5323961b
		SPNumber:10655572201
		ChargeNumber:000000000000000000000
		corpId:93433
		serviceType:FLBQX
		feeValue:1000
		givenValue:0
		expireTime:
		scheduleTime:
		messageContent:恭喜您，订购成功!
		linkID:91683782
	 */
	
	public static HashMap<String,BusinessInfo> msg2BusMao=new HashMap<String,BusinessInfo>();
	static{
//		msg2BusMao.put("FBD", new BusinessInfo("FLBDB", "FBD", "2", "200"));
		msg2BusMao.put("FLQ", new BusinessInfo("FLBQX", "FLQ", "3", "1000"));
//		msg2BusMao.put("FLW", new BusinessInfo("FLBWG", "FLW", "3", "300"));
//		msg2BusMao.put("FLT", new BusinessInfo("FLBTG", "FLT", "3", "300"));
//		msg2BusMao.put("FLJ", new BusinessInfo("FLBJD", "FLJ", "3", "300"));
//		msg2BusMao.put("FLP", new BusinessInfo("FLBJP", "FLP", "3", "300"));
//		msg2BusMao.put("FLC", new BusinessInfo("FLBCY", "FLC", "3", "300"));
//		msg2BusMao.put("TD"+"FBD", new BusinessInfo("FLBDB", "FBD", "2", "200"));
		msg2BusMao.put("TD"+"FLQ", new BusinessInfo("FLBQX", "FLQ", "3", "1000"));
//		msg2BusMao.put("TD"+"FLW", new BusinessInfo("FLBWG", "FLW", "3", "300"));
//		msg2BusMao.put("TD"+"FLT", new BusinessInfo("FLBTG", "FLT", "3", "300"));
//		msg2BusMao.put("TD"+"FLJ", new BusinessInfo("FLBJD", "FLJ", "3", "300"));
//		msg2BusMao.put("TD"+"FLP", new BusinessInfo("FLBJP", "FLP", "3", "300"));
//		msg2BusMao.put("TD"+"FLC", new BusinessInfo("FLBCY", "FLC", "3", "300"));
	}
	
	public static HashMap<String,BusinessInfo> servicetype2BusMao=new HashMap<String,BusinessInfo>();
	static{
		servicetype2BusMao.put("FLBDB", new BusinessInfo("FLBDB", "FBD", "2", "200"));
		servicetype2BusMao.put("FLBQX", new BusinessInfo("FLBQX", "FLQ", "3", "1000"));
		servicetype2BusMao.put("FLBWG", new BusinessInfo("FLBWG", "FLW", "3", "300"));
		servicetype2BusMao.put("FLBTG", new BusinessInfo("FLBTG", "FLT", "3", "300"));
		servicetype2BusMao.put("FLBJD", new BusinessInfo("FLBJD", "FLJ", "3", "300"));
		servicetype2BusMao.put("FLBJP", new BusinessInfo("FLBJP", "FLP", "3", "300"));
		servicetype2BusMao.put("FLBCY", new BusinessInfo("FLBCY", "FLC", "3", "300"));
	}
	
}
