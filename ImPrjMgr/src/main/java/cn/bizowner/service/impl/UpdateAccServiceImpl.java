package cn.bizowner.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.mapper.ColumnNameMapper;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.mapper.TopicMapper;
import cn.bizowner.model.ChangeLog;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.TopicMes;
import cn.bizowner.service.CommonService;
import cn.bizowner.service.TopicService;
import cn.bizowner.service.UpdateAccService;
import cn.bizowner.sqlite.AccountUtil;
import cn.bizowner.sqlite.ChangeLogUtil;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.SqliteHelper;
import cn.bizowner.sqlite.SwAccountUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.Utils;



@Service("updateAccService")
public class UpdateAccServiceImpl implements UpdateAccService {
	
	
	
	@Autowired
	private PlanTopicMapper planTopicMapper;
	
	
	
	@Autowired
	private ColumnNameMapper columnNameMapper;
	
	
	@Autowired
	private TopicMapper topicMapper;
	

	@Override
	public void updateRecord(String proId,String userId,String mc,Integer accountType,String id,String columnName,String newValue) throws Exception {
			
		
			/*if(changeLog.getAccountType() == 2 || changeLog.getAccountType() == 3)
			{
					List<TableStruct> listTableStruct = tableStructMapper.getTableStruct("", columnName)
			}*/
			
		
			String dbPath = Utils.dbPath+proId+".db";	
		
			String oldValue = AccountUtil.getValue(dbPath, accountType, columnName, id);
		  
			String currTime = CommonUtils.getCurrentTime();
		
			ChangeLog changeLog = new ChangeLog();
			changeLog.setId(CommonUtils.getUUID());
			changeLog.setAccountType(accountType);
			changeLog.setAssetId(id);
			changeLog.setColumnName(columnName);
			changeLog.setData1(oldValue);
			changeLog.setData2(newValue);
			changeLog.setChangTime(currTime);
			changeLog.setUserId(userId);
			changeLog.setOperType(Utils.Log_OperType_Modify);
		
			
		
		
			List<String> listSql = new ArrayList<String>();
			List<Object[]> listObj = new ArrayList<Object[]>();
			
			SqliteHelper hAsset = new SqliteHelper(dbPath);
			 
			 
			ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);
			
			 
			
			if(changeLog.getAccountType() == Utils.AccountType_Detail_Jq_RelaCw || changeLog.getAccountType() == Utils.AccountType_Detail_Jq_RelaSw)
			{
				
				String columnType = DbUtil.getDataType("imp_jq_asset", changeLog.getColumnName());
				
				
				if(columnType != null)
				{
					
					
					
						String updateSql = "update imp_jq_asset set "+changeLog.getColumnName()+"=? "+" where id=?";
					
						listSql.add(updateSql);
						
						if(columnType.equalsIgnoreCase("decimal"))
						{
								Double  value = NumberUtils.toDouble(changeLog.getData2(), 0);
								
								Object[] updateObj = {value,changeLog.getAssetId()};
								
								listObj.add(updateObj);
						}
						else if(columnType.equalsIgnoreCase("integer"))
						{
								Integer value = NumberUtils.toInt(changeLog.getData2(), 0);
								
								Object[] updateObj = {value,changeLog.getAssetId()};
								
								listObj.add(updateObj);
						}
						else
						{
							
								Object[] updateObj = {changeLog.getData2(),changeLog.getAssetId()};
							
								listObj.add(updateObj);
						}
				}
				
			} 
			else if(changeLog.getAccountType() == Utils.AccountType_Detail_Sw)
			{
				
				String columnType = DbUtil.getDataType("imp_sw_asset", changeLog.getColumnName());
				
				
				if(columnType != null)
				{
					
						String updateSql = "update imp_sw_asset set "+changeLog.getColumnName()+"=? "+" where id=?";
					
						listSql.add(updateSql);
						
						if(columnType.equalsIgnoreCase("decimal"))
						{
								Double  value = NumberUtils.toDouble(changeLog.getData2(), 0);
								
								Object[] updateObj = {value,changeLog.getAssetId()};
								
								listObj.add(updateObj);
						}
						else if(columnType.equalsIgnoreCase("integer"))
						{
								Integer value = NumberUtils.toInt(changeLog.getData2(), 0);
								
								Object[] updateObj = {value,changeLog.getAssetId()};
								
								listObj.add(updateObj);
						}
						else
						{
							
								Object[] updateObj = {changeLog.getData2(),changeLog.getAssetId()};
							
								listObj.add(updateObj);
						}
				}
			}
			
			
			hAsset.executeSqlTrans(listSql, listObj);
			
			hAsset.destroyed();
			
			
			
