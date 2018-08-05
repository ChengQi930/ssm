package cn.bizowner.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateCalculate {
	
	//获得当前日期与本周一相差的天数  
    public static int getMondayPlus(Calendar cCurrent)  
    {  
        int dayOfWeek = cCurrent.get(Calendar.DAY_OF_WEEK);  
        if (dayOfWeek == 1)   
        {  
            return -6;  
        }  
        else {  
            return 2 - dayOfWeek;  
        }  
    }  
    // 获得本周星期一的日期  
    public static Calendar getCurrentMonday(Calendar cCurrent)   
    {  
     Calendar cMonday = Calendar.getInstance();  
        int mondayPlus = getMondayPlus(cCurrent);  
        cMonday.set(cCurrent.get(Calendar.YEAR),  
          cCurrent.get(Calendar.MONTH),  
          cCurrent.get(Calendar.DAY_OF_MONTH));          
        cMonday.add(Calendar.DAY_OF_MONTH, mondayPlus);          
        return cMonday;  
    }  
    //获得本周星期日的日期  
    public static Calendar getCurrentSunday(Calendar cMonday)   
    {  
     Calendar cSunday = Calendar.getInstance();  
     cSunday.set(cMonday.get(Calendar.YEAR),  
       cMonday.get(Calendar.MONTH),  
       cMonday.get(Calendar.DAY_OF_MONTH));      
     cSunday.add(Calendar.DAY_OF_MONTH, 6);//获取周日日期 周一+6天  
        return cSunday;  
    }      
    // 获得本月第一天的日期  
    public static Calendar getFirstDayofMonth(Calendar cCurrent)   
    {  
     Calendar cFirstDay = Calendar.getInstance();  
     cFirstDay.set(cCurrent.get(Calendar.YEAR),  
       cCurrent.get(Calendar.MONTH),  
       cCurrent.get(Calendar.DAY_OF_MONTH));  
     cFirstDay.set(Calendar.DAY_OF_MONTH, 1);//获取本月第一天         
        return cFirstDay;  
    }  
    // 获得本月最后一天的日期  
    public static Calendar getLastDayofMonth(Calendar cFirstDay)   
    {  
     Calendar cLast = Calendar.getInstance();  
     cLast.set(cFirstDay.get(Calendar.YEAR),  
       cFirstDay.get(Calendar.MONTH),  
       cFirstDay.get(Calendar.DAY_OF_MONTH));     
     cLast.add(Calendar.MONTH, 1);         //本月第一天加上1月  
     cLast.add(Calendar.DAY_OF_MONTH, -1); //减去1天,得到本月的最后一天         
  
   
        return cLast;  
    }  
 // 获得本月第一天的日期  
    public static Calendar getFirstDayofYear(Calendar cCurrent)   
    {  
     Calendar cFirstDay = Calendar.getInstance();  
     cFirstDay.set(cCurrent.get(Calendar.YEAR),  
       cCurrent.get(Calendar.MONTH),  
       cCurrent.get(Calendar.DAY_OF_MONTH));  
     cFirstDay.set(Calendar.DAY_OF_YEAR, 1);//获取本年第一天         
        return cFirstDay;  
    }  
    // 获得本月最后一天的日期  
    public static Calendar getLastDayofYear(Calendar cFirstDay)   
    {  
     Calendar cLast = Calendar.getInstance();  
     cLast.set(cFirstDay.get(Calendar.YEAR),  
       cFirstDay.get(Calendar.MONTH),  
       cFirstDay.get(Calendar.DAY_OF_MONTH));     
     cLast.add(Calendar.YEAR, 1);          //本年第一天加上1年  
     cLast.add(Calendar.DAY_OF_YEAR, -1); //减去1天,得到本年的最后一天      
        return cLast;  
    }
    
    //获取两个日期相隔的天数  
    public static int getBetweenDays(long lFrom,long lTo)  
    {       
     Calendar cFrom = Calendar.getInstance();  
  cFrom.setTimeInMillis(lFrom);  
  Calendar cTo = Calendar.getInstance();  
  cTo.setTimeInMillis(lTo);  
     return getBetweenDays(cFrom,cTo);       
    }  
    public static int getBetweenDays(Calendar cFrom,Calendar cTo)  
    {  
     int betweenDays = 0;  
     // 保证第二个时间一定大于第一个时间  
       
     int betweenYears = cTo.get(Calendar.YEAR)-cFrom.get(Calendar.YEAR);   
     betweenDays = cTo.get(Calendar.DAY_OF_YEAR)-cFrom.get(Calendar.DAY_OF_YEAR);   
     for(int i=0;i<betweenYears;i++)  
     {  
      cFrom.set(Calendar.YEAR,(cFrom.get(Calendar.YEAR)+1));  
      betweenDays += cFrom.getMaximum(Calendar.DAY_OF_YEAR);   
     }   
     return betweenDays;  
    } 
    
    
    /**
     * 计算两个月日期间相差多少个月   yyyy-MM
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMonthPoorDayCross(String startDate ,String endDate){
    	if(startDate.contains("/")&&endDate.contains("/")){
    		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM");
            DateTime start = formatter.parseDateTime(startDate);
            DateTime end = formatter.parseDateTime(endDate);
            int months = Months.monthsBetween(start, end).getMonths();
    		return months+1;
    	}
    		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
            DateTime start = formatter.parseDateTime(startDate);
            DateTime end = formatter.parseDateTime(endDate);
            int months = Months.monthsBetween(start, end).getMonths();
    		return months+1;
    	
    }
    
    /**
     * 判断某一年是否是闰年
     */
    public static boolean isLeapYear(Integer year){
    	 if(year%4==0&&year%100!=0||year%400==0){
    		 return true;
    	 }
    	return false;
    }
    
    /**
	 * 获得两个日期之间的所有月份
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException 
	 */
	 public static ArrayList<String> getMonthBetween(String minDate,String maxDate) throws Exception {
	        ArrayList<String> result = new ArrayList<String>();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

	        Calendar min = Calendar.getInstance();
	        Calendar max = Calendar.getInstance();

	        min.setTime(sdf.parse(minDate));
	        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	        max.setTime(sdf.parse(maxDate));
	        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	        Calendar curr = min;
	        while (curr.before(max)) {
	         result.add(sdf.format(curr.getTime()));
	         curr.add(Calendar.MONTH, 1);
	        }

	        return result;
	    }
	 
	 /**
	  * 
	  * @return 当前时间的拼值
	  */
	 public static String getproNo(){
		String proNo = TimeUtil.getYearMonthDayHourMinuteSecond(new Date().getTime());
		String[] split = proNo.replace(" ",":").replace(":","-").split("-");
		String prono = "";
		for (String string : split) {
			prono += string;
		}
		return prono;
	 }
	 
	 /**
	     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	     * 
	     * @param dateDate
	     * @return
	     */
	  public static String dateToStrLong(java.util.Date dateDate) {
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String dateString = formatter.format(dateDate);
	     return dateString;
	  }
	  
	  //换成date类型
	  public static Date getDate(String date) {
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  Date parse = null;
		  if(date == null){
			  date = "0000-00-00";
			  try {
					parse = simpleDateFormat.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			  return parse;
		  }
		  
		 
		try {
			parse = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return parse;
	  }
	  
	  //换成  Date类型 String类型
	  public static String getDateString(Date date) {
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String parse = null;
		try {
			parse = simpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return parse;
	  }
	  
	  
	  /**  
	     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式  
	     * @param date2 被比较的时间  为空(null)则为当前时间  
	     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年  
	     * @return  
	     */ 
	    public static int compareDate(String date1,String date2,int stype){  
	        int n = 0;  
	           
	        String[] u = {"天","月","年"};  
	        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";  
	           
	        date2 = date2==null?DateCalculate.getCurrentDate():date2;  
	           
	        DateFormat df = new SimpleDateFormat(formatStyle);  
	        Calendar c1 = Calendar.getInstance();  
	        Calendar c2 = Calendar.getInstance();  
	        try {  
	            c1.setTime(df.parse(date1));  
	            c2.setTime(df.parse(date2));  
	        } catch (Exception e3) {  
	            System.out.println("wrong occured");  
	        }  
	        //List list = new ArrayList();  
	        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果  
	            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来  
	            n++;  
	            if(stype==1){  
	                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1  
	            }  
	            else{  
	                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1  
	            }  
	        }  
	           
	        n = n-1;  
	           
	        if(stype==2){  
	            n = (int)n/365;  
	        }     
	           
	        /*System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);*/        
	        return n;  
	    }  
	       
	    /**  
	     * 得到当前日期  
	     * @return  
	     */ 
	    public static String getCurrentDate() {  
	        Calendar c = Calendar.getInstance();  
	        Date date = c.getTime();  
	        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");  
	        return simple.format(date);  
	 
	    }  
	 
}
