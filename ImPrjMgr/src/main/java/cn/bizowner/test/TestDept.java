package cn.bizowner.test;

import java.util.List;

import cn.bizowner.dingtalk.openapi.helper.DeptHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.model.UserDept;
import cn.bizowner.model.DingDingUserInfo;

public class TestDept {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*List<DeptInfo> listDept = DeptHelper.getDeptList();
		
		for(int i=0;i<listDept.size();i++)
		{
				DeptInfo deptInfo = listDept.get(i);
				
				System.out.println(deptInfo.toString());
				
				List<DingDingUserInfo> listUserInfo = DeptHelper.getUserInfoByDeptId(deptInfo.getId());
				for(int j=0;j<listUserInfo.size();j++)
				{
					DingDingUserInfo userInfo = listUserInfo.get(j);
					System.out.println(userInfo.toString());
				}
				
				System.out.println("****************");
		}*/
		
		String deptId = DeptHelper.getDeptIdByName("实施部");
		
		List<Dto> listDto = DeptHelper.getChildDeptsByDeptId(deptId);
		for(int i=0;i<listDto.size();i++)
		{
				System.out.println(listDto.get(i).getAsString("id"));
				System.out.println(listDto.get(i).getAsString("name"));
		}
			
	}

}
