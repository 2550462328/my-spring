package cn.zhanghui.myspring.beanfactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Getter
@Setter
public class BeanDefinition1 {
	private String className;
	private Object bean;
}
