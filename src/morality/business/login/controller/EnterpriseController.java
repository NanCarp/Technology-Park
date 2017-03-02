package morality.business.login.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import morality.util.interceptor.ManageInterceptor;

/**
 * @desc 企业管理
 * @author liyu
 */

@Before(ManageInterceptor.class)
public class EnterpriseController extends Controller {

	public void index() {
		render("");
	}

	// 入驻企业列表
	public void in() {
		render("in_list.html");
	}

	// 离驻企业列表
	public void leave() {
		render("leave_list.html");
	}

	// 企业经济情况列表
	public void economy() {
		render("economy_list.html");
	}

	// 企业从业人员列表
	public void employee() {
		render("employee_list.html");
	}

	// 企业知识产权列表
	public void ipr() {
		render("ipr_list.html");
	}
}
