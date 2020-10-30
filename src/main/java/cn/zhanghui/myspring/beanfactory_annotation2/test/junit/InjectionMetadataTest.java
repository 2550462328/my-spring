package cn.zhanghui.myspring.beanfactory_annotation2.test.junit;

import java.lang.reflect.Field;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_annotation2.annotation.AutowiredFieldElement;
import cn.zhanghui.myspring.beanfactory_annotation2.annotation.InjectionElement;
import cn.zhanghui.myspring.beanfactory_annotation2.annotation.InjectionMetadata;
import cn.zhanghui.myspring.beanfactory_annotation2.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_annotation2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.EatDao;
import cn.zhanghui.myspring.beanfactory_annotation2.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_annotation2.xml.XmlBeanDefinitionReader;

public class InjectionMetadataTest {

	@Test
	public void testInject() throws NoSuchFieldException, SecurityException {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext3.xml"));

		Class<?> clazz = PersonService.class;

		LinkedList<InjectionElement> elements = new LinkedList<>();
		{
			// 这里相当于封装了DependencyDescriptor的resolveDependency方法
			// 根据解析区域（变量域、构造方法、get方法）返回不同的解析对象
			// 加入beanFactory的目的是为了对这个解析对象进一步递归解析操作，比如解析它里面的@Autowired
			Field f = clazz.getDeclaredField("eatDao");
			InjectionElement injectionEle = new AutowiredFieldElement(f, true, beanFactory);
			elements.add(injectionEle);
		}
		{
			Field f = clazz.getDeclaredField("drinkDao");
			InjectionElement injectionEle = new AutowiredFieldElement(f, true, beanFactory);
			elements.add(injectionEle);
		}
		
		// 这里是真正的注入过程，将elemenets中的变量通过setter的方式放到clazz中
		// metadata.inject()实质调用的是InjectionElement.inject()
		InjectionMetadata metadata = new InjectionMetadata(clazz, elements);
		PersonService personServie = new PersonService();
		metadata.inject(personServie);
		
		Assert.assertTrue(personServie.getEatDao() instanceof EatDao);
		Assert.assertTrue(personServie.getDrinkDao() instanceof DrinkDao);
	}
}
