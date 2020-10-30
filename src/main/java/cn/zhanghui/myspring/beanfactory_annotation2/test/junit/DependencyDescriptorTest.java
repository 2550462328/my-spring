package cn.zhanghui.myspring.beanfactory_annotation2.test.junit;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_annotation2.config.DependencyDescriptor;
import cn.zhanghui.myspring.beanfactory_annotation2.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_annotation2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.EatDao;
import cn.zhanghui.myspring.beanfactory_annotation2.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_annotation2.xml.XmlBeanDefinitionReader;

public class DependencyDescriptorTest {
	
	// 测试解析指定类中的变量域，解析过程就是从BeanFactory的BeanMap中查找有没有这个类
	// 所以这也说明了@Autowired是根据类型查找的
	@Test
	public void testResolveDependency() throws NoSuchFieldException, SecurityException {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext3.xml"));
		
		Field f =  PersonService.class.getDeclaredField("eatDao");
		DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
		Object o = beanFactory.resolveDependency(descriptor);
		
		Assert.assertTrue(o instanceof EatDao);
	}
}
