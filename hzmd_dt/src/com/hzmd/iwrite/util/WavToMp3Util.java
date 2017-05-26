package com.hzmd.iwrite.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hzmd.common.util.FileUtil;

public class WavToMp3Util {

  /** 
   * 转化wav to mp3 
   */
  public static void singleShell(String srcPath, String newPath) throws Exception {
    Runtime run = null;
    try {
      run = Runtime.getRuntime();
      String command = "lame -V2  " + srcPath + " " + newPath;
      //String cmd = createBat(command);
      Process process = run.exec(command);
      process.getOutputStream().close();
      process.getInputStream().close();
      process.getErrorStream().close();
      process.waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      run.freeMemory();
    }
  }
  /**
   * wav to mp3
   */
	public static void singleShellByJAVE(String wavFilePath, String mp3FilePath) {
		File source = new File(wavFilePath);
  	File target = new File(mp3FilePath);
  	AudioAttributes audio = new AudioAttributes();
  	audio.setCodec("libmp3lame");
  	audio.setBitRate(new Integer(128000));
  	audio.setChannels(new Integer(2));
  	audio.setSamplingRate(new Integer(44100));
  	EncodingAttributes attrs = new EncodingAttributes();
  	attrs.setFormat("mp3");
  	attrs.setAudioAttributes(audio);
  	Encoder encoder = new Encoder();
  	try {
			encoder.encode(source, target, attrs);
			//System.out.println("转化成功::"+FileUtil.getAbsolutePath(target));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}
  /** 
   * 转化一个文件下所以文件，包括n层文件夹 
   * @param strList 
   */
  private static void callShell(List<String> strList) {
    try {
      for (int i = 0; i < strList.size(); i++) {
        String newName = strList.get(i).replace(".wav", ".mp3");
        String command = "d:\\lame -V2  " + strList.get(i) + " " + newName;
        String cmd = createBat(command);
        Runtime.getRuntime().exec(cmd);
      }

    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  public static String createBat(String command) throws IOException {
    String dir = "d:\\lame.bat";

    byte[] b = command.getBytes();
    File file = new File(dir);
    if (!file.exists()) {
      file.createNewFile();
    }
    FileOutputStream os = new FileOutputStream(file);
    os.write(b);
    os.close();

    return dir;

  }

  private static List<String> getFileName(String path, List<String> strList) {
    File file = new File(path);
    FileFilter ff = new FileFilter() {
      @Override
      public boolean accept(File dir) {
        if (dir.getName().endsWith(".wav")) {
          return true;
        }
        return false;
      }
    };
    if (file.isFile()) {
      System.out.println("is not file" + file.getAbsolutePath());
    } else {
      File[] fileList = file.listFiles(ff);
      for (File f : fileList) {
        if (f.isFile()) {
          strList.add(f.getAbsolutePath());
        } else {
          getFileName(f.getAbsolutePath(), strList);
        }
      }
    }
    return strList;
  }

  public static void main(String[] args) throws Exception {

    //String path = "G:\\本人文档存放\\研发中心任务文档\\自动分析html\\colorblock-音效文件\\audio\\20114822174851.wav";
//    String path = "doc/temptemp/0/1440424406487111111.wav";
    String srcFileName="D:\\pstvxp_1454041345147.wav";
    String tarFileName="D:\\123.mp3"; 
    // change(path);  
    //AudioImpress ai  = new AudioImpress();  
    singleShell(srcFileName,tarFileName);
//    CreatePkg pkg = new CreatePkg();  
//    pkg.audioImpress(path);  
  }

  protected static void change(String path) {
    List<String> strList = new ArrayList<String>();
    getFileName(path, strList);
    callShell(strList);
  }


}
