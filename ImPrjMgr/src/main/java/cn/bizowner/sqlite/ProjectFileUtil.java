package cn.bizowner.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.utils.Utils;

public class ProjectFileUtil {

	
	
	public static void delProjectFileInfo(String fileId,int type,List<String> listSql,List<Object[]> listObj) throws Exception
	{	
		String delFileSql = "delete from imp_file_list where id=?";
		Object[] proFileInfoObj = {fileId};
		
		listSql.add(delFileSql);
		listObj.add(proFileInfoObj);
		
		
		if(type == Utils.AccountType_Jq)
		{
					String delAssetSql = "delete from imp_cw_asset where fileId=?";
					Object[] assetObj = {fileId};
					
					listSql.add(delAssetSql);
					listObj.add(assetObj);
					
					
					
					String delRelSql = "delete from imp_rela_cw_jq where 1=?";
					Object[] relObj = {1};
					
					listSql.add(delRelSql);
					listObj.add(relObj);
					
					
					
					String delLogSql = "delete from imp_change_log where accountType=?";
					Object[] logObj = {Utils.AccountType_Detail_Cw};
					
					listSql.add(delLogSql);
					listObj.add(logObj);
					
		}
		else if(type == Utils.AccountType_Sw)
		{
					String delAssetSql = "delete from imp_sw_asset where 1=?";
					Object[] assetObj = {1};
					
					listSql.add(delAssetSql);
					listObj.add(assetObj);
					
					
					
					String delRelSql = "delete from imp_rela_jq_sw where 1=?";
					Object[] relObj = {1};
					
					listSql.add(delRelSql);
					listObj.add(relObj);
					
					
					
					String delLogSql = "delete from imp_change_log where accountType=?";
					Object[] logObj = {Utils.AccountType_Detail_Sw};
					
					listSql.add(delLogSql);
					listObj.add(logObj);
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void insertProjectFileInfo(ProjectFileInfo projectFileInfo,List<String> listSql,List<Object[]> listObj) throws Exception
	{
		String insertProFileInfo = "insert into imp_file_list(id,projectId,type,fileName,filePath,updateTime,remark) values (?,?,?,?,?,?,?)";
		Object[] proFileInfoObj = {projectFileInfo.getId(),projectFileInfo.getProjectId(),projectFileInfo.getType(),projectFileInfo.getFileName(),projectFileInfo.getFilePath(),projectFileInfo.getUpdateTime(),projectFileInfo.getRemark()};
		
		listSql.add(insertProFileInfo);
		listObj.add(proFileInfoObj);
	}
	
	
	
	
	public static String getFileId(String dbPath,Integer type) throws Exception
	{
		
		String id = null;
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		String sql = "select id from imp_file_list where type="+type;
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(sql);
		
		if(listMainMap != null && listMainMap.size()==1)
		{
			id = listMainMap.get(0).get("id").toString();
		}
		
		return id;			
	}
	
	
	
	
	public static List<ProjectFileInfo> getJqAccountFileList(String dbPath,Integer type) throws Exception
	{
		
				
				List<ProjectFileInfo> listProjectFileInfo = new ArrayList<ProjectFileInfo>();
		
				String selectSql = "select id,projectId,type,fileName,filePath,remark,updateTime from imp_file_list where type="+type;
				
				
				SqliteHelper hAsset = new SqliteHelper(dbPath);
				
				List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
				
				for(int i=0;i<listMainMap.size();i++)
				{
						Map<String, Object> mainMap = listMainMap.get(i);
					
						String id = (String)mainMap.get("id");
						String projectId = (String)mainMap.get("projectId");
						Integer iType = NumberUtils.toInt(String.valueOf(mainMap.get("type")),0);
						String fileName = (String)mainMap.get("fileName");
						String filePath = (String)mainMap.get("filePath");
						String remark = (String)mainMap.get("remark");
						String updateTime = (String)mainMap.get("updateTime");
						
						
						ProjectFileInfo pf = new ProjectFileInfo();
						pf.setId(id);
						pf.setProjectId(projectId);
						pf.setType(iType);
						pf.setFileName(fileName);
						pf.setFilePath(filePath);
						pf.setRemark(remark);
						pf.setUpdateTime(updateTime);
						
						listProjectFileInfo.add(pf);
				}
				
				
				return listProjectFileInfo;
	}
	
	
	
	
	
	public static String getLastestFileId(String dbPath,Integer type) throws Exception
	{
		
				String id = null;
							
				String selectSql = "select id from imp_file_list where type="+type+" order by updateTime desc limit 1";
				
				
				SqliteHelper hAsset = new SqliteHelper(dbPath);
				
				List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
				
				if(listMainMap.size()==1)
				{
					id = listMainMap.get(0).get("id").toString();
				}
				
				return id;
	}
	
}
