package com.hzmd.iwrite.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hzmd.iwrite.model.ErrorSumModel;
import com.hzmd.iwrite.model.InformactionModel;
import com.hzmd.iwrite.model.QueryInformationModel;
import com.jfinal.core.Controller;

public class ErrorStatisticsController extends Controller{
	public static String _cp = "";
	public void index(){
		setCp();
		List<QueryInformationModel> schoolList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'school'");
		setAttr("schoolList", schoolList);
		List<QueryInformationModel> yearList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'year'");
		setAttr("yearList", yearList);
		List<QueryInformationModel> examTypeList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'exam_type'");
		setAttr("examTypeList", examTypeList);
		List<QueryInformationModel> bookList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'book'");
		setAttr("bookList", bookList);
		int size = InformactionModel.inft._findFirst("SELECT SUM(words_count) FROM basic_information ")._get_int("SUM(words_count)");
		setAttr("Size", size);
		this.render("/error/home_tongji.ftl");
	}
	public void errorStatistics(){
		setCp();
		String schoolName = getParam("schoolName");
		String book = getParam("book");
		String examType = getParam("examType");
		String yearList = getParam("yearList");
		String score = getParam("score");
		String SQL = "SELECT error_type,COUNT(*) error_count FROM error_sum WHERE  1=1 ";		
		String errorSQL = "SELECT error,COUNT(*) errorsum FROM error_sum WHERE 1=1 ";
		if(!"".equals(schoolName) && schoolName !=null){
			schoolName = splitWord(schoolName);
			String sql = " AND school in ("+schoolName+")";
			SQL += sql;	
			errorSQL += sql;
		}
		if(!"".equals(score) && score !=null){
			score = splitScore(score);
			String sql = " AND ("+score+")";
			SQL += sql;	
			errorSQL += sql;
		}
		if(!"".equals(book) && book !=null){
			book = splitWord(book);
			String sql = "AND textBook in ("+book+")";
			SQL += sql;	
			errorSQL += sql;
		}
		if(!"".equals(examType) && examType !=null){
			examType = splitWord(examType);
			String sql = " AND exam_type in ("+examType+")";
			SQL += sql;	
			errorSQL += sql;
		}
		if(!"".equals(yearList) && yearList !=null){
			yearList = splitWord(yearList);
			String sql = " AND year in ("+yearList+")";
			SQL += sql;	
			errorSQL += sql;
		}
		String sql = " GROUP BY error_type";	
		SQL += sql;		
		List<ErrorSumModel> errorTypeList_1 = ErrorSumModel._es._find(SQL);
		Map<Integer, ErrorSumModel> map = new HashMap<Integer, ErrorSumModel>();
		for (int i = 0; i < errorTypeList_1.size(); i++) {
			ErrorSumModel im = errorTypeList_1.get(i);
			int error_type = im._get_int("error_type");
			
			/*if("词法类".equals(error_type)){
				map.put(1,im);
			}else if("句法类".equals(error_type)){
				map.put(2, im);
			}else if("搭配类".equals(error_type)){
				map.put(3, im);
			}else if("技术规范类".equals(error_type)){
				map.put(4, im);
			}else if("其它错误".equals(error_type)){
				map.put(5, im);
			}*/
			if(error_type == 141){
				//词法类
				map.put(1,im);
			}else if(error_type == 140){
				//句法类
				map.put(2, im);
			}else if(error_type == 142){
				//搭配类
				map.put(3, im);
			}else if(error_type == 143){
				//技术规范类
				map.put(4, im);
			}else if(error_type == 139){
				//其它错误
				map.put(5, im);
			}
		}
		List<ErrorSumModel> errorTypeList = new ArrayList<>();
		for (int i = 1; i < 6; i++) {
			ErrorSumModel im = map.get(i);
			if(im != null){
			errorTypeList.add(map.get(i));
			}
		}
		for (int i = 0; i < errorTypeList.size(); i++) {
			ErrorSumModel im = errorTypeList.get(i);
			if(im != null){
			String error_type = im._get_String("error_type");
			String erorsql = "";
			String Esql = " AND error_type= "+error_type+ " GROUP BY error";
			erorsql = errorSQL + Esql;
			List<ErrorSumModel> errorList = ErrorSumModel._es._find(erorsql);
			im.put("errorList", errorList);
			}
		}
		setAttr("errorTypeList", errorTypeList);
		this.render("/error/tongji_2.ftl");
	}
	public void errorDetails(){
		setCp();
		String schoolName = getParam("schoolName");
		String book = getParam("book");
		String examType = getParam("examType");
		String yearList = getParam("yearList");	
		String error = getParam("type");
		int page = getParam_int("page",1);
		String score = getParam("score");
		setAttr("type", error);
		String SQL = "SELECT error_sentence,error_word FROM error_sum  WHERE 1=1";
		if(!"".equals(schoolName) && schoolName !=null){
			schoolName = splitWord(schoolName);
			String sql = " AND school in ("+schoolName+")";
			SQL += sql;	
		}
		if(!"".equals(score) && score !=null){
			score = splitScore(score);
			String sql = " AND ("+score+")";
			SQL += sql;	
		}
		if(!"".equals(error) && error !=null){
			error = splitWord(error);
			String sql = "AND error in ("+error+")";
			SQL += sql;	
		}
		if(!"".equals(book) && book !=null){
			book = splitWord(book);
			String sql = "AND textBook in ("+book+")";
			SQL += sql;	
		}
		if(!"".equals(examType) && examType !=null){
			examType = splitWord(examType);
			String sql = " AND exam_type in ("+examType+")";
			SQL += sql;	
		}
		if(!"".equals(yearList) && yearList !=null){
			yearList = splitWord(yearList);
			String sql = " AND year in ("+yearList+")";
			SQL += sql;	
		}
		List<ErrorSumModel> sentencePage = ErrorSumModel._es._find(SQL);
		int pageNumbae = sentencePage.size();
		pageNumbae = getPageNumber(pageNumbae);
		setAttr("page", pageNumbae);
		SQL += " Limit "+(page+-1)*10+",10";
		List<ErrorSumModel> errorList = ErrorSumModel._es._find(SQL);
		for (ErrorSumModel im : errorList) {
			String str = im._get_String("error_sentence");
			String word = im._get_String("error_word").trim();
			//str = str.replaceAll("(?i)"+word, "<span class=\"font-color\">"+word+"</span>");
			/*String[] sd = str.split(" ");
			String sentence="";
			for (int i = 0; i < sd.length; i++) {
				if((word).contains(sd[i])){
					String dd = sd[i];
					sd[i] = sd[i].replaceAll(sd[i], "<span class=\"font-color\">"+sd[i]+"</span>");
				}
				sentence += sd[i]+" "; 
			}*/
			str = str.replaceAll(word, "<span class=\"font-color\">"+word+"</span>");
			im.put("error_sentence",str);
			//im.put("error_sentence",str);
	}
		setAttr("errorList", errorList);
		this.render("/error/tongji_3.ftl");
	}
	private static String splitScore(String score) {
		String sql = "";
		String[] scoreStr = score.split(",");
		for (String str : scoreStr) {
			String[] sst = str.split("-");
				sql += " (total_score <"+sst[0]+" AND total_score>"+sst[1]+") OR";				
		}
		sql = sql.substring(0, sql.length()-2);
		return sql;
	}
	public static String splitWord( String str){
		String[] word = str.split(",");
		String sql = "";
		for (int i = 0; i < word.length; i++) {
			word[i] = word[i] +",";
			if(i==word.length-1){
				word[i] = word[i].substring(0, word[i].length()-1);
			}
			sql+=word[i];
		}
		return sql;
	}
	private void setCp() {
		_cp = getRequest().getContextPath();
		this.setAttr("_cp", _cp);
		this.setAttr("_com", this);
	}
	private int getPageNumber(int pageNumbae) {
		int page = pageNumbae/10+1;
		return page;
	}
	
	 public String convertToName(Object id){
		 String reName = id + "";
		 try{
			 return QueryInformationModel.dao._findById(id)._get_String("name");
		 }catch(Exception ex){
			 
		 }
		 return reName;
	 }
}
