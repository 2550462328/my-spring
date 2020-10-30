package cn.zhanghui.myspring.beanfactory_annotation.test.junit;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import cn.zhanghui.myspring.beanfactory_annotation.core.annotation.AnnotationAttribute;
import cn.zhanghui.myspring.beanfactory_annotation.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.classreading.AnnotationMetadataReadingVisitor;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.classreading.ClassMetadataReadingVistor;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.classreading.MetadataReader;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.classreading.SimpleMetadaReader;
import cn.zhanghui.myspring.beanfactory_annotation.stereotype.Component;

public class ClassReaderTest {
	
	/**
	 * 测试读取Resouce中的类信息
	 * @throws IOException
	 */
	@Test
	public void testRead() throws IOException {
		ClassPathResource resource = new ClassPathResource(
                "cn/zhanghui/myspring/beanfactory_annotation/test/service/PersonService.class");

		ClassReader reader = new ClassReader(resource.getInputStream());

		ClassMetadataReadingVistor visitor = new ClassMetadataReadingVistor();

		reader.accept(visitor, ClassReader.SKIP_DEBUG);

		Assert.assertFalse(visitor.isAbstract());
		Assert.assertFalse(visitor.isInterface());
		Assert.assertEquals("cn.zhanghui.myspring.beanfactory_annotation.test.service.PersonService", visitor.getClassName());
		Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
		Assert.assertEquals(1, visitor.getInterfaces().length);
		Assert.assertEquals("java.io.Serializable", visitor.getInterfaces()[0]);
	}
	
	/**
	 * 测试读取Resouce中的注释信息
	 * @throws IOException
	 */
	@Test
	public void testGetAnnotation() throws IOException {
		// 下面这种写法非常繁琐，下一个测试方法会进行优化
		ClassPathResource resource = new ClassPathResource(
                "cn/zhanghui/myspring/beanfactory_annotation/test/service/PersonService.class");

		ClassReader reader = new ClassReader(resource.getInputStream());

		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

		// 感觉像是两个人在打电话，一个人（Visitor）希望另一个人(Reader)帮它查点东西，这里就是resource的信息
		// accept表示 电话通了，开始沟通
		reader.accept(visitor, ClassReader.SKIP_DEBUG);

		String annotation = "cn.zhanghui.myspring.beanfactory_annotation.stereotype.Component";
		Assert.assertTrue(visitor.hasAnnotation(annotation));

		AnnotationAttribute attribute = visitor.getAnnotationAttribute(annotation);

		Assert.assertEquals("personService", attribute.get("value"));
	}

	@Test
	public void testGetAnnotation2() throws IOException {

		// 下面这种写法就是优化后的，使用一个类去处理Resource并生成想要的Visitor
		ClassPathResource resource = new ClassPathResource(
                "cn/zhanghui/myspring/beanfactory_annotation/test/service/PersonService.class");

		MetadataReader reader = new SimpleMetadaReader(resource);

		AnnotationMetadata amd = reader.getAnnotationMetadata();

		String annotation = Component.class.getName();

		Assert.assertTrue(amd.hasAnnotation(annotation));

		AnnotationAttribute attribute = amd.getAnnotationAttribute(annotation);
		Assert.assertEquals("personService", attribute.get("value"));

		// 对Class 的metadata进行验证
		Assert.assertFalse(amd.isAbstract());
		Assert.assertFalse(amd.isInterface());
	}
}
