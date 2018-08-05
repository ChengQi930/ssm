/*+**********************************************************************************
 * The contents of this file are subject to the vtiger CRM Public License Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * The Original Code is:  vtiger CRM Open Source
 * The Initial Developer of the Original Code is vtiger.
 * Portions created by vtiger are Copyright (C) vtiger.
 * All Rights Reserved.
 ************************************************************************************/
package cn.bizowner.crm.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.bizowner.crm.env.Env;
import cn.bizowner.utils.HttpUtils;


/**
 * Vtiger Webservice Client
 * @author prasad
 *
 */

public class CrmHelper {
	
		
	
	public void hasError(String result) throws Exception
	{		
			if(result == null)
			{
				throw new Exception("result is null");
			}
			else
			{
				JSONObject resultObject = (JSONObject)JSONObject.parse(result);
				if(resultObject.get("success").toString() == "false") 
				{
					
					JSONObject jo = resultObject.getJSONObject("error");
					
					
					throw new Exception(jo.getString("code"));
				}
			}
	}

	
	protected String doChallenge(String username) throws Exception{
		
		Map params = new HashMap();
		params.put("operation", "getchallenge");
		params.put("username", username);
		
		String response = HttpUtils.doGet(Env.serviceurl, params);
		
		hasError(response);
		
		JSONObject jo = (JSONObject)(JSONObject.parse(response));
		
		JSONObject result = jo.getJSONObject("result");
		
		String  token = result.get("token").toString();
		
		return token;		
	}
	
	/**
	 * Check and perform login if required.
	 */
	protected void checkLogin() {
		// TODO
	}
	
	/**
	 * Generate MD5 (in hex)
	 * @param input
	 * @return
	 * @throws Exception
	 */
	protected String md5Hex(String input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hash = md.digest(input.getBytes());
		return String.format("%032x", new BigInteger(1, hash));
	}
	
	
	/**
	 * Do Login Operation
	 * @param username
	 * @param vtigerUserAccessKey
	 * @return
	 */
	public String doLogin(String username, String vtigerUserAccessKey) throws Exception{
		
		
			String token = doChallenge(username);
		
			Map params = new HashMap();
			params.put("operation", "login");
			params.put("username", username);
			params.put("accessKey", md5Hex(token + vtigerUserAccessKey));
			
			String response = HttpUtils.doPost(Env.serviceurl, params);
			
			
			hasError(response); 
			
			JSONObject jo = (JSONObject)(JSONObject.parse(response));
			
			JSONObject result = jo.getJSONObject("result");
			
			String  sessionId = result.get("sessionName").toString();
			
			return sessionId;
			
		
	}
	
	/**
	 * Do Query operation.
	 * @param query
	 * @return
	 */
	public JSONArray doQuery(String query,String sessionId) throws Exception{
		// Perform re-login if required.
		
		if(query.trim().endsWith(";") == false) {
			query += ";";
		}
		
		Map params = new HashMap();
		params.put("operation", "query");
		params.put("sessionName", sessionId);
		params.put("query", query);
		
		
		String response = HttpUtils.doGet(Env.serviceurl, params);
		
		hasError(response); 
		
		JSONObject jo = (JSONObject)(JSONObject.parse(response));
		
		JSONArray result = jo.getJSONArray("result");
		
		return result;
	}
	
	/**
	 * Get Result Column Names.
	 * @param result
	 * @return
	 */
	public List getResultColumns(JSONArray result) {
		List columns = new ArrayList();
		if(!result.isEmpty()) {
			JSONObject row = (JSONObject)result.get(0);
			Iterator iterator = row.keySet().iterator();
			while(iterator.hasNext()) {
				columns.add(iterator.next().toString());
			}
		}
		return columns;
	}
	
	
}
