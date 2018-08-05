package cn.bizowner.utils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpUtils {

		/*public static String doPostWithJSON(String url,Map<String, String> params)
		{
			CloseableHttpClient httpClient = null;
			
			try
			{
				
				httpClient = getHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
				
				if(params != null)
				{
					String jsonStr = JSON.toJSONString(params);  
				    StringEntity requestEntity = new StringEntity(jsonStr,"utf-8");//���������������  
				    requestEntity.setContentEncoding("UTF-8");    
				    requestEntity.setContentType("application/json");
				    httpPost.setEntity(requestEntity);
				}
				
				
			    HttpResponse  response =httpClient.execute(httpPost); 
			    
			    int statusCode = response.getStatusLine().getStatusCode();
			    
			    if (statusCode != 200) {
					httpPost.abort();
					throw new RuntimeException("HttpClient,error status code :" + statusCode);
				}
			    
			    HttpEntity responseEntity = response.getEntity();
				String result = null;
				if (responseEntity != null) {
					result = EntityUtils.toString(responseEntity, "UTF-8");
				}
				
				return result;
				
			}	
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			finally
			{
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (Exception e) {
					System.out.println("�ر�IO���쳣");
				}
			}
			
			return null;			
		}*/
		
		
		
		
		
		
		
		public static String doPost(String url,Map<String, String> params)
		{
			return 	doPost(url,params,false);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		public static String doPost(String url,Map<String, String> params,boolean convertToJSON)
		{
			CloseableHttpClient httpClient = null;
			
			try
			{
				
				httpClient = getHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
				
				
				if(params != null)
				{
					
					if(convertToJSON ==true)
					{
						String jsonStr = JSON.toJSONString(params);  
					    StringEntity requestEntity = new StringEntity(jsonStr,"utf-8");
					    requestEntity.setContentEncoding("UTF-8");    
					    requestEntity.setContentType("application/json");
					    httpPost.setEntity(requestEntity);
					}
					else
					{		
						List<NameValuePair> formParams = buildParameters(params);
						HttpEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
						httpPost.setEntity(requestEntity);
					}
					
					
				}
				
				
			    HttpResponse  response =httpClient.execute(httpPost); 
			    
			    int statusCode = response.getStatusLine().getStatusCode();
			    
			    if (statusCode != 200) {
					httpPost.abort();
					throw new RuntimeException("HttpClient,error status code :" + statusCode);
				}
			    
			    HttpEntity responseEntity = response.getEntity();
				String result = null;
				if (responseEntity != null) {
					result = EntityUtils.toString(responseEntity, "UTF-8");
				}
				
				return result;
				
			}	
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			finally
			{
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (Exception e) {
					System.out.println("�ر�IO���쳣");
				}
			}
			
			return null;			
		}
		
		
		
		
		
		
		
		
		public static String doFileUpload(String url,Map<String,Object> params,String filePath,String paramName)
		{
			CloseableHttpClient httpClient = null;
			
			try
			{
				
				httpClient = getHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
				
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				
				File file = new File(filePath);
				String fileName = file.getName();
				builder.addBinaryBody(paramName, file,ContentType.MULTIPART_FORM_DATA,fileName);
				//builder.addPart(paramName, new FileBody(new File(filePath)));
				
				if(params != null)
				{
					Iterator iterator = params.entrySet().iterator();  
					while (iterator.hasNext()) {  
			            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();  
			            builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.create("text/plain",Charset.forName("UTF-8")));  
			            //ContentType contentType = ContentType.create("text/plain",Charset.forName("UTF-8"));
			            //StringBody stringBody = new StringBody(entry.getValue(),contentType);
			            //builder.addPart(entry.getKey(), stringBody);
					}
				}
				
				httpPost.setEntity(builder.build());
				
			    HttpResponse  response =httpClient.execute(httpPost); 
			    
			    int statusCode = response.getStatusLine().getStatusCode();
			    
			    if (statusCode != 200) {
					httpPost.abort();
					throw new RuntimeException("HttpClient,error status code :" + statusCode);
				}
			    
			    HttpEntity responseEntity = response.getEntity();
				String result = null;
				if (responseEntity != null) {
					result = EntityUtils.toString(responseEntity, "UTF-8");
				}
				
				return result;
				
			}	
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			finally
			{
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (Exception e) {
					System.out.println("�ر�IO���쳣");
				}
			}
			
			return null;			
		}
		
		
		
		
		
		
		
		
		
		public static boolean doFileDownload(String url,Map<String,String> params,String fileDir)
		{
			CloseableHttpClient httpClient = null;
			
			try
			{
				
				httpClient = getHttpClient();
				
				List<NameValuePair> formParams = buildParameters(params);
				
				if(formParams != null)
				{
					HttpEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
					url += "?" + EntityUtils.toString(requestEntity);
				}
				
				HttpGet httpGet = new HttpGet(url);
				
			    HttpResponse  response =httpClient.execute(httpGet); 
			    
			    int statusCode = response.getStatusLine().getStatusCode();
			    
			    if (statusCode != 200) {
			    	httpGet.abort();
					throw new RuntimeException("HttpClient,error status code :" + statusCode);
				}
			    
			    
			    String fileName = "";
                Header[] headers =  response.getAllHeaders();
                int i=0;
                while(i<headers.length)
                {
                		String headName = headers[i].getName();
                		if(headName.equals("Content-Disposition"))
                		{
                			String value = headers[i].getValue();
                			fileName = value.substring(value.lastIndexOf("=") + 1, value.length());
                			break;
                		}
                		i++;
                }
			    
                
                File downloadFile = new File(fileDir + File.separator + fileName);
                FileUtils.writeByteArrayToFile(downloadFile, EntityUtils.toByteArray(response.getEntity()));
				
				return true;
				
			}	
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			finally
			{
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (Exception e) {
				}
			}
			
			return false;			
		}
		
		
		
		
		
		
		
		
		public static String doGet(String url,Map<String, String> params)
		{
			CloseableHttpClient httpClient = null;
			
			try
			{
				
				httpClient = getHttpClient();
				
				
				List<NameValuePair> formParams = buildParameters(params);
				
				if(formParams != null)
				{
					HttpEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
					url += "?" + EntityUtils.toString(requestEntity);
				}
				
				HttpGet httpGet = new HttpGet(url);
				
			    HttpResponse  response =httpClient.execute(httpGet); 
			    
			    int statusCode = response.getStatusLine().getStatusCode();
			    
			    if (statusCode != 200) {
			    	httpGet.abort();
					throw new RuntimeException("HttpClient,error status code :" + statusCode);
				}
			    
			    HttpEntity responseEntity = response.getEntity();
				String result = null;
				if (responseEntity != null) {
					result = EntityUtils.toString(responseEntity, "UTF-8");
				}
				
				return result;
				
			}	
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			finally
			{
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (Exception e) {
					System.out.println("�ر�IO���쳣");
				}
			}
			
			return null;			
		}
		
		
		
		
		
		
		
		
		 public static CloseableHttpClient getHttpClient() 
		 {
			 
		 	RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).setConnectTimeout(30000).setSocketTimeout(30000).build();
		 
		    return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		}
		
		
		
		public static List<NameValuePair> buildParameters(Map<String, String> params) 
		{
			if(params != null)
			{		
				List<NameValuePair> lparams = new ArrayList<NameValuePair>();
			    Iterator<Entry<String, String>> it = params.entrySet().iterator();
			    while (it.hasNext()) {
			      Entry<String, String> entry = it.next();
			      lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			    }
			    return lparams;
			}	
		    return null;
		}
}
