package morality.business.login.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import morality.business.login.service.FileManageService;
import morality.util.interceptor.ManageInterceptor;

@Before(ManageInterceptor.class)
public class FileManageController extends Controller {


	/*********************** 文件传阅管理 ************************/
	// 文件传阅管理
	public void filelist() {
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		Integer recipient_id = admin.getInt("id");//登录者id，用于判断是否有权限
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if(mopids.indexOf("177")!=-1){
			setAttr("_add", true);
		}
		if(mopids.indexOf("178")!=-1){
			setAttr("_delete", true);
		}
		if(mopids.indexOf("179")!=-1){
			setAttr("_edit", true);
		}
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Page<Record> page = FileManageService.getWjcyList(pageno, 16,recipient_id);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("wjcylist", page.getList());
		render("fileread.html");
	}

	// 添加以及修改文件传阅管理
	public void getFileRead() {
		Integer id = getParaToInt();
		Record record = Db.findById("t_file", id);
		if (record != null) {
			setAttr("file", record);
			setAttr("fid", record.getStr("recipient"));
			String file_url = record.getStr("file_url");
			setAttr("farr", file_url);
		}
		render("fileread_detail.html");
	}

	// 获得园区主任
	public void getParkhead() {
		renderJson(FileManageService.getParkheadInfo());
	}

	// 删除文件
	public void delFile() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_file", id);
		renderJson(result);
	}

	// 上传文件
	public void uploadfile() {
		UploadFile uf = getFile("file1");
		String fname = uf.getOriginalFileName();
		uf.getFile().renameTo(new File(PropKit.get("filepath") + fname));
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("error", 0);
		responseMap.put("url", fname);
		renderJson(responseMap);
	}

	// 保存文件
	public void saveFile() {
		boolean result = false;
		Integer id = getParaToInt("id");
		Record admin = getSessionAttr("admin");
		Record record = new Record();
		record.set("uploader", admin.getInt("id"));
		record.set("recipient", getPara("recipient"));
		record.set("file_name", getPara("file_name"));
		record.set("file_url", getPara("file_url"));
		if (id != null) {
			record.set("id", id);
			record.set("modify_time", new Date());
			result = Db.update("t_file", record);
		} else {
			record.set("create_time", new Date());
			record.set("modify_time", new Date());
			result = Db.save("t_file", record);
		}
		renderJson(result);
	}

	// 下载文件
	public void downloadFile() throws IOException {
		Integer id = getParaToInt();
		FileManageService.downloadFile(getResponse(), id);
		renderNull();
	}

	/*********************** 项目申报管理 ************************/
	// 项目申报管理
	public void projectDeclar() {
		// 验证权限
		Record admin = getSessionAttr("admin");
		Integer rid = admin.getInt("role_id");
		Integer recipient_id = admin.getInt("id");//登录者id，用于判断是否有权限
		String mopids = Db.queryStr("select module_power_id from t_role_permissions where role_id = ?", rid);
		if (mopids.indexOf("181") != -1) {
			setAttr("_add", true);
		}
		if (mopids.indexOf("182") != -1) {
			setAttr("_delete", true);
		}
		if (mopids.indexOf("183") != -1) {
			setAttr("_edit", true);
		}
		if (mopids.indexOf("184") != -1) {
			setAttr("_search", true);
		}
		Integer pageno = getParaToInt() == null ? 1 : getParaToInt();
		Page<Record> page = FileManageService.getProjectList(pageno, 16, recipient_id);
		setAttr("pageno", page.getPageNumber());
		setAttr("totalpage", page.getTotalPage());
		setAttr("totalrow", page.getTotalRow());
		setAttr("projectlist", page.getList());
		render("projectdeclar.html");
	}

	// 添加项目申报管理
	public void getProject() {
		Integer id = getParaToInt();
		Record record = Db.findById("t_project", id);
		if (record != null) {
			setAttr("project", record);
			setAttr("fid", record.getStr("recipient"));
			String file_url = record.getStr("file_url");
			setAttr("farr", file_url);
		}
		render("projectdeclar_detail.html");
	}

	// 删除项目
	public void delProject() {
		Integer id = getParaToInt();
		boolean result = Db.deleteById("t_project", id);
		renderJson(result);
	}

	// 保存项目
	public void saveProject() {
		boolean result = false;
		Integer id = getParaToInt("id");
		Record admin = getSessionAttr("admin");
		Record record = new Record();
		record.set("uploader", admin.getInt("id"));
		record.set("recipient", getPara("recipient"));
		record.set("project_name", getPara("project_name"));
		record.set("file_url", getPara("file_url"));
		if (id != null) {
			record.set("id", id);
			record.set("modify_time", new Date());
			result = Db.update("t_project", record);
		} else {
			record.set("create_time", new Date());
			record.set("modify_time", new Date());
			result = Db.save("t_project", record);
		}
		renderJson(result);
	}

	// 下载项目
	public void downloadProject() throws IOException {
		Integer id = getParaToInt();
		FileManageService.downloadProject(getResponse(), id);
		renderNull();
	}
}
