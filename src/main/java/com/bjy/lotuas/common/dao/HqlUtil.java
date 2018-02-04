package com.bjy.lotuas.common.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.bjy.lotuas.common.util.StringUtil;

public class HqlUtil
{
	/**
	 * 生成where in 的条件sql,格式：(?,?,?)
	 * @param length
	 * @return
	 */
	public static String createInHql(int length){
		if(length>0){
			StringBuffer hql=new StringBuffer("(");
			for(int i=0; i<length; i++){
				hql.append("?,");
			}
			hql.deleteCharAt(hql.lastIndexOf(","));
			hql.append(")");
			return hql.toString();
		}
		return null;
	}
	
	/**
	 * 生成like查询中_匹配sql
	 * @param length
	 * @return
	 */
	public static String createFullString(int length){
		StringBuffer buf=new StringBuffer();
		for(int i=0; i<length; i++){
			buf.append("_");
		}
		return buf.toString();
	}
	
	
	/**
	 * sort格式:
	 * 	id,asc,
	 * 	id,asc;name,desc,age
	 * @param sort
	 * @return
	 */
	public static Sort getSort(String sort) {
		if(StringUtil.isNotNull(sort)) {
			String[] sorts=sort.split(";");
			List<Order> orders=new ArrayList<Order>();
			for(String str: sorts) {
				String[] strs=str.split(",");
				Order order=null;
				if(strs.length>1) {
					order=new Order(Direction.fromString(strs[1]), strs[0]);
				}else {
					order=new Order(Direction.ASC, strs[0]);
				}
				orders.add(order);
			}
			return new Sort(orders);
		}
		return null;
	}
	
	
	/**
	 * sort格式:
	 * 	id,asc,
	 * 	id,asc;name,desc,age
	 * @param sort
	 * @return
	 */
	public static LinkedHashMap<String, String> getSortMap(String sort) {
		if(StringUtil.isNotNull(sort)) {
			String[] sorts=sort.split(";");
			LinkedHashMap<String, String> orderMap=new LinkedHashMap<String, String>();
			for(String str: sorts) {
				String[] strs=str.split(",");
				if(strs.length>1) {
					orderMap.put(strs[0], strs[1]);
				}else {
					orderMap.put(strs[0], "asc");
				}
			}
			return orderMap;
		}
		return null;
	}
	
}
