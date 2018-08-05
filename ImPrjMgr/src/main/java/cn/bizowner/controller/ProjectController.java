package cn.bizowner.controller;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.service.ProjectService;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.DateCalculate;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/Project")
public class ProjectController {

	
	@Autowired
	private ProjectService projectService;
	
	
	@RequestMapping(value="/AddPro",method=RequestMethod.POST)  
    public @ResponseBody String AddPro(HttpServletRequest request,ProjectInfo projectInfo)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		try
		{			
					HttpSession httpSession = request.getSession();
					String userId = httpSession.getAttribute("userId").toString();
					
					projectInfo.setCreateUser(userId);
					projectInfo.setProId(CommonUtils.getUUID());
					projectInfo.setCreateTime(new Date());
					projectInfo.setProStatus(Utils.Project_Status_Main_Running);
					
					projectInfo.setProNo(DateCalculate.getproNo());
					
					projectService.addPro(projectInfo);
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
	
	
	
	
	
	
	
	
	@RequestMapping(value="/UpdatePro",method=RequestMethod.POST)  
    public @ResponseBody String UpdatePro(HttpServletRequest request,ProjectInfo projectInfo)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		try
		{			
					projectService.updatePro(projectInfo);
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
	
	
	
	
	
	
	@RequestMapping(value="/DelPro",method=RequestMethod.GET)  
    public @ResponseBody String DelPro(HttpServletRequest request,String proId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		try
		{			
					projectService.delPro(proId);
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
	
	
	
	
	
	@RequestMapping(value="/GetProList",method=RequestMethod.GET)  
    public @ResponseBody String GetProList(HttpServletRequest request,String proName,
    		@RequestParam(value="mainStatus", required=false,defaultValue="1")Integer mainStatus,
    		@RequestParam(value="subStatus", required=false,defaultValue="0")Integer subStatus,
    		@RequestParam(value="start", required=false,defaultValue="0")Integer start,
    		@RequestParam(value="limit", required=false,defaultValue="0")Integer limit)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto retDto = null;
		
		try
		{			
				retDto = 	projectService.getProList(mainStatus,subStatus, proName,start, limit);
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
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetProInfoById",method=RequestMethod.GET)  
    public @ResponseBody String GetProInfoById(HttpServletRequest request,String proId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		ProjectInfo projectInfo = null;
		
		Dto outDto = new BaseDto();
		
		try
		{			
				projectInfo = projectService.getProInfoById(proId);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", projectInfo);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping("/ChooseOnePro")  
    public @ResponseBody String ChooseOnePro(HttpServletRequest request,String proId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		try
		{			
						HttpSession httpSession = request.getSession();
						
						ProjectInfo projectInfo = projectService.getProjectInfoById(proId);
						
						httpSession.setAttribute("proId", proId);
						httpSession.setAttribute("proName", projectInfo.getProName());
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
	
	
	
	
	
	
	
	@RequestMapping("/IsHaveChoosePro")  
    public @ResponseBody String IsHaveChoosePro(HttpServletRequest request)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		Dto outDto = new BaseDto();
		
		
		try
		{			
						HttpSession httpSession = request.getSession();
						
						Object o = httpSession.getAttribute("proId");
						
						if(o == null)
						{
							flag = false;
						}
		}
		catch(Exception ex)
		{
			flag = false;
		}	
		
		outDto.put("flag", flag);
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
}
