package cn.bizowner.test;

import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.ExamineHelper;
import cn.bizowner.model.DingDingExamineResult;

public class TestGetExamine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		try
		{
			String processInstanceId ="dc67acf9-501c-4cf5-8fd3-483bb018d256";
			
			DingDingExamineResult dingDingExamineResult= ExamineHelper.getExamineResult(AuthHelper.getAccessToken(), processInstanceId);
			
			if(dingDingExamineResult != null)
			{
				System.out.println(dingDingExamineResult.getFinishTime());
				System.out.println(dingDingExamineResult.getRemark());
				System.out.println(dingDingExamineResult.getResult());
			}
					
			
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		
		
	}

}
