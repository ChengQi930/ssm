package cn.bizowner.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.plugin.Invocation;

import cn.bizowner.model.BigClass;
import cn.bizowner.model.ProjectPlanList;
import cn.bizowner.model.WrodAction;

public interface WordService {

	  //
	public WrodAction createUserListWord(HttpServletRequest request);
	
	//获取 实物盘点信息
	public ProjectPlanList getProjectPlanList(String proId);
	
	//获取账实核对的信息
	public ProjectPlanList getPlanListZS(String proId);
	
	//获取维护的 资产大类
	 public List<BigClass> getbigclass();
}
