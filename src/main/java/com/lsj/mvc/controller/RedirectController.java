package com.lsj.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

	@RequestMapping("/redirectview")
	public String redirectview(){
		return "redirectview";
	}
	
}
