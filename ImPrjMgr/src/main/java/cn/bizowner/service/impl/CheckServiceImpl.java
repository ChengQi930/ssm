package cn.bizowner.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.CheckIndexMapper;
import cn.bizowner.mapper.ColumnSelectMapper;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.mapper.TopicMapper; 
import cn.bizowner.model.ChangeLog;
import cn.bizowner.model.CheckIndex;
import cn.bizowner.model.CwRelaJq;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.TopicMes;
import cn.bizowner.service.CheckService;
import cn.bizowner.sqlite.ChangeLogUtil;
import cn.bizowner.sqlite.CheckUtil;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.SqliteHelper;
import cn.bizowner.sqlite.SwAccountUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ConvertUtil;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.Utils;



@Service("checkService")
public class CheckServiceImpl implements CheckService {
 
	
	@Autowired
	private ColumnSelectMapper columnSelectMapper;
	
	
	@Autowired
	private PlanUserMapper planUserMapper;
	
	 
	@Autowired
	private TopicMapper topicMapper;
	
	
	
	@Autowired
	private PlanTopicMapper planTopicMapper;
	
	
	@Autowired
	private CheckIndexMapper checkIndexMapper;
	
	
	
	@Override
	public void jqRelaSwMulToMul(String userId,String mc,String proId,String jqId,String swId) throws Exception
	{ 
		
			List<String> listJqIds = new ArrayList<String>();
			
			JSONArray  jaJq = (JSONArray)JSONArray.parse(jqId); 
			
			for(Object obj : jaJq)
			{
					String str = obj.toString();
					listJqIds.add(str);
			}
			
			
			List<String> listSwIds = new ArrayList<String>();
			
			JSONArray  jaSw = (JSONArray)JSONArray.parse(swId); 
			
			for(Object obj : jaSw)
			{
					String str = obj.toString();
					listSwIds.add(str);
			}
			
			
			 
			
			String dbPath = Utils.dbPath+proId+".db";		
			
			List<JqRelaSw> listJqRelaSw = new ArrayList<JqRelaSw>();
			
			String uuid = CommonUtils.getUUID();
			
			int iRelaType = 1;
			
			if(listJqIds.size()==1 || listSwIds.size()==1)
			{
				iRelaType = 1;		//一对一
			}
			else
			{
				iRelaType = 2;		//多对多
			}
			
			
			for(int i=0;i<listJqIds.size();i++)
			{
				JqRelaSw jqRelaSw = new JqRelaSw();
				jqRelaSw.setId(uuid);
				jqRelaSw.setJqId(listJqIds.get(i));
				jqRelaSw.setSwId(listSwIds.get(i));
				jqRelaSw.setRelaTime(CommonUtils.getCurrentTime());
				jqRelaSw.setUserId(userId);
				jqRelaSw.setType(iRelaType);
				listJqRelaSw.add(jqRelaSw);
			}
			
			
			
			 
			ChangeLog changeLog = new ChangeLog();
			changeLog.setId(CommonUtils.getUUID());
			changeLog.setData1(jqId);
			changeLog.setData2(swId);
			changeLog.setChangTime(CommonUtils.getCurrentTime());
			changeLog.setUserId(userId);
			changeLog.setOperType(Utils.Log_OperType_JqRelaSw);
			
			
			
			String topicName = planTopicMapper.getTopicId(proId, 2);
			List<String> listBarcodes = JqAccountUtil.getAssetNamesByIds(dbPath, listJqIds);
			String strBarcodes  = StringUtils.join(listBarcodes, ",");
			
			TopicMes topicMes = new TopicMes();
			topicMes.setId(CommonUtils.getUUID());
			topicMes.setTopicName(topicName);
			topicMes.setData1(jqId);
			topicMes.setData2(swId);
			topicMes.setMessage("用户"+mc+"将久其帐中资产编号为"+strBarcodes+"的资产和实物帐进行了关联");
			topicMes.setOperType(Utils.Log_OperType_JqRelaSw);
			topicMes.setProId(proId);
			
			List<String> listSql = new ArrayList<String>();
			
			List<Object[]> listObj = new ArrayList<Object[]>();
				
			CheckUtil.insertJqRelaSw(listJqRelaSw,listSql,listObj);	
				
			ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);	
			
