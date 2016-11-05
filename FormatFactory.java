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
	 * ����ʵ�������ȫ�޶��� �� org.entitydemo.entity.ClassName
	 * 
	 * ����HttpServletRequest ����
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
		// ����request�е������������������ֵ
		// Enumeration<String> enumeration = request.getParameterNames();
		Map<String, String[]> map = request.getParameterMap();
		// �õ���������б�
		Set<String> names = analysisProperty(map);
		// ��ʼ���ʵ����
		Object object = stuffEntity(clazz, map, names);
		return object;
	}

	/**
	 * ���������ȫ�޶��� ��̬���� ʵ�����˶���
	 */
	public Class CreateClass(String ClassName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clazz = Class.forName(ClassName);
		return clazz;
	}

	/**
	 * ʵ��������
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
	 * ���� ���� �õ���������б�
	 * 
	 * @return ����
	 */
	public Set<String> analysisProperty(Map<String, String[]> maps) {
		Set<String> Names = null;
		if (!maps.isEmpty()) {
			Names = maps.keySet();// �õ�request��������� set����
		}
		return Names;
	}

	/**
	 * ���ʵ���� ��ʵ����Ϊ��׼ ���а��� �Ƚϴ�����Ӧ���� �õ��������� ����set�����������
	 * 
	 * ����ֵΪ �־��������� ��������б� �������set����
	 * 
	 * @param Class
	 * @param Map<String,
	 *            String[]>
	 * @param Set<String>
	 * @return �����ϵĶ���
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object stuffEntity(Class clazz, Map<String, String[]> map, Set<String> names) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		// ʵ�����˶���
		Object obj = CreatenewInstance(clazz);

		Field[] fields = clazz.getDeclaredFields();
		// Method[] methods = clazz.getMethods();

		// �Գ־û��������Ϊ ��׼ ÿ�Ƚ�һ������ �ͱ���ѭ��һ�������������
		for (Field f : fields) {
			String name = f.getName();
			for (String param : names) {
				// ������� ��ͬ����
				if (StringUtilAndTools.ComparisonNameWithProperty(param, name)) {
					String paramvalue = map.get(param)[0];// �õ���������� ��ֵ
					obj = setPropertyValue(f, paramvalue, obj, clazz);
					// ��ʼ�����Ը�ֵ
					break;
				}
			}

		}
		return obj;
	}

	/**
	 * �ж����� ���� ���и�ֵ
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

		// �õ���ǰ��� �������� ����ֵ����
		Method method = clazz.getDeclaredMethod(StringUtilAndTools.CreateFieldToMethod(field.getName()),field.getType());
		// ���õ�ǰ�������� �Ѿ�ʵ�����Ķ��� ���� ����ֵ����
		method.invoke(obj, StringUtilAndTools.judgmentC(field.getType(), paramvalue));

		return obj;
	}
}
