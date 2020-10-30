package cn.zhanghui.myspring.beanfactory_up.exception;

import cn.zhanghui.myspring.beanfactory.bean.BeanExceptionn;

/**
 * @ClassName: BeanStoreException.java
 * @Description: 读取bean的xml配置文件失败时的异常
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:58:23
 */
public class BeanStoreException extends BeanExceptionn {

	public BeanStoreException(String message) {
		super(message);
	}

}
