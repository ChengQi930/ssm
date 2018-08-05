package cn.bizowner.dingtalk.openapi.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceCreateRequest;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceGetRequest;
import com.dingtalk.api.request.SmartworkBpmsProcessinstanceListRequest;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceCreateResponse;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceGetResponse;
import com.dingtalk.api.response.SmartworkBpmsProcessinstanceListResponse;
import com.taobao.api.ApiException;

import cn.bizowner.dingtalk.openapi.env.Env;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.DingDingExamineResult;
import cn.bizowner.model.DingDingUserInfo;

public class ExamineHelper {
	
		public static String sendExamine(String accessToken,String originatorUserId,Long deptId,String approvers,HashMap<String,String> param) throws Exception
		{
			
			try
			{
				DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
				
				SmartworkBpmsProcessinstanceCreateRequest req = new SmartworkBpmsProcessinstanceCreateRequest();
				
				req.setAgentId(Env.ExamineAgentId);
				
				req.setProcessCode(Env.ExamineProcessCode);
				
				req.setOriginatorUserId(originatorUserId); //发起人id
				 
				req.setDeptId(deptId);    //发起人部门id
				  
				req.setApprovers(approvers); //审批人id
				
				List<SmartworkBpmsProcessinstanceCreateRequest.FormComponentValueVo> list =new ArrayList<>();
				
				
				
				Iterator iter = param.entrySet().iterator();
				while (iter.hasNext()) 
				{
					Map.Entry entry = (Map.Entry) iter.next();
					String key = entry.getKey().toString();
					String val = entry.getValue().toString();
					
					SmartworkBpmsProcessinstanceCreateRequest.FormComponentValueVo obj = new SmartworkBpmsProcessinstanceCreateRequest.FormComponentValueVo();
					
					obj.setName(key);
					obj.setValue(val);
					
					list.add(obj);   //添加审批信息
					
				}
				
				req.setFormComponentValues(list);
				
				SmartworkBpmsProcessinstanceCreateResponse rsp = client.execute(req, accessToken);
				
				String reponse = rsp.getBody();
				
				if(reponse != null)
				{
						JSONObject jo = JSONObject.parseObject(reponse);
						
						JSONObject processinstance = jo.getJSONObject("dingtalk_smartwork_bpms_processinstance_create_response");
						
						JSONObject result = processinstance.getJSONObject("result");
						
						int errcode = result.getIntValue("ding_open_errcode");
						if(errcode == 0)
						{ 
								String processInstanceId = result.getString("process_instance_id");
								
								return processInstanceId;
						}
				}
			}
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			
			return null;
		}
		
		//获取单条审批信息！
		public static DingDingExamineResult getExamineResult(String accessToken,String  processInstanceId)
		{
			
			try
			{
				DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
				SmartworkBpmsProcessinstanceGetRequest req = new SmartworkBpmsProcessinstanceGetRequest();
				req.setProcessInstanceId(processInstanceId);
				SmartworkBpmsProcessinstanceGetResponse rsp = client.execute(req, accessToken);
				
				String reponse = rsp.getBody();
				
				if(reponse != null)
				{
					JSONObject jo = JSONObject.parseObject(reponse);
					
					JSONObject processinstance = jo.getJSONObject("dingtalk_smartwork_bpms_processinstance_get_response");
				
					JSONObject result = processinstance.getJSONObject("result");
					
					int errcode = result.getIntValue("ding_open_errcode");
					if(errcode == 0)
					{
							JSONObject process_instance = result.getJSONObject("process_instance");
							String status = process_instance.getString("status");
							if(status != null && status.equals("COMPLETED"))
							{
								JSONObject operation_records = process_instance.getJSONObject("operation_records");
							
								JSONArray operation_records_vo = operation_records.getJSONArray("operation_records_vo");
								
								for(int i=0;i<operation_records_vo.size();i++)
								{
									JSONObject operation_record = operation_records_vo.getJSONObject(i);
									
									String operation_type = operation_record.getString("operation_type");
									if(operation_type.equals("EXECUTE_TASK_NORMAL"))
									{
											String date = operation_record.getString("date");
											String operation_result = operation_record.getString("operation_result");
											String remark = operation_record.getString("remark");
											
											
											
											DingDingExamineResult dingDingExamineResult = new DingDingExamineResult();
											
											
											dingDingExamineResult.setFinishTime(date);
											dingDingExamineResult.setResult(operation_result);
											dingDingExamineResult.setRemark(remark); 
											
									
											return dingDingExamineResult;	
									}
								}
							}
							
					}
				}
			}
			catch(Exception ex)
			{
					String s = ex.getMessage();
			}
			
			return null;
		}
	
