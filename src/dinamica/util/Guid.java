package dinamica.util;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Random;

//import org.apache.commons.id.LongIdentifierGenerator;
//import org.apache.commons.id.random.SessionIdGenerator;
//import org.apache.commons.id.serial.AlphanumericGenerator;
//import org.apache.commons.id.serial.LongGenerator;

import dinamica.util.DateHelper;

public class Guid {


	public static void main(String[] args) throws Exception {
//	    // 产生 5 �? GUID
//	    for (int i = 0; i < 5; i++)
//	    {
//
//	      // 创建 GUID 对象
//	      UUID uuid = UUID.randomUUID();
//
//	      // 得到对象产生的ID
//	      String a = uuid.toString();
//
//	      // 转换为大�?
//
//	      a = a.toUpperCase();
//
//	      // 替换 -
//	       a = a.replaceAll("-", "");
//	      //System.out.println(a);
//	    }
		
		
		
//		IdWorker iw1 = new IdWorker(1);
//        IdWorker iw2 = new IdWorker(2);
//        IdWorker iw3 = new IdWorker(3);
//
//        System.out.println("---------------");
//        for (int i = 0; i < 1000000; i++) {
//            System.out.println(iw1.nextId());
//            System.out.println(iw2.nextId());
//        }
      //  getUniqueId2();
		
		
		for (int i = 0; i < 1000000; i++) {
			System.out.println(Guid.getUniqueIdWithNaos());
		}
		//System.out.println(Guid.getUniqueId());
	}

	 
//	private static long tmpID = 0;
//
//	private static boolean tmpIDlocked = false;
//
//	private static long getUniqueId() {
//		long ltime = 0;
//		while (true) {
//			if (tmpIDlocked == false) {
//				tmpIDlocked = true;
//				ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
//				if (tmpID < ltime) {
//					tmpID = ltime;
//				} else {
//					tmpID = tmpID + 1;
//					ltime = tmpID;
//				}
//				tmpIDlocked = false;
//				return ltime;
//			}
//		}
//	}

	
	
	
	public static String getUniqueId() 
	{
		IdWorker iw1 = new IdWorker(1);
		try {
			return new Long(iw1.nextId()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
//	public static Long getUniqueId2() 
//	{
//        LongIdentifierGenerator f = new LongGenerator(true, 0);
//        System.out.println(f.nextLongIdentifier());
//        System.out.println(f.nextIdentifier());
//        
//        AlphanumericGenerator aaa = new  AlphanumericGenerator(true);
//        System.out.println(aaa.nextStringIdentifier());;
//        
//        SessionIdGenerator bbb = new  SessionIdGenerator();
//        System.out.println(bbb.nextStringIdentifier());;
//        return f.nextLongIdentifier();
//	}
	
	
	
	//不�?�合分布�?
	public static String getUniqueIdOld() 
	{
		double i=Math.random();//
		double i2=i*10000000;
		int rad=(int)i2;
		String timestamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		String guid=timestamp+rad;
		////System.out.println(guid);
		return guid;
	}
	
	/*
	 * 
	 */
	public static String getUniqueIdWithNaos() 
	{
		String timestamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
		String guid=timestamp+DateHelper.getNanos();
		return guid;
	}
	
	
	public  static String  genRandom(int num)
	{
		StringBuffer result=new StringBuffer("");
		Random rd = new Random();
		for(int i=0;i<num;i++)
		{
			result.append(rd.nextInt(10) + "");
		}
		return result.toString();
	}
	

}
