package cn.zhanghui.myspring.beanfactory_aop2.test.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj.AspectJBeforeAdvice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.AspectInstanceFactory;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.MethodLocatingFactory;
import cn.zhanghui.myspring.beanfactory_aop2.beans.ConstructorArgument.ValueHolder;
import cn.zhanghui.myspring.beanfactory_aop2.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_aop2.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_aop2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.test.tx.TransactionManager;

public class BeanDefinitionTest extends CommonTest {
	
	@Test
	public void testAopBeanDefinition() {
		DefaultBeanFactory factory = (DefaultBeanFactory)this.getBeanFactory("applicationContext5.xml");
		
		{
			BeanDefinition bd = factory.getBeanDefinition("tx");
			Assert.assertTrue(bd.getClassName().equals(TransactionManager.class.getName()));
		}
		
		{
			BeanDefinition bd = factory.getBeanDefinition("placeOrder");
			
			Assert.assertTrue(bd.isSynthetic());
			Assert.assertTrue(bd.getBeanClass().equals(AspectJExpressionPointcut.class));
			
			PropertyValue pv = bd.getPropertyValues().get(0);
			Assert.assertEquals("expression", pv.getName());
			Assert.assertEquals("execution(* cn.zhanghui.myspring.beanfactory_aop2.test.service.*.placeOrder(..))", pv.getValue());
		}
		
		//检查AspectJBeforeAdvice
		{
			String name = AspectJBeforeAdvice.class.getName() + "#0";
			BeanDefinition bd = factory.getBeanDefinition(name);
			Assert.assertTrue(bd.getBeanClass().equals(AspectJBeforeAdvice.class));
			
			Assert.assertTrue(bd.isSynthetic());
			
			List<ValueHolder> args = bd.getConstructorArgument().getArgumentValues();
			Assert.assertEquals(3, args.size());
			
			//第一个参数 MethodLocatingFactory methodFactory
			{
				BeanDefinition innerBeanDef = (BeanDefinition) args.get(0).getValue();
				Assert.assertTrue(innerBeanDef.isSynthetic());
				Assert.assertTrue(innerBeanDef.getBeanClass().equals(MethodLocatingFactory.class));
				
				List<PropertyValue> pvs = innerBeanDef.getPropertyValues();
				Assert.assertEquals("targetBeanName", pvs.get(0).getName());
				Assert.assertEquals("tx",pvs.get(0).getValue());
				Assert.assertEquals("methodName", pvs.get(1).getName());
				Assert.assertEquals("start",pvs.get(1).getValue());
			}
			
			//第二个参数 AspectJExpressionPointcut pointcut
			{
				RuntimeBeanReference ref = (RuntimeBeanReference) args.get(1).getValue();
				Assert.assertEquals("placeOrder", ref.getName());
			}
			
			//第三个参数 AspectInstanceFactory adviceObjectFactory
			{
				BeanDefinition innerBeanDef = (BeanDefinition) args.get(2).getValue();
				Assert.assertTrue(innerBeanDef.isSynthetic());
				Assert.assertTrue(innerBeanDef.getBeanClass().equals(AspectInstanceFactory.class));
				
				List<PropertyValue> pvs = innerBeanDef.getPropertyValues();
				Assert.assertEquals("aspectBeanName", pvs.get(0).getName());
				Assert.assertEquals("tx", pvs.get(0).getValue());
			}
		}
	}
	
}
