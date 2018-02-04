package com.bjy.lotuas.config;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bjy.lotuas.common.util.ReflectUtil;

@Component
public class ApplicationContextPostProcessor implements ApplicationContextAware{
	private final Log log4j = LogFactory.getLog(this.getClass());
	
	private ApplicationContext appCtx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appCtx=applicationContext;
	}
	
	
	
	@PostConstruct
	public void postProcess() {
		try {
			ReflectUtil.setAccessibleValue(SystemContext.class, "applicationContext", appCtx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log4j.error(e.getMessage());
			log4j.error("系统启动失败！！！！！！");
			
		}
	}
}
