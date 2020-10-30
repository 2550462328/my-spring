package cn.zhanghui.myspring.beanfactory_annotation2.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTest.class, AutowiredAnnotationProcessorTest.class, DependencyDescriptorTest.class,
		InjectionMetadataTest.class })
public class AllTests {

}
