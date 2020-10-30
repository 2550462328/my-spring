package cn.zhanghui.myspring.beanfactory.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zhanghui.myspring.beanfactory.BeanDefinition;
import cn.zhanghui.myspring.beanfactory.BeanFactory;
import cn.zhanghui.myspring.beanfactory.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory.exception.BeanStoreException;
import cn.zhanghui.myspring.util.ClassUtils;

public class DefaultBeanFactory implements BeanFactory {

	public static final String ID_ATTRIBUTE = "id";
	
	public static final String CLASS_ATTRIBUTE = "class";
	
	private Map<String, BeanDefinition> container = new HashMap<>();
	
    public DefaultBeanFactory(String configFile) {
    	loadBeanDefinition(configFile);
	}
    
	public DefaultBeanFactory() {
	}

	@Override
	public Object getBean(String beanId) {
		BeanDefinition bd = container.get(beanId);
		if(bd == null){
			throw new BeanCreateException("Bean is not definied!");
		}
		String className = bd.getClassName();
		ClassLoader cl = ClassUtils.getDefaultClassLoader();  // 反射获取实例
		try {
			Class<?> clazz = cl.loadClass(className);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreateException("create bean " + className + "failed!");
		} 
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanId) {
		return container.get(beanId);
	}
	
	private void loadBeanDefinition(String configFile) {
		InputStream is = null;
		try {
			ClassLoader cl = ClassUtils.getDefaultClassLoader();
			is = cl.getResourceAsStream(configFile);
			
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			
			Element root = doc.getRootElement();
			Iterator<Element> itr = root.elements().iterator();
			while(itr.hasNext()) {
				Element ele = itr.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
				container.put(id, bd);
			}
		} catch (DocumentException e) {
			throw new BeanStoreException(configFile + "is valid xml file!");
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// ***********************************************************************************************
	// 自己写的逻辑
//	private Map<String, BeanDefinition1> container = new HashMap<String, BeanDefinition1>();
//	public DefaultBeanFactory(String xmlName) throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//		SAXReader reader = new SAXReader();
//		String path = Thread.currentThread().getContextClassLoader().getResource(xmlName).getPath();
//		File file = new File(path);
//		Document doc = reader.read(file);
//		Element root = doc.getRootElement();
//		List<Element> elist = root.elements();
//		for (Element ele : elist) {
//			String className = ele.attributeValue("class");
//			Class clazz = Class.forName(className);
//			if (clazz != null) {
//				container.put(ele.attributeValue("id"), new BeanDefinition(className,clazz.newInstance()));
//			}
//		}
//	}

//	@Override
//	public Object getBean(String beanName) {
//		return container.containsKey(beanName) ? container.get(beanName).getBean() : null;
//	}
//
//	@Override
//	public BeanDefinition getBeanDefinition(String className) {
//		return container.containsKey(className) ? container.get(className) : null;
//	}

}
