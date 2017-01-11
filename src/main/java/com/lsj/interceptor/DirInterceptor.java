package com.lsj.interceptor;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DirInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		String blogRootPath = (String) request.getAttribute("blogRootPath");
		String dirStr = blogRootPath + request.getParameter("dir");
		
		if(request.getParameter("dir") == null){
			request.getRequestDispatcher("/dir.do?dir=/").forward(request, response);
		}else{
			File dir = new File(dirStr);
			if(!dir.isDirectory()){
				request.getSession(true).setAttribute("inform", "路径错误");
				request.getRequestDispatcher("/redirectview.do").forward(request, response);
				return false;
			}else{
				request.setAttribute("dir", dir);
			}
		}
		return true;
	}
}
