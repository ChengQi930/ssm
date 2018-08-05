package cn.bizowner.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.Plan;
import cn.bizowner.service.FanShapeService;
import cn.bizowner.utils.DateCalculate;
import cn.bizowner.utils.JSONHelper;

@RestController
@RequestMapping("/FanShapeStatistical")
public class ProjectStatisticsController {

	 @Autowired
	 private FanShapeService fanshape;
	 
	 /**
	  *    符合给定时间段内的 所有项目的阶段占的比重！ 
	  * @param startDate   开始时间
	  * @param endDate  结束时间
	  * @return
	  */
	 @RequestMapping(value = "/FanShape",method = RequestMethod.GET)
	 public String fanshaper(String startDate,String endDate){
		 String result =null;
		 boolean flag = true;
		 String  errMess = "";
		 Dto outDto = new BaseDto();
		 List<HashMap<Object, Object>> phase =null;
		 //非闰年日期
		 int[] num = {31,28,31,30,31,30,31,31,30,31,30,31};
		 try {
			int parseInt = Integer.parseInt(endDate.substring(0,endDate.indexOf("-")));
			  boolean leapYear = DateCalculate.isLeapYear(parseInt);
			  String month = endDate.substring(endDate.indexOf("-")+1, endDate.length()).toString();
			  String months = null;  //月份为 0开头的要处理一下
			   if(month.startsWith("0")){
				   months = month.substring(1).toString();
			   }else{
				   months = month.substring(0).toString();
			   }
			  if(leapYear){
				     num[1] = 29;
				     for(int i=1;i<=12;i++){
				    	 if(Integer.parseInt(months) == i){
				    		 phase = fanshape.getPhase(new StringBuilder(startDate).insert(startDate.length(), "-01").toString(), //
				    				                   new StringBuilder(endDate).insert(endDate.length(), "-"+num[i-1]).toString());	
				    	 }
				     }
				}else{
					for(int i=1;i<=12;i++){
					if(Integer.parseInt(months) == i){
			    		 phase = fanshape.getPhase(new StringBuilder(startDate).insert(startDate.length(), "-01").toString(), //
			    				                   new StringBuilder(endDate).insert(endDate.length(), "-"+num[i-1]).toString());
			    		 break;
			    	 }
					}
				}
			  
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		 if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	    	  outDto.put("result", phase);
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	    	  outDto.put("errmsg", errMess);
	      }
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	 }
	 
	 
	 /**
	  * 
	  * @param createTime  开始时间
	  * @param estTime     结束时间
	  * @return     给定时间内各个月份的 项目金额
	  */
	@RequestMapping(value = "/ColumnarFigure", method = RequestMethod.GET)
	 public String getColumnarFigure(String createTime,String estTime){
		String result =null;
		 boolean flag = true;
		 String  errMess = "";
		 Dto outDto = new BaseDto();
		 List<LinkedHashMap<Object, Object>> columnarMoney = null;
		try {
			   columnarMoney = fanshape.getColumnarMoney(createTime, estTime);
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	    	  outDto.put("result", columnarMoney);
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	    	  outDto.put("errmsg", errMess);
	      }
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	 }
}