			String topicName = null;
			if(changeLog.getAccountType() == Utils.AccountType_Detail_Cw || changeLog.getAccountType() == Utils.AccountType_Detail_Jq_RelaCw)
			{
				topicName  = planTopicMapper.getTopicId(proId, 1);
			}
			else
			{
				topicName = planTopicMapper.getTopicId(proId, 2);
			}
			 
			TopicMes topicMes = new TopicMes();
			topicMes.setId(CommonUtils.getUUID());
			topicMes.setTopicName(topicName);
			topicMes.setAccountType(accountType);
			topicMes.setAssetId(id);
			topicMes.setOperType(Utils.Log_OperType_Modify);
			topicMes.setMessage(getTopicMes(mc,accountType,columnName,oldValue,newValue));
			topicMes.setProId(proId);
			
			topicMapper.addMes(topicMes);
			
			return ;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	private String  getTopicMes(String mc,Integer accountType,String fieldName,String oldValue,String newValue)
	{
				String str = "用户"+mc+"将";
				
				Integer type = 0;
				
				if(accountType == Utils.AccountType_Detail_Cw)
				{
					str+="财务账";
					
					type = 1;
				}
				else if(accountType == Utils.AccountType_Detail_Jq_RelaCw || accountType == Utils.AccountType_Detail_Jq_RelaSw)
				{
					str+="久其账";
					
					type = 2;
				}
				else if(accountType == Utils.AccountType_Detail_Sw)
				{
					str+="实物账";
					
					type = 3;
				}
				
				String columnName = columnNameMapper.getColumnNameByFieldName(type, fieldName);
				
				str+="的字段"+columnName+"由"+oldValue+"改为"+newValue;
				
				return str;
	}











	@Override
	public void addJqAsset(String proId, String userId, String mc,Integer type, JqAssetAccount jqAssetAccount) throws Exception 
	{
		
		String dbPath = Utils.dbPath+proId+".db";	
		
		
		String currTime = CommonUtils.getCurrentTime();
	
		ChangeLog changeLog = new ChangeLog();
		changeLog.setId(CommonUtils.getUUID());
		
		if(type == 1)
		{
			changeLog.setAccountType(Utils.AccountType_Detail_Jq_RelaCw);
		}
		else
		{
			changeLog.setAccountType(Utils.AccountType_Detail_Jq_RelaSw);
		} 
		changeLog.setAssetId(jqAssetAccount.getId());
		changeLog.setChangTime(currTime);
		changeLog.setUserId(userId);
		changeLog.setOperType(Utils.Log_OperType_Add);
	 
		
		List<JqAssetAccount> listJqAssetAccount = new ArrayList<JqAssetAccount>();
		listJqAssetAccount.add(jqAssetAccount);
		
		
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		
		ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);
		
		JqAccountUtil.addJqAssetAccount(listJqAssetAccount, listSql, listObj);
		
		
		hAsset.executeSqlTrans(listSql, listObj);
		
		hAsset.destroyed();
		
		
		String topicName =  planTopicMapper.getTopicId(proId, type);
		
		System.out.println("topicName:"+topicName);
		  
		TopicMes topicMes = new TopicMes();
		topicMes.setId(CommonUtils.getUUID());
		topicMes.setTopicName(topicName); 
		if(type == 1)
		{
			topicMes.setAccountType(Utils.AccountType_Detail_Jq_RelaCw);
		}
		else
		{
			topicMes.setAccountType(Utils.AccountType_Detail_Jq_RelaSw);
		}
		topicMes.setProId(proId);
		topicMes.setAssetId(jqAssetAccount.getId());
		topicMes.setOperType(Utils.Log_OperType_Add);
		topicMes.setMessage("用户"+mc+"添加了一条久其账信息");
		 
		topicMapper.addMes(topicMes);
		
