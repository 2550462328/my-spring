package cn.zhanghui.myspring.beanfactory_up.context.support;

import cn.zhanghui.myspring.beanfactory_up.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_up.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_up.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_up.xml.XmlBeanDefinitionReader;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * @ClassName: AbstractApplicationContext.java
 * @Description: 使用模板方法设计模式合并ClassPathXmlApplication和FileSystemXmlApplication的通用代码
 * @author: ZhangHui
 * @date: 2019年10月25日 下午3:06:22
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
	private DefaultBeanFactory factory = null;
	
	private ClassLoader cl;
	
	public AbstractApplicationContext(String path) {
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = this.getResourceByPath(path);
		reader.loadBeanDefinition(resource);
		factory.setClassLoader(cl);
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
	
}
