package cn.bizowner.model;

public class RoleQxInfo {
	
		private String funcId;
	
		private String parentId;
		
		private String funcName;
		
		private String remark;
		
		private boolean enableFlag;
		
		 
		
		public String getFuncId() {
			return funcId;
		}
		public void setFuncId(String funcId) {
			this.funcId = funcId;
		}
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		public String getFuncName() {
			return funcName;
		}
		public void setFuncName(String funcName) {
			this.funcName = funcName;
		}
		public void setEnableFlag(boolean enableFlag) {
			this.enableFlag = enableFlag;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public boolean getEnableFlag() {
			return enableFlag;
		}
		public void setEnableFlag(String enableFlag) {
			if(enableFlag != null && enableFlag.equals("Y"))
			{
				this.enableFlag = true;
			}
			else
			{
				this.enableFlag = false;
			}
		}
}
