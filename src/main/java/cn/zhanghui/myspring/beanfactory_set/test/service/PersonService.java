package cn.zhanghui.myspring.beanfactory_set.test.service;

import lombok.Data;
import cn.zhanghui.myspring.beanfactory_set.test.dao.DrinkDao;
import cn.zhanghui.myspring.beanfactory_set.test.dao.EatDao;

@Data
public class PersonService {
	private EatDao eatDao;
	private DrinkDao drinkDao;
	private String name;
	// 除实例类型或者String类型外其他类型都需要转换
	private int age;
}
