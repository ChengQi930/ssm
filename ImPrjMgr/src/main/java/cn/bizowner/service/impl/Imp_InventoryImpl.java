package cn.bizowner.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;

import cn.bizowner.mapper.UserMapper;
import cn.bizowner.model.IA_Users;
import cn.bizowner.model.Inventory_Accounts;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.UserInfo;
import cn.bizowner.model.imp_qc_task;
import cn.bizowner.service.Imp_InventoryService;
import cn.bizowner.sqlite.Inventory;
import cn.bizowner.sqlite.SqliteHelper;
import cn.bizowner.utils.Utils;

@Service
public class Imp_InventoryImpl implements Imp_InventoryService{

	
	@Autowired
	private UserMapper userMapper;
	

	@Override
	public int insertIA(Inventory_Accounts IA,List<String> userSql) throws Exception{
		
			int insertInventory = Inventory.insertInventory(IA,userSql);
			
			if(insertInventory == 1){
				return 1;
			}else{
				return 0;
			}
		
	}

	@Override
	public List<JqAssetAccount> getJqAssetAccount(String dbpath,String QcTaskId,String userid) throws Exception{
			List<JqAssetAccount> jqAssetAccount = Inventory.getJqAssetAccount(dbpath,QcTaskId,userid);
			return jqAssetAccount;
	}

	//查询清查项目
	@Override
	public List getimpqc(String attribute) throws Exception{
		try {
			List<imp_qc_task> listQc = Inventory.getimp_qc_task(attribute);
			
			for(int i=0;i<listQc.size();i++)
			{
					imp_qc_task task = listQc.get(i);
				
					List<IA_Users> listUsers = task.getIA_User();
					for(int j=0;j<listUsers.size();j++)
					{
						UserInfo userInfo = userMapper.getUserInfo(listUsers.get(j).getQc_user_id()) ;
						if(userInfo != null)
						{
							listUsers.get(j).setMc(userInfo.getMc());
						} 
					}
			}
			
			
			return listQc;
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public int delete(String dbpath, String taskid) throws Exception{
		
		int deleteInventory = Inventory.deleteInventory(dbpath, taskid);
		
		if(deleteInventory == 1){
		   return 1;	
		}else if(deleteInventory == 0){
			 return 0;	
		}else{
			return 0;	
		}
	}

	@Override
	public int updateInventory(Inventory_Accounts IA, List<String> user) throws Exception {
		int updateInventory = Inventory.updateInventory(IA, user);
		
		if(updateInventory == 1){
			   return 1;	
			}else{
				return 0;	
			}
	}

	@Override
	public int uploadInventory(SwAssetAccount sw, String dbpath) throws Exception {
		
		return Inventory.UploadInventoryTask(dbpath, sw);
	}

    
	
   
}
