package cn.zhanghui.myspring.beanfactory_aop2.aop.config;

import cn.zhanghui.myspring.beanfactory_aop2.BeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.BeanFactoryAware;
import cn.zhanghui.myspring.beanfactory_aop2.support.AbstractBeanFactory;

public class AspectInstanceFactory implements BeanFactoryAware {

	private String aspectBeanName;

	private AbstractBeanFactory beanFactory;

	public void setAspectBeanName(String aspectBeanName) {
		this.aspectBeanName = aspectBeanName;
	}

	public Object getAspectBean() {
		return beanFactory.getBean(aspectBeanName);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (AbstractBeanFactory) beanFactory;
	}

	public Object getAspectInstance() {
		return this.beanFactory.getBean(this.aspectBeanName);
	}
}
