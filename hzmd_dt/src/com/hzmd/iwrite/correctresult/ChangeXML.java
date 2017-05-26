package com.hzmd.iwrite.correctresult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.hzmd.iwrite.db.UseDbUtil;
import com.hzmd.iwrite.model.InformactionModel;
public class ChangeXML {
	//private static int BASIC_ID = 49864;
	private static int BASIC_ID = 1000000;
	static ArrayList<File> jj = new ArrayList<File>();
	public static void main(String[] args) {
		 try {
		      UseDbUtil.start();
		      
		      System.out.println("------华华丽丽的开始------");
		      fetchInformation();
		      System.out.println("-----华华丽丽的结束--------");
		    } finally {
		      UseDbUtil.stop();
		    }
		
	}
	/**
	 * 解析XML文件
	 * */
	public static void fetchInformation(){		
		File XmlFilePath = new File("c://////school_xml");
		jj = print(XmlFilePath);
		for (int i = 0; i < jj.size(); i++) {
			analyze(jj.get(i));
		}
	}
	/**
	 * JDOM解析XML文件
	 * */
	private static void analyze(File xmlFile) {
		SAXBuilder builder=new SAXBuilder(false);		
		try {
			Document doc=builder.build(xmlFile);
			Element iWriteCorpus=doc.getRootElement();
			List<Element> fileList = iWriteCorpus.getChildren("file");
			for (Element file : fileList) {
				String filePath = file.getAttributeValue("id");
				List<Element> head = file.getChildren("head");
				Element text = file.getChild("text");
				List<Element> p = text.getChildren("p");
				String userId = "";
				//InformactionModel.inft.set("userId", userId).save();
				String userName = "";
				String S_name = "";
				String gender = "";
				String phone = "";
				String email = "";
				int affiliation_1 = 1000;
				String school_at = "";
				String grade = "";
				String Class = "";
				int exam_type = -1;
				String Essay_translation = "";
				String prompt = "";
				String Instructions = "";
				String student_start = "";
				String sub_endTime = "";
				String exam_start = "";
				String exam_end = "";
				int total_points = 10;
				String txt = "";
				String words_count = "";
				String writing_time = "";
				String Essayid = "";
				String dataSource = "";
				int Textbook = -100;
				String Score = "";//人评分数
				String year = "";
				for (Element type : head) {
					 userId = type.getChildText("UID");
					//InformactionModel.inft.set("userId", userId).save();
					 userName = type.getChildText("SID");//用户ID
					 S_name = type.getChildText("S_name");//用户名
					 gender = type.getChildText("Gender");//真实姓名
					 phone = type.getChildText("Phone");//电话
					 email = type.getChildText("Email");//邮箱
					String affiliation = type.getChildText("Affiliation");//学校
					affiliation_1 =getAffiliation(affiliation);
					 school_at = type.getChildText("School_at_universtity");//院系
					 grade = type.getChildText("Grade");//年级
					 Class = type.getChildText("Class");//班级
					 String exam_type_1 = type.getChildText("Exam_type");//使用场景
					 exam_type = getExamType(exam_type_1);
					 Essay_translation = type.getChildText("Essay_translation");//语料类型
					 prompt = type.getChildText("Prompt");//作文标题
					 Instructions = type.getChildText("Instructions");//作文指令
					 student_start = type.getChildText("Student_start");//写作开始时间
					 sub_endTime = type.getChildText("Submission_time"); //写作结束时间
					 exam_start = type.getChildText("Exam_start");//考试开始时间
					 exam_end = type.getChildText("Exam_end");//考试结束时间
					 words_count = type.getChildText("Word_count");//作文词数
					 writing_time = type.getChildText("Writing_time_taken");//写作时间
					 total_points = getTotalPoints(type.getChildText("Total_points"));//Integer.parseInt(type.getChildText("Total_points"));//试题总分
					 Essayid = type.getChildText("Essayid");//作文ID
					 dataSource = type.getChildText("DataSource");//数据来源
					 Score = type.getChildText("Score");
					 year = getYear(exam_start,student_start);
				}				
			for (Element p_txt : p) {
				txt += p_txt.getValue()+"/n";
				txt = txt.substring(0, txt.length()-2);
			}	
			InformactionModel setXML = new InformactionModel();
			setXML.set("basic_Id",BASIC_ID).set("userId", userId).set("userName", userName)
			.set("s_name", S_name).set("genDer", gender).set("phone", phone).set("email", email).set("school", affiliation_1)
			.set("school_at", school_at).set("grade", grade).set("cls", Class).set("exam_type", exam_type).set("essay_traslation", Essay_translation)
			.set("prompt", prompt).set("instructions", Instructions).set("student_start", student_start).set("submission_time", sub_endTime)
			.set("text", txt).set("exam_start", exam_start).set("exam_end", exam_end).set("words_count", words_count).set("writing_time", writing_time)
			.set("total_points", total_points).set("essayid", Essayid).set("dataSource", dataSource).set("textBook", Textbook)
			.set("score", Score).set("year", year).set("filePath", filePath).save()
			;//.set("text", txt)
			BASIC_ID++;
			System.out.println("-----------");
			}
		} catch (JDOMException e) {
			System.out.println("----这里出错了---");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("----那里出错了---");
			e.printStackTrace();
		}
	}
	private static int getExamType(String exam_type_1) {
		if("学校考试".equals(exam_type_1)){
			return 132;
		}
		if("班级测试".equals(exam_type_1)){
			return 133;
		}
		return -1;
	}
	private static int getAffiliation(String affiliation) {
		if("新疆石河子大学".equals(affiliation)){
			return 22;
		}
		if("西南交通大学".equals(affiliation)){
			return 80;
		}
		if("昆明理工大学".equals(affiliation)){
			return 96;
		}
		if("河南工业大学".equals(affiliation)){
			return 97;
		}
		if("桂林理工大学".equals(affiliation)){
			return 57;
			}
		return 1000;
	}
	/**
	 * 根据考试开始时间获取作文年份
	 * */
	private static String getYear(String exam_start,String stu_start) {
		String year = "";
		if(exam_start != null && !"".equals(exam_start)){
			year = exam_start.substring(0, 4);
		}else if(!"".equals(stu_start) && stu_start !=null){
			year = stu_start.substring(0, 4);
		}else{
		if("".equals(year)){
			year = "未知年份";
		}}
		return year;
	}
	/**
	 * 获取作文总分，没有则为10.
	 * */
	private static int getTotalPoints(String childText) {
		int total_points = 10;
		if(childText !=null && !"".equals(childText)){
			if(childText.contains(".")){
			int index = childText.indexOf(".");
			System.out.println(childText);
			childText = childText.substring(0, index);
			total_points = Integer.parseInt(childText);
			}else{
				total_points = Integer.parseInt(childText);
			}
		}
		return total_points;
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
