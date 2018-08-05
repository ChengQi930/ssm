package cn.bizowner.service;

import java.util.List;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;
import cn.bizowner.model.PlanUser;
import cn.bizowner.model.Role;
public interface RoleService {
		
		public void addRole(Role role) throws Exception;
		
		public void updateRole(Role role) throws Exception;
		
		public void delRole(String roleId) throws Exception;
		
		public Dto getRoleList(String roleName,Integer start,Integer limit) throws Exception;
		
		public Role getRoleInfo(String roleId) throws Exception;
		 
		public List<Role> getRoleListByUserId(String userId) throws Exception;
}
