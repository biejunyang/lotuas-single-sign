package com.bjy.lotuas.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bjy.lotuas.access.controller.AccessController;
import com.bjy.lotuas.access.entity.TUserBean;
import com.bjy.lotuas.common.dao.HqlUtil;
import com.bjy.lotuas.common.dao.QueryDao;
import com.bjy.lotuas.common.entity.BaseEntity;
import com.bjy.lotuas.common.util.EstimateTypeUtil;
import com.bjy.lotuas.common.util.VoUtil;
import com.bjy.lotuas.common.vo.PaginatedHelper;

@Transactional(propagation=Propagation.REQUIRED)
public class BaseService implements QueryDao
{
	@Autowired
	protected HibernateTemplate hibernateTemplate;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Resource(name="queryDaoImpl")
	private QueryDao queryDao;
	
	@Autowired
	protected HttpSession httpSession;

	
	/**
	 * 获取系统登录用户
	 * @return
	 */
	public TUserBean getLoginUser(){
		Object user=httpSession.getAttribute(AccessController.SESSION_LOGION_USER_TOKEN);
		return user==null? null : (TUserBean)user;
	}
	
	
	/**
	 * 获取系统登录用户id
	 * @return
	 */
	public Integer getLoginUserId(){
		TUserBean user=getLoginUser();
		return user==null ? null : user.getUserId();
	}
	
	
	/**
	 * 通过主键，获取一个数据库映射的实例对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T getTarget(Class<T> clazz, Serializable id){
		return hibernateTemplate.get(clazz, id);
	}
	
	

	/**
	 * 通过某个属性，获取一个数据库映射的实例对象
	 * */
	public <T> T getTarget(Class<? extends T> clazz,String uniqueName,Object value){
		return getTarget(clazz,new String[]{uniqueName},new Object[]{value});
	}
	
	/**
	 * 通过某个属性，获取一个数据库映射的实例对象
	 * */
	public <T> T getTarget(Class<? extends T> clazz,String[] fields,Object values[]){
		return VoUtil.returnOneTarget(getList(clazz,fields,values));
	}
	
	
	/**
	 * 保存实体
	 * @param entity
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveTarget(BaseEntity entity){
		hibernateTemplate.save(entity);
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveOrUpdate(BaseEntity entity){
		hibernateTemplate.saveOrUpdate(entity);
	}
	
	
	/**
	 * 更新实体
	 * @param entity
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateTarget(BaseEntity entity){
		hibernateTemplate.update(entity);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void update(final String hql, final Map<String, Object> paramMap){
		if(paramMap!=null && paramMap.size()>0){
			hibernateTemplate.execute(new HibernateCallback<Object>()
			{
				@Override
				public Object doInHibernate(
						Session session) throws HibernateException
				{
					Query query=session.createQuery(hql);
					for(Map.Entry<String, Object> en: paramMap.entrySet()){
						String key=en.getKey();
						Object value=en.getValue();
						if (EstimateTypeUtil.isArray(value)) {
							query.setParameterList(key, (Object[]) value);
						} else if (EstimateTypeUtil.isCollection(value)) {
							query.setParameterList(key, (Collection<?>) value);
						} else {
							query.setParameter(key, value);
						}
					}
					query.executeUpdate();
					return null;
				}	
			});
		}
	}
	
	/**
	 * 删除实体
	 * @param entity
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(BaseEntity entity){
		hibernateTemplate.delete(entity);
	}



	/**
	 * 通过主键，删除一个数据库实例
	 * */
	@Transactional(propagation=Propagation.REQUIRED)
	public void  deleteTarget(Class<?> target,String pkName,Serializable id){
		 StringBuffer queryString = new StringBuffer();
		 queryString.append(" DELETE FROM "+target.getName()+" a where a."+pkName+"=? ");
		 hibernateTemplate.bulkUpdate(queryString.toString(),new Object[]{id});
	}

	
	/**
	 * 通过主键，删除一个数据库实例
	 * */
	@Transactional(propagation=Propagation.REQUIRED)
	public void  deleteTarget(Class<?> target,String pkName,Object[] ids){
		if(ids!=null && ids.length>0){
			StringBuffer queryString = new StringBuffer();
			queryString.append(" DELETE FROM "+target.getName()+" a where ")
					   .append(" a.").append(pkName).append(" in ")
					   .append(HqlUtil.createInHql(ids.length));
			hibernateTemplate.bulkUpdate(queryString.toString(),ids);
		}
		 
	}
	
