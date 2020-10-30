package cn.zhanghui.myspring.beanfactory.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory.BeanDefinition;
import cn.zhanghui.myspring.beanfactory.BeanFactory;
import cn.zhanghui.myspring.beanfactory.bean.Car;
import cn.zhanghui.myspring.beanfactory.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory.exception.BeanStoreException;
import cn.zhanghui.myspring.beanfactory.support.DefaultBeanFactory;

/**
 * @ClassName: BeanFactoryTest.java
 * @Description: Spring的bean工厂测试类
 * @author: ZhangHui
 * @date: 2019年10月15日 下午3:39:06
 */
public class BeanFactoryTest {
	@Test
	public void testGetBean()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException {
		BeanFactory beanFactory = new DefaultBeanFactory("applicationContext.xml");
		BeanDefinition bd = beanFactory.getBeanDefinition("car");
		assertEquals("cn.zhanghui.myspring.beanfactory.bean.Car", bd.getClassName());
		Car car = (Car) beanFactory.getBean("car");
		assertNotNull(car);
	}

	@Test
	public void testValidBean() {
		BeanFactory beanFactory = new DefaultBeanFactory("applicationContext.xml");
		try {
			beanFactory.getBean("invalidBean");
		} catch (BeanCreateException e) {
			return;
		}
		Assert.fail("unexpected result");
	}
	
	@Test
	public void testValidXml() {
		BeanFactory beanFactory;
		try {
			beanFactory = new DefaultBeanFactory("appxxx.xml");
		} catch (BeanStoreException e) {
			return;
		}
		beanFactory.getBean("car");
		Assert.fail("unexpected result");
	}
}
