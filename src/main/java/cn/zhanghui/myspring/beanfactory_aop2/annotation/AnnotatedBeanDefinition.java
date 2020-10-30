package cn.zhanghui.myspring.beanfactory_aop2.annotation;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
	AnnotationMetadata getMetadata();
}
