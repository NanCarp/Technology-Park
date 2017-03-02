package morality.business.login.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
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
		setAttr("name", admin.getStr("name"));
		setAttr("role", admin.getStr("role_name"));
		setAttr("department", admin.getStr("department_name"));
		render("index.html");
	}
	
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
}
