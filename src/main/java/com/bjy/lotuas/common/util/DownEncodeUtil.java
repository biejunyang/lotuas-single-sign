package com.bjy.lotuas.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public final class DownEncodeUtil {
	private DownEncodeUtil() {

	}

	public static String getEncodeFileName(String fileName, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String rt = null;
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {// firefox浏览器
			rt = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {// IE浏览器
			rt = URLEncoder.encode(fileName, "UTF-8");
		} else {
			rt = toUtf8String(fileName);
		}
		return rt;
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b = null;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
}
