package cn.zhanghui.myspring.beanfactory_annotation;

import java.util.List;

import cn.zhanghui.myspring.beanfactory_annotation.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_annotation.beans.PropertyValue;


/**
 * @ClassName: BeanDefinition.java
 * @Description: bean的定义信息接口
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:59:21
 */
public interface BeanDefinition {

	static final String SCOPE_DEFAULT = ""; // scope默认值
	static final String SCOPE_SINGLETON = "singleton";
	static final String SCOPE_PROTOTYPE = "prototype";

	String getClassName();

	String getId();

	boolean isSingleton();

	boolean isPrototype();

	String getScope();

	void setScope(String scope);

	List<PropertyValue> getPropertyValues();
	
	ConstructorArgument getConstructorArgument();

	boolean hasConstructorArgumentValues();
}
