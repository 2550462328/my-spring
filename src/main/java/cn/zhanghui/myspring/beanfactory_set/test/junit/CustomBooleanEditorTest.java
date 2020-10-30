package cn.zhanghui.myspring.beanfactory_set.test.junit;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_set.propertyeditors.CustomBooleanEditor;

/**
 * @ClassName: CustomNumberEditorTest.java
 * @Description: 测试将一个字符串转换成布尔值
 * @author: ZhangHui
 * @date: 2019年10月29日 上午10:29:14
 */
public class CustomBooleanEditorTest {

	@Test
	public void testConvertString_normal() {
		CustomBooleanEditor editor = new CustomBooleanEditor(true);

		editor.setAsText("true");
		Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
		editor.setAsText("false");
		Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
		
		editor.setAsText("on");
		Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
		editor.setAsText("off");
		Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
		
		editor.setAsText("yes");
		Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
		editor.setAsText("no");
		Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
		
		editor.setAsText("1");
		Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
		editor.setAsText("0");
		Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
		
		CustomBooleanEditor editor1 = new CustomBooleanEditor("hello", "world", true);
		editor1.setAsText("hello");
		Assert.assertEquals(true, ((Boolean) editor1.getValue()).booleanValue());
		editor1.setAsText("world");
		Assert.assertEquals(false, ((Boolean) editor1.getValue()).booleanValue());
	}

	@Test
	public void testConvertString_border() {
		CustomBooleanEditor editor = new CustomBooleanEditor(true);

		editor.setAsText("");
		Assert.assertTrue(editor.getValue() == null);
	}

	@Test
	public void testConvertString_abnormal() {
		CustomBooleanEditor editor = new CustomBooleanEditor(true);

		try {
			editor.setAsText("aaa11");
		} catch (IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}
}
