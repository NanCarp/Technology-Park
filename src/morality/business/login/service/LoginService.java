package morality.business.login.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
* 登录业务逻辑层
* @author yangbo
*/
public class LoginService {

	// 拦截器获得用户信息
	public static Record getLoginById(int id){
		return Db.findFirst("SELECT a.id,a.name,a.password,b.id as role_id,b.role_name,c.department_name FROM t_employee a "+
				"LEFT JOIN t_role b ON a.role_id = b.id LEFT JOIN t_department c ON a.department_id = c.id WHERE a.status = 1 AND a.id=?", id);
	}
	
	// 获得用户登录信息
	public static Record getLoginInfo(String username){
	  return Db.findFirst("SELECT a.id,a.name,a.password,a.role_id,b.role_name,c.department_name FROM t_employee a "+
			"LEFT JOIN t_role b ON a.role_id = b.id LEFT JOIN t_department c ON a.department_id = c.id WHERE a.status = 1 AND a.name=?", username);
	}
	
	// 根据角色ID找出所有菜单
	public static List<Record> getMenusByRid(int rid){
		return Db.find("SELECT b.id, b.url, a.menu_id, a.role_id,b.icon, b.pid, b.name FROM t_role_details a LEFT JOIN t_menu b ON a.menu_id = b.id WHERE role_id = ?", rid);
	}
	
	// 根据父次啊单和角色ID找出所有子菜单
	public static List<Record> getzMenusById(int pid, int rid){
		return  Db.find("SELECT a.name, a.url, a.icon FROM t_menu a LEFT JOIN t_role_details b ON a.id = b.menu_id WHERE a.pid = ? AND b.role_id = ?", pid, rid);
	}

}
