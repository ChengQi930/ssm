package cn.bizowner.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/*import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;*/
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

public class CommonUtils {

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 返回当前日期时间字符串<br>
	 * 默认格式:yyyy-mm-dd hh:mm:ss
	 * 
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * 返回当前日期时间字符串<br>
	 * 默认格式:yyyymmddhhmmss
	 * 
	 * @return String 返回当前字符串型日期时间
	 */
	public static BigDecimal getCurrentTimeAsNumber() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		returnStr = f.format(date);
		return new BigDecimal(returnStr);
	}

	/**
	 * 返回自定义格式的当前日期时间字符串
	 * 
	 * @param format
	 *            格式规则
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime(String format) {
		String returnStr = null;
		DateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}
	
	
	
	public static String getCurrTime()
	{
		String strCurrTime = "";
		try
		{	
			Calendar now = Calendar.getInstance();
			strCurrTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(now.getTime());
		}
		catch(Exception ex)
		{
			String errMess = ex.getMessage();
		}
		return strCurrTime;
	}
	
	
	
	
	public static String getCurrDate()
	{
		String strCurrTime = "";
		try
		{	
			Calendar now = Calendar.getInstance();
			strCurrTime = (new SimpleDateFormat("yyyy-MM-dd")).format(now.getTime());
		}
		catch(Exception ex)
		{
			String errMess = ex.getMessage();
		}
		return strCurrTime;
	}
	
	
	
	
	
	public static Date convertStringToDate(String strDate)
	{
		Date date = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    date = sdf.parse(strDate);  
		}
		catch(Exception ex)
		{
				String str = ex.getMessage();
		}
		return date;
	}
	
	
	
	public static String convertDateToString(Date date)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str = sdf.format(date);
			return str;
		}
		catch(Exception ex)
		{
			
		}
		return null;
	}
	
	
	
	public static boolean isSameDate(Date d1, Date d2)
	{
		/*LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();  
	    LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();  
	    return localDate1.isEqual(localDate2); */
		return true;
	}
	
	
	
	
	public static String getUUID()
	{
				UUID uuid = UUID.randomUUID();
				String str = uuid.toString();
				return str;
		
	}
	
	
	public static String getStartDate(String endDate)
    {
		String startDate = "";
		String[] content = endDate.split("-",-1);
        if (content.length == 3)
        {
            int year = Integer.parseInt(content[0]);
            int month = Integer.parseInt(content[1]);
            int day = Integer.parseInt(content[2]);
            
            
            if (day > 25)
            {
                //startDate = year + "-" + month + "-26";
                day = 26;
            }
            else
            {
                if (month != 1)
                {
                    /*int tempMonth = month - 1;
                    startDate = year + "-" + tempMonth + "-26";*/
                    
                    month=month-1;
                    day = 26;
                }
                else
                {
                    /*int tempYear = year - 1;
                    startDate = tempYear + "-12-26";*/
                    
                    year = year - 1;
                    month = 12;
                    day = 26;

                }
            }
            
            startDate = new StringBuilder().append(year).append("-")  
                    .append(month < 10 ? "0" + month : month).append("-")  
                    .append((day < 10) ? "0" + day : day).toString();
        }

        
        
        return startDate;

    }
	
	
	
	public static String pad (String str ,int size ,boolean isprefixed) {
        if (str == null)
            str = "";
        int str_size = str.length();
        int pad_len = size - str_size;
        StringBuffer retvalue = new StringBuffer();
        for (int i = 0; i < pad_len; i++) {
            retvalue.append("0");
        }
        if (isprefixed) 
            return retvalue.append(str).toString();
        return retvalue.insert(0, str).toString();
    }
	
	
	
	
	
	
	public static String getKeyFromJo(JSONObject jo,String strValue)
	{
			String key = "";
			
			for (Map.Entry<String, Object> entry : jo.entrySet()) {
				
				String value = entry.getValue().toString();
								
				if(strValue!=null && strValue.equals(value))
				{
					key = entry.getKey().toString();
					break;
				}
	        }
			return key;
	}
	
	
	
}
