package cn.zhanghui.myspring.beanfactory_annotation.beans;

import cn.zhanghui.myspring.beanfactory_annotation.exception.TypeMismatchException;

public interface TypeConverter {
	public abstract <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
