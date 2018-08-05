package cn.bizowner.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.utils.CommonUtils;

public class JqAccountUtil {

	
	
	
	
	
	public static List<String> getAssetNamesByIds(String dbPath,List<String> listIds) throws Exception
	{
		List<String> listBarcodes = new ArrayList<String>();
		 
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		 
		String selectSql = "select assetBarcode from imp_jq_asset where id in (";
		for(int i=0;i<listIds.size();i++)
		{
				String str = listIds.get(i).toString();
				selectSql+="'"+str+"',";
		}
		
		selectSql = selectSql.substring(0,selectSql.length()-1);
		selectSql+=")";
		
		
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		for(int i=0;i<listMainMap.size();i++)
		{
					String assetBarcode = listMainMap.get(i).get("assetBarcode").toString();
					listBarcodes.add(assetBarcode);
		}
		
		return listBarcodes;
			
	}
	
	
	
	
	
	
	
	
	
	
	
	public static List<JqAssetAccount> getJqAccountAsset(String dbPath,String selectSql) throws Exception
	{
		List<JqAssetAccount> listJqAssetAccount = new ArrayList<JqAssetAccount>();
		 
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		for(int i=0;i<listMainMap.size();i++)
		{
			
			Map<String, Object> mainMap = listMainMap.get(i);
			
			JqAssetAccount jqAssetAccount = new JqAssetAccount();
		 	
		 	jqAssetAccount.setId((String)mainMap.get("id"));
		 	jqAssetAccount.setFileId((String)mainMap.get("fileId"));
			jqAssetAccount.setAssetBarcode((String)mainMap.get("assetBarcode"));
			jqAssetAccount.setAssetBigClass((String)mainMap.get("assetBigclass"));
			jqAssetAccount.setAssetClass((String)mainMap.get("assetClass"));
			jqAssetAccount.setAssetName((String)mainMap.get("assetName"));
			jqAssetAccount.setAssetCwrzrq(CommonUtils.convertStringToDate((String)mainMap.get("assetCwrzrq")));
			jqAssetAccount.setMoneyType((String)mainMap.get("moneyType"));
			
			jqAssetAccount.setMoney(NumberUtils.toDouble(mainMap.get("money").toString(),0));
			jqAssetAccount.setGetType((String)mainMap.get("getType"));
			jqAssetAccount.setGetDate(CommonUtils.convertStringToDate((String)mainMap.get("get_date")));
			jqAssetAccount.setUseStatus((String)mainMap.get("useStatus"));
			jqAssetAccount.setRemark((String)mainMap.get("remark"));
			jqAssetAccount.setUseDirect((String)mainMap.get("useDirect"));
			jqAssetAccount.setUseDept((String)mainMap.get("useDept"));
			jqAssetAccount.setManageDept((String)mainMap.get("manageDept"));
			jqAssetAccount.setUseUser((String)mainMap.get("useUser"));
			jqAssetAccount.setSl(NumberUtils.toInt(mainMap.get("sl").toString(),0));
			jqAssetAccount.setDocumentMaker((String)mainMap.get("documentMaker"));
			jqAssetAccount.setDocumentMakTime(CommonUtils.convertStringToDate((String)mainMap.get("documentMaktime")));
			jqAssetAccount.setCheckNo((String)mainMap.get("checkNo"));
			jqAssetAccount.setBelongCompany((String)mainMap.get("belongCompany"));
			jqAssetAccount.setCardStatus((String)mainMap.get("cardStatus"));
			jqAssetAccount.setTotalDepreciate(NumberUtils.toDouble(mainMap.get("totalDepreciate").toString(), 0));
			jqAssetAccount.setAlreadyDepreciateMonth(NumberUtils.toInt(mainMap.get("alreadyDepreciateMonth").toString(),0));
			jqAssetAccount.setNetValue(NumberUtils.toDouble(mainMap.get("netValue").toString(),0));
			jqAssetAccount.setBudgetProjectNo((String)mainMap.get("budgetProjectNo"));
			jqAssetAccount.setBuyOrgType((String)mainMap.get("buyOrgType"));
			jqAssetAccount.setAssetPp((String)mainMap.get("assetPp"));
			jqAssetAccount.setAssetModel((String)mainMap.get("assetModel"));
			jqAssetAccount.setSaveAddress((String)mainMap.get("saveAddress"));
			jqAssetAccount.setManufacturer((String)mainMap.get("manufacturer"));
			jqAssetAccount.setSeller((String)mainMap.get("seller"));
			jqAssetAccount.setContractNo((String)mainMap.get("contractNo"));
			jqAssetAccount.setInvoiceNo((String)mainMap.get("invoiceNo"));
			jqAssetAccount.setUseArea(NumberUtils.toDouble(mainMap.get("useArea").toString(),0));
			jqAssetAccount.setStructure((String)mainMap.get("structure"));
			jqAssetAccount.setDesignUse((String)mainMap.get("designUse"));
			jqAssetAccount.setFinancialFunds(NumberUtils.toDouble(mainMap.get("financialFunds").toString(),0));
			jqAssetAccount.setNotFinancialFunds(NumberUtils.toDouble(mainMap.get("notFinancialFunds").toString(),0));
			jqAssetAccount.setAccountNo((String)mainMap.get("accountNo"));
			jqAssetAccount.setPropertyRight((String)mainMap.get("propertyRight"));
			jqAssetAccount.setOwnerType((String)mainMap.get("ownerType"));
			jqAssetAccount.setOwnerNo((String)mainMap.get("ownerNo"));
			jqAssetAccount.setLandUseType((String)mainMap.get("landUseType"));
			jqAssetAccount.setIssueDate(CommonUtils.convertStringToDate((String)mainMap.get("issueDate")));
			jqAssetAccount.setLandUse((String)mainMap.get("landUse"));
			jqAssetAccount.setLandOrder((String)mainMap.get("landOrder"));
			jqAssetAccount.setDepreciateStatus((String)mainMap.get("depreciateStatus"));
			jqAssetAccount.setLocation((String)mainMap.get("location"));
			jqAssetAccount.setCarUse((String)mainMap.get("carUse"));
			jqAssetAccount.setCarProduce((String)mainMap.get("carProduce"));
			jqAssetAccount.setCarNo((String)mainMap.get("carNo"));
			jqAssetAccount.setCarPl((String)mainMap.get("carPl"));
			 
			listJqAssetAccount.add(jqAssetAccount);
		} 
		
		return listJqAssetAccount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<JqAssetAccount> getJqAccountAsset(String dbPath,String fileId,int type,int start,int limit,JqAccountQueryInfo jqAccountQueryInfo) throws Exception
	{
		List<JqAssetAccount> listJqAssetAccount = new ArrayList<JqAssetAccount>();
		
		
		
		String selectSql = null;
		
		
		String whereSql =" where 1=1 and fileId='"+fileId+"' ";
		
		if(jqAccountQueryInfo != null)
		{
			if(jqAccountQueryInfo.getAssetBarcode() != null && jqAccountQueryInfo.getAssetBarcode().length()>0)
			{
				whereSql+=" and assetBarcode like '%"+jqAccountQueryInfo.getAssetBarcode()+"%'";
			}
			if(jqAccountQueryInfo.getAssetName() != null && jqAccountQueryInfo.getAssetName().length()>0)
			{
				whereSql+=" and assetName like '%"+jqAccountQueryInfo.getAssetName()+"%'";
			}
			if(jqAccountQueryInfo.getUseDept() != null && jqAccountQueryInfo.getUseDept().length()>0)
			{
				whereSql+=" and useDept like '%"+jqAccountQueryInfo.getUseDept()+"%'";
			}
			if(jqAccountQueryInfo.getSaveAddress() != null && jqAccountQueryInfo.getSaveAddress().length()>0)
			{
				whereSql+=" and saveAddress like '%"+jqAccountQueryInfo.getSaveAddress()+"%'";
			}
			if(jqAccountQueryInfo.getAssetClass() != null && jqAccountQueryInfo.getAssetClass().length()>0)
			{
				whereSql+=" and assetClass like '%"+jqAccountQueryInfo.getAssetClass()+"%'";
			}
			if(jqAccountQueryInfo.getAssetBigClass() != null && jqAccountQueryInfo.getAssetBigClass().length()>0)
			{
				whereSql+=" and assetBigclass like '%"+jqAccountQueryInfo.getAssetBigClass()+"%'";
			}
		}
		
		
		
		
		if(type == 1)
		{
			//财务久其对账
			selectSql = "select imp_jq_asset.*,imp_rela_cw_jq.id as relaId from imp_jq_asset left join imp_rela_cw_jq on imp_jq_asset.id=imp_rela_cw_jq.jqId "+whereSql;
		}
		else
		{
			//久其实物对账
			selectSql = "select imp_jq_asset.*,imp_rela_jq_sw.id as relaId from imp_jq_asset left join imp_rela_jq_sw on imp_jq_asset.id=imp_rela_jq_sw.jqId "+whereSql;
		}
		
		if(limit > 0)
		{
			selectSql +=" limit "+ start+","+limit;
		}
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		for(int i=0;i<listMainMap.size();i++)
		{
			
			Map<String, Object> mainMap = listMainMap.get(i);
			
			JqAssetAccount jqAssetAccount = new JqAssetAccount();
		 	
		 	jqAssetAccount.setId((String)mainMap.get("id"));
		 	jqAssetAccount.setFileId((String)mainMap.get("fileId"));
			jqAssetAccount.setAssetBarcode((String)mainMap.get("assetBarcode"));
			jqAssetAccount.setAssetBigClass((String)mainMap.get("assetBigclass"));
			jqAssetAccount.setAssetClass((String)mainMap.get("assetClass"));
			jqAssetAccount.setAssetName((String)mainMap.get("assetName"));
			jqAssetAccount.setAssetCwrzrq(CommonUtils.convertStringToDate((String)mainMap.get("assetCwrzrq")));
			jqAssetAccount.setMoneyType((String)mainMap.get("moneyType"));
			
			jqAssetAccount.setMoney(NumberUtils.toDouble(mainMap.get("money").toString(),0));
			jqAssetAccount.setGetType((String)mainMap.get("getType"));
			jqAssetAccount.setGetDate(CommonUtils.convertStringToDate((String)mainMap.get("get_date")));
			jqAssetAccount.setUseStatus((String)mainMap.get("useStatus"));
			jqAssetAccount.setRemark((String)mainMap.get("remark"));
			jqAssetAccount.setUseDirect((String)mainMap.get("useDirect"));
			jqAssetAccount.setUseDept((String)mainMap.get("useDept"));
			jqAssetAccount.setManageDept((String)mainMap.get("manageDept"));
			jqAssetAccount.setUseUser((String)mainMap.get("useUser"));
			jqAssetAccount.setSl(NumberUtils.toInt(mainMap.get("sl").toString(),0));
			jqAssetAccount.setDocumentMaker((String)mainMap.get("documentMaker"));
			jqAssetAccount.setDocumentMakTime(CommonUtils.convertStringToDate((String)mainMap.get("documentMaktime")));
			jqAssetAccount.setCheckNo((String)mainMap.get("checkNo"));
			jqAssetAccount.setBelongCompany((String)mainMap.get("belongCompany"));
			jqAssetAccount.setCardStatus((String)mainMap.get("cardStatus"));
			jqAssetAccount.setTotalDepreciate(NumberUtils.toDouble(mainMap.get("totalDepreciate").toString(), 0));
			jqAssetAccount.setAlreadyDepreciateMonth(NumberUtils.toInt(mainMap.get("alreadyDepreciateMonth").toString(),0));
			jqAssetAccount.setNetValue(NumberUtils.toDouble(mainMap.get("netValue").toString(),0));
			jqAssetAccount.setBudgetProjectNo((String)mainMap.get("budgetProjectNo"));
			jqAssetAccount.setBuyOrgType((String)mainMap.get("buyOrgType"));
			jqAssetAccount.setAssetPp((String)mainMap.get("assetPp"));
			jqAssetAccount.setAssetModel((String)mainMap.get("assetModel"));
			jqAssetAccount.setSaveAddress((String)mainMap.get("saveAddress"));
			jqAssetAccount.setManufacturer((String)mainMap.get("manufacturer"));
			jqAssetAccount.setSeller((String)mainMap.get("seller"));
			jqAssetAccount.setContractNo((String)mainMap.get("contractNo"));
			jqAssetAccount.setInvoiceNo((String)mainMap.get("invoiceNo"));
			jqAssetAccount.setUseArea(NumberUtils.toDouble(mainMap.get("useArea").toString(),0));
			jqAssetAccount.setStructure((String)mainMap.get("structure"));
			jqAssetAccount.setDesignUse((String)mainMap.get("designUse"));
			jqAssetAccount.setFinancialFunds(NumberUtils.toDouble(mainMap.get("financialFunds").toString(),0));
			jqAssetAccount.setNotFinancialFunds(NumberUtils.toDouble(mainMap.get("notFinancialFunds").toString(),0));
			jqAssetAccount.setAccountNo((String)mainMap.get("accountNo"));
			jqAssetAccount.setPropertyRight((String)mainMap.get("propertyRight"));
			jqAssetAccount.setOwnerType((String)mainMap.get("ownerType"));
			jqAssetAccount.setOwnerNo((String)mainMap.get("ownerNo"));
			jqAssetAccount.setLandUseType((String)mainMap.get("landUseType"));
			jqAssetAccount.setIssueDate(CommonUtils.convertStringToDate((String)mainMap.get("issueDate")));
			jqAssetAccount.setLandUse((String)mainMap.get("landUse"));
			jqAssetAccount.setLandOrder((String)mainMap.get("landOrder"));
			jqAssetAccount.setDepreciateStatus((String)mainMap.get("depreciateStatus"));
			jqAssetAccount.setLocation((String)mainMap.get("location"));
			jqAssetAccount.setCarUse((String)mainMap.get("carUse"));
			jqAssetAccount.setCarProduce((String)mainMap.get("carProduce"));
			jqAssetAccount.setCarNo((String)mainMap.get("carNo"));
			jqAssetAccount.setCarPl((String)mainMap.get("carPl"));
			
			String relaId = (String)mainMap.get("relaId");
			
			if(relaId == null)
			{
				jqAssetAccount.setRela(false);
			}
			else
			{
				jqAssetAccount.setRela(true);
			}
			
			listJqAssetAccount.add(jqAssetAccount);
		}
		
		
		
		
		
		
		
		return listJqAssetAccount;
	}
	
	
	 
	
	public static JqAssetAccount getJqAssetDetailById(String dbPath,String fileId,int type,String id) throws Exception
	{
		JqAssetAccount jqAssetAccount = new  JqAssetAccount();
		
		
		
		String selectSql = null;
		
		
		if(type == 1)
		{
			//财务久其对账
			selectSql = "select imp_jq_asset.*,imp_rela_cw_jq.id as relaId from imp_jq_asset left join imp_rela_cw_jq on imp_jq_asset.id=imp_rela_cw_jq.jqId "+
			" where imp_jq_asset.fileId='"+fileId+"' and imp_jq_asset.id='"+id+"' ";
		}
		else
		{
			//久其实物对账
			selectSql = "select imp_jq_asset.*,imp_rela_jq_sw.id as relaId from imp_jq_asset left join imp_rela_jq_sw on imp_jq_asset.id=imp_rela_jq_sw.jqId "+
			" where imp_jq_asset.fileId='"+fileId+"' and imp_jq_asset.id='"+id+"' ";
		}
		
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		
		if(listMainMap.size() == 1)
		{
				Map<String, Object> mainMap = listMainMap.get(0);
				
				jqAssetAccount.setId((String)mainMap.get("id"));
			 	jqAssetAccount.setFileId((String)mainMap.get("fileId"));
				jqAssetAccount.setAssetBarcode((String)mainMap.get("assetBarcode"));
				jqAssetAccount.setAssetBigClass((String)mainMap.get("assetBigclass"));
				jqAssetAccount.setAssetClass((String)mainMap.get("assetClass"));
				jqAssetAccount.setAssetName((String)mainMap.get("assetName"));
				jqAssetAccount.setAssetCwrzrq(CommonUtils.convertStringToDate((String)mainMap.get("assetCwrzrq")));
				jqAssetAccount.setMoneyType((String)mainMap.get("moneyType"));
				
				jqAssetAccount.setMoney(NumberUtils.toDouble(mainMap.get("money").toString(),0));
				jqAssetAccount.setGetType((String)mainMap.get("getType"));
				jqAssetAccount.setGetDate(CommonUtils.convertStringToDate((String)mainMap.get("get_date")));
				jqAssetAccount.setUseStatus((String)mainMap.get("useStatus"));
				jqAssetAccount.setRemark((String)mainMap.get("remark"));
				jqAssetAccount.setUseDirect((String)mainMap.get("useDirect"));
				jqAssetAccount.setUseDept((String)mainMap.get("useDept"));
				jqAssetAccount.setManageDept((String)mainMap.get("manageDept"));
				jqAssetAccount.setUseUser((String)mainMap.get("useUser"));
				jqAssetAccount.setSl(NumberUtils.toInt(mainMap.get("sl").toString(),0));
				jqAssetAccount.setDocumentMaker((String)mainMap.get("documentMaker"));
				jqAssetAccount.setDocumentMakTime(CommonUtils.convertStringToDate((String)mainMap.get("documentMaktime")));
				jqAssetAccount.setCheckNo((String)mainMap.get("checkNo"));
				jqAssetAccount.setBelongCompany((String)mainMap.get("belongCompany"));
				jqAssetAccount.setCardStatus((String)mainMap.get("cardStatus"));
				jqAssetAccount.setTotalDepreciate(NumberUtils.toDouble(mainMap.get("totalDepreciate").toString(), 0));
				jqAssetAccount.setAlreadyDepreciateMonth(NumberUtils.toInt(mainMap.get("alreadyDepreciateMonth").toString(),0));
				jqAssetAccount.setNetValue(NumberUtils.toDouble(mainMap.get("netValue").toString(),0));
				jqAssetAccount.setBudgetProjectNo((String)mainMap.get("budgetProjectNo"));
				jqAssetAccount.setBuyOrgType((String)mainMap.get("buyOrgType"));
				jqAssetAccount.setAssetPp((String)mainMap.get("assetPp"));
				jqAssetAccount.setAssetModel((String)mainMap.get("assetModel"));
				jqAssetAccount.setSaveAddress((String)mainMap.get("saveAddress"));
				jqAssetAccount.setManufacturer((String)mainMap.get("manufacturer"));
				jqAssetAccount.setSeller((String)mainMap.get("seller"));
				jqAssetAccount.setContractNo((String)mainMap.get("contractNo"));
				jqAssetAccount.setInvoiceNo((String)mainMap.get("invoiceNo"));
				jqAssetAccount.setUseArea(NumberUtils.toDouble(mainMap.get("useArea").toString(),0));
				jqAssetAccount.setStructure((String)mainMap.get("structure"));
				jqAssetAccount.setDesignUse((String)mainMap.get("designUse"));
				jqAssetAccount.setFinancialFunds(NumberUtils.toDouble(mainMap.get("financialFunds").toString(),0));
				jqAssetAccount.setNotFinancialFunds(NumberUtils.toDouble(mainMap.get("notFinancialFunds").toString(),0));
				jqAssetAccount.setAccountNo((String)mainMap.get("accountNo"));
				jqAssetAccount.setPropertyRight((String)mainMap.get("propertyRight"));
				jqAssetAccount.setOwnerType((String)mainMap.get("ownerType"));
				jqAssetAccount.setOwnerNo((String)mainMap.get("ownerNo"));
				jqAssetAccount.setLandUseType((String)mainMap.get("landUseType"));
				jqAssetAccount.setIssueDate(CommonUtils.convertStringToDate((String)mainMap.get("issueDate")));
				jqAssetAccount.setLandUse((String)mainMap.get("landUse"));
				jqAssetAccount.setLandOrder((String)mainMap.get("landOrder"));
				jqAssetAccount.setDepreciateStatus((String)mainMap.get("depreciateStatus"));
				jqAssetAccount.setLocation((String)mainMap.get("location"));
				jqAssetAccount.setCarUse((String)mainMap.get("carUse"));
				jqAssetAccount.setCarProduce((String)mainMap.get("carProduce"));
				jqAssetAccount.setCarNo((String)mainMap.get("carNo"));
				jqAssetAccount.setCarPl((String)mainMap.get("carPl"));
				
				String relaId = (String)mainMap.get("relaId");
				
				if(relaId == null)
				{
					jqAssetAccount.setRela(false);
				}
				else
				{
					jqAssetAccount.setRela(true);
				}
		}
		
		
		return jqAssetAccount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static JqAssetAccount getJqAssetDetailById(String dbPath,String id) throws Exception
	{
		JqAssetAccount jqAssetAccount = new  JqAssetAccount();
		 
		
		String selectSql = "select * from imp_jq_asset where id='"+id+"'";
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		
		if(listMainMap.size() == 1)
		{
				Map<String, Object> mainMap = listMainMap.get(0);
				
				jqAssetAccount.setId((String)mainMap.get("id"));
			 	jqAssetAccount.setFileId((String)mainMap.get("fileId"));
				jqAssetAccount.setAssetBarcode((String)mainMap.get("assetBarcode"));
				jqAssetAccount.setAssetBigClass((String)mainMap.get("assetBigclass"));
				jqAssetAccount.setAssetClass((String)mainMap.get("assetClass"));
				jqAssetAccount.setAssetName((String)mainMap.get("assetName"));
				jqAssetAccount.setAssetCwrzrq(CommonUtils.convertStringToDate((String)mainMap.get("assetCwrzrq")));
				jqAssetAccount.setMoneyType((String)mainMap.get("moneyType"));
				
				jqAssetAccount.setMoney(NumberUtils.toDouble(""+mainMap.get("money"),0));
				jqAssetAccount.setGetType((String)mainMap.get("getType"));
				jqAssetAccount.setGetDate(CommonUtils.convertStringToDate((String)mainMap.get("get_date")));
				jqAssetAccount.setUseStatus((String)mainMap.get("useStatus"));
				jqAssetAccount.setRemark((String)mainMap.get("remark"));
				jqAssetAccount.setUseDirect((String)mainMap.get("useDirect"));
				jqAssetAccount.setUseDept((String)mainMap.get("useDept"));
				jqAssetAccount.setManageDept((String)mainMap.get("manageDept"));
				jqAssetAccount.setUseUser((String)mainMap.get("useUser"));
				jqAssetAccount.setSl(NumberUtils.toInt(""+mainMap.get("sl"),0));
				jqAssetAccount.setDocumentMaker((String)mainMap.get("documentMaker"));
				jqAssetAccount.setDocumentMakTime(CommonUtils.convertStringToDate((String)mainMap.get("documentMaktime")));
				jqAssetAccount.setCheckNo((String)mainMap.get("checkNo"));
				jqAssetAccount.setBelongCompany((String)mainMap.get("belongCompany"));
				jqAssetAccount.setCardStatus((String)mainMap.get("cardStatus"));
				jqAssetAccount.setTotalDepreciate(NumberUtils.toDouble(""+mainMap.get("totalDepreciate"), 0));
				jqAssetAccount.setAlreadyDepreciateMonth(NumberUtils.toInt(""+mainMap.get("alreadyDepreciateMonth"),0));
				jqAssetAccount.setNetValue(NumberUtils.toDouble(""+mainMap.get("netValue"),0));
				jqAssetAccount.setBudgetProjectNo((String)mainMap.get("budgetProjectNo"));
				jqAssetAccount.setBuyOrgType((String)mainMap.get("buyOrgType"));
				jqAssetAccount.setAssetPp((String)mainMap.get("assetPp"));
				jqAssetAccount.setAssetModel((String)mainMap.get("assetModel"));
				jqAssetAccount.setSaveAddress((String)mainMap.get("saveAddress"));
				jqAssetAccount.setManufacturer((String)mainMap.get("manufacturer"));
				jqAssetAccount.setSeller((String)mainMap.get("seller"));
				jqAssetAccount.setContractNo((String)mainMap.get("contractNo"));
				jqAssetAccount.setInvoiceNo((String)mainMap.get("invoiceNo"));
				jqAssetAccount.setUseArea(NumberUtils.toDouble(""+mainMap.get("useArea"),0));
				jqAssetAccount.setStructure((String)mainMap.get("structure"));
				jqAssetAccount.setDesignUse((String)mainMap.get("designUse"));
				jqAssetAccount.setFinancialFunds(NumberUtils.toDouble(""+mainMap.get("financialFunds"),0));
				jqAssetAccount.setNotFinancialFunds(NumberUtils.toDouble(""+mainMap.get("notFinancialFunds"),0));
				jqAssetAccount.setAccountNo((String)mainMap.get("accountNo"));
				jqAssetAccount.setPropertyRight((String)mainMap.get("propertyRight"));
				jqAssetAccount.setOwnerType((String)mainMap.get("ownerType"));
				jqAssetAccount.setOwnerNo((String)mainMap.get("ownerNo"));
				jqAssetAccount.setLandUseType((String)mainMap.get("landUseType"));
				jqAssetAccount.setIssueDate(CommonUtils.convertStringToDate((String)mainMap.get("issueDate")));
				jqAssetAccount.setLandUse((String)mainMap.get("landUse"));
				jqAssetAccount.setLandOrder((String)mainMap.get("landOrder"));
				jqAssetAccount.setDepreciateStatus((String)mainMap.get("depreciateStatus"));
				jqAssetAccount.setLocation((String)mainMap.get("location"));
				jqAssetAccount.setCarUse((String)mainMap.get("carUse"));
				jqAssetAccount.setCarProduce((String)mainMap.get("carProduce"));
				jqAssetAccount.setCarNo((String)mainMap.get("carNo"));
				jqAssetAccount.setCarPl((String)mainMap.get("carPl"));
				
				String relaId = (String)mainMap.get("relaId");
				
				if(relaId == null)
				{
					jqAssetAccount.setRela(false);
				}
				else
				{
					jqAssetAccount.setRela(true);
				}
		}
		
		
		return jqAssetAccount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static int getJqAccountCount(String dbPath,String fileId,JqAccountQueryInfo jqAccountQueryInfo) throws Exception
	{
		
		
		int count = 0;
		
		String selectSql = null;
		
		
		String whereSql =" where fileId='"+fileId+"' ";
		
		if(jqAccountQueryInfo != null)
		{
			if(jqAccountQueryInfo.getAssetBarcode() != null && jqAccountQueryInfo.getAssetBarcode().length()>0)
			{
				whereSql+=" and assetBarcode like '%"+jqAccountQueryInfo.getAssetBarcode()+"%'";
			}
			if(jqAccountQueryInfo.getAssetName() != null && jqAccountQueryInfo.getAssetName().length()>0)
			{
				whereSql+=" and assetName like '%"+jqAccountQueryInfo.getAssetName()+"%'";
			}
			if(jqAccountQueryInfo.getUseDept() != null && jqAccountQueryInfo.getUseDept().length()>0)
			{
				whereSql+=" and useDept like '%"+jqAccountQueryInfo.getUseDept()+"%'";
			}
			if(jqAccountQueryInfo.getSaveAddress() != null && jqAccountQueryInfo.getSaveAddress().length()>0)
			{
				whereSql+=" and saveAddress like '%"+jqAccountQueryInfo.getSaveAddress()+"%'";
			}
			if(jqAccountQueryInfo.getAssetClass() != null && jqAccountQueryInfo.getAssetClass().length()>0)
			{
				whereSql+=" and assetClass like '%"+jqAccountQueryInfo.getAssetClass()+"%'";
			}
			if(jqAccountQueryInfo.getAssetBigClass() != null && jqAccountQueryInfo.getAssetBigClass().length()>0)
			{
				whereSql+=" and assetBigclass like '%"+jqAccountQueryInfo.getAssetBigClass()+"%'";
			}
		}
		
		
		
		
		selectSql = "select count(*) from imp_jq_asset "+whereSql;
		
		
		SqliteHelper hAsset = new SqliteHelper(dbPath);
		
		List<Map<String, Object>> listMainMap = hAsset.executeQuery(selectSql);
		
		if(listMainMap.size()==1)
		{
			count = Integer.parseInt(listMainMap.get(0).get("count(*)").toString());
		}
		
		
		return count;
	}
	
	
	
	public static void updateJqAsset(String dbPath,JqAssetAccount jqAssetAccount) throws Exception
	{
				
			String updateSql = "update imp_jq_asset set ";
			if(jqAssetAccount.getAssetBarcode()!=null)
			{
				updateSql+=" assetBarcode='"+jqAssetAccount.getAssetBarcode()+"',";
			}
			if(jqAssetAccount.getAssetBigClass()!=null)
			{
				updateSql+=" assetBigclass='"+jqAssetAccount.getAssetBigClass()+"',";
			}
			if(jqAssetAccount.getAssetClass()!=null)
			{
				updateSql+=" assetClass='"+jqAssetAccount.getAssetClass()+"',";
			}
			if(jqAssetAccount.getAssetName()!=null)
			{
				updateSql+=" assetName='"+jqAssetAccount.getAssetName()+"',";
			}
			if(jqAssetAccount.getAssetCwrzrq()!=null)
			{
				updateSql+=" assetCwrzrq='"+jqAssetAccount.getAssetCwrzrq()+"',";
			}
			if(jqAssetAccount.getMoneyType()!=null)
			{
				updateSql+=" moneyType='"+jqAssetAccount.getMoneyType()+"',";
			}
			if(jqAssetAccount.getMoney()!=null)
			{
				updateSql+=" money="+jqAssetAccount.getMoney()+",";
			}
			if(jqAssetAccount.getGetType()!=null)
			{
				updateSql+=" getType='"+jqAssetAccount.getGetType()+"',";
			}
			if(jqAssetAccount.getGetDate()!=null)
			{
				updateSql+=" get_date='"+jqAssetAccount.getGetDate()+"',";
			}
			if(jqAssetAccount.getUseStatus()!=null)
			{
				updateSql+=" useStatus='"+jqAssetAccount.getUseStatus()+"',";
			}
			if(jqAssetAccount.getRemark()!=null)
			{
				updateSql+=" remark='"+jqAssetAccount.getRemark()+"',";
			}
			if(jqAssetAccount.getUseDirect()!=null)
			{
				updateSql+=" useDirect='"+jqAssetAccount.getUseDirect()+"',";
			}
			if(jqAssetAccount.getUseDept()!=null)
			{
				updateSql+=" useDept='"+jqAssetAccount.getUseDept()+"',";
			}
			if(jqAssetAccount.getManageDept()!=null)
			{
				updateSql+=" manageDept='"+jqAssetAccount.getManageDept()+"',";
			}
			if(jqAssetAccount.getUseUser()!=null)
			{
				updateSql+=" useUser='"+jqAssetAccount.getUseUser()+"',";
			}
			if(jqAssetAccount.getSl()!=null)
			{
				updateSql+=" sl="+jqAssetAccount.getSl()+",";
			}
			if(jqAssetAccount.getDocumentMaker()!=null)
			{
				updateSql+=" documentMaker='"+jqAssetAccount.getDocumentMaker()+"',";
			}
			if(jqAssetAccount.getDocumentMakTime()!=null)
			{
				updateSql+=" documentMaktime='"+jqAssetAccount.getDocumentMakTime()+"',";
			}
			if(jqAssetAccount.getCheckNo()!=null)
			{
				updateSql+=" checkNo='"+jqAssetAccount.getCheckNo()+"',";
			}
			if(jqAssetAccount.getBelongCompany()!=null)
			{
				updateSql+=" belongCompany='"+jqAssetAccount.getBelongCompany()+"',";
			}
			if(jqAssetAccount.getCardStatus()!=null)
			{
				updateSql+=" cardStatus='"+jqAssetAccount.getCardStatus()+"',";
			}
			if(jqAssetAccount.getTotalDepreciate()!=null)
			{
				updateSql+=" totalDepreciate="+jqAssetAccount.getTotalDepreciate()+",";
			}
			if(jqAssetAccount.getAlreadyDepreciateMonth()!=null)
			{
				updateSql+=" alreadyDepreciateMonth="+jqAssetAccount.getAlreadyDepreciateMonth()+",";
			}
			if(jqAssetAccount.getNetValue()!=null)
			{
				updateSql+=" netValue="+jqAssetAccount.getNetValue()+",";
			}
			if(jqAssetAccount.getBudgetProjectNo()!=null)
			{
				updateSql+=" budgetProjectNo='"+jqAssetAccount.getBudgetProjectNo()+"',";
			}
			if(jqAssetAccount.getBuyOrgType()!=null)
			{
				updateSql+=" buyOrgType='"+jqAssetAccount.getBuyOrgType()+"',";
			}
			if(jqAssetAccount.getAssetPp()!=null)
			{
				updateSql+=" assetPp='"+jqAssetAccount.getAssetPp()+"',";
			}
			if(jqAssetAccount.getAssetModel()!=null)
			{
				updateSql+=" assetModel='"+jqAssetAccount.getAssetModel()+"',";
			}
			if(jqAssetAccount.getSaveAddress()!=null)
			{
				updateSql+=" saveAddress='"+jqAssetAccount.getSaveAddress()+"',";
			}
			if(jqAssetAccount.getManufacturer()!=null)
			{
				updateSql+=" manufacturer='"+jqAssetAccount.getManufacturer()+"',";
			}
			if(jqAssetAccount.getSeller()!=null)
			{
				updateSql+=" seller='"+jqAssetAccount.getSeller()+"',";
			}
			if(jqAssetAccount.getContractNo()!=null)
			{
				updateSql+=" contractNo='"+jqAssetAccount.getContractNo()+"',";
			}
			if(jqAssetAccount.getInvoiceNo()!=null)
			{
				updateSql+=" invoiceNo='"+jqAssetAccount.getInvoiceNo()+"',";
			}
			if(jqAssetAccount.getUseArea()!=null)
			{
				updateSql+=" useArea="+jqAssetAccount.getUseArea()+",";
			}
			if(jqAssetAccount.getStructure()!=null)
			{
				updateSql+=" structure='"+jqAssetAccount.getStructure()+"',";
			}
			if(jqAssetAccount.getDesignUse()!=null)
			{
				updateSql+=" designUse='"+jqAssetAccount.getDesignUse()+"',";
			}
			if(jqAssetAccount.getFinancialFunds()!=null)
			{
				updateSql+=" financialFunds="+jqAssetAccount.getFinancialFunds()+",";
			}
			if(jqAssetAccount.getNotFinancialFunds()!=null)
			{
				updateSql+=" notFinancialFunds="+jqAssetAccount.getNotFinancialFunds()+",";
			}
			if(jqAssetAccount.getAccountNo()!=null)
			{
				updateSql+=" accountNo='"+jqAssetAccount.getAccountNo()+"',";
			}
			if(jqAssetAccount.getPropertyRight()!=null)
			{
				updateSql+=" propertyRight='"+jqAssetAccount.getPropertyRight()+"',";
			}
			if(jqAssetAccount.getOwnerType()!=null)
			{
				updateSql+=" ownerType='"+jqAssetAccount.getOwnerType()+"',";
			}
			if(jqAssetAccount.getOwnerNo()!=null)
			{
				updateSql+=" ownerNo='"+jqAssetAccount.getOwnerNo()+"',";
			}
			if(jqAssetAccount.getLandUseType()!=null)
			{
				updateSql+=" landUseType='"+jqAssetAccount.getLandUseType()+"',";
			}
			if(jqAssetAccount.getIssueDate()!=null)
			{
				updateSql+=" issueDate='"+jqAssetAccount.getIssueDate()+"',";
			}
			if(jqAssetAccount.getLandUse()!=null)
			{
				updateSql+=" landUse='"+jqAssetAccount.getLandUse()+"',";
			}
			if(jqAssetAccount.getLandOrder()!=null)
			{
				updateSql+=" landOrder='"+jqAssetAccount.getLandOrder()+"',";
			}
			if(jqAssetAccount.getDepreciateStatus()!=null)
			{
				updateSql+=" depreciateStatus='"+jqAssetAccount.getDepreciateStatus()+"',";
			}
			if(jqAssetAccount.getLocation()!=null)
			{
				updateSql+=" location='"+jqAssetAccount.getLocation()+"',";
			}
			if(jqAssetAccount.getCarUse()!=null)
			{
				updateSql+=" carUse='"+jqAssetAccount.getCarUse()+"',";
			}
			if(jqAssetAccount.getCarProduce()!=null)
			{
				updateSql+=" carProduce="+jqAssetAccount.getCarProduce()+"',";
			}
			if(jqAssetAccount.getCarNo()!=null)
			{
				updateSql+=" carNo='"+jqAssetAccount.getCarNo()+"',";
			}
			if(jqAssetAccount.getCarPl()!=null)
			{
				updateSql+=" carPl='"+jqAssetAccount.getCarPl()+"',";
			}
			updateSql = updateSql.substring(0,updateSql.length()-1);
			
			updateSql+=" where id='"+jqAssetAccount.getId()+"'";
			
			
			SqliteHelper hAsset = new SqliteHelper(dbPath);
			
			hAsset.executeUpdate(updateSql);
			
			hAsset.destroyed();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void addJqAssetAccount(List<JqAssetAccount> listJqAssetAccount,List<String> listSql,List<Object[]> listObj) throws Exception
	{
			for(int i=0;i<listJqAssetAccount.size();i++)
			{
				
				JqAssetAccount jqAssetAccount = listJqAssetAccount.get(i);
									
				String insertSql = "insert into imp_jq_asset(id,fileId,assetBarcode,assetBigclass,assetClass,assetName,assetCwrzrq,moneyType,money,"
						+ "getType,get_date,useStatus,remark,useDirect,useDept,manageDept,useUser,sl,documentMaker,documentMaktime,checkNo,belongCompany,"
						+ "cardStatus,totalDepreciate,alreadyDepreciateMonth,netValue,budgetProjectNo,buyOrgType,assetPp,assetModel,saveAddress,manufacturer,"
						+ "seller,contractNo,invoiceNo,useArea,structure,designUse,financialFunds,notFinancialFunds,accountNo,propertyRight,ownerType,ownerNo,"
						+ "landUseType,issueDate,landUse,landOrder,depreciateStatus,location,carUse,carProduce,carNo,carPl) values"
						+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				Object[] obj = {jqAssetAccount.getId(),jqAssetAccount.getFileId(),jqAssetAccount.getAssetBarcode(),jqAssetAccount.getAssetBigClass(),jqAssetAccount.getAssetClass(),jqAssetAccount.getAssetName(),
						CommonUtils.convertDateToString(jqAssetAccount.getAssetCwrzrq()),jqAssetAccount.getMoneyType(),jqAssetAccount.getMoney(),jqAssetAccount.getGetType(),CommonUtils.convertDateToString(jqAssetAccount.getGetDate()),jqAssetAccount.getUseStatus(),jqAssetAccount.getRemark(),
						jqAssetAccount.getUseDirect(),jqAssetAccount.getUseDept(),jqAssetAccount.getManageDept(),jqAssetAccount.getUseUser(),jqAssetAccount.getSl(),jqAssetAccount.getDocumentMaker(),CommonUtils.convertDateToString(jqAssetAccount.getDocumentMakTime()),jqAssetAccount.getCheckNo(),
						jqAssetAccount.getBelongCompany(),jqAssetAccount.getCardStatus(),jqAssetAccount.getTotalDepreciate(),jqAssetAccount.getAlreadyDepreciateMonth(),jqAssetAccount.getNetValue(),jqAssetAccount.getBudgetProjectNo(),jqAssetAccount.getBuyOrgType(),
						jqAssetAccount.getAssetPp(),jqAssetAccount.getAssetModel(),jqAssetAccount.getSaveAddress(),jqAssetAccount.getManufacturer(),jqAssetAccount.getSeller(),jqAssetAccount.getContractNo(),jqAssetAccount.getInvoiceNo(),jqAssetAccount.getUseArea(),
						jqAssetAccount.getStructure(),jqAssetAccount.getDesignUse(),jqAssetAccount.getFinancialFunds(),jqAssetAccount.getNotFinancialFunds(),jqAssetAccount.getAccountNo(),jqAssetAccount.getPropertyRight(),jqAssetAccount.getOwnerType(),jqAssetAccount.getOwnerNo(),
						jqAssetAccount.getLandUseType(),CommonUtils.convertDateToString(jqAssetAccount.getIssueDate()),jqAssetAccount.getLandUse(),jqAssetAccount.getLandOrder(),jqAssetAccount.getDepreciateStatus(),jqAssetAccount.getLocation(),jqAssetAccount.getCarUse(),jqAssetAccount.getCarProduce(),
						jqAssetAccount.getCarNo(),jqAssetAccount.getCarPl()};
			
				listSql.add(insertSql);
				listObj.add(obj);
			}
	}
}
