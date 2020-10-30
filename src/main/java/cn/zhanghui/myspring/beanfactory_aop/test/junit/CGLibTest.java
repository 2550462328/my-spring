package cn.zhanghui.myspring.beanfactory_aop.test.junit;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

import cn.zhanghui.myspring.beanfactory_aop.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_aop.test.tx.TransactionManager;

public class CGLibTest implements MethodInterceptor {

	@Test
	public void testCGLib() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(PersonService.class);
		//设置拦截器
		enhancer.setCallback(this);
		PersonService personService = (PersonService) enhancer.create();
		personService.placeOrder();
	}

	@Test
	public void testFilter() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(PersonService.class);
		enhancer.setInterceptDuringConstruction(false);
		
		Callback[] callbacks = new Callback[] {this, NoOp.INSTANCE};
		Class<?>[] types = new Class<?>[callbacks.length];
		for(int i = 0; i < callbacks.length; i++) {
			types[i] = callbacks[i].getClass();
		}
		
		//设置过滤器
		enhancer.setCallbackFilter(new CGLibTest().new ProxyCallbackFilter());
		enhancer.setCallbacks(callbacks);
		enhancer.setCallbackTypes(types);
		
		PersonService personService = (PersonService) enhancer.create();
		personService.placeOrder();
		personService.toString();
	}
	
	@Override
	public Object intercept(Object obj, Method targetMethod, Object[] args, MethodProxy proxy) throws Throwable {
		TransactionManager tx = new TransactionManager();
		tx.start();
		Object result = proxy.invokeSuper(obj, args);
		tx.commit();
		return result;
	}
	
	class ProxyCallbackFilter implements CallbackFilter{
		@Override
		public int accept(Method method) {
			// 如果拦截方法名以place开头，使用第一个拦截器
			if(method.getName().startsWith("place")) {
				return 0;
			//否则使用第二个拦截器	
			}else {
				return 1;
			}
		}
	}
}

