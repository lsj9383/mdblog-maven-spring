package com.lsj.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamUtil {
	static public String ReadInputStream(InputStream is) throws Exception{
		InputStreamReader isr = new InputStreamReader(is);
		StringBuilder text = new StringBuilder();
		char[] buffer = new char[1024];
		int length = 0;
		
		while((length = isr.read(buffer)) != -1){
			text.append(new String(buffer, 0, length));
		}		
		
		return new String(text);
	}
	
	static public void WriteOutputStream(OutputStream os, String text) throws Exception{
		os.write(text.getBytes());
	}
	
	static public void InputStream2OutputStream(InputStream is, OutputStream os) throws Exception{
		byte[] buffer = new byte[1024];
		int length = 0;
		while((length = is.read(buffer)) != -1){
			os.write(buffer, 0, length);
		}
	}
}
