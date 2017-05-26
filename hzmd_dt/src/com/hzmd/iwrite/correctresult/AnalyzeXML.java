package com.hzmd.iwrite.correctresult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.hzmd.iwrite.model.InformactionModel;

public class AnalyzeXML {
	static int f = 1;
	static ArrayList<File> jj = new ArrayList<File>();
	static FileOutputStream output = null;
	public static void main(String[] args) {
		Map<HSSFWorkbook, HSSFSheet> map = DeriveXLS.writeExcel();
		fetchInformation(map);
		System.out.println("-----华丽的结束-----");
	}
	/**
	 * 解析XML文件
	 * @param wb 
	 * */
	public static void fetchInformation(Map<HSSFWorkbook, HSSFSheet> map){		
		File XmlFilePath = new File("d:/xml_1");
		jj = print(XmlFilePath);
		for (int i = 0; i < jj.size(); i++) {
			analyze(jj.get(i),map);
		}
	}
	/**
	 * JDOM解析XML文件
	 * @param wb 
	 * */
	private static void analyze(File xmlFile, Map<HSSFWorkbook, HSSFSheet> map) {
		SAXBuilder builder=new SAXBuilder(false);		
		try {		
			String gender = "";
			String affiliation = "";
			String school_at = "";
			String grade = "";
			String exam_type = "";
			String Essay_translation = "";
			String prompt = "";
			String words_count = "";
			String Textbook = "";
			String filePath ="";
			String total_score = "";
			String lang_score = "";
			String cont_score = "";
			String orgn_score = "";
			String mech_score = "";
			Document doc=builder.build(xmlFile);
			Element iWriteCorpus=doc.getRootElement();
			List<Element> fileList = iWriteCorpus.getChildren("file");
			for (Element file : fileList) {
				 filePath = file.getAttributeValue("id");
				List<Element> head = file.getChildren("head");				
				for (Element type : head) {
					 gender = type.getChildText("Gender");
					 affiliation = type.getChildText("Affiliation");//学校
					 school_at = type.getChildText("School_at_universtity");//院系
					 grade = type.getChildText("Grade");//年级
					 exam_type = type.getChildText("Exam_type");//使用场景
					 Essay_translation = type.getChildText("Essay_translation");//语料类型
					 prompt = type.getChildText("Prompt");//作文标题
					 words_count = type.getChildText("Word_count");//作文词数
					 Textbook = type.getChildText("Textbook");//所用教材
					 Element score = type.getChild("E-score");
					 total_score = score.getChildText("Total_score");
					 lang_score = score.getChildText("Lang_score");
					 cont_score = score.getChildText("Cont_score");
					 orgn_score = score.getChildText("Orgn_score");
					 mech_score = score.getChildText("Mech_score");
				}				
			
		ArrayList<String> xml = new ArrayList<>();	
		xml.add(filePath);
		xml.add(gender);
		xml.add(affiliation);
		xml.add(school_at);
		xml.add(grade);
		xml.add(exam_type);
		xml.add(Essay_translation);
		xml.add(prompt);
		xml.add(total_score);
		xml.add(lang_score);
		xml.add(cont_score);
		xml.add(orgn_score);
		xml.add(mech_score);
		xml.add(Textbook);
		xml.add(words_count);
		HSSFWorkbook wb =null;
		HSSFSheet sheel =null;
		for(Entry<HSSFWorkbook, HSSFSheet> vo : map.entrySet()){ 
			 wb = vo.getKey(); 
			sheel = vo.getValue(); 
			}
		HSSFRow row=sheel.createRow(f); 
		for (int i = 0; i < xml.size(); i++) {						
				row.createCell(i).setCellValue(xml.get(i));			
			}					
		f++;
		System.out.println(f);
		try {	
			FileOutputStream output = new FileOutputStream("d:/iWrite_Corpus_index.xls");
				wb.write(output);
				output.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		
		} catch (JDOMException e) {
			System.out.println("----这里出错了---");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("----那里出错了---");
			e.printStackTrace();
		}
	}
	/**
	 * 返回文件夹中所有文件
	 * */
public static ArrayList<File> print(File path) {		
		if (path != null) {
			if (path.isDirectory()) { // 判断是否为一个目录
				File[] fileArray = path.listFiles(); // 返回目录下的所有文件
				if (fileArray != null) {
					for (int i = 0; i < fileArray.length; i++) {
						// 递归调用
						print(fileArray[i]);						
					}
				}
			} else {				
					jj.add(path);									
			  }
		}
		return jj;		
	}
}
