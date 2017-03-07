package morality.business.login.service;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class EnterpriseService {
	/***********************入驻企业管理************************/
	// 入驻企业分页查询
	public static Page<Record> getEnterpriseInList(Integer pageno, Integer pagesize) {
		// return Db.paginate(pageno, pagesize,
		// "SELECT
		// id,enterprise_name,industry,organizational_code,registration_number,representative,contact,registered_address
		// ",
		// "FROM t_enterprise_in");
		return Db.paginate(pageno, pagesize, "SELECT * ", "FROM t_enterprise_in ");
	}
	
	public static boolean saveRetreat(Integer id, String retreatreason) {
		Record record = new Record();
		record.set("retreat_reason", retreatreason);
		return Db.update("t_enterprise_in", record);
	}

	/***********************离驻企业管理************************/
	// 离驻企业分页查询
	public static Page<Record> getEnterpriseRetreatList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT * ", "FROM t_enterprise_in ");
	}

	/***********************企业经济情况管理************************/
	// 企业经济情况分页查询
	public static Page<Record> getEconomyList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,company_name,the_date,income,net_profit,taxation,investment ",
				"FROM t_enterprise_economy ");
	}

	/***********************企业从业人员管理************************/
	// 企业从业人员分页查询
	public static Page<Record> getPractitionersList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT * ",
				"FROM t_practitioners ");
	}
	
	/***********************企业知识产权管理************************/
	// 企业知识产权分页查询
	public static Page<Record> getPropertyRightList(Integer pageno, Integer pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT id,company_name,the_date,apply,approval,patent,copyright,software_product",
				"FROM t_property_right ");
	}

	// 保存企业知识产权
	public static boolean savePropertyRight(Record record) {
		if (null != record.getInt("id")) {
			return Db.update("t_property_right", record);
		} else {
			record.set("create_time", new Date());
			return Db.save("t_property_right", record);
		}
	}



}
