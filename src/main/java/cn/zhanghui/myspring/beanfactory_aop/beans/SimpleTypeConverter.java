package cn.zhanghui.myspring.beanfactory_aop.beans;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

import cn.zhanghui.myspring.beanfactory_aop.exception.TypeMismatchException;
import cn.zhanghui.myspring.beanfactory_aop.propertyeditors.CustomBooleanEditor;
import cn.zhanghui.myspring.beanfactory_aop.propertyeditors.CustomNumberEditor;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * 
 * @ClassName: SimpleTypeConverter.java
 * @Description: 类型转换，主要将String类型转换成int、boolean类型等
 * @author: ZhangHui
 * @date: 2019年11月28日 下午2:45:06
 */
public class SimpleTypeConverter implements TypeConverter {
	
	private Map<Class<?>, PropertyEditor> defaultEditors;
	
	@Override
	public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException{
		if(ClassUtils.isAssignableValue(requiredType, value)) {  // 判断value的类型是不是requiredType类或者子类
			return (T)value;
		}else {
			if(value instanceof String) { 
				PropertyEditor editor = findDefaultEditor(requiredType); // 这个就是用来转换的工具类
				try {
					editor.setAsText((String)value);  
				} catch (IllegalArgumentException e) {
					throw new TypeMismatchException("illegal argument " + value);
				}
				return (T)editor.getValue();
			}else {
				throw new RuntimeException("can`t convert value for " + value + " class:" + requiredType);
			}
		}
	}
	
	private PropertyEditor findDefaultEditor(Class<?> requiredType) {
		PropertyEditor editor = this.getDefaultEditor(requiredType);
		if(editor == null) {
			throw new RuntimeException("Editor for " + requiredType + "has not been implemented!");
		}
		return editor;
	}

	private PropertyEditor getDefaultEditor(Class<?> requiredType) {
		if(this.defaultEditors == null) {
			createDefaultEditors();
		}
		return this.defaultEditors.get(requiredType);
	}
	
	private void createDefaultEditors() {
		this.defaultEditors = new HashMap<>(64);
		this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false)); // 转换成boolean
		this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
		
		this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));  // 转换成int
		this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
		
		//TODO（其他类型的Editor都可以在这里加入）
	}
}
