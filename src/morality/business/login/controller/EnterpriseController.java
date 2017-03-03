package morality.business.login.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import morality.business.login.service.DataDictionaryService;
import morality.business.login.service.EnterpriseService;
import morality.util.interceptor.ManageInterceptor;

/**
 * @desc 企业管理
 * @author liyu
 */

@Before(ManageInterceptor.class)
public class EnterpriseController extends Controller {

	public void index() {
		render("");
	}

	// *******************************行业代码管理*******************************/
	// 入驻企业列表
	public void in_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = EnterpriseService.getEnterpriseInList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("enterpriseinlist", page.getList());

		render("in_list.html");
	}

	// 获得单条记录
	public void getEnterpriseIn() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("enterprisein", Db.findById("t_enterprise_in", id));
		}

		render("in_detail.html");
	}

	// *******************************离驻企业管理*******************************/
	// 离驻企业列表
	public void retreat_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = EnterpriseService.getEnterpriseRetreatList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("enterpriseretreatlist", page.getList());

		render("retreat_list.html");
	}

	// 获得单条记录
	public void getEnterpriseRetreat() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("enterpriseretreat", Db.findById("t_enterprise_in", id));
		}

		render("retreat_detail.html");
	}

	// *******************************企业经济情况管理*******************************/
	// 企业经济情况列表
	public void economy_list() {

		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = EnterpriseService.getEconomyList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("economylist", page.getList());

		render("economy_list.html");
	}

	// 获得单条记录
	public void getEconomy() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("economy", Db.findById("t_enterprise_economy", id));
		}

		render("economy_detail.html");
	}

	// *******************************企业从业人员管理*******************************/
	// 企业从业人员列表
	public void practitioners_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = EnterpriseService.getPractitionersList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("practitionerslist", page.getList());

		render("practitioners_list.html");
	}

	// 获得单条记录
	public void getPractitioner() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("practitioner", Db.findById("t_practitioner", id));
		}

		render("practitioner_detail.html");
	}

	// *******************************企业知识产权管理*******************************/
	// 企业知识产权列表
	public void property_right_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = EnterpriseService.getPropertyRightList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("propertyrightlist", page.getList());

		render("property_right_list.html");
	}

	// 获得单条记录
	public void getPropertyRight() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("propertyright", Db.findById("t_property_right", id));
		}

		render("property_right_detail.html");
	}

}
