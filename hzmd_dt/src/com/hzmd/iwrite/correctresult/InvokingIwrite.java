package com.hzmd.iwrite.correctresult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzmd.iwrite.db.UseDbUtil;
import com.hzmd.iwrite.model.InformactionModel;

public class InvokingIwrite {
	static final Logger log = LoggerFactory.getLogger(InvokingIwrite.class);
	static int COUNT=200;
	static Map<Integer, InformactionModel> map = new HashMap<Integer, InformactionModel>();
	static{for (int i = 0; i < COUNT; i++) {
		map.put(i, null);
	}
	}
	public static void main(String[] args) {
		try {
		      UseDbUtil.start();
		     //IwriteCorrect.getText(InformactionModel r);
		    // setQueryInformation();
		      //String jj = "In my opinion,certainly,there are many different ways to spend their spare time between boys and girls.As for boys,l believe that most of boys spent spare time like me.l hold that many boys are cheerful.They would like to play sports such as football,basketball,tennis and so on.So,spending time on sports is neccesary.Then,many boys like playing computer games,which can let them relaxed.Commonly,they also spent spare time on games.However,few boys like to watch movies and buy something in market that many girls like to.On the other hand,girls always dance,sing, and watch movies on weekend.Because most of them are silent.Few girls would like to play basketball orfootball,unless their boyfriends play it on playground.And l think they also spent spare time on reading books.They would like to go to library to lent many kinds of books.Certainly,what l wrote is what l think.And l think boys and girls should try many kinds of ways to spend spare time,which can make them more relaxed and better.";
		      //invokeRemoteWritingRate(jj,"",5,10,10);
		      InvokingIwrite invok = new InvokingIwrite();
		      invok.doCorrect();
		    } finally {
		      UseDbUtil.stop();
		    }
	}
	
	public void doCorrect(){
		 while(true){
	    	  ThreadPool();
	    	  for (Integer key : map.keySet()) {
	    		  InformactionModel informactionModel = map.get(key);
	    		  if(informactionModel!=null && informactionModel._get_int("correct") == 2){
	    		  log.error("Begin to new IwriteCorrect:" + key);
	    		  IwriteCorrect ic = new IwriteCorrect(map.get(key),key,this);
	    		  ic.start();
	    		  informactionModel.set("correct", 3).update();
	    		  log.error("End to new IwriteCorrect:" + key);
	    		  }
			}
	      }
	}
	
	public void removeTask(Integer key){
		map.put(key, null);
	}
	/**
	 * 返回线程池中 null 数量
	 * */
	public static int getCount(){
		int n=0;
		for (Integer key : map.keySet()) {
			if(map.get(key)==null){
				n++;
			}
		}
		return n;
	}
	/**
	 * 刷新线程池数据
	 * */
	public static void ThreadPool(){
		int count = getCount();
		if(count > 0){
			String SQL = "select * from basic_information where correct = 0 Limit 0,"+count;
			List<InformactionModel> inftList = InformactionModel.inft._find(SQL);
			for (InformactionModel im : inftList) {
				im.set("correct", 2).update();
				setMap(im);
			}
		}
	}
	private static void setMap(InformactionModel im) {
		for (Integer key : map.keySet()) {
			InformactionModel inft = map.get(key);
			if(inft==null){
				map.put(key, im);
				return;
			}
		}
		
	}
}
