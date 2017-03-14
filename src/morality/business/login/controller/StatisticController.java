package morality.business.login.controller;

import java.io.IOException;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import morality.business.login.service.EnterpriseService;
import morality.business.login.service.StatisticService;
import morality.util.interceptor.ManageInterceptor;

/**
 * @desc 查询统计
 * @author yangbo
 */
@Before(ManageInterceptor.class)
public class StatisticController extends Controller {

	/*********************** 楼区数据统计 ************************/
	// 楼区列表
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

	// 楼区信息导出
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
	public void companylist() {
		Integer pageno = getParaToInt("pageno") == null ? 1 : getParaToInt("pageno");
		Page<Record> page = StatisticService.getCompanyInfoList(pageno, 16);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("companylist", page.getList());
		render("company_list.html");
	}

	// 导出word
	public void exportWord() throws IOException {
		Integer id = getParaToInt();
		StatisticService.excWord(getResponse(), getRequest(), id);
		renderNull();
	}

	/*********************** 园区缴费情况总表 **********************/
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
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		String year = getPara("year") == null ? "" : getPara("year");
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
	
	// 园区缴费情况导出Excel
		public void exportPayment() {
			String year = getPara(0) == null ? "" : getPara(0);
			String companyname = getPara(1) == null ? "" : getPara(1);
			boolean result = StatisticService.getPaymentForExcel(getResponse(), companyname, year );
			if (result) {
				renderNull();
			} else {
				renderText("导出失败");
			}
		}
}
