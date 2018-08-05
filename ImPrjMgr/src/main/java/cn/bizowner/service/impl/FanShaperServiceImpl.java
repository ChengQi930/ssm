package cn.bizowner.service.impl;

import static org.mockito.Matchers.intThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.crm.helper.Test;
import cn.bizowner.mapper.FanShapeMapper;
import cn.bizowner.model.Plan;
import cn.bizowner.model.ProjectPlanData;
import cn.bizowner.model.ProjectPlanList;
import cn.bizowner.service.FanShapeService;
import cn.bizowner.utils.DateCalculate;

@Service
public class FanShaperServiceImpl implements FanShapeService{

	@Autowired
	 private FanShapeMapper shapeMapper;
	
	@Override
	public List<HashMap<Object, Object>> getPhase(String startDate, String endDate) throws Exception {
		
		
		ArrayList<HashMap<Object, Object>> list = new ArrayList<HashMap<Object, Object>>();
		
		ArrayList<ProjectPlanList> phase = shapeMapper.getPhase(startDate, endDate);
		
		HashMap<Object, Object> hashMap = new HashMap<>();
		for (ProjectPlanList plan : phase) {
			 Set<ProjectPlanData> planData = plan.getProjectPlan();
			 String type = null;   //阶段 编号
			 String planTitle = null; // 对应的 阶段 名称
				for (ProjectPlanData projectPlanData : planData) {
					 type = projectPlanData.getType(); //
					 planTitle = projectPlanData.getPlanTitle();
					 
				}
				String phaseAmount = plan.getPhaseAmount();	//数量
				HashMap<Object, Object> hash = new HashMap<>();
				hash.put("name", planTitle);
				hash.put("value", phaseAmount);
				list.add(hash);
				/*Object put = hashMap.put("Name",planTitle);
				hashMap.put("Value", );
				list.add();*/
			
		}
		return list;
	}

	
	
	
	
	
	@Override
	public List<LinkedHashMap<Object, Object>> getColumnarMoney(String createTime, String estTime) throws Exception {
		int[] num = {31,28,31,30,31,30,31,31,30,31,30,31};
		
		ArrayList<LinkedHashMap<Object, Object>> list = new ArrayList<LinkedHashMap<Object, Object>>();
		
		ArrayList<String> monthBetween = DateCalculate.getMonthBetween(createTime, estTime); //相差的所有月份
		  for (String string : monthBetween) {
			   int parseInt = Integer.parseInt(string.substring(0,string.indexOf("-")));
				  boolean leapYear = DateCalculate.isLeapYear(parseInt);
				  String month = string.substring(string.indexOf("-")+1, string.length()); //月份
				  
				  String months = null;  //月份为 0开头的要处理一下
				   if(month.startsWith("0")){
					   months = month.substring(1).toString();
				   }else{
					   months = month.substring(0).toString();
				   }
				  if(leapYear){
					     num[1] = 29;
					     for(int j=1;j<=12;j++){
					    	 if(Integer.parseInt(months) == j){
					    		 LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();	
					    	 String columnarMoney = shapeMapper.getColumnarMoney(parseInt+"-"+month+"-01",
					    			                                             parseInt+"-"+month+ "-"+num[j-1]); 
					    	 linkedHashMap.put("month", month);
					    	 linkedHashMap.put("money", columnarMoney);
					    	 list.add(linkedHashMap); 
					        }
					     }
					}else{
						for(int o=1;o<=12;o++){
						if(Integer.parseInt(months) == o){
							LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
							String columnarMoney = shapeMapper.getColumnarMoney(parseInt+"-"+month+"-01",
                                                         parseInt+"-"+month+ "-"+num[o-1]); 
							linkedHashMap.put("month", month);
					    	 linkedHashMap.put("money", columnarMoney);
							list.add(linkedHashMap); 
				    	 }
						}
						
					}
				  
     		
     	       }
		return list;
	}

}
