package cn.bizowner.model;


public class UpdateAssetMgrThreadParam {
		
		private String proUuid;
		
		private String projectId;
		
		private Integer accountType;
		
		private Integer updateType;
		
		private String filePath;
		
		private String fileName;

		public String getProUuid() {
			return proUuid;
		}

		public void setProUuid(String proUuid) {
			this.proUuid = proUuid;
		}

		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}

		public Integer getAccountType() {
			return accountType;
		}

		public void setAccountType(Integer accountType) {
			this.accountType = accountType;
		}
		 

		public Integer getUpdateType() {
			return updateType;
		}

		public void setUpdateType(Integer updateType) {
			this.updateType = updateType;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		
}
