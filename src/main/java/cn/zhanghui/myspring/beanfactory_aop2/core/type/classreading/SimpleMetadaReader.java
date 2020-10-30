package cn.zhanghui.myspring.beanfactory_aop2.core.type.classreading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.asm.ClassReader;

import lombok.Data;
import cn.zhanghui.myspring.beanfactory_aop2.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_aop2.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_aop2.core.type.ClassMetadata;

@Data
public class SimpleMetadaReader implements MetadataReader {

	private final Resource resource;

	private final ClassMetadata classMetadata;

	private final AnnotationMetadata annotationMetadata;

	public SimpleMetadaReader(Resource resource) throws IOException {
		ClassReader reader;
		try (InputStream is = new BufferedInputStream(resource.getInputStream())) {
			reader = new ClassReader(is);
		}
		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
		reader.accept(visitor, ClassReader.SKIP_DEBUG);

		this.classMetadata = visitor;
		this.annotationMetadata = visitor;
		this.resource = resource;
	}
}
