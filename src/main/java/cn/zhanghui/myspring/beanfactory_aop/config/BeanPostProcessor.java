package cn.zhanghui.myspring.beanfactory_aop.config;

import cn.zhanghui.myspring.beanfactory.bean.BeanExceptionn;

public interface BeanPostProcessor {
	//初始化前
	Object beforeInitialization(Object bean, String beanName) throws BeanExceptionn;
	
	//初始化后
	Object afterInitialization(Object bean, String beanName) throws BeanExceptionn;
}