	/**
	 * 通过主键，删除数据库实例
	 * */
	@Transactional(propagation=Propagation.REQUIRED)
	public void  deleteTarget(Class<?> target,String fieldName,Object fieldValue){
		if(fieldName!=null && !fieldName.equals("") && fieldValue!=null){
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put(fieldName, fieldValue);
			deleteTarget(target, paramMap);
		}
		 
	}
	
	/**
	 * 删除实体
	 * @param clazz
	 * @param paramMap
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteTarget(Class<?> clazz, final Map<String, Object> paramMap){
		if(paramMap!=null && paramMap.size()>0){
			final StringBuffer queryString = new StringBuffer();
			queryString.append(" DELETE FROM "+clazz.getName()+" a where 1=1 ");
			for(Map.Entry<String, Object> en: paramMap.entrySet()){
				queryString.append(" and ").append(en.getKey()).append("=:").append(en.getKey());
			}
			hibernateTemplate.execute(new HibernateCallback<Object>()
			{
				@Override
				public Object doInHibernate(
						Session session) throws HibernateException
				{
					Query query=session.createQuery(queryString.toString());
					for(Map.Entry<String, Object> en: paramMap.entrySet()){
						String key=en.getKey();
						Object value=en.getValue();
						if (EstimateTypeUtil.isArray(value)) {
							query.setParameterList(key, (Object[]) value);
						} else if (EstimateTypeUtil.isCollection(value)) {
							query.setParameterList(key, (Collection<?>) value);
						} else {
							query.setParameter(key, value);
						}
					}
					query.executeUpdate();
					return null;
				}	
			});
		}
	}
	
	
	/**
	 * 清空实体映射表的数据
	 * */
	@Transactional(propagation=Propagation.REQUIRED)
	public void clearTableData(Class<?> target){
		 StringBuffer queryString = new StringBuffer();
		 queryString.append(" DELETE FROM "+target.getName()+" a where 1=1 ");
		 hibernateTemplate.bulkUpdate(queryString.toString());
	}
	

	

	@Override
	public <T> T getSingleResult(String hql, Class<T> clazz) {
		return queryDao.getSingleResult(hql, clazz);
	}



	@Override
	public <T> T getSingleResult(String hql, Object[] params, Class<T> clazz) {
		return queryDao.getSingleResult(hql, params, clazz);
	}



	@Override
	public <T> T getSingleResult(String hql, Map<String, Object> paramMap, Class<T> clazz) {
		return queryDao.getSingleResult(hql, paramMap, clazz);
	}



	/**
	 * count数量查询
	 */
	@Override
	public long getCount(String hql)
	{
		return queryDao.getCount(hql);
	}

	@Override
	public long getCount(String hql, Object[] params)
	{
		return queryDao.getCount(hql, params);
	}

	@Override
	public long getCount(String hql, Map<String, Object> paramMap)
	{
		return queryDao.getCount(hql, paramMap);
	}

	@Override
	public long getCount(Class<?> clazz)
	{
		return queryDao.getCount(clazz);
	}

	@Override
	public long getCount(Class<?> clazz, String fieldName, Object fieldValue)
	{
		return queryDao.getCount(clazz, fieldName, fieldValue);
	}

	@Override
	public long getCount(Class<?> clazz, String[] fieldNames,
			Object[] fieldValues)
	{
		return queryDao.getCount(clazz, fieldNames, fieldValues);
	}

	@Override
	public long getCount(Class<?> clazz, Map<String, Object> parmaMap)
	{
		return queryDao.getCount(clazz, parmaMap);
	}


