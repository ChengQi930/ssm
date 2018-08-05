package cn.bizowner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.dto.Dto;
import cn.bizowner.model.ColumnSelect;



@Mapper
public interface ColumnSelectMapper {
	

	public List<Dto> getHeadInfo(@Param("userId")String userId,@Param("type")Integer type);
	
	
	public int deleteColumnSelect(@Param("userId")String userId,@Param("type")Integer type);
	
	
	public int addColumnSelect(@Param("columnSelect")ColumnSelect columnSelect);
	
	
	public int isSetColumnSelect(@Param("userId")String userId,@Param("type")Integer type);
}
