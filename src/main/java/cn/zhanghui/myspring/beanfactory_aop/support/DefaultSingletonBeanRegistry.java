package cn.zhanghui.myspring.beanfactory_aop.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.zhanghui.myspring.beanfactory_aop.config.SingletonBeanRegistry;
import cn.zhanghui.myspring.util.Assert;

/**
 * @ClassName: DefaultSingletonBeanRegistry.java
 * @Description: 将一个bean放入单例map中和从单例Map中获取bean，是控制scope的核心方法
 * @author: ZhangHui
 * @date: 2019年10月25日 下午5:06:34
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
	
	@Override
	public void registrySingleton(String beanName, Object singletonObject) {
		Assert.notNull(beanName, "beanName must not be null");
		Object oldObject = this.singletonObjects.get(beanName);
		if(oldObject != null) {
			throw new IllegalStateException("there is already exists " + beanName + "object");
		}
		this.singletonObjects.put(beanName, singletonObject);
	}

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}
	
}
