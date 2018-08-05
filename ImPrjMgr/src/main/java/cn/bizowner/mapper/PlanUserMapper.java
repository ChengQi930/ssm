package cn.bizowner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanUser;



@Mapper
public interface PlanUserMapper {

		public int isHavePerm(@Param("userId")String userId,@Param("proId")String proId,@Param("type")Integer type);
	
		public int addPlanUser(@Param("planUser")PlanUser planUser);

		public int delPlanUser(@Param("planId")String planId);
		
		public List<Dto> getUserIdInfoByPlanId(@Param("planId")String planId);
}
