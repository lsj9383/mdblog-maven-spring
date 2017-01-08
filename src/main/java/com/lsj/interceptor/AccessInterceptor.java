package com.lsj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AccessInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exc)
			throws Exception {
		System.out.println("after");

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
		System.out.println("post");

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

		String access = (String) request.getSession(true).getAttribute("access");
		
		if(access != null){
			return true;
		}else{
			//记录当前访问的uri,以便在登录成功后继续访问。
			if(request.getRequestURI().endsWith("dir.do")
			|| request.getRequestURI().endsWith("uploadview.do")){
				request.getSession(true).setAttribute("svuri", request.getRequestURI());
			}else{
				request.getSession(true).setAttribute("svuri", "/springmdblog/pad.do");
			}
			response.sendRedirect("/springmdblog/loginview.do");		//由于这个地址是由浏览器解析的，也就是说这个路径是前端路径，因此需要写明webapp-name:mdblog
			return false;
		}
	}
}
