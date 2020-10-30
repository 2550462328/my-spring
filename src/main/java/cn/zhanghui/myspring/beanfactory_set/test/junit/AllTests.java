package cn.zhanghui.myspring.beanfactory_set.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTest.class, BeanDefinitionTest.class, BeanDefinitionValueResolveTest.class,
		CustomBooleanEditorTest.class, CustomNumberEditorTest.class, TypeConverterTest.class })
public class AllTests {

}
