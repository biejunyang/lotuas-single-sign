package com.bjy.lotuas.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;


public class StringUtil
{
	/**
	 * 判断值非空,不为null和""
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(String value){
		return (value!=null && !value.equals(""));
	}
	

	/**
	 * 生成流水号
	 * @return
	 */
	public static String createSerialNumber(int serialLength, int currentNum){
		String str=String.valueOf((++currentNum));
		StringBuffer buf=new StringBuffer();
		for(int i=0; i<serialLength-str.length(); i++){
			buf.append("0");
		}
		return buf.append(str).toString();
	}

	/**
	 * 字符串拼接
	 * @param values
	 * @param seperator
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static String appendList(List<?> values, String seperator, String attributeName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(CollectionUtil.isNotNull(values)){
			StringBuffer buf=new StringBuffer();
			for(Object val:values){
				if(isNotNull(attributeName)){
					buf.append(PropertyUtils.getProperty(val, attributeName)).append(seperator);					
				}else{
					buf.append(val.toString()).append(seperator);
				}
			}
			buf.deleteCharAt(buf.lastIndexOf(seperator));
			return buf.toString();
		}
		return null;
	}
	
	public static String appendList(List<?> values, String seperator) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return appendList(values, seperator, null);
	}
	
	
	public static String appendList(List<?> values) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return appendList(values, ",");
	}
}
