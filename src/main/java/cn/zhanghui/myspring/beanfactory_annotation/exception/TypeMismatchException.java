package cn.zhanghui.myspring.beanfactory_annotation.exception;

public class TypeMismatchException extends Exception {
	private String message;

	public TypeMismatchException(String message) {
		super(message);
		this.message = message;
	}
	
}
