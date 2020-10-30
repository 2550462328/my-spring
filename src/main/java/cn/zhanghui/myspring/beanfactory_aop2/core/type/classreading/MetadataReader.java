package cn.zhanghui.myspring.beanfactory_aop2.core.type.classreading;

import cn.zhanghui.myspring.beanfactory_aop2.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_aop2.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_aop2.core.type.ClassMetadata;

/**
 * 对各种ClassVisitor进行包装
 */
public interface MetadataReader {
	Resource getResource();
	
	ClassMetadata getClassMetadata();
	
	AnnotationMetadata getAnnotationMetadata();
}
