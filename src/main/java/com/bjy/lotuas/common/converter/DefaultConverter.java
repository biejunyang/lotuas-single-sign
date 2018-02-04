package com.bjy.lotuas.common.converter;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.bjy.lotuas.common.util.TypeUtil;

public class DefaultConverter implements TypeConverter {

	private final static SimpleDateFormat[] sfs = DefaultConverterConstant.getDafaultDateFormats();

	private final static NumberFormat nf = DefaultConverterConstant.getDafaultNumberFormat();

	@Override
	public <T> T convert(Type type, Object obj) throws TypeConverterException {
		Object value = null;
		boolean flag = false;
		T t = null;
		if (type instanceof Class<?> && java.util.Date.class.isAssignableFrom((Class<?>) type)) {
			if (obj != null) {
				String val = obj.toString();
				try {
					value = sfs[0].parse(val);
					flag = true;
				} catch (ParseException e0) {
					try {
						value = sfs[1].parse(val);
						flag = true;
					} catch (ParseException e1) {
						try {
							value = sfs[2].parse(val);
							flag = true;
						} catch (ParseException e2) {
							flag = false;
						}
					}
				}
			}
		}
		if (type == int.class || type == Integer.class) {
			try {
				value = nf.parse(obj.toString()).intValue();
				flag = true;
			} catch (ParseException e) {
				flag = false;
			}
		}
		if (type == long.class || type == Long.class) {
			try {
				value = nf.parse(obj.toString()).intValue();
				flag = true;
			} catch (ParseException e) {
				flag = false;
				throw new TypeConverterException(e);
			}
		}
		try{
			t = getBean(flag?value:obj, type);
			return t;
		}catch(Exception e){
			throw new TypeConverterException(e);
		}
	}

	private <T> T getBean(Object obj,Type type) throws TypeConverterException{
		try{
			return TypeUtil.cast(obj, type);
		}catch(Exception e){
			throw new TypeConverterException("默认的类型转换器在转换数据过程中出错！",e);
		}
	}
}
