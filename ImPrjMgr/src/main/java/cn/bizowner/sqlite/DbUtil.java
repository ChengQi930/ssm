package cn.bizowner.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.Utils;

public class DbUtil {
	
	public static void insertSql(String dbPath,List<String> listSql,List<Object[]> listObj) throws Exception
	{
			SqliteHelper hAsset = new SqliteHelper(dbPath);
		
			hAsset.executeSqlTrans(listSql, listObj);
			
			hAsset.destroyed();
	}
	
	
	public static String getDataType(String tableName,String columnName) throws Exception
	{
		
		
			String dataType = null;
		
			String dbPath = Utils.dbPath+"temp.db";
		
			SqliteHelper hAsset = new SqliteHelper(dbPath);
			
			String querySql = "PRAGMA table_info('"+tableName+"')";
		
			List<Map<String, Object>> listMap = hAsset.executeQuery(querySql);
			
			for(int i=0;i<listMap.size();i++)
			{
					Map<String, Object> map = listMap.get(i);
					
					String name = map.get("name").toString();
					
					if(name != null && name.equals(columnName))
					{
						dataType = map.get("type").toString();
					}
			}
			
			hAsset.destroyed();
			return dataType;
	}
	
}
