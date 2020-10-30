package cn.zhanghui.myspring.beanfactory_annotation2.test.service;

import java.io.Serializable;

import lombok.Getter;
import cn.zhanghui.myspring.beanfactory_annotation2.stereotype.Autowired;
import cn.zhanghui.myspring.beanfactory_annotation2.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_annotation2.test.dao.EatDao;

@Component
@Getter
public class PersonService implements Serializable {
	
	private static final long serialVersionUID = 1L;

//	@Autowired
	private EatDao eatDao;
	
//	@Autowired
	private DrinkDao drinkDao;
	
	public PersonService() {
		
	}
	
	@Autowired
	public void setEatDao(EatDao eatDao) {
		this.eatDao = eatDao;
	}
	
	@Autowired(required=true)
	public void setDrinkDao(DrinkDao drinkDao) {
		this.drinkDao = drinkDao;
	}
	
} 
