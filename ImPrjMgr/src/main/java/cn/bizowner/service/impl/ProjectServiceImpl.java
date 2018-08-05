package cn.bizowner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto; 
import cn.bizowner.mapper.PlanMapper;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.mapper.ProjectMapper;
import cn.bizowner.mapper.UserMapper;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.UserInfo;
import cn.bizowner.service.CommonService;
import cn.bizowner.service.ProjectService;
import cn.bizowner.service.TopicService;
import cn.bizowner.sqlite.CwAccountUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.FileUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.Utils;



@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
 
	@Autowired
	private ProjectMapper projectMapper;
	
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Autowired
	private PlanMapper planMapper;
	
	@Autowired
	private PlanTopicMapper planTopicMapper;
	

	@Override
	public void addPro(ProjectInfo projectInfo) throws Exception 
	{
				int ret = projectMapper.addPro(projectInfo);
				if(ret != 1)
				{
						throw new Exception("添加失败");
				}
				
				
				String dbPath = Utils.dbPath+projectInfo.getProId()+".db";
				
				String tempDbPath = Utils.dbPath+"temp.db";
				
				FileUtils.copyFile(tempDbPath, dbPath);
				
	}
	
	
	
	
	@Override
	public void updatePro(ProjectInfo projectInfo) throws Exception 
	{
				int ret = projectMapper.updatePro(projectInfo);
				if(ret != 1)
				{
						throw new Exception("修改失败");
				}
	}
	
	

	@Override
	public Dto getProList(Integer mainStatus,Integer subStatus, String proName, Integer start, Integer limit) throws Exception
	{
			if(subStatus != null && mainStatus==null)
			{
						if(subStatus == Utils.Project_Status_Sub_Running_Normal || subStatus == Utils.Project_Status_Sub_Running_Exceed)
						{
							mainStatus = Utils.Project_Status_Main_Running;
						}
						else if(subStatus == Utils.Project_Status_Sub_Finish_Advance || subStatus == Utils.Project_Status_Sub_Finish_Normal || subStatus==Utils.Project_Status_Sub_Finish_Exceed)
						{
							mainStatus = Utils.Project_Status_Main_Finish;
						}
						else
						{
							mainStatus = Utils.Project_Status_Main_Stop;
						}
			}
		
			Dto dto = new BaseDto();
			
			int count = projectMapper.getProjectCount(mainStatus,subStatus, proName);
			
			if(limit == 0)
			{ 
					limit = count;
			}
			List<ProjectInfo> listProjectInfo = projectMapper.getProjectList(mainStatus,subStatus, proName, start, limit);
		  
			
			
			for(int i=0;i<listProjectInfo.size();i++)
			{
					setProStatus(listProjectInfo.get(i));
			}
			
			
			dto.put("list", listProjectInfo);
			dto.put("count", count);
			
			return dto;
	}

	@Override
	public ProjectInfo getProjectInfoById(String proId) {
			return projectMapper.getProjectInfoById(proId);
	}

	
	
	
	
	@Override
	public ProjectInfo getProInfoById(String proId) throws Exception{
		
		
			ProjectInfo projectInfo = projectMapper.getProjectInfoById(proId);
			
			setProStatus(projectInfo);
			  
			UserInfo createUserInfo = userMapper.getUserInfo(projectInfo.getCreateUser().toString());
			UserInfo impUserInfo = userMapper.getUserInfo(projectInfo.getImpUser().toString());
			UserInfo saleUserInfo = userMapper.getUserInfo(projectInfo.getSaleUser().toString());

			
			Dto createUserDto = new BaseDto();
			createUserDto.put("id", createUserInfo.getUserId());
			createUserDto.put("mc", createUserInfo.getMc());
			
			//String createUserJs = JSONHelper.encodeObject2Json(createUserDto);
			
			
			Dto impUserDto = new BaseDto();
			impUserDto.put("id", impUserInfo.getUserId());
			impUserDto.put("mc", impUserInfo.getMc());
			
			//String impUserJs = JSONHelper.encodeObject2Json(impUserDto);
			
			Dto saleUserto = new BaseDto();
			saleUserto.put("id", saleUserInfo.getUserId());
			saleUserto.put("mc", saleUserInfo.getMc());
			
			//String saleUserJs = JSONHelper.encodeObject2Json(saleUserto);
		
			
			projectInfo.setCreateUser(createUserDto);
			projectInfo.setImpUser(impUserDto);
			projectInfo.setSaleUser(saleUserto);
			
			return projectInfo;
	}
	
	



	@Override
	public void delPro(String proId) throws Exception {
		
				String dbPath = Utils.dbPath+proId+".db";
				
				int count = CwAccountUtil.getCwAccountCount(dbPath,null,null);
				
				if(count > 0)		
				{
					ProjectInfo projectInfo = new ProjectInfo();
					projectInfo.setProStatus(Utils.Project_Status_Main_Stop);
					projectInfo.setProId(proId);
					projectMapper.updatePro(projectInfo);
				}
				else
				{
					
					PlanQueryInfo planQueryInfo = new PlanQueryInfo();
					planQueryInfo.setProId(proId);
					
					List<Plan>  listPlan =  planMapper.getPlanList(planQueryInfo,0,0);
					
					for(int i=0;i<listPlan.size();i++)
					{
						planTopicMapper.delTopicByPlanId(listPlan.get(i).getPlanId());
					}
					
					planMapper.delPlanByProId(proId);
					
					projectMapper.delPro(proId);
				}
			
	}
	
	
	
	public void setProStatus(ProjectInfo proInfo)
	{
		
			String currDate = CommonUtils.getCurrDate();
		
			int status = proInfo.getProStatus();
			if(status == Utils.Project_Status_Main_Running)
			{ 
					status = Utils.Project_Status_Sub_Running_Normal;
					if(proInfo.getEstTime() != null)
					{
						if(CommonUtils.convertDateToString(proInfo.getEstTime()).compareTo(currDate)>0)
						{
							status = Utils.Project_Status_Sub_Running_Normal;
						}
						else
						{
							status = Utils.Project_Status_Sub_Running_Exceed;
						}
					} 
					
					proInfo.setProStatus(status);
			}
			else if(status == Utils.Project_Status_Main_Finish)
			{ 
					status = Utils.Project_Status_Sub_Finish_Normal;
					
					if(proInfo.getEstTime() != null && proInfo.getFinishTime() !=null)
					{
						if(proInfo.getEstTime().compareTo(proInfo.getFinishTime())<0)
						{
							status = Utils.Project_Status_Sub_Finish_Advance;
						}
						else if(proInfo.getEstTime().compareTo(proInfo.getFinishTime())==0)
						{
							status = Utils.Project_Status_Sub_Finish_Normal;
						}
						else
						{
							status = Utils.Project_Status_Sub_Running_Exceed;
						}
					}
				 
					proInfo.setProStatus(status);
			}
			else if(status == Utils.Project_Status_Main_Stop)
			{
					status = Utils.Project_Status_Sub_Stop;
					proInfo.setProStatus(status);
			}
	}
	
	
	
	
	@Override
	public String getproNo(String proId) throws Exception {
		return projectMapper.getproNo(proId);
	}




	@Override
	public String getUserIdById(String proId) throws Exception {
		
		return projectMapper.getUserIdById(proId);
	}
	
}
