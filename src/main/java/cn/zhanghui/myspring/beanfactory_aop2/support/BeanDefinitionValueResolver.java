package cn.zhanghui.myspring.beanfactory_aop2.support;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.FactoryBean;
import cn.zhanghui.myspring.beanfactory_aop2.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_aop2.config.TypedStringValue;

/**
 * @ClassName: BeanDefinitionValueResolver.java
 * @Description: 将beanId解析成实例Bean
 * @author: ZhangHui
 * @date: 2019年10月28日 下午5:21:59
 */
public class BeanDefinitionValueResolver {
	private final AbstractBeanFactory defaultBeanFactory;

	public BeanDefinitionValueResolver(AbstractBeanFactory defaultBeanFactory) {
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
		} else if (value instanceof BeanDefinition) {
			BeanDefinition bd = (BeanDefinition) value;
			String innerBeanName = "(inner bean)" + bd.getClassName() + "#"
					+ Integer.toHexString(System.identityHashCode(bd));
			return resolveInnerBean(innerBeanName, bd);
		} else if (value instanceof String) {
			return value;
		} else {
			throw new RuntimeException("the value " + value + " has not implemented");
		}
	}

	/**
	 * 解析构造函数的参数是Beandefinition的情况
	 * 
	 * @param innerBeanName
	 * @param bd
	 * @return
	 */
	private Object resolveInnerBean(String innerBeanName, BeanDefinition bd) {
		Object innerBean = this.defaultBeanFactory.createBean(bd);

		if (innerBean instanceof FactoryBean) {
			try {
				return ((FactoryBean) innerBean).getObject();
			} catch (Exception e) {
				return null;
			}
		} else {
			return innerBean;
		}
	}

}
