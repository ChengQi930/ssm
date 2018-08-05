package cn.bizowner.model;

import java.util.List;

public class DingDingUserInfo {
	
		private String userId;
		
		private String mc;

		private String mobile;
		
		private String status;
				
		private List<UserDept> listDept;

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

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<UserDept> getListDept() {
			return listDept;
		}

		public void setListDept(List<UserDept> listDept) {
			this.listDept = listDept;
		}

		 
		
		
}
