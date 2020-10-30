package cn.zhanghui.myspring.beanfactory_aop.core.io.support;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import cn.zhanghui.myspring.beanfactory_aop.core.io.FileSystemResource;
import cn.zhanghui.myspring.beanfactory_aop.core.io.Resource;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * 
 * @ClassName: PackageResourceLoader.java
 * @Description: 从指定的包里获得文件，然后将文件转换成FileSystemResource
 * @author: ZhangHui
 * @date: 2019年12月3日 下午2:09:16
 */
public class PackageResourceLoader {

	private Logger log = LoggerFactory.getLogger(PackageResourceLoader.class);

	private ClassLoader classLoader;
	
	
	public PackageResourceLoader() {
	}

	public PackageResourceLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		if(classLoader == null) {
			classLoader = ClassUtils.getDefaultClassLoader();
		}
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public Resource[] getResources(String basepackage) {
		Assert.notNull(basepackage, "basepackage must not be null");
		String location = ClassUtils.convertClassNameToResourcePath(basepackage);
		ClassLoader cl = getClassLoader();
		
		URL url = cl.getResource(location);
		if(url == null) {
			throw new IllegalStateException("the resource  [" + location +"] is not exists!");
		}
		
		File rootDir = new File(url.getFile());
		
		Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
		Resource[] result = new Resource[matchingFiles.size()];

		int i = 0;
		for (File file : matchingFiles) {
			result[i++] = new FileSystemResource(file);
		}
		return result;
	}

	/**
	 * 获取rootDir子目录中的文件
	 * 
	 * @param rootDir
	 * @return
	 */
	private Set<File> retrieveMatchingFiles(File rootDir) {
		if (!rootDir.exists()) {
			if (log.isDebugEnabled()) {
				log.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it is not found!");
			}
			return Collections.emptySet();
		}
		if (!rootDir.isDirectory()) {
			if (log.isWarnEnabled()) {
				log.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory!");
			}
			return Collections.emptySet();
		}
		if (!rootDir.canRead()) {
			if (log.isWarnEnabled()) {
				log.debug("Skipping [" + rootDir.getAbsolutePath()
						+ "] because the application is not allowed to read the directory!");
			}
			return Collections.emptySet();
		}

		Set<File> result = new LinkedHashSet<>();
		doRetrieveMatchingFiles(rootDir, result);
		return result;
	}
	
	/**
	 * 递归获取rootDir中的file文件
	 * @param rootDir
	 * @param result
	 */
	private void doRetrieveMatchingFiles(File rootDir, Set<File> result) {
		File[] fileList = rootDir.listFiles();
		if (fileList == null) {
			if (log.isWarnEnabled()) {
				log.debug("Could not retrieve contents of direcotry [" + rootDir.getAbsolutePath() + "]");
			}
			return;
		}

		for (File content : fileList) {
			if (content.isDirectory()) {
				if (!content.canRead()) {
					if (log.isDebugEnabled()) {
						log.debug("Skipping subdirectory [" + content.getAbsolutePath()
								+ "] because the application is not allowed to read the directory!");
					}
				} else {
					doRetrieveMatchingFiles(content, result);
				}
			} else {
				result.add(content);
			}
		}
	}

}
