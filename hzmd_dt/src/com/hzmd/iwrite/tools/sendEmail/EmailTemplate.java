package com.hzmd.iwrite.tools.sendEmail;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EmailTemplate {

	public static Map<String,Object> getRetrievePasswordTemplate(String httpUrl,Map<String,Object> m,String pngPath){
		
		String tempStr="<!DOCTYPE html>"
		+"<html>"
		+"<head>"
		+"<meta charset=\"utf-8\">"
		+"<title></title>"
			+"<style>"
			    +"*{margin: 0;padding: 0;}"
	            +"ul{list-style: none;}"
	            +"a{text-decoration: none;color:#000;}"
	            +"img{border:none;}"
	            +"body{font-family:\"微软雅黑\",\"宋体\";font-size: 14px;}"
	            +"em{ font-style: normal;}"
				+".send-email{width: 800px; padding: 10px 0 56px; margin: 30px auto; border-radius: 4px;}"
	            +".send-email .send-logo{display: block; margin: 0 auto;}"
	            +".send-email .send-word{ padding: 34px 0 37px; width: 288px; text-align: center; margin: 14px auto 33px auto;color:#000000;line-height:19px; border-width:1px 0; border-color:#D8D8D8; border-style: solid;}"
	            +".send-email .send-btn{border:1px solid #3EA5FF;width:123px;height:34px; line-height: 34px; text-align: center;font-size:18px;color:#40A6FF; display: block; margin: 0 auto;}"
			+"</style>"
		+"</head>"
		+"<body>"
			+"<div class=\"send-email\">"
			+"<img src=\"cid:logo.png\" class=\"send-logo\">"
			+"<div class=\"send-word\">您刚刚执行了找回密码操作，点击验证按钮继续下一步操作</div>"
			+"<a href=\""+httpUrl+"_EMAIL_CONTENT_REPLACE_\" class=\"send-btn\">验证</a>"
			+"</div>"
		+"</body>"
	+"</html>";
		
		if(m.get("contentReplace")!=null){
			tempStr=tempStr.replace("_EMAIL_CONTENT_REPLACE_", m.get("contentReplace").toString());
		}
		Map<String,Object> map=new HashMap<String,Object>();
		if(m.get("receiverList")!=null){
			map.put("receiverList", m.get("receiverList"));
		}else{
			map.put("receiverList", null);
		}
		String path = EmailTemplate.class.getResource("/").getPath();
		path = (path.substring(1).replace("classes/", "")+"static/its/lib/itest/images/").replaceAll("///","\\") ;
		map.put("subject", "iTEST找回密码");
		map.put("content", tempStr);
//		map.put( "attachments", new File[]{new File(getRealPathBase()+"sendmail/logo.png")});
		map.put( "attachments", new File[]{new File(pngPath+"/its/lib/itest/images/sendmail/logo.png")});
		return map;
	}
}
