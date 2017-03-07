package morality.business.login.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ParkManageService {

	//员工管理
	public static Page<Record> getEmpList(Integer pageno, int pagesize){
		return Db.paginate(pageno, pagesize, "select e.id,e.name,e.empno,e.phone,d.department_name,p.position_name,e.status ", "from t_employee e left join t_department d on e.department_id = d.id left join t_position p on p.id = e.position");
	};
	//楼宇管理
	public static Page<Record> getBuilding(Integer pageno, int pagesize){
		return Db.paginate(pageno, pagesize, "select b.id,b.name,n.name as naturename,b.floor_no,u.sort_id,b.total_area,b.usable_area,b.status ","from t_building b LEFT JOIN t_building_nature n ON b.nature = n.id LEFT JOIN t_building_number u ON b.building_no = u.id");
	}
	//区域管理
	public static Page<Record> getArea(Integer pageno,int pagesize){
		return Db.paginate(pageno, pagesize, "select *", "from t_area");	
	}
	//安全管理检查记录
	public static Page<Record> getSafeInspect(Integer pageno,int pagesize){
		return Db.paginate(pageno, pagesize, "select *","from t_safety_inspection");
	}
	//园区安全责任书签订
	public static Page<Record> getSafeProtocols(Integer pageno,int pagesize){
		return Db.paginate(pageno, pagesize, "select *", "from t_security_protocols");
	}
	
	//查询缴费记录
	public static Page<Record> getPayment(Integer pageno,int pagesize){
		return Db.paginate(pageno, pagesize, "select * ", "from t_payment");	
	}
	//查询通知公告记录
	public static Page<Record> getNotice(Integer pageno,int pagesize){
		return Db.paginate(pageno, pagesize, "select *", "from t_notice");
	}
	
	//根据员工号查询单个员工
	public static Record getEmp(int id){
		return Db.findById("t_employee", id);
	}
	//根据id查询单个楼宇信息
	public static Record getBuildingMessage(int id){	
		return Db.findFirst("select select b.id,b.name,n.name as nature,b.floor_no,u.building_NO,b.total_area,b.usable_area,b.status from t_building b LEFT JOIN t_building_nature n ON n.id = b.nature LEFT JOIN t_building_number u on b.building_no = u.id where b.id = ?",id);
	}
	//获取职位
	public static List<Record> getPosition(){
		return Db.find("select id,position_name from t_position");
	}

}
