package cn.zhanghui.myspring.beanfactory_aop2.exception;

import cn.zhanghui.myspring.beanfactory_aop2.beans.BeanExceptionn;

/**
 * @ClassName: BeanCreateException.java
 * @Description: 获取beanId无效时抛出的异常
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:57:40
 */
public class BeanAutowiredException extends BeanExceptionn {

	public BeanAutowiredException(String message) {
		super(message);
	}
	
}
