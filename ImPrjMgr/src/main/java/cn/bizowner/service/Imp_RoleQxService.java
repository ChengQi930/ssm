package cn.bizowner.service;

import java.util.List;

import cn.bizowner.model.Imp_RoleQx;

public interface Imp_RoleQxService {
	
	
	//查询原始角色权限的 id 和  remak
	public List<Imp_RoleQx> TheOriginal(String roleid) throws Exception;
	
	//查询最新角色权限的 id 和  remak
	public List<Imp_RoleQx> TheLatest(Imp_RoleQx roleqx) throws Exception;
	
	//删除所有角色的 资源
	public int roleundofunctionAll(String roleid) throws Exception;
	
	//给角色 分配 资源
   public int roleqx(Imp_RoleQx roleqx);
   
   //撤销 角色 资源
   public int roleundofunction(Imp_RoleQx roleqx);
}
