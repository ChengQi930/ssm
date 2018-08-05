package cn.bizowner.config;
 
import java.util.ArrayList;
import java.util.List; 
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;

import cn.bizowner.activemq.JMSProducer;
import cn.bizowner.activemq.JMSProducer1;
import cn.bizowner.activemq.MqHelper;
import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.ExamineHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.ApprovalMapper;
import cn.bizowner.mapper.TopicMapper;
import cn.bizowner.mapper.UserDeptMapper;
import cn.bizowner.mapper.UserMapper;
import cn.bizowner.model.Approval;
import cn.bizowner.model.DingDingExamineResult;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.JqAssetAccount; 
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.TopicMes; 
import cn.bizowner.model.UserInfo; 
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.SwAccountUtil; 
import cn.bizowner.utils.JSONHelper; 
import cn.bizowner.utils.Utils;


@Component
public class SpringInitializingBean implements  InitializingBean{

	
	@Autowired
	private TopicMapper topicMapper;
	 
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserDeptMapper userDeptMapper;
	
	@Autowired
	private ApprovalMapper approvalmapper;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
			  
			/*SendMsgToMqThread th = new SendMsgToMqThread();
		
			Thread th1 = new Thread(th);
			th1.start();
			 
		
		
			SyncUserThread syncUserThread = new SyncUserThread();
		
			Thread th2 = new Thread(syncUserThread);
			th2.start();*/
		
