package cn.bizowner.dingtalk.openapi.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.UserDept;
import cn.bizowner.model.DeptInfo;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.utils.HttpUtils;

public class DeptHelper {

	public static List<DeptInfo> getDeptList() throws Exception
	{
			 String url = "https://oapi.dingtalk.com/department/list";
		 	 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("access_token", AuthHelper.getAccessToken());
			 
		 	 String str = HttpUtils.doGet(url, hashMap);
		 	 
		 	 
		 	 JSONObject jo = JSONObject.parseObject(str);
			 
			 
			 int errcode = jo.getIntValue("errcode");
			 	
			 	if(errcode == 0)
			 	{
			 		
			 		 JSONArray ja = jo.getJSONArray("department");
			 		 
			 		 List<DeptInfo> listDeptInfo = new ArrayList<DeptInfo>();
			 		 
			 		 for(int i=0;i<ja.size();i++)
			 		 {
			 			 	jo = ja.getJSONObject(i);
			 			 	String id = jo.getString("id");
			 			 	String parentId="1";
			 			 	if(!id.equals("1"))
			 			 	{
			 			 		parentId = jo.getString("parentid");
			 			 	}
			 			 	String name = jo.getString("name");
			 			 	
			 			 	DeptInfo deptInfo = new DeptInfo();
			 			 	
			 			 	deptInfo.setDeptId(id);
			 			 	deptInfo.setParentDeptId(parentId);
			 			 	deptInfo.setDeptName(name);
			 			 	
			 			 	listDeptInfo.add(deptInfo);
			 		 }
			 		
			 		 return listDeptInfo;
			 	}
			 	else
			 	{
			 		 String errMsg = jo.getString("errmsg");
			 		 throw new Exception(errMsg);
			 	}
			 
	}
	
	
	
	
	
	
	
