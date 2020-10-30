package cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj;

import java.lang.reflect.Method;

import cn.zhanghui.myspring.beanfactory_aop2.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.Pointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.AspectInstanceFactory;

/**
 * @ClassName: AbstractAspectJAdvice.java
 * @Description：Advice接口的模板类
 * @author: ZhangHui
 * @date: 2019年12月13日 下午1:59:06
 */
public abstract class AbstractAspectJAdvice implements Advice {
	private Method adviceMethod;
	private AspectJExpressionPointcut pointcut;
	private AspectInstanceFactory adviceObjectFactory;
	
	public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
		super();
		this.adviceMethod = adviceMethod;
		this.pointcut = pointcut;
		this.adviceObjectFactory = adviceObjectFactory;
	}

	public void invokeAdviceMethod() throws Throwable {
		adviceMethod.invoke(adviceObjectFactory.getAspectBean());
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	public Method getAdviceMethod() {
		return this.adviceMethod;
	}
	
	public Object getAdviceInstance() {
		return adviceObjectFactory.getAspectInstance();
	}
}
