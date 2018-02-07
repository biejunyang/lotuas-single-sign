package com.bjy.lotuas.access.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bjy.lotuas.access.annotation.Permission;
import com.bjy.lotuas.access.controller.AccessController;
import com.bjy.lotuas.access.entity.TUserBean;
import com.bjy.lotuas.access.exception.SessionTimeOutException;
import com.bjy.lotuas.access.service.AccessService;
import com.bjy.lotuas.config.SystemContext;

/**
 * 权限认证拦截器
 * @author DELL
 *
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter
{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AccessService accessService=SystemContext.getBean(AccessService.class);
		TUserBean user=accessService.getLoginUser();
		if(user!=null) {
			if(user.getUsername().equals(AccessController.SUPER_ADMIN))
				return true;
			
			//请求权限校验
			HandlerMethod handlerMethod=(HandlerMethod)handler;
			Permission permission=handlerMethod.getMethodAnnotation(Permission.class);
			if(permission!=null) {
				
//				if(accessService.isNeedAuthUrl(url)){
//					if(accessService.authUrl(url)){					
//						
//						return true;
//					}else{
//						throw new NoAccessException("没有授权对该页面的操作，请与管理员联系");
//					}
//				}
				
			}
		
			return true;
		}else {
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				response.setHeader("session_timeout_status", "timeout");// 在响应头设置session状态
				throw new SessionTimeOutException("系统超时退出，请重新登录！");
			} else {
				response.sendRedirect(request.getContextPath() + "/loginPage");
				return false;
			}
		}
	}
	
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
}
