package com.bjy.lotuas.common.converter;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bjy.lotuas.common.util.TypeUtil;

public class SimpleConverter implements TypeConverter{
	
	private NumberFormat nf = null;

	private SimpleDateFormat sf = null;
	
	public SimpleConverter(String nfPattern, String sfPattern) {
		this(new DecimalFormat(nfPattern),new SimpleDateFormat(sfPattern));
	}
	
	public SimpleConverter(NumberFormat nf) {
		this(nf,DefaultConverterConstant.getDafaultDateFormats()[0]);
	}
	
	public SimpleConverter(SimpleDateFormat sf) {
		this(DefaultConverterConstant.getDafaultNumberFormat(),sf);
	}
	
	public SimpleConverter(NumberFormat nf, SimpleDateFormat sf) {
		super();
		this.nf = nf;
		this.sf = sf;
	}
	
	@Override
	public <T> T convert(Type type, Object obj) throws TypeConverterException {
		Object value = null;
		boolean flag = false;
		T t = null;
		if (type instanceof Class<?>
				&& Date.class.isAssignableFrom((Class<?>) type)) {
			if (obj != null) {
				String val = obj.toString();
				try {
					value = sf.parse(val);
					flag = true;
				} catch (ParseException e0) {
					flag = false;
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
			}
		}
		try{
			t = getTarket(flag?value:obj, type);
			return t;
		}catch(Exception e){
			throw new TypeConverterException(e);
		}
	}
	
	<T> T getTarket(Object obj,Type type) throws TypeConverterException{
		try{
			return TypeUtil.cast(obj, type);
		}catch(Exception e){
			throw new TypeConverterException("默认的类型转换器在转换数据过程中出错！", e);
		}
	}
}
