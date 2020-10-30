package cn.zhanghui.myspring.beanfactory_annotation.test.junit;

import org.junit.Assert;
import org.junit.Test;


import cn.zhanghui.myspring.beanfactory_annotation.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.context.annotation.ScannedGenericBeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.core.annotation.AnnotationAttribute;
import cn.zhanghui.myspring.beanfactory_annotation.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_annotation.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_annotation.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation.xml.XmlBeanDefinitionReader;

public class XmlBeanDefinitionReaderTest {
	
	/**
	 * 测试在xmlReader读取xml的时候就会扫描basePackage目录进行ScannedGenericBeanDefinition装载
	 */
	@Test
	public void testParseScanedBean() {
		BeanDefinitionRegistry registry = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext2.xml"));
		
		String annotation = Component.class.getName();
		
		{
			BeanDefinition bd = registry.getBeanDefinition("personService");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) bd;
			AnnotationMetadata amd = (AnnotationMetadata) beanDefinition.getMetadata();
			Assert.assertTrue(amd.hasAnnotation(annotation));
			AnnotationAttribute attribute = amd.getAnnotationAttribute(annotation);
			Assert.assertEquals("personService", attribute.getString("value"));
			
		}
	}
}