	public static List<DingDingUserInfo> getUserInfoByDeptId(String id) throws Exception
	{
			 String url = "https://oapi.dingtalk.com/user/list";
		 	 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("access_token", AuthHelper.getAccessToken());
			 hashMap.put("department_id", id);
			 
		 	 String str = HttpUtils.doGet(url, hashMap);
		 	 
		 	 
		 	 JSONObject jo = JSONObject.parseObject(str);
			 
			 
			 int errcode = jo.getIntValue("errcode");
			 	
			 	if(errcode == 0)
			 	{
			 		
			 		 JSONArray ja = jo.getJSONArray("userlist");
			 		 
			 		 List<DingDingUserInfo> listUserInfo = new ArrayList<DingDingUserInfo>();
			 		 
			 		 for(int i=0;i<ja.size();i++)
			 		 {
			 			 
			 			 jo = ja.getJSONObject(i);
			 			 
			 			 DingDingUserInfo userInfo = new DingDingUserInfo();
				 		 
				 		 String userid = jo.getString("userid"); 
				 		 String mobile = jo.getString("mobile"); 
				 		 String name = jo.getString("name"); 
				 		 
				 		 userInfo.setUserId(userid);
				 		 userInfo.setMobile(mobile);
				 		 userInfo.setMc(name);
				 		 
				 		 listUserInfo.add(userInfo);
			 		 }
			 		
			 		 return listUserInfo;
			 	}
			 	else
			 	{
			 		 String errMsg = jo.getString("errmsg");
			 		 throw new Exception(errMsg);
			 	}
			 
	}
	
	
	
	
	
	
	public static String getDeptNameByDeptId(String deptId) throws Exception
	{
			 String url = "https://oapi.dingtalk.com/department/get";
		 	 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("access_token", AuthHelper.getAccessToken());
			 hashMap.put("id", deptId);
			 
		 	 String str = HttpUtils.doGet(url, hashMap);
		 	 
		 	 
		 	 JSONObject jo = JSONObject.parseObject(str);
			 
			 
			 int errcode = jo.getIntValue("errcode");
			 	
			 	if(errcode == 0)
			 	{
			 		 String deptName = jo.getString("name");
			 		 
			 		 return deptName;
			 		
			 	}
			 	else
			 	{
			 		 String errMsg = jo.getString("errmsg");
			 		 throw new Exception(errMsg);
			 	}
			 
	}
	
	
	
	
	
	
	
	
	public static List<Dto> getChildDeptsByDeptId(String deptId) throws Exception
	{
			 String url = "https://oapi.dingtalk.com/department/list";
		 	 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("access_token", AuthHelper.getAccessToken());
			 hashMap.put("id", deptId);
			 hashMap.put("fetch_child","false");
			 
		 	 String str = HttpUtils.doGet(url, hashMap);
		 	 
		 	 
		 	 JSONObject jo = JSONObject.parseObject(str);
			 
			 
			 int errcode = jo.getIntValue("errcode");
			 	
			 	if(errcode == 0)
			 	{
			 		
			 		
			 		 List<Dto> listDto = new ArrayList<Dto>();
			 		
			 		 JSONArray ja = jo.getJSONArray("department");
			 		 
			 		 for(int i=0;i<ja.size();i++)
			 		 {
			 			 		jo = ja.getJSONObject(i);
			 			 		
			 			 		String id = jo.getString("id");
						 		String deptName = jo.getString("name");
						 		 
						 		 Dto dto = new BaseDto();
						 		 dto.put("id", id);
						 		 dto.put("name", deptName);
						 		 
						 		listDto.add(dto);
						 		 
			 		 }
			 		
			 		 return listDto;
			 		
			 	}
			 	else
			 	{
			 		 String errMsg = jo.getString("errmsg");
			 		 throw new Exception(errMsg);
			 	}
			 
	}
	
	
	
	
	
	
	
	
	public static List<Dto> getAllChildDeptsByDeptId(String deptId) throws Exception
	{
			 String url = "https://oapi.dingtalk.com/department/list";
		 	 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("access_token", AuthHelper.getAccessToken());
			 hashMap.put("id", deptId);
			 
		 	 String str = HttpUtils.doGet(url, hashMap);
		 	 
		 	 
		 	 JSONObject jo = JSONObject.parseObject(str);
			 
			 
			 int errcode = jo.getIntValue("errcode");
			 	
			 	if(errcode == 0)
			 	{
			 		
			 		
			 		 List<Dto> listDto = new ArrayList<Dto>();
			 		
			 		 JSONArray ja = jo.getJSONArray("department");
			 		 
			 		 for(int i=0;i<ja.size();i++)
			 		 {
			 			 		jo = ja.getJSONObject(i);
			 			 		
			 			 		String id = jo.getString("id");
						 		String deptName = jo.getString("name");
						 		 
						 		 Dto dto = new BaseDto();
						 		 dto.put("id", id);
						 		 dto.put("name", deptName);
						 		 
						 		listDto.add(dto);
						 		 
			 		 }
			 		
			 		 return listDto;
			 		
			 	}
			 	else
			 	{
			 		 String errMsg = jo.getString("errmsg");
			 		 throw new Exception(errMsg);
			 	}
			 
	}
	
	
	
	
	
	
	public static String getDeptIdByName(String deptName) throws Exception
	{
			 String url = "https://oapi.dingtalk.com/department/list";
		 	 
			 Map<String,String> hashMap = new HashMap<String,String>();
			 hashMap.put("access_token", AuthHelper.getAccessToken());
			 
		 	 String str = HttpUtils.doGet(url, hashMap);
		 	 
		 	 
		 	 JSONObject jo = JSONObject.parseObject(str);
			 
			 
			 int errcode = jo.getIntValue("errcode");
			 	
			 	if(errcode == 0)
			 	{
			 		
			 			String deptId = null;
			 		
				 		JSONArray ja = jo.getJSONArray("department");
				 		 
				 		 for(int i=0;i<ja.size();i++)
				 		 {
				 			 
				 			 		jo = ja.getJSONObject(i);
				 			 		
							 		String name = jo.getString("name");
							 		 
							 		if(name.equals(deptName))
							 		{
							 			deptId = jo.getString("id");
							 			break;
							 		}
				 		 }
				 		 
				 		 return deptId;
			 		
			 	}
			 	else
			 	{
			 		 String errMsg = jo.getString("errmsg");
			 		 throw new Exception(errMsg);
			 	}
			 
	}
}
