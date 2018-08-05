package cn.bizowner.model;

import java.util.Date;

public class JqAssetAccount {		
	
		private String id;
		
		private String fileId;
		
		private String assetBarcode;			//资产编号 
		
		private String assetBigClass;			//资产大类
		
		private String assetClass;				//资产分类
		
		private String assetName;				//资产名称
		
		private Date assetCwrzrq;				//财务入账日期
		
		private String moneyType;				//价值类型
		
		private Double money;					//价值
		
		private String getType;					//取得方式
			
		private Date getDate;					//取得日期
		
		private String useStatus;				//使用状况
		
		private String remark;					//备注
		
		private String useDirect;				//使用方向
		
		private String useDept;					//使用部门
		
		private String manageDept;				//管理部门
		
		private String useUser;					//使用人
		
		private Integer sl;						//数量
		
		private String documentMaker;			//制单人
		
		private Date documentMakTime;			//制单时间
		
		private String checkNo;					//清查编号
		
		private String belongCompany;			//所属单位
		
		private String cardStatus;				//卡片状态
		
		private Double totalDepreciate;			//累计折旧
		
		private Integer alreadyDepreciateMonth;	//已提折旧月数	
		
		private Double netValue;				//净值
		
		private String budgetProjectNo;			//预算项目编号
		
		private String buyOrgType;				//采购组织形式
		
		private String assetPp;					//品牌
		
		private String assetModel;				//规格型号
		
		private String saveAddress;				//存放地点
		
		private String manufacturer;			//生产厂家
		
		private String seller;					//销售商
		
		private String contractNo;				//合同编号
		
		private String invoiceNo;				//发票号
		
		private Double useArea;					//使用权面积/建筑面积
		
		private String structure;				//建筑结构
		
		private String designUse;				//设计用途
		
		private Double financialFunds;			//财政性资金
		
		private Double notFinancialFunds;		//非财政性资金
		
		private String accountNo;				//会计凭证号
		
		private String propertyRight;			//产权形式
		
		private String ownerType;				//权属性质	
		
		private String ownerNo;					//权属证号
		
		private String landUseType;				//土地使用权类型
		
		private Date issueDate;					//发证日期
			
		private String landUse;					//地类(用途)	
		
		private String landOrder;				//土地级次
		
		private String depreciateStatus;		//折旧状态
		
		private String location;				//坐落位置
		
		private String carUse;					//车辆用途
		
		private String carProduce;				//车辆产地
		
		private String carNo;					//车牌号
		
		private String carPl;					//排气量
		
		private boolean isRela;					//是否关联
		 
		private Integer type;					//建议对账时用到

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

		public String getAssetBarcode() {
			return assetBarcode;
		}

		public void setAssetBarcode(String assetBarcode) {
			this.assetBarcode = assetBarcode;
		}

		public String getAssetBigClass() {
			return assetBigClass;
		}

		public void setAssetBigClass(String assetBigClass) {
			this.assetBigClass = assetBigClass;
		}

		public String getAssetClass() {
			return assetClass;
		}

		public void setAssetClass(String assetClass) {
			this.assetClass = assetClass;
		}

		public String getAssetName() {
			return assetName;
		}

		public void setAssetName(String assetName) {
			this.assetName = assetName;
		}

		public Date getAssetCwrzrq() {
			return assetCwrzrq;
		}

		public void setAssetCwrzrq(Date assetCwrzrq) {
			this.assetCwrzrq = assetCwrzrq;
		}

		public String getMoneyType() {
			return moneyType;
		}

