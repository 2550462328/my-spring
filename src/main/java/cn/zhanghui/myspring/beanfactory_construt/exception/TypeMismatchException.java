package cn.zhanghui.myspring.beanfactory_construt.exception;

public class TypeMismatchException extends Exception {
	private String message;

	public TypeMismatchException(String message) {
		super(message);
		this.message = message;
	}
	
}
