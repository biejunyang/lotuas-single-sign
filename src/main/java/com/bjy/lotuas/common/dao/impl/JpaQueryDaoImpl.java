package com.bjy.lotuas.common.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bjy.lotuas.common.dao.QueryDao;
import com.bjy.lotuas.common.util.EstimateTypeUtil;
import com.bjy.lotuas.common.util.TypeUtil;
import com.bjy.lotuas.common.vo.PaginatedHelper;

@Repository("jpaQueryDaoImpl")
@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
public class JpaQueryDaoImpl implements QueryDao{
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 获取唯一的记录值
	 * 
	 * @param hql
	 * @param params
	 * @param clazz
	 * @return
	 */
	@Override
	public <T> T getSingleResult(String hql, Object[] params, Class<T> clazz) {
		Query query=entityManager.createQuery(hql);
		if(params!=null && params.length>0) {
			for(int i=0; i<params.length; i++) {
				query.setParameter(i+1, params[i]);
			}
		}
		Object value=query.getSingleResult();
		return TypeUtil.cast(value, clazz);
	}
	
	@Override
	public <T> T getSingleResult(String hql, Map<String, Object> paramMap, Class<T> clazz) {
		Query query=entityManager.createQuery(hql);
		if(paramMap!=null && paramMap.size()>0) {
			for(Map.Entry<String, Object> en:paramMap.entrySet()) {
				String key=en.getKey();
				Object value=en.getValue();
				if (EstimateTypeUtil.isArray(value)) {
					query.setParameter(key, Arrays.asList((Object[]) value));
				} else if (EstimateTypeUtil.isCollection(value)) {
					query.setParameter(key, (Collection<?>) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		return TypeUtil.cast(query.getSingleResult(), clazz);
	}
	
	@Override
	public <T> T getSingleResult(String hql, Class<T> clazz) {
		return getSingleResult(hql, new Object[] {}, clazz);
	}
	
	/**
	 * 通过hql查询记录数,hql查询参数格式：a=? and b=?
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	@Override
	public long getCount(String hql, Object[] params) {
		return getSingleResult(hql, params, long.class);
	}
	
	@Override
	public long getCount(String hql) {
		return getCount(hql, new Object[] {});
	}
	
	
	@Override
	public long getCount(String hql, Map<String, Object> paramMap) {
		return getSingleResult(hql, paramMap, long.class);
	}
	

	@Override
	public long getCount(Class<?> clazz, Map<String, Object> paramMap) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from ").append(clazz.getName()).append(" where 1=1 ");
		if (paramMap != null && paramMap.size() > 0) {
			List<String> removeKeys = new ArrayList<String>();
			Map<String, Object> replacedFields = new HashMap<String, Object>();
			for (Map.Entry<String, Object> en : paramMap.entrySet()) {
				String valueName = en.getKey();
				if (en.getKey().indexOf(".") > -1) {
					removeKeys.add(en.getKey());
					valueName = en.getKey().replaceAll("[.]", "_");
					replacedFields.put(valueName, en.getValue());
				}
				if (EstimateTypeUtil.isArray(en.getValue()) || EstimateTypeUtil.isCollection(en.getValue())) {
					hql.append(" and ").append(en.getKey()).append(" in (:").append(valueName).append(")");
				} else {
					hql.append(" and ").append(en.getKey()).append("=:").append(valueName);
				}
			}
			for (String key : removeKeys) {
				paramMap.remove(key);
			}
			paramMap.putAll(replacedFields);
		}
		return getCount(hql.toString(), paramMap);
	}

	public long getCount(Class<?> clazz, String[] fieldNames, Object[] fieldValues) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from ").append(clazz.getName()).append(" where 1=1");
		if (fieldNames != null && fieldNames.length > 0 && fieldValues != null && fieldValues.length > 0) {
			for (String name : fieldNames) {
				hql.append(" and ").append(name).append("=?");
			}
		}
		return getCount(hql.toString(), fieldValues);

	}

	public long getCount(Class<?> clazz) {
		return getCount(clazz, new String[] {}, new String[] {});
	}

	
	public long getCount(Class<?> clazz, String fieldName, Object fieldValue) {
		if (fieldName != null && !fieldName.equals("")) {
			return getCount(clazz, new String[] { fieldName }, new Object[] { fieldValue });
		}
		return getCount(clazz, new String[] {}, new String[] {});
	}

	@Override
	public <T> List<T> getList(String hql) {
		return getList(hql, -1, -1);
	}

	@Override
	public <T> List<T> getList(final String hql, final Object params[]) {
		return getList(hql, params, -1, -1);
	}

	@Override
	public <T> List<T> getList(final String hql, final Map<String, Object> paramMap) {
		return getList(hql, paramMap, -1, -1);
	}

	@Override
	public <T> List<T> getList(String hql, int start, int limit) {
		return getList(hql, new Object[] {}, start, limit);
	}

	@Override
	public <T> List<T> getList(String hql, PaginatedHelper paginatedHelper) {
		return getList(hql, new Object[] {}, paginatedHelper);
	}

	@Override
	public <T> List<T> getList(String hql, Object[] params, PaginatedHelper paginatedHelper) {
		return getList(hql, params, paginatedHelper.getStartIndex(), paginatedHelper.getPageSize());
	}

	@Override
	public <T> List<T> getList(String hql, Map<String, Object> paramMap, PaginatedHelper paginatedHelper) {
		return getList(hql, paramMap, paginatedHelper.getStartIndex(), paginatedHelper.getPageSize());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(final String hql, final Object[] params, final int start, final int limit) {
		Query query=entityManager.createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i+1, params[i]);
			}
		}
		if (start > -1 && limit > -1) {
			query.setMaxResults(limit);
			query.setFirstResult(start);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(final String hql, final Map<String, Object> paramMap, final int start, final int limit) {
		Query query = entityManager.createQuery(hql);
		if (paramMap != null && paramMap.size() > 0) {
			for (Map.Entry<String, Object> en : paramMap.entrySet()) {
				String key = en.getKey();
				Object value = en.getValue();
				if (EstimateTypeUtil.isArray(value)) {
					query.setParameter(key, Arrays.asList((Object[])value));
				} else if (EstimateTypeUtil.isCollection(value)) {
					query.setParameter(key, (Collection<?>) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		if (start > -1 && limit > -1) {
			query.setMaxResults(limit);
			query.setFirstResult(start);
		}
		return query.getResultList();

	}

	@Override
	public <T> List<T> getList(Class<?> clazz) {
		return getList(clazz, null, -1, -1, null);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap) {
		return getList(clazz, paramMap, -1, -1);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap, int start, int limit) {
		return getList(clazz, paramMap, start, limit, null);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap, int start, int limit,
			LinkedHashMap<String, String> orderBy) {
		StringBuffer hql = new StringBuffer();
		hql.append("from ").append(clazz.getName()).append(" where 1=1");
		if (paramMap != null && paramMap.size() > 0) {
			Map<String, Object> replacedFields = new HashMap<String, Object>();
			List<String> removeKeys = new ArrayList<String>();
			for (Map.Entry<String, Object> en : paramMap.entrySet()) {
				String valueName = en.getKey();
				if (valueName.indexOf(".") > -1) {
					removeKeys.add(en.getKey());
					valueName = en.getKey().replaceAll("[.]", "_");
					replacedFields.put(valueName, en.getValue());
				}
				if (EstimateTypeUtil.isArray(en.getValue()) || EstimateTypeUtil.isCollection(en.getValue())) {
					hql.append(" and ").append(en.getKey()).append(" in (:").append(valueName).append(")");
				} else {
					hql.append(" and ").append(en.getKey()).append("=:").append(valueName);
				}
			}
			for (String key : removeKeys) {
				paramMap.remove(key);
			}
			paramMap.putAll(replacedFields);
		}
		if (orderBy != null && orderBy.size() > 0) {
			hql.append(" order by ");
			for (Map.Entry<String, String> en : orderBy.entrySet()) {
				hql.append(en.getKey()).append(" ").append(en.getValue()).append(", ");
			}
			hql.deleteCharAt(hql.lastIndexOf(","));
		}

		return getList(hql.toString(), paramMap, start, limit);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap, PaginatedHelper paginatedHelper,
			LinkedHashMap<String, String> orderBy) {
		if (paginatedHelper != null) {
			return getList(clazz, paramMap, paginatedHelper.getStartIndex(), paginatedHelper.getPageSize(), orderBy);

		} else {
			return getList(clazz, paramMap, -1, -1, orderBy);
		}
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, Map<String, Object> paramMap, PaginatedHelper paginatedHelper) {
		return getList(clazz, paramMap, paginatedHelper.getStartIndex(), paginatedHelper.getPageSize());

	}

	@Override
	public <T> List<T> getList(Class<?> clazz, final Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderBy) {
		return getList(clazz, paramMap, -1, -1, orderBy);
	}

	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String[] fields, Object values[],
			LinkedHashMap<String, String> orderBy) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (fields != null && fields.length > 0 && fields.length == values.length && values != null
				&& values.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				paramMap.put(fields[i], values[i]);
			}
		}
		return getList(clazz, paramMap, orderBy);
	}

	public <T> List<T> getList(Class<?> clazz, LinkedHashMap<String, String> orderBy) {
		return getList(clazz, null, -1, -1, orderBy);
	}

	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String fieldName, Object value,
			LinkedHashMap<String, String> orderBy) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (fieldName != null && !fieldName.equals("") && value != null) {
			paramMap.put(fieldName, value);
		}
		return getList(clazz, paramMap, orderBy);
	}

	/**
	 * 通过某个属性，获取一个数据库映射的实例对象列表
	 */
	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String fieldName, Object value) {
		return getList(clazz, new String[] { fieldName }, new Object[] { value });
	}

	/**
	 * 通过多个属性条件，获取一个数据库映射的实例对象列表
	 */
	@Override
	public <T> List<T> getList(Class<? extends T> clazz, String[] fields, Object values[]) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				paramMap.put(fields[i], values[i]);
			}
		}
		return getList(clazz, paramMap);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, final int start, final int limit,
			LinkedHashMap<String, String> orderBy) {
		return getList(clazz, null, start, limit, orderBy);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, PaginatedHelper paginatedHelper, LinkedHashMap<String, String> orderBy) {
		return getList(clazz, null, paginatedHelper.getStartIndex(), paginatedHelper.getPageSize(), orderBy);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, int start, int limit) {
		return getList(clazz, start, limit, null);
	}

