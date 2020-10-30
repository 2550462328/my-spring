package cn.zhanghui.myspring.beanfactory_up.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_up.bean.Student;
import cn.zhanghui.myspring.beanfactory_up.context.ApplicationContext;
import cn.zhanghui.myspring.beanfactory_up.context.support.ClassPathXmlApplicationContext;
import cn.zhanghui.myspring.beanfactory_up.context.support.FileSystemXmlApplicationContext;

/**
 * @ClassName: ApplicationContextTest.java
 * @Description: 这里是模拟真实环境下的使用spring获取bean实例
 * @author: ZhangHui
 * @date: 2019年10月24日 下午9:32:16
 */
public class ApplicationContextTest {
	@Test
	public void testGetBean() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Student student = (Student)context.getBean("student");
		assertNotNull(student);
	}
	
	@Test
	public void testGetBeanFromFileSystemContext() {
		ApplicationContext context = new FileSystemXmlApplicationContext("D:\\develop\\workspace\\workspace-myspring\\myspring\\src\\main\\resources\\applicationContext.xml");
		Student student = (Student)context.getBean("student");
		assertNotNull(student);
	}
}
