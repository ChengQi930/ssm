package cn.bizowner.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.bizowner.dingtalk.openapi.helper.UserHelper;
import cn.bizowner.model.DingDingUserInfo;

/**
 * 使用注解标注过滤器
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
 * 属性filterName声明过滤器的名称,可选
 * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 * 
 * @author   单红宇(365384722)
 * @myblog  http://blog.csdn.net/catoop/
 * @create    2016年1月6日
 */
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {

    @Override
    public void destroy() {
        //System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain chain) throws IOException, ServletException {
        //System.out.println("执行过滤操作");
        
       /* HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        
        String url = request.getRequestURI().toString();
        
        String remoteUrl = request.getRemoteAddr().toString();
        
        System.out.println("remoteUrl = "+remoteUrl);
        
        
        if(url.equals("/"))
        {
        	 chain.doFilter(servletRequest, servletResponse);
        }
        else if(url.equals("/main.html"))
        //else if(url.equals("/mainManager"))
        {
        			String code = request.getParameter("code");
        			
        			String userId= UserHelper.getUserId(code);
        
        			if(userId == null)
        			{
        				response.sendRedirect("http://127.0.0.1:8080");
        				//response.sendRedirect("http://192.168.1.66:9514/mainManager/");
        			}
        			else
        			{
        				System.out.println("userId="+userId);
            			
            			HttpSession httpSession = request.getSession();
            			
            			System.out.println("sessionId="+httpSession.getId());
            			            			
            			httpSession.setAttribute("userId", userId);
            			
            			chain.doFilter(servletRequest, servletResponse);
        			}
        }
        else
        {
        	HttpSession httpSession = request.getSession();
        	Object o = httpSession.getAttribute("userId");
        	if(o==null)
        	{
        		response.sendRedirect("http://127.0.0.1:8080");
        		//response.sendRedirect("http://192.168.1.66:9514/mainManager/");
        	}
        	else
        	{
        		chain.doFilter(servletRequest, servletResponse);
        	}
        }*/
      
        
        chain.doFilter(servletRequest, servletResponse);
       
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        //System.out.println("过滤器初始化");
    }

}