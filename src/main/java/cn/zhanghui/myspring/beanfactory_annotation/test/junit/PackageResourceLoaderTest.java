package cn.zhanghui.myspring.beanfactory_annotation.test.junit;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_annotation.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_annotation.core.io.support.PackageResourceLoader;

public class PackageResourceLoaderTest {
	
	/**
	 * 测试获得指定目录下的所有文件
	 */
	@Test
	public void testGetResources() {
		PackageResourceLoader loader = new PackageResourceLoader();
		
		Resource[] resource = loader.getResources("cn.zhanghui.myspring.beanfactory_annotation.test.service");
		
		Assert.assertEquals(1, resource.length);
	}
}
