package cn.bizowner.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.bizowner.model.IA_Users;
import cn.bizowner.model.Inventory_Accounts;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAssetAccount;

public interface Imp_InventoryService {
	
	//增加 Inventory_Accounts表
     public int insertIA(Inventory_Accounts IA,List<String> userSql) throws Exception;
     
    //查询  清单项目
     public List getimpqc(String dbpath) throws Exception;
     
    //删除项目清单
     public int delete(String dbpath,String qcid) throws Exception;
     
     //修改项目清单
     public int updateInventory(Inventory_Accounts IA,List<String> user) throws Exception;
     
   //下发项目清单
     public List<JqAssetAccount> getJqAssetAccount(String dbpath,String QcTaskId,String userid) throws Exception;
     
    //上传清查任务
     public int uploadInventory(SwAssetAccount sw,String dbpath) throws Exception;
}
