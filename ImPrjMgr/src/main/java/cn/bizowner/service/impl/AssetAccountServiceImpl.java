package cn.bizowner.service.impl;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.ColumnNameMapper;
import cn.bizowner.mapper.ColumnSelectMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.service.AssetAccountService;
import cn.bizowner.sqlite.CwAccountUtil;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.ProjectFileUtil;
import cn.bizowner.sqlite.SqliteHelper;
import cn.bizowner.sqlite.SwAccountUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.FileUtils;
import cn.bizowner.utils.Utils;



@Service("assetAccountService")
public class AssetAccountServiceImpl  implements AssetAccountService {
	
	
	@Autowired
	private ColumnSelectMapper columnSelectMapper;
	
	@Autowired
	private PlanUserMapper planUserMapper;
	
	
	public Dto getJqAccountAsset(String dbPath,String id,Integer type,String userId) throws Exception
	{
			
				Dto outDto = new BaseDto();
		
				List<JqAssetAccount> listJqAssetAccount = JqAccountUtil.getJqAccountAsset(dbPath, id,type,0,0,null);
				
				
				int iColumnType = 2;
				
				if(type == 1)
				{
					iColumnType = 2;
				}
				else
				{
					iColumnType = 3;
				}
				
				
				int count = columnSelectMapper.isSetColumnSelect(userId, iColumnType);;
				
				
				if(count == 0)
				{
						userId = "default";
				}
				
				List<Dto> tempListDto =  columnSelectMapper.getHeadInfo(userId, iColumnType);
				
				List<Dto> listDto = new ArrayList<Dto>();
				
				for(int i=0;i<tempListDto.size();i++)
				{
						Dto tempDto = tempListDto.get(i);
						
						String filedName = tempDto.getAsString("fieldName");
						String columnName = tempDto.getAsString("columnName");
						String flag = tempDto.getAsString("flag");
						
						Dto dto = new BaseDto();
					
						dto.put(filedName, columnName+","+flag);
						
						listDto.add(dto);
				}
				
				
				outDto.put("head", listDto);
				outDto.put("content", listJqAssetAccount);
				
				
				return outDto;
	}
	
	
	
	
	
	
	public Dto getSwAccountAsset(String dbPath,String userId,SwAccountQueryInfo swAccountQueryInfo) throws Exception
	{
			
				Dto outDto = new BaseDto();
		
				List<SwAssetAccount> listSwAssetAccount = SwAccountUtil.getSwAccountAsset(dbPath, 0, 0, swAccountQueryInfo);
				
				int count = columnSelectMapper.isSetColumnSelect(userId, 4);
				if(count == 0)
				{
						userId = "default";
				}
				
				List<Dto> tempListDto =  columnSelectMapper.getHeadInfo(userId, 4);
				
				List<Dto> listDto = new ArrayList<Dto>();
				
				for(int i=0;i<tempListDto.size();i++)
				{
						Dto tempDto = tempListDto.get(i);
						
						String filedName = tempDto.getAsString("fieldName");
						String columnName = tempDto.getAsString("columnName");
						String flag = tempDto.getAsString("flag");
						
						Dto dto = new BaseDto();
					
						dto.put(filedName, columnName+","+flag);
						
						listDto.add(dto);
				}
				outDto.put("head", listDto);
				outDto.put("content", listSwAssetAccount);
				return outDto;
	}
	
	
	
	
	
	
	
	
 
	public void UpdateJqAssetAccount(ProjectFileInfo projectFileInfo,final List<JqAssetAccount> listJqAssetAccount) throws Exception
	{
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
		String dbPath = Utils.dbPath+projectFileInfo.getProjectId()+".db";
		
		File file = new File(dbPath);
		if(!file.exists())
		{
			String tempDbPath = Utils.dbPath+"temp.db";
			
			FileUtils.copyFile(tempDbPath, dbPath);
		}
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);		
		
		
		
		ProjectFileUtil.insertProjectFileInfo(projectFileInfo, listSql, listObj);
		JqAccountUtil.addJqAssetAccount(listJqAssetAccount,listSql,listObj);
		
		 
		
		hAsset.executeSqlTrans(listSql, listObj);
		
