package cn.zhanghui.myspring.beanfactory_aop.exception;

public class AopInvocationException extends Exception {
	private String messsage;

	public AopInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AopInvocationException(String message) {
		super(message);
	}
	
	
}
