package cn.zhanghui.myspring.beanfactory_aop.test.tx;

import cn.zhanghui.myspring.util.MessageTracker;

public class TransactionManager {
	public void start() {
		System.out.println("start transaction");
		MessageTracker.addMessage("start transaction");
	}
	
	public void commit() {
		System.out.println("start commit");
		MessageTracker.addMessage("start commit");
	}
	
	public void rollback() {
		System.out.println("start rollback");
		MessageTracker.addMessage("start rollback");
	}
}
