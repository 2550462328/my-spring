package cn.zhanghui.myspring.beanfactory_annotation.core.io;

import java.io.IOException;
import java.io.InputStream;

import cn.zhanghui.myspring.util.Assert;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * @ClassName: ClassPathResource.java
 * @Description: 类环境加载的资源
 * @author: ZhangHui
 * @date: 2019年10月24日 下午10:28:32
 */
public class ClassPathResource implements Resource {
	
	private final String configFile;
	private ClassLoader classLoader;
	
	public ClassPathResource(String configFile) {
		this(configFile, (ClassLoader)null);
	}
	public ClassPathResource(String configFile, ClassLoader classLoader) {
		Assert.notNull(configFile, "configFile must not be null!");
		this.configFile = configFile;
		this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return classLoader.getResourceAsStream(configFile);
	}

	@Override
	public String getDescription() {
		return "configFile []";
	}

}
