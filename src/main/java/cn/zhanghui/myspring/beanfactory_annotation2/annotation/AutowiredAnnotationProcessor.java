package cn.zhanghui.myspring.beanfactory_annotation2.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import cn.zhanghui.myspring.beanfactory.bean.BeanExceptionn;
import cn.zhanghui.myspring.beanfactory_annotation2.config.AutowiredCapableBeanFactory;
import cn.zhanghui.myspring.beanfactory_annotation2.config.InstantiationAwareBeanPostProcessor;
import cn.zhanghui.myspring.beanfactory_annotation2.core.annotation.AnnotationUtils;
import cn.zhanghui.myspring.beanfactory_annotation2.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory_annotation2.stereotype.Autowired;
import cn.zhanghui.myspring.beanfactory_annotation2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.util.ReflectionUtils;

/**
 * @ClassName: AutowiredAnnotationProcessor.java
 * @Description: 解析指定的类，返回InjectionMetadata
 * @author: ZhangHui
 * @date: 2019年12月6日 上午10:28:17
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {
	private AutowiredCapableBeanFactory beanFactory;

	private String requiredParameterName = "required";

	private boolean requiredParameterValue = true;

	private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

	public AutowiredAnnotationProcessor() {
		this.autowiredAnnotationTypes.add(Autowired.class);
	}

	public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
		LinkedList<InjectionElement> elements = new LinkedList<>();
		Class<?> targetClass = clazz;

		do {
			LinkedList<InjectionElement> currentElements = new LinkedList<>();
			for (Field field : targetClass.getDeclaredFields()) {
				
				//查找@Autowired注释
				Annotation ann = findAutowiredAnnotation(field);
				if (ann != null) {
					// 确认被修饰的字段是否是static的
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}

					boolean required = determinedRequiredStatus(ann);
					// 新增一条InjectionElement
					currentElements.add(new AutowiredFieldElement(field, required, beanFactory));
				}
			}

			for (Method method : targetClass.getDeclaredMethods()) {
				//查找@Autowired注释
				Annotation ann = findAutowiredAnnotation(method);
				if (ann != null) {
					// 确认被修饰的字段是否是static的
					if (Modifier.isStatic(method.getModifiers())) {
						continue;
					}
					boolean required = determinedRequiredStatus(ann);
					// 新增一条InjectionElement
					currentElements.add(new AutowiredMethodElement(method, required, beanFactory));
				}
			}
			elements.addAll(0, currentElements);
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);

		return new InjectionMetadata(clazz, elements);
	}
	
	/**
	 * 在ao上查找autowiredAnnotationTypes中的注释
	 * 这里查找方式说明，如果在变量或者方法上面加上了@Autowired和@Resource注释的话，只会生效第一个
	 * @param ao
	 * @return
	 */
	private Annotation findAutowiredAnnotation(AccessibleObject ao) {
		for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
			Annotation ann = AnnotationUtils.getAnnotation(ao, type);
			if (ann != null) {
				return ann;
			}
		}
		return null;
	}
	
	/**
	 * 获取注释（这里指@Autowired）的required属性
	 * @param ann
	 * @return
	 */
	private boolean determinedRequiredStatus(Annotation ann) {
		try {
			Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
			if (method == null) {
				return true;
			}
			return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
		} catch (Exception e) {
			return true;
		}
	}

	public void setBeanFactory(DefaultBeanFactory factory) {
		this.beanFactory = factory;
	}

	@Override
	public Object beforeInitialization(Object bean, String beanName) throws BeanExceptionn {
		return null;
	}

	@Override
	public Object afterInitialization(Object bean, String beanName) throws BeanExceptionn {
		return null;
	}

	@Override
	public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanExceptionn {
		return null;
	}

	@Override
	public boolean afterInstantiation(Object bean, String beanName) {
		return false;
	}

	@Override
	public void postProcessorPropertyValues(Object bean, String beanName) {
		InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
		try {
			metadata.inject(bean);
		} catch (Throwable e) {
			throw new BeanCreateException("Inject dependency failure!");
		}
	}

}
