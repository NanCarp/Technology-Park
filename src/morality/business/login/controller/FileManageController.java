package morality.business.login.controller;

import com.jfinal.core.Controller;

public class FileManageController extends Controller {
	
	//文件传阅管理
	public void index(){
		render("fileread.html");
	}
	//项目申报管理
	public void projectDeclar(){
		render("projectdeclar.html");
	}
}
