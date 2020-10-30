package cn.zhanghui.myspring.beanfactory_set.beans;

import cn.zhanghui.myspring.beanfactory_set.exception.TypeMismatchException;

public interface TypeConverter {
	public abstract <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
