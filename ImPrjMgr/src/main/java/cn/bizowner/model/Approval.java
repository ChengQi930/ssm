package cn.bizowner.model;

import java.util.ArrayList;
import java.util.Date;


public class Approval {
     private String id;
     
     private String applicant;
     
     private String approver;
     
     private Date StartTime;
     
     private Date EnDofTime;
     
     private String results;
     
     private String proId;
     
     private String planId;
     
     private String Describes;
     private String ProcessInstanceId;
     
     
     
     private String applicantMc;
     private String approverMc;
     

	
    
     
	

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public String getApplicantMc() {
		return applicantMc;
	}

	public void setApplicantMc(String applicantMc) {
		this.applicantMc = applicantMc;
	}

	

	public String getApproverMc() {
		return approverMc;
	}

	public void setApproverMc(String approverMc) {
		this.approverMc = approverMc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getStartTime() {
		return StartTime;
	}

	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}

	public Date getEnDofTime() {
		return EnDofTime;
	}

	public void setEnDofTime(Date enDofTime) {
		EnDofTime = enDofTime;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getDescribes() {
		return Describes;
	}

	public void setDescribes(String describes) {
		Describes = describes;
	}

	@Override
	public String toString() {
		return "Approval [id=" + id + ", applicant=" + applicant + ", approver=" + approver + ", StartTime=" + StartTime
				+ ", EnDofTime=" + EnDofTime + ", results=" + results + ", proId=" + proId + ", planId=" + planId
				+ ", Describes=" + Describes + ", ProcessInstanceId=" + ProcessInstanceId + ", applicantMc="
				+ applicantMc + ", approverMc=" + approverMc + "]";
	}

         
}
