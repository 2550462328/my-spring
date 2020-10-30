package cn.zhanghui.myspring.beanfactory_aop2.propertyeditors;

import java.beans.PropertyEditorSupport;

import cn.zhanghui.myspring.util.StringUtils;

/**
 * 
 * @ClassName: CustomBooleanEditor.java
 * @Description: 将字符串转换成布尔值
 * @author: ZhangHui
 * @date: 2019年10月29日 上午11:01:08
 */
public class CustomBooleanEditor extends PropertyEditorSupport {
	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";
	public static final String VALUE_ON = "on";
	public static final String VALUE_OFF = "off";
	public static final String VALUE_YES = "yes";
	public static final String VALUE_NO = "no";
	public static final String VALUE_1 = "1";
	public static final String VALUE_0 = "0";
	private final String trueString;
	private final String falseString;
	private final boolean allowEmpty;

	public CustomBooleanEditor(boolean allowEmpty) {
		this(null, null, allowEmpty);
	}

	public CustomBooleanEditor(String trueString, String falseString, boolean allowEmpty) {
		this.trueString = trueString;
		this.falseString = falseString;
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		String input = text != null ? text.trim() : null;
		if ((this.allowEmpty) && (!StringUtils.hasLength(input))) {
			setValue(null);
		} else if ((this.trueString != null) && (this.trueString.equalsIgnoreCase(input))) {
			setValue(Boolean.TRUE);
		} else if ((this.falseString != null) && (this.falseString.equalsIgnoreCase(input))) {
			setValue(Boolean.FALSE);
		} else if ((this.trueString == null) && (("true".equalsIgnoreCase(input)) || ("on".equalsIgnoreCase(input))
				|| ("yes".equalsIgnoreCase(input)) || ("1".equals(input)))) {
			setValue(Boolean.TRUE);
		} else if ((this.falseString == null) && (("false".equalsIgnoreCase(input)) || ("off".equalsIgnoreCase(input))
				|| ("no".equalsIgnoreCase(input)) || ("0".equals(input)))) {
			setValue(Boolean.FALSE);
		} else {
			throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
		}
	}

	public String getAsText() {
		if (Boolean.TRUE.equals(getValue())) {
			return this.trueString != null ? this.trueString : "true";
		}
		if (Boolean.FALSE.equals(getValue())) {
			return this.falseString != null ? this.falseString : "false";
		}
		return "";
	}
}