package com.hzmd.iwrite.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpsClientConnectUtil {

	public static void main(String[] args) throws ParseException, IOException {
		Map<String,String> params = new HashMap<String,String>();
		params.put("keyBook", "98DJFZ-LJJDDQ-K6RT46");
		params.put("xueXiaoId", "1");
		params.put("productId", "3000000");
		//params.put("personalId", "111");
		params.put("userid", "d233a80c544854538715220ea153c5c1");
		params.put("userName", "demo");
		params.put("fullName", "wwe");
		//params.put("phone", "");
		//params.put("email", "");
		String url = ""; 
		url = "https://testlicense.unipus.cn/license/school_consumer/vtest/bookcode_activate";
		url = "https://testlicense.unipus.cn/license-proxy/1.0/activateCode/getQrCodeUrl";
		url = "https://testlicense.unipus.cn/license-proxy/1.0/activateCode/selfActivateCode";
		url = "https://testlicense.unipus.cn/license/1.0/licenses/check_license";
//		JSONObject obj = postJSON(url, JSON.toJSON(params).toString(), null);
		JSONObject obj = postJSON(url, "{\"product\":\"iTest\",\"schoolCode\":\"DLDR\"}", null);
		System.out.println(obj.toString());
	}
	
	/**
	 * 生成假数据
	 * @param type 
	 * @param url 
	 * @return
	 */
	public static JSONObject falseJson(int type, String url){
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		obj.put("status", 200);
		JSONObject msg = new JSONObject();
		msg.put("code", 0);
		msg.put("msg", "success");
		if(type==1){
			//"{\"success\":true,\"message\":\"{\"code\":\"0\",\"msg\":\"success\",\"rs\":{\"activate_time\":\"1460012698\",\"expire_time\":\"1470585600\"}}\",\"status\":200}";
			JSONObject rs = new JSONObject();
			rs.put("activate_time", "1460012698");
			rs.put("expire_time", "1470585600");
			rs.put("useDuration", "4");
			msg.put("rs", rs);
		}else if(type==2){
			//"{\"success\":true,\"message\":\"{\"rs\":{\"id\":\"57071844e4b00a57b86213e4\",\"codePath\":\"https://testlicense.unipus.cn:443/license-proxy/resources/image/qrcode/d233a80c544854538715220ea153c5c198djfzljjddqk6rt461460084580.png\"},\"code\":\"0\",\"msg\":\"success\"}\",\"status\":200}";
			JSONObject rs = new JSONObject();
			rs.put("id", "57071844e4b00a57b86213e4");
			rs.put("codePath", "https://testlicense.unipus.cn:443/license-proxy/resources/image/qrcode/d233a80c544854538715220ea153c5c198djfzljjddqk6rt461460084580.png");
			msg.put("rs", rs);
		}else if(type==3){
			//{"success":true,"message":"{\"code\":\"2033\",\"msg\":\"failure\",\"error\":\"无效的验证码\"}","status":200}
			msg.put("code", 2047);
			msg.put("msg", "failure");
			msg.put("error", "该验证码已经在公网激活过，请输入手机号或者邮箱进行下一步校验");
		}else if(type==4){
			msg.put("code", 0);
			msg.put("msg", "success");
		}else if(type==5){
			msg.put("code", 1010);
			msg.put("msg", "failure");
			msg.put("error", "手机号为空aaa");
		}
		
		obj.put("message", msg);
		return obj;
	}
	

	private static CloseableHttpClient createSSLClientDefault() {
		try {

			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static JSONObject post(String url, Map<String, String> data, Map<String, String> header) throws ParseException, IOException {

		CloseableHttpClient client = createSSLClientDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(15000).setSocketTimeout(15000).build();
		post.setConfig(config);

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (data != null && data.size() > 0) {

			for (Map.Entry<String, String> entry : data.entrySet()) {

				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

			}
		}
		
		if (header != null && header.size() > 0) {

			for (Map.Entry<String, String> entry : header.entrySet()) {
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
		post.setEntity(entity);

		return getCallback(client, post);

	}
	
	public static JSONObject put(String url, Map<String, String> header){
		
		HttpPut put = new HttpPut(url);

		CloseableHttpClient client = createSSLClientDefault();
		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		put.setConfig(config);
		
		if (header != null && header.size() > 0) {

			for (Map.Entry<String, String> entry : header.entrySet()) {
				put.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		return getCallback(client, put);
		
	}
	
	private static JSONObject getCallback(CloseableHttpClient client, HttpUriRequest request){
		JSONObject callback = new JSONObject();
		CloseableHttpResponse response = null;
		String msg = null;
		int status = 0;
		try {
			response = client.execute(request);
			StatusLine statusLine = response.getStatusLine() ; 
			if(null!=statusLine)
				status = statusLine.getStatusCode();
			HttpEntity en = response.getEntity();
			msg = EntityUtils.toString(en);
			callback.put("success", true);
			callback.put("status", status);
			callback.put("message", msg);
		} catch (Exception e) {
			e.printStackTrace();
			callback.put("success", false);
			callback.put("status", -88);
			callback.put("message", "msg: > " + status + " >> " + msg + "url :" + request.getURI().toString() );
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return callback;
	}

	public static JSONObject get(String url, Map<String, String> header) {

		HttpGet get = new HttpGet(url);
		@SuppressWarnings("unused")
		JSONObject callback = new JSONObject();

		CloseableHttpClient client = createSSLClientDefault();
		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		get.setConfig(config);

		if (header != null && header.size() > 0) {

			for (Map.Entry<String, String> entry : header.entrySet()) {
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		return getCallback(client, get);

	}

	public static JSONObject postJSON(String url, String strData, Map<String, String> header) {

		HttpPost httpPost = new HttpPost(url);

		CloseableHttpClient client = createSSLClientDefault();

		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
		httpPost.setConfig(config);

		if(strData!=null&&strData.trim().length() > 0){
			
			StringEntity strEntity = new StringEntity(strData, ContentType.create("application/json", "UTF-8"));
			httpPost.setEntity(strEntity);
		}

		if (header != null && header.size() > 0) {

			for (Map.Entry<String, String> entry : header.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}

		return getCallback(client, httpPost);
	}

}
