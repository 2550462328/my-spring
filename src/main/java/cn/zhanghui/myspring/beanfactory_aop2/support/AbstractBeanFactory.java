package cn.zhanghui.myspring.beanfactory_aop2.support;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.config.AutowiredCapableBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.config.ConfigurableBeanFactory;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, AutowiredCapableBeanFactory{
	protected abstract Object createBean(BeanDefinition bd);
}

