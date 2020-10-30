package cn.zhanghui.myspring.beanfactory_aop2.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 
 * @ClassName: ReflectiveMethodInvocation.java
 * @Description: 实际调用Advice拦截器链的类
 * @author: ZhangHui
 * @date: 2019年12月19日 上午9:40:11
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
	
	protected final Object targetObject;
	protected final Method targetMethod;
	protected Object[] arguments;
	
	protected final List<MethodInterceptor>  interceptors;
	
	private int currentInterceptrorIndex = -1;
	
	public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] arguments,
			List<MethodInterceptor> interceptors) {
		super();
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.arguments = arguments;
		this.interceptors = interceptors;
	}

	@Override
	public Object[] getArguments() {
		return this.arguments;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return this.targetMethod;
	}

	@Override
	public Object getThis() {
		return this.targetObject;
	}

	@Override
	public Object proceed() throws Throwable {
		//所有拦截器已经完成
		if(this.currentInterceptrorIndex == this.interceptors.size() -1) {
			return invokeJointpoint();
		}
		
		this.currentInterceptrorIndex ++;
		
		MethodInterceptor interceptor = this.interceptors.get(currentInterceptrorIndex);
		
		//这里会有一个递归的调用，每个Advice都会回调proceed()方法
		return interceptor.invoke(this);
	}

	private Object invokeJointpoint() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.targetMethod.invoke(this.targetObject, this.arguments);
	}

	@Override
	public Method getMethod() {
		return this.targetMethod;
	}
	
}
