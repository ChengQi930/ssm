package cn.bizowner.utils;

import java.util.ArrayList;
import java.util.List;

import cn.bizowner.model.DownFileInfo;
import cn.bizowner.model.OutDataInfo;


public class Utils {
 
		public static List<DownFileInfo> listDownFileInfo = new ArrayList<DownFileInfo>();
	
		public static List<OutDataInfo> listDataInfo = new ArrayList<OutDataInfo>();
	
		/*public static String filePath = "E:\\temp\\file\\";
	 
		public static String webRootPath = "E:\\";
		
		public static String uploadPath = webRootPath+"temp\\upload\\";
		
		
		public static String exportPath = "E:\\temp\\export\\";
		 
		public static String dbPath = "E:\\temp\\db\\";*/
		
		
		
		
		public static String filePath = "D:\\temp\\file\\";
		 
		public static String webRootPath = "D:\\";
		
		public static String uploadPath = webRootPath+"temp\\upload\\";
		 
		public static String exportPath = "D:\\temp\\export\\";
		 
		public static String dbPath = "D:\\temp\\db\\";
		
		
		
		
		public static int Project_Status_Main_Running = 1;	//进行中
		public static int Project_Status_Main_Finish = 2;	//已完成
		public static int Project_Status_Main_Stop = 3;		//已终结
		
		  
		public static int Project_Status_Sub_Running_Normal=1;	//正常进行中
		public static int Project_Status_Sub_Running_Exceed=2;	//超期进行中
		public static int Project_Status_Sub_Finish_Advance=3;	//提前完成
		public static int Project_Status_Sub_Finish_Normal=4;	//正常完成
		public static int Project_Status_Sub_Finish_Exceed=5;	//超期完成
		public static int Project_Status_Sub_Stop=6;			//终止的
		
		
		public static int Plan_Status_New = 1;		//新建
		public static int Plan_Status_Running = 2;	//进行中
		public static int Plan_Status_Exceed = 3;   //超期
		public static int Plan_Status_Finish = 4;	//已完成
		
		
		/*public static int OperType_Add=1;				//添加
		public static int OperType_Update=2;			//修改
		public static int OperType_Split=3;				//拆分
		public static int OperType_Rela_CwJq=4;			//关联(财务久其)
		public static int OperType_Rela_JqSw=5;			//关联(久其实物)
*/		
		
		
		
		
		
		
		public static int AccountType_Detail_Cw=1;			//财务帐
		public static int AccountType_Detail_Jq_RelaCw=2;	//久其帐(与财务对账)
		public static int AccountType_Detail_Jq_RelaSw=3;	//久其帐(与实务对账)
		public static int AccountType_Detail_Sw=4;			//实物帐
		
		
		
		public static int AccountType_Cw=1;					//财务帐
		public static int AccountType_Jq=2;					//久其帐 
		public static int AccountType_Sw=3;					//实物帐
		
		
		
		
		public static int UpdateType_All=1;			//全部更新
		public static int UpdateType_Append=2;		//追加更新
		
		
		/*public static int Log_AccountType_Cw=1;				//财务帐日志
		public static int Log_AccountType_JqRelaCw=2;		//久其关联财务日志
		public static int Log_AccountType_JqRelaSw=3;		//久其关联实物日志
		public static int Log_AccountType_Sw=4;				//实物帐日志
*/		
		
		
		
		/*public static int Log_OperType_Add_Jq=1;					//添加
		public static int Log_OperType_Add_Sw=2;					//添加
		public static int Log_OperType_Modify_Jq=3;					//修改
		public static int Log_OperType_Modify_Sw=4;					//修改
		public static int Log_OperType_CwRelaJq=5;					//久其和财务关联
		public static int Log_OperType_JqRelaSw=6;					//久其和实物关联
		public static int Log_OperType_TrmtCwRelaJq=7;				//解关联久其和财务
		public static int Log_OperType_TrmtJqRelaSw=8;    			//解关联久其和实物
		public static int Log_OperType_Split=9;						//拆分
*/		
		
		
		
		
		public static int Log_OperType_Add=1;						//添加
		public static int Log_OperType_Modify=2;					//修改
		public static int Log_OperType_CwRelaJq=3;					//久其和财务关联
		public static int Log_OperType_JqRelaSw=4;					//久其和实物关联
		public static int Log_OperType_TrmtCwRelaJq=5;				//解关联久其和财务
		public static int Log_OperType_TrmtJqRelaSw=6;    			//解关联久其和实物
		public static int Log_OperType_Split=7;						//拆分
		
		
		 
		
		public static int Mes_OperType_Add=1;					//添加
		public static int Mes_OperType_Modify=2;				//修改
		public static int Mes_OperType_Rela=3;					//关联
		public static int Mes_OperType_Trmt=4;					//解关联 
		public static int Mes_OperType_Split=5;					//拆分
		 
}
