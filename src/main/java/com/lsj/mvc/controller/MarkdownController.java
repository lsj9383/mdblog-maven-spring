package com.lsj.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MarkdownController {
	@RequestMapping("/md")
	public String md(HttpServletRequest request){
		return "mdview";
	}
}
