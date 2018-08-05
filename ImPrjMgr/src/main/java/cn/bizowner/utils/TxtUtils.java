package cn.bizowner.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class TxtUtils {
	public static JSONObject txt2json(String filePath,String separator)
	{
		
		BufferedReader br = null;
		
		JSONObject json = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			
			String data = null;
			
			String[] headNames={};
			String strHead = null;
			
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			
			for(int i=0;(data = br.readLine())!=null;i++)
			{
					  String[] str = data.split(separator);
				
					 if(i==0)
					 {
						 strHead =data;
						 headNames = str;
					 }
					 else
					 {
						 Map<String,String> map = new LinkedHashMap<String,String>();
						 for(int j=0;j<str.length;j++)
						 {
							 	map.put(headNames[j], str[j]);
						 }
						 
						 list.add(map);
					 }
			}
			
			
			br.close();
			br = null;
			
			
			Map<String, Object> hashMap= new LinkedHashMap<String, Object>();
	        hashMap.put("head", strHead);
	        hashMap.put("content", list);
	        
	        json = JSONObject.parseObject(JSON.toJSONString(hashMap));
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		
		finally
		{
				if(br != null)
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		return json; 
		
		
	}
	
	
	
	
	public static void export2Txt(Map<String, String> headMap,String separator,JSONArray jaArray,String filePath)
	{
		
		BufferedWriter bw = null;
		try
		{
			//String[] headers = head.split(separator);
			
			String[] properties = new String[headMap.size()];
            String[] headers = new String[headMap.size()];
            int ii = 0;
            for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) 
            {
                String fieldName = iter.next();

                properties[ii] = fieldName;
                headers[ii] = headMap.get(fieldName);
                
                ii++;
            }
			
	        
			OutputStream outTxt = new FileOutputStream(filePath);
	        
	        bw = new BufferedWriter(new OutputStreamWriter(outTxt));
	        
	        String headStr=StringUtils.join(headers, separator);
	        bw.write(headStr);
	        bw.newLine();
	        
	        
	        for(Object obj : jaArray)
	        {
	        	JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
	        	String str = "";
	        	 for (int i = 0; i < properties.length; i++)
	        	 {
	        		 Object o =  jo.get(properties[i]);
	        		 String s = "";
	        		 if(o != null)
	        		 {
	        			 s = o.toString();
	        		 }
	        		 str+=s+separator;   		 
	        	 }
	        	 str = str.substring(0,str.length()-1);
	        	 
	        	 bw.write(str);
	             bw.newLine();
	             
	        	 
	        }
	        if(bw != null)	bw.close();
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		finally
		{
			 if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
