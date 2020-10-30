package cn.zhanghui.myspring.beanfactory_annotation.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

import cn.zhanghui.myspring.util.NumberUtils;
import cn.zhanghui.myspring.util.StringUtils;

/**
 * 
 * @ClassName: CustomNumberEditor.java
 * @Description: 将字符串转换成数字
 * @author: ZhangHui
 * @date: 2019年10月29日 上午10:38:03
 */
public class CustomNumberEditor extends PropertyEditorSupport {
	private final Class<? extends Number> numberClass;
	private final NumberFormat numberFormat;
	private final boolean allowEmpty;

	public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
		this(numberClass, null, allowEmpty);
	}

	public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty)
			throws IllegalArgumentException {
		if ((numberClass == null) || (!Number.class.isAssignableFrom(numberClass))) {
			throw new IllegalArgumentException("Property class must be a subclass of Number");
		}
		this.numberClass = numberClass;
		this.numberFormat = numberFormat;
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException  {
		if ((this.allowEmpty) && (!StringUtils.hasText(text))) {
			setValue(null);
		} else if (this.numberFormat != null) {
			setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
		} else {
			// 核心转换方法
			setValue(NumberUtils.parseNumber(text, this.numberClass));
		}
	}

	public void setValue(Object value) {
		if ((value instanceof Number)) {
			super.setValue(NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass));
		} else {
			super.setValue(value);
		}
	}

	public String getAsText() {
		Object value = getValue();
		if (value == null) {
			return "";
		}
		if (this.numberFormat != null) {
			return this.numberFormat.format(value);
		}
		return value.toString();
	}
}
