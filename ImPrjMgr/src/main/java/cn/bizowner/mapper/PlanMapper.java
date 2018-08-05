package cn.bizowner.mapper;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;


@Mapper
public interface PlanMapper {
	
		public int addPlan(@Param("plan")Plan plan);
		
		public int updatePlan(@Param("plan")Plan plan);
		
		public int delPlan(@Param("planId")String planId);
		
		public int delPlanByProId(@Param("proId")String proId);
		
		public List<Plan> getPlanList(@Param("planQueryInfo")PlanQueryInfo planQueryInfo,@Param("start")Integer start,@Param("limit")Integer limit);
		
		public int getPlanCount(@Param("planQueryInfo")PlanQueryInfo planQueryInfo);
		
		public Plan getPlanInfoById(@Param("planId")String planId);
		
		public int isPlanExist(@Param("proId")String proId,@Param("type")Integer type);
		
		//查询最新的一条项目计划id
		public List<Plan> getEndPlanId(@Param("proId") String proId,@Param("type") String type);
		
		//根据项目proId获取 项目 状态
		public ArrayList<Plan> getproStatus(@Param("proId") String proId);
		
}
