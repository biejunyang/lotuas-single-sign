package com.bjy.lotuas.common.util;

import java.util.Collection;

public final class EstimateTypeUtil {

	private EstimateTypeUtil(){}
	
	public static boolean isArray(Object object) {
		if (object == null) {
			return false;
		}
		Class<?> clazz = object.getClass();
		if (clazz.isArray()) {
			return true;
		}
		return false;
	}

	public static boolean isCollection(Object object) {
		if (object instanceof Collection){
			return true;
		}
		return false;
	}
}
