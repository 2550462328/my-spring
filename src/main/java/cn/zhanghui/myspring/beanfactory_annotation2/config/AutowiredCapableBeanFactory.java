package cn.zhanghui.myspring.beanfactory_annotation2.config;


public interface AutowiredCapableBeanFactory{
	Object resolveDependency(DependencyDescriptor descriptor);
}
