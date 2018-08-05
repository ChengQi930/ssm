package cn.bizowner.model;


public class BigClass {
   private String id;
   private String mc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	@Override
	public String toString() {
		return "BigClass [id=" + id + ", mc=" + mc + "]";
	}
}
