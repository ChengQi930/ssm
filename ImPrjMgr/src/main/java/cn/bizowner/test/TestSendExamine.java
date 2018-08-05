package cn.bizowner.test;

import java.util.HashMap;
import java.util.Map;

import cn.bizowner.dingtalk.openapi.env.Env;
import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.ExamineHelper;


public class TestSendExamine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try
		{
				HashMap<String,String> hashMap = new HashMap<String,String>();
				
				hashMap.put("项目名称", "1234567");
				hashMap.put("开始时间", "2018-04-24 13:33:27");
				hashMap.put("完成时间", "2018-04-24 14:33:27");
				hashMap.put("备注","1");
				 
				
				Long deptId = 64509127L;
				
				String chensiyuUserId = "191908680737772979";
				String liujieUserId = "0951370504677976";
				 
				String   processInstanceId =  ExamineHelper.sendExamine(AuthHelper.getAccessToken(), chensiyuUserId, deptId, liujieUserId, hashMap);
		
				System.out.println(processInstanceId);
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
				
				System.out.println(s);
		}
		

		
		
	}

}
