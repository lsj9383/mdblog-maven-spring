package com.lsj.mvc.controller;

import java.io.File;
import java.io.FileFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DirController {
	
	@RequestMapping("/dir")
	public String dir(HttpServletRequest request, HttpServletResponse response){
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		String dirStr = blogRoot + request.getParameter("dir");
		
		if(request.getParameter("dir") == null){
			request.getSession(true).setAttribute("inform", "路径为空");
			return "redirectview";
		}else{
			File dir = new File(dirStr);
			if(dir.isDirectory()){
				File[] dirList = dir.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.isDirectory();
					}
				});
				
				File[] fileList = dir.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname.isFile();
					}
				});
				request.setAttribute("dirList", dirList);
				request.setAttribute("fileList", fileList);
				return "dirview";
			}else{
				request.getSession(true).setAttribute("inform", "路径错误");
				return "redirectview";
			}
		}
	}
}
