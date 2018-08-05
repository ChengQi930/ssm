package cn.bizowner.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.Utils;

public class AccountUtil {

	
	
	
	
	
	public static String getValue(String dbPath,Integer accountType,String columnName,String id) throws Exception
	{
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		String selectSql = "select "+columnName;
		
		if(accountType == Utils.AccountType_Detail_Jq_RelaCw || accountType == Utils.AccountType_Detail_Jq_RelaSw)
		{
			selectSql+=" from  imp_jq_asset where id='"+id+"'";
		}
		else if(accountType == Utils.AccountType_Detail_Sw)
		{
			selectSql+=" from imp_sw_asset where id='"+id+"'";
		}
		
		 
		 
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		String value = listMainMap.get(0).get(columnName).toString();
		 
		return value;
			
	}
	 
	
}
