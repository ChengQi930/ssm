package cn.bizowner.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.DownAssetMgrParam;
import cn.bizowner.model.DownFileInfo;
import cn.bizowner.model.JqAccountQueryInfo;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.model.ProjectFileInfo;
import cn.bizowner.model.SwAccountQueryInfo;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.UpdateAssetMgrThreadParam;
import cn.bizowner.service.AssetAccountService;
import cn.bizowner.service.CommonService;
import cn.bizowner.sqlite.CwAccountUtil;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.ProjectFileUtil;
import cn.bizowner.sqlite.SwAccountUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.NumberUtil;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/AssetMgr")
public class AssetMgrController {
  
	@Autowired
	private AssetAccountService assetAccountService;
	 
	@Autowired
	private CommonService commonService;
	
	
	@RequestMapping("/Test")
	public ModelAndView test()
	{
		
			ModelAndView result = new ModelAndView("file");
			return result;
	}
	
	
	
	@RequestMapping("/GetFileContent")  
    public @ResponseBody String GetFileContent(MultipartFile accountFile,HttpServletRequest request,HttpServletResponse response)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
			
		JSONObject jo = null;
		
		String tempFileName = null;
		
		try
		{
			String fileName = accountFile.getOriginalFilename(); 
			
			tempFileName = Utils.uploadPath+CommonUtils.getUUID()+"_"+fileName;		
	    	
	        File dir = new File(Utils.uploadPath);          
	        if(!dir.exists()){  
	            dir.mkdirs();  
	        }  
	        //MultipartFile自带的解析方法  
	        
	        
	        File file = new File(tempFileName);
	        
	        accountFile.transferTo(file);  
	                
	        
			if(tempFileName.endsWith(".xls"))
			{
				jo = ExcelUtils.newExcel2json(tempFileName);
			}
			else if(tempFileName.endsWith(".xlsx"))
			{
				jo = ExcelUtils.newExcelX2json(tempFileName);
			}
			else
			{
				throw new Exception("error file type");
			}
	        
	        
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
			ex.printStackTrace();
		}	
		finally
		{
				File file = new File(tempFileName);
				if(file.exists())
				{
						file.delete();
				}
		}
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", jo);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	@RequestMapping("/UpdateAssetAccount")  
    public @ResponseBody String UpdateAssetAccount(MultipartFile accountFile,@RequestParam(value="accountType", required=false,defaultValue="1")Integer accountType,@RequestParam(value="updateType", required=false,defaultValue="1")Integer updateType,HttpServletRequest request)
	{  
		  	
			String result =null;
			
			boolean flag = true;
			
			String errMes = "";
			
			Dto outDto = new BaseDto();
			
			final String proUuid =  CommonUtils.getUUID();
			
			try
			{
				
				String filePath = "";
				
				String oriFileName = accountFile.getOriginalFilename(); 
				
				String tempPath = Utils.filePath;
				
				String fileType = oriFileName.substring(oriFileName.lastIndexOf("."),oriFileName.length());
								
				String newFileName = CommonUtils.getUUID()+fileType;
				
				filePath = tempPath+newFileName;
				
				
				File dir = new File(filePath);          
		        
		        //MultipartFile自带的解析方法  
				accountFile.transferTo(dir);  
				
				
				
				
				
				
				String projectId = request.getSession().getAttribute("proId").toString();
								
				UpdateAssetAccountThread th = new  UpdateAssetAccountThread();
				
				UpdateAssetMgrThreadParam param = new UpdateAssetMgrThreadParam();
				
				
				param.setProUuid(proUuid);
				param.setProjectId(projectId);
				param.setAccountType(accountType);
				param.setUpdateType(updateType);
				param.setFilePath(filePath);
				param.setFileName(oriFileName);
				
				th.setUpdateAssetMgrThreadParam(param);
				
				Thread thread = new Thread(th);
				thread.start();
				
			
			}
			catch(Exception ex)
			{
				flag = false;
				errMes = ex.getMessage();
			}
			
			
			outDto.put("flag", flag);
			if(flag == true)
			{
				outDto.put("result", proUuid);
			}
			else
			{
				outDto.put("errmsg", errMes);
			}
			
			if(outDto != null)
			{
				result = JSONHelper.encodeObject2JsonWithNull(outDto);
			}
			
			return result;
    }
	
		
		
	
	
	
	public class UpdateAssetAccountThread implements Runnable
	{

		private UpdateAssetMgrThreadParam updateAssetMgrThreadParam;
		
		public UpdateAssetMgrThreadParam getUpdateAssetMgrThreadParam() {
			return updateAssetMgrThreadParam;
		}


