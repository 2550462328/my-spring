package cn.zhanghui.myspring.beanfactory_set.beans;

import lombok.Data;

@Data
public class PropertyValue {
	private final String name;
	
	private final Object value;
	
	private boolean converted = false; // 对于name-ref的情形是否已转换
	
	private  String convertedValue; // name-ref情形转换后的值

	public PropertyValue(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}
}
