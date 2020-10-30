package cn.zhanghui.myspring.beanfactory_aop.config;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class DependencyDescriptor {
	//被修饰的字段
	private final  Member member;
	
	//是否必须有值存在
	private final boolean required;
	
	public DependencyDescriptor(Member member, boolean required) {
		this.member = member;
		this.required = required;
	}
	
	public Class<?> getDependencyType(){
		if(this.member instanceof Field) {
			Field field = (Field) member;
			return field.getType();
		}
		// 如果是放在setter方法上，取第一个参数的类型
		if(this.member instanceof Method) { 
			Method method = (Method)member;
			return method.getParameterTypes()[0];
		}
		throw new RuntimeException("only support field and method dependency");
	}

	public boolean isRequired() {
		return required;
	}
	
}
