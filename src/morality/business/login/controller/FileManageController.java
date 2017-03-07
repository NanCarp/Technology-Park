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
	//添加以及修改文件传阅管理
	public void getFileRead(){
			
		render("fileread_detail.html");
	}
	
	//添加项目申报管理
	public void getProject(){
			
		render("projectdeclar_detail.html");
	}
}
