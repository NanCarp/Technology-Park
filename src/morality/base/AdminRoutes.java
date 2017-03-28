package morality.base;
import com.jfinal.config.Routes;
import morality.business.login.controller.FileManageController;
import morality.business.login.controller.DataDictionaryController;
import morality.business.login.controller.EnterpriseController;
import morality.business.login.controller.LoginController;
import morality.business.login.controller.ParkManageController;
import morality.business.login.controller.StatisticController;
import morality.business.login.controller.SystomController;

/**
* @ClassName: AdminRoutes
* @Description: 配置后端路由（供管理系统）
* @author: Yetangtang
* @date: 2017年2月27日 下午6:21:20
* @version: 1.0 版本初成
 */
public class AdminRoutes extends Routes{

	@Override
	public void config() {
		//设置页面base路径
		setBaseViewPath("/manage");
		//用户登录控制器
		add("/manage", LoginController.class, "/admin");
		
		//系统操作
		add("/systom", SystomController.class, "/systom");

		//数据字典
		add("/data", DataDictionaryController.class, "/data");
		
		//园区管理
		add("/parkmanage",ParkManageController.class,"/parkmanage");
		
		//企业管理
		add("/enterprise", EnterpriseController.class, "/enterprise");
		
		//文件管理
		add("/filemanage",FileManageController.class,"/filemanage");

		//查询统计
		add("/statistic",StatisticController.class,"/statistic");
	}
}
