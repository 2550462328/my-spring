package cn.zhanghui.myspring.beanfactory_annotation2.config;

/**
 * @ClassName: SingletonBeanRegistry.java
 * @Description: 使一个实例变成单例
 * @author: ZhangHui
 * @date: 2019年10月25日 下午5:04:28
 */
public interface SingletonBeanRegistry {
	void registrySingleton(String beanName, Object singletonObject);
	
	Object getSingleton(String beanName);
}
