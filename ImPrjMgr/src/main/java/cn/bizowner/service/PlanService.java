package cn.bizowner.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;
import cn.bizowner.model.PlanUser;
public interface PlanService {
			
	public void addPlan(Plan plan,List<PlanUser> listPlanUser,String topicName) throws Exception;
	
	public void updatePlan(Plan plan,List<PlanUser> listPlanUser) throws Exception;
	
	public void delPlan(String plan) throws Exception;

	public Dto getPlanList(PlanQueryInfo planQueryInfo,Integer start,Integer limit) throws Exception;

	public List<PlanType> getPlanTypeList();
	
	public Plan getPlanInfoById(String planId) throws Exception;
	
	//查询最新的一条项目计划id
	public String getEndPlanId(String proId,String type);
	
	//根据proid 查询  本系统维护的数据
	public ArrayList<Plan> getproStatus(String proId);
}
