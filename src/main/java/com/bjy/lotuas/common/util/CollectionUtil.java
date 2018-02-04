package com.bjy.lotuas.common.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 集合帮助类
 * @author DELL
 *
 */
public class CollectionUtil
{
	/**
	 * 判断集合不为空且,元素个数>0
	 * @param col
	 * @return
	 */
	public static boolean isNotNull(Collection<?> col){
		return col!=null && col.size()>0 ? true : false;
	}
	
	/**
	 * 判断集合中的内容是否相同
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static boolean isEqual(Collection<?> col1, Collection<?> col2){
		if(col1==null && col2==null)
			return true;
		if(col1!=null && col2!=null){
			if(col1.size()== col2.size()){
				int size=col1.size();
				Set<Object> set=new HashSet<Object>(col1);
				set.addAll(col2);
				return set.size()==size;
			}
			return false;
		}
		return false;
	}
}
