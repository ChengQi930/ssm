package cn.bizowner.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON; 
import com.alibaba.fastjson.JSONObject;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.ColumnSelect; 
import cn.bizowner.service.ColumnService; 
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.JSONHelper; 


@RestController
//@Controller
@RequestMapping("/Column")
public class ColumnController {
 
	
	@Autowired
	private ColumnService columnService;
	
	
	
	@RequestMapping("/Test")
	public ModelAndView test()
	{
		
			ModelAndView result = new ModelAndView("file");
			return result;
	}
	
	
	
	@RequestMapping("/SaveColumnSelect")  
    public @ResponseBody String SaveColumnSelect(HttpServletRequest request,String selectStr,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
			
		
		
		try
		{
			
			//String userId = "001";
			
			
			HttpSession httpSesion =  request.getSession();
			String userId = httpSesion.getAttribute("userId").toString();
			
			
			List<ColumnSelect> listColumnSelect = new ArrayList<ColumnSelect>();
			
			JSONObject jsonObj = JSON.parseObject(selectStr);
	        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
	            System.out.println(entry.getKey() + ":" + entry.getValue());
	            
	            String key = entry.getKey().toString();
	            String value = entry.getValue().toString();
	            
	            ColumnSelect columnSelect = new ColumnSelect();
	            
	            columnSelect.setId(CommonUtils.getUUID());
	            columnSelect.setUserId(userId);
	            columnSelect.setType(type);
	            columnSelect.setFieldName(value);
	            columnSelect.setOrder(Integer.parseInt(key));
	            
	            
	            listColumnSelect.add(columnSelect);
	        }
	        	
	        
	        columnService.saveColumnSelect(listColumnSelect);
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/GetJqSwCommonField")  
    public @ResponseBody String GetJqSwCommonField()
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
			
		List<Dto> listDto = null;
		
		try
		{   
	        listDto = columnService.getJqSwCommonField();
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result",listDto);
		}
		else
		{
			outDto.put("flag", false);
			outDto.put("errmsg", errMess);
		}			
		
		
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		
		return result;
		
    }
	
	
}
