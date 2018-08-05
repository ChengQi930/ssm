package cn.bizowner.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.bizowner.dto.Dto;
import cn.bizowner.model.Role;


@Mapper
public interface RoleMapper {
	  
			public int addRole(@Param("role")Role role);
			
			public int updateRole(@Param("role")Role role);
			
			public int delRole(@Param("roleId")String roleId);
			
			public List<Role> getRoleList(@Param("roleName")String roleName,@Param("start")Integer start,@Param("limit")Integer limit);
			
			public int getRoleCount(@Param("roleName")String roleName);
			
			public Role getRoleInfo(@Param("roleId")String roleId);
			
			public int isRoleNameExist(@Param("roleName")String roleName);
			
			public List<Role> getRoleInfoByUserId(@Param("userId")String userId);
}
