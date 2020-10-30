package cn.zhanghui.myspring.beanfactory_aop.context.support;

import cn.zhanghui.myspring.beanfactory_aop.core.io.FileSystemResource;
import cn.zhanghui.myspring.beanfactory_aop.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext{

	public FileSystemXmlApplicationContext(String path) {
		super(path);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}
	
}
