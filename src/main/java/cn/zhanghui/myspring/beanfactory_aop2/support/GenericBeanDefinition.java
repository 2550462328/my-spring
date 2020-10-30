package cn.zhanghui.myspring.beanfactory_aop2.support;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_aop2.beans.PropertyValue;

/**
 * @ClassName: GenericBeanDefinition.java
 * @Description: bean的定义信息
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:58:57
 */
@Getter
@Setter
@NoArgsConstructor
public class GenericBeanDefinition implements BeanDefinition {
	private String id;
	private String className;
	private boolean singleton = true;
	private boolean prototype = false;
	private boolean synthetic;
	private String scope = SCOPE_DEFAULT;
	private Class<?> beanClass;

	public List<PropertyValue> propertyValues = new ArrayList<>();

	public ConstructorArgument constructorArgument = new ConstructorArgument();

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

	@Override
	public Class<?> getBeanClass() {
		if (this.beanClass == null) {
			throw new IllegalStateException("Bean class name :[" + this.className
					+ "] has not been resolved into Class, resolve the bean class firstly!");
		}
		return this.beanClass;
	}

	@Override
	public boolean hasBeanClass() {
		return this.beanClass != null;
	}

	@Override
	public Class<?> resolveBeanClass(ClassLoader classLoader) {
		try {
			Class<?> resolveClass = classLoader.loadClass(className);
			this.beanClass = resolveClass;
			return resolveClass;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((constructorArgument == null) ? 0 : constructorArgument.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((propertyValues == null) ? 0 : propertyValues.hashCode());
		result = prime * result + (prototype ? 1231 : 1237);
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + (singleton ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericBeanDefinition other = (GenericBeanDefinition) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (constructorArgument == null) {
			if (other.constructorArgument != null)
				return false;
		} else if (!constructorArgument.equals(other.constructorArgument))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (propertyValues == null) {
			if (other.propertyValues != null)
				return false;
		} else if (!propertyValues.equals(other.propertyValues))
			return false;
		if (prototype != other.prototype)
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (singleton != other.singleton)
			return false;
		return true;
	}

	public GenericBeanDefinition(Class<?> beanClass, List<PropertyValue> propertyValues,
			ConstructorArgument constructorArgument) {
		super();
		this.beanClass = beanClass;
		this.className = beanClass.getName();
		this.propertyValues = propertyValues;
		this.constructorArgument = constructorArgument;
	}

	public GenericBeanDefinition(Class<?> beanClass) {
		super();
		this.beanClass = beanClass;
		this.className = beanClass.getName();
	}
}