		public void setUpdateAssetMgrThreadParam(
				UpdateAssetMgrThreadParam updateAssetMgrThreadParam) {
			this.updateAssetMgrThreadParam = updateAssetMgrThreadParam;
		}


		@Override
		public void run() {
			
			
			SingleList singlelist = SingleList.getInstance();
			
			String proUuid = updateAssetMgrThreadParam.getProUuid();
			
			
			boolean updateFlag = true;
			
			String filePath = null;
			
			try
			{
				
				singlelist.addTail(proUuid,"解析文件内容..");
				
				
				
				filePath = updateAssetMgrThreadParam.getFilePath();
				
				String fileName = updateAssetMgrThreadParam.getFileName();
		        
				String fileId = CommonUtils.getUUID();
		        
		        ProjectFileInfo projectFileInfo = new ProjectFileInfo();
		        projectFileInfo.setId(fileId);
		        projectFileInfo.setFileName(fileName);
		        projectFileInfo.setFilePath(filePath);
		        projectFileInfo.setProjectId(updateAssetMgrThreadParam.getProjectId());
		        projectFileInfo.setType(updateAssetMgrThreadParam.getAccountType());
		        projectFileInfo.setUpdateTime(CommonUtils.getCurrentTime());
		        String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		        
		        
		        
		       
		        
		        
		        Integer accountType = updateAssetMgrThreadParam.getAccountType();
		        Integer updateType = updateAssetMgrThreadParam.getUpdateType();
			
				JSONObject jo = null;
				
				if(fileType.equals(".xls"))
				{
					
					
					jo = ExcelUtils.newExcel2json(filePath);
					
					/*if(accountType == 1)
					{
						jo = ExcelUtils.newExcel2json_cx(filePath);
					}
					else
					{
						jo = ExcelUtils.newExcel2json(filePath);
					}*/
					
				}
				else if(fileType.equals(".xlsx"))
				{
					jo = ExcelUtils.newExcelX2json(filePath);
				}
				
				JSONArray jaContent = jo.getJSONArray("content");
				
				JSONObject jHead = jo.getJSONObject("head");
				
				
				
								
				
				
				//1表示财务账,2表示久其账,3表示实物账
				if(accountType == 1)
				{
					 	String year = CommonUtils.getCurrentTime("yyyy");
				        int index = fileName.indexOf("年");
				        if(index-4>0)
				        {
				        	year = fileName.substring(index-4,index);
				        }
					
					
						String monthKey = CommonUtils.getKeyFromJo(jHead,"月");
						String dayKey = CommonUtils.getKeyFromJo(jHead,"日");
						String accountNoKey = CommonUtils.getKeyFromJo(jHead,"凭证号码");
						String remarkKey = CommonUtils.getKeyFromJo(jHead,"摘要");
						String debitKey = CommonUtils.getKeyFromJo(jHead,"借方");
						String lenderKey = CommonUtils.getKeyFromJo(jHead,"贷方");
						String directionKey = CommonUtils.getKeyFromJo(jHead,"方向");
						String balanceKey = CommonUtils.getKeyFromJo(jHead,"余额");
						
						List<CwAssetAccount> listCwAssetAccount = new ArrayList<CwAssetAccount>();
						
						for(Object obj : jaContent)
						{
							 	jo = (JSONObject) JSONObject.toJSON(obj);
							 	String uuid =  CommonUtils.getUUID();
							 	
							 	CwAssetAccount cwAssetAccount = new CwAssetAccount();
							 	cwAssetAccount.setId(uuid);
							 	cwAssetAccount.setFileId(fileId);
							 	cwAssetAccount.setYear(year);
							 	cwAssetAccount.setMonth(jo.getString(monthKey));
							 	cwAssetAccount.setDay(jo.getString(dayKey));
							 	cwAssetAccount.setAccountNo(jo.getString(accountNoKey));
							 	cwAssetAccount.setRemark(jo.getString(remarkKey));
							 	cwAssetAccount.setDebit(NumberUtil.NumToMoney(jo.getString(debitKey)));
							 	cwAssetAccount.setLender(NumberUtil.NumToMoney(jo.getString(lenderKey)));
							 	cwAssetAccount.setDirection(jo.getString(directionKey));
							 	cwAssetAccount.setBalance(NumberUtil.NumToMoney(jo.getString(balanceKey)));
 							 	listCwAssetAccount.add(cwAssetAccount);
							 	
						}
						
						
						
						assetAccountService.UpdateCwAssetAccount(projectFileInfo, listCwAssetAccount);
				}
				
				else if(accountType == 2)
				{			
					
						String assetBarcodeKey = CommonUtils.getKeyFromJo(jHead,"资产编号");
						String assetClassKey = CommonUtils.getKeyFromJo(jHead,"资产分类");
						String assetNameKey = CommonUtils.getKeyFromJo(jHead,"资产名称");
						String assetCwrzrqKey = CommonUtils.getKeyFromJo(jHead,"财务入账日期");
						String moneyTypeKey = CommonUtils.getKeyFromJo(jHead,"价值类型");
						String moneyKey = CommonUtils.getKeyFromJo(jHead,"价值");
						String getTypeKey = CommonUtils.getKeyFromJo(jHead,"取得方式");
						String getDateKey = CommonUtils.getKeyFromJo(jHead,"取得日期");
						String useStatusKey = CommonUtils.getKeyFromJo(jHead,"使用状况");
						String remarkKey = CommonUtils.getKeyFromJo(jHead,"备注");
						String useDirectKey = CommonUtils.getKeyFromJo(jHead,"使用方向");
						String useDeptKey = CommonUtils.getKeyFromJo(jHead,"使用部门");
						String manageDeptKey = CommonUtils.getKeyFromJo(jHead,"管理部门");
						
						String useUserKey = CommonUtils.getKeyFromJo(jHead,"使用人");
						String slKey = CommonUtils.getKeyFromJo(jHead,"数量");
						String documentMakerKey = CommonUtils.getKeyFromJo(jHead,"制单人");
						String documentMakTimeKey = CommonUtils.getKeyFromJo(jHead,"制单时间");
						
						String checkNoKey = CommonUtils.getKeyFromJo(jHead,"清查编号");
						String belongCompanyKey = CommonUtils.getKeyFromJo(jHead,"所属单位");
						String cardStatusKey = CommonUtils.getKeyFromJo(jHead,"卡片状态");
						String totalDepreciateKey = CommonUtils.getKeyFromJo(jHead,"累计折旧");
						String alreadyDepreciateMonthKey = CommonUtils.getKeyFromJo(jHead,"已提折旧月数");
						String netValueKey = CommonUtils.getKeyFromJo(jHead,"净值");
						String budgetProjectNoKey = CommonUtils.getKeyFromJo(jHead,"预算项目编号");
						String buyOrgTypeKey = CommonUtils.getKeyFromJo(jHead,"采购组织形式");
						
						String assetPpKey = CommonUtils.getKeyFromJo(jHead,"品牌");
						String assetModelKey = CommonUtils.getKeyFromJo(jHead,"规格型号");
						String saveAddressKey = CommonUtils.getKeyFromJo(jHead,"存放地点");
						String manufacturerKey = CommonUtils.getKeyFromJo(jHead,"生产厂家");
						String sellerKey = CommonUtils.getKeyFromJo(jHead,"销售商");
						String contractNoKey = CommonUtils.getKeyFromJo(jHead,"合同编号");
						String invoiceNoKey = CommonUtils.getKeyFromJo(jHead,"发票号");
						String useAreaKey = CommonUtils.getKeyFromJo(jHead,"使用权面积/建筑面积");
						
						String structureKey = CommonUtils.getKeyFromJo(jHead,"建筑结构");
						String designUseKey = CommonUtils.getKeyFromJo(jHead,"设计用途");
						String financialFundsKey = CommonUtils.getKeyFromJo(jHead,"财政性资金");
						String notFinancialFundsKey = CommonUtils.getKeyFromJo(jHead,"非财政性资金");
						String accountNoKey = CommonUtils.getKeyFromJo(jHead,"会计凭证号");
						String propertyRightKey = CommonUtils.getKeyFromJo(jHead,"产权形式");
						String ownerTypeKey = CommonUtils.getKeyFromJo(jHead,"权属性质");
						
						String ownerNoKey = CommonUtils.getKeyFromJo(jHead,"权属证号");
						String landUseTypeKey = CommonUtils.getKeyFromJo(jHead,"土地使用权类型");
						String issueDateKey = CommonUtils.getKeyFromJo(jHead,"发证日期");
						String landUseKey = CommonUtils.getKeyFromJo(jHead,"地类(用途)");
						String landOrderKey = CommonUtils.getKeyFromJo(jHead,"土地级次");
						String depreciateStatusKey = CommonUtils.getKeyFromJo(jHead,"折旧状态");
						String locationKey = CommonUtils.getKeyFromJo(jHead,"坐落位置");
						String carUseKey = CommonUtils.getKeyFromJo(jHead,"车辆用途");
						String carProduceKey = CommonUtils.getKeyFromJo(jHead,"车辆产地");
						String carNoKey = CommonUtils.getKeyFromJo(jHead,"车牌号");
						String carPlKey = CommonUtils.getKeyFromJo(jHead,"排气量");
						
						
						
						List<JqAssetAccount> listJqAssetAccount = new ArrayList<JqAssetAccount>();
						
						for(Object obj : jaContent)
						{
							 	jo = (JSONObject) JSONObject.toJSON(obj);
							 	String uuid =  CommonUtils.getUUID();
							 	
							 	JqAssetAccount assetAccount = new JqAssetAccount();
							 	
							 	assetAccount.setId(uuid);
							 	assetAccount.setFileId(fileId);
							 	assetAccount.setAssetBarcode(jo.getString(assetBarcodeKey));
							 	String assetClass = jo.getString(assetClassKey);
							 	assetAccount.setAssetClass(assetClass);
							 	assetAccount.setAssetBigClass(commonService.getAssetBigClassByAssetClass(assetClass));
							 	assetAccount.setAssetName(jo.getString(assetNameKey));
							 	assetAccount.setAssetCwrzrq(CommonUtils.convertStringToDate(jo.getString(assetCwrzrqKey)));
							 	assetAccount.setMoneyType(jo.getString(moneyTypeKey));
							 	assetAccount.setMoney(NumberUtils.toDouble(jo.getString(moneyKey),0));
							 	assetAccount.setGetType(jo.getString(getTypeKey));
							 	assetAccount.setGetDate(CommonUtils.convertStringToDate(jo.getString(getDateKey)));
							 	assetAccount.setUseStatus(jo.getString(useStatusKey));
							 	assetAccount.setRemark(jo.getString(remarkKey));
							 	assetAccount.setUseDirect(jo.getString(useDirectKey));
							 	String useDept = jo.getString(useDeptKey);
							 	assetAccount.setUseDept(useDept);
							 	assetAccount.setManageDept(jo.getString(manageDeptKey));
							 	assetAccount.setUseUser(jo.getString(useUserKey));
							 	assetAccount.setSl(NumberUtils.toInt(jo.getString(slKey),0));
							 	assetAccount.setDocumentMaker(jo.getString(documentMakerKey));
							 	assetAccount.setDocumentMakTime(CommonUtils.convertStringToDate(jo.getString(documentMakTimeKey)));
							 	assetAccount.setCheckNo(jo.getString(checkNoKey));
							 	assetAccount.setBelongCompany(jo.getString(belongCompanyKey));
							 	assetAccount.setCardStatus(jo.getString(cardStatusKey));
							 	assetAccount.setTotalDepreciate(NumberUtils.toDouble(jo.getString(totalDepreciateKey),0));
							 	assetAccount.setAlreadyDepreciateMonth(NumberUtils.toInt(jo.getString(alreadyDepreciateMonthKey),0));
							 	assetAccount.setNetValue(NumberUtils.toDouble(jo.getString(netValueKey),0));
							 	assetAccount.setBudgetProjectNo(jo.getString(budgetProjectNoKey));
							 	assetAccount.setBuyOrgType(jo.getString(buyOrgTypeKey));
							 	assetAccount.setAssetPp(jo.getString(assetPpKey));
							 	assetAccount.setAssetModel(jo.getString(assetModelKey));
							 	assetAccount.setSaveAddress(jo.getString(saveAddressKey));
							 	assetAccount.setManufacturer(jo.getString(manufacturerKey));
							 	assetAccount.setSeller(jo.getString(sellerKey));
							 	assetAccount.setContractNo(jo.getString(contractNoKey));
							 	assetAccount.setInvoiceNo(jo.getString(invoiceNoKey));
							 	assetAccount.setUseArea(NumberUtils.toDouble(jo.getString(useAreaKey),0));
							 	assetAccount.setStructure(jo.getString(structureKey));
							 	assetAccount.setDesignUse(jo.getString(designUseKey));
							 	assetAccount.setFinancialFunds(NumberUtils.toDouble(jo.getString(financialFundsKey),0));
							 	assetAccount.setNotFinancialFunds(NumberUtils.toDouble(jo.getString(notFinancialFundsKey),0));
							 	assetAccount.setAccountNo(jo.getString(accountNoKey));
							 	assetAccount.setPropertyRight(jo.getString(propertyRightKey));
							 	assetAccount.setOwnerType(jo.getString(ownerTypeKey));
							 	assetAccount.setOwnerNo(jo.getString(ownerNoKey));
							 	assetAccount.setLandUseType(jo.getString(landUseTypeKey));
							 	assetAccount.setIssueDate(CommonUtils.convertStringToDate(jo.getString(issueDateKey)));
							 	assetAccount.setLandUse(jo.getString(landUseKey));
							 	assetAccount.setLandOrder(jo.getString(landOrderKey));
							 	assetAccount.setDepreciateStatus(jo.getString(depreciateStatusKey));
							 	assetAccount.setLocation(jo.getString(locationKey));
							 	assetAccount.setCarUse(jo.getString(carUseKey));
							 	assetAccount.setCarProduce(jo.getString(carProduceKey));
							 	assetAccount.setCarNo(jo.getString(carNoKey));
							 	assetAccount.setCarPl(jo.getString(carPlKey));
 							 	
							 	listJqAssetAccount.add(assetAccount);
							 	
						}
						
						
						
						assetAccountService.UpdateJqAssetAccount(projectFileInfo, listJqAssetAccount);
				}
				else
				{
						
					
						String assetBarcodeKey = CommonUtils.getKeyFromJo(jHead,"资产编号");
						String assetNameKey = CommonUtils.getKeyFromJo(jHead,"资产名称");
						String assetClassKey = CommonUtils.getKeyFromJo(jHead,"资产分类");
						String slKey = CommonUtils.getKeyFromJo(jHead,"数量");
						String getDateKey = CommonUtils.getKeyFromJo(jHead,"取得日期");
						String assetPpKey = CommonUtils.getKeyFromJo(jHead,"品牌");
						String assetModelKey = CommonUtils.getKeyFromJo(jHead,"规格型号");
						String useDeptKey = CommonUtils.getKeyFromJo(jHead,"使用部门");
						String useUserKey = CommonUtils.getKeyFromJo(jHead,"使用人");
						String saveAddressKey = CommonUtils.getKeyFromJo(jHead,"存放地点");
						String moneyKey = CommonUtils.getKeyFromJo(jHead,"价值");
						String remarkKey = CommonUtils.getKeyFromJo(jHead,"备注");
						
						List<SwAssetAccount> listSwAssetAccount = new ArrayList<SwAssetAccount>();
						
						for(Object obj : jaContent)
						{
							 	jo = (JSONObject) JSONObject.toJSON(obj);
							 	String uuid =  CommonUtils.getUUID();
							 	
							 	SwAssetAccount swAssetAccount = new SwAssetAccount();
							 	
							 	swAssetAccount.setId(uuid);
							 	swAssetAccount.setFileId(fileId);
							 	swAssetAccount.setAssetBarcode(jo.getString(assetBarcodeKey));
							 	swAssetAccount.setAssetName(jo.getString(assetNameKey));
							 	swAssetAccount.setAssetClass(jo.getString(assetClassKey));
							 	swAssetAccount.setGetDate(CommonUtils.convertStringToDate(jo.getString(getDateKey)));
							 	swAssetAccount.setUseDept(jo.getString(useDeptKey));
							 	swAssetAccount.setUseUser(jo.getString(useUserKey));
							 	swAssetAccount.setSl(NumberUtils.toInt(jo.getString(slKey),0));
							 	swAssetAccount.setAssetPp(jo.getString(assetPpKey));
							 	swAssetAccount.setAssetModel(jo.getString(assetModelKey));
							 	swAssetAccount.setSaveAddress(jo.getString(saveAddressKey));
							 	swAssetAccount.setMoney(NumberUtils.toDouble(jo.getString(moneyKey),0));
							 	swAssetAccount.setRemark(jo.getString(remarkKey));
							 	listSwAssetAccount.add(swAssetAccount);
							 	
						}
						
						
						
						assetAccountService.UpdateSwAssetAccount(projectFileInfo, listSwAssetAccount,updateType);
					
				}
				
			}
			catch(Exception ex)
			{
					String s = ex.getMessage();
					updateFlag = false;
					ex.printStackTrace();
					
					
					File file = new File(filePath);
					if(file.exists())	file.delete();
					
					//System.out.println(ex.printStackTrace(););
			}
			
			finally
			{
				ProgressInfo progressInfo = new ProgressInfo();
				progressInfo.setProgress("更新完成");
				progressInfo.setFinishFlag(true);
				progressInfo.setUpdateFlag(updateFlag);
				progressInfo.setFinishTime(CommonUtils.getCurrentTime());	
				singlelist.change(proUuid, progressInfo);
			}
		}
	}
	
	
	
	
	
	
	
	
	@RequestMapping("/GetJqAccountFileList")  
    public @ResponseBody String GetJqAccountFileList(HttpServletRequest request,HttpServletResponse response)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		List<ProjectFileInfo> listProjectFileInfo=null;
		
