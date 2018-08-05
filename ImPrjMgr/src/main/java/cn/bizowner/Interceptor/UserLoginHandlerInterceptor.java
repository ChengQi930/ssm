package cn.bizowner.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.bizowner.dto.Dto;
import cn.bizowner.dto.impl.BaseDto;
import cn.bizowner.utils.JSONHelper;


@Component
public class UserLoginHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		
		String url = request.getRequestURI();
		
		System.out.println(url);
		
		if(url.indexOf("/User/GetLoginInfo")>=0 || url.indexOf("/User/GetCode")>=0 || url.indexOf("/User/Test")>=0 ||  url.indexOf("/User/SetValue")>=0 ||   url.indexOf("/User/SetUser")>=0  || url.indexOf("/AssetMgr/Test")>=0)	
		{	
					return true;
		}
		
		
		HttpSession session  = request.getSession();
		String userId = (String) session.getAttribute("userId");
		if(userId == null)
		{
			response.setStatus(999);
			
			Dto outDto = new BaseDto();
			outDto.put("flag", false);
			outDto.put("error", "Session expired");
			
			response.getWriter().write(JSONHelper.encodeObject2Json(outDto));
			
			return false;
		}
		
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("HandlerInterceptor1...postHandle");

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("HandlerInterceptor1...afterCompletion");

	}

}
