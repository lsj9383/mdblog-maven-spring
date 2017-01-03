package com.lsj.mvc.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsj.util.FileUtil;

@Controller
public class MarkdownController {
	@RequestMapping("/md")
	public String md(HttpServletRequest request){
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		
		String mdFileName = request.getParameter("md")+".md";
		String htmlFileName = mdFileName.replaceAll(".md", ".html");
		
		try {
			File htmlFile = new File(String.format("%s/%s/%s", blogRoot, mdFileName,htmlFileName));
			File mdFile =  new File(String.format("%s/%s/%s", blogRoot, mdFileName,mdFileName));
			
			if(mdFile.exists() || htmlFile.exists()){			//md文件存在  或 html文件存在
				if(!htmlFile.exists()){		//html文件不存在, 将markdown转换为markdown文件
					mdFile =  new File(String.format("%s/%s/%s", blogRoot, mdFileName,mdFileName));
					FileUtil.Md2HtmlAsFile(mdFile, htmlFile);
				}
				return "mdview";
			}else{
				throw new Exception("not find mdfile or htmlfile");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession(true).setAttribute("inform", "博客不存在");
			return "redirectview";
		}
	}
}
