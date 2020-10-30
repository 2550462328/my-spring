package cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import cn.zhanghui.myspring.beanfactory_aop2.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.AspectInstanceFactory;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice{
	public AspectJBeforeAdvice(Method adviceMethod,  AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
		super(adviceMethod, pointcut, adviceObjectFactory);
	}
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		this.invokeAdviceMethod();
		Object o = mi.proceed();
		return o;
	}
}
