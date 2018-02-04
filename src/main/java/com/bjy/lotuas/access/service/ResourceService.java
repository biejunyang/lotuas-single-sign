package com.bjy.lotuas.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bjy.lotuas.access.dao.ResourceRepository;
import com.bjy.lotuas.access.entity.TResourceBean;
import com.bjy.lotuas.common.service.BaseService;
import com.bjy.lotuas.common.vo.PaginatedHelper;

@Service
public class ResourceService extends BaseService{
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	
	
	public Page<TResourceBean> listPagePersons(TResourceBean resource, PaginatedHelper paginatedHelper, Sort sort){
		Page<TResourceBean> pageResources=resourceRepository.findAll(new PageRequest(paginatedHelper.getPage(), paginatedHelper.getPageSize(), sort));
		return pageResources;
	}

	
	
	public List<TResourceBean> listPersons(TResourceBean resource, Sort sort){
		if(sort==null) {
			sort=new Sort(Direction.ASC, "resourceId");
		}
		return resourceRepository.findAll(sort);	
	}
	
	
}
