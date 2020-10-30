package cn.zhanghui.myspring.beanfactory_aop2.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhanghui.myspring.beanfactory_aop2.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop2.exception.AopConfigException;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * jdk根据Advised生成动态代理
 * @ClassName: JdkAopProxyFactory.java
 * @Description: 该类的功能描述
 * @author: ZhangHui
 * @date: 2019年12月19日 上午9:17:05
 */
public class JdkAopProxyFactory implements AopProxyFactory, InvocationHandler {

	private Logger logger = LoggerFactory.getLogger(JdkAopProxyFactory.class);
	
	private final Advised advised;
	
	public JdkAopProxyFactory(Advised advised) throws AopConfigException {
		if(advised.getAdvices().size() == 0) {
			throw new AopConfigException("No advices specified");
		}
		this.advised = advised;
	}
	
	
	@Override
	public Object getProxy() {
		return getProxy(null);
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		if(classLoader == null) {
			classLoader = ClassUtils.getDefaultClassLoader();
		}
		Class<?>[] proxiedInterfaces =  advised.getProxiedInterfaces();
		
		return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object target = this.advised.getTargetObject();
		
		//核心代理类的拦截方法
		List<Advice> chain = this.advised.getAdvices(method);
		Object retVal;
		if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
			retVal = method.invoke(target, args);
		} else {
			List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<>();
			interceptors.addAll(chain);

			// 核心方法在这里
			retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();
		}
		return retVal;
	}
	
}
