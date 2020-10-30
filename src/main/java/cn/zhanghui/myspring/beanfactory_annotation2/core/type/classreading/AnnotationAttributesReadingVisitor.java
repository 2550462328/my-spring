package cn.zhanghui.myspring.beanfactory_annotation2.core.type.classreading;


import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import cn.zhanghui.myspring.beanfactory_annotation2.core.annotation.AnnotationAttribute;

import java.util.Map;


public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
	
	private final String annotationType;
	
	private final Map<String, AnnotationAttribute> attributeMap;
	
	AnnotationAttribute attribute = new AnnotationAttribute();

	public AnnotationAttributesReadingVisitor(String annotationType,
			Map<String, AnnotationAttribute> attributeMap) {
		super(SpringAsmInfo.ASM_VERSION);
		this.annotationType = annotationType;
		this.attributeMap = attributeMap;
	}
	
	@Override
	public void visit(String name, Object value) {
		this.attribute.put(name, value);
	}
	
	@Override
	public void visitEnd() {
		this.attributeMap.put(this.annotationType, this.attribute);
	}

	
	
}	
