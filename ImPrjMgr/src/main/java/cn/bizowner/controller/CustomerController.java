package cn.bizowner.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.crm.env.Env;
import cn.bizowner.crm.helper.CrmHelper;
import cn.bizowner.dingtalk.openapi.helper.DeptHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.CrmMapper;
import cn.bizowner.model.ColumnSelect;
import cn.bizowner.model.CrmInfo;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;
import cn.bizowner.model.PlanUser;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.UpdateAssetMgrThreadParam;
import cn.bizowner.service.AssetAccountService;
import cn.bizowner.service.CheckService;
import cn.bizowner.service.ColumnService;
import cn.bizowner.service.PlanService;
import cn.bizowner.service.ProjectService;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ErrorCode;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/Customer")
public class CustomerController {

	
	
	@Autowired
	private CrmMapper crmMapper;
	
	
	@RequestMapping(value="/GetCustomerList",method=RequestMethod.GET)  
    public @ResponseBody String GetImpUsers(HttpServletRequest request,
    		@RequestParam(value="customerName", required=false,defaultValue="")String customerName,
    		@RequestParam(value="billCity", required=false,defaultValue="")String billCity,
    		@RequestParam(value="start", required=false,defaultValue="0")Integer start,
    		@RequestParam(value="limit", required=false,defaultValue="0")Integer limit)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		int errCode = ErrorCode.CRM_GET_FAIL;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto retDto = new BaseDto();
		
		
		
		
		try
		{	
			
			
			String userId = null;
			
			HttpSession httpSession = request.getSession();
		
			userId = httpSession.getAttribute("userId").toString();
			
			CrmInfo crmInfo = crmMapper.getCrmInfo(userId);
			if(crmInfo == null)
			{
					errCode = ErrorCode.CRM_EMPTY_INFO;
					throw new Exception("请输入CRM账号");
			}
			
			String crmUserName = crmInfo.getCrmUserName();
			
			String crmUserKey = crmInfo.getCrmUserKey();
					
			
			
			CrmHelper crmHelper = new 	CrmHelper();
			
			String sessionId = crmHelper.doLogin(crmUserName, crmUserKey);
			
			
			
			String querySql  = "SELECT accountname,bill_city FROM Accounts ";
			
			String condSql = "";
			condSql+="where accountname like '%"+customerName+"%'";
			condSql+="and bill_city like '%"+billCity+"%'";
			
			
			querySql+=condSql +" order by createdtime desc limit "+start+","+limit;
			
			
			
			
			String countSql = "select count(*) from Accounts "+condSql;
			
			
			int count = 0;
			
			JSONArray countJa = crmHelper.doQuery(countSql,sessionId);
			
			if(countJa != null)
			{
				count = Integer.parseInt(countJa.getJSONObject(0).getString("count"));
			}
			
			JSONArray ja = crmHelper.doQuery(querySql,sessionId);		
			
			
			
			
			
			
			List<Dto> listDto = new ArrayList<Dto>();
			for(int i=0;i<ja.size();i++)
			{
						Dto dto = new BaseDto();
						
						dto.put("customerName", ja.getJSONObject(i).getString("accountname"));
						dto.put("billCity", ja.getJSONObject(i).getString("bill_city"));
						
						
						listDto.add(dto);
			}
			
			
			
			retDto.put("count", count);
			retDto.put("data", listDto);
			

		}
		catch(Exception ex)
		{
 			errMess = ex.getMessage();
 			
 			
 			
			if(errMess != null && errMess.equals("INVALID_AUTH_TOKEN"))
			{
						errCode = ErrorCode.CRM_INVALID_USERNAME;
						errMess = "用户名错误";
			}
			else if(errMess != null && errMess.equals("INVALID_USER_CREDENTIALS"))
			{
						errCode = ErrorCode.CRM_INVALID_PASSWORD;
						errMess = "密码错误";
			}
			else
			{
						
			}
			
			
			flag = false;
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", retDto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
			outDto.put("errcode", errCode);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	@RequestMapping(value="/Login",method=RequestMethod.POST)  
    public @ResponseBody String GetImpUsers(HttpServletRequest request,
    		String crmUserName,
    		String crmUserKey)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		int errCode = ErrorCode.CRM_LOG_FAIL;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		
		try
		{	
			
			HttpSession httpSession = request.getSession();
			String userId = httpSession.getAttribute("userId").toString();
			
			
			CrmHelper crmHelper = new 	CrmHelper();
			
			String sessionId = crmHelper.doLogin(crmUserName, crmUserKey);
			
			
			CrmInfo crmInfo = new CrmInfo();
			crmInfo.setUserId(userId);
			crmInfo.setCrmUserName(crmUserName);
			crmInfo.setCrmUserKey(crmUserKey);
			crmMapper.delCrmInfo(userId);
			crmMapper.addCrmInfo(crmInfo);
			
			
		}
		catch(Exception ex)
		{
 			errMess = ex.getMessage();
 			
 			
			if(errMess != null && errMess.equals("INVALID_AUTH_TOKEN"))
			{
						errCode = ErrorCode.CRM_INVALID_USERNAME;
						errMess = "用户名错误";
			}
			else if(errMess != null && errMess.equals("INVALID_USER_CREDENTIALS"))
			{
						errCode = ErrorCode.CRM_INVALID_PASSWORD;
						errMess = "密码错误";
			}
			else
			{
						
			}
			
			
			flag = false;
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
			outDto.put("errcode", errCode);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
}
