package cn.zhanghui.myspring.beanfactory_construt.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTest.class, BeanDefinitionTest.class, ConstructorResolverTest.class })
public class AllTests {

}
