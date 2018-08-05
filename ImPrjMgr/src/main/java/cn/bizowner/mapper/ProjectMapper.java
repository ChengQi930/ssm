package cn.bizowner.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.bizowner.model.ProjectInfo;


@Mapper
public interface ProjectMapper {
	
		public int addPro(@Param("projectInfo")ProjectInfo projectInfo);
		
		public int updatePro(@Param("projectInfo")ProjectInfo projectInfo);
		
		public int delPro(@Param("proId")String proId);
		
		public int getProjectCount(@Param("mainStatus")Integer mainStatus,@Param("subStatus")Integer subStatus,@Param("proName")String proName);

		public List<ProjectInfo> getProjectList(@Param("mainStatus")Integer mainStatus,@Param("subStatus")Integer subStatus,@Param("proName")String proName,@Param("start")Integer start,@Param("limit")Integer limit);
		
		public ProjectInfo getProjectInfoById(@Param("proId")String proId);
		
		 //查询审批人id
		public String getUserIdById(String proId) throws Exception;
		//根据proId获取proNo
		public String getproNo(@Param("proId") String proId);
}
