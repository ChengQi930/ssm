package cn.bizowner.sqlite;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;

import cn.bizowner.controller.Imp_InventoryController;
import cn.bizowner.dingtalk.openapi.helper.DeptHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.IA_Users;
import cn.bizowner.model.Imp_User;
import cn.bizowner.model.Inventory_Accounts;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.model.SwAssetAccount;
import cn.bizowner.model.imp_qc_task;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.TimeUtil;
import cn.bizowner.utils.Utils;

public class Inventory {  
      
	//建立清查任务
	public static int insertInventory(Inventory_Accounts IA,List<String> user){
		  
	   String dbName=IA.getProjectId();	
	   String dbpath = Utils.dbPath+dbName+".db";
	   
	   List<String> listSql = new ArrayList<String>();
	   List<Object[]> listObj = new ArrayList<Object[]>();
		 try {
			 
			String insertIAinfo = "insert into imp_qc_task(id,projectId,taskName,taskDate,task_status,remark,leader,men) values(?,?,?,?,?,?,?,?)";
			
			String id = IA.getId();
			String taskName = IA.getTaskName();
			Object[] proFileInfoObj = {IA.getId(), IA.getProjectId(), IA.getTaskName(), IA.getTaskDate(),
					IA.getTask_status(), IA.getRemark(), IA.getLeader() ,IA.getMen()};
			
			listSql.add(insertIAinfo);
			listObj.add(proFileInfoObj);
			
			if(user != null){
			for(int i=0;i<user.size();i++){
				String insertIUserinfo = "insert into imp_qc_users(uid,qc_task_id,qc_user_id,user_Task_status) values(?,?,?,?)";
				UUID randomUUID = UUID.randomUUID();
				Object[] proFileUserInfo = {randomUUID+"", IA.getId(), user.get(i),0};
				listSql.add(insertIUserinfo);
				listObj.add(proFileUserInfo);
			}
			}
			SqliteHelper sqliteHelper = new SqliteHelper(dbpath);
			
			sqliteHelper.executeSqlTrans(listSql, listObj);
			sqliteHelper.destroyed();
			
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	//修改清查任务
	public static int updateInventory(Inventory_Accounts IA,List<String> user) throws Exception{
		
		//projectId
		String ProjectId = IA.getProjectId();
		
		//dbpath
	    String dbpath = Utils.dbPath+ProjectId+".db";
		
		//qc   id
		String qcid = IA.getId();
		//qc taskName
		String taskName = IA.getTaskName();
		//qc taskDate
		long time = new Date().getTime();
		String yearMonthDay = TimeUtil.getYearMonthDay(time);
		
		
		
		//修改清查项目表
		 String updateTask="update imp_qc_task set taskName='"+taskName+"',remark='"+IA.getRemark()+"',leader='"+IA.getLeader()+"',men='"+IA.getMen()+"' where id = '"+qcid+"'";
		SqliteHelper updateHelper;
		
			updateHelper = new SqliteHelper(dbpath);
			int exe = updateHelper.executeUpdate(updateTask);
		
		
		
		
		//先查原始数据 并且保存下 对应用户的 状态 和 userid
		List<String> SaveThe = new ArrayList<String>(); //保存原始 状态数据
		List<String> Qcuserid = new ArrayList<String>();//保存原始 用户 id数据
		String OselectSql=null;
		OselectSql = "select qc_user_id,user_Task_status from imp_qc_users";
		SqliteHelper OsqliteHelper;
		
			OsqliteHelper = new SqliteHelper(dbpath);
			List<Map<String, Object>> executeQuery = OsqliteHelper.executeQuery(OselectSql);
			for (Map<String, Object> map : executeQuery) {
				
				String SaveTheField = (String) map.get("user_Task_status");  
				String qc_user_id = (String) map.get("qc_user_id");
				
				SaveThe.add(SaveTheField);
				Qcuserid.add(qc_user_id);
			}
			
		
		List<String> SaveThe2 = new ArrayList<String>(); //保存最新 状态数据
		List<String> Qcuserid2 = new ArrayList<String>();//保存最新 用户 id数据
		
		for (int i=0;i<user.size();i++) {	 
		String selectSql=null;
		selectSql = "select qc_user_id,user_Task_status from imp_qc_users as qu where qu.qc_user_id  = '"+user.get(i)+"'";
		SqliteHelper sqliteHelper;
		
			sqliteHelper = new SqliteHelper(dbpath);
			List<Map<String, Object>> executeQuery1 = sqliteHelper.executeQuery(selectSql);
			for (Map<String, Object> map : executeQuery1) {
				
				String SaveTheField = (String) map.get("user_Task_status");  
				String qc_user_id = (String) map.get("qc_user_id");
				
				SaveThe2.add(SaveTheField);
				
				Qcuserid2.add(qc_user_id);
			}
		 	
		}
		
		//全部删除
		String delSqluser = null;
		delSqluser="DELETE from imp_qc_users where imp_qc_users.qc_task_id ='"+qcid+"'";
		
			SqliteHelper sqliteHelper = new SqliteHelper(dbpath);
				int executeUpdate2 = sqliteHelper.executeUpdate(delSqluser);
				sqliteHelper.destroyed();
				
					//删掉以后再 增加！
					List<String> listSql = new ArrayList<String>();
					   List<Object[]> listObj = new ArrayList<Object[]>();
					  
							for(int i=0;i<user.size();i++){
								
								String insertIUserinfo = "insert into imp_qc_users(uid,qc_task_id,qc_user_id,user_Task_status) values(?,?,?,?)";
								UUID randomUUID = UUID.randomUUID();
								
							       if(executeUpdate2 == 0){
							    	   for(int j=0;j<user.size();i++){
							    		   Object[] proFileUserInfo = {randomUUID+"", IA.getId(), user.get(i),0};
											listObj.add(proFileUserInfo);
											break;
							    	   }
							       }else{
									for(int k=0;k<user.size();k++){
										
										if(Qcuserid2.size()>=Qcuserid.size()){
											
											for(int p=0;p<Qcuserid.size();p++){
												
												  	if(Qcuserid2.get(k).equals(Qcuserid.get(k))){
												  		Object[] proFileUserInfo = {randomUUID+"", IA.getId(), user.get(i),SaveThe.get(k)};
														listObj.add(proFileUserInfo);
														break;
												  	}else{
												  		Object[] proFileUserInfo = {randomUUID+"", IA.getId(), user.get(i),0};
														listObj.add(proFileUserInfo);
														break;
												  	}
												
												  	
											}
											
										}
										
										if(user.get(i).equals(Qcuserid.get(k))){
											Object[] proFileUserInfo = {randomUUID+"", IA.getId(), user.get(i),SaveThe.get(k)};
											listObj.add(proFileUserInfo);
											break;
										}else{
											Object[] proFileUserInfo = {randomUUID+"", IA.getId(), user.get(i),0};
											listObj.add(proFileUserInfo);
											break;
										}
										
									}
							    	   
							       }
									listSql.add(insertIUserinfo);
								
							}
							
					   SqliteHelper insert = new SqliteHelper(dbpath);
						
					   insert.executeSqlTrans(listSql, listObj);
					   insert.destroyed();
					return executeUpdate2;
						
						
		
		
		
		
		
		
	}
	
	//查询清查任务！
	public static List<imp_qc_task> getimp_qc_task(String attribute) throws Exception{
		
		String dbpath = Utils.dbPath+attribute+".db";
		List<imp_qc_task> list = new ArrayList<imp_qc_task>();
		
		String selectSql= "select * from imp_qc_task as qt join imp_qc_users as qu on qt.id = qu.qc_task_id";
		String select2 = "select * from imp_qc_task where imp_qc_task.projectId = '"+attribute+"'";
		
		SqliteHelper sqliteHelper1 = new SqliteHelper(dbpath);	
        List<Map<String, Object>> executeQuery1 = sqliteHelper1.executeQuery(select2);
        
		for (Map<String, Object> map : executeQuery1) {
			
			 String id =  (String) map.get("id");
			 String projectId = (String)map.get("projectId");
			 String taskName = (String)map.get("taskName");
			 String taskDate = (String)map.get("taskDate");
			 String task_status = (String)map.get("task_status");
			 String remark = (String)map.get("remark");
			 
			 imp_qc_task imp_qc_task = new imp_qc_task();
			 imp_qc_task.setId(id);
			 imp_qc_task.setProjectId(projectId);
			 imp_qc_task.setTaskName(taskName);
			 imp_qc_task.setTaskDate(taskDate);
			 imp_qc_task.setTask_status(task_status);
			 imp_qc_task.setRemark(remark);
   		    
			 SqliteHelper sqliteHelper = new SqliteHelper(dbpath);	
	         List<Map<String, Object>> executeQuery = sqliteHelper.executeQuery(selectSql);
	         List<IA_Users> list2 = new ArrayList<IA_Users>();;
	         for (Map<String, Object> map2 : executeQuery) {
	        	 String uid = (String)map2.get("uid");
				 String qc_task_id = (String)map2.get("qc_task_id");
				 String qc_user_id = (String)map2.get("qc_user_id");
				 String user_Task_status = (String) map2.get("user_Task_status");
				 
					IA_Users ia_Users = new IA_Users();
					 ia_Users.setUid(uid);
					 ia_Users.setQc_task_id(qc_task_id);
					 ia_Users.setQc_user_id(qc_user_id);
					 ia_Users.setUser_Task_status(user_Task_status);
					 
						//根据用户id查出user信息！
						/*DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(qc_user_id);
						if(userInfoByUserId != null){
							String mc2 = userInfoByUserId.getMc();
							 ia_Users.setMc(mc2);
						}*/
						
						
					
						 
						  list2.add(ia_Users);
						  
						  imp_qc_task.setIA_User(list2);
							 
							 
							 
							 
			}
	         list.add(imp_qc_task);
		}
		return  list;
		
	}
	
	//删除清查任务
	public static int deleteInventory(String dbpath,String taskid){
	    String delSql="DELETE from imp_qc_task where imp_qc_task.id ='"+taskid+"'";
	    String delSqluser="DELETE from imp_qc_users where imp_qc_users.qc_task_id ='"+taskid+"'";
		try {
			SqliteHelper sqliteHelper = new SqliteHelper(dbpath);
				int executeUpdate = sqliteHelper.executeUpdate(delSql);
				int executeUpdate2 = sqliteHelper.executeUpdate(delSqluser);
				sqliteHelper.destroyed();
				if(executeUpdate !=0 && executeUpdate2 != 0){
					return 1;
				}else{
					return 0;
				}
			
		} catch (Exception e) {
			return 0;
		}
		
	} 
	
	
   //下发清查任务
	public static List<JqAssetAccount> getJqAssetAccount(String dbpath,String QcTaskId,String userid) throws ClassNotFoundException, SQLException{
		
		
		//请求下发时 就修改 状态
		//下发时根据qctaskid 将 qctask 表的状态改成 1;
		String updateSql = null;
		updateSql ="update imp_qc_task set task_status = 1 where projectId = '"+QcTaskId+"'";
		
		String updateUser = null;            
		updateUser ="update imp_qc_users set user_Task_status = 1 where qc_user_id = '"+userid+"'";
		
		  List<JqAssetAccount> list = new ArrayList<JqAssetAccount>();
		
		  String selectSql = null;
		  
		  selectSql = "SELECT id,fileId,assetBarcode,assetName,sl,assetCwrzrq,assetPp,assetModel,money,belongCompany,saveAddress,depreciateStatus FROM imp_jq_asset";
		  if(dbpath != null){
			  SqliteHelper sqliteHelper = new SqliteHelper(dbpath);
			  List<Map<String, Object>> executeQuery = sqliteHelper.executeQuery(selectSql);
			  
			  //改成 1;
			  int executeUpdate = sqliteHelper.executeUpdate(updateSql);
			  int executeUser = sqliteHelper.executeUpdate(updateUser);
			  
			  for (Map<String, Object> map : executeQuery) {
				  String id = (String) map.get("id");
				  String fileId = (String) map.get("fileId");
				  String assetBarcode = (String) map.get("assetBarcode");
				  String assetName = (String) map.get("assetName");
				  Integer sl = (Integer) map.get("sl");
				  
				  String date =  (String) map.get("assetCwrzrq");
				  Date assetCwrzrq = CommonUtils.convertStringToDate(date);
				  
				  String assetPp = (String) map.get("assetPp");
				  String assetModel = (String) map.get("assetModel");
				  Object money =  (Object) map.get("money");
				  Double parseDouble = Double.parseDouble(money.toString());
				  
				  String belongCompany = (String) map.get("belongCompany");
				  String saveAddress = (String) map.get("saveAddress");
				  String depreciateStatus = (String) map.get("depreciateStatus");
				  
				  JqAssetAccount jqAssetAccount = new JqAssetAccount();
				  jqAssetAccount.setId(id);
				  jqAssetAccount.setFileId(fileId);
				  jqAssetAccount.setAssetBarcode(assetBarcode);
				  jqAssetAccount.setAssetName(assetName);
				  jqAssetAccount.setSl(sl);
				  jqAssetAccount.setAssetCwrzrq(assetCwrzrq);
				  jqAssetAccount.setAssetPp(assetPp);
				  jqAssetAccount.setAssetModel(assetModel);
				  jqAssetAccount.setMoney(parseDouble);
				  jqAssetAccount.setBelongCompany(belongCompany);
				  jqAssetAccount.setSaveAddress(saveAddress);
				  jqAssetAccount.setDepreciateStatus(depreciateStatus);
				  list.add(jqAssetAccount);
			}
			  return  list;
		  }else{
			  return null; 
		  }
		
	}

   //上传清查任务！存入sqllite表中
	public static int UploadInventoryTask(String proId, SwAssetAccount SW) throws Exception{
		
		
		String dbpath = Utils.dbPath+proId+".db";
		
		List<String> listSql = new ArrayList<String>();
		List<Object[]> listObj = new ArrayList<Object[]>();
		
			 
			String insertIAinfo = "insert into imp_sw_asset(id,taskId,projectId,assetName,sl,get_date,assetPp,assetModel,useDept,useUser,saveAddress,pictureId) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			
			Object[] proFileInfoObj = {SW.getId(),SW.getTaskId(),proId,SW.getAssetName(),
					SW.getSl(),SW.getGetDate(),SW.getAssetPp(),SW.getAssetModel(),SW.getUseDept(),SW.getUseUser(),SW.getSaveAddress(),SW.getPictureId()};
			
			listSql.add(insertIAinfo);
			listObj.add(proFileInfoObj);
			
			
			SqliteHelper sqliteHelper = new SqliteHelper(dbpath);
			
			sqliteHelper.executeSqlTrans(listSql, listObj);
			sqliteHelper.destroyed();
			
			return 1;
			
		   
	};
}
