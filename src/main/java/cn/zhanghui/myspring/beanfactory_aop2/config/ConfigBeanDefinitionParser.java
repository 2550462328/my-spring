package cn.zhanghui.myspring.beanfactory_aop2.config;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import cn.zhanghui.myspring.beanfactory_aop2.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop2.aop.AspectJExpressionPointcut;
import cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj.AspectJAfterAdvice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj.AspectJAfterThrowingAdvice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.aspectj.AspectJBeforeAdvice;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.AspectInstanceFactory;
import cn.zhanghui.myspring.beanfactory_aop2.aop.config.MethodLocatingFactory;
import cn.zhanghui.myspring.beanfactory_aop2.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_aop2.beans.ConstructorArgument.ValueHolder;
import cn.zhanghui.myspring.beanfactory_aop2.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_aop2.support.BeanDefinitionReaderUtils;
import cn.zhanghui.myspring.beanfactory_aop2.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_aop2.support.GenericBeanDefinition;
import cn.zhanghui.myspring.util.StringUtils;

/**
 * 
 * @ClassName: ConfigBeanDefinitionParser.java
 * @Description: 解析aop配置文件
 * @author: ZhangHui
 * @date: 2019年12月18日 上午11:25:34
 */
public class ConfigBeanDefinitionParser {

	private static final String ASPECT = "aspect";
	private static final String ID = "id";
	private static final String REF = "ref";
	private static final String AFTER = "after";
	private static final String AFTER_RETURNING_ELEMENT = "after-returning";
	private static final String AFTER_THROWING_ELEMENT = "after-throwing";
	private static final String AROUND = "around";
	private static final String BEFORE = "before";
	private static final String POINTCUT = "pointcut";
	private static final String ASPECT_NAME_PROPERTY = "aspectName";
	private static final String POINTCUT_REF = "pointcut-ref";
	private static final String EXPRESSION = "expression";

	public BeanDefinition parse(Element ele, BeanDefinitionRegistry registry) {
		List<Element> childElts = ele.elements();
		for (Element childElem : childElts) {
			String localName = childElem.getName();
			if (ASPECT.equals(localName)) {
				parseAspect(childElem, registry);
			}
		}
		return null;
	}
	
