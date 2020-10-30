package cn.zhanghui.myspring.beanfactory_annotation2.test.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_annotation2.annotation.AutowiredAnnotationProcessor;
import cn.zhanghui.myspring.beanfactory_annotation2.annotation.InjectionElement;
import cn.zhanghui.myspring.beanfactory_annotation2.annotation.InjectionMetadata;
import cn.zhanghui.myspring.beanfactory_annotation2.config.DependencyDescriptor;
import cn.zhanghui.myspring.beanfactory_annotation2.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_annotation2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.EatDao;
import cn.zhanghui.myspring.beanfactory_annotation2.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_annotation2.xml.XmlBeanDefinitionReader;

public class AutowiredAnnotationProcessorTest {

	private DefaultBeanFactory factory;

	@Before
	public void init() {
		DrinkDao drinkDao = new DrinkDao();
		EatDao eatDao = new EatDao();
		factory = new DefaultBeanFactory() {
			public Object resolveDependency(DependencyDescriptor descriptor) {
				if (descriptor.getDependencyType().equals(EatDao.class)) {
					return eatDao;
				}
				if (descriptor.getDependencyType().equals(DrinkDao.class)) {
					return drinkDao;
				}
				throw new RuntimeException("can`t support more type to test!");
			}
		};
	}
	
	@Test
	public void testGetInjectionMetadata() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext3.xml"));
		
		AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
		processor.setBeanFactory(beanFactory);

		InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PersonService.class);

		List<InjectionElement> elements = injectionMetadata.getElements();
		Assert.assertEquals(2, elements.size());
		
		PersonService personService = new PersonService();
		
		for(InjectionElement element : elements) {
			element.inject(personService);
		}
		
		Assert.assertNotNull(personService.getEatDao());
		Assert.assertNotNull(personService.getDrinkDao());
	}
}
