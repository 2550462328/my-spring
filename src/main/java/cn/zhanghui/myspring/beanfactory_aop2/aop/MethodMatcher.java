package cn.zhanghui.myspring.beanfactory_aop2.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

	boolean match(Method method );
}
