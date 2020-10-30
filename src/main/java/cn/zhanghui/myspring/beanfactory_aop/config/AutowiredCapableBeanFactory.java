package cn.zhanghui.myspring.beanfactory_aop.config;


public interface AutowiredCapableBeanFactory{
	Object resolveDependency(DependencyDescriptor descriptor);
}
