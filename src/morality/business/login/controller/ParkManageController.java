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
		setAttr("totalrow",page.getTotalRow());
		render("employee_list.html");
	}
	//楼宇管理
	public void building(){
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getBuilding(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("buildinglist", page.getList());
		setAttr("totalrow", page.getTotalRow());
		render("building_list.html");
	}
	//区域管理
	public void areamanage(){
		
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getArea(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("arealist", page.getList());
		
		render("area_manage.html");
	}
	//缴费管理
	public void paymanage(){
		
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getPayment(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("paylist", page.getList());
		setAttr("totalrow", page.getTotalRow());
		render("pay_manage.html");
	}
	//安全管理检查记录
	public void safetyagreement(){
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getSafeInspect(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("safeinspectlist", page.getList());
		setAttr("totalrow", page.getTotalRow());
		render("safety_record.html");
	}
	//园区安全责任书签订
	public void safetyrecord(){	
		Integer pageno = getParaToInt() == null?1:getParaToInt();
		Page<Record> page = ParkManageService.getSafeProtocols(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("safeprolist", page.getList());
		render("safety_agreement.html");
	}
	//通知公告
	public void notice(){	
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = ParkManageService.getNotice(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("noticelist", page.getList());
		render("notice.html");
	}
	
	//添加以及修改员工信息
	public void getemp(){
		Integer id = getParaToInt();
		if(id!=null){
			setAttr("emp", ParkManageService.getEmp(id));	
		}
		List<Record> departs = SystomService.getDepartMents();
		List<Record> positions = ParkManageService.getPosition();
		setAttr("departs", departs);
		setAttr("positions", positions);	
		render("emp_detail.html");
	}
	//添加以及修改楼宇信息
	public void getBuding(){
		
		render("building_detail.html");
	}
	//添加以及修改区域管理信息
	public void getArea(){
		
		render("area_detail.html");
	}
	//添加以及修改缴费记录
	public void getPay(){
		
		render("pay_detail.html");
	}
	//添加以及修改园区安全管理检查记录
	public void getSaftyRecord(){
		
		render("safetyrec_detail.html");
	}
	//添加以及修改园区安全责任书签订情况
	public void getSaftyAgree(){
		
		render("safetyagr_detail.html");
	}
	//添加以及修改通知公告
	public void getNotice(){
		
		render("notice_detail.html");
	}
	//删除员工信息
	public void delemp(){
		
		boolean data = true;
		renderJson("");
	}
}
