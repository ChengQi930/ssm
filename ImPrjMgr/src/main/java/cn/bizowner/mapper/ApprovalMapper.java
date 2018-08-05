package cn.bizowner.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.Approval;
import cn.bizowner.model.DingDingExamineResult;
import cn.bizowner.model.ProjectPlanList;

@Mapper
public interface ApprovalMapper {
     
	//增加
	public int AddApproval(@Param("approval") Approval approval)throws Exception;
	
	//根据 planid 查询和查询所有！
	public ArrayList<Approval> getApproval(@Param("planId") String planId)throws Exception;
	
	
	//修改Approval表中的三个字段！
	public int UpdateApproval(@Param("Ding") DingDingExamineResult Ding,@Param("processInstanceId")String processInstanceId)throws Exception;
	//修改Approval表中的planstatus
	public int Updateplan(@Param("planId") String planId);
	
	//查询出库中有多少条审批中的审批数据
	public ArrayList<Approval> getApprovalInthe()throws Exception;
	
	//不理想状态下 用户看见拒绝后 还会 再次提交审批！
	 public ArrayList<Approval> getUnsatisfactorycondition(@Param("ProId")String ProId)throws Exception;
	 
	//根据钉钉批量查询过来的 planTitle值查询出 对应的阶段时间！
	 public ArrayList<ProjectPlanList> getPlanTitle(@Param("planTitle")String planTitle);
	 
	//修改项目阶段的最后时间 一开始为null情况
	 public int  UpdateProjectDate(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("type") String type);
	
	//不为null的情况
	 public int  UpdateProjectDateNoNull(@Param("endDate")String endDate,@Param("type") String type);
	 
	//查询出 不为本系统维护的 其他几条阶段信息
	 public ArrayList<ProjectPlanList> getProjectPlan();
}
