package cn.zhanghui.myspring.beanfactory_aop2.test.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_aop2.aop.Advice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj.AspectJBeforeAdvice;
import cn.zhanghui.myspring.beanfactory_aop2.exception.NoSuchBeanDefinitionException;
import cn.zhanghui.myspring.beanfactory_aop2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.test.tx.TransactionManager;

public class BeanFactoryTest extends CommonTest {
	static String expectedExpression = "execution(* cn.zhanghui.myspring.beanfactory_aop2.test.service.*.placeOrder(..))";
	
	@Test
	public void testGetBeanByType() throws NoSuchMethodException, SecurityException, NoSuchBeanDefinitionException {
		DefaultBeanFactory beanFactory = (DefaultBeanFactory) this.getBeanFactory("applicationContext5.xml");
		
		List<Object> advices = beanFactory.getBeansByType(Advice.class);
		
		Assert.assertEquals(3, advices.size());
		
		{
			AspectJBeforeAdvice advice = null;
			for(Object advice_o : advices) {
				if(advice_o.getClass().isAssignableFrom(AspectJBeforeAdvice.class)) {
					advice = (AspectJBeforeAdvice) advice_o;
				}
			}
			Assert.assertNotNull(advice);
			Assert.assertEquals(TransactionManager.class.getMethod("start"), advice.getAdviceMethod());
			Assert.assertEquals(expectedExpression, advice.getPointcut().getExpression());
			Assert.assertEquals(TransactionManager.class, advice.getAdviceInstance().getClass());
		}
		
	}
}
