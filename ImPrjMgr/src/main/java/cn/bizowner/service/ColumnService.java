package cn.bizowner.service;

import java.util.List;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.ColumnSelect;

public interface ColumnService {
	
	public void saveColumnSelect(List<ColumnSelect> listColumnSelect) throws Exception;
	
	
	public List<Dto> getJqSwCommonField() throws Exception;
}
