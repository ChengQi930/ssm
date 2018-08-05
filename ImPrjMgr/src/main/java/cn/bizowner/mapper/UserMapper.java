package cn.bizowner.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import cn.bizowner.model.UserInfo;


@Mapper
public interface UserMapper {
	
		public int addUserInfo(@Param("user")UserInfo user);

		public int delUserInfo(@Param("userId")String userId);
		  
		public int updateUserInfo(@Param("user")UserInfo user);
		
		public UserInfo getUserInfo(@Param("userId")String userId);
		
		public List<UserInfo> getUserList(@Param("mc")String mc,@Param("start")Integer start,@Param("limit")Integer limit);
		
		public int getUserCount(@Param("mc")String mc);
		
		public int isUserIdExist(@Param("userId")String userId);
}
