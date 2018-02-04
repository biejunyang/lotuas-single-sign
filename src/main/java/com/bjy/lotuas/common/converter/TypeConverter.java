package com.bjy.lotuas.common.converter;

import java.lang.reflect.Type;

public interface TypeConverter {
	public <T> T convert(Type type,Object obj) throws TypeConverterException;
}
