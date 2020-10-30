package cn.zhanghui.myspring.beanfactory_aop.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MethodLocatingFactoryTest.class, PointcutTest.class,
		CGLibFactoryTest.class, CGLibTest.class, ReflectiveMethodInvocationTest.class })
public class AllTests {

}
