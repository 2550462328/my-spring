package cn.zhanghui.myspring.beanfactory_aop2.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTest.class, BeanDefinitionTest.class, BeanFactoryTest.class})
public class AllTests {
	
}
