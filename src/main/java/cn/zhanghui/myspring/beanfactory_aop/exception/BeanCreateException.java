package cn.zhanghui.myspring.beanfactory_aop.exception;

import cn.zhanghui.myspring.beanfactory_aop.beans.BeanExceptionn;

/**
 * @ClassName: BeanCreateException.java
 * @Description: 获取beanId无效时抛出的异常
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:57:40
 */
public class BeanCreateException extends BeanExceptionn {

	public BeanCreateException(String message) {
		super(message);
	}

	public BeanCreateException(String message, Throwable cause) {
		super(message, cause);
	}

}
