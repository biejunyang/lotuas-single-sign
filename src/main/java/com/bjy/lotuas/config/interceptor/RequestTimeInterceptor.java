package com.bjy.lotuas.config.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bjy.lotuas.common.util.DateTimeUtil;

public class RequestTimeInterceptor implements HandlerInterceptor{
	
	private final Log log4j = LogFactory.getLog(this.getClass());
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long startTime=System.currentTimeMillis();
		log4j.info("请求路径"+request.getRequestURI()+"请求开始时间："+DateTimeUtil.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss"));
		request.setAttribute("startTime", startTime);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime=(Long)request.getAttribute("startTime");
		request.removeAttribute("startTime");
		long endTime=System.currentTimeMillis();
		log4j.info("请求路径"+request.getRequestURI()+"请求完成时间："+DateTimeUtil.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss"));
		log4j.info("请求路径"+request.getRequestURI()+"请求处理时间为："+(endTime-startTime)+"ms");
	}
}
