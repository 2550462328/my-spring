package cn.zhanghui.myspring.beanfactory_set.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zhanghui.myspring.beanfactory_set.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_set.beans.PropertyValue;
import cn.zhanghui.myspring.beanfactory_set.config.RuntimeBeanReference;
import cn.zhanghui.myspring.beanfactory_set.config.TypedStringValue;
import cn.zhanghui.myspring.beanfactory_set.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_set.exception.BeanStoreException;
import cn.zhanghui.myspring.beanfactory_set.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_set.support.GenericBeanDefinition;
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

	BeanDefinitionRegistry registry;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	/**
	 * 从Resource中向BeanDefinitionRegistry中装载beanDefinition
	 * 
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
				// 解析bean的property放到propertyValue中
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
	 * 
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
	 * 
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
}
