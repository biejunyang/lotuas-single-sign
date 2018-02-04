package com.bjy.lotuas.samples.tomcatjsp.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjy.lotuas.common.dao.impl.QueryDaoImpl;
import com.bjy.lotuas.samples.tomcatjsp.exception.MyException;
import com.bjy.lotuas.samples.tomcatjsp.service.ExceptionTestService;

@Controller
public class WelcomeController {
	
	@Value("${application.message:Hello World2}")
	private String message = "Hello World";
	
	@Autowired
	private ExceptionTestService exceptionTestService;
	
	@Autowired
	private QueryDaoImpl queryDaoImpl;
	
	@GetMapping("/welcome1")
	public String welcome1(Map<String, Object> model, HttpServletRequest req) {
		model.put("time", new Date());
		model.put("message", this.message);
		System.out.println(queryDaoImpl);
		return "samples/welcome";
	}
	


	@RequestMapping("/fail2")
	public String fail2() {
		System.out.println("fail2");
		throw new MyException("Oh dear!");
	}
	
	@RequestMapping("/fail3")
	public String fail3() {
		System.out.println("fail3");
		exceptionTestService.fail1();
		return "";
	}
	

	
	@RequestMapping("/fail4")
	public String fail4() {
		throw new IllegalStateException();
	}

	
//	@ExceptionHandler(RuntimeException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	public  MyRestResponse handleMyRuntimeException(MyException exception, HttpServletRequest req, Model model) {
//		return new MyRestResponse("Some data I want to send back to the client.");
//	}
//	
	
}
