package com.hzmd.iwrite.web.controller;
/*package com.hzmd.itest.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzmd.common.bean.ReturnJsonObject;
import com.hzmd.itest.global.ITestCC.exportCC;
import com.hzmd.itest.model.CodeModel;
import com.hzmd.itest.model.DictionaryDefinfoModel;
import com.hzmd.itest.model.DictionaryWordsModel;
import com.hzmd.itest.model.GroupModel;
import com.hzmd.itest.model.UserGroupModel;
import com.hzmd.itest.model.UserModel;
import com.hzmd.itest.model.WordModel;
import com.hzmd.itest.model.WordTagModel;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.sun.scenario.effect.impl.hw.ShaderSource;

import javafx.scene.control.Alert;
import net.sf.ehcache.util.FindBugsSuppressWarnings;

public class WordTagController extends Controller {
	public static String _cp = "";

	public void index() {

		// this.setAttr("wordList", WordModel._mg._find("select * from
		// dt_word"));
		setCp();
		this.render("/word/login.ftl");
	}

	private void setCp() {
		_cp = getRequest().getContextPath();
		this.setAttr("_cp", _cp);
	}
	public void jieShi(){
		
		String word = getParam("word");
		Map<String,Integer> defs = new HashMap<String,Integer>();
		StringBuilder jieshi  = new StringBuilder();
		jieshi.append("<h2>"+word+"</h2>");
		List<DictionaryWordsModel> words = DictionaryWordsModel._mgr._find("SELECT * FROM dictionary_words WHERE word = ?",word);
		for (DictionaryWordsModel wd : words) {
			String pos = wd._get_String("pos");
			StringBuilder defBuilder = new StringBuilder();
			List<DictionaryWordsModel> words1 = DictionaryWordsModel._mgr._find("SELECT * from dictionary_words WHERE word = '"+wd._get_String("word") +"' and pos = '"+ wd._get_String("pos") +"'");
			int n =1;
			for (DictionaryWordsModel w1 : words1) {
				List<DictionaryDefinfoModel> info = DictionaryDefinfoModel._mrg._find("select * from dictionary_definfo where pId=?",w1._get_int("id"));
				for (DictionaryDefinfoModel def : info) {
					if (!def._get_String("def").equals("") && !defs.containsKey(def._get_String("def"))) {
						 defs.put(def._get_String("def"), 0);
						  defBuilder.append("<li><span class='english'>" + n + ".");
						  defBuilder.append(def._get_String("def"));
						  defBuilder.append("</span>");
						  defBuilder.append("<span class='chinese'>");
						  defBuilder.append(def._get_String("trans") );
						  defBuilder.append("</span></li>");
						  n++;
					}
				}
			}
			if (defBuilder.length()!=0) {
				jieshi.append("<p>"+ pos + "</p>");
				jieshi.append("<ul class='js-list'>");
				jieshi.append(defBuilder.toString());
				jieshi.append("</ul>");
			}
		}
		System.out.println("---------------------返回------------------");
		ReturnJsonObject<ReturnJsonObject> rlt = new ReturnJsonObject<>();
		String jieshi1 = jieshi.toString();
		rlt.setMsg(jieshi1);
		//renderHtml(jieshi.toString());
		renderJson(rlt);
	}
	*//**
	 * 做完 保存后提示
	 * *//*
	public void tiShi(){
		setCp();
		String user = getParam("user");
		setAttr("user", user);
		int groupCount = weizuo();
		setAttr("group", groupCount);
		this.render("/word/jieguo.ftl");
	}
	*//**
	 * 验证 user
	 *//*
	public void userLogin() {
		ReturnJsonObject<JSONObject> rlt = new ReturnJsonObject<JSONObject>();
		String user = getParam("user");
		setCp();
		List<UserModel> list = UserModel._me._find("select * from dt_user where u_code=?", user);
		if (list.size() == 0) {
			rlt.setCode(-1);
			this.renderJson(rlt);
		} else {
			JSONObject json = new JSONObject();
			json.put("wc", wanc());
			json.put("wz", weizuo());
			rlt.codeSuccess().setData(json);
			rlt.setCode(1);
			this.renderJson(rlt);
		}
	}

	*//**
	 * --获取单词--
	 *//*
	public void getWords() {
		setCp();
		String user = getParam("user");
		try {
			int g_id = UserGroupModel._me
					._findOneColInteger("select g_id from dt_user_group where u_code=? and status =1 limit 1", user);
			setAttr("user", user);
			setAttr("g_id", g_id);
			setAttr("wordList", WordModel._mg._find("SELECT a.*,b.dt_type FROM dt_word AS a LEFT JOIN dt_word_tag as b on a.w_id = b.w_id and b.g_id = ? and b.u_code = ?	where a.g_id =? order by a.id", g_id,user,g_id));
		} catch (Exception e) {
			int g_id = UserGroupModel._me
					._findOneColInteger("select g_id from dt_user_group where u_code=? and status =0 limit 1", user);
			setAttr("user", user);
			setAttr("g_id", g_id);
			setAttr("wordList", WordModel._mg._find("select * from dt_word where g_id=?", g_id));
		}

		this.render("/word/words.ftl");
	}

	*//**
	 * -----单词分组-----
	 *//*
	public void group() {
		List<WordModel> list = WordModel._mg._find("select * from dt_word");
		int a = 1;
		int g = 0;
		for (int i = 0; i < list.size(); i++) {
			g++;
			list.get(i).set("g_id", a)._update();
			if (i == list.size() - 1) {
				break;
			}
			if (g == 200) {
				a++;
				g = 0;
			}
		}
	}

	*//**
	 * 未完成 组
	 *//*

	public int weizuo() {
		String user = getParam("user");
		System.out.println(user + "lllllllllllllllllllllllllllll");
		int wz = UserGroupModel._me._findOneColInteger("select count(*) from dt_user_group where (status=0 or status=1) and u_code=?",
				user);
		System.out.println(wz + "**********************************");
		return wz;
	}

	*//**
	 * 获取已完成组
	 *//*
	public int wanc() {
		String user = getParam("user");
		int qbg = UserGroupModel._me._findOneColInteger("select count(*) from dt_user_group where u_code=?", user);
		int wc = qbg - weizuo();
		System.out.println(wc + "++++++++++++++++++++++++");
		return wc;
	}

	*//**
	 * 保存问卷信息
	 * 
	 * @return
	 *//*
	public void saveTg() {
		ReturnJsonObject<JSONObject> rlt = new ReturnJsonObject<JSONObject>();
		try {
			String u_code = getParam("user");
			int g_id = getParam_int("g_id");
			String tg = getParam("list");
			JSONArray jsonArray = JSONArray.parseArray(tg);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				int dt_type = obj.getIntValue("dt_type");
				int w_id = obj.getIntValue("w_id");
				boolean k = Db.deleteById("dt_word_tag", "w_id", w_id);
				System.out.println(k+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
				WordTagModel model = new WordTagModel();
				model.set("w_id", w_id);
				model.set("dt_type",dt_type);
				model.set("g_id",g_id);
				model.set("u_code", u_code);
				model._save();
			}
			int count = WordTagModel._me._findOneColInteger("SELECT COUNT(*) FROM dt_word_tag WHERE u_code=? AND g_id=?",u_code,g_id);
				int status=0;		
				int wordCount = GroupModel._me._findOneColInteger("select word_count from dt_group where id=?", g_id);
			if(wordCount>count&&count>0){
				status = 1;
				Db.update("UPDATE dt_user_group SET `status`=? WHERE u_code=?AND g_id=?",status,u_code,g_id);
			rlt.setCode(1);
			}else if(count==wordCount){
				status = 2;
				Db.update("UPDATE dt_user_group SET `status`=? WHERE u_code=?AND g_id=?",status,u_code,g_id);
				rlt.setCode(2);
			}
			int k = Db.update("UPDATE dt_user_group SET `status`=? WHERE u_code=?AND g_id=?",status,u_code,g_id);
				System.out.println("___________________________________"+k);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(rlt);
	}
}
*/