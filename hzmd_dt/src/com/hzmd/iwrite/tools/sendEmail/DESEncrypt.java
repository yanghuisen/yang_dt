package com.hzmd.iwrite.tools.sendEmail;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;



public class DESEncrypt {
	private static String key = "h9-m2^Z@";
	public static void main(String[] args) {
		try {
			String j=encrypt("37015376@qq.com#&$1449221322899#&$a6a6c6fd-012e-4321-8b15-8b36f023475e");
			System.out.println(j);
			String a = decrypt(j);
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//BHMV4BGm5OD5VN68CLQqPmD5voZPxMKMLrucPtaMqQuPaejg+5QCZw==
		//BHMV4BGm5OD5VN68CLQqPmD5voZPxMKMLrucPtaMqQuPaejg+5QCZw==
		//		1$1000$admin$572$2014-01-09 11:24:25
	}

	public static String decrypt(String value){
		try {
			if (value!=null&&!"".equals(value)){
				byte[] bytesrc = Base64.decodeBase64(value);
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
				IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
				cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
				byte[] retByte = cipher.doFinal(bytesrc);
				value = new String(retByte, "UTF-8");
			} else {
				value=null;
			}
		} catch (Exception e) {
			value=null;
		}
		return value;
	}

	public static String encrypt(String value) {
		try {
			if (value!=null&&!"".equals(value)){
				Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
				DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
				IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
				value = Base64.encodeBase64String(cipher.doFinal(value.getBytes("UTF-8")));
			} else {
				value=null;
			}
		} catch (Exception e) {
			value=null;
			e.printStackTrace();
		}
		return value;
	}


	
}
