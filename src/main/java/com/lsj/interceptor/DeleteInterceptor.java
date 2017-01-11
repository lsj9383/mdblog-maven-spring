package com.lsj.interceptor;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.util.FileUtil;

public class DeleteInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		String blogRoot = (String) request.getAttribute("blogRootPath");
		String fileName = request.getParameter("file");
		String dirName = FileUtil.ParentName(fileName);
		
		if(fileName==null){
			request.getSession(true).setAttribute("inform", "未指定文件");
			request.getRequestDispatcher("/redirectview.do").forward(request, response);
			return false;
		}
		
		String fileStr = blogRoot + fileName;
		
		File file = new File(fileStr);
		if(file.exists()){
			request.setAttribute("file", file);
			request.setAttribute("dirName", dirName);
			return true;
		}else{
			request.getSession(true).setAttribute("inform", "文件不存在");
			request.getRequestDispatcher("/redirectview.do").forward(request, response);
			return false;
		}
	}
}
