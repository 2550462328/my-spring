package cn.zhanghui.myspring.beanfactory_aop2.aop;

public interface Pointcut {
	MethodMatcher getMethodMatcher();
	String getExpression();
}
