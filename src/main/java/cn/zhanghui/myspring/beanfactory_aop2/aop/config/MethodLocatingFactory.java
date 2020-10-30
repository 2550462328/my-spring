package cn.zhanghui.myspring.beanfactory_aop2.aop.config;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;
import cn.zhanghui.myspring.beanfactory_aop2.BeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.BeanFactoryAware;
import cn.zhanghui.myspring.beanfactory_aop2.FactoryBean;
import cn.zhanghui.myspring.beanfactory_aop2.beans.BeanUtils;
import cn.zhanghui.myspring.beanfactory_aop2.exception.NoSuchBeanDefinitionException;
import cn.zhanghui.myspring.util.StringUtils;

@Getter
@Setter
public class MethodLocatingFactory implements FactoryBean<Method>, BeanFactoryAware{
	
	private String methodName;
	
	private String targetBeanName;
	
	private Method method;
	
	@Override
	public Method getObject() {
		return this.method;
	}

	@Override
	public Class<?> getObjectType() {
		return Method.class;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if(!StringUtils.hasText(this.targetBeanName)) {
			throw new IllegalArgumentException("Property 'targetBeanName' is required" );
		}
		if(!StringUtils.hasText(this.methodName)) {
			throw new IllegalStateException("Property 'methodName' is required" );
		}
			
		try {
			Class<?> beanClass = beanFactory.getType(this.targetBeanName);
			if(beanClass == null) {
				throw new IllegalArgumentException("Can`t determine type of bean with name'" + this.targetBeanName + "'");
			}
			// 获取相关方法
			this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
			
			if(this.method == null) {
				throw new IllegalStateException("Unable to locate method [" + this.methodName + "] on bean [" + this.targetBeanName +"]");	
			}
		} catch (NoSuchBeanDefinitionException e) {
			e.printStackTrace();
		}
	}
}
