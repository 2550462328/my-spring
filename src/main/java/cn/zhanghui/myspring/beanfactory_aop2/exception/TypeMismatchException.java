package cn.zhanghui.myspring.beanfactory_aop2.exception;

public class TypeMismatchException extends Exception {
	private String message;

	public TypeMismatchException(String message) {
		super(message);
		this.message = message;
	}
	
}
