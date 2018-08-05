package cn.bizowner.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.utils.Utils;

public class CwAccountUtil {

	 
	public static void delCwInfo(String dbPath,String year,List<String> listSql,List<Object[]> listObj) throws Exception
	{	
		String selectSql = "select id,fileId from imp_cw_asset where year='"+year+"'";
		 
		
		List<String> listIds = new ArrayList<String>();
		List<String> listFileIds = new ArrayList<String>();
		
		SqliteHelper sqliteHelper = new SqliteHelper(dbPath);
		List<Map<String, Object>> listMap = sqliteHelper.executeQuery(selectSql);
		
		for(int i=0;i<listMap.size();i++)
		{
				String id = (String)listMap.get(i).get("id");
				String fileId = (String)listMap.get(i).get("fileId");
				listIds.add(id);
				listFileIds.add(fileId);
		}
		
		
		String delRelaSql  ="delete from imp_rela_cw_jq where cwId in (?)";
		Object[] delRelaObj = {listIds};
		
		listSql.add(delRelaSql);
		listObj.add(delRelaObj);
		
		String delChangeLogSql = "delete from imp_change_log where assetId in (?)";
		Object[] delChangeLogObj = {listIds};
		
		
		listSql.add(delChangeLogSql);
		listObj.add(delChangeLogObj);
		
		
		String delCwSql  = "delete from imp_cw_asset where year=?";
		Object[] delCwObj = {year};
		
		
		listSql.add(delCwSql);
		listObj.add(delCwObj);
		
		
		
		String delProFileSql  = "delete from imp_file_list where id=?";
		Object[] delProFileObj = {listFileIds.get(0)};
		
		
		listSql.add(delProFileSql);
		listObj.add(delProFileObj);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
			
	public static CwAssetAccount getCwAssetDetailById(String dbPath,String id) throws Exception
	{
			CwAssetAccount cwAssetAccount = new  CwAssetAccount();
			
			String selectSql = null;
			
			
			//财务久其对账
			selectSql = "select imp_cw_asset.*,imp_rela_cw_jq.id as relaId from imp_cw_asset left join imp_rela_cw_jq on imp_cw_asset.id=imp_rela_cw_jq.cwId "+
			" where imp_cw_asset.id='"+id+"'";
			
			
		
			SqliteHelper hAsset = new SqliteHelper(dbPath);
			
			List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
			
			if(listMainMap.size()==1)
			{
				
				Map<String, Object> mainMap = listMainMap.get(0);
				
				cwAssetAccount.setId((String)mainMap.get("id"));
				cwAssetAccount.setFileId((String)mainMap.get("fileId"));
				cwAssetAccount.setYear((String)mainMap.get("year"));
				cwAssetAccount.setMonth((String)mainMap.get("month"));
				cwAssetAccount.setDay((String)mainMap.get("day"));
				cwAssetAccount.setAccountNo((String)mainMap.get("accountNo"));
				cwAssetAccount.setRemark((String)mainMap.get("remark"));
				cwAssetAccount.setDebit((String)mainMap.get("debit"));
				cwAssetAccount.setLender((String)mainMap.get("lender"));
				cwAssetAccount.setDirection((String)mainMap.get("direction"));
				cwAssetAccount.setBalance((String)mainMap.get("balance"));
				
				String relaId = (String)mainMap.get("relaId");
				
				if(relaId == null)
				{
					cwAssetAccount.setRela(false);
				}
				else
				{
					cwAssetAccount.setRela(true);
				}
				
			}
			
			return cwAssetAccount;
	}
	
	
	
	
	public static void insertCwAssetAccount(String dbPath,List<CwAssetAccount> listCwAssetAccount) throws Exception
	{
		
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
		for(int i=0;i<listCwAssetAccount.size();i++)
		{
					
					CwAssetAccount cwAssetAccount = new CwAssetAccount();
					
					String insertSql = "insert into imp_cw_asset(id,fileId,year,month,day,accountNo,remark,debit,lender,direction,balance) values (?,?,?,?,?,?,?,?,?,?,?)";
		
					Object[] obj = {cwAssetAccount.getId(),cwAssetAccount.getFileId(),cwAssetAccount.getYear(),cwAssetAccount.getMonth(),cwAssetAccount.getDay(),cwAssetAccount.getAccountNo(),
					cwAssetAccount.getRemark(),cwAssetAccount.getDebit(),cwAssetAccount.getLender(),cwAssetAccount.getDirection(),cwAssetAccount.getBalance()};
		
					listSql.add(insertSql);
					listObj.add(obj);
		}
		
		DbUtil.insertSql(dbPath,listSql,listObj);
	}
	
	
	
	
	
	
	
	public static void insertCwAssetAccount(List<CwAssetAccount> listCwAssetAccount,List<String> listSql,List<Object[]> listObj) throws Exception
	{
	
		for(int i=0;i<listCwAssetAccount.size();i++)
		{
					
					CwAssetAccount cwAssetAccount = listCwAssetAccount.get(i);
					
					String insertSql = "insert into imp_cw_asset(id,fileId,year,month,day,accountNo,remark,debit,lender,direction,balance) values (?,?,?,?,?,?,?,?,?,?,?)";
		
					Object[] obj = {cwAssetAccount.getId(),cwAssetAccount.getFileId(),cwAssetAccount.getYear(),cwAssetAccount.getMonth(),cwAssetAccount.getDay(),cwAssetAccount.getAccountNo(),
					cwAssetAccount.getRemark(),cwAssetAccount.getDebit(),cwAssetAccount.getLender(),cwAssetAccount.getDirection(),cwAssetAccount.getBalance()};
		
					listSql.add(insertSql);
					listObj.add(obj);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<CwAssetAccount> getCwAccountAsset(String dbPath,String fileId,int start,int limit) throws Exception
	{
		List<CwAssetAccount> listJqAssetAccount = new ArrayList<CwAssetAccount>();
		
		String selectSql = null;
		
		
		String whereSql =" where 1=1";
		if(fileId != null)
		{
			whereSql+=" and fileId='"+fileId+"' ";
		}
		
		
		//财务久其对账
		selectSql = "select imp_cw_asset.*,imp_rela_cw_jq.id as relaId from imp_cw_asset left join imp_rela_cw_jq on imp_cw_asset.id=imp_rela_cw_jq.cwId "+whereSql;
		
		
		if(limit > 0)
		{
			selectSql +=" limit "+ start+","+limit;
		}
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		for(int i=0;i<listMainMap.size();i++)
		{
			
			Map<String, Object> mainMap = listMainMap.get(i);
			
			CwAssetAccount cwAssetAccount = new CwAssetAccount();
		 	
			cwAssetAccount.setId((String)mainMap.get("id"));
			cwAssetAccount.setFileId((String)mainMap.get("fileId"));
			cwAssetAccount.setYear((String)mainMap.get("year"));
			cwAssetAccount.setMonth((String)mainMap.get("month"));
			cwAssetAccount.setDay((String)mainMap.get("day"));
			cwAssetAccount.setAccountNo((String)mainMap.get("accountNo"));
			cwAssetAccount.setRemark((String)mainMap.get("remark"));
			cwAssetAccount.setDebit((String)mainMap.get("debit"));
			cwAssetAccount.setLender((String)mainMap.get("lender"));
			cwAssetAccount.setDirection((String)mainMap.get("direction"));
			cwAssetAccount.setBalance((String)mainMap.get("balance"));
			
			String relaId = (String)mainMap.get("relaId");
			
			if(relaId == null)
			{
				cwAssetAccount.setRela(false);
			}
			else
			{
				cwAssetAccount.setRela(true);
			}
			
			listJqAssetAccount.add(cwAssetAccount);
		}
		
		return listJqAssetAccount;
	}
	
	
	
	
	
	
	public static int getCwAccountCount(String dbPath,String fileId,String year) throws Exception
	{
		
		int count = 0;
		
		String selectSql = null;
		
		selectSql = "select count(*) from imp_cw_asset where 1=1";
		if(fileId != null)
		{	
			selectSql+=" and fileId='"+fileId+"'";
		}
		if(year != null)
		{	
			selectSql+=" and year='"+year+"'";
		}
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		if(listMainMap.size()==1)
		{
			count = Integer.parseInt(listMainMap.get(0).get("count(*)").toString());
		}
		
		return count;
	}
}
