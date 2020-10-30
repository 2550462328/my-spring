package cn.zhanghui.myspring.beanfactory_annotation2.config;

import cn.zhanghui.myspring.beanfactory.bean.BeanExceptionn;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
	
	//实例化前
	Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanExceptionn;
	
	//实例化后
	boolean afterInstantiation(Object bean, String beanName);
	
	//autowired这个bean
	void postProcessorPropertyValues(Object  bean, String beanName);
}
