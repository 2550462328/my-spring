package cn.zhanghui.myspring.beanfactory_annotation.test.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTest.class, ApplicationContextTest.class, ClasspathBeanDefinitionScannerTest.class, ClassReaderTest.class, PackageResourceLoaderTest.class, XmlBeanDefinitionReaderTest.class})
public class AllTests {

}
