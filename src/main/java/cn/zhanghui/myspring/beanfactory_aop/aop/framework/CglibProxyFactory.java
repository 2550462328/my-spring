package cn.zhanghui.myspring.beanfactory_aop.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import cn.zhanghui.myspring.beanfactory_aop.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop.exception.AopConfigException;

public class CglibProxyFactory implements AopProxyFactory {
	// 这些常量相当于是CallbackFilter中根据method返回的Interceptor的下标
	private static final int AOP_PROXY = 0;

	protected Logger log = LoggerFactory.getLogger(CglibProxyFactory.class);

	protected Advised advised;

	private Object[] constructorArgs;

	private Class<?>[] constructorArgsTypes;

	public CglibProxyFactory(Advised advised) throws AopConfigException {
		if (advised.getAdvices().size() == 0) {
			throw new AopConfigException("No advisors and no TargetSource specified");
		}
		this.advised = advised;
	}

	public void setConstructorArguments(Object[] constructorArgs, Class<?>[] constructorArgsTypes) {
		if (constructorArgs == null || constructorArgsTypes == null) {
			throw new IllegalArgumentException(
					"Both 'constructArgs' and 'constructorArgsTypes' can`t be null together");
		}
		if (this.constructorArgs.length != constructorArgsTypes.length) {
			throw new IllegalArgumentException("Number of 'constructArgs' (" + constructorArgs.length
					+ ") must match number of 'constructorArgsTypes' (" + constructorArgsTypes.length + ")");
		}
		this.constructorArgs = constructorArgs;
		this.constructorArgsTypes = constructorArgsTypes;
	}

	@Override
	public Object getProxy() {
		return getProxy(null);
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		if (log.isDebugEnabled()) {
			log.debug("Creating CGLIB proxy: target source is " + this.advised.getTargetClass());
		}

		Class<?> rootClass = this.advised.getTargetClass();

		Enhancer enhancer = new Enhancer();
		if (classLoader != null) {
			enhancer.setClassLoader(classLoader);
		}
		enhancer.setSuperclass(rootClass);

		// 创建的代理类命名规则，默认后缀加上"BySpringCGLIB"
		enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
		enhancer.setInterceptDuringConstruction(false);

		Callback[] callbacks = getCallbacks(rootClass);

		Class<?>[] types = new Class<?>[callbacks.length];
		for (int x = 0; x < callbacks.length; x++) {
			types[x] = callbacks[x].getClass();
		}

		enhancer.setCallbackFilter(new ProxyCallbackFilter(this.advised));
		enhancer.setCallbacks(callbacks);
		enhancer.setCallbackTypes(types);

		Object proxy;
		if (this.constructorArgs != null) {
			proxy = enhancer.create(constructorArgsTypes, constructorArgs);
		} else {
			proxy = enhancer.create();
		}
		return proxy;
	}

	private Callback[] getCallbacks(Class<?> rootClass) {
		Callback aopInterceptor = new DynamicAdvisedInterceptor(this.advised);
		Callback[] callbacks = new Callback[] { aopInterceptor };
		return callbacks;
	}

	private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {
		private final Advised advised;

		public DynamicAdvisedInterceptor(Advised advised) {
			super();
			this.advised = advised;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Class<?> targetClass = null;
			Object target = this.advised.getTargetObject();

			if (target != null) {
				targetClass = target.getClass();
			}

			List<Advice> chain = this.advised.getAdvices(method);
			Object retVal;
			if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
				retVal = methodProxy.invoke(target, args);
			} else {
				List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<>();
				interceptors.addAll(chain);

				// 核心方法在这里
				retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();
			}

			return retVal;
		}

	}

	private static class ProxyCallbackFilter implements CallbackFilter {

		private final Advised advised;

		public ProxyCallbackFilter(Advised advised) {
			this.advised = advised;
		}

		@Override
		public int accept(Method method) {
			return AOP_PROXY;
		}
	}
}
