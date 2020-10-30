package cn.zhanghui.myspring.beanfactory_construt.test.service;

import lombok.Getter;
import cn.zhanghui.myspring.beanfactory_construt.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_construt.test.dao.EatDao;

@Getter
public class PersonService {
	private EatDao eatDao;
	private DrinkDao drinkDao;
	private String name;
	// 除实例类型或者String类型外其他类型都需要转换
	private int age;
	
	public PersonService() {
		
	}
	
	public PersonService(EatDao eatDao, DrinkDao drinkDao, String name, int age) {
		super();
		this.eatDao = eatDao;
		this.drinkDao = drinkDao;
		this.name = name;
		this.age = age;
	}


	
} 
