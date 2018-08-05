package cn.bizowner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.bizowner.model.Plan;


public interface FanShapeService {
     
	  //查询符合该时间段的所有项目的阶段占的比重
		public List<HashMap<Object, Object>> getPhase(String startDate, String endDate) throws Exception;
		
		//查询一段时间内 各个月所产出的所有项目信息的金额总和  柱状图！
		public List<LinkedHashMap<Object, Object>> getColumnarMoney(String createTime,String estTime)throws Exception;
} 
