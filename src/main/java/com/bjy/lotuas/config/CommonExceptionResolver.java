package com.bjy.lotuas.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.bjy.lotuas.samples.tomcatjsp.exception.MyException;
import com.bjy.lotuas.samples.tomcatjsp.exception.ServiceException;

public class CommonExceptionResolver implements HandlerExceptionResolver{
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
//		System.out.println(handler);
//		System.out.println(handler.getClass());
		String viewName="common/error";
		Map<String, Object> model=new HashMap<String, Object>();
		model.put("ex", ex);
		if(ex instanceof MyException) {
			model.put("message", ex.getMessage());
			viewName="common/error3";
		}else if(ex instanceof ServiceException) {
			viewName="common/500";
		}
		return new ModelAndView(viewName, model);
	}
}
