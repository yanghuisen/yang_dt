package com.hzmd.iwrite.correctresult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hzmd.iwrite.db.UseDbUtil;
import com.hzmd.iwrite.model.ErrorSumModel;
import com.hzmd.iwrite.model.InformactionModel;
import com.hzmd.iwrite.model.WordModel;

public class Hj {
	static HashMap<String, String> wordMap = new HashMap<String,String>();
	public static void main(String[] args) {
		 try {
		      UseDbUtil.start();
		      
		      System.out.println("------华华丽丽的开始------");
		      getSql();
		      System.out.println(wordMap.get("abacuses"));
		      System.out.println("-----华华丽丽的结束--------");
		    } finally {
		      UseDbUtil.stop();
		    }
		
	}
	private static void getSql() {
		if( wordMap.size() == 0){			
			List<WordModel> words = WordModel._dao._find("select * from word ") ;
			//"select * from word where word =?"+"OR variant=?",word,word
			ArrayList<String> list= new ArrayList<String>(); 
			for (WordModel wd : words) {
				String variant = wd._get_String("variant");
				if(!list.contains(variant)){
				List<WordModel> words_1 = WordModel._dao._find("select * from word where word =?"+"OR variant=?",variant,variant);
				ArrayList<String> wordList= new ArrayList<String>(); 
				for (WordModel wm : words_1) {
					String variant_1 = wm._get_String("variant");
					if(!wordList.contains(variant_1)){
						wordList.add(variant_1);
						list.add(variant_1);
					}
					String word_1 = wd._get_String("word");
					if(!wordList.contains(word_1)){
						wordList.add(word_1);
						list.add(word_1);
					}
				}
				for (String key : wordList) {
					wordMap.put(key,wordList.toString());
				}				
			}
			}
		}
	}
}
