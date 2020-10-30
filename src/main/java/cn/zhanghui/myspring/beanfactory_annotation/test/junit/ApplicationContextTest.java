package cn.zhanghui.myspring.beanfactory_annotation.test.junit;

import org.junit.Test;
import org.springframework.util.Assert;

import cn.zhanghui.myspring.beanfactory_annotation.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_annotation.context.support.ClassPathXmlApplicationContext;
import cn.zhanghui.myspring.beanfactory_annotation.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_annotation.test.dao.EatDao;

public class ApplicationContextTest {

	@Test
	public void testGetBeanProperties() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");

		EatDao eatDao = (EatDao) context.getBean("eatDao");
		DrinkDao drinkDao = (DrinkDao) context.getBean("drinkDao");
		Assert.notNull(eatDao);
		Assert.notNull(drinkDao);
	}
}
