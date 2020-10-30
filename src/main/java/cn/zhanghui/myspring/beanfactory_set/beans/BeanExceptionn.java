package cn.zhanghui.myspring.beanfactory_set.beans;

/**
 * @ClassName: BeanExceptionn.java
 * @Description: 处理bean时异常的父类
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:59:47
 */
public class BeanExceptionn extends RuntimeException {
	public BeanExceptionn(String message) {
		super(message);
	}

}
