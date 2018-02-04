package com.bjy.lotuas.access.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjy.lotuas.access.entity.TSystemBean;
import com.bjy.lotuas.access.vo.SystemVo;
import com.bjy.lotuas.common.easyui.EasyuiGridResult;
import com.bjy.lotuas.common.exception.VOCastException;
import com.bjy.lotuas.common.service.BaseService;
import com.bjy.lotuas.common.util.VoUtil;
import com.bjy.lotuas.common.vo.CommonResultVo;
import com.bjy.lotuas.common.vo.PaginatedHelper;

@Service
public class SystemService extends BaseService{
	
	
	
	
	public EasyuiGridResult listPageSystems(PaginatedHelper paginatedHelper, LinkedHashMap<String, String> orderMap) {
		long count=super.getCount(TSystemBean.class);
		List<TSystemBean> systems=super.getList(TSystemBean.class, paginatedHelper, orderMap);
		return new EasyuiGridResult(systems, count);
	}
	
	
	
	@Transactional
	public CommonResultVo saveSystem(SystemVo systemVo) throws VOCastException {
		TSystemBean system=null;
		String msg=null;
		if(systemVo.getSystemId()!=null) {
			 system=super.getTarget(TSystemBean.class, systemVo.getSystemId());
			 msg="更新成功!";
		}else {
			system=new TSystemBean();
			msg="修改成功!";
		}
		VoUtil.copyValuesNotNull(systemVo, system, false);
		super.saveOrUpdate(system);
		return new CommonResultVo(true, msg);
	}
}
