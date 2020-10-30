

package cn.zhanghui.myspring.beanfactory_aop2.support;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.exception.BeanStoreException;
import cn.zhanghui.myspring.util.StringUtils;

public abstract class BeanDefinitionReaderUtils {

	public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";

	public static String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry)
			throws BeanStoreException {

		return generateBeanName(beanDefinition, registry, false);
	}

	public static String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry,
			boolean isInnerBean) throws BeanStoreException {

		String generatedBeanName = definition.getClassName();
		if (!StringUtils.hasText(generatedBeanName)) {
			throw new BeanStoreException("Unnamed bean definition specifies neither "
					+ "'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
		}
		return uniqueBeanName(generatedBeanName, registry);
	}

	public static String uniqueBeanName(String beanName, BeanDefinitionRegistry registry) {
		String id = beanName;
		int counter = -1;

		// Increase counter until the id is unique.
		while (counter == -1 || registry.containsBeanDefinition(id)) {
			counter++;
			id = beanName + GENERATED_BEAN_NAME_SEPARATOR + counter;
		}
		return id;
	}

	public static String registerWithGeneratedName(BeanDefinition definition, BeanDefinitionRegistry registry)
			throws BeanStoreException {

		String generatedName = generateBeanName(definition, registry, false);
		registry.registryBeanDefinition(generatedName, definition);
		return generatedName;
	}

}
