package com.hzmd.iwrite.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzmd.common.I;
import com.hzmd.common.http.HttpFetcher;
import com.hzmd.common.util.ConvertUtil;
import com.hzmd.common.util.DateUtil;
import com.hzmd.common.util.JsonUtil;
import com.hzmd.common.util.StringUtil;
import com.hzmd.iwrite.correctresult.InvokingIwrite;
import com.hzmd.iwrite.db.UseDbUtil;
import com.hzmd.iwrite.model.ErrorSumModel;
import com.hzmd.iwrite.model.InformactionModel;
import com.hzmd.iwrite.model.QueryInformationModel;
import com.hzmd.iwrite.util.ItestUtil;
import com.hzmd.iwrite.web.config.G;
import com.hzmd.iwrite.web.controller.RateScore;

public class IwriteCorrect extends Thread{
	static final Logger log = LoggerFactory.getLogger(IwriteCorrect.class);
	private static HttpFetcher iwriteFetcher;
	private static int CT = 1;
//	private JSONObject machineRateInfo = null;
	private static File BASE_DIR = new File("F:/temp/iwrite/" + DateUtil.formatQuietly(new Date(), "yyyy-MM-dd_HHmm"));
	private static String IWRITE_RATE_URL = "http://eng.iwrite.unipus.cn/iwriteservice/check";
	public static String IWRITE_RATE_RESULT_URL = "http://eng.iwrite.unipus.cn/iwriteservice/correntResult";
	private static final Map<String, String> IWRITE_RATE_RESULT_CACHE = new HashMap<String, String>();
	private static boolean IWRITE_RATE_USE_CACHE = false;
	private static ArrayList<Integer> ERROR_JUFALEI = new ArrayList<>();
	private static ArrayList<Integer> ERROR_CIFALEI = new ArrayList<>();
	private static ArrayList<Integer> ERROR_DAPEILEI = new ArrayList<>();
	private static ArrayList<Integer> ERROR_GUIFANLEI = new ArrayList<>();
	 static {
		    iwriteFetcher = new HttpFetcher();
		    IWRITE_RATE_URL = G.getProp("iwrite.rate.url", IWRITE_RATE_URL);
		    IWRITE_RATE_RESULT_URL = G.getProp("iwrite.rateResult.url", IWRITE_RATE_RESULT_URL);
		  }
	private InformactionModel _im;
	private Integer _taskKey =0 ;
	//private InvokingIwrite _invoIwrite;
	//public IwriteCorrect(InformactionModel im,Integer key, InvokingIwrite invo){
		//_im = im;
		//_taskKey = key;
	//	_invoIwrite = invo;
	//}
	public static void main(String[] args) {
		try {
		      UseDbUtil.start();
		     //IwriteCorrect.getText(InformactionModel r);
		    // setQueryInformation();
		      //String jj = "In my opinion,certainly,there are many different ways to spend their spare time between boys and girls.As for boys,l believe that most of boys spent spare time like me.l hold that many boys are cheerful.They would like to play sports such as football,basketball,tennis and so on.So,spending time on sports is neccesary.Then,many boys like playing computer games,which can let them relaxed.Commonly,they also spent spare time on games.However,few boys like to watch movies and buy something in market that many girls like to.On the other hand,girls always dance,sing, and watch movies on weekend.Because most of them are silent.Few girls would like to play basketball orfootball,unless their boyfriends play it on playground.And l think they also spent spare time on reading books.They would like to go to library to lent many kinds of books.Certainly,what l wrote is what l think.And l think boys and girls should try many kinds of ways to spend spare time,which can make them more relaxed and better.";
		      //invokeRemoteWritingRate(jj,"",5,10,10);
		     // InvokingIwrite invok = new InvokingIwrite();
		      //invok.doCorrect();
		    //  getText();
		    } finally {
		      UseDbUtil.stop();
		    }
	}
	private static void setQueryInformation() {
		List<QueryInformationModel> schoolNameList = QueryInformationModel.dao._find("SELECT schoolName FROM query_information GROUP BY schoolName");
		List<InformactionModel> schoolList = InformactionModel.inft._find("SELECT school FROM basic_information WHERE correct = 1 GROUP BY school"); 
		List<InformactionModel> yearList = InformactionModel.inft._find("SELECT year FROM basic_information WHERE correct = 1 GROUP BY year");
		List<QueryInformationModel> yearsList = QueryInformationModel.dao._find("SELECT years FROM query_information GROUP BY years");
		List<InformactionModel> examTypeList = InformactionModel.inft._find("SELECT exam_type FROM basic_information WHERE correct = 1 GROUP BY exam_type");
		List<QueryInformationModel> examList = QueryInformationModel.dao._find("SELECT exam_type FROM query_information GROUP BY exam_type");
		List<InformactionModel> bookList  = InformactionModel.inft._find("SELECT textBook FROM basic_information WHERE correct = 1 GROUP BY textBook");
		List<QueryInformationModel> book_List = QueryInformationModel.dao._find("SELECT text_book FROM query_information GROUP BY text_book");
		for (InformactionModel im : schoolList) {
			String schoolName = im._get_String("school");
			if(schoolNameList.isEmpty() || !schoolNameList.contains(schoolName)){
			QueryInformationModel qt = new QueryInformationModel();
			qt.set("schoolName", schoolName)._saveOrUpdate();
			}
		}
		for (InformactionModel ift : yearList) {
			String year = ift._get_String("year");
			if(yearsList.isEmpty() || !yearsList.contains(year)){
				QueryInformationModel qt = new QueryInformationModel();
				qt.set("years", year)._saveOrUpdate();
			}
		}
		for (InformactionModel ift : examTypeList) {
			String exam_type = ift._get_String("exam_type");
			if(examList.isEmpty() || !examList.contains(exam_type)){
				QueryInformationModel qt = new QueryInformationModel();
				qt.set("exam_type", exam_type)._saveOrUpdate();
			}
		}
		for (InformactionModel ift : bookList) {
			String textBook = ift._get_String("textBook");
			if(book_List.isEmpty() || !book_List.contains(textBook)){
				QueryInformationModel qt = new QueryInformationModel();
				qt.set("text_book", textBook)._saveOrUpdate();
			}
		}
	}
	public void run() {
		getText(_im);
	}
	/**
	 * 获取作文内容批改结果并存储
	 * */
	public  void getText(InformactionModel im){
			String essay = im._get_String("text");
			//int fullScore = im._get_int("total_points");
			int basic_Id = im._get_int("basic_Id");
			log.error("#begin to invoke remote writing rate: eassy:" + basic_Id);
			JSONObject score = invokeRemoteWritingRate(essay,"",150,500,100);
			log.error("#end to invoke remote writing rate: eassy:" + basic_Id);
			if(score==null){
				System.out.println(im._get_int("id"));
			}			
			JSONArray errorResult = score.getJSONArray("ErrorResult");
			JSONObject total = score.getJSONObject("total");//总分
			JSONObject language = score.getJSONObject("language");//语言
			JSONObject content = score.getJSONObject("content");//内容
			JSONObject organization = score.getJSONObject("organization");//结构
			JSONObject mechanics = score.getJSONObject("mechanics");//技术规范	
			System.out.println("技术规范："+mechanics);
			for (int i = 0; i < errorResult.size(); i++) {
				JSONObject Error = errorResult.getJSONObject(i);
				JSONArray errorList = Error.getJSONArray("errorList");
				for (int j = 0; j < errorList.size(); j++) {
					JSONObject error = errorList.getJSONObject(j);
					ErrorSumModel esm = new ErrorSumModel();
					esm.set("basic_Id", basic_Id).set("error_sentence", Error.getString("content")).set("error_type", getErrorType(error))
					.set("error", error.getString("typeName")).set("sentence_location", Error.getString("index")).set("error_word", geTerrorWord(error,Error))
					.set("error_comment", error.getString("desc")).save();					
				}				
			}
			im.set("total_score", total.getString("score")).set("lang_score",language.getString("score"))
			.set("cont_score", content.getString("score")).set("orgn_score", organization.getString("score")).set("mech_score", mechanics.getString("score"))
			.set("e_comments",total.getString("comment")).set("correct", 1)._saveOrUpdate();
			//_taskKey = key;
		//	_invoIwrite.removeTask(_taskKey);
		}
	
