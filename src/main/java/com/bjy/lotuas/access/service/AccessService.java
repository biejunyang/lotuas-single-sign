<<<<<<< HEAD
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
=======
package com.bjy.lotuas.access.service;

import org.springframework.stereotype.Service;

import com.bjy.lotuas.common.service.BaseService;

@Service
public class AccessService extends BaseService{
	
}
>>>>>>> dbd16a9be26cc09e9b5b90c44877193bc40dcba6
