package cn.bizowner.model;

import java.util.Date;

public class ProjectInfo {

		private String proId;
		
		private String proName;
		
		private String proType;
		
		private Date estTime;
		
		private Object createUser;
		
		private Date createTime;
		
		private Date finishTime;
		
		private Object impUser;
		  
		private Object saleUser;
		 
		private String customer;
		
		private Double money;
		
		private Integer proStatus;
		private String proNo;

		public String getProId() {
			return proId;
		}

		public void setProId(String proId) {
			this.proId = proId;
		}

		public String getProName() {
			return proName;
		}

		public void setProName(String proName) {
			this.proName = proName;
		}

		public String getProType() {
			return proType;
		}

		public void setProType(String proType) {
			this.proType = proType;
		}

		public Date getEstTime() {
			return estTime;
		}

		public void setEstTime(Date estTime) {
			this.estTime = estTime;
		}

		public Object getCreateUser() {
			return createUser;
		}

		public void setCreateUser(Object createUser) {
			this.createUser = createUser;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		} 
		
		public Date getFinishTime() {
			return finishTime;
		}

		public void setFinishTime(Date finishTime) {
			this.finishTime = finishTime;
		}

		public Object getImpUser() {
			return impUser;
		}

		public void setImpUser(Object impUser) {
			this.impUser = impUser;
		}

		public Object getSaleUser() {
			return saleUser;
		}

		public void setSaleUser(Object saleUser) {
			this.saleUser = saleUser;
		}

		public String getCustomer() {
			return customer;
		}

		public void setCustomer(String customer) {
			this.customer = customer;
		}
		 
		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public Integer getProStatus() {
			return proStatus;
		}

		public void setProStatus(Integer proStatus) {
			this.proStatus = proStatus;
		}

		public String getProNo() {
			return proNo;
		}

		public void setProNo(String proNo) {
			this.proNo = proNo;
		}
		
		
}
