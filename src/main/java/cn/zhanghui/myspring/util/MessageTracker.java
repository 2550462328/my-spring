package cn.zhanghui.myspring.util;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @ClassName: MessageTracker.java
 * @Description: aop测试的工具类
 * @author: ZhangHui
 * @date: 2019年12月12日 上午9:46:24
 */
public class MessageTracker {
	
	private static List<String> MESSAGES = new ArrayList<>();
	
	public static void addMessage(String message) {
		MESSAGES.add(message);
	}
	
	public static void clearMessages() {
		MESSAGES.clear();
	}
	
	public static List<String> getMsgs(){
		return MESSAGES;
	}
	
	public static boolean hasMessage(String message) {
		return MESSAGES.contains(message);
	}
	
}
