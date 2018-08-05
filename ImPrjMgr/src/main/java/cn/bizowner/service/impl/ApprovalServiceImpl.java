package cn.bizowner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.mapper.ApprovalMapper;
import cn.bizowner.model.Approval;
import cn.bizowner.model.ProjectPlanList;
import cn.bizowner.service.ApprovalService; 

@Service
public class ApprovalServiceImpl implements ApprovalService{

	@Autowired
	private ApprovalMapper Approval;
	
	
	
	@Override
	public int AddApproval(Approval approval) throws Exception {
		
		int addApproval = Approval.AddApproval(approval);
		if(addApproval == 1){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public ArrayList<Approval> getApproval(String planId) throws Exception {
		   return Approval.getApproval(planId);
	}

	
	
	@Override
	public ArrayList<Approval> getApprovalInthe() throws Exception {
		 ArrayList<Approval> approvalInthe = Approval.getApprovalInthe();
		return approvalInthe;
	}

	@Override
	public ArrayList<Approval> getUnsatisfactory(String ProId) throws Exception {
		ArrayList<Approval> unsatisfactorycondition = Approval.getUnsatisfactorycondition(ProId);
		return unsatisfactorycondition;
	}
    
	//获取阶段的名称
	@Override
	public ArrayList<ProjectPlanList> getPlanTitle(String planTitle) {
		return Approval.getPlanTitle(planTitle);
	}

	@Override
	public int UpdateProjectDate(String startDate, String endDate, String type) {
		return Approval.UpdateProjectDate(startDate, endDate, type);
	}

	@Override
	public int UpdateProjectDateNoNull(String endDate, String type) {
		return Approval.UpdateProjectDateNoNull(endDate, type);
	}

	@Override
	public ArrayList<ProjectPlanList> getProjectPlan() {
		return Approval.getProjectPlan();
	}

	

	

}
