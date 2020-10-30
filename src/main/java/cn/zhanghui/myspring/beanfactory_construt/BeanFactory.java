package cn.zhanghui.myspring.beanfactory_construt;

/**
 * @ClassName: BeanFactory.java
 * @Description: 获取bean的实例接口
 * @author: ZhangHui
 * @date: 2019年10月15日 下午4:01:52
 */
public interface BeanFactory {
	Object getBean(String string);
}
