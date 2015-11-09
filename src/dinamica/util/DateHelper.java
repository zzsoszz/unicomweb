package dinamica.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


import java.text.SimpleDateFormat;   
import java.util.ArrayList;   
import java.util.Calendar;
import java.util.Date;   
import java.util.GregorianCalendar;
import java.util.List;   
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DateHelper {   

	//http://zh.wikipedia.org/wiki/%E8%94%A1%E5%8B%92%E5%85%AC%E5%BC%8F
	public void getWeek(Date date)
	{
		//w=y+[y/4]+[c/4]-2c+[26(m+1)/10]+d-1;
	}
	
	public static Date getDate()
    {
		try {
			return getDate("yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }
	//EEE, dd MMM yyyy HH:mm:ss zzz
	public static Date getYesterday() throws ParseException
    {
		return addDay(getDate("yyyy-MM-dd"),-1);
    }
	
	public static Date getTime() 
    {
		try {
			return getDate("yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	
	public static String getTimeString() 
    {
		return getDateString("yyyy-MM-dd HH:mm:ss");
    }
	public static String getTime3String() 
    {
		return getDateString("yyyy-MM-dd HH:mm:ss.SSS");
    }
	
	public static Timestamp getTimestamp() throws ParseException
    {
		Timestamp ts = new Timestamp(getDate("yyyy-MM-dd HH:mm:ss").getTime());
		return ts;
    }
	
	public static Timestamp convertStringToTimestamp(String str) throws Exception
	{
		//"2011-05-09 11:49:45";
		Timestamp ts= new Timestamp(System.currentTimeMillis());
		try{
			ts = Timestamp.valueOf(str);
		}catch(Exception ex)
		{
			throw new Exception(ex.getMessage()+str);
		}
		return ts;
	}
	
	

	public static String convertTimestampToString(Timestamp str)
	{
		return convertTimestampToString(str,"yyyy-MM-dd HH:mm:ss");
	}
	
	public  static String convertTimestampToString(Timestamp str,String format)
	{
		//Timestamp ts = new Timestamp(System.currentTimeMillis());
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat(format);
		tsStr = sdf.format(str);
		//System.out.println(tsStr);
		return tsStr;
	}
	
	public  static Date convertTimestampToDate(Timestamp timestamp)
	{
		Date date = timestamp;
		return date;
	}
	
	public  static Timestamp convertDateToTimestamp(Date date)
	{
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}
	
	
	public static Date getDate(String format) throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat(format);   
        Date date =new Date(System.currentTimeMillis());
        return convertStringToDate(df.format(date),format);
    }
	
	
	
	public static String getDateString()
    {
        return getDateString("yyyy-MM-dd");
    }
	
	public static String getDateString(int after)
    {
        return addDay(getDateString("yyyy-MM-dd"),after);
    }
	
	
    public static String getDateString(String format)
    {
        SimpleDateFormat df = new SimpleDateFormat(format);   
        Date date =new Date(System.currentTimeMillis());
        String dateStr = df.format(date);   
        return dateStr;
    }
    
    
    public static String getDateStringWithNanos(String format)
    {
        SimpleDateFormat df = new SimpleDateFormat(format);   
        Date date =new Date(System.currentTimeMillis());
        //String dateStr = df.format(date);
        NumberFormat nf = new DecimalFormat("000000000");
        String dateStr = df.format(date.getTime()) +"."+ nf.format(System.nanoTime());
        return dateStr;
    }
    
//    
//    鏆傛椂涓嶇敤
//    public static String getDateStringWithNanos(String dateformat)
//    {
//    	final long timeUS = HiresTimer.currentTimeUS();
//        return HiresTimer.toString(dateformat,timeUS);
//    }
    
    
    public static String getNanos()
    {
        NumberFormat nf = new DecimalFormat("000000000");
        String dateStr =  nf.format(System.nanoTime());
        return dateStr;
    }
    
   
    
    public static void main(String args[]) throws ParseException
    {
//    	
//    	String insertSql="insert jiankongcollect (nowtime,collectdata) values('"+DateHelper.getDateString("yyyy-MM-dd HH:mm:ss")+"','"+"ddddd"+"')";
//    	//System.out.println(insertSql);
   // 	//System.out.println(getDateString("yyyy-MM-dd HH:mm:ss"));
   // 	;
    	String nString=addDay("2014-05-06",0);
    	Date aa=addHour(convertStringToDate("01:10","HH:mm"),3);
    	System.out.println(aa);
    	
    	for (int i=0;i<100000;i++)
    	{
    		System.out.println(getDateStringWithNanos("yyyy-mm-dd"));
    	}
    	//System.out.println(nString);
    }
  /**  
    * 瀛楃涓茶浆鏃ユ湡绫诲瀷  
    *   
    * @author HeCheng  
    * @time 2010-12-08 18:31:46  
    * @return  
 * @throws ParseException 
    */  
   public static Date convertStringToDate(String time, String format) throws ParseException {   
	   if (format == null) {   
           format = "yyyy-MM-dd";   
       }
       SimpleDateFormat sdf = new SimpleDateFormat(format);   
       return sdf.parse(time);   
   }
   public static String convertDateToString(Date date, String format) {   
       if (format == null) {   
           format = "yyyy-MM-dd";   
       }
       SimpleDateFormat sdf = new SimpleDateFormat(format);   
       try {   
           return sdf.format(date);  
       } catch (Exception e) {   
           return null;   
       }
   } 
   
   /**  
    * 鍙栬繎涓�骞�  
    *   
    * @author HeCheng  
    * @time 2010-12-08 21:27:41  
    * @return  
    */  
   public static String getLastOneYearDay() {   
       return getLastNumberDayBeforeYesterDay(365);   
   }   
      
   /**  
    * 鍙栬繎涓変釜鏈�  
    *   
    * @author HeCheng  
    * @time 2010-12-08 21:27:41  
    * @return  
    */  
   public static String getLastThreeMonthDay() {   
       return getLastNumberDayBeforeYesterDay(90);   
   }   
      
   /**  
    * 鍙栬繎涓�涓湀  
    *   
    * @author HeCheng  
    * @time 2010-12-08 21:27:41  
    * @return  
    */  
   public static String getLastMonthDay() {   
       return getLastNumberDayBeforeYesterDay(30);   
   }   
      
   /**  
    * 鍙栬繎涓�鍛�  
    *   
    * @author HeCheng  
    * @time 2010-12-08 21:27:41  
    * @return  
    */  
   public static String getLastWeekDay() {   
       return getLastNumberDayBeforeYesterDay(7);   
   }   
 
   /**  
    * 鍙栨槰澶╃殑鍓峏X澶�  
    *   
    * @author HeCheng  
    * @time 2010-12-08 21:06:08  
    * @param number  
    * @return  
    */  
   public static String getLastNumberDayBeforeYesterDay(int number) {   
       String yesterDay = getYesterdayOrTomorrow(getNowDate(), -1);   
       return getLastNumberDay(yesterDay, number);   
   }   
 
   /**  
    * 鍙栦箣鍓峏X澶�  
    *   
    * @author HeCheng  
    * @time 2010-12-08 21:06:08  
    */  
   @SuppressWarnings("deprecation")   
   public static String getLastNumberDay(String day, int number) {   
       String ntime = "";   
       try {   
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
           Date startDate = df.parse(day);   
           Date endDate = null;   
           endDate = new Date(startDate.getTime()   
                    - (((long) number * (long) 24 * (long) 3600 * (long) 1000)));   
            ntime = endDate.getYear() + 1900 + "-" + (endDate.getMonth() + 1)   
                    + "-" + endDate.getDate();   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return ntime;   
    }   
  
    /**  
     * 鍙栦笂涓湀鐨勬槰澶�  
     *   
     * @author HeCheng  
     * @time 2010-12-08 18:41:14  
     * @param yesterday  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getYesterdayOnLastMonth(String yesterday) {   
        String ntime = "";   
        try {   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
            Date startDate = df.parse(yesterday);   
            Date endDate = null;   
            endDate = new Date(startDate.getTime()   
                    - (((long) 30 * (long) 24 * (long) 3600 * (long) 1000)));   
            ntime = endDate.getYear() + 1900 + "-" + (endDate.getMonth() + 1)   
                    + "-" + endDate.getDate();   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return ntime;   
    }   
  
    /**  
     * 鍙栦粖骞寸涓�澶�  
     *   
     * @author HeCheng  
     * @time 2010-12-08 18:44:12  
     * @param nowDate  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getFirstDayInYear(String nowDate) {   
        String ntime = "";   
        try {   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
            Date startDate = df.parse(nowDate);   
            ntime = startDate.getYear() + 1900 + "-01-01";   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return ntime;   
    }   
  
    /**  
     * 鍙栨湰瀛ｅ害绗竴澶�  
     *   
     * @author HeCheng  
     * @time 2010-12-08 18:46:33  
     * @param nowDate  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getFirstDayInThisQuarter(String nowDate) {   
        String ntime = "";   
        int nowMonth;   
        int nowYear;   
        try {   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
            Date startDate = df.parse(nowDate);   
            nowMonth = startDate.getMonth() + 1;   
            nowYear = startDate.getYear() + 1900;   
            while (true) {   
                if (nowMonth >= 10) {   
                    ntime = nowYear + "-10-01";   
                    break;   
                }   
                if (nowMonth >= 7) {   
                    ntime = nowYear + "-07-01";   
                    break;   
                }   
                if (nowMonth >= 4) {   
                    ntime = nowYear + "-04-01";   
                    break;   
                }   
                if (nowMonth >= 1) {   
                    ntime = nowYear + "-01-01";   
                    break;   
                }   
            }   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return ntime;   
    }   
  
    /**  
     * 鍙栨槰澶�  
     *   
     * @author HeCheng  
     * @time 2010-12-08 20:27:21  
     * @return  
     */  
    public static String getYesterDay() {   
        return getYesterdayOrTomorrow(getNowDate(), -1);   
    }   
  
    /**  
     * 鍙栨槑澶�  
     *   
     * @author HeCheng  
     * @time 2010-12-08 20:27:21  
     * @return  
     */  
    public static String getTomorrow() {   
        return getYesterdayOrTomorrow(getNowDate(), 1);   
    }   
  
    /**  
     * 鍙栦粖澶�  
     *   
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getNowDate() {   
        Date date = new Date();   
        int nowMonth = date.getMonth() + 1;   
        int nowYear = date.getYear() + 1900;   
        int day = date.getDate();   
        String startTime = nowYear + "-" + nowMonth + "-" + day;   
        return startTime;   
    }   
  
    /**  
     * 鍙栨椂闂存  
     *   
     * @author HeCheng  
     * @time 2010-12-08 18:34:22  
     * @param startTime  
     * @param endTime  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static List<String> getTimes(String startTime, String endTime) {   
        List<String> dayList = new ArrayList<String>();   
        try {   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
            Date startDate = df.parse(startTime);   
            Date endDate = df.parse(endTime);   
            String now = "";   
            for (long i = startDate.getTime(); i <= endDate.getTime(); i += (long) 24  
                    * (long) 3600 * (long) 1000) {   
                Date date = new Date(i);   
                now = date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-"  
                        + date.getDate();   
                dayList.add(now);   
            }   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return dayList;   
    }   
  
    /**  
     * 鍙栧綋鍓嶆湀绗竴澶�  
     *   
     * @author HeCheng  
     * @time 2010-12-08 18:34:29  
     *   
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getMonthFirstDay() {   
        Date date = new Date();   
        int nowMonth = date.getMonth() + 1;   
        int nowYear = date.getYear() + 1900;   
        String startTime = nowYear + "-" + nowMonth + "-1";   
        return startTime;   
    }   
  
    /**  
     * 鍙栨槑澶╂垨鏄ㄥぉ  
     *   
     * @param nowDate  
     * @param con  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getYesterdayOrTomorrow(String nowDate, int con) {   
        String ntime = "";   
        try {   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
            Date startDate = df.parse(nowDate);   
            Date endDate = null;   
            if (con == -1) {   
                endDate = new Date(startDate.getTime() - (long) 24  
                        * (long) 3600 * (long) 1000);   
            } else {   
                endDate = new Date(startDate.getTime() + (long) 24  
                        * (long) 3600 * (long) 1000);   
            }   
            ntime = endDate.getYear() + 1900 + "-" + (endDate.getMonth() + 1)   
                    + "-" + endDate.getDate();   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return ntime;   
    }   
  
    /**  
     * 鍙栨湀鐨勬渶鍚庝竴澶�  
     *   
     * @param time  
     * @return  
     */  
    @SuppressWarnings("deprecation")   
    public static String getMonthEndDay(String time) {   
        String ntime = "";   
        try {   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
            Date startDate = df.parse(time);   
            int nowMonth = startDate.getMonth() + 1;   
            int nextMonth = nowMonth + 1;   
            int nowYear = startDate.getYear() + 1900;   
            String nextTime = nowYear + "-" + nextMonth + "-1";   
            Date tmpDate = df.parse(nextTime);   
            Date endDate = new Date(tmpDate.getTime() - 24 * 3600 * 1000);   
            ntime = endDate.getYear() + 1900 + "-" + (endDate.getMonth() + 1)   
                    + "-" + endDate.getDate();   
        } catch (Exception e) {   
            //System.out.println(e);   
        }   
        return ntime;   
    }
    
    
	public static JdbcTemplate getJdbcTemplate(String datasource) {
		 	Context initContext;
		 	JdbcTemplate jdbcTemplate = null;
		 	DataSource ds = null;
			try {
					initContext = new InitialContext();
				    Context envContext = (Context)initContext.lookup("java:/comp/env");
				    if (datasource == null)
				    {
				        ds = (DataSource) envContext.lookup("jdbc/jk");
				    }
				    else
				    {
				        ds = (DataSource) envContext.lookup(datasource);
				    }
				    jdbcTemplate=new JdbcTemplate(ds);
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return jdbcTemplate;
	}
	
	
	public static JdbcTemplate getJdbcTemplate() {
		return getJdbcTemplate("jdbc/jk");
	}

	
	public static String getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);

			// //System.out.println("鐩搁殧鐨勫ぉ鏁�="+day);
		} catch (ParseException e) {
			// TODO 鑷姩鐢熸垚 catch 鍧�
			e.printStackTrace();
		}
		return new Long(day).toString();
	}
	
	public static long getSubDay(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			// TODO 鑷姩鐢熸垚 catch 鍧�
			e.printStackTrace();
		}
		return day;
	}
	
	
	
	public static void handleEveryDay(String beginDateStr, String endDateStr,DealHandler handler) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			
			day = (endDate.getTime() - beginDate.getTime())/ (24 * 60 * 60 * 1000);
			for(int i=0;i<day;i++)
			{
				//addDay(beginDateStr, 1)
				handler.doAny(addDay(beginDate, i));
			}
		} catch (Exception e) {
			// TODO 鑷姩鐢熸垚 catch 鍧�
			e.printStackTrace();
		}
	}
	
	
	public static String getHourSub(String beginDateStr, String endDateStr) {
		long hour = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date beginDate;
		java.util.Date endDate;
		try{
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			hour = (endDate.getTime() - beginDate.getTime()) / ( 60 * 60 * 1000);
			// //System.out.println("鐩搁殧鐨勫ぉ鏁�="+day);
		} catch (ParseException e) {
			// TODO 鑷姩鐢熸垚 catch 鍧�
			e.printStackTrace();
		}
		return new Long(hour).toString();
	}
	
	
	public static String getHourSub(Date beginDate, Date endDate) {
		long hour = 0;
		hour = (endDate.getTime() - beginDate.getTime()) / ( 60 * 60 * 1000);
		return new Long(hour).toString();
	}
	
	public static long getSubDay(Date beginDate, Date endDate) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		day = (endDate.getTime() - beginDate.getTime())
				/ (24 * 60 * 60 * 1000);
		return new Long(day);
	}
	
	public static String getDaySub(Date beginDate, Date endDate) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		day = (endDate.getTime() - beginDate.getTime())
				/ (24 * 60 * 60 * 1000);
		return new Long(day).toString();
	}
	
	
	public static ArrayList<String> getDayListBySub(String beginDateStr, String endDateStr) {
		ArrayList<String> list=new ArrayList<String>();
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())/ (24 * 60 * 60 * 1000);
			// //System.out.println("鐩搁殧鐨勫ぉ鏁�="+day);
			for(int i=0;i<=day;i++)
			{
				String newdate=addDay(beginDateStr,i);
				list.add(newdate);
			}
		} catch (ParseException e) {
			// TODO 鑷姩鐢熸垚 catch 鍧�
			e.printStackTrace();
		}
		return list;
	}
	
	public static String addDay(String beginDateStr, int adddaycount) {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			Date  beginDate= format.parse(beginDateStr);
			Calendar cal=Calendar.getInstance();  
			cal.setTime(beginDate);
			cal.add(Calendar.DATE, adddaycount);
			Date enddate=cal.getTime();
			return format.format(enddate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String addDayByDate(String beginDateStr, int adddaycount){
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate;
		try {
			beginDate = format.parse(beginDateStr);
			Calendar cal=Calendar.getInstance();  
			cal.setTime(beginDate);
			cal.add(Calendar.DATE, adddaycount);
			Date enddate=cal.getTime();
			return format.format(enddate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static String addHour(String beginDateStr, int adddaycount) {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			Date  beginDate= format.parse(beginDateStr);
			Calendar cal=Calendar.getInstance();  
			cal.setTime(beginDate);
			cal.add(Calendar.HOUR_OF_DAY, adddaycount);
			Date enddate=cal.getTime();
			return format.format(enddate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static Date addHour(Date beginDate, int count) {
		Calendar cal=Calendar.getInstance();  
		cal.setTime(beginDate);
		cal.add(Calendar.HOUR_OF_DAY, count);
		Date enddate=cal.getTime();
		return enddate;
	}
	
	
	
	
	public static String addMonth(String beginDateStr, int addcount) {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			Date  beginDate= format.parse(beginDateStr);
			Calendar cal=Calendar.getInstance();  
			cal.setTime(beginDate);
			cal.add(Calendar.MONTH, addcount);
			Date enddate=cal.getTime();
			return format.format(enddate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public static Date addDay(Date beginDate, int addcount) {
		Calendar cal=Calendar.getInstance();  
		cal.setTime(beginDate);
		cal.add(Calendar.DAY_OF_YEAR, addcount);
		Date enddate=cal.getTime();
		return enddate;
	}
	
	public static Date addSecond(Date beginDate, int addcount) {
			Calendar cal=Calendar.getInstance();  
			cal.setTime(beginDate);
			cal.add(Calendar.SECOND, addcount);
			Date enddate=cal.getTime();
			return enddate;
	}
	
	public static String addYEAR(String beginDateStr, int addcount) {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			Date  beginDate= format.parse(beginDateStr);
			Calendar cal=Calendar.getInstance();  
			cal.setTime(beginDate);
			cal.add(Calendar.YEAR, addcount);
			Date enddate=cal.getTime();
			return format.format(enddate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getWeekOfDate(Date date) { 
		String[] weekDaysName = { "鏃�", "涓�", "浜�", "涓�", "鍥�", "浜�", "鍏�" }; 
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" }; 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); 
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
		return weekDaysName[intWeek];
	} 

	/**
	 * 鎶婃棩鏈熸牸寮忕殑瀛楃涓茶浆鎹负Date绫诲瀷
	 * @author 寤栧簡
	 * @param strDate 浼犲叆yyyy-MM-dd鏍煎紡鐨勫瓧绗︿覆
	 * @return 杩斿洖杞崲鍚庣殑鏃ユ湡
	 * @throws ParseException 
	 */
	public static Date StringToDate(String strDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(strDate);
		return new java.sql.Date(date.getTime());
		
	}
	public static Date StringToTime(String strDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse(strDate);
		return new java.sql.Date(date.getTime());
		
	}

	/**
	 * 鎶婄鏍煎紡杞寲涓烘棩鏈熷瓧绗︿覆
	 * @author 寮犳檽涓�
	 * @param str 浼犲叆绉�
	 * @return 杩斿洖杞寲鍚庣殑鏃ユ湡瀛楃涓� 
	 */
	public static String millisecondToDate(String str){
		long ms=Long.parseLong(str);
		Date dat=new Date(ms*1000);
//		//System.out.println(dat);
		GregorianCalendar gc = new GregorianCalendar();   
        gc.setTime(dat);  
//        //System.out.println(gc.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String sb=format.format(gc.getTime());  
		return sb;
	}
	
	public static Date StringToDate1(String strDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(strDate);
		return new java.util.Date(date.getTime());
		
	}
	  public static int compare_date(Date dt1, Date dt2) {
	         
	        
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         try {
//	             Date dt1 = df.parse(DATE1);
//	             Date dt2 = df.parse(DATE2);
	             if (dt1.getTime() > dt2.getTime()) {
	   //              System.out.println("dt1 鍦╠t2鍓�");
	                 return 1;
	             } else if (dt1.getTime() < dt2.getTime()) {
	    //             System.out.println("dt1鍦╠t2鍚�");
	                 return -1;
	             } else {
	                 return 0;
	             }
	         } catch (Exception exception) {
	             exception.printStackTrace();
	         }
	         return 0;
	     }
}  