	//解析<aspect标签>
	private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry) {
//		String aspectId = aspectElement.attributeValue(ID);
		String aspectName = aspectElement.attributeValue(REF);

		List<BeanDefinition> beanDefinitions = new ArrayList<>();
		List<RuntimeBeanReference> beanReferences = new ArrayList<>();

		List<Element> eleList = aspectElement.elements();

		// 是否已经添加new RuntimeBeanReference(aspectName)
		// 只有在有AdviceNode的时候这个beanReferences添加了才有意义
		boolean adviceFoundAlready = false;

		for (int i = 0; i < eleList.size(); i++) {
			Element ele = eleList.get(i);
			if (isAdviceNode(ele)) {
				if (!adviceFoundAlready) {
					adviceFoundAlready = true;
					if (!StringUtils.hasText(aspectName)) {
						return;
					}
					beanReferences.add(new RuntimeBeanReference(aspectName));
				}
				GenericBeanDefinition advisorDefinition = parseAdvice(aspectName, i, aspectElement, ele, registry,
						beanDefinitions, beanReferences);
				beanDefinitions.add(advisorDefinition);
			}
		}

		List<Element> pointcuts = aspectElement.elements(POINTCUT);
		for (Element pointcutElement : pointcuts) {
			parsePointcut(pointcutElement, registry);
		}
	}
	
	//解析<aop:before>等advice类型的标签
	private GenericBeanDefinition parseAdvice(String aspectName, int order, Element aspectElement,
			Element adviceElement, BeanDefinitionRegistry registry, List<BeanDefinition> beanDefinitions,
			List<RuntimeBeanReference> beanReferences) {

		GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
		methodDefinition.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
		methodDefinition.getPropertyValues()
				.add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
		methodDefinition.setSynthetic(true);

		GenericBeanDefinition aspectFactoryDef = new GenericBeanDefinition(AspectInstanceFactory.class);
		aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
		aspectFactoryDef.setSynthetic(true);

		GenericBeanDefinition adviceDef = createAdviceDefinition(adviceElement, registry, aspectName, order,
				methodDefinition, aspectFactoryDef, beanDefinitions, beanReferences);
		adviceDef.setSynthetic(true);

		BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);

		return null;
	}
	
	//解析<aop:point>标签
	private GenericBeanDefinition parsePointcut(Element pointcutElement, BeanDefinitionRegistry registry) {
		String id = pointcutElement.attributeValue(ID);
		String expression = pointcutElement.attributeValue(EXPRESSION);
		GenericBeanDefinition pointcutDefinition = null;
		pointcutDefinition = createPointcutDefinition(expression);
		String pointcutBeanName = id;
		if (StringUtils.hasText(pointcutBeanName)) {
			registry.registryBeanDefinition(pointcutBeanName, pointcutDefinition);
		} else {
			BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);
		}
		return pointcutDefinition;
	}
	
	//ele是不是<aop:before>等类型advice的标签
	private boolean isAdviceNode(Element ele) {
		if (!(ele instanceof Element)) {
			return false;
		} else {
			String name = ele.getName();
			return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name)
					|| AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));
		}
	}

	//创建Advice对应的BeanDefinition
	private GenericBeanDefinition createAdviceDefinition(Element adviceElement, BeanDefinitionRegistry registry,
			String aspectName, int order, GenericBeanDefinition methodDefinition,
			GenericBeanDefinition aspectFactoryDef, List<BeanDefinition> beanDefinitions,
			List<RuntimeBeanReference> beanReferences) {
		GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(getAdviceClass(adviceElement));
		adviceDefinition.getPropertyValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));

		// 构建adviceDefinition的构造函数
		ConstructorArgument cav = adviceDefinition.getConstructorArgument();
		cav.addArgumentValue(new ValueHolder(methodDefinition));

		Object pointcut = parsePointProperty(adviceElement);
		if (pointcut instanceof BeanDefinition) {
			cav.addArgumentValue(new ValueHolder(pointcut));
		} else if (pointcut instanceof String) {
			RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
			beanReferences.add(pointcutRef);
			cav.addArgumentValue(new ValueHolder(pointcutRef));
		}
		cav.addArgumentValue(new ValueHolder(aspectFactoryDef));

		return adviceDefinition;
	}
	
	//解析<aop:pointcut>标签的属性
	private Object parsePointProperty(Element adviceElement) {
		if (adviceElement.attribute(POINTCUT) != null && adviceElement.attribute(POINTCUT_REF) != null) {
			return null;
		} else if (adviceElement.attribute(POINTCUT) != null) {
			String expression = adviceElement.attributeValue(POINTCUT);

			GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
			return pointcutDefinition;
		}else if (adviceElement.attribute(POINTCUT_REF) != null) {
			String pointcutRef = adviceElement.attributeValue(POINTCUT_REF);
			if (!StringUtils.hasText(pointcutRef)) {
				return null;
			}
			return pointcutRef;
		} else {
			return null;
		}
	}
    
	//创建pointcut的BeanDefinition
	private GenericBeanDefinition createPointcutDefinition(String expression) {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
		beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		beanDefinition.setSynthetic(true);
		beanDefinition.getPropertyValues().add(new PropertyValue(EXPRESSION, expression));
		return beanDefinition;
	}
	
	//判断adviceElement标签的advice类型
	private Class<?> getAdviceClass(Element adviceElement) {
		String elementName = adviceElement.getName();
		if (BEFORE.equals(elementName)) {
			return AspectJBeforeAdvice.class;
		} else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
			return AspectJAfterAdvice.class;
		} else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
			return AspectJAfterThrowingAdvice.class;
		} else {
			return null;
		}
	}
}