		//批量获取 指定某一个项目的审批结果
		public static LinkedHashMap<Object, Object> getBatchApplyFor(String proNo,String size){
			try {
				DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
				SmartworkBpmsProcessinstanceListRequest req = new SmartworkBpmsProcessinstanceListRequest();
				req.setProcessCode(Env.ImplementationDaily);   //必须
				req.setStartTime(Env.StartTime);     //开始时间必须
				/*req.setEndTime(1531756800000L);   //结束时间*/
				//每页请求多少页审批结果
				req.setSize(Long.parseLong(size));
				req.setCursor(0L);
				
				String accessToken = AuthHelper.getAccessToken();
				SmartworkBpmsProcessinstanceListResponse rsp = client.execute(req, accessToken);
				
				String reponse = rsp.getBody();
				
				if(reponse != null){
					JSONObject jo = JSONObject.parseObject(reponse);
					JSONObject processinstance_list = jo.getJSONObject("dingtalk_smartwork_bpms_processinstance_list_response");
					JSONObject result = processinstance_list.getJSONObject("result");
					int errcode = result.getIntValue("ding_open_errcode");
					if(errcode == 0){
						JSONObject resultNext = result.getJSONObject("result");
						int next_cursor = resultNext.getIntValue("next_cursor");
						  /* if(next_cursor != 0){*/
							   JSONObject list = resultNext.getJSONObject("list");
								JSONArray process_instance_top_vo = list.getJSONArray("process_instance_top_vo");
								//将json倒叙
								Collections.reverse(process_instance_top_vo);
								//审批参数值！
								 LinkedHashMap<Object, Object> outDto  = new LinkedHashMap<>();  //47c10fe5-cae5-4f7b-b5f1-f58188e5dfc2
								
								for(int i=0;i<process_instance_top_vo.size();i++){
									JSONObject process_instance_top = process_instance_top_vo.getJSONObject(i);
									 String status = process_instance_top.getString("status");
									  if(status!=null&&status.equals("COMPLETED")){
										  //该条审批项目的唯一id;
								  String process_instance_id = process_instance_top.getString("process_instance_id");
								//该项目的开始时间
								  String create_time = process_instance_top.getString("create_time");
								    //该项目的结束时间
								  String finish_time = process_instance_top.getString("finish_time");
								  /*JSONObject approver_userid_list = process_instance_top.getJSONObject("approver_userid_list");
								  JSONArray approver_string = approver_userid_list.getJSONArray("string");*/
								   /* //审批人id   approver
								      ArrayList<Object> approver = new ArrayList<Object>();
								         for(int j=0;j<approver_string.size();j++){
								        	 
								        	 String approver_id = approver_string.getString(j);
								        	 DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(approver_id);
								        	 approver.add(userInfoByUserId.getMc());
								         }*/
								         
								         /*JSONObject cc_userid_list = process_instance_top.getJSONObject("cc_userid_list");
										  JSONArray cc_string = cc_userid_list.getJSONArray("string");*/
										  /* //抄送人id  cc 
										  ArrayList<Object> cc = new ArrayList<Object>();
										         for(int j=0;j<cc_string.size();j++){
										        	 String cc_id = cc_string.getString(j);
										        	 DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(cc_id);
										        	 cc.add(userInfoByUserId.getMc());
										         }    */   
								         
										 JSONObject form_component_values = process_instance_top.getJSONObject("form_component_values"); 
										 JSONArray form_component_value_vo = form_component_values.getJSONArray("form_component_value_vo");
										    
										 
										 /*HashMap<Object, Object> form_component_value = new HashMap<>();*/
										
										     for(int k=0;k<form_component_value_vo.size();k++){
										    	 JSONObject jsonObject_i = form_component_value_vo.getJSONObject(k);
										    	    String name = jsonObject_i.getString("name");
										    	    String value = jsonObject_i.getString("value");
										    	    if(name.equals("项目编号:")&& value.equals(proNo)){
										    	    	
										    	    for(int r=k;r<form_component_value_vo.size();r++){
										    	    		 JSONObject jsonObject = form_component_value_vo.getJSONObject(r);
													    	    String name2 = jsonObject.getString("name");
													    	    String value2 = jsonObject.getString("value");
										    	    if(name2.equals("项目阶段")){
										    	    	String replacevalue = value2.replace("[", "").replace("\"","").replace("\"", "").replace("]", "");
										    	    	outDto.put(replacevalue, finish_time);
										    	    	 break;
										    	    }
										    	     
										    	    	 }
										    	   break; 
										    	    
										    	 }
										     }
										     /*outDto.put("finish_time", finish_time);    //结束时间
*/										     
										    /* outDto.put("process_instance_id", process_instance_id);  //该审批条目的唯一id
*/										       /*outDto.put("create_time", create_time);   //开始时间
*/										       
										       /*outDto.put("approver_string", approver);  //审批人id
*/										       /*outDto.put("cc_string", cc);              //抄送人id
*/										       /*outDto.put("form_component_value_vo", form_component_value);*/   //审批条目的所有信息
										       
									  }
									  
								}
								return  outDto; 
						   /*}*/
					}
				}
			} catch (Exception e) {
				String s = e.getMessage();
			}
			return null;
		}
}
