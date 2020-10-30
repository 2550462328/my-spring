package cn.zhanghui.myspring.beanfactory_annotation2.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * @ClassName: ConstructorArgument.java
 * @Description: bean下通过构造器传入的bean,维护到argumentValues列表中
 * @author: ZhangHui
 * @date: 2019年11月22日 下午3:01:41
 */
public class ConstructorArgument {
	
	private List<ValueHolder> argumentValues = new LinkedList<>();

	public List<ValueHolder> getArgumentValues() {
		return Collections.unmodifiableList(argumentValues);
	}

	public void addArgumentValue(ValueHolder valueHolder) {
		this.argumentValues.add(valueHolder);
	}
	
	public int getArgumentCount() {
		return argumentValues.size();
	}
	
	public boolean isEmpty() {
		return this.argumentValues.isEmpty();
	}
	
	@Data
	public static class ValueHolder {
		private Object value;
		
		private String type;
		
		private String name;
		
		private int index;
		
		public ValueHolder(Object value) {
			this.value = value;
		}
	}
}


