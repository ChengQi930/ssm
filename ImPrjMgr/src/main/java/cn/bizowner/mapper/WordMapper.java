package cn.bizowner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.BigClass;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.ProjectPlanList;

@Mapper
public interface WordMapper {

	 //获取project 信息
	public ProjectInfo getProject(@Param("proId") String proId);
	
	//获取 实物盘点信息
	public ProjectPlanList getProjectPlanList(@Param("proId") String proId);
	
	//获取账实核对的信息
	public ProjectPlanList getPlanListZS(@Param("proId") String proId);
	
	//获取维护的 资产大类
	 public List<BigClass> getbigclass();
}
