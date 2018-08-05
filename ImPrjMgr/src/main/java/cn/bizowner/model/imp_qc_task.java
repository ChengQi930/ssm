package cn.bizowner.model;

import java.util.List;

public class imp_qc_task {
	
    private String id;
    
    private String projectId;
    
    private String taskName;
    
    private String taskDate;
    
    private String task_status;
    
    private String remark;
    
    private List<IA_Users> IA_User;
    
    
    
 	public List<IA_Users> getIA_User() {
 		
 	return IA_User;
 	}
 	
 	public void setIA_User(List<IA_Users> iA_User) {
 		
 		IA_User = iA_User;
 	}
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    
}