	/**
	 * [分页]列表查询
	 */
	@Override
	public <T> List<T> getList(String hql)
	{
		return queryDao.getList(hql);
	}

	@Override
	public <T> List<T> getList(String hql, Object[] params)
	{
		return queryDao.getList(hql, params);
	}

	@Override
	public <T> List<T> getList(String hql, Map<String, Object> paramMap)
	{
		return queryDao.getList(hql, paramMap);
	}

	@Override
	public <T> List<T> getList(String hql, int start, int limit)
	{
		return queryDao.getList(hql, start, limit);
	}

	@Override
	public <T> List<T> getList(String hql, Object[] params, int start, int limit)
	{
		return queryDao.getList(hql, params, start, limit);
	}

	@Override
	public <T> List<T> getList(String hql, Map<String, Object> paramMap,
			int start, int limit)
	{
		return queryDao.getList(hql, paramMap, start, limit);
	}
	
	@Override
	public <T> List<T> getList(String hql, PaginatedHelper paginatedHelper)
	{
		return queryDao.getList(hql, paginatedHelper);
	}

	@Override
	public <T> List<T> getList(String hql, Object[] params,
			PaginatedHelper paginatedHelper)
	{
		return queryDao.getList(hql, params, paginatedHelper);
	}


	@Override
	public <T> List<T> getList(String hql, Map<String, Object> paramMap,
			PaginatedHelper paginatedHelper)
	{
		return queryDao.getList(hql, paramMap, paginatedHelper);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz)
	{
		return queryDao.getList(clazz);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap)
	{
		return queryDao.getList(clazz, paramMap);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap,
			int start, int limit)
	{
		return queryDao.getList(clazz, paramMap, start, limit);
	}


	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap,
			PaginatedHelper paginatedHelper)
	{
		return queryDao.getList(clazz, paramMap, paginatedHelper);
	}
	
	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String fieldName,
			Object value)
	{
		return queryDao.getList(clazz, fieldName, value);
	}
	
	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String[] fields,
			Object[] values)
	{
		return queryDao.getList(clazz, fields, values);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz,
			LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String fieldName,
			Object value, LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, fieldName, value, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String[] fields,
			Object[] values, LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, fields, values, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, paramMap, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap,
			int start, int limit, LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, paramMap, start, limit, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap,
			PaginatedHelper paginatedHelper,
			LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, paramMap, paginatedHelper, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz, int start, int limit,
			LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, start, limit, orderBy);
	}



	@Override
	public <T> List<T> getList(Class<?> clazz, PaginatedHelper paginatedHelper,
			LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getList(clazz, paginatedHelper, orderBy);
	}
	
	@Override
	public <T> List<T> getList(Class<?> clazz, int start, int limit)
	{
		return queryDao.getList(clazz, start, limit);
	}
	
	@Override
	public <T> List<T> getList(Class<?> clazz, PaginatedHelper paginatedHelper)
	{
		return queryDao.getList(clazz, paginatedHelper);
	}
	
	
	
	@Override
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName)
	{
		return queryDao.getFieldList(clazz, fieldName);
	}
	@Override
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName,
			Map<String, Object> paramMap)
	{
		return queryDao.getFieldList(clazz, fieldName, paramMap);
	}
	@Override
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName,
			Map<String, Object> paramMap, LinkedHashMap<String, String> orderBy)
	{
		return queryDao.getFieldList(clazz, fieldName, paramMap, orderBy);
	}
	
	@Override
	public boolean isFieldExist(Class<?> clazz, String fieldName,
			Object fieldValue, String pkName, Object pkVal)
	{
		return queryDao.isFieldExist(clazz, fieldName, fieldValue, pkName, pkVal);
	}
	
	@Override
	public boolean isFieldExist(Class<?> clazz, String[] fieldNames,
			Object[] fieldValues, String pkName, Object pkVal)
	{
		return queryDao.isFieldExist(clazz, fieldNames, fieldValues, pkName, pkVal);
	}
}
