package cn.bizowner.model;

import java.util.Date;

public class ProjectFileInfo {

			private String id;
			
			private String projectId;
			
			private Integer type;
			
			private String fileName;
			
			private String filePath;
			
			private String remark;
			
			private String updateTime;

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

			public Integer getType() {
				return type;
			}

			public void setType(Integer type) {
				this.type = type;
			}

			

			public String getFileName() {
				return fileName;
			}

			public void setFileName(String fileName) {
				this.fileName = fileName;
			}
			

			public String getFilePath() {
				return filePath;
			}

			public void setFilePath(String filePath) {
				this.filePath = filePath;
			}

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}

			public String getUpdateTime() {
				return updateTime;
			}

			public void setUpdateTime(String updateTime) {
				this.updateTime = updateTime;
			}

			
			
}
