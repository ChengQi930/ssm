package cn.bizowner.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Plan {

		private String planId;
		
		private String proId;
		
		private String planTitle;
		
		private String startDate;
		
		private String endDate;
				
		private Integer type;
		
		private Integer planStatus;
		
		private Object impUsers;
		
		private Set<ProjectPlanData> PlanData = new HashSet<ProjectPlanData>();
		
		public Set<ProjectPlanData> getPlanData() {
			return PlanData;
		}

		public void setPlanData(Set<ProjectPlanData> planData) {
			PlanData = planData;
		}

		public String getPlanId() {
			return planId;
		}

		public void setPlanId(String planId) {
			this.planId = planId;
		}

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

		public Object getImpUsers() {
			return impUsers;
		}

		public void setImpUsers(Object impUsers) {
			this.impUsers = impUsers;
		}
		
		@Override
		public String toString() {
			return "Plan [planId=" + planId + ", proId=" + proId + ", planTitle=" + planTitle + ", startDate="
					+ startDate + ", endDate=" + endDate + ", type=" + type + ", planStatus=" + planStatus
					+ ", impUsers=" + impUsers + ", PlanData=" + PlanData + "]";
		}
		
}
