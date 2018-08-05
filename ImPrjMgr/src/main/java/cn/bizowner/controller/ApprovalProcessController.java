package cn.bizowner.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.util.IdentityHashMap;

import cn.bizowner.dingtalk.openapi.env.Env;
import cn.bizowner.dingtalk.openapi.helper.AuthHelper;
import cn.bizowner.dingtalk.openapi.helper.DeptHelper;
import cn.bizowner.dingtalk.openapi.helper.ExamineHelper;
import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.Approval;
import cn.bizowner.model.DeptInfo;
import cn.bizowner.model.DingDingUserInfo;
import cn.bizowner.model.Plan;
import cn.bizowner.model.PlanQueryInfo;
import cn.bizowner.model.PlanUser;
import cn.bizowner.model.ProjectInfo;
import cn.bizowner.model.ProjectPlanData;
import cn.bizowner.model.ProjectPlanList;
import cn.bizowner.model.UserDept;
import cn.bizowner.service.PlanService;
import cn.bizowner.service.impl.ApprovalServiceImpl;
import cn.bizowner.service.impl.PlanServiceImpl;
import cn.bizowner.service.impl.ProjectServiceImpl;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.DateCalculate;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.TimeUtil;
import freemarker.core.ReturnInstruction.Return;

@RestController
@RequestMapping("/ApprovalProcess")
public class ApprovalProcessController {
        
	@Autowired
	private ProjectServiceImpl Project;
	
	@Autowired
	private PlanServiceImpl planservice;
	
	@Autowired
	private ApprovalServiceImpl approvalservice;
	
	
	
	
	//按planId查询审批项目状态
	@RequestMapping(value = "/GetApproval" ,method = RequestMethod.GET)
	public @ResponseBody String getApproval(String planId){
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		ArrayList<Approval> arrayList = new ArrayList<Approval>();
		try {
			ArrayList<Approval> approval = approvalservice.getApproval(planId);
			 for (Approval approval2 : approval) {
				 String applicantMc = approval2.getApplicant();
				 DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(approval2.getApplicant());//申请人
				 approval2.setApplicantMc(userInfoByUserId.getMc());
				 DingDingUserInfo ApproverMc = UserHelper.getUserInfoByUserId(approval2.getApprover());//审批人
				 approval2.setApproverMc(ApproverMc.getMc());
				 
				 arrayList.add(approval2);
			}
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	    	  outDto.put("result", arrayList); 
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	      }
		result = JSONHelper.encodeObject2Json(outDto, "yyyy-MM-dd");
		return result;
	}
	
	
	//查询所有审批项目的状态
	@RequestMapping(value = "/GetApprovalAll" ,method = RequestMethod.GET)
	public @ResponseBody String getApprovalAll(){
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		ArrayList<Object> arrayList = new ArrayList<>();
		try {
			List<Approval> approvalAll = approvalservice.getApproval(null);
			 for (Approval approval2 : approvalAll) {
				 String applicantMc = approval2.getApplicant();
				 DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(approval2.getApplicant());//申请人
				 approval2.setApplicantMc(userInfoByUserId.getMc());
				 DingDingUserInfo ApproverMc = UserHelper.getUserInfoByUserId(approval2.getApprover());//审批人
				 approval2.setApproverMc(ApproverMc.getMc());
				 arrayList.add(approval2);
			 }
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	    	  outDto.put("result", arrayList); 
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	      }
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	}
	
	
	/**
	 *    注：该接口为理想状态
	 * @param request    
	 * @param paramkey  审批的审批项目名称
	 * @param paramValue 审批的审批项目值
	 * @param type       项目计划
	 * @return
	 */
	@RequestMapping(value = "/ApprovalResults",method = RequestMethod.POST)
	public @ResponseBody String ApprovalProcess(HttpServletRequest request,String paramKey,String paramValue,String type){
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		try {
				String uuid = CommonUtils.getUUID();
				String accessToken = AuthHelper.getAccessToken();
				HashMap<String, String> param = new HashMap<String, String>();
				param.put(paramKey, paramValue);
				//审批人id
				String attribute = (String) request.getSession().getAttribute("proId");
				String approvers =  Project.getUserIdById(attribute);
				//发起者id;
				String attribute2 = (String) request.getSession().getAttribute("userId");
				//发起者的部门id
				DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(attribute2);
				List<UserDept> listDept = userInfoByUserId.getListDept();
				
				String id = null;
				for (UserDept deptInfo : listDept) {
					id = deptInfo.getDeptId();
				}
				//查看部门的所有用户id
				/*List<DingDingUserInfo> userInfoByDeptId = DeptHelper.getUserInfoByDeptId(id);
				for (DingDingUserInfo dingDingUserInfo : userInfoByDeptId) {
					System.out.println(dingDingUserInfo.getMc());
					System.out.println(dingDingUserInfo.getUserId());
				}*/
				//processInstanceId
				String sendExamine = ExamineHelper.sendExamine(accessToken, attribute2, Long.parseLong(id), approvers,param);
				
				if (sendExamine == null) {
					flag = false;
					
				} else {
					//先将值存进审批表中！
					Approval approval = new Approval();
					
					approval.setId(uuid);
					approval.setApplicant(attribute2); //发起人id
					approval.setApprover(approvers); //审批人id;
					approval.setStartTime(new Date()); //开始时间
					//结束时间就是请求结果以后修改的时间
					approval.setEnDofTime(null);
					//结果先为请求中！
					approval.setResults("审批中");
					approval.setProId(attribute); //项目id
					//项目计划Id;
					String endPlanId = planservice.getEndPlanId(attribute, type);
					approval.setPlanId(endPlanId);
					//描述等结果出来再修改
					approval.setDescribes(null);
					approval.setProcessInstanceId(sendExamine);
					
					int addApproval = approvalservice.AddApproval(approval);
					
					/*这里拿项目计划Id 作为订阅条件
					JMSConsumer.main(endPlanId);*/  
					
					//将申请存入库中后就定时的去遍历数据库
					//开辟一个线程 让不断的去循环数据库！
					/*SendMsgToMyThread send = MsgToMyThread;
			        Thread thread = new Thread(send);
					thread.start();*/
					
				}
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	    	  outDto.put("errmsg", errMess);
	      }
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	}
	
