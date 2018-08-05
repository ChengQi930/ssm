package cn.bizowner.dingtalk.openapi.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.model.UserDept;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.utils.HttpUtils;

public class UserHelper {

			public static String  getUserId(String code)
			{
					
					try
					{
						String appAccessToken = AuthHelper.getAppAccessToken();
						
						JSONObject joPersistentCode = getPersistentCode(appAccessToken,code);
					
						String snsKoken = getSnsToken(appAccessToken,joPersistentCode.getString("openid"),joPersistentCode.getString("persistent_code"));
						
						String unionId = getUnionId(snsKoken);
						
						String userId = getUserIdByUnionId(unionId);
						
						//DingDingUserInfo userInfo = getUserInfoByUserId(userId);
						
						return userId;
						
					}
					catch(Exception ex)
					{	
							String s = ex.getMessage();
					}
					return null;
						
			}
			
			
			public static  JSONObject getPersistentCode(String appAccessToken,String code) throws Exception
			{
						
				 	 String url = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token="+appAccessToken;
				 	 
				 	 Map<String,String> hashMap = new HashMap<String,String>();
				 	 
				 	 hashMap.put("tmp_auth_code",code);
				 	 
				 	 String str = HttpUtils.doPost(url, hashMap,true);
				 	 
				 	JSONObject jo = JSONObject.parseObject(str);
					 
				 	int errcode = jo.getIntValue("errcode");
				 	
				 	if(errcode == 0)
				 	{
				 		jo.remove("errcode");
				 		jo.remove("errmsg");
				 		
				 		return jo;
				 	}
				 	else
				 	{
				 		 String errMsg = jo.getString("errmsg");
				 		 throw new Exception(errMsg);
				 	}
					 
			}
			
			
			
			
			public static  String getSnsToken(String appAccessToken,String openId,String persistentCode) throws Exception
			{
						
				 	 String url = "https://oapi.dingtalk.com/sns/get_sns_token?access_token="+appAccessToken;
				 	 
				 	 Map<String,String> hashMap = new HashMap<String,String>();
				 	 
				 	 hashMap.put("openid",openId);
				 	 hashMap.put("persistent_code",persistentCode);
				 	 
				 	 String str = HttpUtils.doPost(url, hashMap,true);
				 	 
					 JSONObject jo = JSONObject.parseObject(str);
					 
					 int errcode = jo.getIntValue("errcode");
					 	
					 	if(errcode == 0)
					 	{
					 		String snsToken = jo.getString("sns_token");
					 		 
					 		return snsToken;
					 	}
					 	else
					 	{
					 		 String errMsg = jo.getString("errmsg");
					 		 throw new Exception(errMsg);
					 	}
					 
			}
			
			
			
			public static String getUnionId(String snsToken) throws Exception
			{
					 String url = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token="+snsToken;
				 	 
				 	 String str = HttpUtils.doGet(url, null);
				 	 
					 JSONObject jo = JSONObject.parseObject(str);
					 
					 int errcode = jo.getIntValue("errcode");
					 	
					 	if(errcode == 0)
					 	{
					 		 JSONObject joUserInfo = jo.getJSONObject("user_info");
								
							 String dingdingId = joUserInfo.getString("unionid");
							 
							 return dingdingId;
					 	}
					 	else
					 	{
					 		 String errMsg = jo.getString("errmsg");
					 		 throw new Exception(errMsg);
					 	}
			}
			
			
			
			public static String getUserIdByUnionId(String unionId) throws Exception
			{
					 String url = "https://oapi.dingtalk.com/user/getUseridByUnionid";
				 	 
					 Map<String,String> hashMap = new HashMap<String,String>();
					 hashMap.put("access_token", AuthHelper.getAccessToken());
					 hashMap.put("unionid", unionId);
					 
				 	 String str = HttpUtils.doGet(url, hashMap);
				 	 
					 JSONObject jo = JSONObject.parseObject(str);
					 
					 int errcode = jo.getIntValue("errcode");
					 	
					 	if(errcode == 0)
					 	{
					 		 String userId = jo.getString("userid");
					 		 return userId;
					 	}
					 	else
					 	{
					 		 String errMsg = jo.getString("errmsg");
					 		 throw new Exception(errMsg);
					 	}
			}
			
			
			
			
			public static DingDingUserInfo getUserInfoByUserId(String userId) throws Exception
			{
					 String url = "https://oapi.dingtalk.com/user/get";
				 	 
					 Map<String,String> hashMap = new HashMap<String,String>();
					 hashMap.put("access_token", AuthHelper.getAccessToken());
					 hashMap.put("userid", userId);
					 
				 	 String str = HttpUtils.doGet(url, hashMap);
				 	 
				 	 
				 	 JSONObject jo = JSONObject.parseObject(str);
					 
					 
					 
					 int errcode = jo.getIntValue("errcode");
					 	
					 	if(errcode == 0)
					 	{
					 		 DingDingUserInfo userInfo = new DingDingUserInfo();
					 		 
					 		 String userid = jo.getString("userid"); 
					 		 String mobile = jo.getString("mobile"); 
					 		 String name = jo.getString("name"); 
					 		 String deptId = jo.getString("department"); 
					 		 
					 		  
					 		 List<UserDept> listDept = new ArrayList<UserDept>();
					 		 
					 		 JSONArray ja = JSONArray.parseArray(deptId);
					 		 for(Object obj : ja)
							 {
									String s = obj.toString();
									
									String deptName = DeptHelper.getDeptNameByDeptId(s);
									
									UserDept deptInfo = new UserDept();

									deptInfo.setUserId(userid);
									deptInfo.setDeptId(s);
									deptInfo.setDeptName(deptName);
									
									listDept.add(deptInfo);
							 }
					 		
					 		
					 		 
					 		 
					 		 userInfo.setUserId(userid);
					 		 userInfo.setMobile(mobile);
					 		 userInfo.setMc(name);
					 		 userInfo.setListDept(listDept);
					 		 
					 		 
					 		 
					 		 return userInfo;
					 	}
					 	return null;
					 	/*else
					 	{
					 		 String errMsg = jo.getString("errmsg");
					 		 throw new Exception(errMsg);
					 	}*/
					 
			}
			
			
			
			
			
			
			
			
			public static String getUserMcByUserId(String userId) throws Exception
			{ 
				
					 String url = "https://oapi.dingtalk.com/user/get";
				 	 
					 Map<String,String> hashMap = new HashMap<String,String>();
					 hashMap.put("access_token", AuthHelper.getAccessToken());
					 hashMap.put("userid", userId);
					 
				 	 String str = HttpUtils.doGet(url, hashMap);
				 	  
				 	 JSONObject jo = JSONObject.parseObject(str);
					  
					 int errcode = jo.getIntValue("errcode");
					 	
					 	if(errcode == 0)
					 	{ 
					 		 String name = jo.getString("name");  
					 		 
					 		 return name;
					 	}
					 	else
					 	{
					 		 String errMsg = jo.getString("errmsg");
					 		 System.out.println("errMsg="+errMsg);
					 		 return "";
					 	}
					 
			}
			
			
			
}
