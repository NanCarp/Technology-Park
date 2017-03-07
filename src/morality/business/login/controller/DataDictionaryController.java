package morality.business.login.controller;

import java.util.Date;
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

	// 保存数据
	public void savePosition() {
		Integer id = getParaToInt("id");
		String positionname = getPara("positionname");
		boolean result = DataDictionaryService.savePosition(id, positionname);

		renderJson("result", result);
	}

	// 删除数据
	public void delPosition() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_position", id);
		renderJson(result);
	}

	// *******************************部门管理*******************************/
	// 部门列表
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

	// 保存数据
	public void saveDepartment() {
		Integer id = getParaToInt("id");
		String departmentname = getPara("departmentname");
		String description = getPara("description");
		boolean result = DataDictionaryService.saveDepartment(id, departmentname, description);

		renderJson("result", result);
	}

	// 删除数据
	public void delDepartment() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_department", id);
		renderJson(result);
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

	// 保存数据
	public void saveBuildingNo() {
		Integer id = getParaToInt("id");
		String buildingNo = getPara("buildingno");
		Integer sortId = getParaToInt("sortid");
		boolean result = DataDictionaryService.saveBuildingNo(id, buildingNo, sortId);

		renderJson("result", result);
	}

	// 删除数据
	public void delBuildingNo() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_building_number", id);
		renderJson(result);
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

	// 保存数据
	public void saveBuildingNature() {
		Integer id = getParaToInt("id");
		String name = getPara("name");
		Integer sortId = getParaToInt("sortid");
		boolean result = DataDictionaryService.saveBuildingNature(id, name, sortId);

		renderJson("result", result);
	}

	// 删除数据
	public void delBuildingNature() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_building_nature", id);
		renderJson(result);
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

	// 保存数据
	public void saveSuperiorIndustry() {
		Integer id = getParaToInt("id");
		String industryCode = getPara("industrycode");
		String industryName = getPara("industryname");
		boolean result = DataDictionaryService.saveSuperiorIndustry(id, industryCode, industryName);

		renderJson("result", result);
	}

	// 删除数据
	public void delSuperiorIndustry() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_superior_industry", id);
		renderJson(result);
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

	// 保存数据
	public void saveSubIndustry() {
		Integer id = getParaToInt("id");
		String industrycode = getPara("industrycode");
		String industryname = getPara("industryname");
		int superiorindustryid = getParaToInt("superiorindustryid");
		boolean result = DataDictionaryService.saveSubIndustry(id, industrycode, industryname, superiorindustryid);

		renderJson("result", result);
	}

	// 删除数据
	public void delSubIndustry() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_sub_industry", id);
		renderJson(result);
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

	// 获得单条记录
	public void getIndustryCode() {
		Integer id = getParaToInt();
		if (null != id) {
			setAttr("industrycode", Db.findById("t_industry_code", id));
		}

		// 获得父级行业列表
		List<Record> superiorindustrylist = DataDictionaryService.getSuperiorIndustryList();
		setAttr("superiorindustrylist", superiorindustrylist);

		// 获得子级行业列表
		List<Record> subindustrylist = DataDictionaryService.getSubIndustryList();
		setAttr("subindustrylist", subindustrylist);

		render("industry_code_detail.html");
	}

	// 保存数据
	public void saveIndustryCode() {

		Record record = new Record()
				.set("id", getParaToInt("id"))
				.set("industry_code", getPara("industrycode"))
				.set("industry_name", getPara("industryname"))
				.set("superior_industry", getPara("superiorindustryname"))
				.set("sub_industry", getPara("subindustryname"))
				.set("modify_time", new Date());
				
		boolean result = DataDictionaryService.saveIndustryCode(record);

		renderJson("result", result);
	}

	// 删除数据
	public void delIndustryCode() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_industry_code", id);
		renderJson(result);
	}

}
