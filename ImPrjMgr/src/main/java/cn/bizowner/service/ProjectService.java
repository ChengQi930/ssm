package cn.bizowner.service;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.ProjectInfo;

public interface ProjectService {
			
	public void addPro(ProjectInfo projectInfo) throws Exception;
	
	public void updatePro(ProjectInfo projectInfo) throws Exception;
	
	public void delPro(String proId) throws Exception;

	public Dto getProList(Integer mainType,Integer subType, String proName, Integer start, Integer limit) throws Exception;

	public ProjectInfo  getProjectInfoById(String proId);
	
	public ProjectInfo  getProInfoById(String proId) throws Exception;
		 
    //查询审批人id
	public String getUserIdById(String proId) throws Exception;
	//根据proId获取 proNo
    public String getproNo(String proId)throws Exception;
}
