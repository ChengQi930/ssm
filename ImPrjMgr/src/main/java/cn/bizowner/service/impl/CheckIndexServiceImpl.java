package cn.bizowner.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.mapper.CheckIndexMapper;
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.mapper.TopicMapper; 
import cn.bizowner.model.ChangeLog;
import cn.bizowner.model.CheckIndex;
import cn.bizowner.model.CwRelaJq;
import cn.bizowner.model.JqRelaSw;
import cn.bizowner.model.TopicMes;
import cn.bizowner.service.CheckIndexService;
import cn.bizowner.service.CheckService;
import cn.bizowner.service.UpdateAccService;
import cn.bizowner.sqlite.ChangeLogUtil;
import cn.bizowner.sqlite.CheckUtil;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.sqlite.JqAccountUtil;
import cn.bizowner.sqlite.SqliteHelper;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.Utils;



@Service("checkIndexService")
public class CheckIndexServiceImpl implements CheckIndexService {

	
	@Autowired
	private CheckIndexMapper checkIndexMapper;
	
	@Override
	public List<CheckIndex> getList() {
		
			return checkIndexMapper.getList();
		
	}

	@Override
	public int addCheckIndex(CheckIndex checkIndex) {
		 
		return checkIndexMapper.addCheckIndex(checkIndex);
	}

	@Override
	public int updateCheckIndex(CheckIndex checkIndex) {
 		
		return checkIndexMapper.updateCheckIndex(checkIndex);	
	}

	@Override
	public int delCheckIndex(String id) {
		
			return checkIndexMapper.delCheckIndex(id);
	}

	@Override
	public CheckIndex getCheckIndexInfoById(String id) {
		
			return checkIndexMapper.getInfoById(id);
		
	}
 
	
	 
}
