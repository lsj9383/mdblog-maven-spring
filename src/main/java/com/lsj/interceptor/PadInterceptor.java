package com.lsj.interceptor;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PadInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		String part = request.getParameter("part");
		part = part==null?"/":part;
		
		if(!(part.endsWith(".part/") || part.endsWith(".part") || part.equals("/"))){
			//非分区路径错误
			request.getSession(true).setAttribute("inform", "路径非part分区");
			request.getRequestDispatcher("/redirectview.do").forward(request, response);
			return false;
		}
		
		String partRoot = request.getAttribute("blogRootPath")+part+"/";
		if(!new File(partRoot).exists()){
			//分区路径不存在
			request.getSession(true).setAttribute("inform", "该分类不存在");
			request.getRequestDispatcher("/redirectview.do").forward(request, response);
			return false;
		}
		
		return true;
	}
}
