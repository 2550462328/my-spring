package cn.zhanghui.myspring.beanfactory_annotation2.test.junit;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.zhanghui.myspring.beanfactory_annotation2.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_annotation2.context.support.ClassPathXmlApplicationContext;
import cn.zhanghui.myspring.beanfactory_annotation2.test.service.PersonService;

public class ApplicationContextTest {

	@Test
	public void testGetBeanProperties() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext3.xml");
		PersonService personService = (PersonService) context.getBean("personService");

		Assert.notNull(personService.getEatDao());
		Assert.notNull(personService.getDrinkDao());
	}
}
