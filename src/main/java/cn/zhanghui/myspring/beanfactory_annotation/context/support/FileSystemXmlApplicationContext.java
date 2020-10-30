package cn.zhanghui.myspring.beanfactory_annotation.context.support;

import cn.zhanghui.myspring.beanfactory_annotation.core.io.FileSystemResource;
import cn.zhanghui.myspring.beanfactory_annotation.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext{

	public FileSystemXmlApplicationContext(String path) {
		super(path);
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}
	
}
