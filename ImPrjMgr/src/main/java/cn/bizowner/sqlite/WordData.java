package cn.bizowner.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.IA_Users;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.imp_qc_task;
import cn.bizowner.utils.DateCalculate;
import cn.bizowner.utils.Utils;

public class WordData {
     
	//获取截止日期！
   public static List<JqAssetAccount> getimp_jq_asset(String attribute) throws Exception{
		
		String dbpath = Utils.dbPath+attribute+".db";
		
		String select2 = "select *,max(get_date) from imp_jq_asset";
		
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
        List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(select2);
        
        ArrayList<JqAssetAccount> arrayList = null;
		for (Map<String, Object> map : executeQuery1) {
			 String id =  (String) map.get("id");
			 String fileId = (String)map.get("fileId");
			 String assetBarcode = (String)map.get("assetBarcode");
			 String assetBigclass = (String)map.get("assetBigclass");
			 String assetClass = (String)map.get("assetClass");
			 String assetName = (String)map.get("assetName");
			 String moneyType = (String)map.get("moneyType");
			 String money = (Integer)map.get("money")+"";
			 String getType = (String)map.get("getType");
			 String get_date = (String)map.get("get_date");
			 String useStatus = (String)map.get("useStatus");
			 String remark = (String)map.get("remark");
			 String useDirect = (String)map.get("useDirect");
			 String useDept = (String)map.get("useDept");
			 String manageDept = (String)map.get("manageDept");
			 String useUser = (String)map.get("useUser");
			 String sl = (Integer)map.get("sl")+"";
			 String documentMaker = (String)map.get("documentMaker");
			 String documentMaktime = (String)map.get("documentMaktime");
			 String checkNo = (String)map.get("checkNo");
			 String belongCompany = (String)map.get("belongCompany");
			 String cardStatus = (String)map.get("cardStatus");
			 String totalDepreciate = (Integer)map.get("totalDepreciate")+"";
			 String alreadyDepreciateMonth = (Integer)map.get("alreadyDepreciateMonth")+"";
			 String netValue = (Integer)map.get("netValue")+"";
			 String budgetProjectNo = (String)map.get("budgetProjectNo");
			 String buyOrgType = (String)map.get("buyOrgType");
			 String assetPp = (String)map.get("assetPp");
			 String assetModel = (String)map.get("assetModel");
			 String saveAddress = (String)map.get("saveAddress");
			 String manufacturer = (String)map.get("manufacturer");
			 String seller = (String)map.get("seller");
			 String contractNo = (String)map.get("contractNo");
			 String invoiceNo = (String)map.get("invoiceNo");
			 String useArea = (Integer)map.get("useArea")+"";
			 String structure = (String)map.get("structure");
			 String designUse = (String)map.get("designUse");
			 String financialFunds = (Integer)map.get("financialFunds")+"";
			 String notFinancialFunds = (Integer)map.get("notFinancialFunds")+"";
			 String accountNo = (String)map.get("accountNo");
			 String propertyRight = (String)map.get("propertyRight");
			 String ownerType = (String)map.get("ownerType");
			 String ownerNo = (String)map.get("ownerNo");
			 String landUseType = (String)map.get("landUseType");
			 String issueDate = (String)map.get("issueDate");
			 String landUse = (String)map.get("landUse");
			 String landOrder = (String)map.get("landOrder");
			 String depreciateStatus = (String)map.get("depreciateStatus");
			 String location = (String)map.get("location");
			 String carUse = (String)map.get("carUse");
			 String carProduce = (String)map.get("carProduce");
			 String carNo = (String)map.get("carNo");
			 String carPl = (String)map.get("carPl");
			 
			 
			 JqAssetAccount jq = new JqAssetAccount();
			  jq.setId(id);
			  jq.setFileId(fileId);
   		      jq.setAssetBarcode(assetBarcode);
   		      jq.setAssetBigClass(assetBigclass);
   		      jq.setAssetClass(assetClass);
   		      jq.setAssetName(assetName);
   		      jq.setMoneyType(moneyType);
   		      jq.setMoney(Double.parseDouble(money));
   		      jq.setGetType(getType);
   		      jq.setGetDate(DateCalculate.getDate(get_date));
   		      jq.setUseStatus(useStatus);
   		      jq.setRemark(remark);
   		      jq.setUseDirect(useDirect);
   		      jq.setUseDept(useDept);
   		      jq.setManageDept(manageDept);
   		      jq.setUseUser(useUser);
   		      jq.setSl(Integer.parseInt(sl));
   		      jq.setDocumentMaker(documentMaker);
   		      jq.setDocumentMakTime(DateCalculate.getDate(documentMaktime));
   		      jq.setCheckNo(checkNo);
   		      jq.setBelongCompany(belongCompany);
   		      jq.setCardStatus(cardStatus);
   		      jq.setTotalDepreciate(Double.parseDouble(totalDepreciate));
   		      jq.setAlreadyDepreciateMonth(Integer.parseInt(alreadyDepreciateMonth));
   		      jq.setNetValue(Double.parseDouble(netValue));
   		      jq.setBudgetProjectNo(budgetProjectNo);
   		      jq.setBuyOrgType(buyOrgType);
   		      jq.setAssetPp(assetPp);
   		      jq.setAssetModel(assetModel);
   		      jq.setSaveAddress(saveAddress);
   		      jq.setManufacturer(manufacturer);
   		      jq.setSeller(seller);
   		      jq.setContractNo(contractNo);
   		      jq.setInvoiceNo(invoiceNo);
   		      jq.setUseArea(Double.parseDouble(useArea));
   		      jq.setStructure(structure);
   		      jq.setDesignUse(designUse);
   		      jq.setFinancialFunds(Double.parseDouble(financialFunds));
   		      jq.setNotFinancialFunds(Double.parseDouble(notFinancialFunds));
   		      jq.setAccountNo(accountNo);
   		      jq.setPropertyRight(propertyRight);
   		      jq.setOwnerType(ownerType);
   		      jq.setOwnerNo(ownerNo);
   		      jq.setLandUseType(landUseType);
   		      
   		      String date = DateCalculate.getDateString(DateCalculate.getDate(issueDate));  
   		      jq.setIssueDate(date.equals("null") == true ? DateCalculate.getDate(date):DateCalculate.getDate(issueDate));
   		      jq.setLandUse(landUse);
   		      jq.setLandOrder(landOrder);
   		      jq.setDepreciateStatus(depreciateStatus);
   		      jq.setLocation(location);
   		      jq.setCarUse(carUse);
   		      jq.setCarProduce(carProduce);
   		      jq.setCarNo(carNo);
   		      jq.setCarPl(carPl);
   		 
   		       arrayList = new ArrayList<JqAssetAccount>();
   		   arrayList.add(jq);
		}
		return arrayList;
	}
   
