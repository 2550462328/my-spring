package cn.zhanghui.myspring.beanfactory_aop.aop;
import org.aopalliance.intercept.MethodInterceptor;

public interface Advice extends MethodInterceptor {
	public Pointcut getPointcut();
}	
