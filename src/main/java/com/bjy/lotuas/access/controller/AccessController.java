package com.bjy.lotuas.access.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjy.lotuas.access.entity.TUserBean;
import com.bjy.lotuas.access.service.AccessService;
import com.bjy.lotuas.common.vo.CommonResultVo;

@Controller
public class AccessController {
	
	public final static String SESSION_LOGION_USER_TOKEN="SESSION_LOGION_USER_TOKEN";
	
	public final static String SUPER_ADMIN="SUPERADMIN";
	
	public final static String DEFAULT_RESET_PED="123456";
	
	@Autowired
	private AccessService accessService;
	
	
	
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@GetMapping("/loginPage")
	public String loginPage(Model model) {
		if(accessService.getLoginUser()!=null) {
			return "redirect:/index";
		}
		return "login";
	}
	
	/**
	 * 登录校验(权限校验是在拦截器中)
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/loginAuth")
	@ResponseBody
	public CommonResultVo loginAuth(String username, String password, HttpSession session) {
		if(StringUtils.isEmpty(username)) {
			return new CommonResultVo(false, "用户名不能为空!");
		}
		if(StringUtils.isEmpty(password)) {
			return new CommonResultVo(false, "密码不能为空!");
		}
		TUserBean user=accessService.getTarget(TUserBean.class, "username", username);
		if(user==null)
			return new CommonResultVo(false, "用户名填写错误!");
		if(!user.getPassword().equals(DigestUtils.md5Hex(password))) 
			return new CommonResultVo(false, "密码填写错误!");
		session.setAttribute(SESSION_LOGION_USER_TOKEN, user);
		return new CommonResultVo(true, "校验成功!");
	}
	
	
	/**
	 * 登录注销
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session!=null){
			session.invalidate();
		}
		return "redirect:/loginPage";
	}
	
	
	
	/**
	 * 系统首页
	 * @param model
	 * @return
	 */
	@GetMapping("index")
	public String index(Model model) {
		return "index";
	}
	
	
	
}
