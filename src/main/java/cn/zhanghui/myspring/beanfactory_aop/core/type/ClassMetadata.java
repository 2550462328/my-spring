package cn.zhanghui.myspring.beanfactory_aop.core.type;

public interface ClassMetadata {

	String getClassName();

	boolean isInterface();

	boolean isAbstract();

	boolean isFinal();

	String getSuperClassName();

	String[] getInterfaces();
}
