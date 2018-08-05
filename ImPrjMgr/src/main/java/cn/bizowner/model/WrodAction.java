package cn.bizowner.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.bizowner.utils.WordUtil;

public class WrodAction {
	
    private String Head;   //委托方
    
    private String JZYear;    //截至 年
    private String JZMonth;   //截至 月
    private String JZDay;     //截至 日
    
    private String Leader;   //组长
    private String Men;      //组员
    
    private String ZCQCStartYear;   //资产清查计划 开始 年
    private String ZCQCStartMonth;  //资产清查计划 开始 月
    private String ZCQCStartDay;   //资产清查计划 开始 天
    
    private String ZCQCEndYear;   //资产清查计划  结束 年
    private String ZCQCEndMonth;  //资产清查计划 结束 月
    private String ZCQCEndDay;   //资产清查计划 结束 天
    
    
    /*private String HowMuchYear; */     //资产清查多少年前
    
    private String ScrapAssets;  //资产建议报废数量
    private String ScrapValue;  //资产建议报废总价值
    
    private String TYSBAmount;   //通用设备数量
    private String TYSBScrapValue;  //通用设备价值
    
    private String ZYSBAmount;   //专用设备数量
    private String ZYSBScrapValue;  //专用设备价值
    
    private String JYZDScrapAssets;  //家具、用具、装具及动植物资产数量
    private String JYZDScrapValue;  //家具、用具、装具及动植物价值
    
    /*private String FTGScrapAssets;  //房屋、土地及构筑物资产数量
    private String FTGScrapValue;  //房屋、土地及构筑物资产价值
    
    private String WCScrapAssets;  //文物及陈列品资产数量
    private String WCScrapValue;  //文物及陈列品资产价值
    
    private String TScrapAssets;  //图书资产数量
    private String TScrapValue;  //图书资产价值
*/    
    //二、资产清查工作结果
    private String HowManyYear;  //本次对账完成多少年的时间
    
    
    //实物清查情况
    private String SWStartYear;  //清查实物开始 年份        在日志中获取！
    private String SWStartMonth;  //清查实物开始 月份
    private String SWStartDay;  //清查实物开始 天数
    
    private String SWAfterDay;  //开始时间与结束时间的 天数
    
    private String SWEndYear;  //清查实物结束 年份
    private String SWEndMonth;  //清查实物结束 月份
    private String SWEndDay;  //清查实物结束 天数
    
    private String SWTotal;  //实物清查共计实物数量
    
    //账实情况
    private String ZSEndYear;  //账实截止 年
    private String ZSEndMonth;  //账实截止 月
    private String ZSEndDay;  //账实截止 天
    
    private String ZSXFAmount;  //账实相符数据数量
    private String ZSTotalValue;  //账实相符数据总价值
    
    //根据固定资产大类
    private String ZS_TYSBAssets;  //通用设备资产数量
    private String ZS_TYSBValue;  //通用设备资产价值
    
    private String ZS_ZYSBAssets;  //专用设备资产数量
    private String ZS_ZYSBValue;  //专用设备资产价值
    
    private String ZS_JYZDAssets;  //家具、用具、装具及动植物资产数量
    private String ZS_JYZDValue;  //家具、用具、装具及动植物资产价值
    
    private String ZS_TFGAssets;  //土地、房屋及构筑物资产数量
    private String ZS_TFGValue;  //土地、房屋及构筑物资产价值
    
    private String ZS_TSDAAssets; //图书、档案:资产数量
    private String ZS_TSDAValue; //图书、档案:资产价值
    
    private String ZS_WXZCAssets; //无形资产:资产数量
    private String ZS_WXZCValue; //无形资产:资产价值
    //条码粘贴情况
    private String TMZTAmount;  //粘贴房间数
   /* private String TMZTState;  //完成情况
    private String TMZTNote;  //备注
*/    
  //大卡粘贴情况
    private String DKZTAmount;  //粘贴房间数
    /*private String DKZTState;  //完成情况
    private String DKZTNote;  //备注
*/    
  //资产系统资产变动情况
   /* private String ZCZMValue;  //账面价值
    private String AssetsChangeAmount;  //资产变动数量
*/    
   
