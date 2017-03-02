package morality.business.login.controller;

import java.util.List;

/**
 * @desc 数据字典
 * @author liyu
 */
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import morality.business.login.service.DataDictionaryService;
import morality.util.interceptor.ManageInterceptor;

@Before(ManageInterceptor.class)
public class DataDictionaryController extends Controller {

	// *******************************职位管理*******************************/
	// 职位列表
	public void position_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Page<Record> page = DataDictionaryService.getPositionList(pageno, 16);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("positionlist", page.getList());

		render("position_list.html");
	}

	// 获得单条记录
	public void getPosition() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("position", Db.findById("t_position", id));
		}
		render("position_detail.html");
	}

	// *******************************大楼管理*******************************/
	// 大楼列表
	public void department_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();

		Page<Record> page = DataDictionaryService.getDepartmentList(pageno, 16);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("departmentlist", page.getList());

		render("department_list.html");
	}

	// 获得单条记录
	public void getDepartment() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("department", Db.findById("t_department", id));
		}
		render("department_detail.html");
	}

	// *******************************大楼编号管理*******************************/
	// 大楼编号列表
	public void building_no_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = DataDictionaryService.getBuildingNoList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("buildingnolist", page.getList());

		render("building_no_list.html");
	}

	// 获得单条记录
	public void getBuildingNo() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("buildingno", Db.findById("t_building_number", id));
		}
		render("building_no_detail.html");
	}

	// *******************************大楼性质管理*******************************/
	// 大楼性质列表
	public void building_nature_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = DataDictionaryService.getBuildingNatureList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("buildingnaturelist", page.getList());

		render("building_nature_list.html");
	}

	// 获得单条记录
	public void getBuildingNature() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("buildingnature", Db.findById("t_building_nature", id));
		}
		render("building_nature_detail.html");
	}

	// *******************************行业代码父级管理*******************************/
	// 行业代码父级列表
	public void superior_industry_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = DataDictionaryService.getSuperiorIndustryList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("superiorindustrylist", page.getList());

		render("superior_industry_list.html");
	}

	// 获得单条记录
	public void getSuperiorIndustry() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("superiorindustry", Db.findById("t_superior_industry", id));
		}
		render("superior_industry_detail.html");
	}

	// *******************************行业代码子级管理*******************************/
	// 行业代码子级列表
	public void sub_industry_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = DataDictionaryService.getSubIndustryList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("subindustrylist", page.getList());

		render("sub_industry_list.html");
	}

	// 获得单条记录
	public void getSubIndustry() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("subindustry", Db.findById("t_sub_industry", id));
		}

		// 获得父级行业列表
		List<Record> superiorindustrylist = DataDictionaryService.getSuperiorIndustryList();
		setAttr("superiorindustrylist", superiorindustrylist);

		render("sub_industry_detail.html");
	}

	// *******************************行业代码管理*******************************/
	// 行业代码列表
	public void industry_code_list() {
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Integer pagesize = 16;

		Page<Record> page = DataDictionaryService.getIndustryCodeList(pageno, pagesize);

		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("industrycodelist", page.getList());

		render("industry_code_list.html");
	}

}
