package cn.zhanghui.myspring.beanfactory_construt.test.junit;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_construt.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_construt.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_construt.support.ConstructorResolver;
import cn.zhanghui.myspring.beanfactory_construt.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_construt.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_construt.xml.XmlBeanDefinitionReader;

public class ConstructorResolverTest {
	
	@Test
	public void testAutowiredConstructor() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext1.xml"));
		
		BeanDefinition bd = beanFactory.getBeanDefinition("personService");
		
		ConstructorResolver resolver = new ConstructorResolver(beanFactory);
		
		PersonService personService = (PersonService) resolver.autowiredConstructor(bd);
		
		Assert.assertNotNull(personService.getEatDao());
		Assert.assertNotNull(personService.getDrinkDao());
		Assert.assertEquals("zhanghui", personService.getName());
		Assert.assertEquals(18, personService.getAge());
	}
}
