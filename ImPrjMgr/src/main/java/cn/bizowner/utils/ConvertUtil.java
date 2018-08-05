package cn.bizowner.utils;

import java.util.Arrays;
import java.util.List;

public class ConvertUtil {

			
			public static String ListToString(List<String> list)
			{
					return String.join(",", list);
			}
			
			
			public static String ListToString(List<String> list,String split)
			{
					return String.join(split, list);
			}
			
			
			public static List<String> StringToList(String str)
			{
					return  Arrays.asList(str.split(","));
			}
			
			
			
			public static List<String> StringToList(String str,String split)
			{
					return  Arrays.asList(str.split(split));
			}
	
}
