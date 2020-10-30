package cn.zhanghui.myspring.beanfactory_aop.test.junit;


import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_aop.aop.config.MethodLocatingFactory;
import cn.zhanghui.myspring.beanfactory_aop.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_aop.exception.NoSuchBeanDefinitionException;
import cn.zhanghui.myspring.beanfactory_aop.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop.test.tx.TransactionManager;
import cn.zhanghui.myspring.beanfactory_aop.xml.XmlBeanDefinitionReader;

public class MethodLocatingFactoryTest {
	
	@Test
	public void testGetMethod() throws NoSuchMethodException, SecurityException, NoSuchBeanDefinitionException {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext4.xml"));
		
		MethodLocatingFactory methodLocationgFactory = new MethodLocatingFactory();
		methodLocationgFactory.setTargetBeanName("tx");
		methodLocationgFactory.setMethodName("start");
		methodLocationgFactory.setBeanFactory(beanFactory);
		
		Method m = methodLocationgFactory.getObject();
		
		Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
		Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));
		
		
	}
}
