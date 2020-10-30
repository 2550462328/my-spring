package cn.zhanghui.myspring.beanfactory_aop.aop.framework;

public interface AopProxyFactory {
	Object getProxy();
	
	Object getProxy(ClassLoader classLoader);
}
