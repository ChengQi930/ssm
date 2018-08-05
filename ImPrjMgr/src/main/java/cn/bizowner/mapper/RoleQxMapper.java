package cn.bizowner.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.RoleQx;
import cn.bizowner.model.RoleQxInfo; 


@Mapper
public interface RoleQxMapper {
	
	public List<RoleQxInfo> getRoleQxList(@Param("userId")String userId,@Param("parentId")String parentId);
	 
	public List<RoleQxInfo> getFunctionListByParentId(@Param("roleId")String roleId,@Param("parentId")String parentId);
	 
	public int isRecordExist(@Param("roleId")String roleId,@Param("funcId")String funcId);
	
	public int addRoleQx(@Param("roleQx")RoleQx roleQx);
	
	public int updateRoleQx(@Param("roleQx")RoleQx roleQx);
}
