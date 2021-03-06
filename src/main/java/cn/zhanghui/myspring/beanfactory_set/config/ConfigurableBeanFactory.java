package cn.zhanghui.myspring.beanfactory_set.config;

import cn.zhanghui.myspring.beanfactory_set.BeanFactory;

/**
 * @ClassName: ConfigurableBeanFactory.java
 * @Description: 为classPathResource配置ClassLoader
 * @author: ZhangHui
 * @date: 2019年10月25日 下午4:09:40
 */
public interface ConfigurableBeanFactory extends BeanFactory {
	void setClassLoader(ClassLoader cl);
	
	ClassLoader getClassLoader();
}
