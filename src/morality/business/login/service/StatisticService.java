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
	public static Page<Record> getBuildInfoList(Integer pageno, int pagesize, Integer fstate, Integer astate,
			String floorno, String areano) {
		String sqlExceptSelect = "FROM t_building a LEFT JOIN t_area b ON a.building_no = b.building_no left join t_building_number c on a.building_no = c.id where 1=1";
		if (fstate != null && fstate != 9) {
			sqlExceptSelect += " and a.status = " + fstate;
		}
		if (astate != null && astate != 9) {
			sqlExceptSelect += " and b.status = " + astate;
		}
		if (floorno != null && floorno != "") {
			sqlExceptSelect += " and a.floor_no = " + floorno;
		}
		if (areano != null && areano != "") {
			sqlExceptSelect += " and b.area_no = " + areano;
		}
		return Db.paginate(pageno, pagesize,
				"SELECT a.id,a.name,a.nature,a.floor_no,a.building_no,c.building_NO, a.total_area,a.usable_area,a.status AS bstate,b.area_name,b.direction,b.area_no,b.area,b.status AS astate ",
				sqlExceptSelect);
	}

	// 导出列表
	public static boolean getBuildInfoForExcel(HttpServletResponse response, int fstate, int astate, String floorno,
			String areano) {
		List<Record> list = getBuildInfoList(1, 100000000, fstate, astate, floorno, areano).getList();
		HSSFWorkbook wbook = new HSSFWorkbook();
		HSSFSheet sheet = wbook.createSheet();
		wbook.setSheetName(0, "楼区数据统计", (short) 1);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 8));
		// 设置列宽
		sheet.setColumnWidth((short) 0, (short) 3000);
		sheet.setColumnWidth((short) 1, (short) 3000);
		sheet.setColumnWidth((short) 2, (short) 5500);
		sheet.setColumnWidth((short) 3, (short) 3000);
		sheet.setColumnWidth((short) 4, (short) 8000);
		sheet.setColumnWidth((short) 5, (short) 3000);
		sheet.setColumnWidth((short) 6, (short) 3000);
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 8, (short) 4000);

		HSSFCellStyle cellStyle = wbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 20);// 设置字体大小
		cellStyle.setFont(font);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		HSSFCellStyle cellBorder = wbook.createCellStyle();
		cellBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		HSSFRow row;
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				row = sheet.createRow(i);
				HSSFCell cell0 = row.createCell((short) 0);
				cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue("楼区数据统计");
			}
			if (i == 1) {
				row = sheet.createRow(i);
				HSSFCell cell0 = row.createCell((short) 0);
				cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellStyle(cellBorder);
				cell0.setCellValue("楼号");

				HSSFCell cell1 = row.createCell((short) 1);
				cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell1.setCellStyle(cellBorder);
				cell1.setCellValue("层号");

				HSSFCell cell2 = row.createCell((short) 2);
				cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell2.setCellStyle(cellBorder);
				cell2.setCellValue("可用面积(平方米)");

				HSSFCell cell3 = row.createCell((short) 3);
				cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell3.setCellStyle(cellBorder);
				cell3.setCellValue("楼层状态");

				HSSFCell cell4 = row.createCell((short) 4);
				cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell4.setCellStyle(cellBorder);
				cell4.setCellValue("区域名");

				HSSFCell cell5 = row.createCell((short) 5);
				cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell5.setCellStyle(cellBorder);
				cell5.setCellValue("区域编号");

				HSSFCell cell6 = row.createCell((short) 6);
				cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell6.setCellStyle(cellBorder);
				cell6.setCellValue("朝向");

				HSSFCell cell7 = row.createCell((short) 7);
				cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell7.setCellStyle(cellBorder);
				cell7.setCellValue("面积(平方米)");

				HSSFCell cell8 = row.createCell((short) 8);
				cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell8.setCellStyle(cellBorder);
				cell8.setCellValue("区域状态");
			}
			row = sheet.createRow(i + 2);
			Record r = list.get(i);

			HSSFCell cell0 = row.createCell((short) 0);
			cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellStyle(cellBorder);
			cell0.setCellValue(r.getInt("floor_no"));

			HSSFCell cell1 = row.createCell((short) 1);
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellStyle(cellBorder);
			cell1.setCellValue(r.getStr("building_NO"));

			HSSFCell cell2 = row.createCell((short) 2);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellBorder);
			cell2.setCellValue(r.getInt("usable_area"));

			HSSFCell cell3 = row.createCell((short) 3);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellBorder);
			cell3.setCellValue(r.getInt("bstate") == 0 ? "未满" : "已满");

			HSSFCell cell4 = row.createCell((short) 4);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellBorder);
			cell4.setCellValue(r.getStr("area_name"));

			HSSFCell cell5 = row.createCell((short) 5);
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setCellStyle(cellBorder);
			cell5.setCellValue(r.getStr("area_no"));

			HSSFCell cell6 = row.createCell((short) 6);
			cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell6.setCellStyle(cellBorder);
			cell6.setCellValue(r.getStr("direction"));

			HSSFCell cell7 = row.createCell((short) 7);
			cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell7.setCellStyle(cellBorder);
			cell7.setCellValue(r.getInt("area"));

			HSSFCell cell8 = row.createCell((short) 8);
			cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell8.setCellStyle(cellBorder);
			cell8.setCellValue(r.getBoolean("astate") == true ? "已入驻" : "未入驻");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + EncordUtil.toUtf8String("楼区数据统计") + ".xls");
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
	public static Record getComInfoById(Integer id) {
		return Db.findFirst(
				"SELECT a.id, a.enterprise_name, a.industry,b.income,b.net_profit,b.taxation,b.investment,c.quantity,c.doctor,c.junior_college,c.returnees, "
						+ "c.thousand_talents_program,c.fresh_graduates,c.insurance,c.add_insurance,d.apply,d.approval,d.patent,d.copyright,d.software_product "
						+ "FROM t_enterprise_in a LEFT JOIN t_enterprise_economy b ON a.id = b.company_id LEFT JOIN t_practitioners c ON a.id = c.company_id "
						+ "LEFT JOIN t_property_right d ON a.id = d.company_id where a.id=?",
				id);
	}

	// 导出Word
	public static void excWord(HttpServletResponse response, HttpServletRequest request, Integer id)
			throws IOException {
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
			response.addHeader("Content-Disposition", "attachment; filename="
					+ EncordUtil.toUtf8String("企业数据总览-" + comInfo.getStr("enterprise_name")) + ".doc");
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			response.getOutputStream().flush();
			response.getOutputStream().close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*********************** 园区缴费情况管理 ************************/
	// 园区缴费情况总表分页
	public static Page<Record> getParkpayList(Integer pageno, int pageSize) {
		String sqlExceptSelect = " FROM t_payment ";
		String select = "SELECT * ";
		return Db.paginate(pageno, pageSize, select, sqlExceptSelect);
	}

	// 查询园区缴费情况列表
	public static Page<Record> getParkpayList(Integer pageno, Integer pagesize, String companyname, String year) {
		String select = "SELECT company_id, company_name,year, "
						+"SUM(CASE WHEN quarterly = '第一季度' THEN should_pay_rent ELSE 0 END) should_pay_rent1, "
						+"SUM(CASE WHEN quarterly = '第二季度' THEN should_pay_rent ELSE 0 END) should_pay_rent2, "
						+"SUM(CASE WHEN quarterly = '第三季度' THEN should_pay_rent ELSE 0 END) should_pay_rent3, "
						+"SUM(CASE WHEN quarterly = '第四季度' THEN should_pay_rent ELSE 0 END) should_pay_rent4, "
						+"SUM(CASE WHEN quarterly = '第一季度' THEN paid_rent ELSE 0 END) paid_rent1, "
						+"SUM(CASE WHEN quarterly = '第二季度' THEN paid_rent ELSE 0 END) paid_rent2, "
						+"SUM(CASE WHEN quarterly = '第三季度' THEN paid_rent ELSE 0 END) paid_rent3, "
						+"SUM(CASE WHEN quarterly = '第四季度' THEN paid_rent ELSE 0 END) paid_rent4, "
						+"SUM(CASE WHEN quarterly = '第一季度' THEN property_costs ELSE 0 END) property_costs1, "
						+"SUM(CASE WHEN quarterly = '第二季度' THEN property_costs ELSE 0 END) property_costs2, "
						+"SUM(CASE WHEN quarterly = '第三季度' THEN property_costs ELSE 0 END) property_costs3, "
						+"SUM(CASE WHEN quarterly = '第四季度' THEN property_costs ELSE 0 END) property_costs4, "
						+"SUM(CASE WHEN quarterly = '第一季度' THEN paid_property_charges ELSE 0 END) paid_property_charges1, "
						+"SUM(CASE WHEN quarterly = '第二季度' THEN paid_property_charges ELSE 0 END) paid_property_charges2, "
						+"SUM(CASE WHEN quarterly = '第三季度' THEN paid_property_charges ELSE 0 END) paid_property_charges3, "
						+"SUM(CASE WHEN quarterly = '第四季度' THEN paid_property_charges ELSE 0 END) paid_property_charges4, "
						+"SUM(CASE WHEN quarterly = '第一季度' THEN should_pay_water ELSE 0 END) should_pay_water1, "
						+"SUM(CASE WHEN quarterly = '第二季度' THEN should_pay_water ELSE 0 END) should_pay_water2, "
						+"SUM(CASE WHEN quarterly = '第三季度' THEN should_pay_water ELSE 0 END) should_pay_water3, "
						+"SUM(CASE WHEN quarterly = '第四季度' THEN should_pay_water ELSE 0 END) should_pay_water4, "
						+"SUM(CASE WHEN quarterly = '第一季度' THEN real_water_fee ELSE 0 END) real_water_fee1, "
						+"SUM(CASE WHEN quarterly = '第二季度' THEN real_water_fee ELSE 0 END) real_water_fee2, "
						+"SUM(CASE WHEN quarterly = '第三季度' THEN real_water_fee ELSE 0 END) real_water_fee3, "
						+"SUM(CASE WHEN quarterly = '第四季度' THEN real_water_fee ELSE 0 END) real_water_fee4 ";
//		String sqlExceptSelect = "FROM t_payment GROUP BY company_id, company_name HAVING 1=1 ";
		String sqlExceptSelect = " FROM t_payment WHERE 1=1 ";
		if ("" != companyname) {
			sqlExceptSelect += " AND company_name LIKE '%" + companyname + "%' ";
		}
		if ("" != year) {
			sqlExceptSelect += " AND year >= " + year;
		}
		sqlExceptSelect += " GROUP BY company_id, company_name ";
		
		return Db.paginate(pageno, pagesize, select, sqlExceptSelect);
	}

	// 导出列表
	public static boolean getPaymentForExcel(HttpServletResponse response, String companyname, String year) {

		List<Record> list = getParkpayList(1, 100000000, companyname, year).getList();
		HSSFWorkbook wbook = new HSSFWorkbook();
		HSSFSheet sheet = wbook.createSheet();
		wbook.setSheetName(0, "海安软件科技园缴费情况汇总表", (short) 1);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 8));
		// 设置列宽
		sheet.setColumnWidth((short) 0, (short) 3000);
		sheet.setColumnWidth((short) 1, (short) 3000);
		sheet.setColumnWidth((short) 2, (short) 5500);
		sheet.setColumnWidth((short) 3, (short) 3000);
		sheet.setColumnWidth((short) 4, (short) 8000);
		sheet.setColumnWidth((short) 5, (short) 3000);
		sheet.setColumnWidth((short) 6, (short) 3000);
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 9, (short) 4000);
		sheet.setColumnWidth((short) 10, (short) 4000);
		sheet.setColumnWidth((short) 11, (short) 4000);
		sheet.setColumnWidth((short) 12, (short) 4000);
		sheet.setColumnWidth((short) 13, (short) 4000);
		sheet.setColumnWidth((short) 14, (short) 4000);
		sheet.setColumnWidth((short) 15, (short) 4000);
		sheet.setColumnWidth((short) 16, (short) 4000);
		sheet.setColumnWidth((short) 17, (short) 4000);
		sheet.setColumnWidth((short) 18, (short) 4000);
		sheet.setColumnWidth((short) 19, (short) 4000);
		sheet.setColumnWidth((short) 20, (short) 4000);
		sheet.setColumnWidth((short) 21, (short) 4000);
		sheet.setColumnWidth((short) 22, (short) 4000);
		sheet.setColumnWidth((short) 23, (short) 4000);
		sheet.setColumnWidth((short) 24, (short) 4000);
		sheet.setColumnWidth((short) 25, (short) 4000);

		HSSFCellStyle cellStyle = wbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 20);// 设置字体大小
		cellStyle.setFont(font);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		HSSFCellStyle cellBorder = wbook.createCellStyle();
		cellBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		HSSFRow row;
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				row = sheet.createRow(i);
				HSSFCell cell0 = row.createCell((short) 0);
				cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue("海安软件科技园缴费情况汇总表");
			}
			if (i == 1) {
				row = sheet.createRow(i);
				HSSFCell cell0 = row.createCell((short) 0);
				cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellStyle(cellBorder);
				cell0.setCellValue("公司名");

				HSSFCell cell1 = row.createCell((short) 1);
				cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell1.setCellStyle(cellBorder);
				cell1.setCellValue("协议起始日期");

				HSSFCell cell2 = row.createCell((short) 2);
				cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell2.setCellStyle(cellBorder);
				cell2.setCellValue("房租应缴");

				HSSFCell cell3 = row.createCell((short) 3);
				cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell3.setCellStyle(cellBorder);
				cell3.setCellValue("房租实收");

				HSSFCell cell4 = row.createCell((short) 4);
				cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell4.setCellStyle(cellBorder);
				cell4.setCellValue("物业应缴");

				HSSFCell cell5 = row.createCell((short) 5);
				cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell5.setCellStyle(cellBorder);
				cell5.setCellValue("物业实缴");

				HSSFCell cell6 = row.createCell((short) 6);
				cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell6.setCellStyle(cellBorder);
				cell6.setCellValue("水费应缴");

				HSSFCell cell7 = row.createCell((short) 7);
				cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell7.setCellStyle(cellBorder);
				cell7.setCellValue("水费实缴)");
				
				HSSFCell cell8 = row.createCell((short) 8);
				cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell8.setCellStyle(cellBorder);
				cell8.setCellValue("房租应缴");
				
				HSSFCell cell9 = row.createCell((short) 9);
				cell9.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell9.setCellStyle(cellBorder);
				cell9.setCellValue("房租实收");
				
				HSSFCell cell10 = row.createCell((short) 10);
				cell10.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell10.setCellStyle(cellBorder);
				cell10.setCellValue("物业应缴");
				
				HSSFCell cell11 = row.createCell((short) 11);
				cell11.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell11.setCellStyle(cellBorder);
				cell11.setCellValue("物业实缴");
				
				HSSFCell cell12 = row.createCell((short) 12);
				cell12.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell12.setCellStyle(cellBorder);
				cell12.setCellValue("水费应缴");
				
				HSSFCell cell13 = row.createCell((short) 13);
				cell13.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell13.setCellStyle(cellBorder);
				cell13.setCellValue("水费实缴)");
				
				HSSFCell cell14 = row.createCell((short) 14);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellBorder);
				cell14.setCellValue("房租应缴");
				
				HSSFCell cell15 = row.createCell((short) 15);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellBorder);
				cell15.setCellValue("房租实收");
				
				HSSFCell cell16 = row.createCell((short) 16);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellBorder);
				cell16.setCellValue("物业应缴");
				
				HSSFCell cell17 = row.createCell((short) 17);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellBorder);
				cell17.setCellValue("物业实缴");
				
				HSSFCell cell18 = row.createCell((short) 18);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellBorder);
				cell18.setCellValue("水费应缴");
				
				HSSFCell cell19 = row.createCell((short) 19);
				cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell19.setCellStyle(cellBorder);
				cell19.setCellValue("水费实缴)");
				
				HSSFCell cell20 = row.createCell((short) 20);
				cell20.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell20.setCellStyle(cellBorder);
				cell20.setCellValue("房租应缴");
				
				HSSFCell cell21 = row.createCell((short) 21);
				cell21.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell21.setCellStyle(cellBorder);
				cell21.setCellValue("房租实收");
				
				HSSFCell cell22 = row.createCell((short) 22);
				cell22.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell22.setCellStyle(cellBorder);
				cell22.setCellValue("物业应缴");
				
				HSSFCell cell23 = row.createCell((short) 23);
				cell23.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell23.setCellStyle(cellBorder);
				cell23.setCellValue("物业实缴");
				
				HSSFCell cell24 = row.createCell((short) 24);
				cell24.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell24.setCellStyle(cellBorder);
				cell24.setCellValue("水费应缴");
				
				HSSFCell cell25 = row.createCell((short) 25);
				cell25.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell25.setCellStyle(cellBorder);
				cell25.setCellValue("水费实缴)");

			}
			row = sheet.createRow(i + 2);
			Record r = list.get(i);

			HSSFCell cell0 = row.createCell((short) 0);
			cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellStyle(cellBorder);
			cell0.setCellValue(r.getStr("company_name"));

			HSSFCell cell1 = row.createCell((short) 1);
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellStyle(cellBorder);
			cell1.setCellValue(r.getStr("year"));

			HSSFCell cell2 = row.createCell((short) 2);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellBorder);
			cell2.setCellValue(r.getBigDecimal("should_pay_rent").toString());

			HSSFCell cell3 = row.createCell((short) 3);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellBorder);
			cell3.setCellValue(r.getBigDecimal("paid_rent").toString());

			HSSFCell cell4 = row.createCell((short) 4);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellBorder);
			cell4.setCellValue(r.getBigDecimal("property_costs").toString());

			HSSFCell cell5 = row.createCell((short) 5);
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setCellStyle(cellBorder);
			cell5.setCellValue(r.getBigDecimal("paid_property_charges").toString());

			HSSFCell cell6 = row.createCell((short) 6);
			cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell6.setCellStyle(cellBorder);
			cell6.setCellValue(r.getBigDecimal("should_pay_water").toString());

			HSSFCell cell7 = row.createCell((short) 7);
			cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell7.setCellStyle(cellBorder);
			cell7.setCellValue(r.getBigDecimal("real_water_fee").toString());
			
			HSSFCell cell8 = row.createCell((short) 8);
			cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell8.setCellStyle(cellBorder);
			cell8.setCellValue(r.getBigDecimal("should_pay_rent").toString());
			
			HSSFCell cell9 = row.createCell((short) 9);
			cell9.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell9.setCellStyle(cellBorder);
			cell9.setCellValue(r.getBigDecimal("paid_rent").toString());
			
			HSSFCell cell10 = row.createCell((short) 10);
			cell10.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell10.setCellStyle(cellBorder);
			cell10.setCellValue(r.getBigDecimal("property_costs").toString());
			
			HSSFCell cell11 = row.createCell((short) 11);
			cell11.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell11.setCellStyle(cellBorder);
			cell11.setCellValue(r.getBigDecimal("paid_property_charges").toString());
			
			HSSFCell cell12 = row.createCell((short) 12);
			cell12.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell12.setCellStyle(cellBorder);
			cell12.setCellValue(r.getBigDecimal("should_pay_water").toString());
			
			HSSFCell cell13 = row.createCell((short) 13);
			cell13.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell13.setCellStyle(cellBorder);
			cell13.setCellValue(r.getBigDecimal("real_water_fee").toString());
			
			HSSFCell cell14 = row.createCell((short) 14);
			cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell14.setCellStyle(cellBorder);
			cell14.setCellValue(r.getBigDecimal("should_pay_rent").toString());
			
			HSSFCell cell15 = row.createCell((short) 15);
			cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell15.setCellStyle(cellBorder);
			cell15.setCellValue(r.getBigDecimal("paid_rent").toString());
			
			HSSFCell cell16 = row.createCell((short) 16);
			cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell16.setCellStyle(cellBorder);
			cell16.setCellValue(r.getBigDecimal("property_costs").toString());
			
			HSSFCell cell17 = row.createCell((short) 17);
			cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell17.setCellStyle(cellBorder);
			cell17.setCellValue(r.getBigDecimal("paid_property_charges").toString());
			
			HSSFCell cell18 = row.createCell((short) 18);
			cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell18.setCellStyle(cellBorder);
			cell18.setCellValue(r.getBigDecimal("should_pay_water").toString());
			
			HSSFCell cell19 = row.createCell((short) 19);
			cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell19.setCellStyle(cellBorder);
			cell19.setCellValue(r.getBigDecimal("real_water_fee").toString());
			
			HSSFCell cell20 = row.createCell((short) 20);
			cell20.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell20.setCellStyle(cellBorder);
			cell20.setCellValue(r.getBigDecimal("should_pay_rent").toString());
			
			HSSFCell cell21 = row.createCell((short) 21);
			cell21.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell21.setCellStyle(cellBorder);
			cell21.setCellValue(r.getBigDecimal("paid_rent").toString());
			
			HSSFCell cell22 = row.createCell((short) 22);
			cell22.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell22.setCellStyle(cellBorder);
			cell22.setCellValue(r.getBigDecimal("property_costs").toString());
			
			HSSFCell cell23 = row.createCell((short) 23);
			cell23.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell23.setCellStyle(cellBorder);
			cell23.setCellValue(r.getBigDecimal("paid_property_charges").toString());
			
			HSSFCell cell24 = row.createCell((short) 24);
			cell24.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell24.setCellStyle(cellBorder);
			cell24.setCellValue(r.getBigDecimal("should_pay_water").toString());
			
			HSSFCell cell25 = row.createCell((short) 25);
			cell25.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell25.setCellStyle(cellBorder);
			cell25.setCellValue(r.getBigDecimal("real_water_fee").toString());

		}
		response.addHeader("Content-Disposition", "attachment;filename=" + EncordUtil.toUtf8String("海安软件科技园缴费情况汇总表") + ".xls");
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

}