	/**
	 * 
	 *  不理想状态下 ！用户可以通过下次登陆时直接请求该接口  
	 * @param ProId 项目id
	 * @return
	 */
	@RequestMapping(value = "/TheQueryAgain",method = RequestMethod.GET)
    public @ResponseBody String getNotideal(HttpServletRequest request) {
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		ArrayList<Approval> unsatisfactory = null;
		try {
			String proId = (String) request.getSession().getAttribute("proId");
			 unsatisfactory = approvalservice.getUnsatisfactory(proId);
			 String applicant = null;//发起人id
			 String approver = null;//审批人id
			 for (Approval approval : unsatisfactory) {
				 applicant = approval.getApplicant();//发起人id
				 approver = approval.getApprover();//审批人id
			}
			 DingDingUserInfo userInfoByUserId = UserHelper.getUserInfoByUserId(applicant);
			 DingDingUserInfo userInfoByUserId2 = UserHelper.getUserInfoByUserId(approver);
			 
			 String applicantmc = userInfoByUserId.getMc();
			 String approvermc = userInfoByUserId2.getMc();
			
			 for (Approval approval : unsatisfactory) {
				approval.setApplicantMc(applicantmc);
				approval.setApproverMc(approvermc);
			}
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	    	  outDto.put("result", unsatisfactory); 
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	      }
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
    	
    }

	
	
	//批量 获取项目审批 阶段统计
	@RequestMapping(value = "/GetProjectPhase",method = RequestMethod.GET)
    public @ResponseBody String getProjectPhase(HttpServletRequest request,String size){
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		List<Object> arrayList = new ArrayList<>();
		try {
			 String proId = (String) request.getSession().getAttribute("proId");
			  String getproNo = Project.getproNo(proId);   //项目唯一标识
			//这里拿到的就是 指定项目 proNo 的所有阶段信息
			  LinkedHashMap<Object, Object> batchApplyFor = ExamineHelper.getBatchApplyFor(getproNo,size);
			  //有多少拿出多少值
			  for(Map.Entry<Object, Object> entry : batchApplyFor.entrySet()){
				   Object key = entry.getKey();  //阶段名称
				   Object value = entry.getValue(); // 结束时间
				   
		     //根据阶段名称 获取 对应阶段的数据
		     ArrayList<ProjectPlanList> planTitle = approvalservice.getPlanTitle(key.toString());
		      //走这里就是暂时第一个  planTitle 就是精确到了 某一个阶段
			   if(planTitle.size() != 0){
				   //一般只能是一个
				   for (ProjectPlanList projectPlanList : planTitle) {
					   Date startDate = projectPlanList.getStartDate();
					   if(startDate!=null){
						   int updateProjectDateNoNull = approvalservice.UpdateProjectDateNoNull(value.toString(), projectPlanList.getListType());
					   }else{
						   /*DateCalculate.dateToStrLong(startDate);*/
						   int updateProjectDate = approvalservice.UpdateProjectDate(value.toString(), value.toString(), projectPlanList.getListType());
					   }
				}
			   }	   
				   
			  }
			  LinkedHashMap<Object,Object> Identity = new LinkedHashMap<Object,Object>();
			  
			 //这里就是除了系统维护的4个阶段以外的 最后结果 
			  ArrayList<ProjectPlanList> projectPlan = approvalservice.getProjectPlan();
			  for (ProjectPlanList projectPlanList : projectPlan) {
				  Date startDate = projectPlanList.getEndDate();  //最后更新时间
				  String planTitle = null;
				   
				  Set<ProjectPlanData> projectPlan2 = projectPlanList.getProjectPlan();
				  if(startDate == null){
					   for (ProjectPlanData projectPlanData : projectPlan2) {
							  planTitle = projectPlanData.getPlanTitle();
						}
					   
					   Identity.put(planTitle, "");
				   }else{
					   String year = TimeUtil.getYearMonthDayHourMinuteSecond(startDate.getTime());
				   for (ProjectPlanData projectPlanData : projectPlan2) {
					  planTitle = projectPlanData.getPlanTitle();
				 }
				   
				   Identity.put(planTitle, year);   
				   }
				 
  
			}
			  
			  
			 //本系统维护的4条项目状态
			 HashMap<Object, Object> HashMap = new HashMap<>();
		       ArrayList<Plan> getproStatus = planservice.getproStatus(proId);
		          for (Plan plan : getproStatus) {
					 String type = plan.getType().toString();
					String planStatus = plan.getPlanStatus().toString();
					Identity.put(type, planStatus);
				}
		         
		  
		  arrayList.add(Identity);
			  
		} catch (Exception e) {
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true){
	    	  outDto.put("flag" , (boolean)true);
	    	  outDto.put("result",arrayList);
	      }else{
	    	  outDto.put("flag" , (boolean)false);
	      }
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
    }
} 
