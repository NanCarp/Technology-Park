package morality.business.login.controller;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import morality.business.login.service.StatisticService;
import morality.util.interceptor.ManageInterceptor;

/**
* @desc 查询统计
* @author yangbo
*/
@Before(ManageInterceptor.class)
public class StatisticController  extends Controller{
	
	/***********************楼区数据统计************************/
	// 楼区列表
	public void buildinglist(){
		Integer pageno = getParaToInt("pageno")==null?1:getParaToInt("pageno");
		Integer fstate = getParaToInt("fstate")==null?9:getParaToInt("fstate");
		Integer astate = getParaToInt("astate")==null?9:getParaToInt("astate");
		String floorno = getPara("floorno");
		String areano = getPara("areano");
		Page<Record> page = StatisticService.getBuildInfoList(pageno, 16, fstate, astate, floorno, areano);
		setAttr("fstate", fstate);
		setAttr("astate", astate);
		setAttr("floorno", floorno);
		setAttr("areano", areano);
		
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("buildlist", page.getList());
		render("buildingInfo_list.html");
	}
	
	// 楼区信息导出
	public void exportbuilding(){
		int fstate = getParaToInt(0);
		int astate = getParaToInt(1);
		String floorno = getPara(2);
		String areano = getPara(3);
		boolean result = StatisticService.getBuildInfoForExcel(getResponse(), fstate, astate, floorno, areano);
		if(result){
			renderNull();
		}else{
			renderText("导出失败");
		}
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
