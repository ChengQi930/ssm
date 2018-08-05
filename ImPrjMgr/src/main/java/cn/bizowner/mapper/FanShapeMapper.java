package cn.bizowner.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.Plan;
import cn.bizowner.model.ProjectPlanList;

@Mapper
public interface FanShapeMapper {
    
	//查询符合该时间段的所有项目的阶段占的比重  扇形图
	public ArrayList<ProjectPlanList> getPhase(@Param("startDate") String startDate,@Param("endDate") String endDate);
	
	//软编码查询出值
	/*public */
	
	//查询一段时间内 各个月所产出的所有项目信息的金额总和  柱状图！
	public String getColumnarMoney(@Param("createTime") String createTime,@Param("estTime") String estTime);
	
	
}