    //结束时间
    private String YearTail;    //清查报告结束时间 年
    private String MonthTail;   //清查报告结束时间  月
    private String DayTail;     //清查报告结束时间  日
     
    
    
    
    
    
	public String getZS_TSDAAssets() {
		return ZS_TSDAAssets;
	}



	public void setZS_TSDAAssets(String zS_TSDAAssets) {
		ZS_TSDAAssets = zS_TSDAAssets;
	}



	public String getZS_TSDAValue() {
		return ZS_TSDAValue;
	}



	public void setZS_TSDAValue(String zS_TSDAValue) {
		ZS_TSDAValue = zS_TSDAValue;
	}



	public String getZS_WXZCAssets() {
		return ZS_WXZCAssets;
	}



	public void setZS_WXZCAssets(String zS_WXZCAssets) {
		ZS_WXZCAssets = zS_WXZCAssets;
	}



	public String getZS_WXZCValue() {
		return ZS_WXZCValue;
	}



	public void setZS_WXZCValue(String zS_WXZCValue) {
		ZS_WXZCValue = zS_WXZCValue;
	}



	public String getMen() {
		return Men;
	}



	public void setMen(String men) {
		Men = men;
	}



	public String getHowManyYear() {
		return HowManyYear;
	}



	public void setHowManyYear(String howManyYear) {
		HowManyYear = howManyYear;
	}



	public String getHead() {
		return Head;
	}



	public void setHead(String head) {
		Head = head;
	}



	public String getJZYear() {
		return JZYear;
	}



	public void setJZYear(String jZYear) {
		JZYear = jZYear;
	}



	public String getJZMonth() {
		return JZMonth;
	}



	public void setJZMonth(String jZMonth) {
		JZMonth = jZMonth;
	}



	public String getJZDay() {
		return JZDay;
	}



	public void setJZDay(String jZDay) {
		JZDay = jZDay;
	}



	public String getLeader() {
		return Leader;
	}



	public void setLeader(String leader) {
		Leader = leader;
	}



	



	public String getZCQCStartYear() {
		return ZCQCStartYear;
	}



	public void setZCQCStartYear(String zCQCStartYear) {
		ZCQCStartYear = zCQCStartYear;
	}



	public String getZCQCStartMonth() {
		return ZCQCStartMonth;
	}



	public void setZCQCStartMonth(String zCQCStartMonth) {
		ZCQCStartMonth = zCQCStartMonth;
	}



	public String getZCQCStartDay() {
		return ZCQCStartDay;
	}



	public void setZCQCStartDay(String zCQCStartDay) {
		ZCQCStartDay = zCQCStartDay;
	}



	public String getZCQCEndYear() {
		return ZCQCEndYear;
	}



	public void setZCQCEndYear(String zCQCEndYear) {
		ZCQCEndYear = zCQCEndYear;
	}



	public String getZCQCEndMonth() {
		return ZCQCEndMonth;
	}



	public void setZCQCEndMonth(String zCQCEndMonth) {
		ZCQCEndMonth = zCQCEndMonth;
	}



	public String getZCQCEndDay() {
		return ZCQCEndDay;
	}



	public void setZCQCEndDay(String zCQCEndDay) {
		ZCQCEndDay = zCQCEndDay;
	}



	public String getScrapAssets() {
		return ScrapAssets;
	}



	public void setScrapAssets(String scrapAssets) {
		ScrapAssets = scrapAssets;
	}



	public String getScrapValue() {
		return ScrapValue;
	}



	public void setScrapValue(String scrapValue) {
		ScrapValue = scrapValue;
	}



	public String getTYSBAmount() {
		return TYSBAmount;
	}



	public void setTYSBAmount(String tYSBAmount) {
		TYSBAmount = tYSBAmount;
	}



	public String getTYSBScrapValue() {
		return TYSBScrapValue;
	}



	public void setTYSBScrapValue(String tYSBScrapValue) {
		TYSBScrapValue = tYSBScrapValue;
	}



	public String getZYSBAmount() {
		return ZYSBAmount;
	}



	public void setZYSBAmount(String zYSBAmount) {
		ZYSBAmount = zYSBAmount;
	}



