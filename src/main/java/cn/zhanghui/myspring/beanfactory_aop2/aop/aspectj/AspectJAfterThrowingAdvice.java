package cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import cn.zhanghui.myspring.beanfactory_aop2.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.AspectInstanceFactory;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice{
	public AspectJAfterThrowingAdvice(Method adviceMethod,  AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
		super(adviceMethod, pointcut, adviceObjectFactory);
	}
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		} catch (Throwable e) {
			this.invokeAdviceMethod();
			throw e;
		}
	}
}
