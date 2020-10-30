package cn.zhanghui.myspring.beanfactory_annotation2.annotation;

import java.util.List;

import lombok.Getter;

/**
 * 
 * @ClassName: InjectionMetadata.java
 * @Description: 对指定的类进行注入操作
 * @author: ZhangHui
 * @date: 2019年12月6日 上午9:11:59
 */
@Getter
public class InjectionMetadata {
	private final Class<?> targetClass;
	private final List<InjectionElement> elements;
	
	public InjectionMetadata(Class<?> targetClass, List<InjectionElement> elements) {
		this.targetClass = targetClass;
		this.elements = elements;
	}

	public void inject(Object target) {
		if(elements == null || elements.isEmpty()) {
			return;
		}
		for(InjectionElement injectionElement : elements) {
			injectionElement.inject(target);
		}
	}
}
