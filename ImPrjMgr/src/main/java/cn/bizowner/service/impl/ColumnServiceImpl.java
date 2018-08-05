package cn.bizowner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.dto.Dto;
import cn.bizowner.mapper.ColumnNameMapper;
import cn.bizowner.mapper.ColumnSelectMapper;
import cn.bizowner.mapper.CommonMapper;
import cn.bizowner.model.ColumnSelect;
import cn.bizowner.service.ColumnService;
import cn.bizowner.service.CommonService;



@Service("columnService")
public class ColumnServiceImpl implements ColumnService {
 
	@Autowired
	private ColumnSelectMapper columnSelectMapper;
	
	@Autowired
	private ColumnNameMapper columnNameMapper;

	@Override
	public void saveColumnSelect(List<ColumnSelect> listColumnSelect) throws Exception{
		
				if(listColumnSelect != null && listColumnSelect.size()>0)
				{
							String userId = listColumnSelect.get(0).getUserId();
							Integer type = listColumnSelect.get(0).getType();
							
							int ret = columnSelectMapper.deleteColumnSelect(userId, type);
							
							if(ret < 0)
							{
										throw new Exception("delete error");
							}
							else
							{
									for(int i=0;i<listColumnSelect.size();i++)
									{
										ret = columnSelectMapper.addColumnSelect(listColumnSelect.get(i));
										if(ret != 1)
										{
												throw new Exception("insert error");
										}
									}
							}
					
				}
	}

	@Override
	public List<Dto> getJqSwCommonField() throws Exception {
		  
		 	return columnNameMapper.getJqSwCommonField();
	}
	
	
	
}
