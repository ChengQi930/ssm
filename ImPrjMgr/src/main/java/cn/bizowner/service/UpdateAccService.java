package cn.bizowner.service;

import java.util.List;

import cn.bizowner.model.ChangeLog;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAssetAccount;


public interface UpdateAccService {
	
	public void updateRecord(String proId,String userId,String mc,Integer accountType,String id,String columnName,String newValue) throws Exception;
	
	
	public void addJqAsset(String proId,String userId,String mc,Integer accountType,JqAssetAccount jqAssetAccount) throws Exception;

	
	public void addSwAsset(String proId,String userId,String mc,SwAssetAccount swAssetAccount) throws Exception;
	
	public List<JqAssetAccount> splitJqAsset(String proId,String userId,String mc,String jqFileId,Integer accountType,String id,Integer sl) throws Exception;
}
