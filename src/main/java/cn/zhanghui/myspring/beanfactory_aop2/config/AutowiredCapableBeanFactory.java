package cn.zhanghui.myspring.beanfactory_aop2.config;


public interface AutowiredCapableBeanFactory{
	Object resolveDependency(DependencyDescriptor descriptor);
}
