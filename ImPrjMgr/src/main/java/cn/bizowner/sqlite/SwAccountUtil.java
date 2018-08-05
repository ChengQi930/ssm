package cn.bizowner.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.utils.CommonUtils;

public class SwAccountUtil {

	 
	
	public static void addSwAssetAccount(List<SwAssetAccount> listSwAssetAccount,List<String> listSql,List<Object[]> listObj) throws Exception
	{
			for(int i=0;i<listSwAssetAccount.size();i++)
			{
				
				SwAssetAccount swAssetAccount = listSwAssetAccount.get(i);
									
				String insertSql = "insert into imp_sw_asset(id,assetBarcode,assetName,assetClass,sl,get_date,assetPp,assetModel,useDept,useUser,saveAddress,money,remark) values"
						+"(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				
				Object[] obj = {swAssetAccount.getId(),swAssetAccount.getAssetBarcode(),swAssetAccount.getAssetName(),swAssetAccount.getAssetClass(),swAssetAccount.getSl(),CommonUtils.convertDateToString(swAssetAccount.getGetDate()),
						swAssetAccount.getAssetPp(),swAssetAccount.getAssetModel(),swAssetAccount.getUseDept(),swAssetAccount.getUseUser(),
						swAssetAccount.getSaveAddress(),swAssetAccount.getMoney(),swAssetAccount.getRemark()};
			
				listSql.add(insertSql);
				listObj.add(obj);
			}
	}
	
	
	
	
	
	
	
	public static void addSwAssetAccount( SwAssetAccount  swAssetAccount,List<String> listSql,List<Object[]> listObj) throws Exception
	{
			 
				String insertSql = "insert into imp_sw_asset(id,assetBarcode,assetName,assetClass,sl,get_date,assetPp,assetModel,useDept,useUser,saveAddress,money,remark) values"
						+"(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				
				Object[] obj = {swAssetAccount.getId(),swAssetAccount.getAssetBarcode(),swAssetAccount.getAssetName(),swAssetAccount.getAssetClass(),swAssetAccount.getSl(),CommonUtils.convertDateToString(swAssetAccount.getGetDate()),
						swAssetAccount.getAssetPp(),swAssetAccount.getAssetModel(),swAssetAccount.getUseDept(),swAssetAccount.getUseUser(),
						swAssetAccount.getSaveAddress(),swAssetAccount.getMoney(),swAssetAccount.getRemark()};
			
				listSql.add(insertSql);
				listObj.add(obj); 
	}
	
	
	
	
			
