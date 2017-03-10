package morality.business.login.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import morality.business.login.service.LoginService;
import morality.util.interceptor.ManageInterceptor;
import morality.util.tool.MD5Util;

/**
* @ClassName: LoginController
* @Description: 用户登录的控制器
* @author: Yetangtang
* @date: 2017年2月25日 上午11:10:54
* @version: 1.0 版本初成
 */
public class LoginController extends Controller{
	
	@Before(ManageInterceptor.class)
	public void index(){
		Record admin = getSessionAttr("admin");
		setAttr("id", admin.getInt("id"));
		setAttr("name", admin.getStr("name"));
		setAttr("role", admin.getStr("role_name"));
		setAttr("rid", admin.getInt("role_id"));
		setAttr("department", admin.getStr("department_name"));
		// 对应用户展示的菜单
		Integer rid = admin.getInt("role_id");
		List<Record> menus = LoginService.getMenusByRid(rid);
	    List<String> list = new ArrayList<>();
		for (Record menu : menus) {
			Map<String, Object> pmap = new HashMap<>();
			if(menu.getInt("pid")==0){
				pmap.put("title", menu.getStr("name"));
				pmap.put("icon", menu.getStr("icon"));
				pmap.put("spread", menu.getInt("id")==1?true:false);
				List<Record> smenu = LoginService.getzMenusById(menu.getInt("id"), rid);
			    List<Object> sl = new ArrayList<>();
				for (Record sm : smenu) {
					Map<String, Object> smap = new HashMap<>();
					smap.put("title", sm.getStr("name"));
					smap.put("icon", sm.getStr("icon"));
					smap.put("href", sm.getStr("url"));
					sl.add(smap);
				}
				pmap.put("children", sl);
				String pjson = JsonKit.toJson(pmap);
				list.add(pjson);
			}
		}
		String ar = "[";
		for (int i=0; i<list.size(); i++) {
			ar += list.get(i);
			if(i<list.size()-1){
				ar += ",";
			}
		}
		ar += "]";
		setAttr("navs", ar);
		render("index.html");
	}
	
	// 登录
	public void login(){
		render("login.html");
	}
	
	// 登录操作
	public void adminLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String username = getPara("username");
		String password = getPara("password");
		
		boolean result = false;
		String msg = new String();
		
		Record admin = LoginService.getLoginInfo(username);
		if(admin == null){
			msg = "用户名或密码错误";
		}else{
			boolean v = MD5Util.validPassword(password, admin.getStr("password"));
			if(v){
				result = true;
				msg = "登录成功";
				getSession().setAttribute("admin", admin);
				Cookie cookie = new Cookie("morality", ""+admin.getInt("id"));
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath("/login/");
				getResponse().addCookie(cookie);
			}else{
				msg = "用户名或密码错误";
			}
		}
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("result", result);	
		responseMap.put("msg", msg);
		renderJson(responseMap);
	}
	
	// 修改密码
	public void changepsw(){
		Integer id = getParaToInt();
		setAttr("id", id);
		render("changepsw.html");
	}
	
	// 保存密码
	public void savePsw() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Integer id = getParaToInt("id");
		String newpsw = getPara("newpsw");
		Record user = new Record();
		user.set("id", id);
		user.set("password", MD5Util.getEncryptedPwd(newpsw));
		boolean result = Db.update("t_employee", user);
		renderJson(result);
	}
}
