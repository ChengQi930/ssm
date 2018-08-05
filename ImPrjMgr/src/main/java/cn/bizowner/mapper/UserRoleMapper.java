package cn.bizowner.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.UserRole;


@Mapper
public interface UserRoleMapper {
	 
		public int addUserRole(@Param("userRole")UserRole userRole);
		
		public int delUserRole(@Param("userId")String userId);
		
		public int isRoleHaveUsers(@Param("roleId")String roleId);
		
		public int isUserHaveRole(@Param("userId")String userId,@Param("roleId")String roleId);
}
