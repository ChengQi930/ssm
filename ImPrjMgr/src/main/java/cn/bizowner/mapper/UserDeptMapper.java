package cn.bizowner.mapper; 
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.bizowner.model.UserDept; 


@Mapper
public interface UserDeptMapper {
	
		public int addUserDept(@Param("userDept")UserDept userDept);
		
		public int delUserDeptByUserId(@Param("userId")String userId);
		
		public List<UserDept> getUserDeptByUserId(@Param("userId")String userId);
	
}
