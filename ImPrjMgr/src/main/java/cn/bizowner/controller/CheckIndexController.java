package cn.bizowner.controller; 
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;  
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto; 
import cn.bizowner.model.CheckIndex; 
import cn.bizowner.service.CheckIndexService; 
import cn.bizowner.utils.CommonUtils; 
import cn.bizowner.utils.JSONHelper; 


@RestController
@RequestMapping("/CheckIndex")
public class CheckIndexController {
 
	@Autowired
	private CheckIndexService checkIndexService;
	 
	
 	@RequestMapping(value = "/GetCheckIndexList", method = RequestMethod.GET)
	public @ResponseBody String GetCheckIndexList()
	{
		
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		
		List<CheckIndex> list = null;
		
		try 
		{
				list = checkIndexService.getList();
		} 
		catch (Exception e) 
		{
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true)
		{	
			outDto.put("flag",true);
			outDto.put("result",list);
		}
		else
		{
			outDto.put("flag",false);
			outDto.put("errmsg",errMess);
		}
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	}
 	
 	
 	 
 	
 	@RequestMapping(value = "/GetCheckIndexInfoById", method = RequestMethod.GET)
	public @ResponseBody String GetCheckIndexInfoById(String id)
	{
		
		String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		
		 CheckIndex  checkIndex = null;
		
		try 
		{ 
				checkIndex = checkIndexService.getCheckIndexInfoById(id);
		} 
		catch (Exception e) 
		{
			flag = false;
			errMess = e.getMessage();
		}
		if(flag == true)
		{	
			outDto.put("flag",true);
			outDto.put("result",checkIndex);
		}
		else
		{
			outDto.put("flag",false);
			outDto.put("errmsg",errMess);
		}
		result = JSONHelper.encodeObject2JsonWithNull(outDto);
		return result;
	}
	
 
	
		
		@RequestMapping(value = "/DelCheckIndex",method = RequestMethod.GET)
	    public @ResponseBody String DelCheckIndex(String id){
	    	
	    	String result =null;
			
	    	boolean flag = true;
			
	    	String  errMess = "";
			
	    	Dto outDto = new BaseDto();
	    	 
			try
			{
					checkIndexService.delCheckIndex(id);
			}
			catch(Exception ex)
			{
					String s = ex.getMessage();
					errMess = s;
			}
	    	 
	    	if(flag == true)
	    	{
   			  		outDto.put("flag", true);
   		    }
	    	else
	    	{
		   			outDto.put("flag", false);
		   			outDto.put("errmsg" ,errMess);
   		    }
	    	 
	    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
	 		
	    	return result;
	    }
		
	
	    
	    
	  @RequestMapping(value = "/UpdateCheckIndex" ,method = RequestMethod.POST)
      public @ResponseBody String UpdateCheckIndex(CheckIndex checkIndex)
	  {
	    	String result =null;
			boolean flag = true;
			String  errMess = "";
			Dto outDto = new BaseDto();
			 
			try 
			{ 
					checkIndexService.updateCheckIndex(checkIndex);
	    	} 
			catch (Exception e) 
			{
	    		flag = false;
				errMess = e.getMessage();
			}
			if(flag == true){
	    		outDto.put("flag", true);
	    	}else{
	    		outDto.put("flag", false);
	    		outDto.put("errmsg", errMess);
	    	}
	    	result = JSONHelper.encodeObject2JsonWithNull(outDto);
	 		return result;
 }
	  
	   
  
  
  
  @RequestMapping(value = "/AddCheckIndex" ,method = RequestMethod.POST)
  public @ResponseBody String AddCheckIndex(CheckIndex checkIndex)
  {
    	String result =null;
		boolean flag = true;
		String  errMess = "";
		Dto outDto = new BaseDto();
		 
		try 
		{  
			
			checkIndex.setId(CommonUtils.getUUID());
			  
			checkIndexService.addCheckIndex(checkIndex);
    	} 
		catch (Exception e) 
		{
    		flag = false;
			errMess = e.getMessage();
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

	
	
}
