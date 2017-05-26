package com.hzmd.iwrite.correctresult;


import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

public class DeriveXLS {
	public static void main(String[] args) {
		writeExcel();
	}
	public static Map<HSSFWorkbook, HSSFSheet> writeExcel(){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("iWrite_Corpus_index");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue("File_id");
		HSSFCell cell1 = row1.createCell(1);
		cell1.setCellValue("Gender");
		HSSFCell cell2 = row1.createCell(2);
		cell2.setCellValue("Affiliation");
		HSSFCell cell3 = row1.createCell(3);
		cell3.setCellValue("School_at_universtity");
		HSSFCell cell4 = row1.createCell(4);
		cell4.setCellValue("Grade");
		HSSFCell cell5 = row1.createCell(5);
		cell5.setCellValue("Exam_type");
		HSSFCell cell6 = row1.createCell(6);
		cell6.setCellValue("Essay_translation");
		HSSFCell cell7 = row1.createCell(7);
		cell7.setCellValue("Prompt");
		HSSFCell cell8 = row1.createCell(8);
		cell8.setCellValue("Total_score");
		HSSFCell cell9 = row1.createCell(9);
		cell9.setCellValue("Lang_score");
		HSSFCell cell10 = row1.createCell(10);
		cell10.setCellValue("Cont_score");
		HSSFCell cell11 = row1.createCell(11);
		cell11.setCellValue("Orgn_score");
		HSSFCell cell12 = row1.createCell(12);
		cell12.setCellValue("Mech_score");
		HSSFCell cell13 = row1.createCell(13);
		cell13.setCellValue("Textbook");
		HSSFCell cell14 = row1.createCell(14);
		cell14.setCellValue("Word_count");
		Map<HSSFWorkbook, HSSFSheet> jj = new HashMap<>();
		jj.put(wb, sheet);
		return jj;
		/*try {
			FileOutputStream output = new FileOutputStream("d:/iWrite_Corpus_index.xls");			
				wb.write(output);
				output.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
