package cn.zhanghui.myspring.beanfactory_annotation.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import cn.zhanghui.myspring.beanfactory_annotation.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_annotation.beans.SimpleTypeConverter;
import cn.zhanghui.myspring.beanfactory_annotation.config.ConfigurableBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory_annotation.exception.TypeMismatchException;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * @ClassName: DefaultBeanFactory.java
 * @Description: 默认获取bean的实例，从xml中装载bean和获取bean 这里将原有的BeanFactory分成两个接口
 *               BeanFactory 负责跟bean有关的操作
 *               BeanDefinitionRegistry操作BeanDefinition和xml
 *               实现职责分离，不用在DefaultBeanFactory中既装载xml又要获取相应的实例 从用户出发
 *               关注点自然只是怎么从DefaultBeanFactory中获取我想要的Bean实例
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:55:50
 */
@Slf4j
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
		implements BeanDefinitionRegistry, ConfigurableBeanFactory {

	private Map<String, BeanDefinition> container = new HashMap<>();

	private ClassLoader cl;
	
	@Override
	public Object getBean(String beanId) {
		BeanDefinition bd = this.getBeanDefinition(beanId);
		if (bd == null) {
			return null;
		}
		if (bd.isSingleton()) { // 放入单例和获取单例
			Object bean = this.getSingleton(beanId);
			if (bean == null) {
				bean = createBean(bd);
				this.registrySingleton(beanId, bean);
			}
			return bean;
		}
		return createBean(bd);
	}

	/**
	 * 创造bean，并给bean的property赋值
	 * 
	 * @param bd
	 * @return
	 */
	private Object createBean(BeanDefinition bd) {
		
		Object bean = initalBean(bd);
		// 给bean设置属性
		populateBean(bd, bean);
		return bean;
	}
	
	/***
	 * 根据BeanDefinition初始化Bean
	 * @param bd
	 * @return
	 */
	private Object initalBean(BeanDefinition bd) {
		if(bd.hasConstructorArgumentValues()) {
			ConstructorResolver constructorResolver = new ConstructorResolver(this);
			return constructorResolver.autowiredConstructor(bd);
		}
		String className = bd.getClassName();
		ClassLoader cl = this.getClassLoader();
		
		try {
			Class<?> clazz = cl.loadClass(className);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreateException("create bean " + className + "failed!");
		}
	}
	
	/**
	 * 给bean设置从propertyValues中解析出的属性
	 * @param bd
	 * @param bean
	 */
	private void populateBean(BeanDefinition bd, Object bean) {
		List<PropertyValue> pvList = bd.getPropertyValues();
		if (pvList.isEmpty()) {
			return;
		}

		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
		SimpleTypeConverter typeConverter = new SimpleTypeConverter();
		for (PropertyValue pv : pvList) {
			String propertyName = pv.getName();
			Object orignalValue = pv.getValue();
			Object resolvedValue = resolver.resolveValueIfNecessary(orignalValue);
			
			// String转换成Number和给bean设置属性的过程可以直接用这个代替
//			try {
//				BeanUtils.setProperty(bean, propertyName, resolvedValue);
//			} catch (IllegalAccessException e) {
//				log.error(e.getMessage());
//			} catch (InvocationTargetException e) {
//				log.error(e.getMessage());
//			}
			// 下面是spring源码的写法
			try {
				// 反射设置属性
				BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				for (PropertyDescriptor pd : pds) {
					if (propertyName.equals(pd.getName())) {
						// 类型不匹配时进行转换，主要是将string类型转换成其他类
						Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, pd.getPropertyType());
						pd.getWriteMethod().invoke(bean, convertedValue);
					}
				}
			} catch (IntrospectionException e) {
				log.error(e.getMessage());
			} catch (IllegalAccessException e) {
				log.error(e.getMessage());
			} catch (IllegalArgumentException e) {
				log.error(e.getMessage());
			} catch (InvocationTargetException e) {
				log.error(e.getMessage());
			} catch (TypeMismatchException e) {
				log.error(e.getMessage());
			} 
		}
	}

	
	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return this.container.get(beanId);
	}
	
	@Override
	public void registryBeanDefinition(String beanId, BeanDefinition bd) {
		this.container.put(beanId, bd);
	}

	@Override
	public void setClassLoader(ClassLoader cl) {
		this.cl = cl;
	}

	@Override
	public ClassLoader getClassLoader() {
		return this.cl != null ? cl : ClassUtils.getDefaultClassLoader();
	}
}
