package cn.zhanghui.myspring.beanfactory_up.support;

import cn.zhanghui.myspring.beanfactory_up.BeanDefinition;

/**
 * @ClassName: BeanDefinitionRegistry.java
 * @Description: 将具体跟bean的相关的内部操作（读取xml装载的bean, 获取beanDefinition）封装在这里
 * @author: ZhangHui
 * @date: 2019年10月24日 下午5:05:46
 */
public interface BeanDefinitionRegistry {
	BeanDefinition getBeanDefinition(String id);
	
	void registryBeanDefinition(String id, BeanDefinition bd);
}
