package cn.zhanghui.myspring.beanfactory_aop.aop;

public interface Pointcut {
	MethodMatcher getMethodMatcher();
	String getExpression();
}
