package com.bjy.lotuas.common.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bjy.lotuas.common.vo.PaginatedHelper;

public interface QueryDao {
	/**
	 * 获取唯一的记录值
	 * 
	 * @param hql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public <T> T getSingleResult(String hql, Class<T> clazz);

	/**
	 * 获取唯一的记录值
	 * 
	 * @param hql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public <T> T getSingleResult(String hql, Object[] params, Class<T> clazz);

	/**
	 * 获取唯一的记录值
	 * 
	 * @param hql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public <T> T getSingleResult(String hql, Map<String, Object> paramMap, Class<T> clazz);

	/**
	 * 查询记录数量
	 * 
	 * @param hql
	 * @return
	 */
	public long getCount(final String hql);

	/**
	 * 通过hql查询记录数,hql查询参数格式：a=? and b=?
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public long getCount(final String hql, final Object params[]);

	/**
	 * 通过hql查询记录数,hql查询参数格式：name=:name and sex=:sex
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public long getCount(final String hql, final Map<String, Object> paramMap);

	/**
	 * 查询符合某一条件的实体记录数量
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public long getCount(Class<?> clazz);
	
	public long getCount(Class<?> clazz, Map<String, Object> parmaMap);

	public long getCount(Class<?> clazz, String fieldName, Object fieldValue);

	public long getCount(Class<?> clazz, String[] fieldNames, Object[] fieldValues);

	/**
	 * 实体列表查询
	 */
	public <T> List<T> getList(final String hql);

	public <T> List<T> getList(final String hql, final Object params[]);

	public <T> List<T> getList(final String hql, final Map<String, Object> paramMap);

	public <T> List<T> getList(final String hql, final int start, final int limit);

	public <T> List<T> getList(final String hql, final Object params[], final int start, final int limit);

	public <T> List<T> getList(final String hql, final Map<String, Object> paramMap, final int start, final int limit);

	public <T> List<T> getList(final String hql, final PaginatedHelper paginatedHelper);

	public <T> List<T> getList(final String hql, final Object params[], final PaginatedHelper paginatedHelper);

	public <T> List<T> getList(final String hql, final Map<String, Object> paramMap,
			final PaginatedHelper paginatedHelper);

	public <T> List<T> getList(Class<?> clazz);

	public <T> List<T> getList(Class<?> clazz, LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<?> clazz, final int start, final int limit, LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<?> clazz, PaginatedHelper paginatedHelper, LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<? extends T> clazz, String fieldName, Object value);

	public <T> List<T> getList(Class<? extends T> clazz, String fieldName, Object value,
			LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<? extends T> clazz, String[] fields, Object values[]);

	public <T> List<T> getList(Class<? extends T> clazz, String[] fields, Object values[],
			LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap);

	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap, final int start, final int limit);

	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap, final int start, final int limit,
			LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap, PaginatedHelper paginatedHelper);

	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap, PaginatedHelper paginatedHelper,
			LinkedHashMap<String, String> orderBy);

	public <T> List<T> getList(Class<?> clazz, PaginatedHelper paginatedHelper);

	public <T> List<T> getList(Class<?> clazz, int start, int limit);

	/**
	 * 获取实体的某一个属性
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName);

	public <T> List<T> getFieldList(Class<?> clazz, String fieldName, Map<String, Object> paramMap);

	public <T> List<T> getFieldList(Class<?> clazz, String fieldName, Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderBy);

	/**
	 * 判断某一个字段的值是否存在
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param fieldValue
	 * @param pkName
	 * @param pkVal
	 * @return
	 */
	public boolean isFieldExist(Class<?> clazz, String fieldName, Object fieldValue, String pkName, Object pkVal);

	public boolean isFieldExist(Class<?> clazz, String[] fieldNames, Object fieldValues[], String pkName, Object pkVal);

}
