package cn.zhanghui.myspring.beanfactory_up.support;

import java.util.HashMap;
import java.util.Map;

import cn.zhanghui.myspring.beanfactory_up.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_up.config.ConfigurableBeanFactory;
import cn.zhanghui.myspring.beanfactory_up.exception.BeanCreateException;
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
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
		implements BeanDefinitionRegistry, ConfigurableBeanFactory {

	private Map<String, BeanDefinition> container = new HashMap<>();

	private ClassLoader cl;

	@Override
	public Object getBean(String beanId) {
		BeanDefinition bd = this.getBeanDefinition(beanId);
		if(bd == null) {
			return null;
		}
		if(bd.isSingleton()) {  // 放入单例和获取单例
			Object bean = this.getSingleton(beanId);
			if(bean == null) {
				bean = createBean(bd);
				this.registrySingleton(beanId, bean);
			}
			return bean;
		}
		return createBean(bd);
	}
	
	/**
	 * 根据beanDefinition创造Bean
	 * @param bd
	 * @return
	 */
	public Object createBean(BeanDefinition bd) {
		String className = bd.getClassName();
		ClassLoader cl = this.getClassLoader();
		try {
			Class<?> clazz = cl.loadClass(className);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreateException("create bean " + className + "failed!");
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
