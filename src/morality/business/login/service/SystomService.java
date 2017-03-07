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

	public static Page<Record> getMenuList(Integer pageno, int pagesize) {
		return Db.paginate(pageno, pagesize, " SELECT a.id, a.name,b.name AS pname,a.url,a.remarks ", " FROM t_menu a LEFT JOIN t_menu b ON a.pid = b.id ORDER BY a.id");
	}

	public static List<Record> getMenu() {
		return Db.find("select id,name,url,pid from t_menu");
	}

	public static boolean saveMenu(Integer id, String name, Integer pid, String url, String desc) {
		Record record = new Record();
		record.set("name", name);
		record.set("url", url);
		record.set("pid", pid);
		record.set("remarks", desc);
		record.set("modify_time", new Date());
		if(id != null){
			record.set("id", id);
			return Db.update("t_menu", record);
		}else{
			record.set("create_time", new Date());
			return Db.save("t_menu", record);
		}
	}

	public static boolean saveAuthority(Integer id, String mid, int rid) {
		boolean result = false;
		String[] mlist = new String[]{};
		if(rid == 1){
			mid = Db.queryStr("SELECT GROUP_CONCAT(id) FROM t_menu");
		}
		mlist = mid.split(",");
		Db.update("delete from t_role_details where role_id=?", rid);
		for(int i=0;i<mlist.length;i++){
			Record record = new Record();
			record.set("role_id", rid);
			record.set("menu_id", mlist[i]);
			record.set("create_time", new Date());
			record.set("modify_time", new Date());
			result = Db.save("t_role_details", record);
			if(!result){
				return result;
			}
		}
		return result;
	}
}
