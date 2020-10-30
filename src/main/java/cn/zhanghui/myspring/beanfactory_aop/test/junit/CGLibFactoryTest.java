package cn.zhanghui.myspring.beanfactory_aop.test.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_aop.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop.aop.aspectj.AspectJAfterAdvice;
import cn.zhanghui.myspring.beanfactory_aop.aop.aspectj.AspectJBeforeAdvice;
import cn.zhanghui.myspring.beanfactory_aop.aop.framework.AdvisedSupport;
import cn.zhanghui.myspring.beanfactory_aop.aop.framework.CglibProxyFactory;
import cn.zhanghui.myspring.beanfactory_aop.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_aop.test.tx.TransactionManager;
import cn.zhanghui.myspring.util.MessageTracker;

public class CGLibFactoryTest {
	private AspectJBeforeAdvice beforeAdvice = null;
	private AspectJAfterAdvice afterAdvice = null;
	private AspectJExpressionPointcut pc = null;
	private PersonService personService = null;
	private TransactionManager tx = null;

	@Before
	public void setup() throws NoSuchMethodException, SecurityException {
		String expression = "execution(* cn.zhanghui.myspring.beanfactory_aop.test.service.*.placeOrder(..))";
		personService = new PersonService();
		tx = new TransactionManager();
		pc = new AspectJExpressionPointcut();
		pc.setExpression(expression);
		MessageTracker.clearMessages();
		beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"), pc, tx);

		afterAdvice = new AspectJAfterAdvice(TransactionManager.class.getMethod("commit"), pc, tx);
	}

	@Test
	public void testGetProxyClass() throws Throwable {
		AdvisedSupport advised = new AdvisedSupport();

		advised.addAdvice(beforeAdvice);
		advised.addAdvice(afterAdvice);
		advised.setTargetObject(personService);	

		CglibProxyFactory proxyFacotry = new CglibProxyFactory(advised);

		PersonService personServiceProxy = (PersonService) proxyFacotry.getProxy();

		personServiceProxy.placeOrder();
		
		List<String> msgs = MessageTracker.getMsgs();
		Assert.assertTrue(3 == msgs.size());
		Assert.assertEquals("start transaction", msgs.get(0));
		Assert.assertEquals("place order", msgs.get(1));
		Assert.assertEquals("start commit", msgs.get(2));
		
		personServiceProxy.toString();
	}
}