		try
		{
			
			//String projectId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			
			String proId = request.getSession().getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";
			
			listProjectFileInfo = ProjectFileUtil.getJqAccountFileList(dbPath, 2);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", listProjectFileInfo);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping("/GetJqAccountAsset")  
    public @ResponseBody String GetJqAccountAsset(HttpServletRequest request,String id,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = null;
		
		try
		{
			
			//String projectId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			
			String proId = request.getSession().getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";			
			
			String userId = "001";
			
			if(id==null)
			{
					id = ProjectFileUtil.getLastestFileId(dbPath, 2);
			}
			
			if(type==1)
			{
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("jqRelaCwFileId", id);
			}
			else
			{
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("jqRelaSwFileId", id);
			}
			
			
			
			
			dto = assetAccountService.getJqAccountAsset(dbPath, id, type, userId);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetSwAccountList",method=RequestMethod.GET)  
    public @ResponseBody String GetSwAccountList(HttpServletRequest request,@RequestParam(value="start", required=false,defaultValue="0")Integer start,@RequestParam(value="limit", required=false,defaultValue="25")Integer limit,SwAccountQueryInfo swAccountQueryInfo)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = null;
		
		try
		{
			
			String proId = request.getSession().getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";			
			
			dto = assetAccountService.getSwAccountList(dbPath, start, limit, swAccountQueryInfo);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping("/GetSwAccountAsset")  
    public @ResponseBody String GetSwAccountAsset(HttpServletRequest request,SwAccountQueryInfo swAccountQueryInfo)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = null;
		
		try
		{
			
			//String projectId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			
			String proId = request.getSession().getAttribute("proId").toString();
			String userId = request.getSession().getAttribute("userId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";			
			
			
			dto = assetAccountService.getSwAccountAsset(dbPath, userId, swAccountQueryInfo);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
			ex.printStackTrace();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result  = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/GetJqAccountList")  
    public @ResponseBody String GetJqAccountList(HttpServletRequest request,String id,@RequestParam(value="start", required=false,defaultValue="0")Integer start,@RequestParam(value="limit", required=false,defaultValue="25")Integer limit,JqAccountQueryInfo jqAccountQueryInfo)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = null;
		
		try
		{
						
			String proId = request.getSession().getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";	
			
			if(id == null)
			{
				id = ProjectFileUtil.getLastestFileId(dbPath, 2);
			}
			
			
			dto = assetAccountService.getJqAccountList(dbPath, id, start, limit, jqAccountQueryInfo);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetCwAccountList",method=RequestMethod.GET)  
    public @ResponseBody String GetCwAccountList(HttpServletRequest request,@RequestParam(value="start", required=false,defaultValue="0")Integer start,@RequestParam(value="limit", required=false,defaultValue="25")Integer limit)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = null;
		
		try
		{
			
			//String projectId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			
			String proId = request.getSession().getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";			
			
			String id = ProjectFileUtil.getLastestFileId(dbPath, 1);
			
			dto = assetAccountService.getCwAccountList(dbPath,id, start, limit);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetCwAccountAsset",method=RequestMethod.GET)  
    public @ResponseBody String GetCwAccountAsset(HttpServletRequest request)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto dto = null;
		
		try
		{
			
			//String proId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			
			String proId = request.getSession().getAttribute("proId").toString();
			
			String userId = request.getSession().getAttribute("userId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";			

			dto = assetAccountService.getCwAccountAsset(dbPath, userId);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetJqAssetDetailById",method=RequestMethod.GET)  
    public @ResponseBody String GetCwAccountAsset(HttpServletRequest request,String id,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		JqAssetAccount jqAssetAccount = null;
		
		try
		{
			HttpSession httpSession = request.getSession();
			
			
			String jqFileId = "";
			if(type == 1)
			{
				jqFileId = httpSession.getAttribute("jqRelaCwFileId").toString();
			}
			else
			{
				jqFileId = httpSession.getAttribute("jqRelaSwFileId").toString();
			}
			
			//String jqFileId = httpSession.getAttribute("jqFileId").toString();
			
			String proId = httpSession.getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";
			
			jqAssetAccount  = JqAccountUtil.getJqAssetDetailById(dbPath, jqFileId, type, id);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", jqAssetAccount);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetCwAssetDetailById",method=RequestMethod.GET)  
    public @ResponseBody String GetCwAssetDetailById(HttpServletRequest request,String id)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		CwAssetAccount cwAssetAccount = null;
		
		try
		{
			HttpSession httpSession = request.getSession();
						
			String proId = httpSession.getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";
			
			cwAssetAccount  = CwAccountUtil.getCwAssetDetailById(dbPath, id);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", cwAssetAccount);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetSwAssetDetailById",method=RequestMethod.GET)  
    public @ResponseBody String GetSwAssetDetailById(HttpServletRequest request,String id)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		SwAssetAccount swAssetAccount = null;
		
		try
		{
			HttpSession httpSession = request.getSession();
						
			String proId = httpSession.getAttribute("proId").toString();
			
			String dbPath = Utils.dbPath+proId+".db";
			
			swAssetAccount  = SwAccountUtil.getSwAssetDetailById(dbPath, id);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", swAssetAccount);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value ="/DownloadAssetMgr",method=RequestMethod.GET)
	public @ResponseBody String DownloadAssetMgr(HttpServletRequest request,@RequestParam(value="type", required=false,defaultValue="1")Integer type) 
	{
		
		Dto outDto = new BaseDto();
		
		boolean flag = true;
		String  errMess = ""; 
		
		String result =null;
		
		final String proUuid =  CommonUtils.getUUID();
		
		try
		{		
			
				String outFileName = "";
				
				if(type == 1)
				{
					outFileName = "财务账对账结果.xls";
				}
				else if(type == 2)
				{
					outFileName = "久其账对账结果(与财务账对账).xls";
				}
				else if(type == 3)
				{
					outFileName = "久其账对账结果(与实物账对账).xls";
				}
				else if(type == 4)
				{
					outFileName = "实物账对账结果.xls";
				}
			
				
				
				String outFilePath = Utils.exportPath+proUuid+".xls";
				
				DownFileInfo downfileInfo = new DownFileInfo();
				downfileInfo.setId(proUuid);
				downfileInfo.setOutFileName(outFileName);
				downfileInfo.setFilePath(outFilePath);
				
				Utils.listDownFileInfo.add(downfileInfo);
				
				DownAssetMgrParam param = new DownAssetMgrParam();
				
				
				HttpSession httpSession =  request.getSession();
				
				String proId = httpSession.getAttribute("proId").toString();
				String jqRelaCwFileId = httpSession.getAttribute("jqRelaCwFileId").toString();
				String jqRelaSwFileId = httpSession.getAttribute("jqRelaSwFileId").toString();
				
				
				param.setType(type);
				param.setProId(proId);
				param.setJqRelaCwFileId(jqRelaCwFileId);
				param.setJqRelaSwFileId(jqRelaSwFileId);
				param.setOutFilePath(outFilePath);
				param.setProUuid(proUuid);
				
				DownloadAssetMgrThread th = new DownloadAssetMgrThread();
				th.setDownAssetMgrParam(param);
				
				Thread thread = new Thread(th);
				thread.start();
				
				
		}
		catch(Exception ex)
		{
			errMess = ex.getMessage();
			flag = false;
		}
		
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", proUuid);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
	} 
	
	
	
	
	
	
	
	
	
	public class DownloadAssetMgrThread implements Runnable
	{

		private DownAssetMgrParam downAssetMgrParam;
		 

		public DownAssetMgrParam getDownAssetMgrParam() {
			return downAssetMgrParam;
		}

		
		public void setDownAssetMgrParam(DownAssetMgrParam downAssetMgrParam) {
			this.downAssetMgrParam = downAssetMgrParam;
		}


		@Override
		public void run() {
			
			
			SingleList singlelist = SingleList.getInstance();
			
			
			String proUuid = downAssetMgrParam.getProUuid();
			
			boolean updateFlag = true;
			
			try
			{
				
					
					
					String proId = downAssetMgrParam.getProId();
				
					singlelist.addTail(proUuid,"文件开始生成...");
				
					int type = downAssetMgrParam.getType();					
					
					String dbPath = Utils.dbPath+proId+".db";
					
					String outFilePath = downAssetMgrParam.getOutFilePath();
					
					
					if(type == 1)
					{
						List<CwAssetAccount> listCwAssetAccount = CwAccountUtil.getCwAccountAsset(dbPath,null, 0, 0);
					
						JSONArray jaContent = JSONArray.parseArray(JSONHelper.encodeObject2Json(listCwAssetAccount,"yyyy-MM-dd"));
						
						
						Map<String,String> headMap = new LinkedHashMap<String,String>();
						headMap.put("month", "月");
						headMap.put("day", "日");
						headMap.put("accountNo", "凭证号码");
						headMap.put("remark", "摘要");
						headMap.put("debit", "借方");
						headMap.put("lender", "贷方");
						headMap.put("direction", "方向");
						headMap.put("balance", "余额");
						headMap.put("rela", "是否关联");
				        
				        ExcelUtils.export2Excel(headMap, jaContent, outFilePath);
					
					}
					else if(type == 2 || type == 3)
					{
						
						
						
						Integer relaType = 0;
						String fileId = "";
						if(type == 2)
						{
								relaType = 1;
								fileId = downAssetMgrParam.getJqRelaCwFileId();
						}
						else
						{
								relaType = 2;
								fileId = downAssetMgrParam.getJqRelaSwFileId();
						}	
						
						
						
						List<JqAssetAccount> listJqAssetAccount = JqAccountUtil.getJqAccountAsset(dbPath, fileId, relaType, 0, 0, null);
						JSONArray jaContent = JSONArray.parseArray(JSONHelper.encodeObject2Json(listJqAssetAccount,"yyyy-MM-dd"));
						
						
						Map<String,String> headMap = new LinkedHashMap<String,String>();
						
				        headMap.put("assetBarcode","资产编号");
				        headMap.put("assetBigClass","资产大类");
				        headMap.put("assetClass","资产分类");
				        headMap.put("assetName","资产名称");
				        headMap.put("assetCwrzrq","财务入账日期");
				        headMap.put("moneyType","价值类型");
				        headMap.put("money","价值");
				        headMap.put("getType","取得方式");
				        headMap.put("getDate","取得日期");
				        headMap.put("useStatus","使用状况");
				        headMap.put("remark","备注");
				        headMap.put("useDirect","使用方向");
				        headMap.put("useDept","使用部门");
				        headMap.put("manageDept","管理部门");
				        headMap.put("useUser","使用人");
				        headMap.put("sl","数量");
				        headMap.put("documentMaker","制单人");
				        headMap.put("documentMakTime","制单时间");
				        headMap.put("checkNo","清查编号");
				        headMap.put("belongCompany","所属单位");
				        headMap.put("cardStatus","卡片状态");
				        headMap.put("totalDepreciate","累计折旧");
				        headMap.put("alreadyDepreciateMonth","已提折旧月数");
				        headMap.put("netValue","净值");
				        headMap.put("budgetProjectNo","预算项目编号");
				        headMap.put("buyOrgType","采购组织形式");
				        headMap.put("assetPp","品牌");
				        headMap.put("assetModel","规格型号");
				        headMap.put("saveAddress","存放地点");
				        headMap.put("manufacturer","生产厂家");
				        headMap.put("seller","销售商");
				        headMap.put("contractNo","合同编号");
				        headMap.put("invoiceNo","发票号");
				        headMap.put("useArea","使用权面积/建筑面积");
				        headMap.put("structure","建筑结构");
				        headMap.put("designUse","设计用途");
				        headMap.put("financialFunds","财政性资金");
				        headMap.put("notFinancialFunds","非财政性资金");
				        headMap.put("accountNo","会计凭证号");
				        headMap.put("propertyRight","产权形式");
				        headMap.put("ownerType","权属性质");
				        headMap.put("ownerNo","权属证号");
				        headMap.put("landUseType","土地使用权类型");
				        headMap.put("issueDate","发证日期");
				        headMap.put("landUse","地类(用途)");
				        headMap.put("landOrder","土地级次");
				        headMap.put("depreciateStatus","折旧状态");
				        headMap.put("location","坐落位置");
				        headMap.put("carUse","车辆用途");
				        headMap.put("carProduce","车辆产地");
				        headMap.put("carNo","车牌号");
				        headMap.put("carPl","排气量");
				        headMap.put("rela","是否关联");
						
						
					
						ExcelUtils.export2Excel(headMap, jaContent, outFilePath);
					
					}
					else if(type == 4)
					{
						 List<SwAssetAccount> listSwAssetAccount = SwAccountUtil.getSwAccountAsset(dbPath, 0, 0, null);
						 JSONArray jaContent = JSONArray.parseArray(JSONHelper.encodeObject2Json(listSwAssetAccount,"yyyy-MM-dd"));
					
						 	
						 
						 Map<String,String> headMap = new LinkedHashMap<String,String>();
							
				       
				        headMap.put("assetName","资产名称");
				        headMap.put("sl","数量");
				        headMap.put("getDate","取得日期");
				        headMap.put("assetPp","品牌");
				        headMap.put("assetModel","规格型号");
				        headMap.put("useDept","使用部门");
				        headMap.put("useUser","使用人");
				        headMap.put("saveAddress","存放地点");
				        headMap.put("rela","是否关联");
				        
				     
					
						ExcelUtils.export2Excel(headMap, jaContent, outFilePath);
					}
					
				
			}
			catch(Exception ex)
			{
					String s = ex.getMessage();
					updateFlag = false;
					
			}
			
			finally
			{
				ProgressInfo progressInfo = new ProgressInfo();
				progressInfo.setProgress("生成完成");
				progressInfo.setFinishFlag(true);
				progressInfo.setUpdateFlag(updateFlag);
				progressInfo.setFinishTime(CommonUtils.getCurrentTime());	
				singlelist.change(proUuid, progressInfo);
			}
		}
	}
}
