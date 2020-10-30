package cn.zhanghui.myspring.beanfactory_aop.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import cn.zhanghui.myspring.beanfactory_aop.aop.Pointcut;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice{
	public AspectJBeforeAdvice(Method adviceMethod, Pointcut pointcut, Object adviceObject) {
		super(adviceMethod, pointcut, adviceObject);
	}
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		this.invokeAdviceMethod();
		Object o = mi.proceed();
		return o;
	}
}
