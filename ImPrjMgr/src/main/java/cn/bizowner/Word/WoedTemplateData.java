package cn.bizowner.Word;

import java.sql.PseudoColumnUsage;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.tools.ant.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.service.impl.ProjectServiceImpl;


public class WoedTemplateData {
	
	
     public static LinkedHashMap<Object, Object> WordMap(HttpServletRequest request) {
    	  LinkedHashMap<Object, Object> hashMap = new LinkedHashMap<>();
    	  
     String attribute = (String) request.getSession().getAttribute("proId");
     
//    	  hashMap.put("Head", );
    	  
		return hashMap;
 }
}