			SqliteHelper hAsset = new SqliteHelper(dbPath);

			hAsset.executeSqlTrans(listSql, listObj);
						
			hAsset.destroyed();
			
			topicMapper.addMes(topicMes);
		
	}
	
	
	 

	@Override 
	public Dto trmtJqRelaSw(String userId,String mc,String proId,String id,Integer type)  throws Exception
	{
		
		
			String dbPath = Utils.dbPath+proId+".db";		
			
			List<String> listSql = new ArrayList<String>();
		
			List<Object[]> listObj = new ArrayList<Object[]>();
		
			List<Dto> listDto = CheckUtil.getTrmtJqRelaSw(dbPath, id, type);
			
			List<String> listJqIds = new ArrayList<String>();
			List<String> listSwIds = new ArrayList<String>();
			
			
			for(int i=0;i<listDto.size();i++)
			{
					String jqId = listDto.get(i).getAsString("jqId");
					String swId = listDto.get(i).getAsString("swId");
					
					listJqIds.add(jqId);
					listSwIds.add(swId);
			}
			
			
			ChangeLog changeLog = new ChangeLog();
			changeLog.setId(CommonUtils.getUUID());
			changeLog.setChangTime(CommonUtils.getCurrentTime());
			changeLog.setUserId(userId);
			changeLog.setOperType(Utils.Log_OperType_TrmtJqRelaSw);
			changeLog.setData1(JSONHelper.encodeObject2JsonWithNull(listJqIds));
			changeLog.setData2(JSONHelper.encodeObject2JsonWithNull(listSwIds));
			
			
			String topicName = planTopicMapper.getTopicId(proId, 2);
			List<String> listBarcodes = JqAccountUtil.getAssetNamesByIds(dbPath, listJqIds);
			String strBarcodes  = StringUtils.join(listBarcodes, ",");
			
			TopicMes topicMes = new TopicMes();
			topicMes.setId(CommonUtils.getUUID());
			topicMes.setTopicName(topicName);
			topicMes.setData1(JSONHelper.encodeObject2JsonWithNull(listJqIds));
			topicMes.setData2(JSONHelper.encodeObject2JsonWithNull(listSwIds));
			topicMes.setMessage("用户"+mc+"将久其帐中资产编号为"+strBarcodes+"的资产和实物帐进行了解关联");
			topicMes.setOperType(Utils.Log_OperType_TrmtJqRelaSw);
			topicMes.setProId(proId); 
			
			CheckUtil.delJqRelaSw(dbPath, id, type,listSql,listObj);
			
			ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);	
			
			SqliteHelper hAsset = new SqliteHelper(dbPath);

			hAsset.executeSqlTrans(listSql, listObj);
						
			hAsset.destroyed();
			
			topicMapper.addMes(topicMes);
			
			
			
			Dto outDto = new BaseDto();
			outDto.put("jqId", listJqIds);
			outDto.put("swId", listJqIds);
			
			return outDto;
	}




	@Override
	public boolean isMulJqRelaSw(String dbPath, String id, Integer type) throws Exception 
	{
			
			return CheckUtil.isMulJqRelaSw(dbPath, id, type);
	}
	
	 

	@Override
	public void cwRelaJqMulToMul(String userId,String mc,String proId,String cwId,String jqId) throws Exception
	{			
		
		List<String> listJqIds = new ArrayList<String>();
		
		JSONArray  jaJq = (JSONArray)JSONArray.parse(jqId); 
		
		for(Object obj : jaJq)
		{
				String str = obj.toString();
				listJqIds.add(str);
		}
		
		
		List<String> listCwIds = new ArrayList<String>();
		
		JSONArray  jaCw = (JSONArray)JSONArray.parse(cwId); 
		
		for(Object obj : jaCw)
		{
				String str = obj.toString();
				listCwIds.add(str);
		}
		
		
		 
		
		String dbPath = Utils.dbPath+proId+".db";		
		
		List<CwRelaJq> listCwRelaJq = new ArrayList<CwRelaJq>();
		
		String uuid = CommonUtils.getUUID();
		 
		Integer iRelaType = 1;		//一对一
		if(listJqIds.size()>1)
		{
			iRelaType = 2;		//一对多
		}
		
		
		for(int i=0;i<listJqIds.size();i++)
		{
			CwRelaJq cwRelaJq = new CwRelaJq();
			cwRelaJq.setId(uuid);
			cwRelaJq.setJqId(listJqIds.get(i));
			cwRelaJq.setCwId(listCwIds.get(0));
			cwRelaJq.setRelaTime(CommonUtils.getCurrentTime());
			cwRelaJq.setUserId(userId);
			cwRelaJq.setType(iRelaType);
			listCwRelaJq.add(cwRelaJq);
		}
		
		
		
		 
		ChangeLog changeLog = new ChangeLog();
		changeLog.setId(CommonUtils.getUUID());
		changeLog.setData1(cwId);
		changeLog.setData2(jqId);
		changeLog.setChangTime(CommonUtils.getCurrentTime());
		changeLog.setUserId(userId);
		changeLog.setOperType(Utils.Log_OperType_CwRelaJq);
		
		
		
		String topicName = planTopicMapper.getTopicId(proId, 1);
		List<String> listBarcodes = JqAccountUtil.getAssetNamesByIds(dbPath, listJqIds);
		String strBarcodes  = StringUtils.join(listBarcodes, ",");
		
		TopicMes topicMes = new TopicMes();
		topicMes.setId(CommonUtils.getUUID());
		topicMes.setTopicName(topicName);
		topicMes.setData1(cwId);
		topicMes.setData2(jqId);
		topicMes.setMessage("用户"+mc+"将久其帐中资产编号为"+strBarcodes+"的资产和财物帐进行了关联");
		topicMes.setOperType(Utils.Log_OperType_CwRelaJq);
		topicMes.setProId(proId);
		
		List<String> listSql = new ArrayList<String>();
		
		List<Object[]> listObj = new ArrayList<Object[]>();
			
		CheckUtil.insertCwRelaJq(listCwRelaJq,listSql,listObj);	
			
		ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);	
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);

		hAsset.executeSqlTrans(listSql, listObj);
					
		hAsset.destroyed();
		
		topicMapper.addMes(topicMes);
		
	}




	@Override 
	public Dto trmtCwRelaJq(String userId,String mc,String proId,String id,Integer type)  throws Exception
	{
		
		
		
			
		String dbPath = Utils.dbPath+proId+".db";		
		
		List<String> listSql = new ArrayList<String>();
	
		List<Object[]> listObj = new ArrayList<Object[]>();
	
		List<Dto> listDto = CheckUtil.getTrmtCwRelaJq(dbPath, id, type);
		
		List<String> listCwIds = new ArrayList<String>();
		List<String> listJqIds = new ArrayList<String>();
		
		for(int i=0;i<listDto.size();i++)
		{ 
				String cwId = listDto.get(i).getAsString("cwId");
				String jqId = listDto.get(i).getAsString("jqId");
			
				if(i==0)
				{
					listCwIds.add(cwId);
					listJqIds.add(jqId);
				}
				else
				{
					listJqIds.add(jqId);
				}
		}
		
		
		 
		ChangeLog changeLog = new ChangeLog();
		changeLog.setId(CommonUtils.getUUID());
		changeLog.setChangTime(CommonUtils.getCurrentTime());
		changeLog.setUserId(userId);
		changeLog.setOperType(Utils.Log_OperType_TrmtCwRelaJq);
		changeLog.setData1(JSONHelper.encodeObject2JsonWithNull(listCwIds));
		changeLog.setData2(JSONHelper.encodeObject2JsonWithNull(listJqIds));
		
		
		String topicName = planTopicMapper.getTopicId(proId, 1);
		List<String> listBarcodes = JqAccountUtil.getAssetNamesByIds(dbPath, listJqIds);
		String strBarcodes  = StringUtils.join(listBarcodes, ",");
		
		TopicMes topicMes = new TopicMes();
		topicMes.setId(CommonUtils.getUUID());
		topicMes.setTopicName(topicName);
		topicMes.setData1(JSONHelper.encodeObject2JsonWithNull(listCwIds));
		topicMes.setData2(JSONHelper.encodeObject2JsonWithNull(listJqIds));
		topicMes.setMessage("用户"+mc+"将久其帐中资产编号为"+strBarcodes+"的资产和财物帐进行了解关联");
		topicMes.setOperType(Utils.Log_OperType_TrmtCwRelaJq);
		topicMes.setProId(proId);
		 
		CheckUtil.delCwRelaJq(id, type,listSql,listObj);
		
		ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);	
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);

		hAsset.executeSqlTrans(listSql, listObj);
					
		hAsset.destroyed();
		
		topicMapper.addMes(topicMes);
		
		
		
		Dto outDto = new BaseDto();
		
		
		outDto.put("jqId", listJqIds);
		outDto.put("cwId", listCwIds);
		
		return outDto;
		
	}




	@Override
	public boolean isMulCwRelaJq(String dbPath,String id, Integer type) throws Exception 
	{
			
			return CheckUtil.isMulCwRelaJq(dbPath, id, type);
	}




	@Override
	public boolean isHavePerm(String proId, String userId, Integer type) throws Exception {
			
			int count = planUserMapper.isHavePerm(userId, proId, type);
			
			if(count > 0)
			{
						return true;
			}
			else
			{
						return false;
			}
	}




	



	@Override
	public Dto suggCheckJqRelaSw(String proId,String checkIndexId,String userId,String jqRelaSwFileId) throws Exception 
	{
		  
			String dbPath = Utils.dbPath+proId+".db";	
		
			CheckIndex checkIndex = checkIndexMapper.getInfoById(checkIndexId);
			
			List<String> list = ConvertUtil.StringToList(checkIndex.getIndexValue());
			
			List<String> listTypes = new ArrayList<String>();
			
			for(int i=0;i<list.size();i++)
			{
					String type = DbUtil.getDataType("imp_jq_asset", list.get(i));
					System.out.println("type="+type);
					listTypes.add(type);
			}
			
			
			String selectSql = "select "+checkIndex.getIndexValue()+" from imp_jq_asset "+
			"where sl=1 and fileId='"+jqRelaSwFileId+"' and (select count(*) from imp_rela_jq_sw where imp_rela_jq_sw.jqId=imp_jq_asset.id)=0 "+
			"group by "+checkIndex.getIndexValue()+"";
			 
			SqliteHelper hAsset = new SqliteHelper(dbPath);
 
			List<Map<String, Object>> listMap = hAsset.executeQuery(selectSql);
			
			
			
			List<JqAssetAccount> listJqAccount = new ArrayList<JqAssetAccount>();
			List<SwAssetAccount> listSwAccount = new ArrayList<SwAssetAccount>();
			
			int type = 1;
			
			for(int i=0;i<listMap.size();i++)
			{
							String jqSql = "select id from imp_jq_asset where (select count(*) from imp_rela_jq_sw where imp_rela_jq_sw.jqId=imp_jq_asset.id)=0";
							
							for(int j=0;j<list.size();j++)
							{
								
								if(listTypes.equals("decimal"))
								{
									jqSql+=" and "+list.get(j)+" = "+listMap.get(i).get(list.get(j));
								}
								else if(listTypes.equals("integer"))
								{
									jqSql+=" and "+list.get(j)+" = "+listMap.get(i).get(list.get(j));
								}
								else
								{
									jqSql+=" and "+list.get(j)+" = '"+listMap.get(i).get(list.get(j))+"'";
								}
								
								
							}
							
							String swSql = "select id from imp_sw_asset where (select count(*) from imp_rela_jq_sw where imp_rela_jq_sw.swId=imp_sw_asset.id)=0";
							for(int j=0;j<list.size();j++)
							{
 								if(listTypes.equals("decimal"))
								{
									swSql+=" and "+list.get(j)+" = "+listMap.get(i).get(list.get(j));
								}
								else if(listTypes.equals("integer"))
								{
									swSql+=" and "+list.get(j)+" = "+listMap.get(i).get(list.get(j));
								}
								else
								{
									swSql+=" and "+list.get(j)+" = '"+listMap.get(i).get(list.get(j))+"'";
								}
							}
							
							
							List<Map<String, Object>> jqListMap = hAsset.executeQuery(jqSql);
							
							List<Map<String, Object>> swListMap = hAsset.executeQuery(swSql);
							
							
							if(jqListMap != null && swListMap != null)
							{
									if(jqListMap.size() == swListMap.size())
									{
										
												  
												for(int k=0;k<jqListMap.size();k++)
												{
													
													JqAssetAccount jq = JqAccountUtil.getJqAssetDetailById(dbPath, jqListMap.get(k).get("id").toString());
													
													jq.setType(type);
													
													SwAssetAccount sw = SwAccountUtil.getSwAssetDetailById(dbPath, swListMap.get(k).get("id").toString());
													
													sw.setType(type);
													
													listJqAccount.add(jq);
													
													listSwAccount.add(sw);
													
												}
												 
												/*Dto dto = new BaseDto();
												dto.put("jq", listJqAccount);
												dto.put("sw", listSwAccount);
												 
												listDto.add(dto); */
												
												
												type++;
									}
							}
							
			}
			
			hAsset.destroyed();
			
			
			
			
			
			int count = columnSelectMapper.isSetColumnSelect(userId, 3);
			
			
			if(count == 0)
			{
					userId = "default";
			}
			
			List<Dto> tempJqListDto =  columnSelectMapper.getHeadInfo(userId, 3);
			
			List<Dto> jqlistDto = new ArrayList<Dto>();
			
			for(int i=0;i<tempJqListDto.size();i++)
			{
					Dto tempDto = tempJqListDto.get(i);
					
					String filedName = tempDto.getAsString("fieldName");
					String columnName = tempDto.getAsString("columnName");
					String flag = tempDto.getAsString("flag");
					
					Dto dto = new BaseDto();
				
					dto.put(filedName, columnName+","+flag);
					
					jqlistDto.add(dto);
			}
			
			
			
			
			
			
			
			count = columnSelectMapper.isSetColumnSelect(userId, 4);
			
			
			if(count == 0)
			{
					userId = "default";
			}
			
			List<Dto> tempSwListDto =  columnSelectMapper.getHeadInfo(userId, 4);
			
			List<Dto> swlistDto = new ArrayList<Dto>();
			
			for(int i=0;i<tempSwListDto.size();i++)
			{
					Dto tempDto = tempSwListDto.get(i);
					
					String filedName = tempDto.getAsString("fieldName");
					String columnName = tempDto.getAsString("columnName");
					String flag = tempDto.getAsString("flag");
					
					Dto dto = new BaseDto();
				
					dto.put(filedName, columnName+","+flag);
					
					swlistDto.add(dto);
			}
			
			
			
			Dto headDto = new BaseDto();
			headDto.put("jq", jqlistDto);
			headDto.put("sw", swlistDto);
			
			
			Dto dataDto = new BaseDto();
			dataDto.put("jq", listJqAccount);
			dataDto.put("sw", listSwAccount);
			
			
			Dto dto = new BaseDto();
			dto.put("data", dataDto);
			dto.put("head", headDto);
			
			
			return dto;
	}		


 
}
