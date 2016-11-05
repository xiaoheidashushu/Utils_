package org.fullmatel.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author xiaoxiang_hou@live.cn
 */
public class FormatFactory {

	private FormatFactory() {

	}

	@SuppressWarnings("rawtypes")
	public static FormatFactory buildFormatFactory() {
		return new FormatFactory();
	}

	/**
	 * 传入实体类的完全限定名 列 org.entitydemo.entity.ClassName
	 * 
	 * 传入HttpServletRequest 对象
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("rawtypes")
	public Object inputRequestReturnEntity(Class clazz, HttpServletRequest request)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		// Class clazz = CreateClass(ClassName);
		// 便利request中的所有请求参数及参数值
		// Enumeration<String> enumeration = request.getParameterNames();
		Map<String, String[]> map = request.getParameterMap();
		// 得到请求参数列表
		Set<String> names = analysisProperty(map);
		// 开始填充实体类
		Object object = stuffEntity(clazz, map, names);
		return object;
	}

	/**
	 * 利用类的完全限定名 动态加载 实例化此对象
	 */
	public Class CreateClass(String ClassName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clazz = Class.forName(ClassName);
		return clazz;
	}

	/**
	 * 实例化此类
	 */
	public Object CreatenewInstance(Class clazz) {
		Object obj = null;
		if (clazz != null) {
			try {
				obj = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 解析 并且 得到请求参数列表
	 * 
	 * @return 集合
	 */
	public Set<String> analysisProperty(Map<String, String[]> maps) {
		Set<String> Names = null;
		if (!maps.isEmpty()) {
			Names = maps.keySet();// 得到request的请求参数 set集合
		}
		return Names;
	}

	/**
	 * 填充实体类 以实体类为基准 其中包含 比较存在相应属性 得到属性类型 利用set方法进行填充
	 * 
	 * 传入值为 持久类的类对象 请求参数列表 请求参数set集合
	 * 
	 * @param Class
	 * @param Map<String,
	 *            String[]>
	 * @param Set<String>
	 * @return 填充完毕的对象
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object stuffEntity(Class clazz, Map<String, String[]> map, Set<String> names) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		// 实例化此对象
		Object obj = CreatenewInstance(clazz);

		Field[] fields = clazz.getDeclaredFields();
		// Method[] methods = clazz.getMethods();

		// 以持久化类的属性为 基准 每比较一次属性 就便利循环一遍请求参数集合
		for (Field f : fields) {
			String name = f.getName();
			for (String param : names) {
				// 如果存在 相同参数
				if (StringUtilAndTools.ComparisonNameWithProperty(param, name)) {
					String paramvalue = map.get(param)[0];// 得到改请求参数 的值
					obj = setPropertyValue(f, paramvalue, obj, clazz);
					// 开始对属性赋值
					break;
				}
			}

		}
		return obj;
	}

	/**
	 * 判断类型 并且 进行赋值
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object setPropertyValue(Field field, String paramvalue, Object obj, Class clazz)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {

		// 得到当前类的 方法名和 传入值类型
		Method method = clazz.getDeclaredMethod(StringUtilAndTools.CreateFieldToMethod(field.getName()),field.getType());
		// 利用当前方法传入 已经实例化的对象 并且 传入值类型
		method.invoke(obj, StringUtilAndTools.judgmentC(field.getType(), paramvalue));

		return obj;
	}
}
