package com.bjy.lotuas.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bjy.lotuas.common.converter.DefaultConverter;
import com.bjy.lotuas.common.converter.TypeConverter;
import com.bjy.lotuas.common.exception.VOCastException;
import com.bjy.lotuas.common.exception.VoOperateNotFindException;

public final class VoUtil {

	/**
	 * 通过属性名称，获取实例中定义的属性类型
	 * */
	public static Class<?> getPropertyType(Object bean, String property)throws VOCastException {
		try {
			return getPropertyDescriptor(bean, property).getPropertyType();
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}
	
	/**
	 * 通过属性名称，获取javabean中该属性的值
	 * */
	public static Object getPropertyValue(Object bean, String property)throws VOCastException {
		try {
			return getPropertyDescriptor(bean, property).getReadMethod().invoke(bean, new Object[] {});
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}

	/**
	 * 设置默认的转换器，通过反射，给某个属性动态赋值，如果当前值为空，将不调用
	 * */
	public static void setPropertyValueNotNull(Object bean, String property,Object value) throws VOCastException {
		try {
			setPropertyValueNotNull(bean, property, value,new DefaultConverter());
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}
	
	/**
	 * 设置转换器列表，通过反射，给某个属性动态赋值，如果当前值为空，将不调用。
	 * */
	public static void setPropertyValueNotNull(Object bean, String property,Object value,Object[] converters) throws VOCastException {
		try {
			setPropertyValueNotNull(bean, property, value, (TypeConverter)converters[0]);
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}
	
	/**
	 * 设置转换器，通过反射，给某个属性动态赋值，如果当前值为空，将不调用。
	 * */
	public static void setPropertyValueNotNull(Object bean, String property,Object value, TypeConverter converter) throws VOCastException {
		if (value != null) {
			try {
				Class<?> calzz = getPropertyType(bean, property);
				Method writer = getPropertyDescriptor(bean, property).getWriteMethod();
				if (calzz.isAssignableFrom(value.getClass())) {
					writer.invoke(bean, new Object[] { value });
				} else {
					Object writeValue = converter.convert(calzz, value);
					writer.invoke(bean, new Object[] { writeValue });
				}
			} catch (Exception e) {
				throw new VOCastException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 设置默认的转换器，通过反射，给某个属性动态赋值
	 * */
	public static void setPropertyValue(Object bean, String property,Object value) throws VOCastException {
		try {
			setPropertyValue(bean, property, value, new DefaultConverter());
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}

	/**
	 * 设置指定的转换器，通过反射，给某个属性动态赋值
	 * */
	public static void setPropertyValue(Object bean, String property,Object value, TypeConverter converter) throws VOCastException {
		try {
			if (value != null) {
				setPropertyValueNotNull(bean, property, value, converter);
			} else {
				BeanUtils.setProperty(bean, property, value);
			}
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}

	/**
	 * 设置默认的转换器，通过反射，动态将源javabean的属性值拷贝给目标javabean，为空和不存在的属性，将不会拷贝
	 * */
	public static void copyValuesNotNull(Object source, Object target)throws VOCastException {
		  copyValuesNotNull(source,target,null);
	}

	public static void copyValuesNotNull(Object source, Object target,Object[] converters)throws VOCastException {
		try {
			Map<String, Object> fieldsMap = describe(source);
			if (fieldsMap != null){
				for (Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
					if(converters==null){
					   setPropertyValueNotNull(target, entry.getKey(),entry.getValue());	
					}else{
						setPropertyValueNotNull(target, entry.getKey(),entry.getValue(),converters);
					}
				}	
			}
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}
	
	public static void copyValuesNotNull(Object source,Object target,boolean notExit) throws VOCastException {
		  copyValuesNotNull(source,target,notExit,null);
	}
	
	public static void copyValuesNotNull(Object source,Object target,boolean notExit,Object[] converters) throws VOCastException {
		if(notExit){
			if(converters==null){
				copyValuesNotNull(source,target);	
			}else{
				copyValuesNotNull(source,target,converters);
			}
		}else{
			Map<String, Object> sourcetMap = describe(source);
			Map<String, Object> targetMap = describe(target);
			if(sourcetMap!=null){
				for(String key : sourcetMap.keySet()){
					if(targetMap.containsKey(key) && sourcetMap.get(key)!=null 
							//&& !"".equals(sourcetMap.get(key).toString())
					  ){
						if(converters==null){
							setPropertyValueNotNull(target,key,sourcetMap.get(key));	
						}else{
							setPropertyValueNotNull(target,key,sourcetMap.get(key),converters);
						}
					}
				}
			}
		}
	}
	
	public static Map<String, Object> describe(Object bean) throws VOCastException {
		try {
			Map<String, Object> fieldsMap = new HashMap<String, Object>();
			PropertyDescriptor[] PropertyDescriptors = getPropertyDescriptors(bean);
			if (PropertyDescriptors != null) {
				for (PropertyDescriptor propertyDescriptor : PropertyDescriptors) {
					String fieldName = propertyDescriptor.getName();
					Method m = propertyDescriptor.getReadMethod();
					Object value = m.invoke(bean, new Object[] {});
					fieldsMap.put(fieldName, value);
				}
			}
			return fieldsMap;
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}

	public static void populate(Object target, Map<String, Object> properties)
			throws VOCastException {
		try {
			if (target != null && properties != null) {
				Map<String, Object> targetMap = describe(target);
				for(Map.Entry<String, Object> entry : properties.entrySet()){
					if(targetMap.containsKey(entry.getKey())){
						setPropertyValue(target,entry.getKey(),entry.getValue());
					}
				}
			}
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}

	public static PropertyDescriptor[] getPropertyDescriptors(Object bean)
			throws IntrospectionException, VOCastException {
		try {
			if (bean != null) {
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(),Object.class);
				return beanInfo.getPropertyDescriptors();
			}
			return null;
		} catch (Exception e) {
			throw new VOCastException(e.getMessage(), e);
		}
	}

	public static PropertyDescriptor getPropertyDescriptor(Object bean,String property) throws IntrospectionException, VOCastException {
		PropertyDescriptor propertyDescriptor = null;
		try {
			propertyDescriptor = new PropertyDescriptor(property,bean.getClass());
			return  propertyDescriptor;
		} catch (Exception e) {
			throw new VoOperateNotFindException("不存在该属性描述器！");
		} 
	}
	
	public static boolean existProperty(Object bean,String property){
		boolean exist = false;
		try {
			PropertyDescriptor pd = VoUtil.getPropertyDescriptor(bean, property);
			if (pd != null) {
				exist = true;
			}
		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}
	
	
	/**
	 * 返回一个实例，如果出现空，就返回null
	 * **/
	@SuppressWarnings("unchecked")
	public static <T> T returnTarget(Object o){
		try{
			return o==null?null:(T) o;
		}catch(NullPointerException e){
			return null;	
		}
	}
	
	/**
	 * 从列表中返回一个实例，如果为空列表则返回null;
	 * */
	public static <T> T returnOneTarget(List<?> list){
		if(list==null || list.size() == 0){
			return null;
		}else{
			return returnTarget(list.get(0));
		}
	}
}
