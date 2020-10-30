package cn.zhanghui.myspring.beanfactory_annotation.test.service;

import java.io.Serializable;

import lombok.Getter;
import cn.zhanghui.myspring.beanfactory_annotation.stereotype.Autowired;
import cn.zhanghui.myspring.beanfactory_annotation.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_annotation.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_annotation.test.dao.EatDao;

@Component(value = "personService")
@Getter
public class PersonService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private EatDao eatDao;
	
	@Autowired
	private DrinkDao drinkDao;
	
	public PersonService() {
		
	}
	
} 
