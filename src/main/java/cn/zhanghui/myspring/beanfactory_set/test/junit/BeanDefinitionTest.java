package cn.zhanghui.myspring.beanfactory_set.test.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_set.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_set.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_set.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_set.config.TypedStringValue;
import cn.zhanghui.myspring.beanfactory_set.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_set.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_set.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_set.xml.XmlBeanDefinitionReader;

public class BeanDefinitionTest {
	
	/**
	 * 测试从指定Bean中获取它的property 的 key-value
	 */
	@Test
	public void testGetBeanProperties() {
		BeanDefinitionRegistry registry = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext.xml"));
		
		BeanDefinition bd = registry.getBeanDefinition("personService");
		
		List<PropertyValue> propertyList = bd.getPropertyValues();
		
		assertTrue(propertyList.size() == 4);
		{
			PropertyValue pv = this.getPropertyValue("eatDao", propertyList);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		{
			PropertyValue pv = this.getPropertyValue("drinkDao", propertyList);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		{
			PropertyValue pv = this.getPropertyValue("name", propertyList);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof TypedStringValue);
			TypedStringValue stringValue = (TypedStringValue) pv.getValue();
			assertNotNull(stringValue);
			assertTrue("zhanghui".equals(stringValue.getValue()));
		}
	}

	private PropertyValue getPropertyValue(String name, List<PropertyValue> pList) {
		for(PropertyValue pv : pList) {
			if(name.equals(pv.getName())) {
				return pv;
			}
		}
		return null;
	}
}
