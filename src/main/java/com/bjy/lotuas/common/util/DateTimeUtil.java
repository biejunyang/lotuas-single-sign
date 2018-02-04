package com.bjy.lotuas.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {

	public final static SimpleDateFormat[] defaultTimeFormats = new SimpleDateFormat[] {
			new SimpleDateFormat("HH:mm:ss") };

	public final static SimpleDateFormat[] defaultDateFormats = new SimpleDateFormat[] {
			new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyyMMdd"), new SimpleDateFormat("yyyy/MM/dd") };

	/**
	 * 字符串转成日期
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseToDate(String dateStr, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateStr);
	}

	/**
	 * 字符串转成日期
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parsetToDate(String dateStr) throws ParseException {
		Date date = null;
		for (SimpleDateFormat defaultFormat : defaultDateFormats) {
			try {
				date = defaultFormat.parse(dateStr);
				break;
			} catch (ParseException e) {
				continue;
			}
		}
		if (date == null) {
			throw new ParseException("日期格式转换错误!", 0);
		}
		return date;
	}

	/**
	 * 获取当前时间的timestamp对象
	 * 
	 * @return
	 */
	public static Timestamp getNowTimeStamp() {
		return new Timestamp((new Date().getTime()));
	}

	public static String format(Date date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	public static String format(Object date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 某年某月的第一天
	 */
	public static Date firstDate(int year, int month) throws ParseException {

		Calendar cal = Calendar.getInstance();
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = cal.getTime();
		return firstDate;

	}

	/**
	 * 某年某月的第最后一天
	 */
	public static Date lastDate(int year, int month) throws ParseException {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);

		Date lastDate = cal.getTime();
		return lastDate;

	}

	/**
	 * 两个日期之间的月份
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		// max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 两个日期之间的月份,如果有移除时间，则需要所有包括月份
	 */
	public static List<String> getMonthBetweenHaveRemove(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		// max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 计算当月的最后一天
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取当月的 天数
	 */
	public static int getCurrentMonthDay() {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据日期 找到对应日期的 星期
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfweek = "-1";
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = myFormatter.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String str = formatter.format(myDate);
			dayOfweek = str;

		} catch (Exception e) {
			System.out.println("错误!");
		}
		return dayOfweek;
	}

	/**
	 * 日期加1天
	 */
	public static String addOneday(String today) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = new Date(f.parse(today).getTime() + 24 * 3600 * 1000);
			return f.format(d);
		} catch (Exception ex) {
			return "输入格式错误";
		}
	}


}
