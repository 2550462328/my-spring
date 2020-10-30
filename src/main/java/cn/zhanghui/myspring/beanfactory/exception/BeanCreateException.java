package cn.zhanghui.myspring.beanfactory.exception;

import cn.zhanghui.myspring.beanfactory.bean.BeanExceptionn;

public class BeanCreateException extends BeanExceptionn {

	public BeanCreateException(String message) {
		super(message);
	}

	public BeanCreateException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
