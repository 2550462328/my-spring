package cn.zhanghui.myspring.beanfactory_up.support;

import lombok.Data;
import cn.zhanghui.myspring.beanfactory_up.BeanDefinition;

/**
 * @ClassName: GenericBeanDefinition.java
 * @Description: bean的定义信息
 * @author: ZhangHui
 * @date: 2019年10月24日 下午4:58:57
 */
@Data
public class GenericBeanDefinition implements BeanDefinition {
	private String id;
	private String className;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	
	public GenericBeanDefinition(String id, String className) {
		super();
		this.id = id;
		this.className = className;
	}
	@Override
	public boolean isSingleton() {
		return this.singleton;
	}
	@Override
	public boolean isPrototype() {
		return this.prototype;
	}
	@Override
	public String getScope() {
		return this.scope;
	}
	@Override
	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}
}
