package com.bjy.lotuas.access.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessController {
	
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@GetMapping("loginPage")
	public String loginPage(Model model) {
		return "login";
	}
	
	
	@PostMapping("loginAuth")
	public String loginAuth(String username, String password) {
		return "login";
	}
	
	
	
	
	@GetMapping("index")
	public String index(Model model) {
		return "index";
	}
}
