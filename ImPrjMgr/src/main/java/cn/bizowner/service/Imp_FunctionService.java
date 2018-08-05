package cn.bizowner.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.bizowner.model.Imp_Function;


public interface Imp_FunctionService {
	/*public int save (Imp_Function function);
    public int delete (String functionid);
    public int update (Imp_Function function);*/
    public List<Imp_Function> find(String node) throws Exception;
    public List<Imp_Function> findId (String functionid);
}
