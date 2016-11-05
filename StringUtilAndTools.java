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
	 * ȥ�ո� �ж��Ƿ�Ϊ�� ���� ����
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
	 * ���ַ���ȫ��ת��ΪСд
	 */
	public static String StrToLower(String str) {
		return str.toLowerCase();
	}

	/**
	 * 
	 * ����Ƿ���� ��ȷ���true
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
	 * �����������true ���򷵻�false
	 */
	public static boolean Checkinclude(String property, String param) {
		// ������� �Ƿ���� ������

		// -1��ʾ ������
		if (param.indexOf(property) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ����ĸת��Ϊ��д ��� setǰ�Y
	 */
	public static String CreateFieldToMethod(String propertyname) {
		StringBuilder builder = new StringBuilder("set" + propertyname);
		builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
		return builder.toString();
	}

	/**
	 * ������� �����жϷ��� ��ֵ������Ӧ�ж�
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object judgmentC(Class clazzType, String paramvalue) {

		// �õ������ SimpleName
		String typename = clazzType.getSimpleName();

		// �õ� ת�������� ������
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
