package com.hzmd.iwrite.correctresult;

import java.util.List;

import com.hzmd.iwrite.db.UseDbUtil;
import com.hzmd.iwrite.model.InformactionModel;

public class ModifyScore {
	public static void main(String[] args) {
		try {
	      UseDbUtil.start();	      
	      System.out.println("------华华丽丽的开始------");
	      //getScore();
	      getScore_1();
	      System.out.println("-----华华丽丽的结束--------");
	    } finally {
	      UseDbUtil.stop();
	    }	
	}
	public static void getScore(){
		List<InformactionModel> ift = InformactionModel.inft._find("SELECT * FROM basic_information WHERE correct = 1");
		for (InformactionModel it : ift) {
			int total_points = it._get_int("total_points");
			int total_score = it._get_int("total_score");
			total_score =  (int)(total_score * (100/total_points));
			int lang_score = it._get_int("lang_score");
			lang_score = (int)(lang_score * 10);
			int cont_score = it._get_int("cont_score");
			cont_score = (int)(cont_score * 10);
			int orgn_score = it._get_int("orgn_score");
			orgn_score = (orgn_score * 10);
			int mech_score = it._get_int("mech_score");
			mech_score = mech_score * 10;
			it.set("lang_score", lang_score).set("total_score", total_score).set("cont_score", cont_score)
			.set("orgn_score", orgn_score).set("mech_score", mech_score).saveOrUpdate();
		}
	}
	public static void getScore_1(){
		List<InformactionModel> ift = InformactionModel.inft._find("SELECT * FROM basic_information WHERE correct = 1  AND total_score < 40 and total_score >10");
		for (InformactionModel it : ift) {
			//int total_points = it._get_int("total_points");
			int total_score = it._get_int("total_score");
			total_score =  (int)(total_score * 2.5);
/*			int lang_score = it._get_int("lang_score");
			lang_score = (int)(lang_score / 10);
			int cont_score = it._get_int("cont_score");
			cont_score = (int)(cont_score / 10);
			int orgn_score = it._get_int("orgn_score");
			orgn_score = (orgn_score / 10);
			int mech_score = it._get_int("mech_score");
			mech_score = mech_score / 10;
			it.set("lang_score", lang_score).set("total_score", total_score).set("cont_score", cont_score)
			.set("orgn_score", orgn_score).set("mech_score", mech_score).saveOrUpdate();*/
			it.set("total_score", total_score).saveOrUpdate();
		}
	}
}
