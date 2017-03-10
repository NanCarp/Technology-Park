package morality.business.login.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import morality.util.tool.DocUtil;
import morality.util.tool.EncordUtil;

/**
* @desc 查询统计
* @author yangbo
*/
public class StatisticService {

	// 获得楼区数据统计列表
	public static Page<Record> getBuildInfoList(Integer pageno, int pagesize, Integer fstate, Integer astate, String floorno, String areano) {
		String sqlExceptSelect = "FROM t_building a LEFT JOIN t_area b ON a.building_no = b.building_no left join t_building_number c on a.building_no = c.id where 1=1";
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
		return Db.paginate(pageno, pagesize, "SELECT a.id,a.name,a.nature,a.floor_no,a.building_no,c.building_NO, a.total_area,a.usable_area,a.status AS bstate,b.area_name,b.direction,b.area_no,b.area,b.status AS astate ", sqlExceptSelect);
	}

	// 导出列表
	public static boolean getBuildInfoForExcel(HttpServletResponse response, int fstate, int astate, String floorno, String areano) {
		List<Record> list = getBuildInfoList(1, 100000000, fstate, astate, floorno, areano).getList();
		HSSFWorkbook wbook = new HSSFWorkbook();
		HSSFSheet sheet = wbook.createSheet();
		wbook.setSheetName(0, "楼区数据统计", (short)1);
	    sheet.addMergedRegion(new Region(0, (short)0, 0, (short)8));   
		//设置列宽
		sheet.setColumnWidth((short)0, (short)3000);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)2, (short)5500);
		sheet.setColumnWidth((short)3, (short)3000);
		sheet.setColumnWidth((short)4, (short)8000);
		sheet.setColumnWidth((short)5, (short)3000);
		sheet.setColumnWidth((short)6, (short)3000);
		sheet.setColumnWidth((short)7, (short)4000);
		sheet.setColumnWidth((short)8, (short)4000);
		
		
        HSSFCellStyle cellStyle = wbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = wbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 20);//设置字体大小    
        cellStyle.setFont(font);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        
        HSSFCellStyle cellBorder = wbook.createCellStyle();
        cellBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        cellBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        cellBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        cellBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
		
		HSSFRow row;
		for(int i=0; i<list.size(); i++){
			if(i==0){
				row = sheet.createRow(i);
				HSSFCell cell0 = row.createCell((short)0);
				cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue("楼区数据统计");
			}
			if(i==1){
				row = sheet.createRow(i);
				HSSFCell cell0 = row.createCell((short)0);
				cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellStyle(cellBorder);
				cell0.setCellValue("楼号");
				
				HSSFCell cell1 = row.createCell((short)1);
				cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell1.setCellStyle(cellBorder);
				cell1.setCellValue("层号");
				
				HSSFCell cell2 = row.createCell((short)2);
				cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell2.setCellStyle(cellBorder);
				cell2.setCellValue("可用面积(平方米)");
				
				HSSFCell cell3 = row.createCell((short)3);
				cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell3.setCellStyle(cellBorder);
				cell3.setCellValue("楼层状态");
				
				HSSFCell cell4 = row.createCell((short)4);
				cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell4.setCellStyle(cellBorder);
				cell4.setCellValue("区域名");
				
				HSSFCell cell5 = row.createCell((short)5);
				cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell5.setCellStyle(cellBorder);
				cell5.setCellValue("区域编号");
				
				HSSFCell cell6 = row.createCell((short)6);
				cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell6.setCellStyle(cellBorder);
				cell6.setCellValue("朝向");
				
				HSSFCell cell7 = row.createCell((short)7);
				cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell7.setCellStyle(cellBorder);
				cell7.setCellValue("面积(平方米)");
				
				HSSFCell cell8 = row.createCell((short)8);
				cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell8.setCellStyle(cellBorder);
				cell8.setCellValue("区域状态");
			}
			row = sheet.createRow(i+1);
			Record r = list.get(i);
			
			HSSFCell cell0 = row.createCell((short)0);
			cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellStyle(cellBorder);
			cell0.setCellValue(r.getInt("floor_no"));
			
			HSSFCell cell1 = row.createCell((short)1);
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellStyle(cellBorder);
			cell1.setCellValue(r.getStr("building_NO"));
			
			HSSFCell cell2 = row.createCell((short)2);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellBorder);
			cell2.setCellValue(r.getInt("usable_area"));
			
			HSSFCell cell3 = row.createCell((short)3);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellBorder);
			cell3.setCellValue(r.getInt("bstate")==0?"未满":"已满");
			
			HSSFCell cell4 = row.createCell((short)4);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellBorder);
			cell4.setCellValue(r.getStr("area_name"));
			
			HSSFCell cell5 = row.createCell((short)5);
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setCellStyle(cellBorder);
			cell5.setCellValue(r.getStr("area_no"));
			
			HSSFCell cell6 = row.createCell((short)6);
			cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell6.setCellStyle(cellBorder);
			cell6.setCellValue(r.getStr("direction"));
			
			HSSFCell cell7 = row.createCell((short)7);
			cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell7.setCellStyle(cellBorder);
			cell7.setCellValue(r.getInt("area"));
			
			HSSFCell cell8 = row.createCell((short)8);
			cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell8.setCellStyle(cellBorder);
			cell8.setCellValue(r.getBoolean("astate")==true?"已入驻":"未入驻");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + EncordUtil.toUtf8String("楼区数据统计")+ ".xls");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			ServletOutputStream out = response.getOutputStream();
			wbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 获得公司信息
	public static Page<Record> getCompanyInfoList(Integer pageno, int pageSize) {
		String sqlExceptSelect = " FROM t_enterprise_in a LEFT JOIN t_enterprise_economy b ON a.id = b.company_id LEFT JOIN t_practitioners c ON a.id = c.company_id "
				+ "LEFT JOIN t_property_right d ON a.id = d.company_id";
		String select = "SELECT a.id, a.enterprise_name, a.industry,b.income,b.net_profit,b.taxation,b.investment,c.quantity,c.doctor,c.junior_college,c.returnees,"
				+ "c.thousand_talents_program,c.fresh_graduates,c.insurance,c.add_insurance,d.apply,d.approval,d.patent,d.copyright,d.software_product";
		return Db.paginate(pageno, pageSize, select, sqlExceptSelect);
	}

	// 根据ID获得公司信息
	public static Record getComInfoById(Integer id){
		return Db.findFirst("SELECT a.id, a.enterprise_name, a.industry,b.income,b.net_profit,b.taxation,b.investment,c.quantity,c.doctor,c.junior_college,c.returnees, "
				+ "c.thousand_talents_program,c.fresh_graduates,c.insurance,c.add_insurance,d.apply,d.approval,d.patent,d.copyright,d.software_product "
				+ "FROM t_enterprise_in a LEFT JOIN t_enterprise_economy b ON a.id = b.company_id LEFT JOIN t_practitioners c ON a.id = c.company_id "
				+ "LEFT JOIN t_property_right d ON a.id = d.company_id where a.id=?", id);
	}

	// 导出Word
    public static void excWord(HttpServletResponse response, HttpServletRequest request, Integer id) throws IOException{
    	Map<String, Object> dataMap = new HashMap<>();
    	Record comInfo = getComInfoById(id);
    	dataMap.put("name", comInfo.getStr("enterprise_name"));
    	dataMap.put("income", comInfo.getBigDecimal("income"));
    	dataMap.put("np", comInfo.getBigDecimal("net_profit"));
    	dataMap.put("taxa", comInfo.getBigDecimal("taxation"));
    	dataMap.put("inv", comInfo.getBigDecimal("investment"));
    	dataMap.put("qua", comInfo.getLong("quantity"));
    	dataMap.put("doctor", comInfo.getInt("doctor"));
    	dataMap.put("jc", comInfo.getInt("junior_college"));
    	dataMap.put("returnees", comInfo.getInt("returnees"));
    	dataMap.put("ttp", comInfo.getInt("thousand_talents_program"));
    	dataMap.put("fg", comInfo.getInt("fresh_graduates"));
    	dataMap.put("ins", comInfo.getInt("insurance"));
    	dataMap.put("ai", comInfo.getInt("add_insurance"));
    	dataMap.put("apply", comInfo.getInt("apply"));
    	dataMap.put("approval", comInfo.getInt("approval"));
    	dataMap.put("patent", comInfo.getInt("patent"));
    	dataMap.put("copyright", comInfo.getInt("copyright"));
    	dataMap.put("sp", comInfo.getInt("software_product"));
    	DocUtil.toPreview(request, DocUtil.WORD_TEMPLATE, dataMap);
	    try {
    	    File previewFile = new File(request.getSession().getServletContext().getRealPath(DocUtil.PREVIEW_DOC));   
	        InputStream is = new FileInputStream(previewFile);
	        response.reset();
	        response.setContentType("application/vnd.ms-word;charset=UTF-8");
	        response.addHeader("Content-Disposition","attachment; filename=" + EncordUtil.toUtf8String("企业数据总览-"+comInfo.getStr("enterprise_name"))+ ".doc");
	        byte[] b = new byte[1024];
	        int len;
	        while ((len=is.read(b)) >0) {
	        	response.getOutputStream().write(b,0,len);
	        }
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	        is.close();
	    } catch (Exception e) {
	       	e.printStackTrace();
	    }
	}
    
    public static Page<Record> getParkpayList(Integer pageno, int pageSize) {
    	return null;
    }
}
