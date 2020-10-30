package cn.zhanghui.myspring.beanfactory_up.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_up.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_up.core.io.FileSystemResource;
import cn.zhanghui.myspring.beanfactory_up.core.io.Resource;

/**
 * @ClassName: 获取Resource的测试
 * @Description: 该类的功能描述
 * @author: ZhangHui
 * @date: 2019年10月25日 下午2:05:22
 */
public class ResourceTest {
	@Test
	public void testClassPathResource() {
		Resource resource = new ClassPathResource("applicationContext.xml");
		try(InputStream is = resource.getInputStream()) {
			assertNotNull(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFileSystemResource() {
		Resource resource = new FileSystemResource("D:\\develop\\workspace\\workspace_yunsen\\MyLearning\\src\\main\\resource\\applicationContext.xml");
		try(InputStream is = resource.getInputStream()) {
			assertNotNull(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
