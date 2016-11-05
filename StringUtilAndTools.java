package org.fullmatel.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUtilAndTools {
	public static boolean ComparisonNameWithProperty(String parametername, String propertyname) {

		if (!isBlank(propertyname) && !isBlank(parametername)) {
			if (CheckEquals(StrToLower(propertyname), StrToLower(parametername))) {
				return true;
			}
			if (Checkinclude(StrToLower(propertyname), StrToLower(parametername))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 去空格 判断是否为空 爲空 返回
	 */
	public static boolean isBlank(String str) {
		str = str.trim();
		if (str.length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 将字符串全部转换为小写
	 */
	public static String StrToLower(String str) {
		return str.toLowerCase();
	}

	/**
	 * 
	 * 检查是否相等 相等返回true
	 */
	public static boolean CheckEquals(String one, String two) {
		if (one.equals(two)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 如果包含返回true 否则返回false
	 */
	public static boolean Checkinclude(String property, String param) {
		// 请求参数 是否包含 属性名

		// -1表示 不包含
		if (param.indexOf(property) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 首字母转换为大写 添加 set前綴
	 */
	public static String CreateFieldToMethod(String propertyname) {
		StringBuilder builder = new StringBuilder("set" + propertyname);
		builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
		return builder.toString();
	}

	/**
	 * 传入参数 并且判断方法 对值进行相应判断
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object judgmentC(Class clazzType, String paramvalue) {

		// 得到此类的 SimpleName
		String typename = clazzType.getSimpleName();

		// 得到 转换方法类 的类型
		Class clazz = ConvertClazz.class;

		Object object = null;
		try {
			//
			Object ConvertObj = clazz.newInstance();
			Method method = clazz.getDeclaredMethod("convert" + typename, String.class);
			object = method.invoke(ConvertObj, paramvalue);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

}
