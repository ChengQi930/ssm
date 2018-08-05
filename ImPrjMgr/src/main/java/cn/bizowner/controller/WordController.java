package cn.bizowner.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.WrodAction;
import cn.bizowner.service.WordService;
import cn.bizowner.service.impl.WordDownloadService;
import cn.bizowner.sqlite.WordData;
import cn.bizowner.utils.DateCalculate;
import cn.bizowner.utils.TimeUtil;
import cn.bizowner.utils.WordUtil;

@Controller
@RequestMapping("/Word")
public class WordController {
	
	@Autowired
	private WordDownloadService WordDownload;
    
	
	
    @RequestMapping(value = "/exportMillCertificate", method = RequestMethod.GET)
    public @ResponseBody void exportMillCertificate(HttpServletRequest request,
                      HttpServletResponse response,String WordName) throws Exception {
    	String attribute = (String) request.getSession().getAttribute("proId"); 
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	WrodAction createUserListWord = WordDownload.createUserListWord(request); //数据模型对象
    	
        
        
        map.put("Head",createUserListWord.getHead());
        map.put("JZYear",createUserListWord.getJZYear());
        map.put("JZMonth", createUserListWord.getJZMonth());
        map.put("JZDay", createUserListWord.getJZDay());
        map.put("Leader",createUserListWord.getLeader()); //组长
        map.put("Men",createUserListWord.getMen());  //组员
        map.put("ZCQCStartYear", createUserListWord.getZCQCStartYear());  //资产清查计划 计划开始时间
        map.put("ZCQCStartMonth", createUserListWord.getZCQCStartMonth());
        map.put("ZCQCStartDay", createUserListWord.getZCQCStartDay());
        map.put("ZCQCEndYear", createUserListWord.getZCQCEndYear());  //资产清查计划 计划结束时间
        map.put("ZCQCEndMonth", createUserListWord.getZCQCEndMonth());
        map.put("ZCQCEndDay", createUserListWord.getZCQCEndDay());
                                                                      
        //资产系统情况     建议报废
        map.put("ScrapAssets", createUserListWord.getScrapAssets());
        map.put("ScrapValue", createUserListWord.getScrapValue());
        map.put("TYSBAmount", createUserListWord.getTYSBAmount());
        map.put("TYSBScrapValue",createUserListWord.getTYSBScrapValue());
        map.put("ZYSBAmount",createUserListWord.getZYSBAmount());
        map.put("ZYSBScrapValue", createUserListWord.getZYSBScrapValue());
        map.put("JYZDScrapAssets",createUserListWord.getJYZDScrapAssets());
        map.put("JYZDScrapValue",createUserListWord.getJYZDScrapValue());
        
        map.put("HowManyYear",createUserListWord.getHowManyYear());  //本次对账完成多少年的时间
        
        map.put("SWStartYear",createUserListWord.getSWStartYear());  //清查实物 开始时间
        map.put("SWStartMonth", createUserListWord.getSWStartMonth());
        map.put("SWStartDay", createUserListWord.getSWStartDay());
        
        map.put("SWAfterDay", createUserListWord.getSWAfterDay());  //开始时间与结束时间的 天数
        
        map.put("SWEndYear", createUserListWord.getSWEndYear());   //清查实物 结束时间
        map.put("SWEndMonth", createUserListWord.getSWEndMonth());
        map.put("SWEndDay", createUserListWord.getSWEndDay());
        
        map.put("SWTotal", createUserListWord.getSWTotal());   //实物清查共计实物数量
        
        //账实    情况
        map.put("ZSEndYear", createUserListWord.getZSEndYear());
        map.put("ZSEndMonth",createUserListWord.getZSEndMonth());
        map.put("ZSEndDay",createUserListWord.getZSEndDay());
        
        map.put("ZSXFAmount",createUserListWord.getZSXFAmount());  //账实相符 的 数量
        map.put("ZSTotalValue", createUserListWord.getZSTotalValue()); //账实相符 的 金额
        
        map.put("ZS_TYSBAssets", createUserListWord.getZS_TYSBAssets()); 
        map.put("ZS_TYSBValue", createUserListWord.getZS_TYSBValue());
        map.put("ZS_ZYSBAssets", createUserListWord.getZS_ZYSBAssets());
        map.put("ZS_ZYSBValue", createUserListWord.getZS_ZYSBValue());
        map.put("ZS_JYZDAssets", createUserListWord.getZS_JYZDAssets());
        map.put("ZS_JYZDValue", createUserListWord.getZS_JYZDValue());
        map.put("ZS_TFGAssets", createUserListWord.getZS_TFGAssets());
        map.put("ZS_TFGValue", createUserListWord.getZS_TFGValue());
        map.put("ZS_TSDAAssets",createUserListWord.getZS_TSDAAssets());
        map.put("ZS_TSDAValue",createUserListWord.getZS_TSDAValue());
        map.put("ZS_WXZCAssets", createUserListWord.getZS_WXZCAssets());
        map.put("ZS_WXZCValue", createUserListWord.getZS_WXZCValue());
        
        map.put("TMZTAmount", createUserListWord.getTMZTAmount());
        map.put("DKZTAmount", createUserListWord.getDKZTAmount());
        
        map.put("YearTail", createUserListWord.getYearTail());
        map.put("MonthTail", createUserListWord.getMonthTail());
        map.put("DayTail", createUserListWord.getDayTail());
        WordUtil.exportMillCertificateWord(request,response,map,WordName);
    }
}
