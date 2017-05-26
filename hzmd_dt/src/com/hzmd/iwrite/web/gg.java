package com.hzmd.iwrite.web;



	
	//package com.hzmd.iwrite.web.controller;

	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.LinkedHashMap;
	import java.util.List;
	import java.util.Map;

	import org.apache.commons.io.FileUtils;
	import org.jdom.Document;
	import org.jdom.Element;
	import org.jdom.JDOMException;
	import org.jdom.input.SAXBuilder;

	import com.alibaba.fastjson.JSONArray;
	import com.alibaba.fastjson.JSONObject;
	import com.hzmd.common.I;
	import com.hzmd.common.http.HttpFetcher;
	import com.hzmd.common.util.ConvertUtil;
	import com.hzmd.common.util.DateUtil;
	import com.hzmd.common.util.JsonUtil;
	import com.hzmd.common.util.StringUtil;
	import com.hzmd.iwrite.model.ErrorSumModel;
	import com.hzmd.iwrite.model.InformactionModel;
	import com.hzmd.iwrite.util.ItestUtil;
	import com.hzmd.iwrite.web.config.G;
import com.hzmd.iwrite.web.controller.RateScore;
import com.jfinal.core.Controller;

	import javafx.print.JobSettings;
	public class gg {
		private static HttpFetcher iwriteFetcher;
		private static int CT = 1;
		private static int BASIC_ID = 1;
//		private JSONObject machineRateInfo = null;
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
		static ArrayList<File> jj = new ArrayList<File>();
		public void index() {
			fetchInformation();
		//	this.render("/error/home_error.ftl");
		}
		/**
		 * 解析XML文件
		 * */
		public void fetchInformation(){		
			File XmlFilePath = new File("d:/Error_Xml");
			jj = print(XmlFilePath);
			for (int i = 0; i < jj.size(); i++) {
				analyze(jj.get(i));
			}
		}
		/**
		 * JDOM解析XML文件
		 * */
		private void analyze(File xmlFile) {
			SAXBuilder builder=new SAXBuilder(false);		
			try {
				Document doc=builder.build(xmlFile);
				Element iWriteCorpus=doc.getRootElement();
				List<Element> fileList = iWriteCorpus.getChildren("file");
				for (Element file : fileList) {
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
					String affiliation = "";
					String school_at = "";
					String grade = "";
					String Class = "";
					String exam_type = "";
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
					String Textbook = "";
					String e_comments = "";
					String error_sentence = "";//错误：句子
					String sentence_location = "";//位置
					String error_type = "";//错误：类型
					String error_word ="";//单词
					String error_comment ="";//建议
					String Total_score = "";//机评：总分
					String Lang_score = "";//语言
					String Cont_score = "";//内容
					String Orgn_score = "";//结构
					String Mech_score = "";//技术规范
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
						 affiliation = type.getChildText("Affiliation");//学校
						 school_at = type.getChildText("School_at_universtity");//院系
						 grade = type.getChildText("Grade");//年级
						 Class = type.getChildText("Class");//班级
						 exam_type = type.getChildText("Exam_type");//使用场景
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
						 Textbook = type.getChildText("Textbook");//所用教材
						 Score = type.getChildText("Score");
						 year = getYear(exam_start);
					}				
				for (Element p_txt : p) {
					txt += p_txt.getValue()+"/n";
					txt = txt.substring(0, txt.length()-2);
				}			
				//score {"ErrorResult":jsonArray,"ScoreResult":String}
				JSONObject score = invokeRemoteWritingRate(txt,"",100,300,total_points);
				JSONArray errorResult = score.getJSONArray("ErrorResult");
				for (int i = 0; i < errorResult.size(); i++) {
					JSONObject Error = errorResult.getJSONObject(i);
					JSONArray errorList = Error.getJSONArray("errorList");
					for (int j = 0; j < errorList.size(); j++) {
						JSONObject error = errorList.getJSONObject(j);
						ErrorSumModel._es.set("basic_Id", BASIC_ID).set("error_sentence", Error.getString("content")).set("error_type", getErrorType(error))
						.set("error", error.getString("typeName")).set("sentence_location", Error.getString("index")).set("error_word", geTerrorWord(error,Error))
						.set("error_comment", error.getString("desc")).save()
						;
					}				
				}
				JSONObject total = score.getJSONObject("total");//总分
				JSONObject language = score.getJSONObject("language");//语言
				JSONObject content = score.getJSONObject("content");//内容
				JSONObject organization = score.getJSONObject("organization");//结构
				JSONObject mechanics = score.getJSONObject("mechanics");//技术规范
				InformactionModel.inft.set("basic_Id",BASIC_ID).set("userId", userId).set("userName", userName)
				.set("s_name", S_name).set("genDer", gender).set("phone", phone).set("email", email).set("school", affiliation)
				.set("school_at", school_at).set("grade", grade).set("cls", Class).set("exam_type", exam_type).set("essay_traslation", Essay_translation)
				.set("prompt", prompt).set("instructions", Instructions).set("student_start", student_start).set("submission_time", sub_endTime)
				.set("text", txt).set("exam_start", exam_start).set("exam_end", exam_end).set("wors_count", words_count).set("writing_time", writing_time)
				.set("total_points", total_points).set("essayid", Essayid).set("dataSource", dataSource).set("textBook", Textbook)
				.set("score", Score).set("total_score", total.getString("score")).set("lang_score",language.getString("score"))
				.set("cont_score", content.getString("score")).set("orgn_score", organization.getString("score")).set("mech_score", mechanics.getString("score"))
				.set("e_comments",total.getString("comment")).set("year", year).save()
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
		/**
		 * 返回错误类别
		 * @param ERROR_TYPE 
		 * */
		private Object getErrorType(JSONObject error) {
			if(ERROR_CIFALEI.size()<1){
				ERROR_JUFALEI.add(1001);
				ERROR_JUFALEI.add(1002);
				ERROR_JUFALEI.add(1003);
				ERROR_JUFALEI.add(1004);
				ERROR_JUFALEI.add(1005);
				ERROR_JUFALEI.add(1006);
				ERROR_JUFALEI.add(1007);
				ERROR_JUFALEI.add(1008);
				ERROR_JUFALEI.add(1010);
				ERROR_JUFALEI.add(1011);
				ERROR_JUFALEI.add(1012);
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
		private String geTerrorWord(JSONObject error, JSONObject Error) {
				String error_words = "";
				String content = Error.getString("content");
				JSONObject index = error.getJSONArray("words").getJSONObject(0);
				int i = index.getIntValue("i");
				int j = index.getIntValue("j");
				error_words = content.substring(i-1, j-1);
			return error_words;
		}
		/**
		 * 根据考试开始时间获取作文年份
		 * */
		private String getYear(String exam_start) {
			String year = "";
			if(exam_start != null && !"".equals(exam_start)){
				year = exam_start.substring(0, 4);
			}
			return year;
		}
		/**
		 * 获取作文总分，没有则为10.
		 * */
		private int getTotalPoints(String childText) {
			int total_points = 10;
			if(childText !=null && !"".equals(childText)){
				total_points = Integer.parseInt(childText);
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
	/**
	 * 调用批改引擎
	 * */
	public JSONObject invokeRemoteWritingRate(String essayContent, String keywords, int downNum, int upNum, int fullScore) {
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
	            score = (int)(value * fullScore / 100);   // score 是*100的分数
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
	}

