package cn.zhanghui.myspring.beanfactory_aop2.aop;
import org.aopalliance.intercept.MethodInterceptor;

public interface Advice extends MethodInterceptor {
	public Pointcut getPointcut();
}	
