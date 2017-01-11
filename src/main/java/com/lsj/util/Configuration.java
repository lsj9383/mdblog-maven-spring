package com.lsj.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Configuration {
	private String appName;
	private String username;
	private String password;
	
	static public Configuration GetInstance(HttpServletRequest request){
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return (Configuration)wac.getBean("blogConfiguration");
	}
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appRootName) {
		this.appName = appRootName;
	}
	private List<UrlButton> urlButtons;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<UrlButton> getUrlButtons() {
		return urlButtons;
	}
	public void setUrlButtons(List<UrlButton> urlButtons) {
		this.urlButtons = urlButtons;
	}
	
}
