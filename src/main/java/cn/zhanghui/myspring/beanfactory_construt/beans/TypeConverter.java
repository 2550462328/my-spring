package cn.zhanghui.myspring.beanfactory_construt.beans;

import cn.zhanghui.myspring.beanfactory_construt.exception.TypeMismatchException;

public interface TypeConverter {
	public abstract <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
