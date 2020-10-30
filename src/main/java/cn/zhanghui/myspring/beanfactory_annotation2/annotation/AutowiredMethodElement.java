package cn.zhanghui.myspring.beanfactory_annotation2.annotation;

import java.lang.reflect.Method;

import cn.zhanghui.myspring.beanfactory.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory_annotation2.config.AutowiredCapableBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation2.config.DependencyDescriptor;
import cn.zhanghui.myspring.beanfactory_annotation2.exception.BeanAutowiredException;
import cn.zhanghui.myspring.util.ReflectionUtils;

/**
 * 
 * @ClassName: AutowiredMethodElement.java
 * @Description: 获取被注入的方法得到的对象
 * @author: ZhangHui
 * @date: 2019年12月6日 上午11:33:01
 */
public class AutowiredMethodElement extends InjectionElement {

	private final boolean required;

	public AutowiredMethodElement(Method method, boolean required, AutowiredCapableBeanFactory factory) {
		super(method, factory);
		this.required = required;
	}

	public Method getMethod() {
		return (Method) this.member;
	}

	@Override
	public void inject(Object target) {
		Method method = getMethod();

		try {
			DependencyDescriptor descriptor = new DependencyDescriptor(method, required);
			Object methodReturnObj = factory.resolveDependency(descriptor);

			if (methodReturnObj != null) {
				// 使field可用，在field为final、private修饰的情况下，accessible为false的
				ReflectionUtils.makeAccessible(method);
				method.invoke(target, methodReturnObj);
			} else if (required) {
				throw new BeanAutowiredException("could not find a bean named["
						+ descriptor.getDependencyType().getName() + "] used to autowired for " + target);
			}

		} catch (Throwable e) {
			throw new BeanCreateException("could not autowird  method:" + method.getName(), e);
		}
	}
}
