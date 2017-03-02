package morality.business.login.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
* 数据字典管理
* @author liyu
*/

public class DataDictionaryService {
	// 职位分页查询
	public static Page<Record> getPositionList(Integer pageno, int pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,position_name,create_time ", "FROM t_position");
	}

	// 部门分页查询
	public static Page<Record> getDepartmentList(Integer pageno, int pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,department_name,create_time,`describe` ", "FROM t_department ");
	}

	// 大楼编号分页查询
	public static Page<Record> getBuildingNoList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,building_no,sort_id,create_time ", "FROM t_building_number ");
	}

	// 大楼性质分页查询
	public static Page<Record> getBuildingNatureList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,name,sort_id,create_time ", "FROM t_building_nature ");
	}

	// 父级行业分页查询
	public static Page<Record> getSuperiorIndustryList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,industry_code,industry_name,create_time ",
				"FROM t_superior_industry ");
	}

	// 查询父级行业列表
	public static List<Record> getSuperiorIndustryList() {
		return Db.find("SELECT id,industry_code,industry_name FROM t_superior_industry ");
	}

	// 子级行业分页查询
	public static Page<Record> getSubIndustryList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize,
				"SELECT id,sub_industry_code,sub_industry_name,superior_industry_id,create_time ",
				"FROM t_sub_industry ");
	}

	// 行业代码分页查询
	public static Page<Record> getIndustryCodeList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize,
				"SELECT id,industry_code,industry_name,superior_industry,sub_industry,create_time ",
				"FROM t_industry_code ");
	}

}
