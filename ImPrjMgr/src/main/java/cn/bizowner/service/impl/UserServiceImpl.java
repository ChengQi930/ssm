package cn.bizowner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.RoleMapper;
import cn.bizowner.mapper.UserDeptMapper;
import cn.bizowner.mapper.UserMapper;
import cn.bizowner.mapper.UserRoleMapper; 
import cn.bizowner.model.Role;
import cn.bizowner.model.UserDept;
import cn.bizowner.model.UserInfo;
import cn.bizowner.model.UserRole;
import cn.bizowner.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private  UserMapper  userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Autowired
	private UserDeptMapper userDeptMapper;
	
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	//分页
	@Override
	public Dto getUserList(String mc,Integer start,Integer limit) throws Exception{
		
		List<UserInfo> listUser =  userMapper.getUserList(mc,start, limit);
		
		for(int i=0;i<listUser.size();i++)
		{
					String userId = listUser.get(i).getUserId(); 
					
					List<Role> listRole = roleMapper.getRoleInfoByUserId(userId);
					 
					List<UserDept> listDept = userDeptMapper.getUserDeptByUserId(userId);
					   
					listUser.get(i).setRoleInfo(listRole);
					
					listUser.get(i).setDeptInfo(listDept);
				 	
		}
		
		int count = userMapper.getUserCount(mc);
		
		Dto dto = new BaseDto();
		dto.put("list", listUser);
		dto.put("count", count);
		 
		return dto;
		
	}

	@Override
	public UserInfo getUserInfo(String userId) throws Exception{
		
		UserInfo userInfo = userMapper.getUserInfo(userId);
		
		List<Role> listRole = roleMapper.getRoleInfoByUserId(userId);
		  
		List<UserDept> listUserDept = userDeptMapper.getUserDeptByUserId(userId);
		 
		
		userInfo.setDeptInfo(listUserDept);
		
		userInfo.setRoleInfo(listRole);
		
		return userInfo;
	}
 

	@Override
	public void addUser(UserInfo user) throws Exception{
		 
		
		userMapper.addUserInfo(user);
		
		return ;
	}

	@Override
	public void delUser(String userId) throws Exception{
		 
			userMapper.delUserInfo(userId);
			 
			userRoleMapper.delUserRole(userId);
			
			return ;
	}

	@Override
	public void updateUser(UserInfo userInfo,List<UserRole> listUserRole)throws Exception {
		   
		
			for(int i=0;i<listUserRole.size();i++)
			{
					Role role = roleMapper.getRoleInfo(listUserRole.get(i).getRoleId());
					if(role.getRoleName().equals("管理员") || role.getRoleName().equals("超级管理员"))
					{
								int iCount  = userRoleMapper.isRoleHaveUsers(role.getRoleId());
								if(iCount > 0)
								{
										throw new Exception(role.getRoleName()+"角色已经分配给了其它用户,该角色只能分配给唯一用户");
								}
					}
			}
		
		
		
			userMapper.updateUserInfo(userInfo);
			 
			userRoleMapper.delUserRole(userInfo.getUserId());
			
			for(int i=0;i<listUserRole.size();i++)
			{	
				userRoleMapper.addUserRole(listUserRole.get(i));
			}
			
			
			return;
	}

	@Override
	public void addUser(List<UserInfo> listUserInfo) throws Exception {
		 
		for(int i=0;i<listUserInfo.size();i++)
		{
			userMapper.addUserInfo(listUserInfo.get(i));
		} 
		
	}
   
}
