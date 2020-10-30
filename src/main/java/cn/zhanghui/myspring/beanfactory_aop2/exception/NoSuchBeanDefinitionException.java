package cn.zhanghui.myspring.beanfactory_aop2.exception;

public class NoSuchBeanDefinitionException extends Exception {
	private String messsage;

	public NoSuchBeanDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchBeanDefinitionException(String message) {
		super(message);
	}
	
	
}
