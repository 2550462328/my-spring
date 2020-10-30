package cn.zhanghui.myspring.beanfactory_annotation2.exception;

import cn.zhanghui.myspring.beanfactory_annotation2.beans.BeanExceptionn;

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

	public BeanCreateException(String id, String string) {
		super(id + string);
	}

}
