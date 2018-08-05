package cn.bizowner.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.plugin.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.mapper.WordMapper;
import cn.bizowner.model.BigClass;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.ProjectPlanList;
import cn.bizowner.model.WrodAction;
import cn.bizowner.service.WordService;
import cn.bizowner.sqlite.WordData;
import cn.bizowner.utils.DateCalculate;
import cn.bizowner.utils.TimeUtil;
import cn.bizowner.utils.WordUtil;

@Service
public class WordDownloadService implements WordService{

	@Autowired
	private WordMapper  WordMapper;
	
	 //获取创建 word Map 值
	@Override
	public WrodAction  createUserListWord(HttpServletRequest request) {
		WrodAction wrodAction = new WrodAction();
		String attribute = (String) request.getSession().getAttribute("proId"); //project
		
		ProjectInfo project = WordMapper.getProject(attribute);  
		
		Date createTime = project.getCreateTime(); //计划开始时间
		Date estTime = project.getEstTime();  //计划结束时间
		String dateString = DateCalculate.getDateString(createTime);  //计划开始时间
	String createYear = dateString.substring(0, dateString.indexOf("-"));
	String createMonth = dateString.subSequence(dateString.indexOf("-")+1, dateString.length()-3).toString();
	String createDay = dateString.subSequence(dateString.indexOf("-")+4, dateString.length()).toString();
	String dateString2 = DateCalculate.getDateString(estTime); //计划结束时间
    String estYear = dateString2.substring(0, dateString2.indexOf("-"));
    String estMonth = dateString2.subSequence(dateString2.indexOf("-")+1, dateString2.length()-3).toString();
    String estDay = dateString2.subSequence(dateString2.indexOf("-")+4, dateString2.length()).toString();
    
    ProjectPlanList projectPlanList = WordMapper.getProjectPlanList(attribute);  // 获取实物信息
    Date startDate = projectPlanList.getStartDate(); //实物盘点 开始时间
    String swstartDate = DateCalculate.getDateString(startDate);  
    
	String swstartYear = swstartDate.substring(0, swstartDate.indexOf("-"));
	String swstartMonth = swstartDate.subSequence(swstartDate.indexOf("-")+1, swstartDate.length()-3).toString();
	String swstartDay = swstartDate.subSequence(swstartDate.indexOf("-")+4, swstartDate.length()).toString();
	
    Date endDate = projectPlanList.getEndDate();    //实物盘点 结束时间
    String swendDate = DateCalculate.getDateString(endDate);  
    
	String swendYear = swendDate.substring(0, swendDate.indexOf("-"));
	String swendMonth = swendDate.subSequence(swendDate.indexOf("-")+1, swendDate.length()-3).toString();
	String swendDay = swendDate.subSequence(swendDate.indexOf("-")+4, swendDate.length()).toString();
	int compareDate = DateCalculate.compareDate(swstartDate, swendDate, 0);  //实物盘点执行多少天
	
	ProjectPlanList planListZS = WordMapper.getPlanListZS(attribute);  // 获取 账实核对
	Date ZSendDate = planListZS.getEndDate();    //账实核对阶段 结束 时间
    String ZSend = DateCalculate.getDateString(ZSendDate);  
    
	String ZSendYear = ZSend.substring(0, ZSend.indexOf("-"));
	String ZSendMonth = ZSend.subSequence(ZSend.indexOf("-")+1, ZSend.length()-3).toString();
	String ZSendDay = ZSend.subSequence(ZSend.indexOf("-")+4, ZSend.length()).toString();
	
	//sqllite 数据块
	List<JqAssetAccount> getimp_jq_asset;
	String Year = null; //截至 年
    String Month = null; //截至 月
    String Day = null;   ////截至 日
    String leader = null;   //组长
    String men = null;     //组员
    String impSwAsset = null;  // sqlLite数据  共计实物数量
    
    String TYcount  = null;  //通用设备的数量
    String TYmoney  = null;  //通用设备的价值
    String JYZDcount  = null;  //家具、用具、装具及动植物的数量
    String JYZDTYmoney  = null;  //家具、用具、装具及动植物的价值
    String ZYcount  = null;  //专用设备 的数量
    String ZYmoney  = null;  //专用设备 的价值
    int ScrapTotalAmount = 0;  //报废资产 总共数量
    int ScrapTotalMoney = 0;   //报废资产 总共金额
    
    String ZSXFtotal = null;     //账实 相符的总 数量
    String ZSXFTotalmoney = null;  //账实 相符的总  金额
    String howManyYear = null;  //账账 完成多少年的
    
    String ZSTYSBTotal = null;  //账实  通用设备 的总数量
    String ZSTYSBValue = null;  //账实  通用设备 的总价值
    String ZSZYSBTotal = null;  //账实  专用设备 的总数量
    String ZSZYSBValue = null;  //账实  专用设备 的总价值
    String ZSJYZDTotal = null;  //账实 家具、用具、装具及动植物 的总数量
    String ZSJYZDValue = null;  //账实 家具、用具、装具及动植物 的总价值
    String ZSTFGTotal = null;  //账实 土地、房屋及构筑物 的总数量
    String ZSTFGValue = null;  //账实 土地、房屋及构筑物 的总价值
    String ZSTDTotal = null;  //账实 图书、档案 的总数量
    String ZSTDValue = null;  //账实 图书、档案 的总价值
    String ZSWXZCTotal = null;  //账实 图书、档案 的总数量
    String ZSWXZCValue = null;  //账实 图书、档案 的总价值
    
    String barcode = null;     //贴条码的所有房间数
	try {
		getimp_jq_asset = WordData.getimp_jq_asset(attribute);
		
	    for (JqAssetAccount jqAssetAccount : getimp_jq_asset) {
	    	 Date getDate = jqAssetAccount.getGetDate();
	    	 String JZdate = DateCalculate.getDateString(getDate);
	    	 Year = JZdate.substring(0, JZdate.indexOf("-"));
	 		 Month = JZdate.subSequence(JZdate.indexOf("-")+1, JZdate.length()-3).toString();
	 		 Day = JZdate.subSequence(JZdate.indexOf("-")+4, JZdate.length()).toString();
		}
	     leader = WordData.getLeader(attribute);  //组长
	     men = WordData.getMen(attribute);    //组员
	    
	      // 资产系统情况
	     
	    List<String> scrapElectronic = WordData.getScrapElectronic(attribute); //建议报废的 通用设备
	    	   TYcount  = scrapElectronic.get(0).equals("null") == true ? "0":scrapElectronic.get(0); 
	    	   TYmoney  = scrapElectronic.get(1).equals("null") == true ? "0":scrapElectronic.get(1);
	    	
	    List<String> furniture = WordData.getFurniture(attribute);    //建议报废的 家具、用具、装具及动植物
			   JYZDcount  = furniture.get(0).equals("null") == true ? "0":furniture.get(0); 
			   JYZDTYmoney  = furniture.get(1).equals("null") == true ? "0":furniture.get(1);
		List<String> specialEquipment = WordData.getSpecialEquipment(attribute);  //建议报废的 专用设备	
		       ZYcount  = specialEquipment.get(0).equals("null") == true ? "0":specialEquipment.get(0); 
		       ZYmoney  = specialEquipment.get(1).equals("null") == true ? "0":specialEquipment.get(1);
		       
		       
		    ScrapTotalAmount = Integer.valueOf(TYcount)+Integer.valueOf(JYZDcount)+Integer.valueOf(ZYcount);  //报废资产 总共数量
		    ScrapTotalMoney = Integer.valueOf(TYmoney)+Integer.valueOf(JYZDTYmoney)+Integer.valueOf(ZYmoney);  //报废资产 总共金额
		    
		    
		     howManyYear = WordData.getHowManyYear(attribute).equals("null") == true ? "0":WordData.getHowManyYear(attribute); 
		     impSwAsset = WordData.getImpSwAsset(attribute).equals("null") == true ? "0" : WordData.getImpSwAsset(attribute); // sqlLite数据  共计实物数量
		     //账实相符
		     List<String> zsxfTotal = WordData.getZSXFTotal(attribute);  
		        ZSXFtotal = zsxfTotal.get(0).equals("null") == true ? "0":zsxfTotal.get(0);   //账实 相符的总 数量
		        ZSXFTotalmoney = zsxfTotal.get(1).equals("null") == true ?"0":zsxfTotal.get(1);  //账实 相符的总  金额
		     //账时相符的 所有 大类 数量 与 价值   
		     LinkedHashMap<Object, List<String>> zsxfTotalWithValue = WordData.getZSXFTotalWithValue(attribute);    
		      for (Map.Entry<Object, List<String>> entry : zsxfTotalWithValue.entrySet()) {
		    	  if(entry.getKey().equals("通用设备")){
		    		  List<String> value = entry.getValue();
		    		   ZSTYSBTotal = value.get(0);
		    		   ZSTYSBValue = value.get(1);
		    	  }else if(entry.getKey().equals("专用设备")){
		    		  List<String> value = entry.getValue();
		    		   ZSZYSBTotal = value.get(0);
		    		   ZSZYSBValue = value.get(1);
		    	  }else if(entry.getKey().equals("家具、用具、装具及动植物")){
		    		  List<String> value = entry.getValue();
		    		   ZSJYZDTotal = value.get(0);
		    		   ZSJYZDValue = value.get(1);
		    	  }else if(entry.getKey().equals("土地、房屋及构筑物")){
		    		  List<String> value = entry.getValue();
		    		   ZSTFGTotal = value.get(0);
		    		   ZSTFGValue = value.get(1);
		    	  }else if(entry.getKey().equals("图书、档案")){
		    		  List<String> value = entry.getValue();
		    		   ZSTDTotal = value.get(0);
		    		   ZSTDValue = value.get(1);
		    	  }else if(entry.getKey().equals("无形资产")){
		    		  List<String> value = entry.getValue();
		    		   ZSWXZCTotal = value.get(0);
		    		   ZSWXZCValue = value.get(1);
		    	  }
		    		
			}
	     //贴条码的所有房间数
		 barcode = WordData.getBarcode(attribute).equals("null") == true ?"0":WordData.getBarcode(attribute);   
	     
	} catch (Exception e) {
		e.printStackTrace();
	}  //sqlLite jq数据
    
	
		wrodAction.setHead(project.getCustomer() == null?"0":project.getCustomer());   //委托方
		wrodAction.setJZYear(Year == null ? "0":Year);
		wrodAction.setJZMonth(Month== null ? "0":Month);   //sqllite 库中来的数据
		wrodAction.setJZDay(Day== null ? "0":Day);
		
		wrodAction.setLeader(leader == null ? "0":leader);   //组长
		wrodAction.setMen(men == null ? "0":men);   //组员
		wrodAction.setZCQCStartYear(createYear == null ? "0":createYear);
		wrodAction.setZCQCStartMonth(createMonth  == null ? "0":createMonth);
		wrodAction.setZCQCStartDay(createDay  == null ? "0":createDay);
		wrodAction.setZCQCEndYear(estYear  == null ? "0":estYear);
		wrodAction.setZCQCEndMonth(estMonth  == null ? "0":estMonth);
		wrodAction.setZCQCEndDay(estDay  == null ? "0":estDay);
		
		                                     //资产系统情况
		wrodAction.setScrapAssets(ScrapTotalAmount == 0 ? "0":ScrapTotalAmount+""); 
		wrodAction.setScrapValue(ScrapTotalMoney == 0 ? "0":ScrapTotalMoney+"");
		wrodAction.setTYSBAmount(TYcount == null ? "0":TYcount);
		wrodAction.setTYSBScrapValue(TYmoney == null ? "0":TYcount);
		wrodAction.setZYSBAmount(ZYcount == null ? "0":ZYcount);
		wrodAction.setZYSBScrapValue(ZYmoney == null ? "0":ZYmoney);
		wrodAction.setJYZDScrapAssets(JYZDcount == null ? "0":JYZDcount);
		wrodAction.setJYZDScrapValue(JYZDTYmoney == null ? "0":JYZDTYmoney);
		
		
		wrodAction.setHowManyYear(howManyYear == null ? "0":howManyYear);  //账账 完成了多少年的账
		
		wrodAction.setSWStartYear(swstartYear == null ? "0":swstartYear);  //实物盘点开始 时间
		wrodAction.setSWStartMonth(swstartMonth == null ? "0":swstartMonth);
		wrodAction.setSWStartDay(swstartDay == null ? "0":swstartDay);
		
		wrodAction.setSWAfterDay(compareDate == 0 ? "0":swstartDay+"");    //执行多少天
		
		wrodAction.setSWEndYear(swendYear== null ? "0":swendYear);   //实物盘点结束 时间
		wrodAction.setSWEndMonth(swendMonth== null ? "0":swendMonth);
		wrodAction.setSWEndDay(swendDay== null ? "0":swendDay);
		wrodAction.setSWTotal(impSwAsset== null ? "0":impSwAsset);   // sqlLite数据  共计实物数量
		
		wrodAction.setZSEndYear(ZSendYear== null ? "0":ZSendYear);   //账实情况 截至日期
		wrodAction.setZSEndMonth(ZSendMonth== null ? "0":ZSendMonth);
		wrodAction.setZSEndDay(ZSendDay== null ? "0":ZSendDay);
		
		wrodAction.setZSXFAmount(ZSXFtotal== null ? "0":ZSXFtotal);;    //账实相符数据数量
		wrodAction.setZSTotalValue(ZSXFTotalmoney== null ? "0":ZSXFTotalmoney);  //账实 相符的总  金额
		
		
		wrodAction.setZS_TYSBAssets(ZSTYSBTotal == null ? "0":ZSTYSBTotal);
		wrodAction.setZS_TYSBValue(ZSTYSBValue == null ? "0":ZSTYSBValue);
		wrodAction.setZS_ZYSBAssets(ZSZYSBTotal== null ? "0":ZSZYSBTotal);
		wrodAction.setZS_ZYSBValue(ZSZYSBValue== null ? "0":ZSZYSBValue);
		wrodAction.setZS_JYZDAssets(ZSJYZDTotal== null ? "0":ZSJYZDTotal);
		wrodAction.setZS_JYZDValue(ZSJYZDValue== null ? "0":ZSJYZDValue);
		wrodAction.setZS_TFGAssets(ZSTFGTotal== null ? "0":ZSTFGTotal);
		wrodAction.setZS_TFGValue(ZSTFGValue== null ?"0":ZSTFGValue);
		wrodAction.setZS_TSDAAssets(ZSTDTotal== null ?"0":ZSTDTotal);
		wrodAction.setZS_TSDAValue(ZSTDValue== null ?"0":ZSTDValue);
		wrodAction.setZS_WXZCAssets(ZSWXZCTotal== null ?"0":ZSWXZCTotal);
		wrodAction.setZS_WXZCValue(ZSWXZCValue== null ?"0":ZSWXZCValue);
		
		wrodAction.setTMZTAmount(barcode== null ?"0":barcode);   //贴条码的 房间数量
		wrodAction.setDKZTAmount(barcode== null ?"0":barcode);   //贴大卡的 房间数量
		
		//点击生成 清查报告的 当前时间
		String yearMonthDay = TimeUtil.getYearMonthDay(new Date().getTime());
		String EndYear = yearMonthDay.substring(0, yearMonthDay.indexOf("-"));
		String EndMonth = yearMonthDay.subSequence(yearMonthDay.indexOf("-")+1, yearMonthDay.length()-4).toString();
		String EndDay = yearMonthDay.subSequence(yearMonthDay.indexOf("-")+4, yearMonthDay.length()).toString();
		
		wrodAction.setYearTail(EndYear== null ?"0":EndYear);    //生成清查报告的 年
		wrodAction.setMonthTail(EndMonth== null ?"0":EndMonth);  //生成清查报告的 月
		wrodAction.setDayTail(EndDay== null ?"0":EndDay);    //生成清查报告的 日
		return wrodAction;
	}

	@Override
	public ProjectPlanList getProjectPlanList(String proId) {
		return WordMapper.getProjectPlanList(proId);
	}

	@Override
	public ProjectPlanList getPlanListZS(String proId) {
		return WordMapper.getPlanListZS(proId);
	}

	@Override
	public List<BigClass> getbigclass() {
		return WordMapper.getbigclass();
	}
    
}