		//审批的线程
		   /*ApprovalThread approvalThread = new ApprovalThread();
		   Thread approval = new Thread(approvalThread);
		   approval.start();*/
	}
	
	public class ApprovalThread implements Runnable{
		@Override
		public void run() {
			try
			{
				while(true)
				{
					//查出库中有多少条审批中的数据！ 带出审批中的       planid  processInstanceId 
					 ArrayList<Approval> approvalInthe = approvalmapper.getApprovalInthe();
					  String accessToken = AuthHelper.getAccessToken();
					 if(approvalInthe.size() != 0){
					   for (Approval approval : approvalInthe) {
						 //先根据processInstanceId去钉钉查询出返回的内容
						   DingDingExamineResult examineResult = ExamineHelper.getExamineResult(accessToken,approval.getProcessInstanceId());
							if(examineResult!=null)
							{	
							int updateApproval = updateApproval(examineResult,approval.getProcessInstanceId(),approval.getPlanId());
							
							//这里将信息发布到 mq上
							Dto outDto = new BaseDto();
							
							outDto.put("flag", (boolean)true);
							outDto.put("result",examineResult);
							String result = JSONHelper.encodeObject2JsonWithNull(outDto);
							 /*JMSProducer.main(approval.getPlanId(),result);*/
							JMSProducer.main(approval.getPlanId(),result);
							 break;		
							}
							else
							{
									Thread.sleep(1*1000);
							}
					}
					 }else{
						wait(); 
					 }
					   
				}
			}
			catch(Exception ex)
			{ 
					String s = ex.getMessage();
			}
			
			finally
			{
				
			}
		}
	}
	
	public int updateApproval(DingDingExamineResult examineResult,String processInstanceId,String PlanId) throws Exception{
		//修改数据库值!
			int updateApproval = approvalmapper.UpdateApproval(examineResult, processInstanceId);
			String result = examineResult.getResult();
			int updateplan =  0;
			if("AGREE".equals(result)){
				 updateplan = approvalmapper.Updateplan(PlanId);
			}
			
		return updateApproval;
		
	}
	
	public class SyncUserThread implements Runnable{
 
		@Override
		public void run() {
			 
			try
			{
				while(true)
				{	 
					
					List<UserInfo> listUserInfo = userMapper.getUserList(null, 0, 0);
					for(int i=0;i<listUserInfo.size();i++)
					{
						
						DingDingUserInfo dingDingUserInfo = UserHelper.getUserInfoByUserId(listUserInfo.get(i).getUserId());
						
						UserInfo userInfo = new UserInfo();
						userInfo.setUserId(dingDingUserInfo.getUserId());
						userInfo.setMc(dingDingUserInfo.getMc());
						
						userMapper.updateUserInfo(userInfo);
						
						userDeptMapper.delUserDeptByUserId(dingDingUserInfo.getUserId());
						
						if(dingDingUserInfo.getListDept()!=null)
						{
							for(int k=0;k<dingDingUserInfo.getListDept().size();k++)
							{
								userDeptMapper.addUserDept(dingDingUserInfo.getListDept().get(k));
							}
						}
					}
					
					Thread.sleep(5*1000);
					 
				}
			}
			catch(Exception ex)
			{ 
					String s = ex.getMessage();
			}
			
			finally
			{ 
				
			}
		}
		
		
	}
	
	
	 
	
	
	public class SendMsgToMqThread implements Runnable
	{ 
		
		@Override
		public void run() {
			 
			try
			{
				while(true)
				{	
					
					int count = topicMapper.getMesCount();
					if(count > 0)
					{
							List<TopicMes> listTopicMes = topicMapper.getMesList();
							for(int i=0;i<listTopicMes.size();i++)
							{
								sendMsg(listTopicMes.get(i));
							}
					}
					else
					{
							Thread.sleep(1*1000);
					}
				}
			}
			catch(Exception ex)
			{ 
					String s = ex.getMessage();
			}
			
			finally
			{ 
				
			}
		}
	}
	
	
	
	
	
	private void sendMsg(TopicMes topicMes)
	{
		try
		{
				Dto dto = new BaseDto();
				 
				String topicName = topicMes.getTopicName();
				
				Integer tempOperType = topicMes.getOperType();
				
				Integer tempAccountType = topicMes.getAccountType();
				 
				   
				String dbPath = Utils.dbPath+topicMes.getProId()+".db";
				
				String assetId = topicMes.getAssetId();
				
				
				int operType = 0;
				
				boolean isJqAccount = false;
				
				if(tempOperType == Utils.Log_OperType_Add)
				{ 
					
					if(tempAccountType == Utils.AccountType_Detail_Jq_RelaCw || tempAccountType == Utils.AccountType_Detail_Jq_RelaSw)
					{
						JqAssetAccount jqAssetAccount = JqAccountUtil.getJqAssetDetailById(dbPath, assetId);
						dto.put("data", jqAssetAccount);
						
						isJqAccount =true;
					}
					else if(tempAccountType == Utils.AccountType_Detail_Sw)
					{
						SwAssetAccount swAssetAccount = SwAccountUtil.getSwAssetDetailById(dbPath, assetId);
						dto.put("data", swAssetAccount);
					
						isJqAccount =false;
					}
					 
					
					operType = Utils.Mes_OperType_Add;
					
				}
				else if(tempOperType == Utils.Log_OperType_Modify)
				{
					if(tempAccountType == Utils.AccountType_Detail_Jq_RelaCw || tempAccountType == Utils.AccountType_Detail_Jq_RelaSw)
					{
						JqAssetAccount jqAssetAccount = JqAccountUtil.getJqAssetDetailById(dbPath, assetId);
						dto.put("data", jqAssetAccount);
						
						isJqAccount = true;
					}
					else
					{
						SwAssetAccount swAssetAccount = SwAccountUtil.getSwAssetDetailById(dbPath, assetId);
						dto.put("data", swAssetAccount);
						
						isJqAccount = false;
					} 
					
					
					operType = Utils.Mes_OperType_Modify;
				}
				else if(tempOperType == Utils.Log_OperType_CwRelaJq)
				{
					String cwId = topicMes.getData1();
					String jqId = topicMes.getData2();
					
					Dto tempDto = new BaseDto();
					
					
					
					List<String> listCwIds = new ArrayList<String>();
					
					JSONArray  jaCw = (JSONArray)JSONArray.parse(cwId); 
					
					for(Object obj : jaCw)
					{
							String str = obj.toString();
							listCwIds.add(str);
					}
					
					 
					List<String> listJqIds = new ArrayList<String>();
					
					JSONArray  jaJq = (JSONArray)JSONArray.parse(jqId); 
					
					for(Object obj : jaJq)
					{
							String str = obj.toString();
							listJqIds.add(str);
					}
					 
					
					tempDto.put("cwId", listCwIds);
					tempDto.put("jqId", listJqIds);
					
					dto.put("data", tempDto);
					
					
					operType = Utils.Mes_OperType_Rela;
					
				}
				else if(tempOperType == Utils.Log_OperType_JqRelaSw)
				{
					String jqId = topicMes.getData1();
					String swId = topicMes.getData2();
					
					
					
					
					
					
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
					 
					
					
					Dto tempDto = new BaseDto();
					
					tempDto.put("jqId", listJqIds);
					tempDto.put("swId", listSwIds);
					
					dto.put("data", tempDto);
					
					
					operType = Utils.Mes_OperType_Rela;
				}
				else if(tempOperType == Utils.Log_OperType_TrmtCwRelaJq)
				{
					String cwId = topicMes.getData1();
					String jqId = topicMes.getData2();
					
					 
					 
					List<String> listCwIds = new ArrayList<String>();
					
					JSONArray  jaCw = (JSONArray)JSONArray.parse(cwId); 
					
					for(Object obj : jaCw)
					{
							String str = obj.toString();
							listCwIds.add(str);
					}
					
					 
					List<String> listJqIds = new ArrayList<String>();
					
					JSONArray  jaJq = (JSONArray)JSONArray.parse(jqId); 
					
					for(Object obj : jaJq)
					{
							String str = obj.toString();
							listJqIds.add(str);
					}
					
					 
					Dto tempDto = new BaseDto();
					
					
					tempDto.put("cwId", listCwIds);
					tempDto.put("jqId", listJqIds);
					
					dto.put("data", tempDto);
					
					operType = Utils.Mes_OperType_Trmt;
				}
				else if(tempOperType == Utils.Log_OperType_TrmtJqRelaSw)
				{
					String jqId = topicMes.getData1();
					String swId = topicMes.getData2();
					
					
					
					
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
					
					Dto tempDto = new BaseDto();
					
					tempDto.put("jqId", listJqIds);
					tempDto.put("swId", listSwIds);
					
					
					dto.put("data", tempDto);
					
					operType = Utils.Mes_OperType_Trmt;
				}
				else if(tempOperType == Utils.Log_OperType_Split)
				{
										
					List<String> listJqId = new ArrayList<String>();
					
					JSONArray  jaSw = (JSONArray)JSONArray.parse(assetId); 
					
					String selectSql  ="select * from imp_jq_asset  where id in (";
					
					for(Object obj : jaSw)
					{
							String str = obj.toString();
							listJqId.add(str);
							
							selectSql+="'"+str+"',";
					}
					
					selectSql = selectSql.substring(0,selectSql.length()-1);
					selectSql+=")";
					
					List<JqAssetAccount> listJqAssetAccount = JqAccountUtil.getJqAccountAsset(dbPath, selectSql);
					
					
					 dto.put("data", listJqAssetAccount);
					 
					 operType = Utils.Mes_OperType_Split;
					 
					 isJqAccount =true;
				}
				
				
				dto.put("isJqAccount", isJqAccount);
				dto.put("operType", operType);
				dto.put("mes", topicMes.getMessage());
				
				
				Dto outDto = new BaseDto();
				outDto.put("flag", true);
				outDto.put("result", dto);
				 
				String mes = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
				
				MqHelper.sendMessage(topicName,mes);
				
				topicMapper.delMes(topicMes.getId());
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		} 
	}
	
	
}
