package com.bjy.lotuas.common.util;

import java.lang.reflect.Field;

/**
 * Reflection Operate Utils
 * @author biejunyang
 *
 */
public class ReflectUtil {
	public static void setAccessibleValue(Class<?> clazz, String filedName,Object value)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field field = clazz.getDeclaredField(filedName);
		field.setAccessible(true);
		field.set(clazz, value);
	}
	
	public static Object getAccessibleValue(String filedName,Object target)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		if(target==null)
			return null;
		Field field = target.getClass().getDeclaredField(filedName);
		field.setAccessible(true);
		return field.get(target);
	}
	
	
}
