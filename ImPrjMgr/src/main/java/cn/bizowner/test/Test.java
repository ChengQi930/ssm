package cn.bizowner.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.DocumentHandler;

import com.alibaba.fastjson.JSONObject;
import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.MediaHelper;

@SuppressWarnings("deprecation")
public class Test {
     
	@org.junit.Test
	public void testName() throws Exception {
		  FileInputStream fileInputStream = new FileInputStream(new File("D:\\.sql"));
		  
		  System.out.println(fileInputStream.toString());
		  
	}
	
	@org.junit.Test
	public void testName3435() throws Exception {
		 String num = "fwwgwgreeggge";
		 File file = new File("D:"+File.separator+"1.txt");
		 
		 if(!file.exists()) {
			 file.createNewFile();
			 
		 }
		 if(!file.isDirectory()) {
			 file.mkdirs();
		 }
	   OutputStream fileOutputStream = new FileOutputStream(file,true); 
	   byte[] bytes = num.getBytes();
       for(int i=0;i<bytes.length;i++) {
    	   fileOutputStream.write(bytes[i]);
       }
       System.out.println("yes");
       fileOutputStream.close();
       
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*String accessToken = AuthHelper.getAccessToken();
		System.out.println(accessToken);
		
		File file = new File("C:\\Users\\liujie\\Desktop\\8.jpg");
		String url = "https://oapi.dingtalk.com/media/upload?" +"access_token=" + accessToken + "&type=file";
		JSONObject response = MediaHelper.uploadMedia(url, "C:\\Users\\liujie\\Desktop\\8.jpg");
		
		
		String media = response.getString("media_id");
		
		System.out.println(response.toJSONString());
		System.out.println(media);
		
		File f = new File("D:\\");
		String downUrl = "https://oapi.dingtalk.com/media/downloadFile?access_token="+accessToken+"&media_id="+media;
		
		boolean flag = MediaHelper.downloadMedia(downUrl,"D:");
		
		System.out.println(flag);*/
		/*DocumentHandler dh = new DocumentHandler();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//添加性别为男
		dataMap.put("sex", "男");
		dh.createDoc("E:\\doc", "test.ftl", "E:/成功.doc", dataMap);*/
		
		
	}
}
