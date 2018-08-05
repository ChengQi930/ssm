package cn.bizowner.service;
import java.util.List;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.SwAssetAccount;

public interface AssetAccountService {
			
	public void UpdateJqAssetAccount(ProjectFileInfo projectFileInfo,final List<JqAssetAccount> listJqAssetAccount) throws Exception;

	public void UpdateCwAssetAccount(ProjectFileInfo projectFileInfo,final List<CwAssetAccount> listCwAssetAccount) throws Exception;
	
	public void UpdateSwAssetAccount(ProjectFileInfo projectFileInfo,final List<SwAssetAccount> listSwAssetAccount,Integer updateType) throws Exception;
	
	
	
		
	public Dto getJqAccountList(String dbPath,String id,int start,int limit,JqAccountQueryInfo jqAccountQueryInfo) throws Exception;

	public Dto getCwAccountList(String dbPath,String id,int start,int limit) throws Exception;
	
	public Dto getSwAccountList(String dbPath,int start,int limit,SwAccountQueryInfo swAccountQueryInfo) throws Exception;
	
	
	
	public Dto getJqAccountAsset(String dbPath,String id,Integer type,String userId) throws Exception;
	
	public Dto getSwAccountAsset(String dbPath,String userId,SwAccountQueryInfo swAccountQueryInfo) throws Exception;

	public Dto getCwAccountAsset(String dbPath,String userId) throws Exception;
	
	
}
