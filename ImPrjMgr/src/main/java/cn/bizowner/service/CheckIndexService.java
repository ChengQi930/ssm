package cn.bizowner.service;
import java.util.List;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.CheckIndex;

public interface CheckIndexService {
	 
	public List<CheckIndex> getList();
	
	public int addCheckIndex(CheckIndex checkIndex);
	 
	public int updateCheckIndex(CheckIndex checkIndex);
	
	public int delCheckIndex(String id);
	
	public CheckIndex getCheckIndexInfoById(String id);
}
