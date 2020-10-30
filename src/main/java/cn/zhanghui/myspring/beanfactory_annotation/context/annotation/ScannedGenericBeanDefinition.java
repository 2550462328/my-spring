package cn.zhanghui.myspring.beanfactory_annotation.context.annotation;

import cn.zhanghui.myspring.beanfactory_annotation.annotation.AnnotatedBeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation.support.GenericBeanDefinition;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition{
	private final AnnotationMetadata metadata;
	
	public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
		super();
		this.metadata = metadata;
		
		setClassName(metadata.getClassName());
	}

	@Override
	public AnnotationMetadata getMetadata() {
		return metadata;
	}
}
