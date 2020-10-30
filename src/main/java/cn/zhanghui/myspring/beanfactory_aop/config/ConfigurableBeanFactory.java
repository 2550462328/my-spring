package cn.zhanghui.myspring.beanfactory_aop.config;

import java.util.List;

import cn.zhanghui.myspring.beanfactory_aop.BeanFactory;


/**
 * @ClassName: ConfigurableBeanFactory.java
 * @Description: 为classPathResource配置ClassLoader
 * @author: ZhangHui
 * @date: 2019年10月25日 下午4:09:40
 */
public interface ConfigurableBeanFactory extends BeanFactory{
	void setClassLoader(ClassLoader cl);
	
	ClassLoader getClassLoader();
	
	//因为BeanPostProcessor可能不止一个（在我们学习中只是配置Autowired的情形），所以需要进行配置
	void addBeanPostProcessor(BeanPostProcessor postProcessor);
	List<BeanPostProcessor> getBeanPostProcessor();
	
}