		public void setMoneyType(String moneyType) {
			this.moneyType = moneyType;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getGetType() {
			return getType;
		}

		public void setGetType(String getType) {
			this.getType = getType;
		}

		public Date getGetDate() {
			return getDate;
		}

		public void setGetDate(Date getDate) {
			this.getDate = getDate;
		}

		public String getUseStatus() {
			return useStatus;
		}

		public void setUseStatus(String useStatus) {
			this.useStatus = useStatus;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getUseDirect() {
			return useDirect;
		}

		public void setUseDirect(String useDirect) {
			this.useDirect = useDirect;
		}

		public String getUseDept() {
			return useDept;
		}

		public void setUseDept(String useDept) {
			this.useDept = useDept;
		}

		public String getManageDept() {
			return manageDept;
		}

		public void setManageDept(String manageDept) {
			this.manageDept = manageDept;
		}

		public String getUseUser() {
			return useUser;
		}

		public void setUseUser(String useUser) {
			this.useUser = useUser;
		}

		public Integer getSl() {
			return sl;
		}

		public void setSl(Integer sl) {
			this.sl = sl;
		}

		public String getDocumentMaker() {
			return documentMaker;
		}

		public void setDocumentMaker(String documentMaker) {
			this.documentMaker = documentMaker;
		}

		public Date getDocumentMakTime() {
			return documentMakTime;
		}

		public void setDocumentMakTime(Date documentMakTime) {
			this.documentMakTime = documentMakTime;
		}

		public String getCheckNo() {
			return checkNo;
		}

		public void setCheckNo(String checkNo) {
			this.checkNo = checkNo;
		}

		public String getBelongCompany() {
			return belongCompany;
		}

		public void setBelongCompany(String belongCompany) {
			this.belongCompany = belongCompany;
		}

		public String getCardStatus() {
			return cardStatus;
		}

		public void setCardStatus(String cardStatus) {
			this.cardStatus = cardStatus;
		}

		public Double getTotalDepreciate() {
			return totalDepreciate;
		}

		public void setTotalDepreciate(Double totalDepreciate) {
			this.totalDepreciate = totalDepreciate;
		}

		public Integer getAlreadyDepreciateMonth() {
			return alreadyDepreciateMonth;
		}

		public void setAlreadyDepreciateMonth(Integer alreadyDepreciateMonth) {
			this.alreadyDepreciateMonth = alreadyDepreciateMonth;
		}

		public Double getNetValue() {
			return netValue;
		}

		public void setNetValue(Double netValue) {
			this.netValue = netValue;
		}

		public String getBudgetProjectNo() {
			return budgetProjectNo;
		}

		public void setBudgetProjectNo(String budgetProjectNo) {
			this.budgetProjectNo = budgetProjectNo;
		}

		public String getBuyOrgType() {
			return buyOrgType;
		}

		public void setBuyOrgType(String buyOrgType) {
			this.buyOrgType = buyOrgType;
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

		public String getSaveAddress() {
			return saveAddress;
		}

		public void setSaveAddress(String saveAddress) {
			this.saveAddress = saveAddress;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}

		public String getSeller() {
			return seller;
		}

		public void setSeller(String seller) {
			this.seller = seller;
		}

		public String getContractNo() {
			return contractNo;
		}

		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}

		public String getInvoiceNo() {
			return invoiceNo;
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

		public Double getUseArea() {
			return useArea;
		}

		public void setUseArea(Double useArea) {
			this.useArea = useArea;
		}

		public String getStructure() {
			return structure;
		}

		public void setStructure(String structure) {
			this.structure = structure;
		}

		public String getDesignUse() {
			return designUse;
		}

		public void setDesignUse(String designUse) {
			this.designUse = designUse;
		}

		public Double getFinancialFunds() {
			return financialFunds;
		}

		public void setFinancialFunds(Double financialFunds) {
			this.financialFunds = financialFunds;
		}

		public Double getNotFinancialFunds() {
			return notFinancialFunds;
		}

		public void setNotFinancialFunds(Double notFinancialFunds) {
			this.notFinancialFunds = notFinancialFunds;
		}

		public String getAccountNo() {
			return accountNo;
		}

		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}

		public String getPropertyRight() {
			return propertyRight;
		}

		public void setPropertyRight(String propertyRight) {
			this.propertyRight = propertyRight;
		}

		public String getOwnerType() {
			return ownerType;
		}

		public void setOwnerType(String ownerType) {
			this.ownerType = ownerType;
		}

		public String getOwnerNo() {
			return ownerNo;
		}

		public void setOwnerNo(String ownerNo) {
			this.ownerNo = ownerNo;
		}

		public String getLandUseType() {
			return landUseType;
		}

		public void setLandUseType(String landUseType) {
			this.landUseType = landUseType;
		}

		public Date getIssueDate() {
			return issueDate;
		}

		public void setIssueDate(Date issueDate) {
			this.issueDate = issueDate;
		}

		public String getLandUse() {
			return landUse;
		}

		public void setLandUse(String landUse) {
			this.landUse = landUse;
		}

		public String getLandOrder() {
			return landOrder;
		}

		public void setLandOrder(String landOrder) {
			this.landOrder = landOrder;
		}

		
		

		public String getDepreciateStatus() {
			return depreciateStatus;
		}

		public void setDepreciateStatus(String depreciateStatus) {
			this.depreciateStatus = depreciateStatus;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getCarUse() {
			return carUse;
		}

		public void setCarUse(String carUse) {
			this.carUse = carUse;
		}

		public String getCarProduce() {
			return carProduce;
		}

		public void setCarProduce(String carProduce) {
			this.carProduce = carProduce;
		}

		public String getCarNo() {
			return carNo;
		}

		public void setCarNo(String carNo) {
			this.carNo = carNo;
		}

		public String getCarPl() {
			return carPl;
		}

		public void setCarPl(String carPl) {
			this.carPl = carPl;
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
