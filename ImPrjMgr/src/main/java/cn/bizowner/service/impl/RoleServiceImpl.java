package cn.bizowner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto; 
import cn.bizowner.mapper.PlanMapper;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanTypeMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.mapper.ProjectMapper;
import cn.bizowner.mapper.RoleMapper;
import cn.bizowner.mapper.UserRoleMapper;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;
import cn.bizowner.model.PlanUser;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.Role;
import cn.bizowner.service.CommonService;
import cn.bizowner.service.PlanService;
import cn.bizowner.service.ProjectService;
import cn.bizowner.service.RoleService;
import cn.bizowner.service.TopicService;
import cn.bizowner.utils.JSONHelper;



@Service("roleService")
public class RoleServiceImpl implements RoleService {
 
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	 

	@Override
	public void addRole(Role role) throws Exception
	{
				int count = roleMapper.isRoleNameExist(role.getRoleName());
				
				if(count > 0)
				{
						throw new Exception("该角色名称已存在,请重新输入");
				}
				roleMapper.addRole(role);
	}



	@Override
	public void updateRole(Role role) throws Exception
	{
	
			int count = roleMapper.isRoleNameExist(role.getRoleName());
			
			if(count > 0)
			{
					throw new Exception("该角色名称已存在,请重新输入");
			}
		
			roleMapper.updateRole(role);
	}



	@Override
	public void delRole(String roleId) throws Exception
	{
			
				int count = userRoleMapper.isRoleHaveUsers(roleId);
				if(count > 0)
				{
						throw new Exception("该角色下面存在用户,暂不能删除");
				}
				
				roleMapper.delRole(roleId);
	}



	@Override
	public Dto getRoleList(String roleName,Integer start,Integer limit) throws Exception
	{
		
		
		Dto dto = new BaseDto();
		
		int count = roleMapper.getRoleCount(roleName);
		
		if(limit == 0)
		{ 
				limit = count ;
		}
		
		List<Role> listRole = roleMapper.getRoleList(roleName, start, limit);
		
		
		dto.put("list", listRole);
		dto.put("count", count);
		
		return dto;
		 
	}



	@Override
	public Role getRoleInfo(String roleId) throws Exception
	{
			Role role = roleMapper.getRoleInfo(roleId);
			 
			return role;
	}
	

	
	
	
	
	
	
	@Override
	public List<Role> getRoleListByUserId(String userId) throws Exception
	{
		
		
		Dto dto = new BaseDto();
		
		int count = roleMapper.getRoleCount(null);
		 
		int start=0;
		int limit=count;
		
		List<Role> listRole = roleMapper.getRoleList(null, start, limit);
		
		for(int i=0;i<listRole.size();i++)
		{
				Role role = listRole.get(i);
				
				int num = userRoleMapper.isUserHaveRole(userId, role.getRoleId());
				if(num == 0)
				{
					listRole.get(i).setHaveFlag(false);
				}
				else
				{
					listRole.get(i).setHaveFlag(true);	
				}
		}
		
		return listRole;
		 
	}
	
}
