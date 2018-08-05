package cn.bizowner.service;
import java.util.List;

import cn.bizowner.dto.Dto;

public interface CheckService {
	 
	public void jqRelaSwMulToMul(String userId,String mc,String proId,String jqId,String swId) throws Exception;
	
	public Dto trmtJqRelaSw(String userId,String mc,String proId,String id,Integer type) throws Exception;
	
	public boolean isMulJqRelaSw(String dbPath,String id, Integer type) throws Exception;
	
	
	
	public void cwRelaJqMulToMul(String userId,String mc,String proId,String cwId,String jqId) throws Exception;
	
	public Dto trmtCwRelaJq(String userId,String mc,String proId,String id,Integer type) throws Exception;
	
	public boolean isMulCwRelaJq(String dbPath,String id, Integer type) throws Exception;


	
	public boolean isHavePerm(String proId,String userId,Integer type) throws Exception;
	
	
	public Dto suggCheckJqRelaSw(String proId,String checkIndexId,String userId,String jqRelaSwFileId) throws Exception;
}
