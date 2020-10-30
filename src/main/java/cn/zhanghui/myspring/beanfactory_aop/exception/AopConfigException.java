package cn.zhanghui.myspring.beanfactory_aop.exception;

public class AopConfigException extends Exception {
	private String messsage;

	public AopConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public AopConfigException(String message) {
		super(message);
	}
	
	
}
