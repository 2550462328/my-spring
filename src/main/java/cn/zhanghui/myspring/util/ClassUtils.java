package cn.zhanghui.myspring.util;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: ClassUtils.java
 * @Description: 获取类加载环境
 * @author: ZhangHui
 * @date: 2019年10月17日 上午9:15:37
 */
public class ClassUtils {

	private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap(8);
	private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap(8);

	private static final Map<String, Class<?>> commonClassCache = new HashMap<>(64);

	private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(32);

	private static final char PACKAGE_SEPARATOR = '.';
	private static final char PATH_SEPERATOR = '/';

	private static final char INNER_CLASS_SEPARATOR = '$';

	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	public static final String ARRAY_SUFFIX = "[]";

	private static final String INTERNAL_ARRAY_PREFIX = "[";

	private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

	static {
		primitiveWrapperTypeMap.put(Boolean.class, Boolean.TYPE);
		primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
		primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
		primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
		primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
		primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
		primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
		primitiveWrapperTypeMap.put(Short.class, Short.TYPE);
		Map.Entry<Class<?>, Class<?>> entry;
		for (Iterator localIterator = primitiveWrapperTypeMap.entrySet().iterator(); localIterator.hasNext();) {
			entry = (Map.Entry) localIterator.next();
			primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
		}
	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (cl == null) {
			try {
				cl = ClassLoader.getSystemClassLoader();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return cl;
	}

	public static boolean isAssignableValue(Class<?> type, Object value) {
		Assert.notNull(type, "Type must not be null");
		return value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive();
	}

	public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
		Assert.notNull(lhsType, "Left-hand side type must not be null");
		Assert.notNull(rhsType, "Right-hand side type must not be null");
		if (lhsType.isAssignableFrom(rhsType)) {
			return true;
		}
		if (lhsType.isPrimitive()) {
			Class<?> resolvedPrimitive = (Class) primitiveWrapperTypeMap.get(rhsType);
			if (lhsType == resolvedPrimitive) {
				return true;
			}
		} else {
			Class<?> resolvedWrapper = (Class) primitiveTypeToWrapperMap.get(rhsType);
			if ((resolvedWrapper != null) && (lhsType.isAssignableFrom(resolvedWrapper))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 转换类路径到resource路径
	 * 
	 * @param basepackage
	 * @return
	 */
	public static String convertClassNameToResourcePath(String resourcePath) {
		return resourcePath.replace(PACKAGE_SEPARATOR, PATH_SEPERATOR);
	}

	/**
	 * 转换resource路径到类路径
	 * 
	 * @param classPath
	 * @return
	 */
	public static String convertResourcePathToClassName(String classPath) {
		return classPath.replace(PATH_SEPERATOR, PACKAGE_SEPARATOR);
	}

	public static String getShorName(String className) {
		int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
		int nameEndIndex = className.indexOf(CGLIB_CLASS_SEPARATOR);
		if (nameEndIndex == -1) {
			nameEndIndex = className.length();
		}
		String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
		shortName = shortName.replace(INNER_CLASS_SEPARATOR, PACKAGE_SEPARATOR);
		return shortName;
	}

	public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException {
		Class<?> clazz = resolvePrimitiveClassName(name);
		if (clazz == null) {
			clazz = commonClassCache.get(name);
		}
		if (clazz != null) {
			return clazz;
		}

		// "java.lang.String[]" style arrays
		if (name.endsWith(ARRAY_SUFFIX)) {
			String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
			Class<?> elementClass = forName(elementClassName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}

		// "[Ljava.lang.String;" style arrays
		if (name.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && name.endsWith(";")) {
			String elementName = name.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), name.length() - 1);
			Class<?> elementClass = forName(elementName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}

		// "[[I" or "[[Ljava.lang.String;" style arrays
		if (name.startsWith(INTERNAL_ARRAY_PREFIX)) {
			String elementName = name.substring(INTERNAL_ARRAY_PREFIX.length());
			Class<?> elementClass = forName(elementName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}

		ClassLoader clToUse = classLoader;
		if (clToUse == null) {
			clToUse = getDefaultClassLoader();
		}
		try {
			return Class.forName(name, false, clToUse);
		} catch (ClassNotFoundException ex) {
			int lastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
			if (lastDotIndex != -1) {
				String innerClassName = name.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR
						+ name.substring(lastDotIndex + 1);
				try {
					return Class.forName(innerClassName, false, clToUse);
				} catch (ClassNotFoundException ex2) {
				}
			}
			throw ex;
		}
	}

	private static Class<?> resolvePrimitiveClassName(String name) {
		Class<?> result = null;
		if (name != null && name.length() <= 8) {
			// Could be a primitive - likely.
			result = primitiveTypeNameMap.get(name);
		}
		return result;
	}

	public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz) {
		return getAllInterfacesForClassAsSet(clazz, null);
	}

	public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clazz, ClassLoader classLoader) {
		Assert.notNull(clazz, "Class must not be null");
		if (clazz.isInterface() && isVisible(clazz, classLoader)) {
			return Collections.singleton(clazz);
		}
		Set<Class<?>> interfaces = new LinkedHashSet<>();
		Class<?> current = clazz;
		while (current != null) {
			Class<?>[] ifcs = current.getInterfaces();
			for (Class<?> ifc : ifcs) {
				if (isVisible(ifc, classLoader)) {
					interfaces.add(ifc);
				}
			}
			current = current.getSuperclass();
		}
		return interfaces;
	}

	public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
		if (classLoader == null) {
			return true;
		}
		try {
			if (clazz.getClassLoader() == classLoader) {
				return true;
			}
		} catch (SecurityException ex) {
		}
		return isLoadable(clazz, classLoader);
	}

	private static boolean isLoadable(Class<?> clazz, ClassLoader classLoader) {
		try {
			return (clazz == classLoader.loadClass(clazz.getName()));
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}
}
