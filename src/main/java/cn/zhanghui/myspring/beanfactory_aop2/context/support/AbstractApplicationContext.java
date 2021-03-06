package cn.zhanghui.myspring.beanfactory_aop2.context.support;


import java.util.ArrayList;
import java.util.List;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.annotation.AutowiredAnnotationProcessor;
import cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj.AspectJAutoProxyCreater;
import cn.zhanghui.myspring.beanfactory_aop2.config.BeanPostProcessor;
import cn.zhanghui.myspring.beanfactory_aop2.config.ConfigurableBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_aop2.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_aop2.exception.NoSuchBeanDefinitionException;
import cn.zhanghui.myspring.beanfactory_aop2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.xml.XmlBeanDefinitionReader;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * @ClassName: AbstractApplicationContext.java
 * @Description: 使用模板方法设计模式合并ClassPathXmlApplication和FileSystemXmlApplication的通用代码
 * @author: ZhangHui
 * @date: 2019年10月25日 下午3:06:22
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
	private DefaultBeanFactory factory = null;
	
	protected List<BeanPostProcessor> postProcessorsList = new ArrayList<>();
	
	private ClassLoader cl;
	
	public AbstractApplicationContext(String path) {
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = this.getResourceByPath(path);
		reader.loadBeanDefinition(resource);
		factory.setClassLoader(cl);
		
		// 这里为factory添加BeanPostProcessor，用来给Bean装配属性，比如@Autowired、@Resource的属性
		registerBeanFactoryProcessors(factory);
	}
	
	public Object getBean(String beanId) {
		return factory.getBean(beanId);
	}

	protected abstract Resource getResourceByPath(String path);

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
		postProcessorsList.add(postProcessor);
	}

	@Override
	public List<BeanPostProcessor> getBeanPostProcessor() {
		return postProcessorsList;
	}
	
	// 这里为factory添加BeanPostProcessor
	protected void registerBeanFactoryProcessors(ConfigurableBeanFactory beanFactory) {
		AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
		postProcessor.setBeanFactory(factory);
		AspectJAutoProxyCreater proxyCreater = new AspectJAutoProxyCreater();
		proxyCreater.setBeanFactory(factory);
		factory.addBeanPostProcessor(postProcessor);
		factory.addBeanPostProcessor(proxyCreater);
	}
	
	@Override
	public Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
		BeanDefinition bd = factory.getBeanDefinition(beanName);
		if(bd == null) {
			throw new NoSuchBeanDefinitionException(beanName);
		}
		return bd.getBeanClass();
	}
	
	@Override
	public List<Object> getBeansByType(Class<?> type) throws NoSuchBeanDefinitionException {
		return this.factory.getBeansByType(type);
	}
}
