package com.bjy.lotuas.access.service;

import org.springframework.stereotype.Service;

import com.bjy.lotuas.common.service.BaseService;

@Service
public class AccessService extends BaseService{
	
	
	
	
	/**
	 * perssion权限认证
	 * @param persmissidId
	 * @param userId
	 * @return
	 */
	public boolean authPermission(String persmissidId, Integer userId) {
		return true;
	}
	
}