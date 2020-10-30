package cn.zhanghui.myspring.beanfactory_aop.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import cn.zhanghui.myspring.beanfactory_aop.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop.beans.ConstructorArgument;
import cn.zhanghui.myspring.beanfactory_aop.beans.ConstructorArgument.ValueHolder;
import cn.zhanghui.myspring.beanfactory_aop.beans.SimpleTypeConverter;
import cn.zhanghui.myspring.beanfactory_aop.config.ConfigurableBeanFactory;
import cn.zhanghui.myspring.beanfactory_aop.exception.BeanCreateException;
import cn.zhanghui.myspring.beanfactory_aop.exception.TypeMismatchException;
import cn.zhanghui.myspring.util.ClassUtils;

/**
 * @ClassName: ConstructorResolver.java
 * @Description:
 * @author: ZhangHui
 * @date: 2019年11月22日 下午4:52:51
 */
public class ConstructorResolver {

	protected final Logger log = LoggerFactory.getLogger(ConstructorResolver.class);

	private final ConfigurableBeanFactory beanFactory;

	public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
		super();
		this.beanFactory = beanFactory;
	}

	/**
	 * 根据xml中配置的construct-arg列表，找到bd对应类中最合适的构造函数进行关联
	 * @param bd
	 * @return
	 */
	public Object autowiredConstructor(BeanDefinition bd) {
		// 匹配成功后对应的构造函数
		Constructor<?> constructorToUse = null;
		// 用来存放将ConstructorArgument中的argumentValues进行BeanDefinitionValueResolver.resolveValueIfNecessary转换后的值
		Object[] argsToUse = null;
		Class<?> beanClass = null;
		try {
			beanClass = this.beanFactory.getClassLoader().loadClass(bd.getClassName());
			Constructor<?>[] candidates = beanClass.getDeclaredConstructors();

			BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

			ConstructorArgument args = bd.getConstructorArgument();
			SimpleTypeConverter typeConverter = new SimpleTypeConverter();

			// 获取构造函数的参数名称
			LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

			for (int i = 0; i < candidates.length; i++) {

				Parameter param[] = candidates[i].getParameters();

				String[] paramsName = parameterNameDiscoverer.getParameterNames(candidates[i]);

				if (param.length != args.getArgumentCount()) {
					continue;
				}
				argsToUse = new Object[param.length];
				
				// 进行类型匹配
				boolean result = this.valueMatchTypes(param, paramsName, args.getArgumentValues(), argsToUse,
						valueResolver, typeConverter);

				if (result) {
					constructorToUse = candidates[i];
					break;
				}
			}

			if (constructorToUse == null) {
				throw new BeanCreateException(bd.getId() + "can`t find  a apporiate constructor");
			}

			try {
				return constructorToUse.newInstance(argsToUse);
			} catch (Exception e) {
				throw new BeanCreateException(bd.getId() + "can`t find  a create instance using" + argsToUse);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 将parameterTypes和argumentValues做类型匹配
	 * 
	 * @param parameterTypes
	 * @param argumentValues
	 * @param argsToUse
	 * @param valueResolver
	 * @param typeConverter
	 * @return
	 * @throws ClassNotFoundException
	 */
	private boolean valueMatchTypes(Parameter[] params, String[] paramsName, List<ValueHolder> argumentValues,
			Object[] argsToUse, BeanDefinitionValueResolver valueResolver, SimpleTypeConverter typeConverter)
			throws ClassNotFoundException {
		//根据index排序
		argumentValues = countSort(argumentValues);
		for (int i = 0; i < params.length; i++) {
			ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
			//判断名称name
			if (StringUtils.isNotBlank(valueHolder.getName())) {
				if (!StringUtils.equals(paramsName[i], valueHolder.getName())) {
					return false;
				}
			}
			//判断类型type
			if (StringUtils.isNotBlank(valueHolder.getType())) {
				Class valueClazz = Class.forName(valueHolder.getType());
				if (!ClassUtils.isAssignable(valueClazz, params[i].getType())) {
					return false;
				}
			}
			
			// 默认进行类型匹配
			try {
				Object resolveValue = valueResolver.resolveValueIfNecessary(valueHolder.getValue());

				// 核心在这里，如果不能将argumentValues中的值转换成parameterTypes中对应的类，则转型失败，类型不匹配
				Object convertedValue = typeConverter.convertIfNecessary(resolveValue, params[i].getType());
				// 转型成功，记录下来
				argsToUse[i] = convertedValue;
			} catch (TypeMismatchException e) {
				log.error(e.getMessage());
				return false;
			} catch (RuntimeException e) {
				log.error(e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将ValueHolder列表根据index排序，不打乱默认index=0的先后顺序
	 * @param argumentValues
	 * @return
	 */
	private List<ValueHolder> countSort(List<ValueHolder> argumentValues) {
		List<ValueHolder> result = argumentValues.stream().sorted((t1, t2) -> {
			return t1.getIndex() >= t2.getIndex() ? 1 : -1;
		}).collect(Collectors.toList());
		return result;
	}

}