		return ;
		
	}

	
	
	
 
	@Override
	public List<JqAssetAccount> splitJqAsset(String proId, String userId, String mc,String jqFileId,Integer accountType, String id, Integer sl) throws Exception
	{
		
		
		
			List<String> assetIds = new ArrayList<String>();
			
			assetIds.add(id);
		
		
			String dbPath = Utils.dbPath+proId+".db";	
			 
			JqAssetAccount jqAssetAccount = JqAccountUtil.getJqAssetDetailById(dbPath, jqFileId, 1, id);
		
			
			String barcode = jqAssetAccount.getAssetBarcode();
			
			Integer assetNum = jqAssetAccount.getSl();
			
			
			Double assetMoney = jqAssetAccount.getMoney();
			 
			Double averMoney = assetMoney/assetNum;

			List<String> listSql = new ArrayList<String>();
			List<Object[]> listObj = new ArrayList<Object[]>();
			
			String updateSql  ="update imp_jq_asset set sl=?,money=? where id=?";
			
			Object[] updateObj = {assetNum-sl+1,(assetNum-sl+1)*averMoney,id};
			
			listSql.add(updateSql);
			listObj.add(updateObj);
			
			
			
			List<JqAssetAccount> listJqAssetAccount = new ArrayList<JqAssetAccount>();
			
			
			for(int i=0;i<sl-1;i++)
			{
					JqAssetAccount account = JqAccountUtil.getJqAssetDetailById(dbPath, jqFileId, 1, id);
					
					String assetId = CommonUtils.getUUID();
					
					account.setId(assetId);
					
					account.setAssetBarcode(barcode+"_"+i+1);
					
					account.setSl(1);
					
					account.setMoney(averMoney);
					 
					listJqAssetAccount.add(account);
					 
					assetIds.add(assetId);
			}
			
			
			JqAccountUtil.addJqAssetAccount(listJqAssetAccount, listSql, listObj);
			
			
			
			String currTime = CommonUtils.getCurrentTime();
			
			
			ChangeLog changeLog = new ChangeLog();
			changeLog.setId(CommonUtils.getUUID());
			changeLog.setAccountType(accountType);
			changeLog.setAssetId(id);
			changeLog.setChangTime(currTime);
			changeLog.setUserId(userId);
			changeLog.setData1(""+sl);
			changeLog.setOperType(Utils.Log_OperType_Split);
			
			
			SqliteHelper hAsset = new SqliteHelper(dbPath);
			
			hAsset.executeSqlTrans(listSql, listObj);
			
			hAsset.destroyed();
			
			
			
			String topicName = null;
			if(accountType == Utils.AccountType_Detail_Cw || accountType == Utils.AccountType_Detail_Jq_RelaCw)
			{
				topicName  = planTopicMapper.getTopicId(proId, 1);
			}
			else
			{
				topicName = planTopicMapper.getTopicId(proId, 2);
			}
			 
			TopicMes topicMes = new TopicMes();
			topicMes.setId(CommonUtils.getUUID());
			topicMes.setTopicName(topicName);
			topicMes.setAccountType(accountType);
			topicMes.setAssetId(JSONHelper.encodeObject2JsonWithNull(assetIds));
			topicMes.setOperType(Utils.Log_OperType_Split);
			topicMes.setProId(proId);
			topicMes.setMessage("用户"+mc+"对久其账中资产编号为"+jqAssetAccount.getAssetBarcode()+"的资产进行了拆分,拆分数量为"+sl);
			 
			topicMapper.addMes(topicMes);
			
			 
			String selectSql = "select * from imp_jq_asset where id in (";
			for(int i=0;i<assetIds.size();i++)
			{
					String str = assetIds.get(i).toString();
					selectSql+="'"+str+"',";
			}
			
			selectSql = selectSql.substring(0,selectSql.length()-1);
			selectSql+=")";
			
			
			
			List<JqAssetAccount> list = JqAccountUtil.getJqAccountAsset(dbPath, selectSql);
			
			return list;
			
	}




	@Override
	public void addSwAsset(String proId, String userId, String mc, SwAssetAccount swAssetAccount) throws Exception {
		// TODO Auto-generated method stub
		
		String dbPath = Utils.dbPath+proId+".db";	
		
		String currTime = CommonUtils.getCurrentTime();
	
		ChangeLog changeLog = new ChangeLog();
		changeLog.setId(CommonUtils.getUUID()); 
		changeLog.setAccountType(Utils.AccountType_Detail_Sw);
		changeLog.setAssetId(swAssetAccount.getId());
		changeLog.setChangTime(currTime);
		changeLog.setUserId(userId);
		changeLog.setOperType(Utils.Log_OperType_Add);
	 
		
		List<SwAssetAccount> listSwAssetAccount  = new ArrayList<SwAssetAccount>();
		listSwAssetAccount.add(swAssetAccount);
		
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		
		ChangeLogUtil.insertChangeLog(changeLog, listSql, listObj);
		SwAccountUtil.addSwAssetAccount(listSwAssetAccount, listSql, listObj);
		
		hAsset.executeSqlTrans(listSql, listObj);
		
		hAsset.destroyed();
		
		
		String topicName =  planTopicMapper.getTopicId(proId, 2);
		
		System.out.println("topicName:"+topicName);
		  
		TopicMes topicMes = new TopicMes();
		topicMes.setId(CommonUtils.getUUID());
		topicMes.setTopicName(topicName); 
		topicMes.setAccountType(Utils.AccountType_Detail_Sw);
		topicMes.setProId(proId);
		topicMes.setAssetId(swAssetAccount.getId());
		topicMes.setOperType(Utils.Log_OperType_Add);
		topicMes.setMessage("用户"+mc+"添加了一条实物账信息");
		 
		topicMapper.addMes(topicMes);
		
		return ;
		
	}

}
