package cn.bizowner.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Role implements Serializable{
    
	
	private String roleId; 

    private String roleName;
    
    private String roleCode;
    
    private String remark;
    
    private boolean haveFlag;   

	  
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
 

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isHaveFlag() {
		return haveFlag;
	}

	public void setHaveFlag(boolean haveFlag) {
		this.haveFlag = haveFlag;
	}

	
	
     
}