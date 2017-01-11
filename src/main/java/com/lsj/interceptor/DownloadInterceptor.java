package com.lsj.interceptor;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DownloadInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		String blogRootPath = (String) request.getAttribute("blogRootPath");
		String fileName = request.getParameter("file");
		File file = new File(blogRootPath + fileName);
		
		if(!file.isFile()){
			request.getSession(true).setAttribute("inform", "文件错误,无法下载");
			request.getRequestDispatcher("redirectview.do").forward(request, response);
			return false;
		}
		
		request.setAttribute("file", file);
		return true;
	}
}
