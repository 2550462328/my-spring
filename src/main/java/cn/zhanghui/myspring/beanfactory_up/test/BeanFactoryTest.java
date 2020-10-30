package cn.zhanghui.myspring.beanfactory_up.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_up.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_up.bean.Student;
import cn.zhanghui.myspring.beanfactory_up.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_up.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory_up.exception.BeanStoreException;
import cn.zhanghui.myspring.beanfactory_up.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_up.xml.XmlBeanDefinitionReader;

/**
 * @ClassName: BeanFactoryTest.java
 * @Description: Spring的bean工厂测试类
 * @author: ZhangHui
 * @date: 2019年10月15日 下午3:39:06
 */
public class BeanFactoryTest {

	DefaultBeanFactory beanFactory = null;
	XmlBeanDefinitionReader reader = null;
	
	// 这里@Before注释的代码会在每个测试用例运行前执行，保证了测试用例隔离性
	@Before
	public void setUp() {
		System.out.println("我重置了beanFactory和reader");
		beanFactory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(beanFactory);
	}

	@Test
	public void testGetBean()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException {
		ClassPathResource resource = new ClassPathResource("applicationContext.xml");
		reader.loadBeanDefinition(resource);

		BeanDefinition bd = beanFactory.getBeanDefinition("student");
		assertEquals("cn.zhanghui.myspring.beanfactory_up.bean.Student", bd.getClassName());

		Student student = (Student) beanFactory.getBean("student");

		assertNotNull(student);
	}
	
	@Test
	public void testValidBean() {
		ClassPathResource resource = new ClassPathResource("applicationContext.xml");
		reader.loadBeanDefinition(resource);
		try {
			beanFactory.getBean("invalidBean");
		} catch (BeanCreateException e) {
			return;
		}
		Assert.fail("unexpected result");
	}

	@Test
	public void testValidXml() {
		try {
			ClassPathResource resource = new ClassPathResource("applicationXXXX.xml");
			reader.loadBeanDefinition(resource);
		} catch (BeanStoreException e) {
			return;
		}
		beanFactory.getBean("car");
		Assert.fail("unexpected result");
	}
	
	@Test
	public void testGetBeanScope()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException {
		ClassPathResource resource = new ClassPathResource("applicationContext.xml");
		reader.loadBeanDefinition(resource);

		BeanDefinition bd = beanFactory.getBeanDefinition("student");
		assertTrue(bd.isSingleton());
		assertFalse(bd.isPrototype());
		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());
		assertEquals("cn.zhanghui.myspring.beanfactory_up.bean.Student", bd.getClassName());

		Student student = (Student) beanFactory.getBean("student");
		assertNotNull(student);
		
		Student student1 = (Student) beanFactory.getBean("student");
		assertTrue(student == student1);
		assertTrue(student.equals(student1));
	}
}
