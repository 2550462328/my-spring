package cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import cn.zhanghui.myspring.beanfactory_aop2.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.AspectInstanceFactory;

public class AspectJAfterAdvice extends AbstractAspectJAdvice{
	public AspectJAfterAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
		super(adviceMethod, pointcut, adviceObjectFactory);
	}
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object o = mi.proceed();
		this.invokeAdviceMethod();
		return o;
	}
}
