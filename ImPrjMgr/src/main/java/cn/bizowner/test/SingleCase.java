package cn.bizowner.test;

import org.apache.ibatis.annotations.Insert;

//单例设计   3种 方式！


public class SingleCase {
	//饿汉
     /*private static final SingleCase insert = new SingleCase();
	 public SingleCase() {
		 
	}
	 public static SingleCase getInstance() {
		 return insert;
	 }*/    
	 
	//懒汉
	/*private static SingleCase insert = null;
	public SingleCase() {}
	public static SingleCase getInstance() {
		if(insert == null) {
			synchronized (SingleCase.class) {
				if(insert == null) {
					insert = new SingleCase();
				}
			}
			
		}
		return insert;
	}*/
	
	//静态内部类方式
	 /* public SingleCase() {}*/
	 /*private static class get {
		private  final static SingleCase inser = new SingleCase();
	 }
	 public static  SingleCase getInstance() {
		 return get.inser;
	 }*/
	
	  /*private static SingleCase SingleCase  = null;
	  public  SingleCase() {}
	  public static SingleCase getInstance() {
		  if(SingleCase == null) {
			  synchronized (SingleCase.class) {
				if(SingleCase == null) {
					SingleCase = new SingleCase();
				}
			}
		  }
		  return SingleCase;
	  }*/
	  
	  private static class get{
		  private static SingleCase SingleCase = new SingleCase();
	  }
	  public static SingleCase getInstance() {
		 return get.SingleCase;
	  }
	 public static void main(String[] args) {
		SingleCase instance = SingleCase.getInstance();
		 SingleCase instance1 = SingleCase.getInstance();
		 SingleCase instance2 = SingleCase.getInstance();
		 System.out.println(instance == instance1 && instance1 == instance2);
		 
	}
}
