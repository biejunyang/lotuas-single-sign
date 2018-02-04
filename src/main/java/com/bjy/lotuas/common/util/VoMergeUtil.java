package com.bjy.lotuas.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjy.lotuas.common.exception.VOCastException;

public final class VoMergeUtil {
	private VoMergeUtil() {
	}

	public static Map<String, Object> merge(Map<String, Object> src,
			Map<String, Object> target) {
		return merge(src, target, true);
	}

	public static Map<String, Object> merge(Map<String, Object> src,
			Map<String, Object> target, boolean cover) {
		if (src == null)
			return target;
		if (target == null)
			return src;
		Map<String, Object> rt = new HashMap<String, Object>();
		rt.putAll(src);
		if (cover) {
			rt.putAll(target);
			return rt;
		} else {
			for (Map.Entry<String, Object> entry : target.entrySet()) {
				if (!src.containsKey(entry.getKey())) {
					src.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return rt;
	}

	public static Map<String, Object> merge(Map<String, Object> src,
			Object target) throws VOCastException {
		return merge(src, VoUtil.describe(target), true);
	}

	public static Map<String, Object> merge(Map<String, Object> src,
			Object target, boolean cover) throws VOCastException {
		return merge(src, VoUtil.describe(target), cover);
	}

	public static Map<String, Object> merge(Object src,
			Map<String, Object> target) throws VOCastException {
		return merge(VoUtil.describe(src), target, true);
	}

	public static Map<String, Object> merge(Object src,
			Map<String, Object> target, boolean cover) throws VOCastException {
		return merge(VoUtil.describe(src), target, cover);
	}

	public static Map<String, Object> merge(Object src, Object target)
			throws VOCastException {
		return merge(VoUtil.describe(src), VoUtil.describe(target), true);
	}

	public static Map<String, Object> merge(Object src, Object target,
			boolean cover) throws VOCastException {
		return merge(VoUtil.describe(src), VoUtil.describe(target), cover);
	}

	public static Map<String, Object> merge(Object[] beans)
			throws VOCastException {
		return merge(Arrays.asList(beans), true);
	}

	public static Map<String, Object> merge(Object[] beans, boolean cover)
			throws VOCastException {
		if (beans == null) {
			return null;
		}
		return merge(Arrays.asList(beans), cover);
	}

	public static Map<String, Object> merge(List<Object> beans)
			throws VOCastException {
		if (beans == null) {
			return null;
		}
		Map<String, Object> rt = new HashMap<String, Object>();
		for (Object bean : beans) {
			rt = merge(rt, bean, true);
		}
		return rt;
	}

	public static Map<String, Object> merge(List<Object> beans, boolean cover)
			throws VOCastException {
		if (beans == null) {
			return null;
		}
		Map<String, Object> rt = new HashMap<String, Object>();
		for (Object bean : beans) {
			rt = merge(rt, bean, cover);
		}
		return rt;
	}
}
