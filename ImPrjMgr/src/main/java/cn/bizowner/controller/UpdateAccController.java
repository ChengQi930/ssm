package cn.bizowner.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto; 
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.model.JqAssetAccount;
import cn.bizowner.service.UpdateAccService;
import cn.bizowner.sqlite.DbUtil;
import cn.bizowner.utils.CommonUtils;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.Utils;


@RestController
//@Controller
@RequestMapping("/UpdateAcc")
public class UpdateAccController {

		
	
	@Autowired
	private UpdateAccService updateAccService;
	
	
	
	
	@RequestMapping(value="/Test",method=RequestMethod.GET)  
    public @ResponseBody String Test()
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		String dataType = null;
				
		try
		{
			dataType = DbUtil.getDataType("imp_change_log", "id");
			
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", dataType);
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
