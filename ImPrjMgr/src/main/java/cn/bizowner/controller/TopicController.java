package cn.bizowner.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.service.TopicService;
import cn.bizowner.utils.JSONHelper;


@RestController
//@Controller
@RequestMapping("/Topic")
public class TopicController {

		
	
	@Autowired
	private TopicService topicService;
	
	
	@RequestMapping("/Test")
	public ModelAndView test()
	{
		
			ModelAndView result = new ModelAndView("file");
			return result;
	}
	
	
	
	@RequestMapping("/GetTopicId")  
    public @ResponseBody String GetTopicId(HttpServletRequest request,@RequestParam(value="type", required=false,defaultValue="1")Integer type)
	{  
		  	
		String result =null;
		
		boolean flag = true;
		String  errMess = "";
		
		Dto outDto = new BaseDto();
		
		String topicId = null;
			
		try
		{
			
			//String projectId = "a7c72ffc-1be1-4a09-bcad-3b7ea281547b";
			
			HttpSession httpSession = request.getSession();
			
			String proId = httpSession.getAttribute("proId").toString();
			
			topicId = topicService.getTopicId(proId, type);
	        	
		}
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", topicId);
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
