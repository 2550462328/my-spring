package cn.zhanghui.myspring.beanfactory_construt.test.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_construt.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_construt.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_construt.beans.ConstructorArgument.ValueHolder;
import cn.zhanghui.myspring.beanfactory_construt.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_construt.config.TypedStringValue;
import cn.zhanghui.myspring.beanfactory_construt.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_construt.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_construt.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_construt.xml.XmlBeanDefinitionReader;

public class BeanDefinitionTest {
	
	/**
	 * 测试从指定Bean中获取它的property 的 key-value
	 */
	@Test
	public void testGetBeanProperties() {
		BeanDefinitionRegistry registry = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext1.xml"));
		
		BeanDefinition bd = registry.getBeanDefinition("personService");
		Assert.assertEquals("cn.zhanghui.myspring.beanfactory_construt.test.service.PersonService", bd.getClassName());
		
		ConstructorArgument args = bd.getConstructorArgument();
		
		List<ValueHolder> valueHolders = args.getArgumentValues();
		
		assertTrue(valueHolders.size() == 4);
		{
			ValueHolder pv = this.getValueHolder("eatDao", valueHolders);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		{
			ValueHolder pv = this.getValueHolder("drinkDao", valueHolders);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof RuntimeBeanReference);
		}
		{
			ValueHolder pv = this.getValueHolder("zhanghui", valueHolders);
			assertNotNull(pv);
			assertTrue(pv.getValue() instanceof TypedStringValue);
			
			TypedStringValue stringValue = (TypedStringValue) pv.getValue();
			assertNotNull(stringValue);
			assertTrue("zhanghui".equals(stringValue.getValue()));
		}
	}

	private ValueHolder getValueHolder(String name, List<ValueHolder> pList) {
		for(ValueHolder pv : pList) {
			Object obj = pv.getValue();
			if(obj instanceof TypedStringValue) {
				TypedStringValue stringValue = (TypedStringValue) obj;
				if(name.equals(stringValue.getValue())) {
					return pv;
				}
			}
			if(obj instanceof RuntimeBeanReference) {
				RuntimeBeanReference runtimeBean = (RuntimeBeanReference) obj;
				if(name.equals(runtimeBean.getName())) {
					return pv;
				}
			}
		}
		return null;
	}
}
