package cn.bizowner.test;

import java.util.List;

public class QcTask {

		private String id;
		
		private String taskName;
		
		
		private List<QcUsers> listQcUser;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTaskName() {
			return taskName;
		}

		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}

		public List<QcUsers> getListQcUser() {
			return listQcUser;
		}

		public void setListQcUser(List<QcUsers> listQcUser) {
			this.listQcUser = listQcUser;
		}
		
		
		
}
