package cn.bizowner.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.bizowner.model.Approval;
import cn.bizowner.model.ProjectPlanList;


public interface ApprovalService {
      
	//增加
	public int AddApproval(Approval approval)throws Exception;
	
    //按id查询  和 查询所有
	public ArrayList<Approval> getApproval(String planId) throws Exception;
	
	
	
	//查询出审批记录
	public ArrayList<Approval> getApprovalInthe()throws Exception;
	
	//用户关闭后 或者 再次提交申请后的查询！
	public ArrayList<Approval> getUnsatisfactory(String ProId) throws Exception;
	
	//根据钉钉批量查询过来的 planTitle值查询出 对应的阶段时间！
    public ArrayList<ProjectPlanList> getPlanTitle(@Param("planTitle")String planTitle);
    
    //修改阶段时间  开始时间为null时
    public int  UpdateProjectDate(String startDate,String endDate,String type);
    
    //开始时间不为null
    public int  UpdateProjectDateNoNull(String endDate, String type);
    
  //查询出 不为本系统维护的 其他几条阶段信息
  	 public ArrayList<ProjectPlanList> getProjectPlan();
}
