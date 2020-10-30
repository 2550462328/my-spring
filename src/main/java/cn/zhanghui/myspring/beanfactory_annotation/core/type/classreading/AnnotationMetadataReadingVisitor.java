package cn.zhanghui.myspring.beanfactory_annotation.core.type.classreading;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.asm.AnnotationVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import lombok.Data;
import cn.zhanghui.myspring.beanfactory_annotation.core.annotation.AnnotationAttribute;
import cn.zhanghui.myspring.beanfactory_annotation.core.type.AnnotationMetadata;

/**
 * 
 * @ClassName: AnnotationMetadataReadingVisitor.java
 * @Description: 这里负责接收ClassReader对class文件的visitAnnotation的内容，也就是类的注释信息
 * @author: ZhangHui
 * @date: 2019年12月3日 下午4:14:50
 */
@Data
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVistor implements AnnotationMetadata {
	private final Set<String> annotationTypes = new LinkedHashSet<>();
	
	private final Map<String, AnnotationAttribute> attributeMap = new LinkedHashMap<>(4);

	@Override
	public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
		String annotationName = Type.getType(desc).getClassName();
		this.annotationTypes.add(annotationName);
		
		// 这里ClassReader已经将类的注释类传过来了，然后我告诉它，如果这个类有详情的话打我下面这个电话
		return new AnnotationAttributesReadingVisitor(annotationName, this.attributeMap);
	}

	public AnnotationAttribute getAnnotationAttribute(String annotation) {
		return attributeMap.get(annotation);
	}

	public boolean hasAnnotation(String annotation) {
		return this.annotationTypes.contains(annotation);
	}
}
