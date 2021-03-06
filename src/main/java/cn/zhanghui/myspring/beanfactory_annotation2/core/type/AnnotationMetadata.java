package cn.zhanghui.myspring.beanfactory_annotation2.core.type;

import java.util.Set;

import cn.zhanghui.myspring.beanfactory_annotation2.core.annotation.AnnotationAttribute;


public interface AnnotationMetadata extends ClassMetadata {
	Set<String> getAnnotationTypes();
	
	boolean hasAnnotation(String annotationName);
	
	AnnotationAttribute getAnnotationAttribute(String annotationType);
}
