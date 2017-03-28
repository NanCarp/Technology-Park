package morality.business.login.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.net.URLDecoder;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import morality.business.login.service.StatisticService;
import morality.util.interceptor.ManageInterceptor;

/**
 * @desc 查询统计
 * @author yangbo
 * @date 2017/03/23
 */
@Before(ManageInterceptor.class)
public class StatisticController extends Controller {
 	/*********************** 楼区数据统计 ************************/
	/**
	 * @author yangbo
	 * @desc 楼区列表
	 */
	public void buildinglist() {
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		Integer fstate = getParaToInt("fstate") == null ? 9 : getParaToInt("fstate");
		Integer astate = getParaToInt("astate") == null ? 9 : getParaToInt("astate");
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

	/**
	 * @author yangbo
	 * @desc 楼区信息导出
	 */
	public void exportbuilding() {
		int fstate = getParaToInt(0);
		int astate = getParaToInt(1);
		String floorno = getPara(2);
		String areano = getPara(3);
		boolean result = StatisticService.getBuildInfoForExcel(getResponse(), fstate, astate, floorno, areano);
		if (result) {
			renderNull();
		} else {
			renderText("导出失败");
		}
	}

	/*********************** 企业数据总览 ************************/
	
	/**
	 * @author xuhui
	 * @desc 显示企业数据
	 */
	public void companylist() {
		// 验证权限
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if (mopids.indexOf("188") != -1) {
			setAttr("_search", true);
		}
		if (mopids.indexOf("189") != -1) {
			setAttr("_export", true);
		}
		if (mopids.indexOf("190") != -1) {
			setAttr("_download", true);
		}
		
		
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		getSession().removeAttribute("wholeMapp");
		Page<Record> page = StatisticService.getCompanyInfosearch(pageno, 16, null);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("companylist", page.getList());
		render("company_list.html");
	}
	
	/**
	 * @author yangbo
	 * @desc 在查询后重新显示企业数据
	 */
	public void companylistForSearch() {
		// 验证权限
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if (mopids.indexOf("188") != -1) {
			setAttr("_search", true);
		}
		if (mopids.indexOf("189") != -1) {
			setAttr("_export", true);
		}
		if (mopids.indexOf("190") != -1) {
			setAttr("_download", true);
		}
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		Page<Record> page = StatisticService.getCompanyInfosearch(pageno, 16, (Map<String,Object>)getSessionAttr("wholeMapp"));
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("companylist", page.getList());
		render("company_list.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 查询企业信息
	 */
	public void comfind(){
		Integer type = getParaToInt("type");
		if(type!=2){
			Map<String,Object> wholeMap = new HashMap<String, Object>();
			wholeMap.put("company_name",getPara("company_name").trim());
			wholeMap.put("company_type",getParaToInt("company_type"));
			wholeMap.put("mindate",getPara("mindate"));//缴费时间
			wholeMap.put("maxdate", getPara("maxdate"));
			wholeMap.put("minsr",getPara("minsr"));//总收入
			wholeMap.put("maxsr",getPara("maxsr"));
			wholeMap.put("income_min",getPara("income_min"));//纯利润
			wholeMap.put("income_max", getPara("income_max"));
			wholeMap.put( "tax_min",getPara("tax_min"));//上缴税费
			wholeMap.put("tax_max",getPara("tax_max"));
			wholeMap.put("rd_min",getPara("rd_min"));//R&D投入
			wholeMap.put("rd_max",getPara("rd_max"));
			wholeMap.put("doctor_min",getParaToInt("doctor_min"));//博士生
			wholeMap.put("doctor_max",getParaToInt("doctor_max"));
			wholeMap.put("junior_min",getParaToInt("junior_min"));//大专生
			wholeMap.put("junior_max",getParaToInt("junior_max"));
			wholeMap.put("styabroad_min",getParaToInt("styabroad_min"));//留学人数
			wholeMap.put("styabroad_max",getParaToInt("styabroad_max"));
			wholeMap.put("thousand_min",getParaToInt("thousand_min"));//千人计划
			wholeMap.put("thousand_max", getParaToInt("thousand_max"));
			wholeMap.put("university_min",getParaToInt("university_min"));//应届大学生毕业生
			wholeMap.put("university_max",getParaToInt("university_max"));
			wholeMap.put("taxstatus_min",getParaToInt("taxstatus_min"));//保险缴纳情况
			wholeMap.put("taxstatus_max",getParaToInt("taxstatus_max"));
			wholeMap.put("addtax_min",getParaToInt("addtax_min"));//当年新增保险
			wholeMap.put("addtax_max",getParaToInt("addtax_max"));
			wholeMap.put("intellapply_min",getParaToInt("intellapply_min"));//申请知识产权
			wholeMap.put("intellapply_max",getParaToInt("intellapply_max"));
			wholeMap.put("aprintell_min",getParaToInt("aprintell_min"));//批准知识产权
			wholeMap.put("aprintell_max",getParaToInt("aprintell_max"));
			wholeMap.put("invent_min",getParaToInt("invent_min"));//发明专利
			wholeMap.put("invent_max",getParaToInt("invent_max"));
			wholeMap.put("software_min",getParaToInt("software_min"));//软件著作权
			wholeMap.put("software_max",getParaToInt("software_max"));
			wholeMap.put("product_min",getParaToInt("product_min"));//软件产品
			wholeMap.put("product_max",getParaToInt("product_max"));
			getSession().setAttribute("wholeMap", wholeMap);
			
			Map<String,Object> wholeMapp = new HashMap<String, Object>();
			wholeMapp.put("company_name",getPara("company_name").trim());
			wholeMapp.put("company_type",getParaToInt("company_type"));
			wholeMapp.put("mindate",getPara("mindate"));//缴费时间
			wholeMapp.put("maxdate", getPara("maxdate"));
			wholeMapp.put("minsr",getPara("minsr"));//总收入
			wholeMapp.put("maxsr",getPara("maxsr"));
			wholeMapp.put("income_min",getPara("income_min"));//纯利润
			wholeMapp.put("income_max", getPara("income_max"));
			wholeMapp.put( "tax_min",getPara("tax_min"));//上缴税费
			wholeMapp.put("tax_max",getPara("tax_max"));
			wholeMapp.put("rd_min",getPara("rd_min"));//R&D投入
			wholeMapp.put("rd_max",getPara("rd_max"));
			wholeMapp.put("doctor_min",getParaToInt("doctor_min"));//博士生
			wholeMapp.put("doctor_max",getParaToInt("doctor_max"));
			wholeMapp.put("junior_min",getParaToInt("junior_min"));//大专生
			wholeMapp.put("junior_max",getParaToInt("junior_max"));
			wholeMapp.put("styabroad_min",getParaToInt("styabroad_min"));//留学人数
			wholeMapp.put("styabroad_max",getParaToInt("styabroad_max"));
			wholeMapp.put("thousand_min",getParaToInt("thousand_min"));//千人计划
			wholeMapp.put("thousand_max", getParaToInt("thousand_max"));
			wholeMapp.put("university_min",getParaToInt("university_min"));//应届大学生毕业生
			wholeMapp.put("university_max",getParaToInt("university_max"));
			wholeMapp.put("taxstatus_min",getParaToInt("taxstatus_min"));//保险缴纳情况
			wholeMapp.put("taxstatus_max",getParaToInt("taxstatus_max"));
			wholeMapp.put("addtax_min",getParaToInt("addtax_min"));//当年新增保险
			wholeMapp.put("addtax_max",getParaToInt("addtax_max"));
			wholeMapp.put("intellapply_min",getParaToInt("intellapply_min"));//申请知识产权
			wholeMapp.put("intellapply_max",getParaToInt("intellapply_max"));
			wholeMapp.put("aprintell_min",getParaToInt("aprintell_min"));//批准知识产权
			wholeMapp.put("aprintell_max",getParaToInt("aprintell_max"));
			wholeMapp.put("invent_min",getParaToInt("invent_min"));//发明专利
			wholeMapp.put("invent_max",getParaToInt("invent_max"));
			wholeMapp.put("software_min",getParaToInt("software_min"));//软件著作权
			wholeMapp.put("software_max",getParaToInt("software_max"));
			wholeMapp.put("product_min",getParaToInt("product_min"));//软件产品
			wholeMapp.put("product_max",getParaToInt("product_max"));
			getSession().setAttribute("wholeMapp", wholeMapp);
		}
		//返回值type==1为查询界面点击查询，返回值type==2为企业数据总揽界面点击刷新;
		type = type == null ? 1 : type;
		if(type != 1){
			if((Map<String,Object>)getSessionAttr("wholeMapp")==null){
				companylist();
			}else{
				companylistForSearch();
			}
		}else{
			renderJson(true);
		}
	}
	
	/**
	 * @author yangbo
	 * @throws IOException
	 * @desc 导出word
	 */
	public void exportWord() throws IOException {
		Integer id = getParaToInt();
		StatisticService.excWord(getResponse(), getRequest(), id);
		renderNull();
	}
	
	/**
	 * @author yangbo 
	 * @desc 查询界面
	 */
	public void opensearch(){
		if(getSessionAttr("wholeMap")!=null){
			if(getSessionAttr("wholeMapp")!=null){
				Map<String,Object> map = getSessionAttr("wholeMap");
				for(Map.Entry<String, Object> entry:map.entrySet()){
					setAttr(entry.getKey(), entry.getValue());
				}
			}
		}
		render("cominfo_find.html");
	}
	
	/**
	 * @author yangbo 
	 * @desc 企业数据导出Excel
	 */
	public void exportCompany() {
		boolean result = StatisticService.getCompanyForExcel(getResponse(), (Map<String,Object>)getSessionAttr("wholeMapp"));
		if (result) {
			renderNull();
		} else {
			renderText("导出失败");
		}
	}
	/*********************** 园区缴费情况总表 **********************/
	
	/**
	 * @author yangbo
	 * @desc 园区缴费情况显示
	 */
	public void parkpaylist() {
		// 验证权限
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if (mopids.indexOf("191") != -1) {
			setAttr("_search", true);
		}
		if (mopids.indexOf("192") != -1) {
			setAttr("_excel", true);
		}

		Integer pagesize = 16;
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		String year = getPara("year") == null ? "": getPara("year");
		String companyname = getPara("companyname") == null ? "" : getPara("companyname");

		Page<Record> page = StatisticService.getParkpayList(pageno, pagesize, companyname, year);

		setAttr("year", year);
		setAttr("companyname", companyname);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("parkpaylist", page.getList());
		render("parkpay_list.html");	
	}
	
	/**
	 * @author yangbo
	 * @desc 园区缴费情况导出Excel
	 */
	public void exportPayment() {
		String year = getPara(0) == null ? "" : getPara(0);
		String companyname = URLDecoder.decode(getPara(1) == null ? "" : getPara(1));
		boolean result = StatisticService.getPaymentForExcel(getResponse(), companyname, year );
		if (result) {
			renderNull();
		} else {
			renderText("导出失败");
		}
	}
}
