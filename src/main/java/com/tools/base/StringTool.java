package com.tools.base;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class StringTool {
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return (str == null || "".equals(str));
	}
	
	/**
	 * 判断字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}

	/**
	 * List按指定分隔符转字符串
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String listToString(List<?> list, String separator){
		if(list.isEmpty()){
			return "";
		}
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	/**
	 * 验证map种当前key是否存在或是否是空字符串 -- 非空
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isKey(@SuppressWarnings("rawtypes") Map map, String key){
		return map.containsKey(key) && !"".equals(map.get(key));
	}
	
	/**
	 * 验证map种当前key是否存在或是否是空字符串 -- 空
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isNotKey(@SuppressWarnings("rawtypes") Map map, String key){
		return !map.containsKey(key) || "".equals(map.get(key));
	}

	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	/**
	 * 判断实体类属性是否为空
	 */
	public static boolean entityIsEmpty(Object obj) {
		if(obj instanceof Integer) {
			Integer parseInt = Integer.parseInt(String.valueOf(obj));
			if(parseInt!=0) {
				return false;
			}
			return true;
		}else {
			return (obj==null||"".equals(obj));
		}
	}
}
