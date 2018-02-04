package com.bjy.lotuas;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjy.lotuas.access.entity.TResourceBean;
import com.bjy.lotuas.common.dao.impl.JpaQueryDaoImpl;
import com.bjy.lotuas.common.dao.impl.QueryDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {MainApplication.class})
public class TestQuerDao {
	
	@Autowired
	private QueryDaoImpl queryDaoImpl;
	
	@Autowired
	private JpaQueryDaoImpl jpaQueryDaoImpl;
	
	@Test
	public void testQueryDao() {
		String hql="from TResourceBean where resourceId = 1";
		TResourceBean resource=queryDaoImpl.getSingleResult(hql, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		hql="from TResourceBean where resourceId = ? and deleteFlag = ?";
		resource=queryDaoImpl.getSingleResult(hql, new Object[] {1, 0}, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		hql="from TResourceBean where resourceId = :resourceId and deleteFlag = :deleteFlag";
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("resourceId", 1);
		paramMap.put("deleteFlag", 0);
		resource=queryDaoImpl.getSingleResult(hql, paramMap, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		
		hql="from TResourceBean where resourceId in (:resourceIds) and deleteFlag = :deleteFlag";
		paramMap=new HashMap<String, Object>();
//		paramMap.put("resourceIds", Arrays.asList(1, 1, 1));
		paramMap.put("resourceIds", new Object[] {1, 1, 1});
		paramMap.put("deleteFlag", 0);
		resource=queryDaoImpl.getSingleResult(hql, paramMap, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		hql="select new map(resourceName as name, resourceCode as code) from TResourceBean where resourceId = ? and deleteFlag = ?";
		@SuppressWarnings("unchecked")
		Map<String, Object> map=queryDaoImpl.getSingleResult(hql, new Object[] {1, 0}, Map.class);
		Assert.assertEquals("权限管理", map.get("name"));
		
		
		hql="select count(*) from TResourceBean where deleteFlag = 0";
		Assert.assertEquals(2, queryDaoImpl.getCount(hql));
		
		hql="select count(*) from TResourceBean where deleteFlag = ?";
		Assert.assertEquals(2, queryDaoImpl.getCount(hql, new Object[] {0}));
		
		hql="select count(*) from TResourceBean where deleteFlag = :deleteFlag";
		paramMap=new HashMap<String, Object>();
		paramMap.put("deleteFlag", 0);
		Assert.assertEquals(2, queryDaoImpl.getCount(hql, paramMap));
		
		
//		Assert.assertEquals(2, queryDaoImpl.getCount(TResourceBean.class));
//		
//		Assert.assertEquals(2, queryDaoImpl.getCount(TResourceBean.class));
	}
		
	
	@Test
	public void testJpaQueryDao() {
		String hql="from TResourceBean where resourceId = 1";
		TResourceBean resource=jpaQueryDaoImpl.getSingleResult(hql, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		hql="from TResourceBean where resourceId = ? and deleteFlag = ?";
		resource=jpaQueryDaoImpl.getSingleResult(hql, new Object[] {1, 0}, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		hql="from TResourceBean where resourceId = :resourceId and deleteFlag = :deleteFlag";
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("resourceId", 1);
		paramMap.put("deleteFlag", 0);
		resource=jpaQueryDaoImpl.getSingleResult(hql, paramMap, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		hql="from TResourceBean where resourceId in (:resourceIds) and deleteFlag = :deleteFlag";
		paramMap=new HashMap<String, Object>();
//		paramMap.put("resourceIds", Arrays.asList(1, 1, 1));
		paramMap.put("resourceIds", new Object[] {1, 1, 1});
		paramMap.put("deleteFlag", 0);
		resource=jpaQueryDaoImpl.getSingleResult(hql, paramMap, TResourceBean.class);
		Assert.assertEquals("权限管理", resource.getResourceName());
		
		
		hql="select new map(resourceName as name, resourceCode as code) from TResourceBean where resourceId = ? and deleteFlag = ?";
		@SuppressWarnings("unchecked")
		Map<String, Object> map=jpaQueryDaoImpl.getSingleResult(hql, new Object[] {1, 0}, Map.class);
		Assert.assertEquals("权限管理", map.get("name"));
		
		hql="select count(*) from TResourceBean where deleteFlag = 0";
		Assert.assertEquals(2, jpaQueryDaoImpl.getCount(hql));
		
		
		hql="select count(*) from TResourceBean where deleteFlag = ?";
		Assert.assertEquals(2, jpaQueryDaoImpl.getCount(hql, new Object[] {0}));
		
		hql="select count(*) from TResourceBean where deleteFlag = :deleteFlag";
		paramMap=new HashMap<String, Object>();
		paramMap.put("deleteFlag", 0);
		Assert.assertEquals(2, jpaQueryDaoImpl.getCount(hql, paramMap));
	}
	
	
}
