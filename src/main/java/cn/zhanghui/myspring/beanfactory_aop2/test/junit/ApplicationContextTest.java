package cn.zhanghui.myspring.beanfactory_aop2.test.junit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import cn.zhanghui.myspring.beanfactory_aop2.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_aop2.context.support.ClassPathXmlApplicationContext;
import cn.zhanghui.myspring.beanfactory_aop2.test.service.Configurable;
import cn.zhanghui.myspring.util.MessageTracker;


public class ApplicationContextTest {
	
	@Before
	public void init() {
		MessageTracker.clearMessages();
	}
	
	@Test
	public void testPlaceOrder() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext5.xml");
		//如果PersonService有接口，使用接口调用
		Configurable personService = (Configurable) context.getBean("personService");
		//如果没有，直接实例调用
//		PersonService personService = (PersonService) context.getBean("personService");
//
//		Assert.notNull(personService.getEatDao());
//		Assert.notNull(personService.getDrinkDao());
		
		personService.placeOrder();
		Assert.isTrue(MessageTracker.hasMessage("start transaction"));
		Assert.isTrue(MessageTracker.hasMessage("place order"));
		Assert.isTrue(MessageTracker.hasMessage("start commit"));
	}
	
}
