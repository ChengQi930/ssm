package cn.bizowner.test;

import java.util.ArrayList;
import java.util.List;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.JSONHelper;

public class TestDto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String taskId = CommonUtils.getUUID();
		
		QcTask qcTask = new QcTask();
		qcTask.setId(taskId);
		qcTask.setTaskName("123");
		
		
		List<QcUsers> qcUsers = new ArrayList<QcUsers>();
		
		QcUsers qcUsers1 = new  QcUsers();
		qcUsers1.setTaskId(taskId);
		qcUsers1.setId(CommonUtils.getUUID());
		qcUsers1.setUserId("1");
		
		
		QcUsers qcUsers2 = new  QcUsers();
		qcUsers1.setTaskId(taskId);
		qcUsers1.setId(CommonUtils.getUUID());
		qcUsers1.setUserId("2");
		
		
		qcUsers.add(qcUsers1);
		qcUsers.add(qcUsers2);
		
		
		qcTask.setListQcUser(qcUsers);
		
		
		
		String result = JSONHelper.encodeObject2JsonWithNull(qcTask);
		
		System.out.println(result);		
		
	}

}
