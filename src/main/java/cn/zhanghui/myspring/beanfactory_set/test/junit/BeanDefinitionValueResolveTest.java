package cn.zhanghui.myspring.beanfactory_set.test.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_set.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_set.config.TypedStringValue;
import cn.zhanghui.myspring.beanfactory_set.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_set.support.BeanDefinitionValueResolver;
import cn.zhanghui.myspring.beanfactory_set.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_set.test.dao.EatDao;
import cn.zhanghui.myspring.beanfactory_set.xml.XmlBeanDefinitionReader;

/**
 * 
 * @ClassName: BeanDefinitionValueResolveTest.java
 * @Description: 测试将beanid变成Bean实例的过程
 * @author: ZhangHui
 * @date: 2019年10月28日 下午5:15:20
 */
public class BeanDefinitionValueResolveTest {

	@Test
	public void testResolveRuntimeBeanReference() {
		DefaultBeanFactory registry = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext.xml"));

		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(registry);

		RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference("eatDao");
		Object value = resolver.resolveValueIfNecessary(runtimeBeanReference);

		assertNotNull(value);
		assertTrue(value instanceof EatDao);
	}

	@Test
	public void testResolveTypedStringValue() {
		DefaultBeanFactory registry = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext.xml"));

		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(registry);

		TypedStringValue typedStringValue = new TypedStringValue("zhanghui");
		Object value = resolver.resolveValueIfNecessary(typedStringValue);

		assertNotNull(value);
		assertTrue("zhanghui".equals(value));
	}
}
