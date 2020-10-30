package cn.zhanghui.myspring.beanfactory_annotation2.annotation;

import cn.zhanghui.myspring.beanfactory_annotation2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation2.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
	AnnotationMetadata getMetadata();
}
