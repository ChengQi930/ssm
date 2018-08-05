package cn.bizowner.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.CwRelaJq;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.utils.CommonUtils;

public class CheckUtil {
	
	
	public static void insertJqRelaSw(List<JqRelaSw> listJqRelaSw,List<String> listSql,List<Object[]> listObj) throws Exception
	{ 
		
		for(int i=0;i<listJqRelaSw.size();i++)
		{
			
					JqRelaSw jqRelaSw = listJqRelaSw.get(i);
			
					String insertSql = "insert into imp_rela_jq_sw(id,jqId,swId,relaTime,userId,type) values (?,?,?,?,?,?)";
		
					Object[] obj = {jqRelaSw.getId(),jqRelaSw.getJqId(),jqRelaSw.getSwId(),jqRelaSw.getRelaTime(),jqRelaSw.getUserId(),jqRelaSw.getType()};
		
					listSql.add(insertSql);
					listObj.add(obj);
		}
	}
	
	
	
	
	public static void delJqRelaSw(String dbPath,String id,Integer type,List<String> listSql,List<Object[]> listObj) throws Exception
	{
				
				String delSql = null;
							
				if(type == 1)
				{
					delSql = "delete from imp_rela_jq_sw where id=(select id from imp_rela_jq_sw where jqId=?)";
				}
				else
				{
					delSql = "delete from imp_rela_jq_sw where id=(select id from imp_rela_jq_sw where swId=?)";
				}
				
				Object[] obj={id}; 
				
				listSql.add(delSql);
				listObj.add(obj);
	}
	
	
	
	
	
	
	
	
	
	public static List<Dto> getTrmtJqRelaSw(String dbPath,String id,Integer type) throws Exception
	{
				
		
				String selectSql = null;
							
				if(type == 1)
				{
					selectSql = "select jqId,swId from imp_rela_jq_sw where id=(select id from imp_rela_jq_sw where jqId='"+id+"')";
				}
				else
				{
					selectSql = "select jqId,swId from imp_rela_jq_sw where id=(select id from imp_rela_jq_sw where swId='"+id+"')";
				}
				
				
				SqliteHelper hAsset = new SqliteHelper(dbPath);
				
				List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
				
				List<Dto> listDto = new ArrayList<Dto>();
				
				for(int i=0;i<listMainMap.size();i++)
				{
						Map<String, Object> map = listMainMap.get(i);
						
						String jqId = map.get("jqId").toString();
						String swId = map.get("swId").toString();
						
						Dto dto = new BaseDto();
						
						dto.put("jqId", jqId);
						dto.put("swId", swId);
						
						listDto.add(dto);
				}
				
				return listDto;
	}
	
	
	
	
	
	
	
	
	public static List<Dto> getTrmtCwRelaJq(String dbPath,String id,Integer type) throws Exception
	{
				
				String selectSql = null;
							
				if(type == 1)
				{
					selectSql = "select cwId,jqId from imp_rela_cw_jq where id=(select id from imp_rela_cw_jq where cwId='"+id+"')";
				}
				else
				{
					selectSql = "select cwId,jqId from imp_rela_cw_jq where id=(select id from imp_rela_cw_jq where jqId='"+id+"')";
				}
				
				
				SqliteHelper hAsset = new SqliteHelper(dbPath);
				
				List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
				
				List<Dto> listDto = new ArrayList<Dto>();
				
				for(int i=0;i<listMainMap.size();i++)
				{
						Map<String, Object> map = listMainMap.get(i);
						
						String cwId = map.get("cwId").toString();
						String jqId = map.get("jqId").toString();
						
						
						Dto dto = new BaseDto();
						
						dto.put("cwId", cwId);
						dto.put("jqId", jqId);
						
						listDto.add(dto);
				}
				
				return listDto;
	}
	
	
	
	
	
		

	
	
	
	public static boolean isMulJqRelaSw(String dbPath,String id,Integer type) throws Exception
	{
		
		
				boolean flag = false;
				
				String selectSql = null;
				if(type == 1)
				{
					selectSql = "select type from imp_rela_jq_sw where  jqId='"+id+"')";
				}
				else
				{
					selectSql = "select type from imp_rela_jq_sw where  swId='"+id+"')";
				}
		
				
				SqliteHelper hAsset = new SqliteHelper(dbPath);
				
				List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
				
				Integer relaType = 0;
				
				if(listMainMap.size()==1)
				{
					relaType = Integer.parseInt(listMainMap.get(0).get("type").toString());
				}
				
				hAsset.destroyed();
				
				
				if(relaType > 1)
				{
					flag =true;
				}
				
				return flag;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	public static void insertCwRelaJq(List<CwRelaJq> listCwRelaJq,List<String> listSql,List<Object[]> listObj) throws Exception
	{
	
		for(int i=0;i<listCwRelaJq.size();i++)
		{
			
					CwRelaJq cwRelaJq = listCwRelaJq.get(i);
			
					String insertSql = "insert into imp_rela_cw_jq(id,cwId,jqId,relaTime,userId,type) values (?,?,?,?,?,?)";
		
					Object[] obj = {cwRelaJq.getId(),cwRelaJq.getCwId(),cwRelaJq.getJqId(),cwRelaJq.getRelaTime(),cwRelaJq.getUserId(),cwRelaJq.getType()};
		
					listSql.add(insertSql);
					listObj.add(obj);
		}
		
	}
	
	
	
	
	public static void  delCwRelaJq(String id,Integer type,List<String> listSql,List<Object[]> listObj) throws Exception
	{
				
				String delSql = null;
							
				if(type == 1)
				{
					delSql = "delete from imp_rela_cw_jq where id=(select id from imp_rela_cw_jq where cwId=?)";
				}
				else
				{
					delSql = "delete from imp_rela_cw_jq where id=(select id from imp_rela_cw_jq where jqId=?)";
				}
				
				
				Object[] obj={id}; 
				
				listSql.add(delSql);
				listObj.add(obj);
				  
	}
		

	
	
	
	public static boolean isMulCwRelaJq(String dbPath,String id,Integer type) throws Exception
	{
		
		
				boolean flag = false;
				
				String selectSql = null;
				if(type == 1)
				{
					selectSql = "select type from imp_rela_cw_jq where  cwId='"+id+"')";
				}
				else
				{
					selectSql = "select type from imp_rela_cw_jq where  jqId='"+id+"')";
				}
		
				
				SqliteHelper hAsset = new SqliteHelper(dbPath);
				
				List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
				
				Integer relaType = 0;
				
				if(listMainMap.size()==1)
				{
					relaType = Integer.parseInt(listMainMap.get(0).get("type").toString());
				}
				
				hAsset.destroyed();
				
				
				if(relaType > 1)
				{
					flag =true;
				}
				
				return flag;
	}
	
	
	
	
	
	 
}
