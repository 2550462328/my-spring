package cn.zhanghui.myspring.beanfactory_set.support;

import cn.zhanghui.myspring.beanfactory_set.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_set.config.TypedStringValue;

/**
 * @ClassName: BeanDefinitionValueResolver.java
 * @Description: 将beanId解析成实例Bean
 * @author: ZhangHui
 * @date: 2019年10月28日 下午5:21:59
 */
public class BeanDefinitionValueResolver {
	private final DefaultBeanFactory defaultBeanFactory;

	public BeanDefinitionValueResolver(DefaultBeanFactory defaultBeanFactory) {
		super();
		this.defaultBeanFactory = defaultBeanFactory;
	}

	public Object resolveValueIfNecessary(Object value) {
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;
			String refName = ref.getName();
			Object bean = this.defaultBeanFactory.getBean(refName);
			return bean;
		} else if (value instanceof TypedStringValue) {
			TypedStringValue stringValue = (TypedStringValue) value;
			return stringValue.getValue();
		} else {
			throw new RuntimeException("the value " + value + " has not implemented");
		}
	}

}
