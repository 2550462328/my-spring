package cn.zhanghui.myspring.beanfactory_aop.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

	boolean match(Method method );
}