	/**
	 * 调用批改引擎
	 * */
	public static JSONObject invokeRemoteWritingRate(String essayContent, String keywords, int downNum, int upNum, int fullScore) {
		JSONObject result = new JSONObject();
		 JSONArray error = new JSONArray();
	    if(essayContent == null) {
	      essayContent = "";
	    }
	    String retContent = null;
	    if(IWRITE_RATE_USE_CACHE) {
	      retContent = IWRITE_RATE_RESULT_CACHE.get(essayContent);
	    }
	    
	    RateScore rs = new RateScore()._setNeedRemoteRate();
	    
	    if(retContent == null) {
//	      if(StringUtil.isNotEmpty(essayContent)) {   // 去掉这个限制
	    	
	        Map<String, Object> map = new LinkedHashMap<String, Object>();
	        map.put("content", essayContent);
	        map.put("key", "100010");
	        map.put("app", "itest");
	        if(StringUtil.isNotEmpty(keywords)) {
	          map.put("keywords", keywords);
	        }
	        if(downNum > 0) {
	          map.put("wordlimit", downNum);
	        }
	        if(upNum > 0) {
	          map.put("wordlimit2", upNum);
	        } else {
	          //map.put("wordlimit2", 99999);   // 暂时改成 99999  Yangming yadan @2017-01  TODO 将来是否要去掉
	        }
	        retContent = iwriteFetcher.doPostQuietly(IWRITE_RATE_URL, map, I.UTF8);
	        if(retContent ==null ){
	        	return null;
	        }
	        JSONObject ret = JSONObject.parseObject(retContent);
	      // System.out.println(ret.getJSONArray("result"));
	       error = ret.getJSONArray("result");
	        if(IWRITE_RATE_USE_CACHE) {
	          IWRITE_RATE_RESULT_CACHE.put(essayContent, retContent);
	        }
//	      }
	    }
	    
	    if(IWRITE_RATE_USE_CACHE) {
	      try {
	        File f = new File(BASE_DIR, (CT++) + ".txt");
	        FileUtils.write(f, essayContent + "\n\n****************************************************************************\n\n" + retContent);  
	      } catch(Exception e) {
	        e.printStackTrace();
	      }  
	    }
	  
	    JSONObject machineRateInfo = new JSONObject();
	    JSONObject machineScoreJo = new JSONObject();
	    machineRateInfo.put("scores", machineScoreJo);
	    
	    if(StringUtil.isNotEmpty(retContent)) {
	      try {
	        JSONObject retJo = (JSONObject)JsonUtil.parseFromJsonStringSafe(retContent);
	        machineRateInfo.put("uuid", retJo.getString("uuid"));
	        JSONArray scoreJA = retJo.getJSONArray("score");
	        for(int i=0; i<scoreJA.size(); ++i) {
	          JSONObject oneScoreJo = scoreJA.getJSONObject(i);
	          JSONObject machineOneScoreJo = new JSONObject();
	          
	          String name = oneScoreJo.getString("name");
	          machineOneScoreJo.put("comment", oneScoreJo.getString("comment"));
	          machineOneScoreJo.put("grade", oneScoreJo.get("grade"));
	          machineOneScoreJo.put("value", oneScoreJo.get("value"));
	          machineOneScoreJo.put("name", name);
	          float value = ConvertUtil.to_float(oneScoreJo.get("value"), 0);
	          
	          int score = 0;
	          if(fullScore > 0) {
	        	  if("mechanics".equals(name)){
	        		  score = (int)(value * 20);
	        	  }else if("language".equals(name)){
	        		  score = (int)((value) * 3.3);
	        	  }else if("content".equals(name)){
	        		  score = (int)((value) * 2.5);
	        	  }else if("organization".equals(name)){
	        		  score = (int)((value) * 4);
	        	  }else {
	            score = (int)(value * fullScore / 100);   // score 是*100的分数
	        	  }
	          }
	          machineOneScoreJo.put("score", score);
	          
	          if("total".equals(name)) {
	            int machineScore = score;
	         // 分数取整 把分数的小数部分取整： 1.0, 1.1 ...  1.4的分数都算1分， 1.5, 1.6 ... 1.9的都算1.5分
	            machineScore = ItestUtil.stdMachineScore(machineScore);
	            rs.setMachineScore(machineScore);
	          }
	          machineScoreJo.put(name, machineOneScoreJo);
	        }
	      } catch(Exception e) {
	        e.printStackTrace(); 
	      }
	    } else {
	      String [] names = {"total", "language", "content", "organization", "mechanics"};
	      for(String name : names) {
	        JSONObject machineOneScoreJo = new JSONObject();
	        machineOneScoreJo.put("comment", "");
	        machineOneScoreJo.put("grade", 0);
	        machineOneScoreJo.put("value", 0);
	        machineOneScoreJo.put("name", name);
	        machineOneScoreJo.put("score", 0);
	        machineScoreJo.put(name, machineOneScoreJo);
	      }
	      rs.setMachineScore(0);
	    } 
	    rs.setMachineRateInfo(machineRateInfo);
	    result.put("ErrorResult", error);
	    result.put("total", rs._getMachineRateInfo_IWriteScore_Safe("total"));
	    result.put("language", rs._getMachineRateInfo_IWriteScore_Safe("language"));
	    result.put("content", rs._getMachineRateInfo_IWriteScore_Safe("content"));
	    result.put("organization", rs._getMachineRateInfo_IWriteScore_Safe("organization"));
	    result.put("mechanics", rs._getMachineRateInfo_IWriteScore_Safe("mechanics"));
//	    log.info("retContent=" + retContent);
	    return result;
	  }  
	/**
	 * 返回错误类别
	 * @param ERROR_TYPE 
	 * */
	private static Object getErrorType(JSONObject error) {
		if(ERROR_CIFALEI.size()<1){
			ERROR_JUFALEI.add(1001);
			ERROR_JUFALEI.add(1002);
			ERROR_JUFALEI.add(1003);
			ERROR_JUFALEI.add(1004);
			ERROR_JUFALEI.add(1005);
			ERROR_JUFALEI.add(1006);
			ERROR_JUFALEI.add(1007);
			ERROR_JUFALEI.add(1008);
			ERROR_JUFALEI.add(1008);
			ERROR_JUFALEI.add(1010);
			ERROR_JUFALEI.add(1011);
			ERROR_JUFALEI.add(1012);
			ERROR_CIFALEI.add(1101);
			ERROR_CIFALEI.add(1102);
			ERROR_CIFALEI.add(1103);
			ERROR_CIFALEI.add(1104);
			ERROR_CIFALEI.add(1105);
			ERROR_CIFALEI.add(1106);
			ERROR_CIFALEI.add(1107);
			ERROR_CIFALEI.add(1108);
			ERROR_CIFALEI.add(1109);
			ERROR_CIFALEI.add(1110);
			ERROR_CIFALEI.add(1111);
			ERROR_CIFALEI.add(1112);
			ERROR_CIFALEI.add(1113);
			ERROR_CIFALEI.add(1114);
			ERROR_CIFALEI.add(1115);
			ERROR_CIFALEI.add(1116);
			ERROR_CIFALEI.add(1117);
			ERROR_CIFALEI.add(1118);
			ERROR_DAPEILEI.add(1201);
			ERROR_DAPEILEI.add(1202);
			ERROR_DAPEILEI.add(1203);
			ERROR_DAPEILEI.add(1204);
			ERROR_GUIFANLEI.add(1301);
			ERROR_GUIFANLEI.add(1302);			
			}
		String errorTypeName = "其它错误";
		int errorType = error.getIntValue("typeId");
		if(ERROR_CIFALEI.contains(errorType)){
			errorTypeName = "词法类";
		}else if(ERROR_DAPEILEI.contains(errorType)){
			errorTypeName = "搭配类";
		}else if(ERROR_JUFALEI.contains(errorType)){
			errorTypeName = "句法类";
		}else if(ERROR_GUIFANLEI.contains(errorType)){
			errorTypeName = "技术规范";
		}
		return errorTypeName;
	}
	/**
	 * 返回句子中错误的单词
	 * */
	private static String geTerrorWord(JSONObject error, JSONObject Error) {
			String error_words = "";
			String content = Error.getString("content");
			JSONObject index = error.getJSONArray("words").getJSONObject(0);
			int i = index.getIntValue("i");
			int j = index.getIntValue("j");
			error_words = content.substring(i, j);
		return error_words;
	}
}
