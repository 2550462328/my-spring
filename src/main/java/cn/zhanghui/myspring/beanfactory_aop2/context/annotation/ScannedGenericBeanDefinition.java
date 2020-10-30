package cn.zhanghui.myspring.beanfactory_aop2.context.annotation;

import cn.zhanghui.myspring.beanfactory_aop2.annotation.AnnotatedBeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_aop2.support.GenericBeanDefinition;

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
