package cn.bizowner.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

	 	private String userId;
	    
	    private String mc;

	    private String userLevel;

	    private Object enableFlag; 
	    
	    private String remark;
	    
	    private List<Role> roleInfo = new ArrayList<Role>();

	    private List<UserDept> deptInfo = new ArrayList<UserDept>();

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getMc() {
			return mc;
		}

		public void setMc(String mc) {
			this.mc = mc;
		}
 

		public String getUserLevel() {
			return userLevel;
		}

		public void setUserLevel(String userLevel) {
			this.userLevel = userLevel;
		}
 
		
		public Object getEnableFlag() {
			return enableFlag;
		}

		public void setEnableFlag(Object enableFlag) {
			this.enableFlag = enableFlag;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public List<Role> getRoleInfo() {
			return roleInfo;
		}

		public void setRoleInfo(List<Role> roleInfo) {
			this.roleInfo = roleInfo;
		}

		public List<UserDept> getDeptInfo() {
			return deptInfo;
		}

		public void setDeptInfo(List<UserDept> deptInfo) {
			this.deptInfo = deptInfo;
		}
	 
	    

}
