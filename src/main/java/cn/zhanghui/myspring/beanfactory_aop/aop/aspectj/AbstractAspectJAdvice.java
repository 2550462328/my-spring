package cn.zhanghui.myspring.beanfactory_aop.aop.aspectj;

import java.lang.reflect.Method;

import cn.zhanghui.myspring.beanfactory_aop.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop.aop.Pointcut;

/**
 * @ClassName: AbstractAspectJAdvice.java
 * @Description：Advice接口的模板类
 * @author: ZhangHui
 * @date: 2019年12月13日 下午1:59:06
 */
public abstract class AbstractAspectJAdvice implements Advice {
	private Method adviceMethod;
	private Pointcut pointcut;
	private Object adviceObject;

	public AbstractAspectJAdvice(Method adviceMethod, Pointcut pointcut, Object adviceObject) {
		super();
		this.adviceMethod = adviceMethod;
		this.pointcut = pointcut;
		this.adviceObject = adviceObject;
	}

	public void invokeAdviceMethod() throws Throwable {
		adviceMethod.invoke(adviceObject);
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	public Method getAdviceMethod() {
		return this.adviceMethod;
	}
}
