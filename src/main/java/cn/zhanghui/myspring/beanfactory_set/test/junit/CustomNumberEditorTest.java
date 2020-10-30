package cn.zhanghui.myspring.beanfactory_set.test.junit;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_set.propertyeditors.CustomNumberEditor;

/**
 * @ClassName: CustomNumberEditorTest.java
 * @Description: 测试将一个字符串转换成数字
 * @author: ZhangHui
 * @date: 2019年10月29日 上午10:29:14
 */
public class CustomNumberEditorTest {

	@Test
	public void testConvertString_normal() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

		editor.setAsText("3");
		Object value = editor.getValue();

		Assert.assertTrue(value instanceof Integer);
		Assert.assertEquals(3, ((Integer) value).intValue());
	}

	@Test
	public void testConvertString_border() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

		editor.setAsText("");
		Assert.assertTrue(editor.getValue() == null);
	}

	@Test
	public void testConvertString_abnormal() {
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

		try {
			editor.setAsText("1.2");
		} catch (IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}
}
