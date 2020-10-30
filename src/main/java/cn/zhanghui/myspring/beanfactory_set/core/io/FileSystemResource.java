package cn.zhanghui.myspring.beanfactory_set.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.zhanghui.myspring.util.Assert;

/**
 * @ClassName: FileSystemResource.java
 * @Description: 根据file path 获取资源
 * @author: ZhangHui
 * @date: 2019年10月25日 下午1:57:25
 */
public class FileSystemResource implements Resource {
	
	private final String path;
	private final File file;
	
	public FileSystemResource(String path) {
		Assert.notNull(path, "Path must not be null");
		this.path = path;
		this.file = new File(path);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	@Override
	public String getDescription() {
		return "file [" + this.file.getAbsolutePath() + "] ";
	}

}