	public static SwAssetAccount getSwAssetDetailById(String dbPath,String id) throws Exception
	{
		SwAssetAccount swAssetAccount = new SwAssetAccount();
		
		
		String selectSql = null;
		
		
		selectSql = "select imp_sw_asset.*,imp_rela_jq_sw.id as relaId from imp_sw_asset left join imp_rela_jq_sw on imp_sw_asset.id=imp_rela_jq_sw.swId"+
		"  where imp_sw_asset.id='"+id+"'";
		

		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		if(listMainMap.size()==1)
		{
			
			Map<String, Object> mainMap = listMainMap.get(0);
						 	
			swAssetAccount.setId((String)mainMap.get("id"));
			swAssetAccount.setTaskId((String)mainMap.get("taskId"));
			swAssetAccount.setAssetName((String)mainMap.get("assetName"));
			swAssetAccount.setSl(NumberUtils.toInt(mainMap.get("sl").toString(),0));
			swAssetAccount.setGetDate(CommonUtils.convertStringToDate((String)mainMap.get("get_date")));
			swAssetAccount.setAssetPp((String)mainMap.get("assetPp"));
			swAssetAccount.setAssetModel((String)mainMap.get("assetModel"));
			swAssetAccount.setUseDept((String)mainMap.get("useDept"));
			swAssetAccount.setUseUser((String)mainMap.get("useUser"));
			swAssetAccount.setSaveAddress((String)mainMap.get("saveAddress"));
			
			
			String relaId = (String)mainMap.get("relaId");
			
			if(relaId == null)
			{
				swAssetAccount.setRela(false);
			}
			else
			{
				swAssetAccount.setRela(true);
			}
			
		}
		
		return swAssetAccount;
	}
	
	
	
	
	public static List<SwAssetAccount> getSwAccountAsset(String dbPath,int start,int limit,SwAccountQueryInfo swAccountQueryInfo) throws Exception
	{
		List<SwAssetAccount> listSwAssetAccount = new ArrayList<SwAssetAccount>();
		
		
		String selectSql = null;
		
		
		String whereSql =" where 1=1";
		
		if(swAccountQueryInfo != null)
		{
			if(swAccountQueryInfo.getAssetBarcode() != null && swAccountQueryInfo.getAssetBarcode().length()>0)
			{
				whereSql+=" and assetBarcode like '%"+swAccountQueryInfo.getAssetBarcode()+"%'";
			}
			if(swAccountQueryInfo.getAssetName() != null && swAccountQueryInfo.getAssetName().length()>0)
			{
				whereSql+=" and assetName like '%"+swAccountQueryInfo.getAssetName()+"%'";
			}
			if(swAccountQueryInfo.getUseDept() != null && swAccountQueryInfo.getUseDept().length()>0)
			{
				whereSql+=" and useDept like '%"+swAccountQueryInfo.getUseDept()+"%'";
			}
			if(swAccountQueryInfo.getSaveAddress() != null && swAccountQueryInfo.getSaveAddress().length()>0)
			{
				whereSql+=" and saveAddress like '%"+swAccountQueryInfo.getSaveAddress()+"%'";
			}
			if(swAccountQueryInfo.getAssetClass() != null && swAccountQueryInfo.getAssetClass().length()>0)
			{
				whereSql+=" and assetClass like '%"+swAccountQueryInfo.getAssetClass()+"%'";
			}
			if(swAccountQueryInfo.getAssetBigClass() != null && swAccountQueryInfo.getAssetBigClass().length()>0)
			{
				whereSql+=" and assetBigclass like '%"+swAccountQueryInfo.getAssetBigClass()+"%'";
			}
		}
		
		
		selectSql = "select imp_sw_asset.*,imp_rela_jq_sw.id as relaId from imp_sw_asset left join imp_rela_jq_sw on imp_sw_asset.id=imp_rela_jq_sw.swId"+whereSql;
		
		if(limit > 0)
		{
			selectSql +=" limit "+ start+","+limit;
		}
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		for(int i=0;i<listMainMap.size();i++)
		{
			
			Map<String, Object> mainMap = listMainMap.get(i);
			
			SwAssetAccount swAssetAccount = new SwAssetAccount();
		 	
			swAssetAccount.setId((String)mainMap.get("id"));
			swAssetAccount.setTaskId((String)mainMap.get("taskId"));
			swAssetAccount.setAssetName((String)mainMap.get("assetName"));
			swAssetAccount.setSl(NumberUtils.toInt(mainMap.get("sl").toString(),0));
			swAssetAccount.setGetDate(CommonUtils.convertStringToDate((String)mainMap.get("get_date")));
			swAssetAccount.setAssetPp((String)mainMap.get("assetPp"));
			swAssetAccount.setAssetModel((String)mainMap.get("assetModel"));
			swAssetAccount.setUseDept((String)mainMap.get("useDept"));
			swAssetAccount.setUseUser((String)mainMap.get("useUser"));
			swAssetAccount.setSaveAddress((String)mainMap.get("saveAddress"));
			
			
			String relaId = (String)mainMap.get("relaId");
			
			if(relaId == null)
			{
				swAssetAccount.setRela(false);
			}
			else
			{
				swAssetAccount.setRela(true);
			}
			
			listSwAssetAccount.add(swAssetAccount);
		}
		
		return listSwAssetAccount;
	}
	
	
	
	
	
	
	public static int getSwAccountCount(String dbPath,SwAccountQueryInfo swAccountQueryInfo) throws Exception
	{
		
		
		int count = 0;
		
		String selectSql = null;
		
		
		String whereSql =" where 1=1";
		
		if(swAccountQueryInfo != null)
		{
			if(swAccountQueryInfo.getAssetBarcode() != null && swAccountQueryInfo.getAssetBarcode().length()>0)
			{
				whereSql+=" and assetBarcode like '%"+swAccountQueryInfo.getAssetBarcode()+"%'";
			}
			if(swAccountQueryInfo.getAssetName() != null && swAccountQueryInfo.getAssetName().length()>0)
			{
				whereSql+=" and assetName like '%"+swAccountQueryInfo.getAssetName()+"%'";
			}
			if(swAccountQueryInfo.getUseDept() != null && swAccountQueryInfo.getUseDept().length()>0)
			{
				whereSql+=" and useDept like '%"+swAccountQueryInfo.getUseDept()+"%'";
			}
			if(swAccountQueryInfo.getSaveAddress() != null && swAccountQueryInfo.getSaveAddress().length()>0)
			{
				whereSql+=" and saveAddress like '%"+swAccountQueryInfo.getSaveAddress()+"%'";
			}
			if(swAccountQueryInfo.getAssetClass() != null && swAccountQueryInfo.getAssetClass().length()>0)
			{
				whereSql+=" and assetClass like '%"+swAccountQueryInfo.getAssetClass()+"%'";
			}
			if(swAccountQueryInfo.getAssetBigClass() != null && swAccountQueryInfo.getAssetBigClass().length()>0)
			{
				whereSql+=" and assetBigclass like '%"+swAccountQueryInfo.getAssetBigClass()+"%'";
			}
		}
		
		
		
		
		selectSql = "select count(*) from imp_sw_asset "+whereSql;
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		if(listMainMap.size()==1)
		{
			count = Integer.parseInt(listMainMap.get(0).get("count(*)").toString());
		}
		
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	public static String getNewBarcode(String dbPath,List<String> listSql) throws Exception
	{
		  
		String  selectSql = "select max(assetBarcode) as barcode from imp_sw_asset where assetBarcode like 'johe%'";
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		String newBarcode = "johe000001";
		
		if(listMainMap != null && listMainMap.size()==1)
		{
			
			Object o = listMainMap.get(0).get("barcode");
			
			if(o != null)
			{
				String maxBarcode = o.toString();
				
				String maxCode = maxBarcode.substring(4);
				int iCode = Integer.parseInt(maxCode);
				
				String strCode = String.format("%06d",iCode + 1);
				
				newBarcode = "johe" +strCode;
			}
			
			
		}
		
		
		return newBarcode;
	}
	
	
	
	
	
	
	
	
	
	public static String getNewBarcode(PreparedStatement prep) throws Exception
	{
		
		String newBarcode = "johe000001";
		  
		String  selectSql = "select max(assetBarcode) as barcode from imp_sw_asset where assetBarcode like 'johe%'";
		 
		ResultSet rs = prep.executeQuery(selectSql);
		 
		if(rs.next())
		{
				String maxBarcode = rs.getString("barcode");
				
				String maxCode = maxBarcode.substring(4);
				int iCode = Integer.parseInt(maxCode);
				
				String strCode = String.format("%06d",iCode + 1);
				
				newBarcode = "johe" +strCode;
		}
		
		if(rs != null)		rs.close();
		
		return newBarcode;
	}
	
	
	
	
	
	
	
	
	
	public static String getNewBarcode(List<Map<String, Object>> listMap) throws Exception
	{
		
		String newBarcode = "johe000001";
		   
		
		if(listMap != null && listMap.size()==1)
		{	
				Object o = listMap.get(0).get("barcode");
				if(o != null)
				{
					String maxBarcode = o.toString();
					
					String maxCode = maxBarcode.substring(4);
					int iCode = Integer.parseInt(maxCode);
					
					String strCode = String.format("%06d",iCode + 1);
					
					newBarcode = "johe" +strCode;
				} 
		} 
	 
		return newBarcode;
	}
}
