package morality.business.login.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import morality.business.login.service.SystomService;
import morality.util.interceptor.ManageInterceptor;

/**
* @desc 系统操作
* @author yangbo
*/
@Before(ManageInterceptor.class)
public class SystomController extends Controller{
	
	/***********************模块管理************************/
	// 模块列表
	public void menulist(){
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if(mopids.indexOf("100")!=-1){
			setAttr("_add", true);
		}
		if(mopids.indexOf("101")!=-1){
			setAttr("_delete", true);
		}
		if(mopids.indexOf("102")!=-1){
			setAttr("_edit", true);
		}
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = SystomService.getMenuList(pageno, 16);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("menulist", page.getList());
		render("menu_list.html");
	}
	
	// 获得单条记录
	public void getMenu(){
		Integer id = getParaToInt();
		if(id != null){
			setAttr("menu", Db.findById("t_menu", id));
		}
		setAttr("menulist", SystomService.getMenu());
		render("menu_detail.html");
	}
	
	// 保存数据
	public void saveMenu(){
		Integer id = getParaToInt("id");
		String name = getPara("mname");
		Integer pid = getParaToInt("pid");
		String url = "".equals(getPara("url"))?null:getPara("url");
		String desc = getPara("desc");
		boolean result = SystomService.saveMenu(id, name, pid, url, desc);
		renderJson("result", result);
	}
	
	// 删除数据
	public void delMenu(){
		Integer id = getParaToInt();
		Db.update("delete from t_role_details where menu_id=?", id);
		boolean result = Db.deleteById("t_menu", id);
		renderJson(result);
	}
	
	// 所有菜单
	public void allMenu(){
		renderJson(SystomService.getAuthMenu());
	}
	
	/***********************角色管理************************/
	// 角色列表
	public void rolelist(){
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if(mopids.indexOf("103")!=-1){
			setAttr("_add", true);
		}
		if(mopids.indexOf("104")!=-1){
			setAttr("_delete", true);
		}
		if(mopids.indexOf("105")!=-1){
			setAttr("_authority", true);
		}
		if(mopids.indexOf("106")!=-1){
			setAttr("_edit", true);
		}
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = SystomService.getRoleList(pageno, 16);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("rolelist", page.getList());
		render("role_list.html");
	}
	
	// 获得单条记录
	public void getRole(){
		Integer id = getParaToInt();
		if(id != null){
			setAttr("role", Db.findById("t_role", id));
		}
		List<Record> departs = SystomService.getDepartMents();
		setAttr("departs", departs);
		render("role_detail.html");
	}
	
	// 保存数据
	public void saveRole(){
		Integer id = getParaToInt("id");
		String rolename = getPara("rolename");
		Integer did = getParaToInt("department");
		String desc = getPara("desc");
		boolean result = SystomService.saveRole(id, rolename, did, desc);
		renderJson("result", result);
	}
	
	// 删除数据
	public void delRole(){
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_role", id);
		renderJson(result);
	}
	
	// 权限分配
	public void getAuthority(){
		int rid = getParaToInt();
		Record record = Db.findFirst("SELECT id,role_id,GROUP_CONCAT(menu_id) AS mid FROM t_role_details where role_id=?", rid);
		String ms = Db.queryStr("SELECT module_power_id FROM t_role_permissions WHERE role_id = ?", rid);
		if(record != null){
			setAttr("id", record.getInt("id"));
			setAttr("mid", record.getStr("mid")+","+ms);
		}
		setAttr("rid", rid);
		render("role_authority.html");
	}

	// 保存权限
	public void saveAuthority(){
		Integer id = getParaToInt("id");
		String mid = getPara("mid");
		int rid = getParaToInt("rid");
		renderJson(SystomService.saveAuthority(id, mid, rid));
	}
	/***********************模块管理************************/
	// 模块列表
	public void authlist(){
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if(mopids.indexOf("107")!=-1){
			setAttr("_add", true);
		}
		if(mopids.indexOf("108")!=-1){
			setAttr("_delete", true);
		}
		if(mopids.indexOf("109")!=-1){
			setAttr("_edit", true);
		}
		Integer pageno = getParaToInt()==null?1:getParaToInt();
		Page<Record> page = SystomService.getAuthList(pageno, 16);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("authlist", page.getList());
		render("auth_list.html");
	}
	
	// 获得单条记录
	public void getAuth(){
		Integer id = getParaToInt();
		if(id != null){
			setAttr("auth", Db.findById("t_power_register", id));
		}
		List<Record> zmenus = SystomService.getZiMenuList();
		setAttr("zmenus", zmenus);
		render("auth_detail.html");
	}
	
	// 保存数据
	public void saveAuth(){
		Integer id = getParaToInt("id");
		String buttonid = getPara("buttonid");
		Integer zimenu = getParaToInt("zimenu");
		String method_name = getPara("method_name");
		Map<String, Object> map = SystomService.saveAuth(id, buttonid, zimenu, method_name);
		renderJson(map);
	}
	
	// 删除数据
	public void delAuth(){
		Integer id = getParaToInt();
		// 对应角色的按钮权限也将删除
		SystomService.deleteAuthForRole(id);
		boolean result = Db.deleteById("t_power_register", id);
		renderJson(result);
	}
}
