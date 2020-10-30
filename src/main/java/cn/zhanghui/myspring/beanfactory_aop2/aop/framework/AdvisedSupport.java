package cn.zhanghui.myspring.beanfactory_aop2.aop.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.zhanghui.myspring.beanfactory_aop2.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.Pointcut;

public class AdvisedSupport implements Advised {
	private Object targetObject = null;
	
	private List<Advice> advices = new ArrayList<>();
	
	private List<Class> proxiedInterfaces = new ArrayList<>();
	
	public AdvisedSupport() {
		super();
	}

	@Override
	public Class<?> getTargetClass() {
		return this.targetObject.getClass();
	}
 
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}
	
	@Override
	public Object getTargetObject() {
		return this.targetObject;
	}

	@Override
	public List<Advice> getAdvices() {
		return this.advices;
	}

	@Override
	public void addAdvice(Advice advice) {
		this.advices.add(advice);
	}

	@Override
	public List<Advice> getAdvices(Method method) {
		List<Advice> result = new ArrayList<>();
		for(Advice  advice : this.advices) {
			Pointcut pc = advice.getPointcut();
			if(pc.getMethodMatcher().match(method)) {
				result.add(advice);
			}
		}
		return result;
	}

	@Override
	public Class[] getProxiedInterfaces() {
		return  proxiedInterfaces.toArray(new Class[0]);
	}

	@Override
	public void addInterface(Class<?> clazz) {
		this.proxiedInterfaces.add(clazz);
	}
}
