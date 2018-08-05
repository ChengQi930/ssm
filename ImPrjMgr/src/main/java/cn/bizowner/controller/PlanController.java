package cn.bizowner.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.PlanTypeMapper;
import cn.bizowner.mapper.ProjectMapper;
import cn.bizowner.model.ColumnSelect;
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
import cn.bizowner.service.ColumnService;
import cn.bizowner.service.PlanService;
import cn.bizowner.service.ProjectService;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/Plan")
public class PlanController {

	
	@Autowired
	private PlanService planService;
	
	@Autowired
	private PlanTypeMapper planTypeMapper;
	
	
	@Autowired
	private ProjectMapper projectMapper;
	
	
	@RequestMapping("/AddPlan")  
    public @ResponseBody String AddPlan(HttpServletRequest request,Plan plan)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		try
		{			
					String planId = CommonUtils.getUUID();
					
					List<PlanUser> listPlanUser = new ArrayList<PlanUser>();
			
					if(plan.getImpUsers() != null)
					{
						JSONArray  jaDetail = (JSONArray)JSONArray.parse(plan.getImpUsers().toString()); 			
						for(Object obj : jaDetail)
						{
								String str = obj.toString();
								
								PlanUser planUser = new PlanUser();
								planUser.setId(CommonUtils.getUUID());
								planUser.setPlanId(planId);
								planUser.setUserId(str);

								listPlanUser.add(planUser);
								
						}
					}
					
					//plan.setStartDate(CommonUtils.getCurrDate());
					plan.setPlanId(planId);
					plan.setPlanStatus(Utils.Plan_Status_New);
					
					
					
					String typeName = planTypeMapper.getTypeNameByTypeValue(plan.getType());
					
					/*HttpSession httpSession = request.getSession();
					String proName = httpSession.getAttribute("proName").toString();*/
					
					ProjectInfo projectInfo = projectMapper.getProjectInfoById(plan.getProId());
					
					
					
					String topicName = projectInfo.getProName()+"_"+typeName+"_"+planId;
					
					planService.addPlan(plan, listPlanUser,topicName);
					
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping("/UpdatePlan")  
    public @ResponseBody String UpdatePlan(HttpServletRequest request,Plan plan)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		try
		{			
			
					String planId = plan.getPlanId();
					
					List<PlanUser> listPlanUser = new ArrayList<PlanUser>();
			
					JSONArray  jaDetail = (JSONArray)JSONArray.parse(plan.getImpUsers().toString()); 			
					for(Object obj : jaDetail)
					{
							String str = obj.toString();
							
							PlanUser planUser = new PlanUser();
							planUser.setId(CommonUtils.getUUID());
							planUser.setPlanId(planId);
							planUser.setUserId(str);

							listPlanUser.add(planUser);
					}
					
					
					planService.updatePlan(plan, listPlanUser);
					
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	@RequestMapping("/DelPlan")  
    public @ResponseBody String DelPlan(HttpServletRequest request,String planId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		try
		{			
					planService.delPlan(planId);
					
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping("/GetPlanList")  
    public @ResponseBody String GetProList(HttpServletRequest request,@RequestParam(value="start", required=false,defaultValue="0")Integer start,
    		@RequestParam(value="limit", required=false,defaultValue="0")Integer limit,PlanQueryInfo planQueryInfo)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		Dto retDto = null;
		
		try
		{			
			
			retDto = planService.getPlanList(planQueryInfo, start, limit);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
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
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	@RequestMapping("/GetPlanInfoById")  
    public @ResponseBody String GetPlanInfoById(String planId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		Plan plan = null;
		
		try
		{		
				plan = planService.getPlanInfoById(planId);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", plan);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping("/GetPlanTypeList")  
    public @ResponseBody String GetPlanTypeList()
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		List<PlanType> listPlanType = null;
		
		
		try
		{			
			
				listPlanType = planService.getPlanTypeList();
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", listPlanType);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
}
