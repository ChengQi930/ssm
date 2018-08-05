package cn.bizowner.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dingtalk.openapi.helper.DeptHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.UserDeptMapper;
import cn.bizowner.mapper.UserMapper;
import cn.bizowner.mapper.UserRoleMapper;
import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.DeptInfo;
import cn.bizowner.model.UserDept;
import cn.bizowner.model.UserInfo;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.UpdateAssetMgrThreadParam;
import cn.bizowner.service.AssetAccountService;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/DingDing")
public class DingDingController {

		
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	
	@Autowired
	private UserDeptMapper userDeptMapper;
	
	
	@RequestMapping("/AddUserInfo")
	public void  AddUserInfo() 
	{
		
		try
		{
				List<DeptInfo> listDept = DeptHelper.getDeptList();
				 
				List<Dto> listDto = new ArrayList<Dto>();
				
				for(int i=0;i<listDept.size();i++)
				{
						DeptInfo deptInfo = listDept.get(i);
						  
						String deptName = deptInfo.getDeptName();
						
						List<DingDingUserInfo> listUserInfo = DeptHelper.getUserInfoByDeptId(deptInfo.getDeptId());
						for(int j=0;j<listUserInfo.size();j++)
						{
							//DingDingUserInfo userInfo = listUserInfo.get(j);
							
							DingDingUserInfo userInfo = UserHelper.getUserInfoByUserId(listUserInfo.get(j).getUserId());
							
							int count = userMapper.isUserIdExist(userInfo.getUserId());
							if(count == 0)
							{
								System.out.println(userInfo.toString());
								
								String userId = userInfo.getUserId();
								
								String mc = userInfo.getMc();
								 
								UserInfo user = new UserInfo();
								user.setUserId(userId);
								user.setMc(mc);
								user.setEnableFlag('Y');
								
								
								userMapper.addUserInfo(user);
								
								
								if(userInfo.getListDept()!=null)
								{
									for(int k=0;k<userInfo.getListDept().size();k++)
									{
										userDeptMapper.addUserDept(userInfo.getListDept().get(k));
									}
								}
								
								
							}
							
							
							 
						}
						
						System.out.println("****************");
				}
				
				
				/*System.out.println("size="+listDto.size());
				
				for(int i=0;i<listDto.size();i++)
				{
					
					//userMapper.delUserInfo(listDto.get(i).getAsString("userId"));
					
					//userMapper.addDingDingInfo(listDto.get(i).getAsString("userId"), listDto.get(i).getAsString("remark"));
				
					userRoleMapper.delUserRole(listDto.get(i).getAsString("userId"));
					
					//userRoleMapper.addUserRole(CommonUtils.getUUID(), listDto.get(i).getAsString("userId"), "SSRY");
				}*/
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		
		
		
	}
	
	
}
