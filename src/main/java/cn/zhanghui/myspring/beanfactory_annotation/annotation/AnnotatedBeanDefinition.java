package cn.zhanghui.myspring.beanfactory_annotation.annotation;

import cn.zhanghui.myspring.beanfactory_annotation.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
	AnnotationMetadata getMetadata();
}
