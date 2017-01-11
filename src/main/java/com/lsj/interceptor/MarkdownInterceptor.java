package com.lsj.interceptor;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lsj.util.FileUtil;

public class MarkdownInterceptor implements HandlerInterceptor {

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		String blogRootPath = (String) request.getAttribute("blogRootPath");
		String mdName = request.getParameter("md");
		mdName = mdName==null?"/":mdName;
		if(!mdName.startsWith("/")){
			mdName = "/" + mdName;
		}
		
		String fileName = mdName.substring(mdName.lastIndexOf("/"));
		String mdFileName = fileName+".md";
		String htmlFileName = fileName+".html";
		
		File htmlFile = new File(String.format("%s/%s/%s", blogRootPath, request.getParameter("md")+".md", htmlFileName));
		File mdFile =  new File(String.format("%s/%s/%s", blogRootPath, request.getParameter("md")+".md", mdFileName));
		
		if(!(mdFile.exists() || htmlFile.exists())){
			//mdfile和htmlfile都没有的情况下，是肯定显示不出博客
			request.getSession(true).setAttribute("inform", "博客不存在");
			request.getRequestDispatcher("redirectview.do").forward(request, response);
			return false;
		}
		
		if(!htmlFile.exists()){
			FileUtil.Md2HtmlAsFile(mdFile, htmlFile);
		}
		
		return true;
	}
}
