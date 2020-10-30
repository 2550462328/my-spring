package cn.zhanghui.myspring.beanfactory_construt.test.junit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.zhanghui.myspring.beanfactory_construt.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_construt.context.support.ClassPathXmlApplicationContext;
import cn.zhanghui.myspring.beanfactory_construt.test.service.PersonService;


public class ApplicationContextTest {
	
	@Test
	public void testGetBeanProperties() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
		PersonService personService = (PersonService) context.getBean("personService");
 
		Assert.notNull(personService.getEatDao());
		Assert.notNull(personService.getDrinkDao());
		assertTrue("zhanghui".equals(personService.getName()));
		assertTrue(personService.getAge() == 18);
	}
}
