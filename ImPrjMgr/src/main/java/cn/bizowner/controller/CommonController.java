package cn.bizowner.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.model.Node;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.utils.JSONHelper;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.Utils;


@Controller
@RequestMapping(value = "/Common")
public class CommonController {

	
	
	@RequestMapping("/GetUpdateProgress")
	public @ResponseBody String GetUpdateProgress(HttpServletRequest request,String id) 
	{
			
			String result =null;
			
			
			boolean flag = true;
			String  errMess = "";
			
			Dto outDto = new BaseDto();
			
			Dto dto = new BaseDto();
		
			try
			{
				
				SingleList singlelist = SingleList.getInstance();
				
				Node node = singlelist.search(id);
				ProgressInfo processInfo = node.getProgressInfo();
				
				
				boolean finishFlag = processInfo.isFinishFlag();
				dto.put("finishFlag", finishFlag);
				if(finishFlag == false)
				{
					dto.put("progress", processInfo.getProgress());
				}
				else
				{
					boolean updateFlag = processInfo.isUpdateFlag();
					dto.put("updateFlag", updateFlag);
					if(updateFlag == false)
					{
						dto.put("error", processInfo.getError());
					}
				}
				
			}
			catch(Exception ex)
			{
				errMess = ex.getMessage();
				flag = false;
			}
			
			if(flag == true)
			{
				outDto.put("flag", true);
				outDto.put("result", dto);
			}
			else
			{
				outDto.put("flag", false);
				outDto.put("errmsg", errMess);
			}			
			
			
			result = JSONHelper.encodeObject2JsonWithNull(outDto);
			
			return result;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/ExportFile",method=RequestMethod.GET)  
    public void ExportFile(String id,HttpServletRequest request,HttpServletResponse response)
	{  
		
		
		InputStream bis = null;
		
		String outFileName = null;
		String filePath = null;
		
		try
		{
			
			
					boolean findFlag = false;
				
					for(int i=0;i<Utils.listDownFileInfo.size();i++)
					{
								String tempId = Utils.listDownFileInfo.get(i).getId();
								if(id != null && id.equals(tempId))
								{
									outFileName = Utils.listDownFileInfo.get(i).getOutFileName();
									filePath = Utils.listDownFileInfo.get(i).getFilePath();
									
									Utils.listDownFileInfo.remove(i);
									
									findFlag = true;
									
									break;
								}
					}
					
					
					if(findFlag == true)
					{
						 //获取输入流  
				        bis = new BufferedInputStream(new FileInputStream(new File(filePath)));  
				        //假如以中文名下载的话  
				        //String filename = "下载文件.txt";  
				        //转码，免得文件名中文乱码  
				        //String filename = URLEncoder.encode(assetAccountfileName,"UTF-8");  
				        
				        String agent = (String)request.getHeader("USER-AGENT"); 
						String filename = "";
						if(agent != null && agent.indexOf("Firefox") != -1) 
						{
							filename = new String(outFileName.getBytes("utf-8"), "ISO_8859_1"); 
						}
						else
						{
							filename =  URLEncoder.encode(outFileName, "UTF-8");
						}
				        
				        //String filename= new String(assetAccountfileName.getBytes("utf-8"), "ISO_8859_1"); 
				        //设置文件下载头  
				        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
				        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
				        response.setContentType("multipart/form-data");   
				        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
				        int len = 0;  
				        while((len = bis.read()) != -1){  
				            out.write(len);  
				            out.flush();  
				        }  
				        out.close();  
				        
				        if(bis != null)		bis.close();
					}
					
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		finally
		{
				
				
				if(bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(filePath != null)
				{
						File file = new File(filePath);
						if(file.exists())
						{
								file.delete();
						}
				}
				
				
		}
    } 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/GetData",method=RequestMethod.GET)  
    public @ResponseBody String GetData(String id)
	{  
		String result =null;
		
		boolean flag = false;
		
		String  errMess = "";	
		
		Object o = null;
		
		Dto outDto = new BaseDto();
		
		try
		{
					for(int i=0;i<Utils.listDataInfo.size();i++)
					{
								String tempId = Utils.listDataInfo.get(i).getId();
								if(id != null && id.equals(tempId))
								{ 
									o = Utils.listDataInfo.get(i).getO();
									
									Utils.listDataInfo.remove(i);
									
									flag = true;
									
									break;
								}
					}
		} 
		catch(Exception ex)
		{
			flag = false;
			errMess = ex.getMessage();
		}	
		
		if(flag == true)
		{
			outDto.put("flag", true);
			outDto.put("result", o);
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
