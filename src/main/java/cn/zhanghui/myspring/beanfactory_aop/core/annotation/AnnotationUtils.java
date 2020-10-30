package cn.zhanghui.myspring.beanfactory_aop.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationUtils {
	
	/**
	 * 获取ae上的注释，如果直接获取不到就遍历ae的所有注释再次确认
	 * @param ae
	 * @param annotationType
	 * @return
	 */
	public static <T extends Annotation> Annotation getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
		T ann = ae.getAnnotation(annotationType);
		if(ann == null) {
			for(Annotation metaAnn : ae.getAnnotations()) {
				ann = metaAnn.annotationType().getAnnotation(annotationType);
				if(ann  != null) {
					break;
				}
			}
		}
		return ann;
	}
	
}
