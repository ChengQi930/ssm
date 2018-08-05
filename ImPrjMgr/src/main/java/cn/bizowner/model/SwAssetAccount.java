package cn.bizowner.model;

import java.util.Date;

public class SwAssetAccount {		
	
			private String id;
			
			private String taskId;
			
			private String fileId;
			
			private String assetBarcode;			//资产编号 
			
			private String assetName;
			
			private String assetClass;
			
			private Integer sl;
			
			private Date getDate;
			
			private String 	assetPp;
			
			private String assetModel;
			
			private String useDept;
			
			private String useUser;
			
			private String saveAddress;
			
			private Double money;					//价值
			
			private String remark;
			
			private String pictureId;
			
			private boolean isRela;					//是否关联
			
			private Integer type;					//建议对账时用到

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getTaskId() {
				return taskId;
			}

			public void setTaskId(String taskId) {
				this.taskId = taskId;
			}

			public String getFileId() {
				return fileId;
			}

			public void setFileId(String fileId) {
				this.fileId = fileId;
			}
  
			public String getAssetBarcode() {
				return assetBarcode;
			}

			public void setAssetBarcode(String assetBarcode) {
				this.assetBarcode = assetBarcode;
			}

			public String getAssetName() {
				return assetName;
			}

			public void setAssetName(String assetName) {
				this.assetName = assetName;
			}

			public String getAssetClass() {
				return assetClass;
			}

			public void setAssetClass(String assetClass) {
				this.assetClass = assetClass;
			}

			public Integer getSl() {
				return sl;
			}

			public void setSl(Integer sl) {
				this.sl = sl;
			}

			public Date getGetDate() {
				return getDate;
			}

			public void setGetDate(Date getDate) {
				this.getDate = getDate;
			}

			public String getAssetPp() {
				return assetPp;
			}

			public void setAssetPp(String assetPp) {
				this.assetPp = assetPp;
			}

			public String getAssetModel() {
				return assetModel;
			}

			public void setAssetModel(String assetModel) {
				this.assetModel = assetModel;
			}

			public String getUseDept() {
				return useDept;
			}

			public void setUseDept(String useDept) {
				this.useDept = useDept;
			}

			public String getUseUser() {
				return useUser;
			}

			public void setUseUser(String useUser) {
				this.useUser = useUser;
			}

			public String getSaveAddress() {
				return saveAddress;
			}

			public void setSaveAddress(String saveAddress) {
				this.saveAddress = saveAddress;
			}

			public Double getMoney() {
				return money;
			}

			public void setMoney(Double money) {
				this.money = money;
			}
			 

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}

			public String getPictureId() {
				return pictureId;
			}

			public void setPictureId(String pictureId) {
				this.pictureId = pictureId;
			}

			public boolean isRela() {
				return isRela;
			}

			public void setRela(boolean isRela) {
				this.isRela = isRela;
			}

			public Integer getType() {
				return type;
			}

			public void setType(Integer type) {
				this.type = type;
			}
 
}
