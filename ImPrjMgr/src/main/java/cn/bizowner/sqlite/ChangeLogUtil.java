package cn.bizowner.sqlite;
 
import java.util.List; 

import cn.bizowner.model.ChangeLog; 

public class ChangeLogUtil {

	 
	
	public static void insertChangeLog(ChangeLog changeLog,List<String> listSql,List<Object[]> listObj) throws Exception
	{
		String insertLogInfo = "insert into imp_change_log(id,accountType,assetId,columnName,data1,data2,changeTime,userId,operType) values (?,?,?,?,?,?,?,?,?)";
		Object[] logObj = {changeLog.getId(),changeLog.getAccountType(),changeLog.getAssetId(),changeLog.getColumnName(),changeLog.getData1(),
				changeLog.getData2(),changeLog.getChangTime(),changeLog.getUserId(),changeLog.getOperType()};
		 
		listSql.add(insertLogInfo);
		listObj.add(logObj);
	}
	 
}
