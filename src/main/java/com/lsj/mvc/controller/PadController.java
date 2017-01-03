package com.lsj.mvc.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PadController {
	
	@RequestMapping("/pad")
	public String pad(HttpServletRequest request, Model mv){
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		
		File rootBlog = new File(blogRoot);
		if(rootBlog.exists()){
			//筛选出博客
			File[] blogs = rootBlog.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".md");
				}
			});
			
			//按时间顺序
			Arrays.sort(blogs, new Comparator<File>(){
				public int compare(File f1, File f2) {
					long diff = f2.lastModified()-f1.lastModified();
					return (int)diff;
				}
			});
			
			mv.addAttribute("blogs", blogs);
		}else{
			mv.addAttribute("blogs", null);
		}
		return "padview";
	}
}
