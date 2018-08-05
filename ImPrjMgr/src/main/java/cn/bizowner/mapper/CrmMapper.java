package cn.bizowner.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.CrmInfo;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.UserInfo;


@Mapper
public interface CrmMapper {
	
	
		public int addCrmInfo(@Param("crmInfo")CrmInfo crmInfo);
		
		public int delCrmInfo(@Param("userId")String userId);
		
		public CrmInfo getCrmInfo(@Param("userId")String userId);
	
}
