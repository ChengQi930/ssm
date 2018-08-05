package cn.bizowner.model;

public class IA_Users {
	
   private String uid;
   
   private String qc_task_id;
   
   private String qc_user_id;
   
   private String user_Task_status;
   
   private String mc;
   
   
	public String getMc() {
	return mc;
}

public void setMc(String mc) {
	this.mc = mc;
}

	public String getUser_Task_status() {
		
	return user_Task_status;
	
	}
	
	public void setUser_Task_status(String user_Task_status) {
		
		this.user_Task_status = user_Task_status;
		
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getQc_task_id() {
		return qc_task_id;
	}
	public void setQc_task_id(String qc_task_id) {
		this.qc_task_id = qc_task_id;
	}
	public String getQc_user_id() {
		return qc_user_id;
	}
	public void setQc_user_id(String qc_user_id) {
		this.qc_user_id = qc_user_id;
	}
   
   
}
