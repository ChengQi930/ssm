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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dingtalk.openapi.helper.DeptHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.RoleMapper;
import cn.bizowner.mapper.UserDeptMapper;
import cn.bizowner.mapper.UserMapper;
import cn.bizowner.model.ColumnSelect;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.Imp_User;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;
import cn.bizowner.model.PlanUser;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.Role;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.UpdateAssetMgrThreadParam;
import cn.bizowner.model.UserDept;
import cn.bizowner.model.UserInfo;
import cn.bizowner.model.UserRole;
import cn.bizowner.service.AssetAccountService;
import cn.bizowner.service.CheckService;
import cn.bizowner.service.ColumnService;
import cn.bizowner.service.PlanService;
import cn.bizowner.service.ProjectService;
import cn.bizowner.service.RoleService;
import cn.bizowner.service.UserService;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ErrorCode;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/User")
public class UserController {
	
	
	@Autowired
	private RoleService roleService;
	
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Autowired
	private RoleMapper roleMapper;
	
	
	
	@Autowired
	private UserDeptMapper userDeptMapper;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private ProjectService projectService;
	
	
	
	
	@RequestMapping("/Test")
	public ModelAndView test()
	{
		
			ModelAndView result = new ModelAndView("user");
			return result;
	}
	
	 
	
	
	
	
	@RequestMapping(value="/SetValue")  
    public @ResponseBody String Test(HttpServletRequest request,String userId,String mc,String proId,
    		String proName,String jqRelaCwFileId,String jqRelaSwFileId) 
	{  

		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		try
		{			
					HttpSession httpSession = request.getSession();
										
					httpSession.setAttribute("userId", userId);
					
					httpSession.setAttribute("mc", mc);
					 
					httpSession.setAttribute("proId", proId);
					 
					//ProjectInfo projectInfo = projectService.getProjectInfoById(proId);
					
 
					httpSession.setAttribute("proName", proName);
 					 
					
					httpSession.setAttribute("jqRelaCwFileId", jqRelaCwFileId);
					
					httpSession.setAttribute("jqRelaSwFileId", jqRelaSwFileId);
 
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
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/SetUser")  
    public @ResponseBody String SetUser(HttpServletRequest request,String userId) 
	{  

		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		try
		{			 
					UserInfo userInfo = userMapper.getUserInfo(userId);
			
					HttpSession httpSession = request.getSession();
										
					httpSession.setAttribute("userId", userId);
					
					httpSession.setAttribute("mc", userInfo.getMc());
					 
					 
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
	
	
	
	
	
	
	@RequestMapping(value="/GetImpLeader",method=RequestMethod.GET)  
    public @ResponseBody String GetImpLeader(HttpServletRequest request,String node)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		List<Dto> listOutDto = new ArrayList<Dto>();
		
		try
		{			
				
			
				String deptId = null;
			
				if(node != null && node.equals("root"))
				{
					deptId = DeptHelper.getDeptIdByName("实施部");
				}
				else
				{
					deptId = node;
				}
				
				
				List<Dto> listDto = DeptHelper.getChildDeptsByDeptId(deptId);
				
				for(int i=0;i<listDto.size();i++)
				{
						//System.out.println(listDto.get(i).getAsString("id"));
						//System.out.println(listDto.get(i).getAsString("name"));
						
						Dto dto = new BaseDto();
						
						dto.put("id", listDto.get(i).getAsString("id"));
						dto.put("mc", listDto.get(i).getAsString("name"));
						dto.put("type", "1");
						
						listOutDto.add(dto);
				}
				
				
				List<DingDingUserInfo> listUserInfo= DeptHelper.getUserInfoByDeptId(deptId);
				for(int i=0;i<listUserInfo.size();i++)
				{
						Dto dto = new BaseDto();
						
						dto.put("id", listUserInfo.get(i).getUserId());
						dto.put("mc", listUserInfo.get(i).getMc());
						dto.put("type", "2");
						
						listOutDto.add(dto);
				}
			
				
					
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", listOutDto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetImpUsers",method=RequestMethod.GET)  
    public @ResponseBody String GetImpUsers(HttpServletRequest request)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		List<Dto> listOutDto = new ArrayList<Dto>();
		
		List<Dto> listUsers = new ArrayList<Dto>();
		
		
		try
		{			
				
			
				String rootDeptId = DeptHelper.getDeptIdByName("实施部");
			
				List<Dto> listDto = DeptHelper.getAllChildDeptsByDeptId(rootDeptId);
				
				for(int i=0;i<listDto.size();i++)
				{		
						String tempDeptId = listDto.get(i).getAsString("id");
						
						List<DingDingUserInfo> listUserInfo= DeptHelper.getUserInfoByDeptId(tempDeptId);
						for(int j=0;j<listUserInfo.size();j++)
						{
								Dto dto = new BaseDto();
								
								dto.put("id", listUserInfo.get(j).getUserId());
								dto.put("mc", listUserInfo.get(j).getMc());
								
								listUsers.add(dto);
						}
				}
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", listUsers);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetSaleUsers",method=RequestMethod.GET)  
    public @ResponseBody String GetSaleUsers(HttpServletRequest request,Plan plan,String node)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		List<Dto> listOutDto = new ArrayList<Dto>();
		
		try
		{			
				
			
				String deptId = null;
			
				if(node != null && node.equals("root"))
				{
					deptId = DeptHelper.getDeptIdByName("销售部");
				}
				else
				{
					deptId = node;
				}
				
				
				List<Dto> listDto = DeptHelper.getChildDeptsByDeptId(deptId);
				
				for(int i=0;i<listDto.size();i++)
				{ 
						Dto dto = new BaseDto();
						
						dto.put("id", listDto.get(i).getAsString("id"));
						dto.put("mc", listDto.get(i).getAsString("name"));
						dto.put("type", "1");
						
						listOutDto.add(dto);
				}
				
				
				List<DingDingUserInfo> listUserInfo= DeptHelper.getUserInfoByDeptId(deptId);
				for(int i=0;i<listUserInfo.size();i++)
				{
						Dto dto = new BaseDto();
						
						dto.put("id", listUserInfo.get(i).getUserId());
						dto.put("mc", listUserInfo.get(i).getMc());
						dto.put("type", "2");
						
						listOutDto.add(dto);
				}
			
				
					
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", listOutDto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetLoginInfo",method=RequestMethod.GET)  
    public @ResponseBody String GetLoginInfo(HttpServletRequest request)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		int errCode = 0;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = new BaseDto();
		
		
		
		try
		{			
					HttpSession httpSession = request.getSession();
					
					
					Object o  = httpSession.getAttribute("userId");
					if(o != null)
					{
						String userId = o.toString();
						
						System.out.println("GetLoginInfo-->userId="+userId);
						
						  
							UserInfo userInfo = userMapper.getUserInfo(userId);
							if((Boolean)userInfo.getEnableFlag() == true)
							{
								
									List<Role> listRole = roleMapper.getRoleInfoByUserId(userId);
									
									if(listRole.size()>0)
									{ 
										List<UserDept> listUserDept = userDeptMapper.getUserDeptByUserId(userId);
										
										List<String> listDeptName = new ArrayList<String>();
										for(int i=0;i<listUserDept.size();i++)
										{
											listDeptName.add(listUserDept.get(i).getDeptName());
										}
										
										
										List<String> listRoleName = new ArrayList<String>();
										List<String> listRoleCode = new ArrayList<String>();
										for(int i=0;i<listRole.size();i++)
										{
												String roleCode = listRole.get(i).getRoleCode();
												String roleName = listRole.get(i).getRoleName();
												
												listRoleName.add(roleName);
												listRoleCode.add(roleCode);
										}
										
										
										String roleName = String.join( ",",listRoleName);
										String roleCode = String.join( ",",listRoleCode);
										 
										dto.put("userId", userId);
										dto.put("mc", userInfo.getMc());
										dto.put("deptName",  String.join( ",",listDeptName));
										dto.put("roleName", String.join( ",",listRoleName));
										dto.put("roleCode", String.join( ",",listRoleCode));
										dto.put("proId", httpSession.getAttribute("proId"));
										dto.put("proName", httpSession.getAttribute("proName"));
										dto.put("jqRelaCwFileId", httpSession.getAttribute("jqRelaCwFileId"));
										dto.put("jqRelaSwFileId", httpSession.getAttribute("jqRelaSwFileId"));
									
										
										httpSession.setAttribute("mc",userInfo.getMc());
									}
									else
									{
											flag = false;
											errCode = ErrorCode.USER_NOROLE_ASSIGN;
											errMess = "当前用户未分配角色,请联系管理员";
									} 
									 
							}
							else
							{
									flag = false;
									errCode = ErrorCode.USER_FORBIDDEN;
									errMess = "当前用户被禁止登录,请联系管理员";
									
							}
						 
					}
					else
					{
								flag = false;
								errCode = ErrorCode.USER_NOLOGIN;
								errMess = "用户尚未登录";
					}
					
					
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errcode", errCode);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/Logout",method=RequestMethod.POST)
	public @ResponseBody String Logout(HttpSession session) 
	{
			String result =null;		
			
			Dto outDto = new BaseDto();
						
			boolean flag = true;
			
			String  errMess = "";
			
			try
			{
				session.invalidate();
			}
			catch(Exception ex)
			{
				String s = ex.getMessage();
				
				errMess = s;
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
	
	
	
	
	
	
	
	@RequestMapping(value="/GetCode",method=RequestMethod.GET)  
    public @ResponseBody String GetCode(HttpServletRequest request,HttpServletResponse response,String code) 
	{  
		        
		
		try
		{
			String userId= UserHelper.getUserId(code);
			
			
			
			System.out.println("GetCode-->userId="+userId);
	        
			if(userId != null)
			{
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("userId", userId);	
					
					
					UserInfo userInfo = userMapper.getUserInfo(userId);
					if(userInfo == null)
					{	
						UserInfo user = new UserInfo();
						user.setUserId(userId);
						userMapper.addUserInfo(user);
					}
			}
			
			//response.sendRedirect("http://192.168.1.68:9080/main.html");
			 
			//测试环境
			//response.sendRedirect("http://192.168.1.66:9514/mainManager/");
			  
			//正式环境
			response.sendRedirect("http://test.vaiwan.com:8081/mainManager/");
			
			//response.sendRedirect("http://liujie.vaiwan.com:8081/main.html/");
			
			
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}		
		return null;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		//查询所有员工 分页查询 
		@RequestMapping(value = "/GetUserList", method = RequestMethod.GET)
		public @ResponseBody String GetUserList(String mc,Integer start,Integer limit){
			
			String result =null;
			boolean flag = true;
			String  errMess = "";
			Dto outDto = new BaseDto();
			
			Dto retDto = null;
			
			try 
			{
				retDto = userService.getUserList(mc,start, limit);
			} 
			catch (Exception e) 
			{
				flag = false;
				errMess = e.getMessage();
			}
			if(flag == true)
			{	
				outDto.put("flag",true);
				outDto.put("result",retDto);
			}
			else
			{
				outDto.put("flag",false);
				outDto.put("errmsg",errMess);
			}
			result = JSONHelper.encodeObject2JsonWithNull(outDto);
			return result;
		}
		
			//按id查询一个员工 
		@RequestMapping(value = "/GetUserInfo" ,method = RequestMethod.GET)
		public @ResponseBody String GetUserInfo(String userId){
				
				String result =null;
				
				boolean flag = true;
				String  errMess = "";
				
				Dto outDto = new BaseDto();
				
				UserInfo userInfo = null;
				
				try 
				{
					userInfo = userService.getUserInfo(userId);
				} 
				catch (Exception e) 
				{
					flag = false;
					errMess = e.getMessage();
				}
				if(flag == true)
				{
					outDto.put("flag", true);
					outDto.put("result",userInfo);
				}
				else
				{
					outDto.put("flag", false);
					outDto.put("errmsg",errMess);
				}
				result = JSONHelper.encodeObject2JsonWithNull(outDto);
				return result;
			}
		
		
			
 		    @RequestMapping(value = "/DelUser",method = RequestMethod.POST)
		    public @ResponseBody String DelUser(String userId){
		    	
		    	String result =null;
				
		    	boolean flag = true;
				
		    	String  errMess = "";
				
		    	Dto outDto = new BaseDto();
		    	 
				try
				{
					userService.delUser(userId);
				}
				catch(Exception ex)
				{
						String s = ex.getMessage();
						errMess = s;
				}
		    	 
		    	if(flag == true)
		    	{
	   			  		outDto.put("flag", true);
	   		    }
		    	else
		    	{
			   			outDto.put("flag", false);
			   			outDto.put("errmsg" ,errMess);
	   		    }
		    	 
		    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
		 		
		    	return result;
		    }
			
		
		    
		    
		  @RequestMapping(value = "/UpdateUser" ,method = RequestMethod.POST)
	      public @ResponseBody String UpdateUser(UserInfo userInfo, String roleIds){
		    	String result =null;
				boolean flag = true;
				String  errMess = "";
				Dto outDto = new BaseDto();
				 
				try 
				{
					
						
						List<UserRole> listUserRole = new ArrayList<UserRole>();
						
						JSONArray  jaRoleIds = (JSONArray)JSONArray.parse(roleIds); 
						
						for(Object obj : jaRoleIds)
						{
								String str = obj.toString(); 
								
								UserRole userRole = new UserRole();
								
								userRole.setId(CommonUtils.getUUID());
								userRole.setUserId(userInfo.getUserId());
								userRole.setRoleId(str);
						
								listUserRole.add(userRole);
						}
						
						 
						if(userInfo.getEnableFlag() != null && userInfo.getEnableFlag().toString().equalsIgnoreCase("FALSE"))
						{
							userInfo.setEnableFlag("N");
						}
						else
						{ 
							userInfo.setEnableFlag("Y");
						}
						
					
			    	   userService.updateUser(userInfo,listUserRole);
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
		  
		  
		   
	
		  
		  
		  
	  @RequestMapping(value="/GetUnAddUser",method=RequestMethod.GET)  
	    public @ResponseBody String GetUnAddUser(HttpServletRequest request,String node)
		{  
			  	
			String result =null;
			
			boolean flag = true;
			
			String  errMess = "";
			
			Dto outDto = new BaseDto();
			
			List<Dto> listOutDto = new ArrayList<Dto>();
			
			try
			{			
					
				
					String deptId = null;
				
					if(node != null && node.equals("root"))
					{
						deptId = "1";
					}
					else
					{
						deptId = node;
					}
					
					
					List<Dto> listDto = DeptHelper.getChildDeptsByDeptId(deptId);
					
					for(int i=0;i<listDto.size();i++)
					{
							//System.out.println(listDto.get(i).getAsString("id"));
							//System.out.println(listDto.get(i).getAsString("name"));
							
							Dto dto = new BaseDto();
							
							dto.put("id", listDto.get(i).getAsString("id"));
							dto.put("mc", listDto.get(i).getAsString("name"));
							dto.put("type", "1");
							
							listOutDto.add(dto);
					}
					
					
					List<DingDingUserInfo> listUserInfo= DeptHelper.getUserInfoByDeptId(deptId);
					for(int i=0;i<listUserInfo.size();i++)
					{
						
						int count = userMapper.isUserIdExist(listUserInfo.get(i).getUserId());
						
						if(count == 0)
						{
								Dto dto = new BaseDto(); 
								dto.put("id", listUserInfo.get(i).getUserId());
								dto.put("mc", listUserInfo.get(i).getMc());
								dto.put("type", "2");
								
								listOutDto.add(dto);
						}
						
					}
				
					
						
			}
			catch(Exception ex)
			{
				flag = false;
				errMess = ex.getMessage();
			}	
			
			if(flag == true)
			{
				outDto.put("flag", true);
				outDto.put("result", listOutDto);
			}
			else
			{
				outDto.put("flag", false);
				outDto.put("errmsg", errMess);
			}			
			
			
			result = JSONHelper.encodeObject2JsonWithNull(outDto);
			
			return result;
			
	    }	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  @RequestMapping(value = "/AddUser" ,method = RequestMethod.POST)
      public @ResponseBody String AddUser(String userId)
	  {
	    	String result =null;
			boolean flag = true;
			String  errMess = "";
			Dto outDto = new BaseDto();
			 
			try 
			{ 
					
					List<UserInfo> listUserInfo = new ArrayList<UserInfo>();
					
					JSONArray  jaUserIds = (JSONArray)JSONArray.parse(userId); 
					
					for(Object obj : jaUserIds)
					{
							String str = obj.toString(); 
							
							
							UserInfo userInfo = new UserInfo();
							
							userInfo.setUserId(str);
							userInfo.setEnableFlag("Y"); 
							userInfo.setMc(UserHelper.getUserMcByUserId(str));
							 
							listUserInfo.add(userInfo);
							
					}
					  
		    	   
		    	   userService.addUser(listUserInfo);
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

		  
	
}
