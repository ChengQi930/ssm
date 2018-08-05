package cn.bizowner.controller;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.MediaHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.IA_Users;
import cn.bizowner.model.Inventory_Accounts;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.UserInfo;
import cn.bizowner.service.impl.Imp_InventoryImpl;
import cn.bizowner.service.impl.UserServiceImpl;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.TimeUtil;
import cn.bizowner.utils.Utils;

@RestController
@RequestMapping("/Inventory")
public class Imp_InventoryController {
    
	@Autowired 
    private Imp_InventoryImpl imp_In;
	
	@Autowired
	private UserServiceImpl user;
	
	//1.建立清查任务接口
	@RequestMapping(value = "/Establish",method = RequestMethod.POST)
	public @ResponseBody String addInventory_Accounts(HttpServletRequest request,Inventory_Accounts IA,String user_id){
		
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		try {
		 JSONArray parse = (JSONArray) JSONArray.parse(user_id);
		 List<String> listJqIds = new ArrayList<String>();
		 for (Object object : parse) {
			 String string = object.toString();
			 listJqIds.add(string);
		 }
		 
		  StringBuffer stringBuffer = new StringBuffer();
		    for(int i=0;i<listJqIds.size();i++){
		    	String menid = listJqIds.get(i);
		      
			 String mc = user.getUserInfo(menid).getMc();   //走数据库
			
			    stringBuffer.append(mc+",");
		    }
		    StringBuffer men = stringBuffer.deleteCharAt(stringBuffer.length()-1);
		    
		    
		    
			 UUID randomUUID = UUID.randomUUID();
			 long time = new  Date().getTime();
			 String yearMonthDay = TimeUtil.getYearMonthDay(time);
			 
			 
			 IA.setMen(men+"");
			 
			 IA.setId(randomUUID+"");
			 HttpSession session = request.getSession();
			 String value = (String) session.getAttribute("proId");
			 String userId = (String) session.getAttribute("userId");
			 String leader = user.getUserInfo(userId).getMc();   //  組长 走数据库
			 IA.setLeader(leader);
			 IA.setProjectId(value);
			 IA.setTaskDate(yearMonthDay);
			 
			 //0为新建状态
			 IA.setTask_status("0");
		 
		 int insertIA = 0;
		
			insertIA = imp_In.insertIA(IA,listJqIds);
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		 
		 if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	    	  outDto.put("errmsg" ,errMess);
	      }
		
		 result = JSONHelper.encodeObject2JsonWithNull(outDto);
			return result;
	}
	
	
	//修改清查任务 
	@RequestMapping(value = "/Update" ,method = RequestMethod.POST)
	public @ResponseBody String updateInventory(HttpServletRequest request,Inventory_Accounts IA,String user_id){
			
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		 JSONArray parse = (JSONArray) JSONArray.parse(user_id);
		 List<String> listJqIds = new ArrayList<String>();
		 
		 for (Object object : parse) {
			 String string = object.toString();
			 listJqIds.add(string);
		 }
		 
			 Inventory_Accounts inventory_Accounts = new Inventory_Accounts();
			 
			 String id = IA.getId();
			 inventory_Accounts.setId(id);
			 
			 String attribute = (String) request.getSession().getAttribute("proId");
			 inventory_Accounts.setProjectId(attribute);
			 
			 String taskName = IA.getTaskName();
			 inventory_Accounts.setTaskName(taskName);
			 
			 long time = new  Date().getTime();
			 String yearMonthDay = TimeUtil.getYearMonthDay(time);
			 inventory_Accounts.setTaskDate(yearMonthDay);
			 
			 String task_status = IA.getTask_status();
			 inventory_Accounts.setTask_status(task_status);
			 
			 String remark = IA.getRemark();
			 inventory_Accounts.setRemark(remark);
			 
		 
		 int insertIA = 0;
		try {
			insertIA = imp_In.updateInventory(inventory_Accounts, listJqIds);
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		 
		 if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	    	  outDto.put("errmsg" , errMess);	 
	      }
		 result = JSONHelper.encodeObject2JsonWithNull(outDto);
			return result;
	}
	
	
	//查询清查任务！
	@RequestMapping(value = "/GetQc",method = RequestMethod.GET)
	public @ResponseBody String getimp_qc_task(HttpServletRequest request){
		  
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		
		String attribute = (String) request.getSession().getAttribute("proId");
		
		
		List getimpqc = null;
		try {
			getimpqc = imp_In.getimpqc(attribute);
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		
		if(getimpqc != null){
			outDto.put("flag",(boolean)true);
			outDto.put("result", getimpqc);
		}else{
			outDto.put("flag",(boolean)false);
			outDto.put("errmag",errMess);
		}
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	}
	
	//删除
	//删除所有清查任务
	@RequestMapping(value = "/Delete" , method = RequestMethod.POST)
	public @ResponseBody String delete(HttpServletRequest request,String taskid){
		
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		
		String attribute = (String) request.getSession().getAttribute("proId");
		String dbpath = Utils.dbPath+attribute+".db";
		
		int delete = 0;
		try {
			delete = imp_In.delete(dbpath, taskid);
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		
		if(delete != 0){
			 outDto.put("flag" , (boolean)true);
			 
		}else{
			outDto.put("flag" , (boolean)false);
			outDto.put("errmag",errMess);
		}
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	}
	
	
	//2.下发清查任务接口 也就是下发久其账单！
	@RequestMapping(value = "/DownloadTask",method = RequestMethod.GET)
	public @ResponseBody String getJqAssetAccount(HttpServletRequest request){
		
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		
		String attribute = (String) request.getSession().getAttribute("proId");
		String dbpath = Utils.dbPath+attribute+".db";
		
		String UserId = (String) request.getSession().getAttribute("userId");
		List<JqAssetAccount> jqAssetAccount = null;
		try {
			jqAssetAccount = imp_In.getJqAssetAccount(dbpath,attribute,UserId);
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		
		if(flag=true){
			outDto.put("flag", (boolean)true);
			outDto.put("result", jqAssetAccount);
		}else{
			outDto.put("flag",(boolean)false);
			outDto.put("errmag",errMess);
		}
		result =JSONHelper.encodeObject2Json(jqAssetAccount,"yyyy-MM-dd");
		/*result = JSONHelper.encodeObject2JsonWithNull(jqAssetAccount);*/
		return result;
	}
	
	//上传清查任务 也就是接收 实物清单！
	@RequestMapping(value = "/UploadTask",method = RequestMethod.POST)
     public @ResponseBody String uploadInventory(HttpServletRequest request,MultipartFile accountFile,SwAssetAccount swAA){
    	  
			String result =null;
			boolean flag = true;
			String errMes = "";
			Dto outDto = new BaseDto();
		try {
			
			
			 SwAssetAccount sw = new SwAssetAccount();
			 sw.setId(UUID.randomUUID()+"");
			 sw.setTaskId(swAA.getId());
			 
			 String projectid = request.getSession().getAttribute("proId")+"";
			 //projectid穿过去以后就充当 projectid
			 sw.setAssetName(swAA.getAssetName());
			 sw.setSl(Integer.parseInt(swAA.getSl()+""));
			 
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 long time = new Date().getTime();
			 sw.setGetDate(simpleDateFormat.parse(TimeUtil.getYearMonthDay(time)));
			 sw.setAssetPp(swAA.getAssetPp());
			 sw.setAssetModel(swAA.getAssetModel());
			 sw.setUseDept(swAA.getUseDept());
			 sw.setUseUser(swAA.getUseUser());
			 sw.setSaveAddress(swAA.getSaveAddress());
			 
			 //接收文件
			 String filePath = "";
				String oriFileName = accountFile.getOriginalFilename();
				String tempPath = Utils.filePath;
				String fileType = oriFileName.substring(oriFileName.lastIndexOf("."), oriFileName.length());
				String newFileName = CommonUtils.getUUID() + fileType;
				filePath = tempPath+newFileName;
				File dir = new File(filePath);
				
				File fileNew=new File(filePath);//new 一个文件 构造参数是字符串
				File rootFile=fileNew.getParentFile();//得到父文件夹
				if(!fileNew.exists()){
					rootFile.mkdirs();
					fileNew.createNewFile();
				}
				//MultipartFile自带的解析方法   文件就存进dir指定的文件夹中！
				accountFile.transferTo(fileNew);
				
				String accessToken = AuthHelper.getAccessToken();
				String url = "https://oapi.dingtalk.com/media/upload?" +"access_token=" + accessToken + "&type=file";
				JSONObject response = MediaHelper.uploadMedia(url, filePath);
				//图片的id
				String media = response.getString("media_id");
				sw.setPictureId(media);
				//删除图片
				fileNew.delete();
				 
			    
			    imp_In.uploadInventory(sw, projectid);   
		     
			
			
		} catch (Exception e) {
			flag = false;
			errMes = e.getMessage();
		}
		 if(flag == true){
			 outDto.put("flag", true);
		 }else{
			 outDto.put("flag", false);
			 outDto.put("errmsg", errMes);
		 }
		 result = JSONHelper.encodeObject2JsonWithNull(outDto);
			return result;
     }
}
