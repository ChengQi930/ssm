package cn.bizowner.model;
import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List; 


public class Imp_User implements Serializable{
	
    private String userId;
    
    private String mc;

    private String userlevel;

    private String enableflag; 
    
    private List<Role> roleInfo = new ArrayList<Role>();

    private List<UserDept> deptInfo = new ArrayList<UserDept>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}

	public String getEnableflag() {
		return enableflag;
	}

	public void setEnableflag(String enableflag) {
		this.enableflag = enableflag;
	}

	public List<Role> getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(List<Role> roleInfo) {
		this.roleInfo = roleInfo;
	}

	public List<UserDept> getDeptInfo() {
		return deptInfo;
	}

	public void setDeptInfo(List<UserDept> deptInfo) {
		this.deptInfo = deptInfo;
	}
 
    
}