	@Override
	public <T> List<T> getList(Class<?> clazz, PaginatedHelper paginatedHelper) {
		return getList(clazz, paginatedHelper, null);
	}

	@Override
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName) {
		return getFieldList(clazz, fieldName, null);
	}

	@Override
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName, Map<String, Object> paramMap) {
		return getFieldList(clazz, fieldName, paramMap, null);
	}

	@Override
	public <T> List<T> getFieldList(Class<?> clazz, String fieldName, Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderBy) {
		StringBuffer hql = new StringBuffer();
		hql.append("select ").append(fieldName).append(" from ").append(clazz.getName()).append(" where 1=1 ");
		if (paramMap != null && paramMap.size() > 0) {
			Map<String, Object> replacedFields = new HashMap<String, Object>();
			List<String> removeKeys = new ArrayList<String>();
			for (Map.Entry<String, Object> en : paramMap.entrySet()) {
				String valueName = en.getKey();
				if (valueName.indexOf(".") > -1) {
					removeKeys.add(en.getKey());
					valueName = en.getKey().replaceAll("[.]", "_");
					replacedFields.put(valueName, en.getValue());
				}
				if (EstimateTypeUtil.isArray(en.getValue()) || EstimateTypeUtil.isCollection(en.getValue())) {
					hql.append(" and ").append(en.getKey()).append(" in (:").append(valueName).append(")");
				} else {
					hql.append(" and ").append(en.getKey()).append("=:").append(valueName);
				}
			}
			for (String key : removeKeys) {
				paramMap.remove(key);
			}
			paramMap.putAll(replacedFields);
		}

		if (orderBy != null && orderBy.size() > 0) {
			hql.append(" order by ");
			for (Map.Entry<String, String> en : orderBy.entrySet()) {
				hql.append(en.getKey()).append(" ").append(en.getValue()).append(", ");
			}
			hql.deleteCharAt(hql.lastIndexOf(","));
		}
		return getList(hql.toString(), paramMap, -1, -1);
	}

	@Override
	public boolean isFieldExist(Class<?> clazz, String fieldName, Object fieldValue, String pkName, Object pkVal) {
		StringBuffer hql = new StringBuffer("select count(*) from ");
		hql.append(clazz.getName()).append(" where ").append(fieldName).append("=?");
		List<Object> params = new ArrayList<Object>();
		params.add(fieldValue);
		if (pkVal != null) {
			hql.append(" and ").append(pkName).append("!=?");
			params.add(pkVal);
		}
		long count = getCount(hql.toString(), params.toArray());
		return count > 0 ? true : false;
	}

	@Override
	public boolean isFieldExist(Class<?> clazz, String[] fieldNames, Object[] fieldValues, String pkName,
			Object pkVal) {
		StringBuffer hql = new StringBuffer("select count(*) from " + clazz.getName() + " where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < fieldNames.length; i++) {
			hql.append(" and ").append(fieldNames[i]).append("=? ");
			params.add(fieldValues[i]);
		}

		if (pkVal != null) {
			hql.append(" and ").append(pkName).append("!=?");
			params.add(pkVal);
		}
		long count = getCount(hql.toString(), params.toArray());
		return count > 0 ? true : false;
	}
	
}
