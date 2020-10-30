package cn.zhanghui.myspring.beanfactory_set.test.junit;

import org.junit.Assert;
import org.junit.Test;

import cn.zhanghui.myspring.beanfactory_set.beans.SimpleTypeConverter;
import cn.zhanghui.myspring.beanfactory_set.beans.TypeConverter;
import cn.zhanghui.myspring.beanfactory_set.exception.TypeMismatchException;


/**
 * 
 * @ClassName: TypeConvertTest.java
 * @Description: 对字符串进行类型转换
 * @author: ZhangHui
 * @date: 2019年10月29日 上午11:07:35
 */
public class TypeConverterTest {
	@Test
	public void testConvertStringToInt_normal() throws TypeMismatchException {
		TypeConverter typeConverter = new SimpleTypeConverter();
		Integer i = typeConverter.convertIfNecessary("3", Integer.class);
		Assert.assertEquals(3, i.intValue());
	}
	
	@Test
	public void testConvertStringToInt_border() throws TypeMismatchException {
		TypeConverter typeConverter = new SimpleTypeConverter();
		Object i = typeConverter.convertIfNecessary("", Integer.class);
		Assert.assertTrue(i == null);
	}
	
	@Test
	public void testConvertStringToInt_abnormal() {
		TypeConverter typeConverter = new SimpleTypeConverter();
		try {
			typeConverter.convertIfNecessary("abc", Integer.class);
		} catch (TypeMismatchException e) {
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void testConvertStringToBoolean_normal() throws TypeMismatchException {
		TypeConverter typeConverter = new SimpleTypeConverter();
		Boolean b1 = typeConverter.convertIfNecessary("true", Boolean.class);
		Assert.assertEquals(true, b1.booleanValue());
		Boolean b2 = typeConverter.convertIfNecessary("false", Boolean.class);
		Assert.assertEquals(false, b2.booleanValue());
		Boolean b3 = typeConverter.convertIfNecessary("yes", Boolean.class);
		Assert.assertEquals(true, b3.booleanValue());
		Boolean b4 = typeConverter.convertIfNecessary("no", Boolean.class);
		Assert.assertEquals(false, b4.booleanValue());
		Boolean b5 = typeConverter.convertIfNecessary("1", Boolean.class);
		Assert.assertEquals(true, b5.booleanValue());
		Boolean b6 = typeConverter.convertIfNecessary("0", Boolean.class);
		Assert.assertEquals(false, b6.booleanValue());
		Boolean b7 = typeConverter.convertIfNecessary("on", Boolean.class);
		Assert.assertEquals(true, b7.booleanValue());
		Boolean b8 = typeConverter.convertIfNecessary("off", Boolean.class);
		Assert.assertEquals(false, b8.booleanValue());
	}
	
	@Test
	public void testConvertStringToBoolean_border() throws TypeMismatchException {
		TypeConverter typeConverter = new SimpleTypeConverter();
		Object b1 = typeConverter.convertIfNecessary("", Boolean.class);
		Assert.assertTrue(b1 == null);
	}
	
	@Test
	public void testConvertStringToBoolean_abnormal() {
		TypeConverter typeConverter = new SimpleTypeConverter();
		try {
			typeConverter.convertIfNecessary("abv", Boolean.class);
		} catch (TypeMismatchException e) {
			return;
		}
		Assert.fail();
	}
}
