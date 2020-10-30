package cn.zhanghui.myspring.beanfactory_aop.beans;

import cn.zhanghui.myspring.beanfactory_aop.exception.TypeMismatchException;

public interface TypeConverter {
	public abstract <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