	public String getZYSBScrapValue() {
		return ZYSBScrapValue;
	}



	public void setZYSBScrapValue(String zYSBScrapValue) {
		ZYSBScrapValue = zYSBScrapValue;
	}



	public String getJYZDScrapAssets() {
		return JYZDScrapAssets;
	}



	public void setJYZDScrapAssets(String jYZDScrapAssets) {
		JYZDScrapAssets = jYZDScrapAssets;
	}



	public String getJYZDScrapValue() {
		return JYZDScrapValue;
	}



	public void setJYZDScrapValue(String jYZDScrapValue) {
		JYZDScrapValue = jYZDScrapValue;
	}

	public String getSWStartYear() {
		return SWStartYear;
	}



	public void setSWStartYear(String sWStartYear) {
		SWStartYear = sWStartYear;
	}



	public String getSWStartMonth() {
		return SWStartMonth;
	}



	public void setSWStartMonth(String sWStartMonth) {
		SWStartMonth = sWStartMonth;
	}



	public String getSWStartDay() {
		return SWStartDay;
	}



	public void setSWStartDay(String sWStartDay) {
		SWStartDay = sWStartDay;
	}



	public String getSWAfterDay() {
		return SWAfterDay;
	}



	public void setSWAfterDay(String sWAfterDay) {
		SWAfterDay = sWAfterDay;
	}



	public String getSWEndYear() {
		return SWEndYear;
	}



	public void setSWEndYear(String sWEndYear) {
		SWEndYear = sWEndYear;
	}



	public String getSWEndMonth() {
		return SWEndMonth;
	}



	public void setSWEndMonth(String sWEndMonth) {
		SWEndMonth = sWEndMonth;
	}



	public String getSWEndDay() {
		return SWEndDay;
	}



	public void setSWEndDay(String sWEndDay) {
		SWEndDay = sWEndDay;
	}



	public String getSWTotal() {
		return SWTotal;
	}



	public void setSWTotal(String sWTotal) {
		SWTotal = sWTotal;
	}



	public String getZSEndYear() {
		return ZSEndYear;
	}



	public void setZSEndYear(String zSEndYear) {
		ZSEndYear = zSEndYear;
	}



	public String getZSEndMonth() {
		return ZSEndMonth;
	}



	public void setZSEndMonth(String zSEndMonth) {
		ZSEndMonth = zSEndMonth;
	}



	public String getZSEndDay() {
		return ZSEndDay;
	}



	public void setZSEndDay(String zSEndDay) {
		ZSEndDay = zSEndDay;
	}



	public String getZSXFAmount() {
		return ZSXFAmount;
	}



	public void setZSXFAmount(String zSXFAmount) {
		ZSXFAmount = zSXFAmount;
	}



	public String getZSTotalValue() {
		return ZSTotalValue;
	}



	public void setZSTotalValue(String zSTotalValue) {
		ZSTotalValue = zSTotalValue;
	}



	public String getZS_TYSBAssets() {
		return ZS_TYSBAssets;
	}



	public void setZS_TYSBAssets(String zS_TYSBAssets) {
		ZS_TYSBAssets = zS_TYSBAssets;
	}



	public String getZS_TYSBValue() {
		return ZS_TYSBValue;
	}



	public void setZS_TYSBValue(String zS_TYSBValue) {
		ZS_TYSBValue = zS_TYSBValue;
	}



	public String getZS_ZYSBAssets() {
		return ZS_ZYSBAssets;
	}



	public void setZS_ZYSBAssets(String zS_ZYSBAssets) {
		ZS_ZYSBAssets = zS_ZYSBAssets;
	}



	public String getZS_ZYSBValue() {
		return ZS_ZYSBValue;
	}



	public void setZS_ZYSBValue(String zS_ZYSBValue) {
		ZS_ZYSBValue = zS_ZYSBValue;
	}



	public String getZS_JYZDAssets() {
		return ZS_JYZDAssets;
	}



	public void setZS_JYZDAssets(String zS_JYZDAssets) {
		ZS_JYZDAssets = zS_JYZDAssets;
	}



	public String getZS_JYZDValue() {
		return ZS_JYZDValue;
	}



