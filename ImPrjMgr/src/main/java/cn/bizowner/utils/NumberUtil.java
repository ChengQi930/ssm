package cn.bizowner.utils;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;

public class NumberUtil {

		public static String NumToMoney(String str)
		{
			
			
			NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
			
			Double dMoney = 0.0;
			dMoney = NumberUtils.toDouble(str,0);
			
			String strMoney = format.format(dMoney);
			
			return strMoney;
		}
		
		
		
		public static void main(String[] args) 
		{
			
						String  s = NumToMoney("长");
						System.out.println(s);
		}
	
}
