package com.bjy.lotuas.config.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bjy.lotuas.common.util.ReflectUtil;
import com.bjy.lotuas.config.SystemContext;

@WebListener
public class InitSystemListener implements ServletContextListener{
	private final Log log4j = LogFactory.getLog(this.getClass());
	
	
	@Override
	public void contextInitialized(ServletContextEvent ev) {
		try {
			Class.forName("com.bjy.lotuas.config.SystemContext");
			ServletContext servletContext=ev.getServletContext();
			ReflectUtil.setAccessibleValue(SystemContext.class, "servletContext", servletContext);
			
			//设置前端路径变量
			String ctx=servletContext.getContextPath();
			if(ctx.endsWith("/")){
				ctx=ctx.substring(0, ctx.lastIndexOf("/"));
			}
			servletContext.setAttribute("ctx", ctx);
			
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			log4j.error(ex.getMessage());
			log4j.error("系统启动失败，无法完成初始化！!!!");
		}
		
		//前端路径设置
		
		
		
		
		
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	};
}
