package cn.bizowner.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface PlanTopicMapper {
	
	public String getTopicId(@Param("proId")String proId,@Param("type")Integer type);
	
	
	public int delTopicByPlanId(@Param("planId")String planId);

}
