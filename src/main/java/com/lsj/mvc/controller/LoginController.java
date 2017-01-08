package com.lsj.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lsj.util.Configuration;

@Controller
public class LoginController {

	@RequestMapping("/loginview")
	public String loginView(){
		return "loginview";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, String username, String password){
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		Configuration blogConfig = (Configuration)wac.getBean("blogConfiguration");
		if(username == null || password == null){
			username = "";
			password = "";
		}
		
		if(	username.equals(blogConfig.getUsername()) && 
			password.equals(blogConfig.getPassword())){
			
			String svuri = (String) request.getSession(true).getAttribute("svuri");
			request.getSession(true).setAttribute("svuri", null);		//清掉uri
			
			request.getSession(true).setAttribute("access", "true");	//给与权限
			//以下为跳转页面的显示信息
			request.getSession(true).setAttribute("to", svuri==null ? "/springmdblog/login.do" : svuri);
			request.getSession(true).setAttribute("inform", "ok");
			request.getSession(true).setAttribute("seconds", 3);
		}else{
			String svuri = (String) request.getSession(true).getAttribute("svuri");
			request.getSession(true).setAttribute("svuri", null);
			
			//以下为跳转页面的显示信息
			request.getSession(true).setAttribute("to", svuri==null ? "/springmdblog/loginview.do" : svuri);
			request.getSession(true).setAttribute("inform", "failed");
			request.getSession(true).setAttribute("seconds", 3);
		}
		
		return "redirect:redirectview.do";
	}
}
