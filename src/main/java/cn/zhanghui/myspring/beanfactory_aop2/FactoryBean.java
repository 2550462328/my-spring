package cn.zhanghui.myspring.beanfactory_aop2;

public interface FactoryBean<T> {
	T getObject() throws Exception;
	
	Class<?> getObjectType();
}
