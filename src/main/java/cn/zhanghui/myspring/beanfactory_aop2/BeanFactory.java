package cn.zhanghui.myspring.beanfactory_aop2;

import java.util.List;

import cn.zhanghui.myspring.beanfactory_aop2.exception.NoSuchBeanDefinitionException;

/**
 * @ClassName: BeanFactory.java
 * @Description: 获取bean的实例接口
 * @author: ZhangHui
 * @date: 2019年10月15日 下午4:01:52
 */
public interface BeanFactory {
	Object getBean(String string);
	Class<?> getType(String beanName)  throws NoSuchBeanDefinitionException;
	List<Object> getBeansByType(Class<?> clazz) throws NoSuchBeanDefinitionException ;
}
