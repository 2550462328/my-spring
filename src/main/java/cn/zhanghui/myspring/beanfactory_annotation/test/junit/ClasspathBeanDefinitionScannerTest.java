package cn.zhanghui.myspring.beanfactory_annotation.test.junit;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_annotation.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.context.annotation.ClassPathBeanDefinitionScanner;
import cn.zhanghui.myspring.beanfactory_annotation.context.annotation.ScannedGenericBeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.core.annotation.AnnotationAttribute;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_annotation.support.DefaultBeanFactory;

public class ClasspathBeanDefinitionScannerTest {
	
	/**
	 * 测试从指定的目录下面读取到带有@Component注释的类，并装载到DefaultBeanFactory中
	 * @throws IOException
	 */
	@Test
	public void testParseScannedBean() throws IOException {
		DefaultBeanFactory factory = new DefaultBeanFactory();

		String basePackage = "cn.zhanghui.myspring.beanfactory_annotation";

		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);

		scanner.doScan(basePackage);

		String annotationName = Component.class.getName();
		{
			BeanDefinition bd = factory.getBeanDefinition("personService");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) bd;
			AnnotationMetadata amd = (AnnotationMetadata) beanDefinition.getMetadata();
			Assert.assertTrue(amd.hasAnnotation(annotationName));
			AnnotationAttribute attribute = amd.getAnnotationAttribute(annotationName);
			Assert.assertEquals("personService", attribute.getString("value"));
		}
		
		
	}
}
