package cn.zhanghui.myspring.beanfactory_aop2.aop.framework;

/**
 * 
 * @ClassName: AopProxyFactory.java
 * @Description: 生成代理类的接口
 * @author: ZhangHui
 * @date: 2019年12月19日 上午9:40:40
 */
public interface AopProxyFactory {
	Object getProxy();
	
	Object getProxy(ClassLoader classLoader);
}
