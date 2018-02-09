package com.bjy.lotuas.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.bjy.lotuas.access.exception.NoAccessException;
import com.bjy.lotuas.access.exception.SessionTimeOutException;
import com.bjy.lotuas.common.entity.TExceptionLogBean;
import com.bjy.lotuas.common.service.ExceptionService;

public class CommonExceptionResolver implements HandlerExceptionResolver{
	
	@Autowired
	private ExceptionService exceptionService;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
		String viewName="common/500";
		Map<String, Object> model=new HashMap<String, Object>();
		model.put("ex", ex);
		model.put("message", ex.getMessage());
		response.setHeader("exceptionType", "Exception");
		if(ex instanceof SessionTimeOutException) {
			response.setHeader("exceptionType", "SessionTimeOutException");// 在响应头设置session状态
			viewName="common/exception";
		}else if(ex instanceof NoAccessException) {
			response.setHeader("exceptionType", "NoAccessException");// 在响应头设置session状态
			viewName="common/exception";
		}
		
		//添加异常日志
		HandlerMethod handlerMethod=(HandlerMethod)handler;
		TExceptionLogBean exceptionLog=new TExceptionLogBean();
		exceptionLog.setExceptionType(ex.getClass().getName());
		exceptionLog.setRequestUrl(request.getRequestURL().toString()+"?"+request.getQueryString());
		exceptionLog.setRequestController(handlerMethod.toString());
		exceptionLog.setRequestContentType(request.getContentType());
		try {
			exceptionLog.setRequestBody(IOUtils.toString(request.getInputStream(), "utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		exceptionLog.setCreateId(exceptionService.getLoginUserId());
		exceptionService.saveTarget(exceptionLog);
		return new ModelAndView(viewName, model);
	}
}
