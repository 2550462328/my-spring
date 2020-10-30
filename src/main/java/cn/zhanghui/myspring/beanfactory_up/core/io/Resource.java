package cn.zhanghui.myspring.beanfactory_up.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: Resource.java
 * @Description: 资源类的接口（包括类环境、文件系统）
 * @author: ZhangHui
 * @date: 2019年10月24日 下午10:26:06
 */
public interface Resource {
	public InputStream getInputStream() throws IOException;
	
	public String getDescription();
}
