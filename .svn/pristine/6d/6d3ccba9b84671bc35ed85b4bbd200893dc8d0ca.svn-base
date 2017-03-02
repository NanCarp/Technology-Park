package morality.business.login.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import morality.business.login.service.ParkManageService;
import morality.business.login.service.SystomService;
import morality.util.interceptor.ManageInterceptor;

@Before(ManageInterceptor.class)
public class ParkManageController extends Controller {
	//员工管理
	public void index(){
		Integer pageno = getParaToInt(0)==null?1:getParaToInt(0);
		Page<Record> page = ParkManageService.getEmpList(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("emplist", page.getList());
		render("employee_list.html");
	}
	//楼宇管理
	public void building(){
		
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getBuilding(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("buildinglist", page.getList());
		render("building_list.html");
	}
	//区域管理
	public void areamanage(){
		
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getArea(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("arealist", page.getList());
		render("area_manage.html");
	}
	//缴费管理
	public void paymanage(){
		
		render("pay_manage.html");
	}
	//安全管理检查记录
	public void safetyagreement(){
		
		render("safety_record.html");
	}
	//园区安全责任书签订
	public void safetyrecord(){
		
		render("safety_agreement.html");
	}
	//通知公告
	public void notice(){
		
		render("notice.html");
	}
	
	//
	public void getemp(){
		Integer empno = getParaToInt();
		if(empno!=null){
//			setAttr("emp", ParkManageService.getEmp(empno));	
		}
		List<Record> departs = SystomService.getDepartMents();
		List<Record> positions = ParkManageService.getPosition();
		setAttr("departs", departs);
		setAttr("positions", positions);	
		render("emp_detail.html");
	}
}
