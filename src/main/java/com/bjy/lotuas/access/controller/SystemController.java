package com.bjy.lotuas.access.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjy.lotuas.access.entity.TSystemBean;
import com.bjy.lotuas.access.service.SystemService;
import com.bjy.lotuas.access.vo.SystemVo;
import com.bjy.lotuas.common.dao.HqlUtil;
import com.bjy.lotuas.common.exception.VOCastException;
import com.bjy.lotuas.common.vo.CommonResultVo;
import com.bjy.lotuas.common.vo.PaginatedHelper;

@Controller
@RequestMapping("/system")
public class SystemController {
	
	@Autowired
	private SystemService systemService;
	
	
	@RequestMapping("managePage")
	public String managePage(Model model) {
		model.addAttribute("system", systemService.getTarget(TSystemBean.class, 1));
		return "authority/system_manage";
	}
	
	
	/**
	 * 获取系统列表
	 * @param page：是否分页
	 * @param paginatedHelper：分页参数：page页数；rows:每页最大数量
	 * @param sort：排序参数，格式：systemId,asc;createTime,desc
	 * @return
	 */
	@RequestMapping("listSystems")
	@ResponseBody
	public Object listSystems(Boolean pagable, PaginatedHelper paginatedHelper, String sort, HttpServletRequest request) {
		try {
			System.err.println(IOUtils.toString(request.getInputStream(), "utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pagable!=null && pagable) {
			systemService=null;
			return systemService.listPageSystems(paginatedHelper, HqlUtil.getSortMap(sort));
		}else {
			return systemService.getList(TSystemBean.class, HqlUtil.getSortMap(sort));
		}
	}
	
	
	@RequestMapping("saveSystem")
	@ResponseBody
	public CommonResultVo saveSystem(SystemVo systemVo) throws VOCastException {
		return systemService.saveSystem(systemVo);
	}
	
	
	
}
