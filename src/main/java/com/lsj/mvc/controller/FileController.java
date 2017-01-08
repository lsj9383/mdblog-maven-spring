package com.lsj.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file, String postdir) throws Exception{
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		String saveDir = blogRoot+postdir+"/";
		String savePath = saveDir + file.getOriginalFilename();
		
		if(!new File(saveDir).exists()){
			new File(saveDir).mkdirs();
		}
		
		file.transferTo(new File(savePath));
		return "uploadview";
	}
	
	@RequestMapping("/download")
	public String download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		String dirName = request.getParameter("file");
		String fileStr = blogRoot + dirName;
		
		File file = new File(fileStr);
		if(file.isFile()){
			String filename = URLEncoder.encode(file.getName(), "utf-8");
			int fileLength = (int) file.length();
			
			response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.setContentLength(fileLength);
            
            OutputStream os = response.getOutputStream();
            InputStream is = new FileInputStream(file);
            try{
            	StreamUtil.InputStream2OutputStream(is, os);
            }catch(Exception e){e.printStackTrace();}
            finally{os.flush();os.close();is.close();}
            return null;
		}else{
			request.getSession(true).setAttribute("inform", "文件错误,无法下载");
			return "redirectview";
		}
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String appRoot = request.getServletContext().getRealPath("/");
		String blogRoot = appRoot+"blogRoot/";
		String fileName = request.getParameter("file");
		String fileStr = blogRoot + fileName;
		String dirName = null;
		
		response.setCharacterEncoding("utf-8");
		
		if(fileName.endsWith("/")){
			dirName = fileName.substring(0, fileName.lastIndexOf("/"));
			dirName = fileName.substring(0, dirName.lastIndexOf("/")+1);
		}else{
			dirName = fileName.substring(0, fileName.lastIndexOf("/")+1);
		}
		
		File file = new File(fileStr);
		if(FileUtil.DeleteFile(file)){
			response.sendRedirect("/springmdblog/dir.do?dir="+URLEncoder.encode(dirName, "UTF-8"));
			return null;
		}else{
			request.getSession(true).setAttribute("inform", "删除失败...");
			return "redirectview";
		}
	}
}
