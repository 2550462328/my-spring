package cn.zhanghui.myspring.beanfactory_aop.test.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_aop.aop.aspectj.AspectJAfterAdvice;
import cn.zhanghui.myspring.beanfactory_aop.aop.aspectj.AspectJAfterThrowingAdvice;
import cn.zhanghui.myspring.beanfactory_aop.aop.aspectj.AspectJBeforeAdvice;
import cn.zhanghui.myspring.beanfactory_aop.aop.framework.ReflectiveMethodInvocation;
import cn.zhanghui.myspring.beanfactory_aop.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_aop.test.tx.TransactionManager;
import cn.zhanghui.myspring.util.MessageTracker;

/**
 * 
 * @ClassName: ReflectiveMethodInvocationTest.java
 * @Description: 实现Interceptors的链式调用
 * @author: ZhangHui
 * @date: 2019年12月13日 下午2:13:16
 */
public class ReflectiveMethodInvocationTest {
	private AspectJBeforeAdvice beforeAdvice = null;
	private AspectJAfterAdvice afterAdvice = null;
	private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
	private PersonService personService=  null;
	private TransactionManager tx = null;
	
	@Before
	public void setup() throws NoSuchMethodException, SecurityException {
		personService = new PersonService();
		tx = new TransactionManager();
		MessageTracker.clearMessages();
		beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),null, tx);
		
		afterAdvice = new AspectJAfterAdvice(TransactionManager.class.getMethod("commit"),null, tx);
		
		afterThrowingAdvice = new AspectJAfterThrowingAdvice(TransactionManager.class.getMethod("rollback"),null, tx);
	}
	
	@Test
	public void testMethodInvocation() throws Throwable {
		Method targetMethod = PersonService.class.getMethod("placeOrder");
		List<MethodInterceptor> interceptors = new ArrayList<>();
		interceptors.add(afterAdvice);
		interceptors.add(beforeAdvice);
		
		ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(personService, targetMethod, new Object[0], interceptors);
		
		mi.proceed();
		
		List<String> msgs = MessageTracker.getMsgs();
		Assert.assertTrue(3 == msgs.size());
		Assert.assertEquals("start transaction", msgs.get(0));
		Assert.assertEquals("place order", msgs.get(1));
		Assert.assertEquals("start commit", msgs.get(2));
	}
	
	@Test
	public void testMethodInvocationWithException() throws Throwable {
		Method targetMethod = PersonService.class.getMethod("placeOrderWithException");
		List<MethodInterceptor> interceptors = new ArrayList<>();
		interceptors.add(afterAdvice);
		interceptors.add(beforeAdvice);
		interceptors.add(afterThrowingAdvice);
		
		ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(personService, targetMethod, new Object[0], interceptors);
		
		try {
			mi.proceed();
		} catch (Throwable e) {
			List<String> msgs = MessageTracker.getMsgs();
			Assert.assertTrue(2 == msgs.size());
			Assert.assertEquals("start transaction", msgs.get(0));
			Assert.assertEquals("start rollback", msgs.get(1));
			return;
		}
		
		Assert.fail("No Exception throwed!");
	}
}
