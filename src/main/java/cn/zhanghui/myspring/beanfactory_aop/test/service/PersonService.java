package cn.zhanghui.myspring.beanfactory_aop.test.service;

import java.io.Serializable;

import lombok.Getter;
import cn.zhanghui.myspring.beanfactory_aop.stereotype.Autowired;
import cn.zhanghui.myspring.beanfactory_aop.stereotype.Component;
import cn.zhanghui.myspring.beanfactory_aop.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_aop.test.dao.EatDao;
import cn.zhanghui.myspring.util.MessageTracker;

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
	
	public void placeOrder() {
		System.out.println("place order");
		MessageTracker.addMessage("place order");
	}
	
	public void placeOrderWithException() {
		throw new NullPointerException();
	}
} 
