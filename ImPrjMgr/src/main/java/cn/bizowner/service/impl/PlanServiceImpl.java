package cn.bizowner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.activemq.MqHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto; 
import cn.bizowner.mapper.PlanMapper;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanTypeMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;
import cn.bizowner.model.PlanUser;
import cn.bizowner.service.PlanService;



@Service("planService")
public class PlanServiceImpl implements PlanService {
 
	@Autowired
	private PlanMapper planMapper;
	
	
	@Autowired
	private PlanTypeMapper planTypeMapper;
	
	
	@Autowired
	private PlanUserMapper planUserMapper;

	@Override
	public void addPlan(Plan plan,List<PlanUser> listPlanUser,String topicName) throws Exception 
	{
			
		
		
			int count = planMapper.isPlanExist(plan.getProId(), plan.getType());
			if(count == 0)
			{
					planMapper.addPlan(plan);
					
					for(int i=0;i<listPlanUser.size();i++)
					{
							planUserMapper.addPlanUser(listPlanUser.get(i));
					}
					
					
					if(plan.getType() == 1 || plan.getType() == 2)			//只有对账计划类型才需要创建topic的
					{
							MqHelper.createTopic(topicName);
					}
			}
			else
			{
					throw new Exception("该计划类型已存在,请重新添加");	
			}
			
			
			
			
	}
	
	
	
	
	
	@Override
	public void updatePlan(Plan plan,List<PlanUser> listPlanUser) throws Exception 
	{
		
		
			int count = planMapper.isPlanExist(plan.getProId(), plan.getType());
			if(count ==0)
			{
					String planId = plan.getPlanId();
					
					planMapper.updatePlan(plan);
					
					planUserMapper.delPlanUser(planId);
				
					for(int i=0;i<listPlanUser.size();i++)
					{
							planUserMapper.addPlanUser(listPlanUser.get(i));
					}
			}
			else
			{
					throw new Exception("该计划类型已存在,请重新修改");	
			}
		
			
	}
	
	

	@Override
	public void delPlan(String planId) throws Exception 
	{
			planMapper.delPlan(planId);
			planUserMapper.delPlanUser(planId);
	}
 
	
	@Override
	public Dto getPlanList(PlanQueryInfo planQueryInfo,Integer start,Integer limit) throws Exception 
	{
		
				Dto outDto = new BaseDto();
		
				List<Plan> listPlan =  planMapper.getPlanList(planQueryInfo,start,limit);
				
				for(int i=0;i<listPlan.size();i++)
				{
						
					
						Plan plan = listPlan.get(i);
						 
						List<Dto> listDto =  planUserMapper.getUserIdInfoByPlanId(plan.getPlanId());
						
						List<String> listString = new ArrayList<String>();
						
						for(int j=0;j<listDto.size();j++)
						{ 
								listString.add(listDto.get(j).getAsString("mc"));
						}
						
						plan.setImpUsers(listString);
						
				}
				
				int count = planMapper.getPlanCount(planQueryInfo);			
				
				outDto.put("list", listPlan);
				outDto.put("count", count);
				
				return outDto;
				
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Plan getPlanInfoById(String planId) throws Exception 
	{ 
				Plan plan=  planMapper.getPlanInfoById(planId); 
				
				List<Dto> listDto = planUserMapper.getUserIdInfoByPlanId(planId);
				  
				plan.setImpUsers(listDto);
						 
				return plan; 
	}
	
	
	





	@Override
	public List<PlanType> getPlanTypeList() {
		
		List<PlanType> listPlanType = planTypeMapper.getPlanTypeList();
		
		return listPlanType;
	}

	@Override
	public String getEndPlanId(String proId, String type) {
		List<Plan> endPlanId = planMapper.getEndPlanId(proId,type);
		String planId = null;
		 for (Plan plan : endPlanId) {
			 planId = plan.getPlanId();
			
		}
		 return planId;
	}





	@Override
	public ArrayList<Plan> getproStatus(String proId) {
		return planMapper.getproStatus(proId);
	}
	
}
