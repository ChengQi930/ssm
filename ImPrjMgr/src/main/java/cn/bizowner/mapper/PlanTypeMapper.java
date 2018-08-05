package cn.bizowner.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanType;


@Mapper
public interface PlanTypeMapper {
	
		public List<PlanType> getPlanTypeList();
		
		public String getTypeNameByTypeValue(@Param("typeValue")Integer typeValue); 
}
