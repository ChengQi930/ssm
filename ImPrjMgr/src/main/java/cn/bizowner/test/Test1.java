package cn.bizowner.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.utils.CommonUtils;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*String fileName = "西安市旅游局行政账2016年科目账";
		
		String year = CommonUtils.getCurrentTime("yyyy");
        int index = fileName.indexOf("年");
        if(index-4>0)
        {
        	year = fileName.substring(index-4,index);
        }
		
        System.out.println(year);*/
		
		
		String s = "/User/Test";
		String s1 = "/User/Test";
		
		int a = s.indexOf(s1);
		
		System.out.println(a);
		
	}
	@Test
	public void testName() throws Exception {
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("1");
		arrayList.add("2");
		arrayList.add("3");
		
		StringBuffer stringBuffer = new StringBuffer();
	    for(int i=0;i<arrayList.size();i++){
	    	String menid = arrayList.get(i);
	      
		/*DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(menid);
		   String menMc = userInfoByUserId.getMc();*/
		    stringBuffer.append(menid+",");
	    }
	    System.out.println(stringBuffer.deleteCharAt(stringBuffer.length()-1));
	}

}
