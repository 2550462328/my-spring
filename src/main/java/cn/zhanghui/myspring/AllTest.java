package cn.zhanghui.myspring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.zhanghui.myspring.beanfactory.test.BeanFactoryTest;
import cn.zhanghui.myspring.beanfactory_annotation.test.junit.AllTests;
import cn.zhanghui.myspring.beanfactory_up.test.V1_AllTests;

@RunWith(Suite.class)
@SuiteClasses({ AllTests.class, cn.zhanghui.myspring.beanfactory_annotation2.test.junit.AllTests.class,
		cn.zhanghui.myspring.beanfactory_aop.test.junit.AllTests.class, cn.zhanghui.myspring.beanfactory_aop2.test.junit.AllTests.class,
		cn.zhanghui.myspring.beanfactory_construt.test.junit.AllTests.class,V1_AllTests.class,BeanFactoryTest.class, cn.zhanghui.myspring.beanfactory_set.test.junit.AllTests.class })
public class AllTest {

}
