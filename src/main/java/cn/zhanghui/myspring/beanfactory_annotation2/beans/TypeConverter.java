package cn.zhanghui.myspring.beanfactory_annotation2.beans;

import cn.zhanghui.myspring.beanfactory_annotation2.exception.TypeMismatchException;

public interface TypeConverter {
	public abstract <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
