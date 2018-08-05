package cn.bizowner.test;

import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.ExamineHelper;
import cn.bizowner.model.DingDingExamineResult;
import cn.bizowner.utils.TimeUtil;

public class TestExamine {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
		/*String accessToken = AuthHelper.getAccessToken();
		
		
		DingDingExamineResult dingDingExamineResult = new DingDingExamineResult();
		
		boolean flag = ExamineHelper.getExamineResult(accessToken, "92d61978-f9a5-4c0e-8205-239b9348a583", dingDingExamineResult);
		
		if(flag == true)
		{
				System.out.println(dingDingExamineResult.getFinishTime());
				System.out.println(dingDingExamineResult.getResult());
				System.out.println(dingDingExamineResult.getRemark());
		}*/
	    /*long time = new Date().getTime();
	    String yearMonthDayHourMinuteSecond = TimeUtil.getYearMonthDayHourMinuteSecond(time);
	    System.out.println(yearMonthDayHourMinuteSecond);*/
		
		/*HashMap<Object, List> hashMap = new HashMap<>();*/
		ArrayList<Object> arrayList = new ArrayList<>();
		
		arrayList.add("accessToken");
		arrayList.add("sendExamine");
		for(int i=0;i<arrayList.size();i++){
			System.out.println(arrayList.get(i));
		}
		/*hashMap.put("93f1a3b1-2a15-4228-9110-cb4dba39f90s",arrayList);
		hashMap.put("123",arrayList);
		for(Map.Entry<Object, List> entry : hashMap.entrySet()){
			Object key = entry.getKey();
			System.out.println(key);
			List value = entry.getValue();
			Object object = value.get(0);
			System.out.println(key + ":" + object);
		}
		int size = hashMap.size();
		System.out.println(size);
		List list = hashMap.get("93f1a3b1-2a15-4228-9110-cb4dba39f90s");
		for (Object object : list) {
			System.out.println(object);
			
		}
		Set<Object> keySet = hashMap.keySet();
		String num = "123";
		for (Object object : keySet) {
			System.out.println(object);
		}
		
		boolean equals = num.equals(hashMap.keySet());
		System.out.println(equals);
		System.out.println(keySet);*/
		
		
	}

}
