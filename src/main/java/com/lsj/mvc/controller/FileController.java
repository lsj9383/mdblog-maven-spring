package com.lsj.mvc.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lsj.util.FileUtil;
import com.lsj.util.StreamUtil;

@Controller
public class FileController {
	
	@RequestMapping("/uploadview")
	public String uploadview(){
		return "uploadview";
	}
	
	@RequestMapping(value="/dir", method=RequestMethod.GET)
	public String dir(HttpServletRequest request, HttpServletResponse response){
		File dir = (File) request.getAttribute("dir");
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
	}
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		File file = (File) request.getAttribute("file");
		
		String filename = URLEncoder.encode(file.getName(), "utf-8");
		int fileLength = (int) file.length();
		
		response.setContentType("application/x-msdownload");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength(fileLength);
        
        OutputStream os = response.getOutputStream();
        InputStream is = new FileInputStream(file);
        StreamUtil.InputStream2OutputStream(is, os);
        os.flush();
        os.close();is.close();
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file, String postdir) throws Exception{
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		String saveDir = blogRoot+postdir+"/";
		String savePath = saveDir + file.getOriginalFilename();
		
		if(!new File(saveDir).exists()){
			new File(saveDir).mkdirs();
		}
		
		file.transferTo(new File(savePath));
		return "redirect:uploadview.do";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		File file = (File) request.getAttribute("file");
		String dirName = (String) request.getAttribute("dirName");
		
		if(FileUtil.DeleteFile(file)){
			response.setCharacterEncoding("utf-8");
			return "redirect:dir.do?dir="+URLEncoder.encode(dirName, "UTF-8");
		}else{
			request.getSession(true).setAttribute("inform", "删除失败...");
			return "redirectview";
		}
	}
}
