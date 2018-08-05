package cn.bizowner.service;

import java.util.List;

import cn.bizowner.model.RoleQx;
import cn.bizowner.model.RoleQxInfo; 

public interface RoleQxService {
	
	public List<RoleQxInfo> getEnableRoleQxList(String userId,String parentId) throws Exception;
	
	public List<RoleQxInfo> getRoleQxList(String roleId,String parentId) throws Exception;
		
	public void updateRoleQxList(final List<RoleQx> listRoleQx) throws Exception;
	
}
