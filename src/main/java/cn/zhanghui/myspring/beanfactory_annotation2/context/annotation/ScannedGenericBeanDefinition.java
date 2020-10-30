package cn.zhanghui.myspring.beanfactory_annotation2.context.annotation;

import cn.zhanghui.myspring.beanfactory_annotation2.annotation.AnnotatedBeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation2.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation2.support.GenericBeanDefinition;

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
