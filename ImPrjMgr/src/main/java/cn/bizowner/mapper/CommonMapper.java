package cn.bizowner.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface CommonMapper {
	public String getTopParentStdCodeByAssetClassName(@Param("assetClassName")String assetClassName);
}
