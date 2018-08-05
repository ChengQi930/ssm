
package cn.bizowner.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.RoleMapper;
import cn.bizowner.model.Role;
import cn.bizowner.model.RoleQx;
import cn.bizowner.model.RoleQxInfo;
import cn.bizowner.service.RoleQxService;
import cn.bizowner.utils.JSONHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/RoleQx")
public class RoleQxController {

	@Autowired
	private RoleQxService roleQxService;
	 
	
	
	//角色权限配置   一级一级展开
	@RequestMapping(value="/GetRoleQxList",method=RequestMethod.GET)
	public @ResponseBody String GetRoleQxList(String roleId,String node) 
	{
		
			Dto outDto = new BaseDto();
			
			boolean flag = true;
			
			String errMes = "";
			
			List<RoleQxInfo> listRoleQxInfo = null;
		
			try
			{
				
				String parentId = null;
				
				if(node != null && node.equals("root")==false)
				{
					parentId = node;
				}
				
				listRoleQxInfo = roleQxService.getRoleQxList(roleId,parentId);
	
			}
			catch(Exception ex)
			{
				flag = false;
				
				errMes = ex.getMessage();
			}
			
			
			if(flag==true)
			{
				outDto.put("flag", true);
				outDto.put("result", listRoleQxInfo);
			}
			else
			{
				outDto.put("flag", false);
				outDto.put("error", errMes);
			}
			
			
			return  JSONHelper.encodeObject2JsonWithNull(outDto);

	}
	
	
	@RequestMapping(value="/UpdateRoleQxList",method=RequestMethod.POST)
	public @ResponseBody String UpdateRoleQxList(@RequestParam("main") String main,@RequestParam("detail") String detail) 
	{
		
			Dto outDto = new BaseDto();
					
			boolean flag = true;
			
			String errMes = "";
		
			try
			{
				
				JSONObject joMain = (JSONObject)JSONObject.parse(main);
				JSONArray  jaDetail = (JSONArray)JSONArray.parse(detail);
				
				String roleId = joMain.getString("roleId");
				
				List<RoleQx> listRoleQx = new ArrayList<RoleQx>();
				
				for(Object obj : jaDetail)
				{
						JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
						
						String funcId = jo.getString("funcId");
						boolean enableFlag = jo.getBooleanValue("enableFlag");
						if(enableFlag == false)		continue;
						
						
						RoleQx roleQx = new RoleQx();
						roleQx.setRoleId(roleId);
						roleQx.setFuncId(funcId);
						
						/*if(enableFlag == true)
						{
							roleQx.setEnableFlag("Y");
						}
						else
						{
							roleQx.setEnableFlag("N");
						}*/
						
						roleQx.setEnableFlag("Y");
						listRoleQx.add(roleQx);
				}
				
				
				
				roleQxService.updateRoleQxList(listRoleQx);
	
				
			}
			catch(Exception ex)
			{
				flag = false;
				
				errMes = ex.getMessage();
			}
			
			
			if(flag==true)
			{
				outDto.put("flag", true);
			}
			else
			{
				outDto.put("flag", false);
				outDto.put("error", errMes);
			}
			
			
			return  JSONHelper.encodeObject2JsonWithNull(outDto);

	}
	
	
	
	//获取赋予权限的资源   登录时进行调用
	@RequestMapping(value="/GetEnableRoleQxList",method=RequestMethod.GET)
	public @ResponseBody String GetRoleQxList(HttpServletRequest request,String funcId) 
	{
			Dto outDto = new BaseDto();
			
			List<RoleQxInfo> listRoleQxInfo = null;
		
			boolean flag = true;
			
			String errMes = "";
		
			try
			{
				
				
				String userId = request.getSession().getAttribute("userId").toString();
				 
				listRoleQxInfo = roleQxService.getEnableRoleQxList(userId, funcId);
	
			}
			catch(Exception ex)
			{
				flag = false;
				
				errMes = ex.getMessage();
			}
			
			
			if(flag==true)
			{
				outDto.put("flag", true);
				outDto.put("result", listRoleQxInfo);
			}
			else
			{
				outDto.put("flag", false);
				outDto.put("error", errMes);
			}
			
			
			return  JSONHelper.encodeObject2JsonWithNull(outDto);
	}
}
