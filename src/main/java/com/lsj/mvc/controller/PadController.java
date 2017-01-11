package com.lsj.mvc.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PadController {  
	
	@RequestMapping(value="/pad", method=RequestMethod.GET)
	public String pad(HttpServletRequest request, String part, Model mv){
		
		String partRoot = request.getAttribute("blogRootPath")+(part==null?"":part)+"/";
		File partRootFile = new File(partRoot);
		
		//筛选出分类
		File[] parts = partRootFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".part");
			}
		});
		
		//筛选出博客
		File[] blogs = partRootFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".md");
			}
		});
		
		//博客按时间顺序
		Arrays.sort(blogs, new Comparator<File>(){
			public int compare(File f1, File f2) {
				long diff = f2.lastModified()-f1.lastModified();
				return (int)diff;
			}
		});
		
		mv.addAttribute("blogs", blogs);
		mv.addAttribute("parts", parts);
		return "padview";
	}
}
