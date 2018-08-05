package cn.bizowner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.CheckIndex;



@Mapper
public interface CheckIndexMapper { 
	
	public int addCheckIndex(@Param("checkIndex")CheckIndex checkIndex);
	
	public int updateCheckIndex(@Param("checkIndex")CheckIndex checkIndex);
	
	public List<CheckIndex> getList();
	
	public  CheckIndex  getInfoById(@Param("id")String id);
	
	public int delCheckIndex(@Param("id")String id);
}

