package cn.zhanghui.myspring.beanfactory_aop.annotation;

import java.lang.reflect.Field;

import cn.zhanghui.myspring.beanfactory_aop.config.AutowiredCapableBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop.config.DependencyDescriptor;
import cn.zhanghui.myspring.beanfactory_aop.exception.BeanAutowiredException;
import cn.zhanghui.myspring.beanfactory_aop.exception.BeanCreateException;
import cn.zhanghui.myspring.util.ReflectionUtils;

public class AutowiredFieldElement extends InjectionElement {

	private final boolean required;

	public AutowiredFieldElement(Field f, boolean required, AutowiredCapableBeanFactory factory) {
		super(f, factory);
		this.required = required;
	}

	public Field getField() {
		return (Field) this.member;
	}

	@Override
	public void inject(Object target) {	
		Field field = getField();

		try {
			DependencyDescriptor descriptor = new DependencyDescriptor(field, required);
			Object fieldObj = factory.resolveDependency(descriptor);

			if (fieldObj != null) {
				// 使field可用，在field为final、private修饰的情况下，accessible为false的
				ReflectionUtils.makeAccessible(field);
				field.set(target, fieldObj);
			}else if (required) {
				throw new BeanAutowiredException("could not find a bean named["
						+ descriptor.getDependencyType().getName() + "] used to autowired for" + target);
			}
		} catch (Throwable e) {
			throw new BeanCreateException("could not autowird field :" + field, e);
		}
	}

}
