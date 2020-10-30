package cn.zhanghui.myspring.beanfactory_aop.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import cn.zhanghui.myspring.beanfactory_aop.aop.Pointcut;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice{
	public AspectJAfterThrowingAdvice(Method adviceMethod, Pointcut pointcut, Object adviceObject) {
		super(adviceMethod, pointcut, adviceObject);
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
