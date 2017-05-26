package com.hzmd.iwrite.correctresult;

import java.io.File;

import com.hzmd.iwrite.db.UseDbUtil;

/**
 * 读取xml
 * @author Administrator
 *
 */
public class ReadXmlMain {
	public static void main(String [] ages){
		UseDbUtil.start();
		readXml(new File("D:\\iWriteData"));
		//UseDbUtil.stop();
	}
	
	private static void readXml(File file){
		if(file.isDirectory()){
			for(File f : file.listFiles()){
				readXml(f);
			}
		}else{
			/*ChangeXML rXml = new ChangeXML(file);
			Thread t = new Thread(rXml);
			t.start();*/
		}
	}
}
