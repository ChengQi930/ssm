package cn.bizowner.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController; 

import com.alibaba.fastjson.JSONArray; 

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.model.ChangeLog;
import cn.bizowner.model.CwAssetAccount;
import cn.bizowner.model.CwRelaJq;
import cn.bizowner.model.DownAssetMgrParam;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.OutDataInfo;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.TopicMes; 
import cn.bizowner.service.CheckService;
import cn.bizowner.service.UpdateAccService;
import cn.bizowner.sqlite.CwAccountUtil;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.SwAccountUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.ExcelUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/Check")
public class CheckController {

		
	
	@Autowired
	private CheckService checkService;
	 
	
	@Autowired
	private UpdateAccService updateAccService;
	
	
	@RequestMapping(value="JqRelaSw",method=RequestMethod.POST)  
    public @ResponseBody String JqRelaSw(HttpServletRequest request,String jqId,String swId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
				
		try
		{
			
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			String mc = httpSesion.getAttribute("mc").toString();
			String proId = httpSesion.getAttribute("proId").toString();
			 
			checkService.jqRelaSwMulToMul(userId,mc,proId,jqId,swId);
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	@RequestMapping(value="TrmtJqRelaSw",method=RequestMethod.POST)  
    public @ResponseBody String TrmtJqRelaSw(HttpServletRequest request,String id,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto retDto = new BaseDto();
				
		try
		{
			/*String proId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			String userId = "001";*/
			
			
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			String proId = httpSesion.getAttribute("proId").toString();
			 
			String mc = httpSesion.getAttribute("mc").toString();
			
			
			retDto = checkService.trmtJqRelaSw(  userId, mc, proId,  id,  type);
			
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", retDto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	@RequestMapping(value="IsMulJqRelaSw",method=RequestMethod.GET)  
    public @ResponseBody String IsMulJqRelaSw(HttpServletRequest request,String id,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		boolean mulFlag = false;
		
		try
		{
			/*String proId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			String userId = "001";*/
			
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			String proId = httpSesion.getAttribute("proId").toString();
			
			
			String dbPath = Utils.dbPath+proId+".db";	
			
			mulFlag = checkService.isMulJqRelaSw(dbPath, id, type);
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", mulFlag);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	@RequestMapping(value="CwRelaJq",method=RequestMethod.POST)  
    public @ResponseBody String CwRelaJq(HttpServletRequest request,String cwId,String jqId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
				
		try
		{ 
			
			
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			String proId = httpSesion.getAttribute("proId").toString();
			String mc = httpSesion.getAttribute("mc").toString();
			
			checkService.cwRelaJqMulToMul(  userId,  mc,proId,  cwId,  jqId);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="TrmtCwRelaJq",method=RequestMethod.POST)  
    public @ResponseBody String TrmtCwRelaJq(HttpServletRequest request,String id,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		Dto retDto = null;
				
		try
		{
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			String proId = httpSesion.getAttribute("proId").toString();
			String mc = httpSesion.getAttribute("mc").toString();
			
			retDto  = checkService.trmtCwRelaJq(userId, mc, proId,  id,  type);
			
			
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", retDto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	@RequestMapping(value="IsMulCwRelaJq",method=RequestMethod.GET)  
    public @ResponseBody String IsMulCwRelaJq(HttpServletRequest request,String id,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		boolean mulFlag = false;
		
		try
		{ 
			
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			String proId = httpSesion.getAttribute("proId").toString();
			
			
			String dbPath = Utils.dbPath+proId+".db";	
			
			mulFlag = checkService.isMulCwRelaJq(dbPath, id, type);
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", mulFlag);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	@RequestMapping(value="/IsHavePerm",method=RequestMethod.GET)  
    public @ResponseBody String IsHavePerm(HttpServletRequest request,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		
		Dto outDto = new BaseDto();
		
		String  errMess = "";
		
		
		boolean retFlag = true;
		
		try
		{			
				HttpSession httpSession = request.getSession();
			
				String proId = httpSession.getAttribute("proId").toString();
					
				String userId = httpSession.getAttribute("userId").toString();
				
				retFlag = checkService.isHavePerm(proId, userId, type);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", retFlag);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/UpdateRecord",method=RequestMethod.POST)  
    public @ResponseBody String UpdateRecord(HttpServletRequest request,@RequestParam(value="accountType", required=false,defaultValue="1")Integer accountType,
    		String id,String columnName,String newValue)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
				
		try
		{
			
			
			HttpSession httpSession = request.getSession();
			
			String userId = httpSession.getAttribute("userId").toString();
			
			String mc = httpSession.getAttribute("mc").toString();
			
			String proId = httpSession.getAttribute("proId").toString();
			
			updateAccService.updateRecord(proId,userId,mc,accountType,id,columnName,newValue);
			
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	 
	
	
	@RequestMapping(value="/AddJqAsset",method=RequestMethod.POST)  
    public @ResponseBody String AddJqAsset(HttpServletRequest request,@RequestParam(value="type", required=false,defaultValue="1")Integer type,JqAssetAccount jqAssetAccount)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
				
		try
		{
			
			
			HttpSession httpSession = request.getSession();
			
			String userId = httpSession.getAttribute("userId").toString();
			
			String mc = httpSession.getAttribute("mc").toString();
			
			String proId = httpSession.getAttribute("proId").toString();
			 
			String jqFileId = "";
			if(type == 1)			//财务久其对账阶段为1
			{
				jqFileId = httpSession.getAttribute("jqRelaCwFileId").toString();
			}
			else					//久其实物阶段为2
			{
				jqFileId = httpSession.getAttribute("jqRelaSwFileId").toString();
			}
			
			jqAssetAccount.setId(CommonUtils.getUUID());
			jqAssetAccount.setFileId(jqFileId);
			
			updateAccService.addJqAsset(proId, userId, mc,type, jqAssetAccount);
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/AddSwAsset",method=RequestMethod.POST)  
    public @ResponseBody String AddSwAsset(HttpServletRequest request,SwAssetAccount swAssetAccount)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
				
		try
		{
			
			
			HttpSession httpSession = request.getSession();
			
			String userId = httpSession.getAttribute("userId").toString();
			
			String mc = httpSession.getAttribute("mc").toString();
			
			String proId = httpSession.getAttribute("proId").toString();
			 
			swAssetAccount.setId(CommonUtils.getUUID());
			  
			updateAccService.addSwAsset(proId, userId, mc,swAssetAccount);
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/SplitJqAsset",method=RequestMethod.POST)  
    public @ResponseBody String SplitJqAsset(HttpServletRequest request,String id,Integer sl,@RequestParam(value="accountType", required=false,defaultValue="1")Integer accountType)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		
		List<JqAssetAccount> list = null;
				
		try
		{
			
			
			HttpSession httpSession = request.getSession();
			
			String userId = httpSession.getAttribute("userId").toString();
			
			String mc = httpSession.getAttribute("mc").toString();
			
			String proId = httpSession.getAttribute("proId").toString();
			 
			
			String jqFileId = null;
			
			if(accountType == Utils.AccountType_Detail_Jq_RelaCw)
			{
				jqFileId = httpSession.getAttribute("jqRelaCwFileId").toString();
			}
			else
			{
				jqFileId = httpSession.getAttribute("jqRelaSwFileId").toString();
			}
			 
			
			list = updateAccService.splitJqAsset(proId, userId, mc, jqFileId, accountType, id, sl);
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", list);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	  
	
	@RequestMapping(value="SuggCheckJqRelaSw",method=RequestMethod.GET)  
    public @ResponseBody String SuggCheckJqRelaSw(HttpServletRequest request,String checkIndexId)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		  
		final String proUuid =  CommonUtils.getUUID();
				
		try
		{ 
			
				HttpSession httpSesion =  request.getSession(); 
				String proId = httpSesion.getAttribute("proId").toString(); 
				String jqRelaSwFileId = httpSesion.getAttribute("jqRelaSwFileId").toString();
				 
				SuggCheckThread th = new SuggCheckThread();
				
				Dto dto = new BaseDto();
				dto.put("proId", proId);
				dto.put("checkIndexId", checkIndexId);
				dto.put("proUuid", proUuid);
				dto.put("jqRelaSwFileId", jqRelaSwFileId);
				
				th.setDto(dto);
				
				
				Thread thread = new Thread(th);
				thread.start();
				 
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
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
		 
		
		result  = JSONHelper.encodeObject2Json(outDto,"yyyy-MM-dd");
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	public class SuggCheckThread implements Runnable
	{

		private Dto dto;
		 
		public Dto getDto() 
		{
			return dto;
		} 
		
		public void setDto(Dto dto) 
		{
			this.dto = dto;
		}

 
		@Override
		public void run() {
			
			
			SingleList singlelist = SingleList.getInstance();
			
			
			String proUuid = dto.getAsString("proUuid");
			String proId = dto.getAsString("proId");
			String checkIndexId = dto.getAsString("checkIndexId");
			String userId = dto.getAsString("userId");
			String jqRelaSwFileId = dto.getAsString("jqRelaSwFileId");
			
			boolean updateFlag = true;
			
			try
			{
				  
					singlelist.addTail(proUuid,"数据加载中...");
					
					Dto dto  = checkService.suggCheckJqRelaSw(proId, checkIndexId,userId,jqRelaSwFileId);
					
					OutDataInfo outDataInfo = new OutDataInfo();
					outDataInfo.setFinishTime(new Date());
					outDataInfo.setId(proUuid);
					outDataInfo.setO(dto);
					
					Utils.listDataInfo.add(outDataInfo);
			}
			catch(Exception ex)
			{
					String s = ex.getMessage();
					updateFlag = false;
					
			}
			
			finally
			{
				ProgressInfo progressInfo = new ProgressInfo();
				progressInfo.setProgress("加载完成");
				progressInfo.setFinishFlag(true);
				progressInfo.setUpdateFlag(updateFlag);
				progressInfo.setFinishTime(CommonUtils.getCurrentTime());	
				singlelist.change(proUuid, progressInfo);
			}
		}
	}
	
}
