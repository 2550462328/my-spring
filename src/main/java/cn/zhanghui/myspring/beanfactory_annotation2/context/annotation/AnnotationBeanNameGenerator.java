package cn.zhanghui.myspring.beanfactory_annotation2.context.annotation;

import java.beans.Introspector;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import cn.zhanghui.myspring.beanfactory_annotation2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation2.annotation.AnnotatedBeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation2.core.annotation.AnnotationAttribute;
import cn.zhanghui.myspring.beanfactory_annotation2.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation2.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_annotation2.support.BeanNameGenerator;
import cn.zhanghui.myspring.util.ClassUtils;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {
	
	@Override
	public String generateBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
		if (bd instanceof AnnotatedBeanDefinition) {
			String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) bd);
			if (StringUtils.isNotBlank(beanName)) {
				return beanName;
			}
		}
		return buildDefaultBeanName(bd, registry);
	}
	
	/**
	 * 从注解的value属性中获取beanName
	 * @param bd
	 * @return
	 */
	private String determineBeanNameFromAnnotation(AnnotatedBeanDefinition bd) {
		AnnotationMetadata amd = (AnnotationMetadata) bd.getMetadata();

		Set<String> types = amd.getAnnotationTypes();
		String beanName = null;

		for (String type : types) {
			AnnotationAttribute attribute = amd.getAnnotationAttribute(type);
			if (attribute.get("value") != null) {
				Object value = attribute.get("value");
				if (value instanceof String) {
					String strVal = (String) value;
					if (StringUtils.isNotBlank(strVal)) {
						beanName = strVal;
					}
				}
			}
		}

		return beanName;
	}
	
	/**
	 * 默认的方式构造
	 * @param bd
	 * @param registry
	 * @return
	 */
	private String buildDefaultBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
		return buildDefaultBeanName(bd);
	}

	private String buildDefaultBeanName(BeanDefinition bd) {
		String shortClassName = ClassUtils.getShorName(bd.getClassName());
		
		//首字母小写（仅对Ab可用，AB无效）
		return Introspector.decapitalize(shortClassName);
	}
	
}
