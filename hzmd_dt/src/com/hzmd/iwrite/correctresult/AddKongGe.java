package com.hzmd.iwrite.correctresult;

import java.util.List;

import com.hzmd.iwrite.db.UseDbUtil;
import com.hzmd.iwrite.model.ErrorSumModel;

public class AddKongGe {
	public static void main(String[] args) {
		 try {
		      UseDbUtil.start();		      
		      System.out.println("------华华丽丽的开始------");
		      errorWord();
		      System.out.println("-----华华丽丽的结束--------");
		    } finally {
		      UseDbUtil.stop();
		    }
	}
	public static void errorWord(){
		List<ErrorSumModel> error = ErrorSumModel._es._find("select * from error_sum where basic_Id >9 and error_word != ''");
		for (ErrorSumModel es : error) {
			String errorWord = es._get_String("error_word");
			errorWord = " "+errorWord+" ";
			es.set("error_word", errorWord)._saveOrUpdate();			
		}
	}
}
