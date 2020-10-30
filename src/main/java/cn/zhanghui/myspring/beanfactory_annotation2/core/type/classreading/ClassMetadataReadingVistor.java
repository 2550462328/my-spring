package cn.zhanghui.myspring.beanfactory_annotation2.core.type.classreading;

import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;

import lombok.Data;
import cn.zhanghui.myspring.beanfactory_annotation2.core.type.ClassMetadata;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * ClassReader、ClassVisitor、ClassWriter中的ClassVisitor
 * @ClassName: ClassMetadataReadingVistor.java
 * @Description: 负责接口ClassReader在read指定class文件时回调的值，在reader.accept(visitor)的时候流程开始
                              会经历visit() -> visit filed() -> visit method() -> visit end()
 * @author: ZhangHui
 * @date: 2019年12月3日 下午2:29:33
 */
@Data
public class ClassMetadataReadingVistor extends ClassVisitor implements ClassMetadata {

	private String className;

	private boolean isInterface;

	private boolean isAbstract;

	private boolean isFinal;

	private String superClassName;

	private String[] interfaces;

	public ClassMetadataReadingVistor() {
		super(SpringAsmInfo.ASM_VERSION);
	}

	/**
	 * 这里只覆盖了一个方法获取class文件的类信息
	 */
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.className = ClassUtils.convertResourcePathToClassName(name);
		this.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
		this.isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
		this.isFinal = (access & Opcodes.ACC_FINAL) != 0;
		if (StringUtils.isNotBlank(superName)) {
			this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
		}
		this.interfaces = new String[interfaces.length];

		System.arraycopy(interfaces, 0, this.interfaces, 0, interfaces.length);

		this.interfaces = Stream.of(this.interfaces).map(str -> ClassUtils.convertResourcePathToClassName(str))
				.toArray(String[]::new);

	}

}