	public void setZS_JYZDValue(String zS_JYZDValue) {
		ZS_JYZDValue = zS_JYZDValue;
	}



	public String getZS_TFGAssets() {
		return ZS_TFGAssets;
	}



	public void setZS_TFGAssets(String zS_TFGAssets) {
		ZS_TFGAssets = zS_TFGAssets;
	}



	public String getZS_TFGValue() {
		return ZS_TFGValue;
	}



	public void setZS_TFGValue(String zS_TFGValue) {
		ZS_TFGValue = zS_TFGValue;
	}



	public String getTMZTAmount() {
		return TMZTAmount;
	}



	public void setTMZTAmount(String tMZTAmount) {
		TMZTAmount = tMZTAmount;
	}



	public String getDKZTAmount() {
		return DKZTAmount;
	}



	public void setDKZTAmount(String dKZTAmount) {
		DKZTAmount = dKZTAmount;
	}



	public String getYearTail() {
		return YearTail;
	}



	public void setYearTail(String yearTail) {
		YearTail = yearTail;
	}



	public String getMonthTail() {
		return MonthTail;
	}



	public void setMonthTail(String monthTail) {
		MonthTail = monthTail;
	}



	public String getDayTail() {
		return DayTail;
	}



	public void setDayTail(String dayTail) {
		DayTail = dayTail;
	}

	@Override
	public String toString() {
		return "WrodAction [Head=" + Head + ", JZYear=" + JZYear + ", JZMonth=" + JZMonth + ", JZDay=" + JZDay
				+ ", Leader=" + Leader + ", Men=" + Men + ", ZCQCStartYear=" + ZCQCStartYear + ", ZCQCStartMonth="
				+ ZCQCStartMonth + ", ZCQCStartDay=" + ZCQCStartDay + ", ZCQCEndYear=" + ZCQCEndYear + ", ZCQCEndMonth="
				+ ZCQCEndMonth + ", ZCQCEndDay=" + ZCQCEndDay + ", ScrapAssets=" + ScrapAssets + ", ScrapValue="
				+ ScrapValue + ", TYSBAmount=" + TYSBAmount + ", TYSBScrapValue=" + TYSBScrapValue + ", ZYSBAmount="
				+ ZYSBAmount + ", ZYSBScrapValue=" + ZYSBScrapValue + ", JYZDScrapAssets=" + JYZDScrapAssets
				+ ", JYZDScrapValue=" + JYZDScrapValue + ", HowManyYear=" + HowManyYear + ", SWStartYear=" + SWStartYear
				+ ", SWStartMonth=" + SWStartMonth + ", SWStartDay=" + SWStartDay + ", SWAfterDay=" + SWAfterDay
				+ ", SWEndYear=" + SWEndYear + ", SWEndMonth=" + SWEndMonth + ", SWEndDay=" + SWEndDay + ", SWTotal="
				+ SWTotal + ", ZSEndYear=" + ZSEndYear + ", ZSEndMonth=" + ZSEndMonth + ", ZSEndDay=" + ZSEndDay
				+ ", ZSXFAmount=" + ZSXFAmount + ", ZSTotalValue=" + ZSTotalValue + ", ZS_TYSBAssets=" + ZS_TYSBAssets
				+ ", ZS_TYSBValue=" + ZS_TYSBValue + ", ZS_ZYSBAssets=" + ZS_ZYSBAssets + ", ZS_ZYSBValue="
				+ ZS_ZYSBValue + ", ZS_JYZDAssets=" + ZS_JYZDAssets + ", ZS_JYZDValue=" + ZS_JYZDValue
				+ ", ZS_TFGAssets=" + ZS_TFGAssets + ", ZS_TFGValue=" + ZS_TFGValue + ", ZS_TSDAAssets=" + ZS_TSDAAssets
				+ ", ZS_TSDAValue=" + ZS_TSDAValue + ", ZS_WXZCAssets=" + ZS_WXZCAssets + ", ZS_WXZCValue="
				+ ZS_WXZCValue + ", TMZTAmount=" + TMZTAmount + ", DKZTAmount=" + DKZTAmount + ", YearTail=" + YearTail
				+ ", MonthTail=" + MonthTail + ", DayTail=" + DayTail + "]";
	}
    
}
