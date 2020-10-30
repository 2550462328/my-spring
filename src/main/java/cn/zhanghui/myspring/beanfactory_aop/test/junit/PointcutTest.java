package cn.zhanghui.myspring.beanfactory_aop.test.junit;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_aop.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop.aop.MethodMatcher;
import cn.zhanghui.myspring.beanfactory_aop.core.io.ClassPathResource;
import cn.zhanghui.myspring.beanfactory_aop.support.DefaultBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop.test.service.PersonService;
import cn.zhanghui.myspring.beanfactory_aop.xml.XmlBeanDefinitionReader;

public class PointcutTest {
	
	@Test
	public void testPointcut() throws NoSuchMethodException, SecurityException {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("applicationContext3.xml"));
		
		String expression = "execution(* cn.zhanghui.myspring.beanfactory_aop.test.service.*.placeOrder(..))";
		
		AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
		
		pc.setExpression(expression);
		
		MethodMatcher methodMatch = pc.getMethodMatcher();
		
		{ 
			Class<?> targetClass = PersonService.class;
			
			Method method1 = targetClass.getMethod("placeOrder");
			Assert.assertTrue(methodMatch.match(method1));
			
			Method method2 = targetClass.getMethod("getDrinkDao");
			Assert.assertFalse(methodMatch.match(method2));
		}
	}
}
