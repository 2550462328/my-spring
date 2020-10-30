package cn.zhanghui.myspring.beanfactory_aop2.core.annotation;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;

public class AnnotationAttribute extends LinkedHashMap<String, Object> {

	public AnnotationAttribute() {
	}

	public AnnotationAttribute(int initialCapacity) {
		super(initialCapacity);
	}

	public String getString(String attributeName) {
		return doGet(attributeName, String.class);
	}
	
	public Boolean getBoolean(String attributeName) {
		return doGet(attributeName, Boolean.class);
	}
	
	public String[] getStringArray(String attributeName) {
		return doGet(attributeName, String[].class);
	}

	public <N extends Number> N getNumber(String attributeName) {
		return (N) doGet(attributeName, Integer.class);
	}

	public <E extends Enum<?>> E getEnum(String attributeName) {
		return (E) doGet(attributeName, Enum.class);
	}

	public <T> Class<? extends T> getClass(String attributeName) {
		return doGet(attributeName, Class.class);
	}
	
	public Class<?>[] getClassArray(String attributeName) {
		return doGet(attributeName, Class[].class);
	}
	
	private <T> T doGet(String attribteName, Class<T> expectedType){
		Object value = get(attribteName);
		if (!expectedType.isInstance(value) && expectedType.isArray() &&
				expectedType.getComponentType().isInstance(value)) {
			Object array = Array.newInstance(expectedType.getComponentType(), 1);
			Array.set(array, 0, value);
			value = array;
		}
		return (T)value;
	}
}
