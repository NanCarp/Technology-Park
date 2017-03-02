package morality.business.login.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
* 登录业务逻辑层
* @author yangbo
*/
public class LoginService {

	// 拦截器获得用户信息
	public static Record getLoginById(int id){
		return Db.findFirst("SELECT a.id,a.name,a.password,b.role_name,c.department_name FROM t_employee a "+
				"LEFT JOIN t_role b ON a.role_id = b.id LEFT JOIN t_department c ON a.department_id = c.id WHERE a.status = 1 AND a.id=?", id);
	}
	
	// 获得用户登录信息
	public static Record getLoginInfo(String username){
	  return Db.findFirst("SELECT a.id,a.name,a.password,b.role_name,c.department_name FROM t_employee a "+
			"LEFT JOIN t_role b ON a.role_id = b.id LEFT JOIN t_department c ON a.department_id = c.id WHERE a.status = 1 AND a.name=?", username);
	}

}
