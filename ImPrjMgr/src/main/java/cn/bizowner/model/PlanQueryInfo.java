package cn.bizowner.model;

public class PlanQueryInfo {
		
		private String proId;
		
		private String planTitle;
		
		private String startDate;
		
		private String endDate;
				
		private Integer type;

		private Integer planStatus;

		public String getProId() {
			return proId;
		}

		public void setProId(String proId) {
			this.proId = proId;
		}

		public String getPlanTitle() {
			return planTitle;
		}

		public void setPlanTitle(String planTitle) {
			this.planTitle = planTitle;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Integer getPlanStatus() {
			return planStatus;
		}

		public void setPlanStatus(Integer planStatus) {
			this.planStatus = planStatus;
		}
		
		
}
