package cn.zhanghui.myspring.beanfactory_aop.config;

import cn.zhanghui.myspring.beanfactory_aop.beans.BeanExceptionn;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
	
	//实例化前
	Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanExceptionn;
	
	//实例化后
	boolean afterInstantiation(Object bean, String beanName);
	
	//autowired这个bean
	void postProcessorPropertyValues(Object  bean, String beanName);
}
