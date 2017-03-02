package morality.business.login.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import morality.util.interceptor.ManageInterceptor;

/**
* @desc 查询统计
* @author yangbo
*/
@Before(ManageInterceptor.class)
public class StatisticController  extends Controller{
	
	/***********************楼区数据统计************************/
	public void buildinglist(){
		render("building_list.html");
	}
	
	/***********************企业数据总览************************/
	public void companylist(){
		render("company_list.html");
	}
	
	/***********************园区缴费情况总表************************/
	public void parkpaylist(){
		render("parkpay_list.html");
	}
}
