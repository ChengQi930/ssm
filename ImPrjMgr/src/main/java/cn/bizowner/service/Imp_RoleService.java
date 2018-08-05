package cn.bizowner.service;

import java.util.List;

import cn.bizowner.model.Role;

public interface Imp_RoleService {
	//增加角色
	public int save (Role role) throws Exception;
	//查询所有角色
    public List<Role> find()throws Exception;
    //查询一个角色
    public Role findId (String roleid)throws Exception;
    
    //按roleName查询 
    public Role selectroleName(String roleName)throws Exception;
    
    //删除一个角色 
    public int delete(String roleid)throws Exception;
    
    //修改 角色
    public int update(Role role)throws Exception;
    
   //根据用户获取角色
    public List<Role> getUserByRole(String userId)throws Exception;
}
