package com.bjy.lotuas.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bjy.lotuas.access.interceptor.AuthorityInterceptor;
import com.bjy.lotuas.config.interceptor.RequestTimeInterceptor;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * 映射静态文件路径路径
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
		registry.addResourceHandler("/assets/**").addResourceLocations("/resources/");
	}
	

	
	/**
	 * 添加拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/*")
			.excludePathPatterns("/resources/**", "/loginPage", "/loginAuth", "/logout", "/error");
		
		OpenSessionInViewInterceptor osiv=new OpenSessionInViewInterceptor();
		osiv.setSessionFactory(sessionFactory);
		registry.addWebRequestInterceptor(osiv);
		
		registry.addInterceptor(new RequestTimeInterceptor());
	}
	
	
	
	
	/**
	 * 重写默认的JSP视图解析器
	 * @return
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	 */
	

	
	/**
	 * 重写Dispather Servlet映射路径
	 * @param dispatchServlet
	 * @return
	@Bean
	public ServletRegistrationBean dispathServlet(DispatcherServlet dispatchServlet) {
		ServletRegistrationBean servletReg= new ServletRegistrationBean(dispatchServlet);
		servletReg.getUrlMappings().clear();//清空默认的映射路径配置
		servletReg.addUrlMappings("*.do");
		return servletReg;
	}	
	 **/
	
	
	/**
	 * 自定义servlet组件
	 * @return
	@Bean
	public ServletRegistrationBean helloServlet() {
		return new ServletRegistrationBean(new HelloServlet(), "/hello");
	}
	 
	
	/**
	 * Spring MVC统一异常处理
	 * @return
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver=new SimpleMappingExceptionResolver();
		//缺省的异常跳转页面，
		exceptionResolver.setDefaultErrorView("common/error");
		//异常处理页面中，获取异常信息的变量名，默认为exceptin
		exceptionResolver.setExceptionAttribute("ex");
		//定义需要特殊处理的异常，以及对应的异常处理页面
		Properties props=new Properties();
		props.put(ServiceException.class.getName(), "common/service_error");
		props.put(ControllerException.class.getName(), "common/controller_error");
		props.put(MyException.class.getName(), "common/error2");
		
		exceptionResolver.setExceptionMappings(props);
		return exceptionResolver;
	}
	 */
	
	
	/**
	 * 实现HandlerExceptionResolver接口自定义统一异常处理器 
	 * @return
	@Bean
	public CommonExceptionResolver commonExceptionResolver() {
		return new CommonExceptionResolver();
	}
	 */
	
	
	/**
	 * 定制内嵌的servlet容器特性
	 * @return
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	        	ErrorPage errorMyExceptionPage=new ErrorPage(MyException.class, "/WEB-INF/jsp/common/error.jsp");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/jsp/common/404.jsp");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/WEB-INF/jsp/common/500.jsp");
	            container.addErrorPages(errorMyExceptionPage, error404Page,error500Page);
	        }
	    };
	}
	 */
	
}
