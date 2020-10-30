package cn.zhanghui.myspring.beanfactory_aop2.test.service;

import lombok.Getter;
import cn.zhanghui.myspring.beanfactory_aop2.stereotype.Autowired;
import cn.zhanghui.myspring.beanfactory_aop2.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_aop2.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_aop2.test.dao.EatDao;
import cn.zhanghui.myspring.util.MessageTracker;

@Component
@Getter
public class PersonService implements Configurable {
	
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
	
	@Override
	public void placeOrder() {
		System.out.println("place order");
		MessageTracker.addMessage("place order");
	}
	
	public void placeOrderWithException() {
		throw new NullPointerException();
	}
} 
