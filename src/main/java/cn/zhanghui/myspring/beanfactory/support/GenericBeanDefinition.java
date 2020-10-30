package cn.zhanghui.myspring.beanfactory.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import cn.zhanghui.myspring.beanfactory.BeanDefinition;

@AllArgsConstructor
@Data
public class GenericBeanDefinition implements BeanDefinition {
	
	private String id;
	private String className;
	
}
