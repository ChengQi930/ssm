package cn.bizowner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ProjectPlanList {
    private String planId;
    
    private Date startDate;
    private Date endDate;
    private String proId;
    private String listType;
    private String phaseAmount;
    
    private Set<ProjectPlanData> ProjectPlan = new HashSet<ProjectPlanData>();
	
    
    
	public String getPhaseAmount() {
		return phaseAmount;
	}
	public void setPhaseAmount(String phaseAmount) {
		this.phaseAmount = phaseAmount;
	}
	public Set<ProjectPlanData> getProjectPlan() {
		return ProjectPlan;
	}
	public void setProjectPlan(Set<ProjectPlanData> projectPlan) {
		ProjectPlan = projectPlan;
	}
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	@Override
	public String toString() {
		return "ProjectPlanList [planId=" + planId + ", startDate=" + startDate + ", endDate=" + endDate + ", proId="
				+ proId + ", listType=" + listType + ", phaseAmount=" + phaseAmount + ", ProjectPlan=" + ProjectPlan
				+ "]";
	}
	
    
}