   public static String getImpSwAsset(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select count(*) as count from imp_sw_asset";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
       List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);
       String count = null;
       for (Map<String, Object> map : executeQuery1) {
    	   count = (Integer) map.get("count")+"";
	}
	   return count;
   };
   
   //清查组长
   public static String getLeader(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select * from imp_qc_task";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
     List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);   
     String Leader = null;
     for (Map<String, Object> map : executeQuery1) {
   	   Leader = (String) map.get("leader");
	}
	  return Leader;
   }
   //清查 组员
   public static String getMen(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select * from imp_qc_task";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
     List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);   
     String men = null;
     for (Map<String, Object> map : executeQuery1) {
   	   men = (String) map.get("men");
	}
	  return men;
   }
   
   
   
   //建议报废的 电子产品 数量 与 总 金额
   public static List<String> getScrapElectronic(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select *,count(*) as count,sum(money)as sum from imp_jq_asset jq where jq.assetBigclass = '通用设备' and julianday(date()) - julianday(jq.get_date) >= 2190";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
      List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);
     
      ArrayList<String> arrayList = new ArrayList<>();
      for (Map<String, Object> map : executeQuery1) {
    	  String count = (Integer)map.get("count")+"";
    	  String money = (Integer)map.get("sum")+"";
    	  arrayList.add(count);
    	  arrayList.add(money);
	}
	  return arrayList;
   }
   
 //建议报废的 家具、用具、装具及动植物  数量 与 总 金额
   public static List<String> getFurniture(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select *,count(*) as count,sum(money) as sum from imp_jq_asset jq where jq.assetBigclass = '家具、用具、装具及动植物' and julianday(date()) - julianday(jq.get_date) >= 5475";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
      List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);
     
      ArrayList<String> arrayList = new ArrayList<>();
      for (Map<String, Object> map : executeQuery1) {
    	  String count = (Integer)map.get("count")+"";
    	  String money = (Integer)map.get("sum")+"";
    	  arrayList.add(count);
    	  arrayList.add(money);
	}
	  return arrayList;
   }
   
 //建议报废的 专用设备  数量 与 总 金额
   public static List<String> getSpecialEquipment(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select *,count(*) as count,sum(money) as sum  from imp_jq_asset jq  where (jq.assetClass ='探矿、采矿、选矿和造块设备' OR jq.assetClass ='石油天然气开采专用设备' OR jq.assetClass ='石油和化学工业专用设备' OR jq.assetClass ='炼焦和金属冶炼轧制设备' OR jq.assetClass ='工程机械' OR jq.assetClass ='农业和林业机械' OR jq.assetClass ='木材采集和加工设备' OR jq.assetClass ='食品加工专用设备' OR jq.assetClass ='饮料加工设备' OR jq.assetClass ='烟草加工设备' OR jq.assetClass ='粮油作物和饲料加工设备' OR jq.assetClass ='纺织设备' OR jq.assetClass ='缝纫、服饰、制革和毛皮加工设备' OR jq.assetClass ='邮政专用设备' and julianday(date()) - julianday(jq.get_date) between 3650 and 5475)||(jq.assetClass = '电力工业专用设备' OR jq.assetClass ='核工业专用设备' OR jq.assetClass ='航空航天工业专用设备' and julianday(date()) - julianday(jq.get_date) between 7300 and 10950)|| (jq.assetClass = '非金属矿物制品工业专用设备' OR jq.assetClass ='造纸和印刷机械' OR jq.assetClass ='安全生产设备'  OR jq.assetClass ='环境污染防治设备' OR jq.assetClass ='水工机械' OR jq.assetClass ='铁路运输设备' OR jq.assetClass ='水上交通运输设备' OR jq.assetClass ='航空器及其配套设备' and julianday(date()) - julianday(jq.get_date) between 3650 and 7300)|| (jq.assetClass = '化学药品和中药专用设备' OR jq.assetClass ='医疗设备' OR jq.assetClass ='电工、电子专用生产设备' OR jq.assetClass ='殡葬设备及用品' OR jq.assetClass ='专用仪器仪表' and julianday(date()) - julianday(jq.get_date) between 1825 and 3650)||(jq.assetClass = '文艺设备' OR jq.assetClass ='体育设备' OR jq.assetClass ='娱乐设备'  and julianday(date()) - julianday(jq.get_date) between 1825 and 5475)||(jq.assetClass = '公安专用设备' and julianday(date()) - julianday(jq.get_date) between 1095 and 3650)and jq.assetBigclass = '专用设备'";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
      List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);
     
      ArrayList<String> arrayList = new ArrayList<>();
      for (Map<String, Object> map : executeQuery1) {
    	  String count = (Integer)map.get("count")+"";
    	  String money = (Integer)map.get("sum")+"";
    	  arrayList.add(count);
    	  arrayList.add(money);
	}
	  return arrayList;
   }
   
 //统计清查 多少年
   public static String getHowManyYear(String attribute) throws Exception{
	   String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select *,count(*) num from (select *,substr(get_date,1,4) s from imp_jq_asset jq group by s)";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
     List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);   
     String num = null;
     for (Map<String, Object> map : executeQuery1) {
   	   num = (Integer) map.get("num")+"";
	}
	  return num;
   }
   
   //账时相符的 数量
  public static List<String> getZSXFTotal(String attribute) throws Exception{
	  String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select *,count(*) total,sum(jq.money) Totalmoney from imp_rela_jq_sw as jq_sw join imp_jq_asset as jq on  jq_sw.jqId = jq.id";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
   List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset); 
   
   ArrayList<String> arrayList = new ArrayList<>();
   for (Map<String, Object> map : executeQuery1) {
 	  String total = (Integer) map.get("total")+"";
 	  String Totalmoney = (Integer) map.get("Totalmoney")+"";
 	  arrayList.add(total);
 	  arrayList.add(Totalmoney);
	}
	  return arrayList;	  
  }
  
  //账时相符的 所有 大类 数量 与 价值
  public static LinkedHashMap<Object,List<String>> getZSXFTotalWithValue(String attribute) throws Exception{
	  String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select jq.assetBigclass,count(*) ZSXFTotal ,sum(money) ZSXFMoney from imp_rela_jq_sw as  jq_sw  join imp_jq_asset as jq on jq_sw.jqId = jq.id group by jq.assetBigclass";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);		
   List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);
   
   LinkedHashMap<Object,List<String>> linkedHashMap = new LinkedHashMap<>();
   
   for (Map<String, Object> map : executeQuery1) {
		   ArrayList<String> TYSBList = new ArrayList<>();
		   TYSBList.add(map.get("ZSXFTotal").equals("null") == true? "0" : map.get("ZSXFTotal")+"");
		   TYSBList.add(map.get("ZSXFMoney").equals("null") == true? "0" : map.get("ZSXFMoney")+"");
		   linkedHashMap.put(map.get("assetBigclass"), TYSBList);
	}
        
	  return linkedHashMap;	  
  }
  
  //贴条码的房间数量
  public static String getBarcode(String attribute) throws Exception{
	  String dbpath = Utils.dbPath+attribute+".db";
		String ImpSwAsset = "select count(*) as barcode from (select * from imp_sw_asset sw group by sw.saveAddress)";
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
   List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(ImpSwAsset);   
   String barcode = null;
   for (Map<String, Object> map : executeQuery1) {
	   barcode = (Integer) map.get("barcode")+"";
	}
	  return barcode;
  }
  
}
