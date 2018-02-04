package com.bjy.lotuas.access.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjy.lotuas.access.entity.TResourceBean;
import com.bjy.lotuas.access.service.ResourceService;
import com.bjy.lotuas.common.dao.HqlUtil;
import com.bjy.lotuas.common.vo.PaginatedHelper;

@Controller
@RequestMapping("/resources")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	
	@GetMapping
	@ResponseBody
	public Object listResources(TResourceBean resource, PaginatedHelper paginatedHelper, Boolean page, String sort){
		Sort realSort=HqlUtil.getSort(sort);
		if(page!=null && page) {
			Page<TResourceBean> pageResources=resourceService.listPagePersons(resource, paginatedHelper,realSort);
			return pageResources;
		}else {
			return resourceService.listPersons(resource,realSort);
		}
	}
	
	
	@GetMapping("/{resourceId:\\d*}")
	@ResponseBody
	public Object getResource(@PathVariable Integer resourceId){
		return resourceService.getTarget(TResourceBean.class, resourceId);
	}
	
	
	
	
}
