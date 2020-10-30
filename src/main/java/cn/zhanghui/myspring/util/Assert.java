package cn.zhanghui.myspring.util;

/**
 * @ClassName: Assert.java
 * @Description: 测试工具
 * @author: ZhangHui
 * @date: 2019年10月25日 下午1:51:00
 */
public class Assert {
	
	// 断言object不为空
	public static void notNull(Object object, String message) {
		if(object == null) {
			throw new IllegalArgumentException(message);
		}
	}
}
