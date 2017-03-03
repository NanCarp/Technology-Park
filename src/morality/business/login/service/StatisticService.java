package morality.business.login.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
* @desc 查询统计
* @author yangbo
*/
public class StatisticService {

	// 获得楼区数据统计列表
	public static Page<Record> getBuildInfoList(Integer pageno, int pagesize, Integer fstate, Integer astate, String floorno, String areano) {
		String sqlExceptSelect = "FROM t_building a LEFT JOIN t_area b ON a.building_no = b.building_no where 1=1";
		if(fstate != null && fstate != 9){
			sqlExceptSelect += " and a.status = "+fstate;
		}
		if(astate != null && astate != 9){
			sqlExceptSelect += " and b.status = "+astate;
		}
		if(floorno != null && floorno != ""){
			sqlExceptSelect += " and a.floor_no = "+floorno;
		}
		if(areano != null && areano != ""){
			sqlExceptSelect += " and b.area_no = "+areano;
		}
		return Db.paginate(pageno, pagesize, "SELECT a.id,a.name,a.nature,a.floor_no,a.building_no, a.total_area,a.usable_area,a.status AS bstate,b.area_name,b.direction,b.area_no,b.area,b.status AS astate ", sqlExceptSelect);
	}

}
