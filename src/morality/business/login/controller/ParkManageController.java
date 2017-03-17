package morality.business.login.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import morality.business.login.service.ParkManageService;
import morality.business.login.service.StatisticService;
import morality.business.login.service.SystomService;
import morality.util.interceptor.ManageInterceptor;
import morality.util.tool.MD5Util;

@Before(ManageInterceptor.class)
public class ParkManageController extends Controller {

	/*********************** 员工管理 ************************/
	// 获得员工管理列表
	public void index() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		if (mopids.indexOf("131") != -1) {
			setAttr("_add", true);
		}
		if (mopids.indexOf("132") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("133") != -1) {
			setAttr("_edit", true);
		}

		Integer pageno = getParaToInt(0) == null ? 1 : getParaToInt(0);
		Page<Record> page = ParkManageService.getEmpList(pageno, 16);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("emplist", page.getList());
		setAttr("totalrow", page.getTotalRow());
		render("employee_list.html");
	}

	// 添加以及修改员工信息（界面弹窗过程）
	public void getemp() {
		Integer id = getParaToInt();
		if (id != null) {
			setAttr("emp", ParkManageService.getEmp(id));
		}
		List<Record> roles = ParkManageService.getRole();
		List<Record> departs = SystomService.getDepartMents();
		List<Record> positions = ParkManageService.getPosition();
		setAttr("roles", roles);
		setAttr("departs", departs);
		setAttr("positions", positions);
		render("emp_detail.html");
	}

	//修改以及添加员工信息
	public void saveEmp() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Integer id= getParaToInt("empid");
		String name = getPara("empname");
		int empno = getParaToInt("empno");
		String pass =MD5Util.getEncryptedPwd((getPara("emppassword1")));
		String password = new String(pass);
		String phone = getPara("emphone");
		int department_id = getParaToInt("department");
		int position = getParaToInt("position");
		int role_id = getParaToInt("role");
		String statusment = getPara("status");
		Integer status = null;
		if (statusment.equals("在职")) {
			status = 1;
		} else {
			status = 0;
		}
		boolean result = ParkManageService.saveEmp(id, name, password, empno, phone, department_id, position, role_id,
				status);
		renderJson("result", result);
	}

	// 删除员工信息
	public void delemp() {
		Integer id = getParaToInt();
		boolean data = ParkManageService.delEmp(id);
		renderJson(data);
	}

	/*********************** 楼宇管理 ************************/
	// 获得楼宇管理列表
	public void building() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		if (mopids.indexOf("134") != -1) {
			setAttr("_add", true);
		}
		if (mopids.indexOf("135") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("136") != -1) {
			setAttr("_edit", true);
		}
		if (mopids.indexOf("137") != -1) {
			setAttr("_search", true);
		}

		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		Integer fstate = getParaToInt("fstate") == null ? 9 : getParaToInt("fstate");
		try {
			String buildno = getPara("floorno");
			buildno = URLDecoder.decode(buildno == null ? "" : buildno, "utf-8");
			Page<Record> page = ParkManageService.getBuilding(pageno, 16, fstate, buildno);
			setAttr("pageno", pageno);
			setAttr("fstate", fstate);
			setAttr("floorno", buildno);
			setAttr("totalpage", page.getTotalPage());
			setAttr("buildinglist", page.getList());
			setAttr("totalrow", page.getTotalRow());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		render("building_list.html");
	}

	// 添加以及修改楼宇信息（界面弹窗过程）
	public void getBuding() {
		Integer id = getParaToInt();
		if (id != null) {
			setAttr("building", ParkManageService.getBuildingMessage(id));
		}
		List<Record> buildNos = ParkManageService.getBuildNos();
		List<Record> buildNatures = ParkManageService.getBuildingNature();
		setAttr("buildNos", buildNos);
		setAttr("buildNatures", buildNatures);
		render("building_detail.html");
	}

	// 修改以及添加楼宇信息
	public void saveBuild() {
		Integer id = getParaToInt("id");
		String name = getPara("buildname");
		int nature = getParaToInt("buildnature");
		int building_no = getParaToInt("buildno");
		int floor_no = getParaToInt("buildfloorno");
		int total_area = getParaToInt("buildtotalarea");
		int usable_area = getParaToInt("buildusablearea");
		int status = getParaToInt("buildstatus");
		boolean result = ParkManageService.saveBuild(id, name, nature, floor_no, building_no, total_area, usable_area,
				status);
		renderJson("result", result);
	}

	// 删除楼宇信息
	public void delBuild() {
		Integer id = getParaToInt();
		boolean data = ParkManageService.delBuild(id);
		renderJson(data);
	}

	/*********************** 区域管理 ************************/
	// 获得区域管理列表
	public void areamanage() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		if (mopids.indexOf("138") != -1) {
			setAttr("_add", true);
		}
		if (mopids.indexOf("139") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("140") != -1) {
			setAttr("_edit", true);
		}
		if (mopids.indexOf("141") != -1) {
			setAttr("_search", true);
		}
		if (mopids.indexOf("142") != -1) {
			setAttr("_entext", true);
		}

		Integer fstate = getParaToInt("fstate") == null ? 9 : getParaToInt("fstate");
		String direction = getPara("direction") == null ? "朝向" : getPara("direction");
		String areaname = getPara("areaname");
		String company_name = getPara("company_name");
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");

		setAttr("fstate", fstate);
		setAttr("direction", direction);
		setAttr("areaname", areaname);
		setAttr("company_name", company_name);
		Page<Record> page = ParkManageService.getArea(pageno, 16, fstate, direction, areaname, company_name);

		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("arealist", page.getList());
		render("area_manage.html");
	}

	//添加以及修改区域管理信息（界面弹窗过程）
	public void getArea(){
		Integer id = getParaToInt();
		if (id != null) {
			Record Area = ParkManageService.getArea(id);
			setAttr("Area", Area);
		}
		List<Record> buildNos = ParkManageService.getBuildNos();
		setAttr("buildNos", buildNos);
		render("area_detail.html");
	}

	// 添加区域管理模块入驻退驻企业信息(界面弹窗界面)
	public void getAreaCompany() {
		Integer id = getParaToInt();
		List<Record> list = ParkManageService.getEnterprise();

		setAttr("id", id);
		setAttr("list", list);
		render("area_detail_entercom.html");
	}

	// 修改区域管管理入驻退驻信息
	public void saveAreaCompany() {
		Integer id = getParaToInt("id");
		String company_name = getPara("company_name");
		boolean result = ParkManageService.saveAreaConpanyName(id, company_name);
		renderJson("result", result);
	}

	// 修改以及添加区域信息
	public void saveArea() {
		Byte status = null;
		Integer id = getParaToInt("id");
		String area_name = getPara("areaname");
		String direction = getPara("areadirect");
		String area_no = getPara("areano");
		String building_no = getPara("buildNo");
		String floor_no = getPara("areafloorno");
		int area = getParaToInt("areasize");
		if (getPara("areastatus").equals("已租")) {
			status = 1;
		} else {
			status = 0;
		}
		;
		String the_company = getPara("the_company");
		boolean result = ParkManageService.saveArea(id, area_name, direction, area_no, building_no, floor_no, area,
				status, the_company);
		renderJson("result", result);
	}

	// 根据id删除区域信息
	public void delArea() {
		Integer id = getParaToInt();
		boolean data = ParkManageService.delArea(id);
		renderJson(data);
	}

	/*********************** 缴费管理 ************************/
	// 获得缴费管理列表
	public void paymanage() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		if (mopids.indexOf("143") != -1) {
			setAttr("_add", true);
		}
		if (mopids.indexOf("144") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("145") != -1) {
			setAttr("_edit", true);
		}
		if (mopids.indexOf("146") != -1) {
			setAttr("_search", true);
		}

		Integer year = getParaToInt("year") == null ? 0 : getParaToInt("year");
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		String quarterly = getPara("quarterly") == null ? "选择季度" : getPara("quarterly");
		String company_name = getPara("company_name");

		setAttr("year", year);
		setAttr("quarterly", quarterly);
		setAttr("company_name", company_name);
		Page<Record> page = ParkManageService.getPayment(pageno, 16, year, quarterly, company_name);

		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("paylist", page.getList());
		setAttr("totalrow", page.getTotalRow());
		render("pay_manage.html");
	}

	// 添加以及修改缴费记录（界面弹窗过程）
	public void getPay() {

		Integer id = getParaToInt();
		if (id != null) {
			Record payment = ParkManageService.getPayment(id);
			setAttr("payment", payment);
		}
		List companyList = ParkManageService.getEnterprise();
		setAttr("companyList", companyList);
		render("pay_detail.html");
	}

	// 修改以及添加缴费信息
	public void savePayment() {
		Integer id = getParaToInt("id");
		String year = getPara("year");
		String quarterly = getPara("quarterly");
		
		Integer company_id = getParaToInt("company");
		String company_name = ParkManageService.getCompanyName(company_id).getStr("enterprise_name");
		Double should_pay_rent = Double.parseDouble(getPara("should_pay_rent"));
		Double paid_rent = Double.parseDouble(getPara("paid_rent"));
		Double property_costs = Double.parseDouble(getPara("property_costs"));
		Double paid_property_charges = Double.parseDouble(getPara("paid_property_charges"));
		Double should_pay_water = Double.parseDouble(getPara("should_pay_water"));
		Double real_water_fee = Double.parseDouble(getPara("real_water_fee"));
		boolean result = ParkManageService.savePayment(id, year, quarterly,company_id,company_name, should_pay_rent, paid_rent, property_costs, paid_property_charges, should_pay_water, real_water_fee);
		renderJson("result",result);
	}

	// 根据id删除缴费信息
	public void delPay() {
		Integer id = getParaToInt();
		boolean data = ParkManageService.delPay(id);
		renderJson(data);
	}

	/*********************** 安全管理检查记录 ************************/
	// 获得安全管理检查列表
	public void safetyrecord() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		/*
		 * if(mopids.indexOf("143")!=-1){ setAttr("_add", true); }
		 */
		if (mopids.indexOf("147") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("148") != -1) {
			setAttr("_edit", true);
		}
		if (mopids.indexOf("149") != -1) {
			setAttr("_search", true);
		}
		if(mopids.indexOf("150")!=-1){
			setAttr("_download",true);
		}
		if (mopids.indexOf("151") != -1) {
			setAttr("_export", true);
		}
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		Integer rectification = getParaToInt("fstate") == null ? 9 : getParaToInt("fstate");
		String company_name = getPara("company_name");
		String sdate = getPara("sdate");
		Page<Record> page = ParkManageService.getSafeInspect(pageno, 16, sdate, rectification, company_name);
		setAttr("fstate", rectification);
		setAttr("company_name", company_name);
		setAttr("sdate", sdate);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("safeinspectlist", page.getList());
		setAttr("totalrow", page.getTotalRow());
		render("safety_record.html");
	}

	//添加以及修改园区安全管理检查记录（界面弹窗过程）
	public void getSaftyRecord(){
		Integer id = getParaToInt();
		if (id != null) {
			Record saferecord = ParkManageService.getSafeRecord(id);
			setAttr("saferecord", saferecord);
		}
		List<Record> enterprise = ParkManageService.getEnterprise();
		setAttr("enterprise", enterprise);
		render("safetyrec_detail.html");
	}

	// 修改以及添加安全管理检查记录
	public void saveSafeRecord() {
		try {
			Integer id = getParaToInt("id");
			Integer company_id = getParaToInt("company_id");
			String company_name = ParkManageService.getCompanyName(company_id).getStr("enterprise_name");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date check_time = sdf.parse(getPara("check_time"));
			String examiner = getPara("examiner");
			Date rectification_time = sdf.parse(getPara("rectification_time"));
			String rectification_man = getPara("rectification_man");
			String supervision_personnel = getPara("supervision_personnel");
			String hidden_danger = getPara("hidden_danger");
			String performance = getPara("performance");
			String remarks = getPara("remarks");
			int is_rectification = getParaToInt("is_rectification");
			boolean result = ParkManageService.saveSafetyRecord(id, company_id, company_name, check_time, examiner,
					rectification_time, rectification_man, supervision_personnel, hidden_danger, performance, remarks,
					is_rectification);
			renderJson("result", result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 根据id删除安全管理检查记录
	public void delsafeRecord() {
		Integer id = getParaToInt();
		boolean data = ParkManageService.delSaveRecord(id);
		renderJson(data);
	}
	
	//导出安全管理检查记录excel表格
	public  void exportSafeRecord(){
		Integer fstate = getParaToInt("fstate");
		String company_name = getPara("company_name");
		String sdate = getPara("sdate");
		boolean result = ParkManageService.getSafeRecordForExcel(getResponse(), sdate, fstate, company_name);
		if (true) {
			renderNull();
		} else {
			renderText("导出失败");
		}

	}
	//导出安全管理检查记录
	public void exportWord() throws IOException {
		Integer id = getParaToInt();
		ParkManageService.excWord(getResponse(), getRequest(), id);
		renderNull();
	}
	/***********************园区安全责任书签订************************/
	//获得安全责任书签订列表
	public void safetyagreement(){
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		if (mopids.indexOf("143") != -1) {
			setAttr("_enterexit", true);
		}
		if (mopids.indexOf("152") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("153") != -1) {
			setAttr("_edit", true);
		}

		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Page<Record> page = ParkManageService.getSafeProtocols(pageno, 10);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("safeprolist", page.getList());
		render("safety_agreement.html");
	}

	// 添加以及修改园区安全责任书签订情况（界面弹窗过程）
	public void getSaftyAgree() {
		Integer id = getParaToInt();
		if (id != null) {
			Record safeagree = ParkManageService.getSafeArgree(id);
			String attachment = ParkManageService.getSafeArgree(id).getStr("attachment");
			setAttr("attachment", attachment);
			setAttr("safeagree",safeagree );
		}
		List<Record> enterprise = ParkManageService.getEnterprise();
		setAttr("enterprise", enterprise);
		render("safetyagr_detail.html");
	}
	
	//修改以及添加园区安全责任书签订情况
	public void saveSafeAgree(){
		boolean status =false;
		Integer id = getParaToInt("id");
		Integer company_id = getParaToInt("company_id");
		Integer sta = getParaToInt("status");
		if(sta==1){
			status = true;
		}else{
			status =false;
		}
		String attachment = getPara("file_url");
		boolean data = ParkManageService.saveSafeAgree(id, company_id, status, attachment);
		renderJson(data);
	}
	
	//根据id删除安全责任书签订情况
	public void delSafeAgree(){
		Integer id = getParaToInt();
		boolean data = ParkManageService.delSafeAgree(id);
		renderJson(data);
	}
	
	// 上传文件
	public void uploadfile(){
		UploadFile uf = getFile("file1");
		String fname = uf.getOriginalFileName();
		uf.getFile().renameTo(new File(PropKit.get("filepath") + fname));
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("error", 0);
		responseMap.put("url", fname);
		renderJson(responseMap);
	}

	/*********************** 通知公告 ************************/
	// 获得通知公告列表
	public void notice() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);

		if (mopids.indexOf("154") != -1) {
			setAttr("_add", true);
		}
		if (mopids.indexOf("156") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("157") != -1) {
			setAttr("_edit", true);
		}

		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Page<Record> page = ParkManageService.getNotice(pageno, 16);
		setAttr("pageno", pageno);
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("noticelist", page.getList());
		render("notice.html");
	}

	// 添加以及修改通知公告（界面弹窗过程）
	public void getNotice() {
		Integer id = getParaToInt();
		if (id != null) {
			Record notice = ParkManageService.getsingleNotice(id);
			setAttr("notice", notice);
		}
		render("notice_detail.html");
	}

	// 修改以及添加通知公告
	public void saveNotice() {
		Record admin = getSessionAttr("admin");
		String role_name = admin.getStr("role_name");
		Integer id = getParaToInt("id");
		String title = getPara("title");
		String content = getPara("cont");
		boolean result = ParkManageService.saveNotice(id,role_name,title, content);
		renderJson("result", result);
	}

	// 根据id删除通知公告
	public void delNotice() {
		Integer id = getParaToInt();
		boolean data = ParkManageService.delNotice(id);
		renderJson(data);
	}

}
