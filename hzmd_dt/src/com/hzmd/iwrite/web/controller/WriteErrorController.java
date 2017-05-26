package com.hzmd.iwrite.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.hzmd.common.bean.ReturnJsonObject;
import com.hzmd.iwrite.model.ErrorSumModel;
import com.hzmd.iwrite.model.InformactionModel;
import com.hzmd.iwrite.model.QueryInformationModel;
import com.hzmd.iwrite.model.WordModel;
import com.jfinal.core.Controller;
public class WriteErrorController extends Controller{
	static HashMap<String,ArrayList<String> > wordMap = new HashMap<String,ArrayList<String> >(); 
	public static String _cp = "";
	public void index(){
		setCp();
		List<QueryInformationModel> schoolList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'school'");
		setAttr("schoolList", schoolList);
		List<QueryInformationModel> yearList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type='year'");
		setAttr("yearList", yearList);
		List<QueryInformationModel> examTypeList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'exam_type'");
		setAttr("examTypeList", examTypeList);
		List<QueryInformationModel> bookList = QueryInformationModel.dao._find("SELECT * FROM query_information WHERE type = 'book' ");
		setAttr("bookList", bookList);
		int size = InformactionModel.inft._findFirst("SELECT SUM(words_count) FROM basic_information ")._get_int("SUM(words_count)");
		setAttr("Size", size);
		this.render("/error/home.ftl");
	}
	public void queryResult(){
		setCp();
		String word = getParam("errorWord");
		String schoolName = getParam("schoolName");
		String book = getParam("book");
		String examType = getParam("examType");
		String yearList = getParam("yearList");
		String score = getParam("score");
		String SQL = "SELECT school,COUNT(*) count FROM error_sum WHERE 1=1 ";
		String EXAM = "SELECT exam_type,COUNT(*) exam FROM error_sum WHERE 1=1 " ;
		String YEAR = "SELECT year,COUNT(*) yearCount FROM error_sum WHERE 1=1 ";
		String BOOK = "SELECT textBook,COUNT(*) bookCount FROM error_sum WHERE 1=1 ";
		String SCORE = "SELECT total_score,COUNT(*) scoreCount FROM error_sum WHERE 1=1 ";
		//GROUP BY (total_score>-1 and total_score<50),(total_score>49 and total_score<60),(total_score>59 and total_score<70),(total_score>69 and total_score<80),(total_score>79 and total_score<90),(total_score>89 and total_score<101)
		if(!"".equals(word) && word !=null){
			String likeSql = getSql(word);
			String sql = " AND error != 170 AND error !=  174 AND error != 146 "+likeSql;
			//String sql = likeSql;
			SQL += sql;
			EXAM += sql;
			YEAR += sql;
			BOOK += sql;
			SCORE += sql;
		}
		if(!"".equals(score) && score !=null){
			score = splitScore(score);
			String sql = " AND ("+score+")";
			SQL += sql;
			EXAM += sql;
			YEAR += sql;
			BOOK += sql;
			SCORE += sql;
		}
		if(!"".equals(schoolName) && schoolName !=null){
			schoolName = splitWord(schoolName);
			String sql = " AND school in ("+schoolName+")";
			SQL += sql;
			EXAM += sql;
			YEAR += sql;
			BOOK += sql;
			SCORE += sql;
		}
		if(!"".equals(book) && book !=null){
			book = splitWord(book);
			String sql = "AND textBook in ("+book+")";
			SQL += sql;
			EXAM += sql;
			YEAR += sql;
			BOOK += sql;
			SCORE += sql;
		}
		if(!"".equals(examType) && examType !=null){
			examType = splitWord(examType);
			String sql = " AND exam_type in ("+examType+")";
			SQL += sql;
			EXAM += sql;
			YEAR += sql;
			BOOK += sql;
			SCORE += sql;
		}
		if(!"".equals(yearList) && yearList !=null){
			yearList = splitWord(yearList);
			String sql = " AND year in ("+yearList+")";
			SQL += sql;
			EXAM += sql;
			YEAR += sql;
			BOOK += sql;
			SCORE += sql;
		}
		String sql = " GROUP BY school";
		String examSql = " GROUP BY exam_type";
		String yearSql = " GROUP BY year";
		String bookSql = " GROUP BY textBook";
		String screSql = " GROUP BY (total_score>-1 and total_score<50),(total_score>49 and total_score<60),(total_score>59 and total_score<70),(total_score>69 and total_score<80),(total_score>79 and total_score<90),(total_score>89 and total_score<101)";
		BOOK += bookSql;
		YEAR += yearSql;
		SQL += sql;
		EXAM += examSql;
		SCORE += screSql;
		List<ErrorSumModel> school_count = ErrorSumModel._es._find(SQL);
		List<ErrorSumModel> examTypeList = ErrorSumModel._es._find(EXAM);
		List<ErrorSumModel> yearsList = ErrorSumModel._es._find(YEAR);
		List<ErrorSumModel> bookList = ErrorSumModel._es._find(BOOK);
		List<ErrorSumModel> scoreList = ErrorSumModel._es._find(SCORE);
		List<ErrorSumModel> scoreArl = new ArrayList<ErrorSumModel>();
		for (ErrorSumModel im : scoreList) {
			int range = im._get_int("total_score");
			String rangeName = getRangeName(range);
			im.set("total_score", rangeName);
			scoreArl.add(im);
		}
		setAttr("bookList", bookList);
		setAttr("word", word);
		setAttr("yearList", yearsList);
		setAttr("school_count", school_count);
		setAttr("examList", examTypeList);
		setAttr("scoreList", scoreArl);
		this.render("/error/query_result.ftl");
	}
	/**
	 * 单词获取变形组SQL
	 * */
	private String getSql(String word) {
		if( wordMap.size() == 0){			
			List<WordModel> words = WordModel._dao._find("select * from word ") ;	
			for (WordModel wd : words) {
				String variant = wd._get_String("variant");
				String word_6 = wd._get_String("word");
				if (!wordMap.containsKey(variant)) {
					List<WordModel> words_1 = WordModel._dao._find("select * from word where word =?", word_6);
					ArrayList<String> wordList = new ArrayList<String>();
					for (WordModel wm : words_1) {
						String variant_1 = wm._get_String("variant");
						if (!wordList.contains(variant_1)) {
							wordList.add(variant_1);
							// list.add(variant_1);
						}
					}
					// wordMap.put(variant,wordList);
					for (String key : wordList) {
						wordMap.put(key, wordList);
					}
				}
			}	
		}
		
		String sql = " AND MATCH(error_word)AGAINST('";
		/*if(wordMap.containsKey(word)){
			ArrayList<String> wordList = wordMap.get(word);
			for (String word_2 : wordList) {
				 sql += " error_word LIKE '% "+word_2+" %' OR";
			}	
			sql = sql.substring(0, sql.length()-2);
		}else{
			sql += " error_word LIKE '% "+word+" %' ";
		}
		
		sql += ")";*/
		if(wordMap.containsKey(word)){
			ArrayList<String> wordList = wordMap.get(word);
			for (String word_2 : wordList) {
				 sql += word_2 +",";
			}	
			sql = sql.substring(0, sql.length()-1);
		}else{
			sql += word;
		}
		
		sql += "')";
		return sql;
	}
	private String getRangeName(int range) {
		String rangeName="";
		if(50>range && range>-1){
			rangeName = "49-0";
		}else if(60>range && range>49){
			rangeName = "59-50";
		}else if(70>range && range>59){
			rangeName = "69-60";
		}else if(80>range && range>69){
			rangeName = "79-70";
		}else if(90>range && range>79){
			rangeName = "89-80";
		}else if(101>range && range>89){
			rangeName = "100-90";
		}
		return rangeName;
	}
	private static String splitScore(String score) {
		String sql = "";		
		String[] scoreStr = score.split(",");
		for (String str : scoreStr) {
			String[] sst = str.split("-");
			int n = Integer.parseInt(sst[0])+1;
			int m =Integer.parseInt(sst[1])-1;
				sql += " (total_score <"+(n)+" AND total_score>"+m+") OR";				
		}
		sql = sql.substring(0, sql.length()-2);
		return sql;
	}
	public void size(){
		setCp();
		ReturnJsonObject<JSONObject> rlt = new ReturnJsonObject<>();
		int type = getParam_int("type");		
		if(type!=1){
			//String word = getParam("errorWord");
			String schoolName = getParam("schoolName");
			String book = getParam("book");
			String examType = getParam("examType");
			String yearList = getParam("yearList");
			String score = getParam("score");
			String SQL = "SELECT SUM(words_count) FROM	basic_information WHERE 1=1";
			if(!"".equals(schoolName)){
					schoolName = splitWord(schoolName);
				String bookSql = " AND school in ("+schoolName+")";
				SQL += bookSql;
			}
			if(!"".equals(score) && score !=null){
				score = splitScore(score);
				String sql = " AND ("+score+")";
				SQL += sql;
			}
			if(!"".equals(book)){
					book = splitWord(book);
				String booksql = " AND textBook in ("+book+")";
				SQL += booksql;
			}
			if(!"".equals(examType)){
					examType = splitWord(examType);
				String exam_Type = " AND exam_Type in ("+examType+")";
				SQL += exam_Type;
			}
			if(!"".equals(yearList)){
					yearList = splitWord(yearList);
				String year = " AND year in ("+yearList+")";
				SQL += year;
			}
			if(SQL.contains("AND")){
				int size = InformactionModel.inft._findFirst(SQL)._get_int("SUM(words_count)");
				rlt.setCode(size);
			}else{
				int size = InformactionModel.inft._findFirst("SELECT SUM(words_count) FROM basic_information ")._get_int("SUM(words_count)");
				rlt.setCode(size);
			}			
		}else{
			rlt.setCode(-1);
		}	
		//this.render("/error/error_details.ftl");
		this.renderJson(rlt);
		//this.render("/error/query_result.ftl");
	}
	private void setCp() {
		_cp = getRequest().getContextPath();
		this.setAttr("_cp", _cp);
		this.setAttr("_com", this);
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
	public void errorDetails(){
		setCp();
		String word = getParam("errorWord");
		String schoolName = getParam("schoolName");
		String book = getParam("book");
		String examType = getParam("examType");
		String yearList = getParam("yearList");	
		String type = getParam("type");
		String score = getParam("score");
		int pageNumber = getParam_int("pageNumber",-1);
		int page = getParam_int("page",1);
		setAttr("type", type);
		String SQL = "SELECT error_sentence , error_word FROM error_sum WHERE 1=1 ";
		if(!"".equals(word) && word !=null){
			String likeSql = getSql(word);
			//String sql = " AND error != '流水句' AND error != '连词缺失' AND error != '主句缺失' "+likeSql;
			String sql = " AND error != 170 AND error !=  174 AND error != 146 "+likeSql;
			//String sql =  likeSql;
			SQL += sql;
		}
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
		if(!"".equals(yearList) && yearList != null && !"null".equals(yearList)){
			yearList = splitWord(yearList);
			String sql = " AND year in ("+yearList+")";
			SQL += sql;
		}
		if(pageNumber==-1){
			List<ErrorSumModel> sentencePage = ErrorSumModel._es._find(SQL);
			pageNumber = sentencePage.size();
			pageNumber = getPageNumber(pageNumber);
		}
		setAttr("page", pageNumber);
		String sql = " Limit "+(page+-1)*10+",10";
		SQL += sql;
		List<ErrorSumModel> sentenceList = ErrorSumModel._es._find(SQL);
		word = " "+word+" ";
		for (ErrorSumModel im : sentenceList) {
				String str = " "+im._get_String("error_sentence");
				str = str.replaceAll(",", " , ");
				str = str.replaceAll("\\.", " . ");
				String word_1 = im._get_String("error_word");				
				String word_2 = word_1.replaceAll(word, "<span style=\"font-weight:bold;\">"+word+"</span>");
				str = str.replace(word_1, "<span class=\"font-color\">"+word_2+"</span>");				
				//str = str.replace(word, "<span style=\"font-weight:bold;\">"+word+"</span>");
				im.put("error_sentence",str);
		}
		setAttr("errorSentence", sentenceList);
		this.render("/error/error_details.ftl");
		
	}
	private int getPageNumber(int pageNumbae) {
		int page = pageNumbae/10+1;
		return page;
	}
	/**
	 * 判断是否整数
	 * */
	 public static boolean isInteger(String str) {    
		    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
		    return pattern.matcher(str).matches();    
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
