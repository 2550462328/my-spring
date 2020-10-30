package cn.zhanghui.myspring.beanfactory;

/**
 * @ClassName: BeanFactory.java
 * @Description: 该类的功能描述
 * @author: ZhangHui
 * @date: 2019年10月15日 下午4:01:52
 */
public interface BeanFactory {

	Object getBean(String string);

	BeanDefinition getBeanDefinition(String string);

}
