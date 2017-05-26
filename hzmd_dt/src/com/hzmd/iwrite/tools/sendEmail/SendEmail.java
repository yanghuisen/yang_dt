package com.hzmd.iwrite.tools.sendEmail;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.hzmd.iwrite.global.ITestCC;
import com.hzmd.iwrite.web.config.G;

public class SendEmail {

  private final static Properties props = new Properties();

  static {
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.host", G.getPropTrim("mail_smtp_host",ITestCC.Mail_CC.mail_smtp_host));
    props.put("mail.user", G.getPropTrim("mail_user",ITestCC.Mail_CC.mail_user));
    props.put("mail.password", G.getPropTrim("mail_password",ITestCC.Mail_CC.mail_password));

  }

  public static String getSendEmail() {
    return props.get("mail.user").toString();
  }

//  public static void send(String templateFile, Map<String, String> replaceMap, String[] receiverList, String subject, File... attachments) throws Exception {
//    String content = readUTF8(templateFile);
//
//    for (Map.Entry<String, String> kv : replaceMap.entrySet()) {
//      content = content.replace(kv.getKey(), kv.getValue());
//    }
//
//    sendEmail(receiverList, subject, content, attachments);
//  }

  private static void sendEmail(String[] receiverList, String subject, String content, File... attachments) throws Exception {
    if (receiverList == null || receiverList.length == 0)
      return;

    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        String userName = props.getProperty("mail.user");
        String password = props.getProperty("mail.password");
        return new PasswordAuthentication(userName, password);
      }
    };
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getInstance(props, authenticator);
    mailSession.setDebug(true);

    // 创建邮件消息
    MimeMessage message = new MimeMessage(mailSession);
    // 设置发件人
    InternetAddress from = new InternetAddress(props.getProperty("mail.user"));
    message.setFrom(from);

    // 设置收件人
    Address[] to = new Address[receiverList.length];
    for (int i = 0; i < receiverList.length; i++)
      to[i] = new InternetAddress(receiverList[i]);

    message.addRecipients(Message.RecipientType.TO, to);

    message.setSubject(subject, "UTF-8");

    MimeMultipart multipart = new MimeMultipart();
    //正文
    BodyPart contentPart = new MimeBodyPart();
    contentPart.setContent(content, "text/html;charset=UTF-8");
    multipart.addBodyPart(contentPart);
    //附件
    if (attachments != null && attachments.length > 0) {
      for (File attachment : attachments) {
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachment);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
        attachmentBodyPart.setContentID(MimeUtility.encodeWord(attachment.getName()));
        multipart.addBodyPart(attachmentBodyPart);
        multipart.setSubType("related");
      }
    }

    message.setContent(multipart);

    Transport.send(message);
  }

  public static boolean sendEmailByEmailTemplate(Map<String,Object> map) {
    if (map.get("receiverList") == null) {
      return false;
    }
    try {
      sendEmail((String[]) map.get("receiverList"), map.get("subject").toString(), map.get("content").toString(), (File[]) map.get("attachments"));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  public static String readUTF8(String fileName) throws Exception {
    return read(new File(fileName), "UTF-8");
  }

  public static String read(File f, String charsetName) throws Exception {
    InputStream in = new BufferedInputStream(new FileInputStream(f));
    try {
      return read(in, charsetName);
    } finally {
      in.close();
    }
  }

  public static String read(InputStream in, String charsetName) throws Exception {
    int length = 4096;

    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    byte[] buffer = new byte[length];
    for (;;) {
      int ins = in.read(buffer);
      if (ins == -1) {
        in.close();
        return bout.toString(charsetName);
      }
      bout.write(buffer, 0, ins);
    }
  }
  
  public static void main(String[] strings) throws Exception {
//    sendEmail(new String[] { "157442595@qq.com" }, "邮箱找回密码", "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "    <meta charset=\"utf-8\">\n" + "    <title></title>\n"
//        + "</head>\n" + "<body>\n" + "adwadwadaw" + "</body>\n" + "</html>\n", new File("C:\\Users\\lx\\Desktop\\图片\\u=230563171,1076672207&fm=21&gp=0.jpg"));
//    File[] files = new File[1];
//    files[0] = new File("C:\\Users\\lx\\Desktop\\图片\\u=230563171,1076672207&fm=21&gp=0.jpg");
//    Map<String,Object>  sendMap = new HashMap<String,Object>();
//    sendMap.put("receiverList", new String[] { "157442595@qq.com" });
//    sendMap.put("subject", "邮箱找回密码");
//    sendMap.put("content", "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "    <meta charset=\"utf-8\">\n" + "    <title></title>\n"
//        + "</head>\n" + "<body>\n" + "adwadwadaw" + "</body>\n" + "</html>\n");
//    sendMap.put("attachments",files);
//    sendEmailByEmailTemplate(sendMap);
    testSend();
  
  }
  
  public static void testSend(){
    Map<String,Object> map=new HashMap<String,Object>();
    String email = "157442595@qq.com";
    Date dt=new Date();
    String vaildCode=UUID.randomUUID().toString();
    String str=email+"&"+dt.getTime()+"&"+vaildCode;
    String strUrl = null;
    try {
      strUrl = URLEncoder.encode(DESEncrypt.encrypt(str),"utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    
    map.put("contentReplace", "login/setNewPassword?p="+strUrl);
    map.put("receiverList", new String[]{email});
    SendEmail.sendEmailByEmailTemplate(EmailTemplate.getRetrievePasswordTemplate(G.getProp("iwrite.application.url",ITestCC.Mail_CC.mail_url),map,""));
    
    /*   
    if(SendEmail.sendEmailByEmailTemplate(EmailTemplate.getRetrievePasswordTemplate(Util.getHttpUrl(getRequest()),map))){
      um.get(0).setPassValidCode(vaildCode);
      um.get(0).update();
      render("retrieve_password_sended.ftl");
    }else{
      setAttr("errorMsg", "邮件发送失败请稍后重试");
      render("retrieve_password.ftl");
    }
     * * */
  
  }
  
}
