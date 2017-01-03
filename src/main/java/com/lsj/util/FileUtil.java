package com.lsj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.fileupload.FileItem;

import com.lsj.mdprocess.MdProcessor;

public class FileUtil {
//	private static final MdProcessor processor = MdProcessor.NewInstance("showdown").setPath("/mdblog/mdsource");
//	private static final MdProcessor processor = MdProcessor.NewInstance("md4j");
	private static final MdProcessor processor = MdProcessor.NewInstance("md4j").setPath("/mdblog/mdsource");
			
	static public String ParentName(String fileName){	//查询fileName的父路径名字，需要注意的是，若已经是根路径了返回的还是根路径。
		
		String dirName = null;
		
		if(fileName.endsWith("/")){
			dirName = fileName.substring(0, fileName.lastIndexOf("/"));
			dirName = fileName.substring(0, dirName.lastIndexOf("/")+1);
		}else if("/".equals(fileName)){
			dirName = "/";
		}else{
			dirName = fileName.substring(0, fileName.lastIndexOf("/")+1);
		}
		
		return dirName;
	}
	
	static public boolean DeleteFile(File file){	//只要有一个文件没删干净，就返回false
		if(file.isFile()){
			return file.delete();
		}else{
			File[] fileList = file.listFiles();
			boolean res = true;
			for(File cfile : fileList){
				res &= DeleteFile(cfile);
			}
			return res&file.delete();
		}
	}
	
	static public void SaveFileItem(FileItem fileItem, File dir) {
		if(!dir.exists()){dir.mkdirs();}
		
		String fileName = fileItem.getName();
		InputStream is = null;
		OutputStream os = null;
		try{
	
			is = fileItem.getInputStream();
			os = new FileOutputStream(new File(dir.getAbsoluteFile()+"/"+fileName));
			StreamUtil.InputStream2OutputStream(is, os);
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try {
				is.close();
				os.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	static public void Md2HtmlAsFile(File mdFile, File htmlFile) throws Exception{
		String mdText = FileUtil.ReadFile(mdFile);
		String htmlText = processor.process(mdText);
		FileUtil.writeFile(htmlFile, htmlText);
	}
	
	static public String ReadFile(File file) throws Exception{
		if(file.exists()){
			FileInputStream fis = new FileInputStream(file);
			try{
				return StreamUtil.ReadInputStream(fis);
			}
			catch (Exception e){e.printStackTrace();}
			finally{
				fis.close();	
			}
		}
		return null;	
	}
	
	static public void writeFile(File file, String text) throws IOException{
		OutputStream os = new FileOutputStream(file);
		os.write(text.getBytes(), 0, text.getBytes().length);
		os.close();
	}
}
