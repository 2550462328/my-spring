package cn.zhanghui.myspring.beanfactory_aop.aop.config;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;
import cn.zhanghui.myspring.beanfactory_aop.beans.BeanUtils;
import cn.zhanghui.myspring.beanfactory_aop.exception.NoSuchBeanDefinitionException;
import cn.zhanghui.myspring.beanfactory_aop.support.DefaultBeanFactory;
import cn.zhanghui.myspring.util.StringUtils;

@Getter
@Setter
public class MethodLocatingFactory {
	
	private String methodName;
	
	private String targetBeanName;
	
	private Method method;
	
	public void setBeanFactory(DefaultBeanFactory beanFactory) throws NoSuchBeanDefinitionException {
		if(!StringUtils.hasText(this.targetBeanName)) {
			throw new IllegalArgumentException("Property 'targetBeanName' is required" );
		}
		if(!StringUtils.hasText(this.methodName)) {
			throw new IllegalStateException("Property 'methodName' is required" );
		}
			
		Class<?> beanClass = beanFactory.getType(this.targetBeanName);
		if(beanClass == null) {
			throw new IllegalArgumentException("Can`t determine type of bean with name'" + this.targetBeanName + "'");
		}
		// 获取相关方法
		this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
		
		if(this.method == null) {
			throw new IllegalStateException("Unable to locate method [" + this.methodName + "] on bean [" + this.targetBeanName +"]");	
		}
	}
	
	public Method getObject() {
		return this.method;
	}
}
