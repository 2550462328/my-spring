package cn.zhanghui.myspring.beanfactory_annotation2.support;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import cn.zhanghui.myspring.beanfactory_annotation2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation2.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_annotation2.beans.SimpleTypeConverter;
import cn.zhanghui.myspring.beanfactory_annotation2.config.*;
import cn.zhanghui.myspring.beanfactory_annotation2.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory_annotation2.exception.TypeMismatchException;
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
		implements BeanDefinitionRegistry, ConfigurableBeanFactory, AutowiredCapableBeanFactory {

	private Map<String, BeanDefinition> container = new HashMap<>();

	protected List<BeanPostProcessor> postProcessorsList = new ArrayList<>();

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
	 * 
	 * @param bd
	 * @return
	 */
	private Object initalBean(BeanDefinition bd) {
		if (bd.hasConstructorArgumentValues()) {
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
	 * 给bean设置赋值（注入）属性
	 * @param bd
	 * @param bean
	 */
	private void populateBean(BeanDefinition bd, Object bean) {
		// 为bean Autowired属性
		// 这里说明在bean中@Autowired的属性会在getBean的时候进行注入
		for (BeanPostProcessor processor : this.getBeanPostProcessor()) {
				
			// 是否有实例化的BeanPostProcessor
			if (processor instanceof InstantiationAwareBeanPostProcessor) {
				((InstantiationAwareBeanPostProcessor) processor).postProcessorPropertyValues(bean, bd.getClassName());
			}
		}

		// 根据bean的get/set方法和xml配置的属性进行映射，然后进行属性装配
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
			// try {
			// BeanUtils.setProperty(bean, propertyName, resolvedValue);
			// } catch (IllegalAccessException e) {
			// log.error(e.getMessage());
			// } catch (InvocationTargetException e) {
			// log.error(e.getMessage());
			// }
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

	// 尝试解析descriptor中Class成BeanDefinition
	@Override
	public Object resolveDependency(DependencyDescriptor descriptor) {
		Class<?> typeToMatch = descriptor.getDependencyType();
		for (BeanDefinition bd : this.container.values()) {
			resolveBeanClass(bd);
			Class<?> beanClass = bd.getBeanClass();
			if (typeToMatch.isAssignableFrom(beanClass)) {
				return this.getBean(bd.getId());
			}
		}
		return null;
	}
	
	// 验证beanDefinition中有没有class
	private void resolveBeanClass(BeanDefinition bd) {
		if (bd.hasBeanClass()) {
			return;
		} else {
			bd.resolveBeanClass(this.getClassLoader());
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

	@Override
	public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
		this.postProcessorsList.add(postProcessor);
	}

	@Override
	public List<BeanPostProcessor> getBeanPostProcessor() {
		return this.postProcessorsList;
	}
}
