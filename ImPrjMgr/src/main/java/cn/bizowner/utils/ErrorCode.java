package cn.bizowner.utils;

public class ErrorCode {

	
			public static int USER_FORBIDDEN=33001;			//用户被禁止登陆     数据库中的enableFlag为false
			public static int USER_NOROLE_ASSIGN=33002;		//没有给用户分配角色
			public static int USER_NOLOGIN=33003;			//用户未登陆
			
			
			public static int CRM_EMPTY_INFO=34001;				//crm用户信息为空
			public static int CRM_INVALID_USERNAME=34002;		//用户名错误
			public static int CRM_INVALID_PASSWORD=34003;		//密码错误
			public static int CRM_LOG_FAIL=34005;				//登录失败
			public static int CRM_GET_FAIL=34009;				//获取失败
	
}
