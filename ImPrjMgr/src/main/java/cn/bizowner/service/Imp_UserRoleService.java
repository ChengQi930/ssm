package cn.bizowner.service;

import java.util.List;

import cn.bizowner.model.Imp_RoleQx;
import cn.bizowner.model.Imp_User;
import cn.bizowner.model.UserRole;

public interface Imp_UserRoleService {
	
	//角色资源  用户角色  表 查询撤销该角色 是否被用户所依赖！  
     public String RQ_select_UR(String userid);
     
     // 给用户 分配  角色  
     public int RoleDistributionUser(UserRole UserRole);
     
     //撤销 用户 的 角色 
     public int RoleUndoUser(UserRole UserRole);
     
   //查询原始用户权限的 userId 和  roleid
 	public List<UserRole> TheOriginal(String userid) throws Exception;
 	
 	//查询最新用户权限的 userId 和  roleid
 	public List<UserRole> TheLatest(UserRole Imp_UserRole) throws Exception;
 	
 	//删除所有用户的 角色 
 	public int roleundofunctionAll(String userid) throws Exception;
}
