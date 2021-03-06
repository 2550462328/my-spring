package cn.zhanghui.myspring.beanfactory_construt.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zhanghui.myspring.beanfactory_construt.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_construt.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_construt.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_construt.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_construt.config.TypedStringValue;
import cn.zhanghui.myspring.beanfactory_construt.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_construt.exception.BeanStoreException;
import cn.zhanghui.myspring.beanfactory_construt.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_construt.support.GenericBeanDefinition;
import cn.zhanghui.myspring.util.StringUtils;

/**
 * @ClassName: XmlBeanDefinitionReader.java
 * @Description: 真正与xml接触 并获取BeanDefinition的接口
 * @author: ZhangHui
 * @date: 2019年10月24日 下午8:54:25
 */
public class XmlBeanDefinitionReader {
	public static final String ID_ATTRIBUTE = "id";

	public static final String CLASS_ATTRIBUTE = "class";

	public static final String SCOPE_ATTRIBUTE = "scope";

	public static final String PROPERTY_ELEMENT = "property";

	public static final String REF_ATTRIBUTE = "ref";

	public static final String NAME_ATTRIBUTE = "name";

	public static final String VALUE_ATTRIBUTE = "value";
	
	public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
	
	public static final String TYPE_ATTRIBUTE = "type";
	
	public static final String INDEX_ATTRIBUTE = "index";
	
	BeanDefinitionRegistry registry;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 从Resource中向BeanDefinitionRegistry中装载beanDefinition
	 * @param resource
	 */
	public void loadBeanDefinition(Resource resource) {
		InputStream is = null;
		try {
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);

			Element root = doc.getRootElement();
			Iterator<Element> itr = root.elements().iterator();
			while (itr.hasNext()) {
				Element ele = itr.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
				if (ele.attribute(SCOPE_ATTRIBUTE) != null) { // 判断bean的scope
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
				}
				//解析bean中construct-arg标签，放入到BeanDefinition中的constructorArguments中
				this.parseConstructorArgElements(ele, bd);
				//解析bean的property标签，放入到BeanDefinition中的propertyValue中
				this.parsePropertyElement(ele, bd);
				// 向BeanFactory注册BeanDefinition
				this.registry.registryBeanDefinition(id, bd); // 这里用BeanDefinitionRegistry 替代了container
			}
		} catch (DocumentException | IOException e) {
			throw new BeanStoreException("configFile is valid xml file!");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析ele下的所有property标签，并放入BeanDefinition中PropertyValue的集合中
	 * @param ele
	 * @param bd
	 */
	public void parsePropertyElement(Element ele, BeanDefinition bd) {
		// Iterator itr = ele.elements().iterator();
		Iterator itr = ele.elementIterator(PROPERTY_ELEMENT);
		while (itr.hasNext()) {
			Element propElement = (Element) itr.next();
			String propertyName = propElement.attributeValue(NAME_ATTRIBUTE);
			if (!StringUtils.hasLength(propertyName)) {
				System.out.println("tag name must not be null!");
				return;
			}
			Object value = this.parsePropertyValue(propElement, bd, propertyName);
			bd.getPropertyValues().add(new PropertyValue(propertyName, value));
		}
	}

	/**
	 * 根据property标签的name去解析这个property的value，这个value可以是ref，也可以是value
	 * @param ele
	 * @param bd
	 * @param propertyName
	 * @return
	 */
	public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
		String elementName = (propertyName != null) ? "<property> element for property '" + propertyName + "'"
				: "<construct-arg> element";

		boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
		boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRIBUTE);
			if (!StringUtils.hasText(refName)) {
				System.err.println(elementName + "contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);
			return ref;
		} else if (hasValueAttribute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
			return valueHolder;
		} else {
			throw new RuntimeException(elementName + "must specify a ref or value");
		}
	}
	
	/**
	 * 解析bean中construct-arg标签，放入到BeanDefinition中的constructorArguments中
	 * @param ele
	 * @param bd
	 */
	public void parseConstructorArgElements(Element ele, BeanDefinition bd) {
		Iterator itr = ele.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
		while (itr.hasNext()) {
			Element element = (Element) itr.next();
			parseConstructorArgElement(element, bd);
		}
	}
	
	/**
	 * 具体解析bean中construct-arg标签
	 * @param ele
	 * @param bd
	 */
	public void parseConstructorArgElement(Element ele, BeanDefinition bd) {
		String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
		String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
		String indexAttr = ele.attributeValue(INDEX_ATTRIBUTE);
		
		Object value = parsePropertyValue(ele, bd, null);
		
		ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
		if(StringUtils.hasLength(typeAttr)) {
			valueHolder.setType(typeAttr);
		} 
		if(StringUtils.hasLength(nameAttr)) {
			valueHolder.setName(nameAttr);
		}
		if(StringUtils.hasLength(indexAttr)) {
			valueHolder.setIndex(Integer.valueOf(indexAttr));
		}
		bd.getConstructorArgument().addArgumentValue(valueHolder);
	}
}
