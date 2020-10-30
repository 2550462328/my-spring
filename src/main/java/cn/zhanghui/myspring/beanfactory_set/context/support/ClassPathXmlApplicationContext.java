package cn.zhanghui.myspring.beanfactory_set.context.support;

import cn.zhanghui.myspring.beanfactory_set.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_set.core.io.Resource;

/**
 * @ClassName: ClassPathXmlApplicationContext.java
 * @Description: 从类环境中加载配置文件
 * @author: ZhangHui
 * @date: 2019年10月24日 下午10:23:34
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	public ClassPathXmlApplicationContext(String path) {
		super(path);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new ClassPathResource(path, this.getClassLoader());
	}
	
}
