package cn.zhanghui.myspring.beanfactory_aop2.annotation;

import java.lang.reflect.Member;

import cn.zhanghui.myspring.beanfactory_aop2.config.AutowiredCapableBeanFactory;


public abstract class InjectionElement {
	
	protected Member member;
	protected AutowiredCapableBeanFactory factory;
	
	public InjectionElement(Member member, AutowiredCapableBeanFactory factory) {
		super();
		this.member = member;
		this.factory = factory;
	}

	public abstract void inject(Object target);
	
}
