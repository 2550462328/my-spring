package cn.zhanghui.myspring.beanfactory_annotation.support;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.zhanghui.myspring.beanfactory_annotation.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_annotation.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_annotation.beans.PropertyValue;

/**
 * @ClassName: GenericBeanDefinition.java
 * @Description: bean的定义信息
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:58:57
 */
@Data
@EqualsAndHashCode
public class GenericBeanDefinition implements BeanDefinition {
	private String id;
	private String className;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	
	public List<PropertyValue> propertyValues = new ArrayList<>();
	
	public ConstructorArgument constructorArgument = new ConstructorArgument();

	public GenericBeanDefinition(){

	}

	public GenericBeanDefinition(String id, String className) {
		super();
		this.id = id;
		this.className = className;
	}
	
	@Override
	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}
	
	@Override
	public boolean hasConstructorArgumentValues() {
		return !this.constructorArgument.isEmpty();
	}
	
	
}
