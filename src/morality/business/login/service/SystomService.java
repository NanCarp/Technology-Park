package morality.business.login.service;

import java.util.Date;
import java.util.List;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
* 系统管理
* @author yangbo
*/
public class SystomService {

	// 获得角色列表
	public static Page<Record> getRoleList(Integer pageno, int pagesize) {
		return Db.paginate(pageno, pagesize, "SELECT a.id,a.role_name,b.department_name,a.role_desc,a.create_time  ", "FROM t_role a LEFT JOIN t_department b ON a.department_id = b.id");
	}

	// 获得部门
	public static List<Record> getDepartMents() {
		return Db.find("SELECT id,department_name FROM t_department");
	}

	public static boolean saveRole(Integer id, String rolename, Integer did, String desc) {
		Record record = new Record();
		record.set("role_name", rolename);
		record.set("department_id", did);
		record.set("role_desc", desc);
		record.set("modify_time", new Date());
		if(id != null){
			record.set("id", id);
			return Db.update("t_role", record);
		}else{
			record.set("create_time", new Date());
			return Db.save("t_role", record);
		}
	}
}
