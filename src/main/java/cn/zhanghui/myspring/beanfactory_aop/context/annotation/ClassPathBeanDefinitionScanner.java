package cn.zhanghui.myspring.beanfactory_aop.context.annotation;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zhanghui.myspring.beanfactory_aop.BeanDefinition;
import cn.zhanghui.myspring.beanfactory_aop.core.io.Resource;
import cn.zhanghui.myspring.beanfactory_aop.core.io.support.PackageResourceLoader;
import cn.zhanghui.myspring.beanfactory_aop.core.type.AnnotationMetadata;
import cn.zhanghui.myspring.beanfactory_aop.core.type.classreading.MetadataReader;
import cn.zhanghui.myspring.beanfactory_aop.core.type.classreading.SimpleMetadaReader;
import cn.zhanghui.myspring.beanfactory_aop.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_aop.support.BeanDefinitionRegistry;
import cn.zhanghui.myspring.beanfactory_aop.support.BeanNameGenerator;




public class ClassPathBeanDefinitionScanner {

	private final BeanDefinitionRegistry registry;

	private PackageResourceLoader resourceLoader = new PackageResourceLoader();
	
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}
	
	/**
	 * 扫描指定的数组包，生成BeaDefinition集合，并注册到BeanFactory中
	 * @param packagesToScan
	 * @return
	 * @throws IOException
	 */
	public Set<BeanDefinition> doScan(String packagesToScan) throws IOException {

		String[] basePackages = StringUtils.split(packagesToScan, ",");

		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
		
		for (String basePackage : basePackages) {
			
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for(BeanDefinition candidate :candidates) {
				beanDefinitions.add(candidate);
				registry.registryBeanDefinition(candidate.getId(), candidate);
			}
		}
		return beanDefinitions;
	}

	private Set<BeanDefinition> findCandidateComponents(String basePackage) throws IOException {
		
		Set<BeanDefinition> candidates = new LinkedHashSet<>();
		
		Resource[] resources = resourceLoader.getResources(basePackage);

		MetadataReader reader = null;
		AnnotationMetadata metadata = null;
		ScannedGenericBeanDefinition beanDefinition = null;
		
		String componentName = Component.class.getName();
		
		for (Resource resource : resources) {
			reader = new SimpleMetadaReader(resource);
			metadata = reader.getAnnotationMetadata();

			if (metadata.hasAnnotation(componentName)) {
				beanDefinition = new ScannedGenericBeanDefinition(metadata);
				
				// 根据BeanDefinition生成对应的beanName
				String beanId = beanNameGenerator.generateBeanName(beanDefinition, registry);
				beanDefinition.setId(beanId);
				
				candidates.add(beanDefinition);
			}
		}
		
		return candidates;
	}
}
