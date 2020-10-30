package cn.zhanghui.myspring.beanfactory_aop.beans;

import java.lang.reflect.Method;

import cn.zhanghui.myspring.util.ClassUtils;
import cn.zhanghui.myspring.util.StringUtils;

public class BeanUtils {

	public static Method resolveSignature(String signature, Class<?> clazz) {
		int startParen = signature.indexOf('(');
		int endParen = signature.indexOf(')');
		if (startParen > -1 && endParen == -1) {
			throw new IllegalArgumentException("Invalid method signature '" + signature +
					"': expected closing ')' for args list");
		}
		else if (startParen == -1 && endParen > -1) {
			throw new IllegalArgumentException("Invalid method signature '" + signature +
					"': expected opening '(' for args list");
		}
		else if (startParen == -1) {
			return findMethodWithMinimalParameters(clazz, signature);
		}
		else {
			String methodName = signature.substring(0, startParen);
			String[] parameterTypeNames =
					StringUtils.commaDelimitedListToStringArray(signature.substring(startParen + 1, endParen));
			Class<?>[] parameterTypes = new Class<?>[parameterTypeNames.length];
			for (int i = 0; i < parameterTypeNames.length; i++) {
				String parameterTypeName = parameterTypeNames[i].trim();
				try {
					parameterTypes[i] = ClassUtils.forName(parameterTypeName, clazz.getClassLoader());
				}
				catch (Throwable ex) {
					throw new IllegalArgumentException("Invalid method signature: unable to resolve type [" +
							parameterTypeName + "] for argument " + i + ". Root cause: " + ex);
				}
			}
			return findMethod(clazz, methodName, parameterTypes);
		}
	}	
	
	public static Method findMethodWithMinimalParameters(Class<?> clazz, String methodName)
			throws IllegalArgumentException {	

		Method targetMethod = findMethodWithMinimalParameters(clazz.getMethods(), methodName);
		if (targetMethod == null) {
			targetMethod = findDeclaredMethodWithMinimalParameters(clazz, methodName);
		}
		return targetMethod;
	}
	
	public static Method findMethodWithMinimalParameters(Method[] methods, String methodName)
			throws IllegalArgumentException {

		Method targetMethod = null;
		int numMethodsFoundWithCurrentMinimumArgs = 0;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				int numParams = method.getParameterCount();
				if (targetMethod == null || numParams < targetMethod.getParameterCount()) {
					targetMethod = method;
					numMethodsFoundWithCurrentMinimumArgs = 1;
				}
				else if (!method.isBridge() && targetMethod.getParameterCount() == numParams) {
					if (targetMethod.isBridge()) {
						// Prefer regular method over bridge...
						targetMethod = method;
					}
					else {
						// Additional candidate with same length
						numMethodsFoundWithCurrentMinimumArgs++;
					}
				}
			}
		}
		if (numMethodsFoundWithCurrentMinimumArgs > 1) {
			throw new IllegalArgumentException("Cannot resolve method '" + methodName +
					"' to a unique method. Attempted to resolve to overloaded method with " +
					"the least number of parameters but there were " +
					numMethodsFoundWithCurrentMinimumArgs + " candidates.");
		}
		return targetMethod;
	}
	
	public static Method findDeclaredMethodWithMinimalParameters(Class<?> clazz, String methodName)
			throws IllegalArgumentException {

		Method targetMethod = findMethodWithMinimalParameters(clazz.getDeclaredMethods(), methodName);
		if (targetMethod == null && clazz.getSuperclass() != null) {
			targetMethod = findDeclaredMethodWithMinimalParameters(clazz.getSuperclass(), methodName);
		}
		return targetMethod;
	}
	
	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			return findDeclaredMethod(clazz, methodName, paramTypes);
		}
	}
	
	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			if (clazz.getSuperclass() != null) {
				return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
			}
			return null;
		}
	}
}
