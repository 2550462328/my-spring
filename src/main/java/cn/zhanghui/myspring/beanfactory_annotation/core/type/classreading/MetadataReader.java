package cn.zhanghui.myspring.beanfactory_annotation.core.type.classreading;

import cn.zhanghui.myspring.beanfactory_annotation.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.ClassMetadata;
/**
 * 对各种ClassVisitor进行包装
 */
public interface MetadataReader {
	Resource getResource();
	
	ClassMetadata getClassMetadata();
	
	AnnotationMetadata getAnnotationMetadata();
}
