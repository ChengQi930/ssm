package cn.bizowner.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.math.NumberUtils;
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

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.ColumnSelect;
import cn.bizowner.model.Imp_User;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.Role;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.UpdateAssetMgrThreadParam;
import cn.bizowner.model.UserRole;
import cn.bizowner.service.AssetAccountService;
import cn.bizowner.service.ColumnService;
import cn.bizowner.service.RoleService;
import cn.bizowner.service.impl.UserServiceImpl;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.PinyYin4J;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/Role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	 
	
	//增加 时 会判断一下 该 roleName是否存在
	@RequestMapping(value = "/AddRole", method = RequestMethod.POST)
	public @ResponseBody String AddRole(String roleName,String remark)
	{
			String result =null;
			boolean flag = true;
			String  errMess = "";
			Dto outDto = new BaseDto();
			 
			try 
			{
				 		Role role = new Role();
				 		role.setRoleId(CommonUtils.getUUID());
				 		role.setRoleName(roleName);
				 		role.setRemark(remark);
				 		//pingyin4j的方法
				 		role.setRoleCode(PinyYin4J.getPinYinHeadChar(roleName)); 
				 		
				 		roleService.addRole(role);
					 
	    	} 
			catch (Exception e) 
			{
	    		flag = false;
				errMess = e.getMessage();
			}
			if(flag == true){
	    		outDto.put("flag", true);
	    	}else{
	    		outDto.put("flag", false);
	    		outDto.put("errmsg", errMess);
	    	}
	    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
	 		return result;
	}
	
	
	
	
	
	
	
	
		@RequestMapping(value = "/UpdateRoleInfo", method = RequestMethod.POST)
		public @ResponseBody String UpdateRoleInfo(Role role)
		{
				String result =null;
				boolean flag = true;
				String  errMess = "";
				Dto outDto = new BaseDto();
				 
				try 
				{ 
					 		roleService.updateRole(role);
						 
		    	} 
				catch (Exception e) 
				{
		    		flag = false;
					errMess = e.getMessage();
				}
				if(flag == true){
		    		outDto.put("flag", true);
		    	}else{
		    		outDto.put("flag", false);
		    		outDto.put("errmsg", errMess);
		    	}
		    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
		 		return result;
		}
	 
	
	
	
		@RequestMapping(value = "/DelRole", method = RequestMethod.POST)
		public @ResponseBody String DelRole(String roleId)
		{
				String result =null;
				boolean flag = true;
				String  errMess = "";
				Dto outDto = new BaseDto();
				 
				try 
				{ 
					 		roleService.delRole(roleId);
						 
		    	} 
				catch (Exception e) 
				{
		    		flag = false;
					errMess = e.getMessage();
				}
				if(flag == true){
		    		outDto.put("flag", true);
		    	}else{
		    		outDto.put("flag", false);
		    		outDto.put("errmsg", errMess);
		    	}
		    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
		 		return result;
		}
		
		
		
		
		
		@RequestMapping(value = "/GetRoleList", method = RequestMethod.GET)
		public @ResponseBody String GetRoleList(String roleName,@RequestParam(value="start", required=false,defaultValue="0")Integer start,
	    		@RequestParam(value="limit", required=false,defaultValue="0")Integer limit)
		{
				String result =null;
				boolean flag = true;
				String  errMess = "";
				Dto outDto = new BaseDto();
				
				Dto retDto = null;
				 
				try 
				{ 
					 		retDto = roleService.getRoleList(roleName,start,limit);
		    	} 
				catch (Exception e) 
				{
		    		flag = false;
					errMess = e.getMessage();
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
		
		
		
		
		
		@RequestMapping(value = "/GetRoleInfo", method = RequestMethod.GET)
		public @ResponseBody String GetRoleList(String roleId)
		{
				String result =null;
				boolean flag = true;
				String  errMess = "";
				Dto outDto = new BaseDto();
				 
				
				Role role = null;
				 
				try 
				{ 
						role = roleService.getRoleInfo(roleId);
		    	} 
				catch (Exception e) 
				{
		    		flag = false;
					errMess = e.getMessage();
				}
				if(flag == true)
				{
		    		outDto.put("flag", true);
		    		outDto.put("result", role);
		    	}
				else
		    	{
		    		outDto.put("flag", false);
		    		outDto.put("errmsg", errMess);
		    	}
		    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
		 		return result;
		}
		
		
		
		
		
		
		
		
		@RequestMapping(value = "/GetRoleInfoByUserId", method = RequestMethod.GET)
		public @ResponseBody String GetRoleInfoByUserId(HttpServletRequest request,String userId)
		{
				String result =null;
				boolean flag = true;
				String  errMess = "";
				Dto outDto = new BaseDto();
				  
				List<Role> listRole =null;
				 
				try 
				{  
						listRole = roleService.getRoleListByUserId(userId);
						 
		    	} 
				catch (Exception e) 
				{
		    		flag = false;
					errMess = e.getMessage();
				}
				if(flag == true)
				{
		    		outDto.put("flag", true);
		    		outDto.put("result", listRole);
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
