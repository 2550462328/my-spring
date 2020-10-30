package cn.zhanghui.myspring.beanfactory_annotation.support;

import cn.zhanghui.myspring.beanfactory_annotation.BeanDefinition;

/**
 * 
 * @ClassName: BeanNameGenerator.java
 * @Description: 可以根据BeanDefinition生成BeanId
 * @author: ZhangHui
 * @date: 2019年12月4日 上午10:38:55
 */
public interface BeanNameGenerator {
	String generateBeanName(BeanDefinition bd, BeanDefinitionRegistry registry);
}
