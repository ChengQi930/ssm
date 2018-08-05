package cn.bizowner.dingtalk.openapi.helper;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.JsapiTicket;
import com.dingtalk.open.client.api.service.corp.CorpConnectionService;
import com.dingtalk.open.client.api.service.corp.JsapiService;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.common.ServiceException;
import com.dingtalk.open.client.common.ServiceNotExistException;
import cn.bizowner.dingtalk.openapi.env.Env;
import cn.bizowner.utils.HttpUtils;
import cn.bizowner.utils.PropertiesUtil;

public class AuthHelper {

	public static Timer timer = null;
	// 调整到1小时50分钟
	public static final long cacheTime = 1000 * 60 * 55 * 2;
	public static long currentTime = 0 + cacheTime + 1;
	public static long lastTime = 0;
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * 在此方法中，为了避免频繁获取access_token，
	 * 在距离上一次获取access_token时间在两个小时之内的情况，
	 * 将直接从持久化存储中读取access_token
	 * 
	 * 因为access_token和jsapi_ticket的过期时间都是7200秒
	 * 所以在获取access_token的同时也去获取了jsapi_ticket
	 * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
	 * 具体信息请查看开发者文档--权限验证配置
	 */
	public static String getAccessToken() throws Exception {
		
		String accessTokenValue = PropertiesUtil.getValue("accesstoken",Env.PropertiesFilePath);
		
		JSONObject joAccessTokenValue = (JSONObject)JSONObject.parse(accessTokenValue);
		
		long curTime = System.currentTimeMillis();
		
		String accToken = "";

		 if (joAccessTokenValue == null || curTime-joAccessTokenValue.getLong("begin_time") >= cacheTime) 
		 {
			 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("corpid", Env.CORP_ID);
			 hashMap.put("corpsecret", Env.CORP_SECRET);
			 
			 String str = HttpUtils.doGet("https://oapi.dingtalk.com/gettoken", hashMap);
			 
			 if(str != null)
			 {
				 JSONObject jo = JSONObject.parseObject(str);
				 
				 accToken = jo.getString("access_token");
				 
				 JSONObject appToken = new JSONObject();
				 appToken.put("access_token", accToken);
				 appToken.put("begin_time", curTime);
				 
				 String strAppAccessToken = appToken.toJSONString();		
					
				 PropertiesUtil.setValue(Env.PropertiesFilePath, "accesstoken", strAppAccessToken);
				 
			 }
			 
			return accToken;
		 } 
		 else 
		 {
			 return joAccessTokenValue.getString("access_token");
		 }
	}

		
	
	
	// 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
	public static String getAppAccessToken() throws Exception {
		
		String appAccessTokenValue = PropertiesUtil.getValue("appaccesstoken",Env.PropertiesFilePath);
		
		JSONObject joAppAccessTokenValue = (JSONObject)JSONObject.parse(appAccessTokenValue);
		
		long curTime = System.currentTimeMillis();
		
		String accToken = "";

		 if (joAppAccessTokenValue == null || curTime-joAppAccessTokenValue.getLong("begin_time") >= cacheTime) 
		 {
			 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("appid", Env.APP_ID);
			 hashMap.put("appsecret", Env.APP_SECRET);
			 
			 String str = HttpUtils.doGet("https://oapi.dingtalk.com/sns/gettoken", hashMap);
			 
			 if(str != null)
			 {
				 JSONObject jo = JSONObject.parseObject(str);
				 
				 accToken = jo.getString("access_token");
				 
				 JSONObject appToken = new JSONObject();
				 appToken.put("app_access_token", accToken);
				 appToken.put("begin_time", curTime);
				 
				 String strAppAccessToken = appToken.toJSONString();		
					
				 PropertiesUtil.setValue(Env.PropertiesFilePath, "appaccesstoken", strAppAccessToken);
				 
			 }
			 
			return accToken;
		 } 
		 else 
		 {
			 return joAppAccessTokenValue.getString("app_access_token");
		 }
	}

	
}
