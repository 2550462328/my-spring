package cn.zhanghui.myspring.beanfactory_aop2.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

import cn.zhanghui.myspring.beanfactory_aop2.aop.Advice;

public interface Advised {
	Class<?> getTargetClass();
	Object getTargetObject();
	
	List<Advice> getAdvices();
	
	void addAdvice(Advice advice);
	
	List<Advice> getAdvices(Method method);
	Class<?>[] getProxiedInterfaces();
	
	void addInterface(Class<?> clazz);
}
