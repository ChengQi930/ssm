package cn.bizowner.model;



public class ProgressInfo {
		
		private String progress;		//更新进度
		
		private boolean finishFlag;	//true表示完成,false表示未完�?
		
		private boolean updateFlag;	//true表示更新成功,false表示更新失败
		
		private String error;//失败信息

		private String finishTime;		//完成时间

		public String getProgress() {
			return progress;
		}

		public void setProgress(String progress) {
			this.progress = progress;
		}

		public boolean isFinishFlag() {
			return finishFlag;
		}

		public void setFinishFlag(boolean finishFlag) {
			this.finishFlag = finishFlag;
		}

		public boolean isUpdateFlag() {
			return updateFlag;
		}

		public void setUpdateFlag(boolean updateFlag) {
			this.updateFlag = updateFlag;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getFinishTime() {
			return finishTime;
		}

		public void setFinishTime(String finishTime) {
			this.finishTime = finishTime;
		}
		
		
}
