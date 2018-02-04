package com.bjy.lotuas.common.converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class DefaultConverterConstant {
	private DefaultConverterConstant() {
	}

	private final static SimpleDateFormat[] dafaultDateFormats = {
		new SimpleDateFormat("yyyy-MM-dd"),
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
		new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
	};
	private final static NumberFormat dafaultNumberFormat = new DecimalFormat("0");

	public static SimpleDateFormat[] getDafaultDateFormats() {
		return dafaultDateFormats;
	}

	public static NumberFormat getDafaultNumberFormat() {
		return dafaultNumberFormat;
	}
}
