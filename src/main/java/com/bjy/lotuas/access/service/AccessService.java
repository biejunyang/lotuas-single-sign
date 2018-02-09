package com.bjy.lotuas.access.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bjy.lotuas.access.controller.AccessController;
import com.bjy.lotuas.access.entity.TUserBean;
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
//		String hql="select from TRes"
		
		return true;
	}
	
	
	
	
	/**
	 * 获取用户的所有权限
	 * @param user
	 * @return
	 */
	public List<String> getUserPermissins(TUserBean user){
		if(user.getUsername().equals(AccessController.SUPER_ADMIN)) {
			String sql="select r.resource_code from t_resource r";
			List<String> resourceCodes=jdbcTemplate.queryForList(sql, String.class, user.getUserId());
			return resourceCodes;
		}else {
			String sql="select r.resource_code from t_resource r where r.resource_id in "
					+ "(select resource_id from t_role_resource where role_id in (select role_id from t_user_role where user_id = ?))";
			List<String> resourceCodes=jdbcTemplate.queryForList(sql, String.class, user.getUserId());
			return resourceCodes;			
		}
	}
	
	
}