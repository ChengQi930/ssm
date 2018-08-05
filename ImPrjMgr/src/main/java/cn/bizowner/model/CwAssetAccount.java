package cn.bizowner.model;

public class CwAssetAccount {		
	
			private String id;
			
			private String fileId;
			
			private String year;
			
			private String month;
			
			private String day;
			
			private String accountNo;
			
			private String remark;
			
			private String debit;
			
			private String lender;
			
			private String direction;
			
			private String balance;
			
			private boolean isRela;					//是否关联

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getFileId() {
				return fileId;
			}

			public void setFileId(String fileId) {
				this.fileId = fileId;
			}
			 

			public String getYear() {
				return year;
			}

			public void setYear(String year) {
				this.year = year;
			}

			public String getMonth() {
				return month;
			}

			public void setMonth(String month) {
				this.month = month;
			}

			public String getDay() {
				return day;
			}

			public void setDay(String day) {
				this.day = day;
			}

			public String getAccountNo() {
				return accountNo;
			}

			public void setAccountNo(String accountNo) {
				this.accountNo = accountNo;
			}

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}

			public String getDebit() {
				return debit;
			}

			public void setDebit(String debit) {
				this.debit = debit;
			}

			public String getLender() {
				return lender;
			}

			public void setLender(String lender) {
				this.lender = lender;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public String getBalance() {
				return balance;
			}

			public void setBalance(String balance) {
				this.balance = balance;
			}

			public boolean isRela() {
				return isRela;
			}

			public void setRela(boolean isRela) {
				this.isRela = isRela;
			}

			
			
			
}
