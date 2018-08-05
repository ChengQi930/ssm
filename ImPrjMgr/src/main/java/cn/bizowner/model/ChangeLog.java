package cn.bizowner.model;

public class ChangeLog {

				private String id;
				
				private Integer accountType;	//财务账为1，久其账 为2, 实物账为3
				
				private String assetId;
				
				private String columnName;  
				
				private String data1;
				
				private String data2;
				
				private String changTime;
				
				private String userId;
				
				private Integer operType;
				
				
				//添加 1    修改 2     关联3(财务久其)、关联4(久其实物)、解关联5(财务久其)、解关联6(久其实物)、拆分7
				//operType为1时,assetId为新增资产的id,data1位新增资产所有内容
				//operType为2时,data1为修改前数据,data2为修改后数据
				//operType为3时,data1为被拆分字段id,data2为拆分数量
				//operType为4时,data1为财务ids,data2为久其ids
				//operType为5时,data1为久其ids,data2为实物ids
				
				

				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
				}

				public Integer getAccountType() {
					return accountType;
				}

				public void setAccountType(Integer accountType) {
					this.accountType = accountType;
				}

				public String getAssetId() {
					return assetId;
				}

				public void setAssetId(String assetId) {
					this.assetId = assetId;
				}

				public String getColumnName() {
					return columnName;
				}

				public void setColumnName(String columnName) {
					this.columnName = columnName;
				}

				public String getData1() {
					return data1;
				}

				public void setData1(String data1) {
					this.data1 = data1;
				}

				public String getData2() {
					return data2;
				}

				public void setData2(String data2) {
					this.data2 = data2;
				}

				public String getChangTime() {
					return changTime;
				}

				public void setChangTime(String changTime) {
					this.changTime = changTime;
				}

				public String getUserId() {
					return userId;
				}

				public void setUserId(String userId) {
					this.userId = userId;
				}

				public Integer getOperType() {
					return operType;
				}

				public void setOperType(Integer operType) {
					this.operType = operType;
				}

}
