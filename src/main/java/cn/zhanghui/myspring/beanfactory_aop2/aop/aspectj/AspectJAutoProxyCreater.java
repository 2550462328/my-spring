package cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.zhanghui.myspring.beanfactory_aop2.aop.framework.AdvisedSupport;
import cn.zhanghui.myspring.beanfactory_aop2.aop.framework.AopProxyFactory;
import cn.zhanghui.myspring.beanfactory_aop2.aop.framework.CglibProxyFactory;
import cn.zhanghui.myspring.beanfactory_aop2.aop.framework.JdkAopProxyFactory;
import cn.zhanghui.myspring.beanfactory_aop2.beans.BeanExceptionn;
import cn.zhanghui.myspring.beanfactory_aop2.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.MethodMatcher;
import cn.zhanghui.myspring.beanfactory_aop2.aop.Pointcut;
import cn.zhanghui.myspring.beanfactory_aop2.config.BeanPostProcessor;
import cn.zhanghui.myspring.beanfactory_aop2.exception.AopConfigException;
import cn.zhanghui.myspring.beanfactory_aop2.exception.NoSuchBeanDefinitionException;
import cn.zhanghui.myspring.beanfactory_aop2.support.AbstractBeanFactory;
import cn.zhanghui.myspring.util.ClassUtils;

public class AspectJAutoProxyCreater implements BeanPostProcessor {

	AbstractBeanFactory beanFactory;

	public void setBeanFactory(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object beforeInitialization(Object bean, String beanName) throws BeanExceptionn {
		return bean;
	}

	@Override
	public Object afterInitialization(Object bean, String beanName) throws BeanExceptionn {
		// 如果这个类本身就是Advice及其子类，那就不要生成动态代理了

		if (isInfrastrureClass(bean.getClass())) {
			return bean;
		}

		List<Advice> advices;
		try {
			advices = getCadidateAdvices(bean);
			if (advices.isEmpty()) {
				return bean;
			}
			return createProxy(advices, bean);
		} catch (NoSuchBeanDefinitionException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据advices拦截器链创建bean的代理类
	 * 
	 * @param advices
	 * @param bean
	 * @return
	 */
	private Object createProxy(List<Advice> advices, Object bean) {
		AdvisedSupport advised = new AdvisedSupport();

		for (Advice advice : advices) {
			advised.addAdvice(advice);
		}

		Set<Class<?>> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
		for (Class<?> clazz : targetInterfaces) {
			advised.addInterface(clazz);
		}

		advised.setTargetObject(bean);

		AopProxyFactory proxyFactory = null;
		try {
			// 如果targetBean没有接口的话就使用CGLIB生成代理
			if (advised.getProxiedInterfaces().length == 0) {
				proxyFactory = new CglibProxyFactory(advised);
				// 有接口就使用jdk动态代理
			} else {
				proxyFactory = new JdkAopProxyFactory(advised);
			}
			return proxyFactory.getProxy();
		} catch (AopConfigException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Advice> getCadidateAdvices(Object bean) throws NoSuchBeanDefinitionException {
		List<Object> advices = this.beanFactory.getBeansByType(Advice.class);

		List<Advice> result = new ArrayList<>();
		for (Object advice : advices) {
			Pointcut pc = ((Advice) advice).getPointcut();
			if (canApply(pc, bean.getClass())) {
				result.add((Advice) advice);
			}
		}
		return result;
	}

	/**
	 * 判断targetClass有没有方法满足pc表达式
	 * 
	 * @param pc
	 * @param targetClass
	 * @return
	 */
	private boolean canApply(Pointcut pc, Class<? extends Object> targetClass) {
		MethodMatcher methodMatcher = pc.getMethodMatcher();

		Set<Class> classes = new LinkedHashSet<>(ClassUtils.getAllInterfacesForClassAsSet(targetClass));
		classes.add(targetClass);

		for (Class<?> clazz : classes) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (methodMatcher.match(method)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断beanClass是不是Advice的子类
	 * 
	 * @param beanClass
	 * @return
	 */
	private boolean isInfrastrureClass(Class<? extends Object> beanClass) {
		boolean retVal = Advice.class.isAssignableFrom(beanClass);
		return retVal;
	}
}
