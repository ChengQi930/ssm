package cn.bizowner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.dto.Dto;



@Mapper
public interface ColumnNameMapper {

		public String getColumnNameByFieldName(@Param("type")Integer type,@Param("fieldName")String fieldName);

		public List<Dto> getJqSwCommonField();


}
