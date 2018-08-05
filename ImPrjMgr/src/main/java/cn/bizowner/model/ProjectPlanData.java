package cn.bizowner.model;

public class ProjectPlanData {
   private String planTitle;
   private String type;
   
	public String getPlanTitle() {
		return planTitle;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ProjectPlanData [planTitle=" + planTitle + ", type=" + type + "]";
	}
   
   
}
