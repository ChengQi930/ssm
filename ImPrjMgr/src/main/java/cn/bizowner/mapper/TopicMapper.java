package cn.bizowner.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.TopicMes;


@Mapper
public interface TopicMapper {
	
	
		public int addMes(@Param("topicMes")TopicMes topicMes);
	
		public int delMes(@Param("id")String id);
		
		public List<TopicMes> getMesList();
		
		public int getMesCount();
}
