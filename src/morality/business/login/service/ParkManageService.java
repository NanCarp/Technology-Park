package morality.business.login.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ParkManageService {

	//员工管理
	public static Page<Record> getEmpList(Integer pageno, int pagesize){
		return Db.paginate(pageno, pagesize, "select e.name,e.empno,e.phone,d.department_name,p.position_name,e.status ", "from t_employee e left join t_department d on e.department_id = d.id left join t_position p on p.id = e.position");
	};
	//楼宇管理
	public static Page<Record> getBuilding(Integer pageno, int pagesize){
		return Db.paginate(pageno, pagesize, "select b.name,n.name as naturename,b.floor_no,u.building_NO,b.total_area,b.usable_area,b.status ","from t_building b LEFT JOIN t_building_nature n ON b.nature = n.id LEFT JOIN t_building_number u ON b.building_no = u.id");
	}
	//区域管理
	public static Page<Record> getArea(Integer pageno,int pagesize){
		return Db.paginate(pageno, pagesize, "select *", "from t_area");	
	}
	
	//获取职位
	public static List<Record> getPosition(){
		return Db.find("select id,position_name from t_position");
	}
}
