package cn.zhanghui.myspring.beanfactory_aop.aop.aspectj;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import cn.zhanghui.myspring.beanfactory_aop.aop.Pointcut;

public class AspectJAfterAdvice extends AbstractAspectJAdvice{
	public AspectJAfterAdvice(Method adviceMethod, Pointcut pointcut, Object adviceObject) {
		super(adviceMethod, pointcut, adviceObject);
	}
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object o = mi.proceed();
		this.invokeAdviceMethod();
		return o;
	}
}
