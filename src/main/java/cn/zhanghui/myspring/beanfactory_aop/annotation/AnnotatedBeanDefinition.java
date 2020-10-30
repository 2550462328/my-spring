package cn.zhanghui.myspring.beanfactory_aop.annotation;

import cn.zhanghui.myspring.beanfactory_aop.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
	AnnotationMetadata getMetadata();
}