		hAsset.destroyed();
	}
	
	
	
	
	
	
	
	
	public void UpdateSwAssetAccount(ProjectFileInfo projectFileInfo,final List<SwAssetAccount> listSwAssetAccount,Integer updateType) throws Exception
	{
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
		String dbPath = Utils.dbPath+projectFileInfo.getProjectId()+".db";
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		hAsset.setSqlTran();
		
		String id = ProjectFileUtil.getFileId(dbPath,Utils.AccountType_Sw);
		    
		 
		 
		if(id != null)
		{
			if(updateType == Utils.UpdateType_All)			//全部更新时需要先删除实物表信息
			{
				ProjectFileUtil.delProjectFileInfo(id,Utils.AccountType_Sw,listSql, listObj);
			}
		}
		 
		
		ProjectFileUtil.insertProjectFileInfo(projectFileInfo, listSql, listObj);
		
		
		for(int i=0;i<listSql.size();i++)
		{
			hAsset.transExecute(listSql.get(i), listObj.get(i));
		}
		
		
		
		for(int i=0;i<listSwAssetAccount.size();i++)
		{
				SwAssetAccount sw = listSwAssetAccount.get(i);
				
				String barcode = sw.getAssetBarcode();
				
				if(barcode == null || barcode.length()==0)
			 	{
					
					
					String selectSql = "select max(assetBarcode) as barcode from imp_sw_asset where assetBarcode like 'johe%'";
					
					List<Map<String, Object>> listMap = hAsset.transQuery(selectSql);
					
			 		barcode = SwAccountUtil.getNewBarcode(listMap);
			 		sw.setAssetBarcode(barcode);
			 	}
				
				
				listSql.clear();
				listObj.clear();
				
				SwAccountUtil.addSwAssetAccount(sw, listSql, listObj);
				
				hAsset.transExecute(listSql.get(0), listObj.get(0));
				
		}
		
		hAsset.commit();
		 
		hAsset.destroyed();
	}
	
	
	
	
	
	
	
	
	
	public Dto getSwAccountList(String dbPath,int start,int limit,SwAccountQueryInfo swAccountQueryInfo) throws Exception
	{
			
				Dto outDto = new BaseDto();
		
				List<SwAssetAccount> listSwAssetAccount = SwAccountUtil.getSwAccountAsset(dbPath, start, limit, swAccountQueryInfo);
				
				int count = SwAccountUtil.getSwAccountCount(dbPath, swAccountQueryInfo);
				
				outDto.put("data", listSwAssetAccount);
				outDto.put("count", count);
				
				return outDto;
	}
	
	
	
	
	
	
	public Dto getJqAccountList(String dbPath,String id,int start,int limit,JqAccountQueryInfo jqAccountQueryInfo) throws Exception
	{
			
				Dto outDto = new BaseDto();
		
				List<JqAssetAccount> listJqAssetAccount = JqAccountUtil.getJqAccountAsset(dbPath, id,0,start,limit,jqAccountQueryInfo);
				
				int count = JqAccountUtil.getJqAccountCount(dbPath, id, jqAccountQueryInfo);
				
				outDto.put("data", listJqAssetAccount);
				outDto.put("count", count);
				
				return outDto;
	}






	






	@Override
	public void UpdateCwAssetAccount(ProjectFileInfo projectFileInfo, List<CwAssetAccount> listCwAssetAccount)
			throws Exception {
		
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
		String dbPath = Utils.dbPath+projectFileInfo.getProjectId()+".db";
		
		File file = new File(dbPath);
		if(!file.exists())
		{
			String tempDbPath = Utils.dbPath+"temp.db";
			
			FileUtils.copyFile(tempDbPath, dbPath);
		}
		
		
		//String id = ProjectFileUtil.getFileId(dbPath,Utils.AccountType_Cw);
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		/*if(id != null)
		{
			ProjectFileUtil.delProjectFileInfo(id,Utils.AccountType_Cw,listSql, listObj);
		}*/
		
		
		String year = listCwAssetAccount.get(0).getYear();
		
		int count =  CwAccountUtil.getCwAccountCount(dbPath,null,year);
		
		if(count > 0)
		{
			CwAccountUtil.delCwInfo(dbPath, year, listSql, listObj);
		}
		
		
		
		ProjectFileUtil.insertProjectFileInfo(projectFileInfo, listSql, listObj);
		CwAccountUtil.insertCwAssetAccount(listCwAssetAccount, listSql, listObj);
		
		hAsset.executeSqlTrans(listSql, listObj);
		
		hAsset.destroyed();
		
	}
	
	
	
	
	
	
	
	public Dto getCwAccountList(String dbPath,String id,int start,int limit) throws Exception
	{
			
				Dto outDto = new BaseDto();
		
				List<CwAssetAccount> listCwAssetAccount = CwAccountUtil.getCwAccountAsset(dbPath,id,start,limit);
				
				int count = CwAccountUtil.getCwAccountCount(dbPath,id,null);
				
				outDto.put("data", listCwAssetAccount);
				outDto.put("count", count);
				
				return outDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public Dto getCwAccountAsset(String dbPath,String userId) throws Exception
	{
			
				Dto outDto = new BaseDto();
				
				
				String id = ProjectFileUtil.getLastestFileId(dbPath, 1);
				
		
				//List<JqAssetAccount> listJqAssetAccount = DbUtil.getJqAccountAsset(dbPath, id,type,0,0,null);
				
				
				List<CwAssetAccount> listCwAssetAccount = CwAccountUtil.getCwAccountAsset(dbPath,null,0,0);
				
				
				int count = columnSelectMapper.isSetColumnSelect(userId, 1);
				
				if(count == 0)
				{
						userId = "default";
				}
				
				List<Dto> tempListDto =  columnSelectMapper.getHeadInfo(userId, 1);
				
				List<Dto> listDto = new ArrayList<Dto>();
				
				for(int i=0;i<tempListDto.size();i++)
				{
						Dto tempDto = tempListDto.get(i);
						
						String filedName = tempDto.getAsString("fieldName");
						String columnName = tempDto.getAsString("columnName");
						String flag = tempDto.getAsString("flag");
						
						Dto dto = new BaseDto();
					
						dto.put(filedName, columnName+","+flag);
						
						listDto.add(dto);
				}
				
				
				outDto.put("head", listDto);
				outDto.put("content", listCwAssetAccount);
				
				
				return outDto;
	}


}
