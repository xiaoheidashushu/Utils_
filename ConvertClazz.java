package org.fullmatel.Utils;

/**
 * 
 * ��ķ��� ����convertΪǰ׺ 
 * 
 * �̶���ʽ	convert + Integer����Ҫת�������ͣ�
 * 
 * */
public class ConvertClazz {

	public  Object convertInteger(String paramvalue){
		return (Object)Integer.parseInt(paramvalue);
	}
	public Object convertDouble(String paramvalue){
		return (Object)Double.parseDouble(paramvalue);
	}
	public Object convertShort(String paramvalue){
		return (Object)Short.parseShort(paramvalue);
	}
	public Object convertLong(String paramvalue){
		return (Object)Long.parseLong(paramvalue);
	}
	public Object convertString(String paramvalue){
		return paramvalue;
	}
	public Object convertbyte(String paramvalue){
		return (Object)Byte.parseByte(paramvalue);
	}
}
