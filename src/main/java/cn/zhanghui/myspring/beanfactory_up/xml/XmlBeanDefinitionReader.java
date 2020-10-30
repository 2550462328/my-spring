package cn.zhanghui.myspring.beanfactory_up.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zhanghui.myspring.beanfactory_up.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_up.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_up.exception.BeanStoreException;
import cn.zhanghui.myspring.beanfactory_up.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_up.support.GenericBeanDefinition;

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

	BeanDefinitionRegistry registry;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	public void loadBeanDefinition(Resource resource) { // 修改一
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
				if(ele.attribute(SCOPE_ATTRIBUTE) != null) {  // 判断bean的scope  // 修改二
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
				}
				this.registry.registryBeanDefinition(id, bd);   // 这里用BeanDefinitionRegistry 替代了container
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
}
