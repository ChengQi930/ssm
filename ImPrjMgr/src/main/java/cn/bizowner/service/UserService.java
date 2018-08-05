package cn.bizowner.service;

import java.util.List;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.UserInfo;
import cn.bizowner.model.UserRole;

public interface UserService { 
	
     public Dto getUserList(String mc,Integer start,Integer limit) throws Exception;
     
     public UserInfo getUserInfo(String userId) throws Exception;
      
     //增加用户！
     public void addUser(UserInfo user) throws Exception;
     
     public void addUser(List<UserInfo> listUserInfo) throws Exception;
     
     //删除用户
     public void delUser(String userId) throws Exception;
     
     //修改用户状态 
     public void updateUser(UserInfo userInfo,List<UserRole> listUserRole) throws Exception;
}
