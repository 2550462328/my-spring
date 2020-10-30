package cn.zhanghui.myspring.beanfactory_aop2.test.junit;

import cn.zhanghui.myspring.beanfactory_aop2.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_aop2.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop2.xml.XmlBeanDefinitionReader;

public class CommonTest {
	public Object getBeanFactory(String configFile) {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource(configFile));
		return beanFactory;
	}
}
