package com.leanin.utils;



import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpClient请求
 * 
 * @author zd
 *
 */
public class HttpClientUtil {
	

	private static final Logger logger=LoggerFactory.getLogger(HttpClientUtil.class);
	/*
	 * 调用URL（携带map参数）发送get请求
	 */
	public static String doGet(String url, Map<String, String> param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * POST请求方法
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url, Map<String, String> param) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPostCarryJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

			// 创建请求体内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	


	
	
	
	/*
	 * 发送消息类ussd
	 */
	public static String invokeUSSD(String url,String phoneNum, String type, String content, String msgId,String token) {

		String request = getRequestBody(phoneNum, type, content, msgId,token);
		CloseableHttpResponse response = sendRequest(url,request);
		//System.out.println("11"+response.toString());
		String resp = getResponseBodyValue(response);
		//logger.info("响应Body  ： " + resp);
		return resp;
	}
	
	
	
	
	
	/**
	 * 获取响应主体的内容
	 * 
	 * @param response
	 * @return
	 */
	public static String getResponseBodyValue(CloseableHttpResponse response) {
		HttpEntity entity = response.getEntity();
		String respBody = null;
		try {
			respBody = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return respBody;
	}

	/**
	 * 功能：发送请求 ，接收响应
	 * 
	 * @param request
	 * @param url
	 * @param token
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private static CloseableHttpResponse sendRequest(String url,String request) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 设置请求和传输超时时间
		//RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//
		HttpPost httpPost = new HttpPost(url);
		//httpPost.setConfig(requestConfig);
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.setEntity(new StringEntity(request, Charset.forName("utf-8")));
		logger.info("POST请求 ： " + httpPost.toString());
//		logger.info("Authorization:" + authorization);
		logger.info("请求Body ：" + request);
		// 发送请求，并接收响应
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 功能：拼接请求Body
	 * 
	 * @param phoneNum
	 * @param type
	 * @param content
	 * @param outMsgId
	 * @return requestBody字符串
	 */
	public static String getRequestBody(String phoneNum, String type, String content, String outMsgId,String token) {
		
		JSONObject json = new JSONObject();
		String jsonStr = null;
		json.put("phone", phoneNum);
		json.put("type", type);

		json.put("content", content);

		json.put("messageId", outMsgId);
		
		json.put("token", token);
		jsonStr = json.toString(2);

		

		
		return jsonStr;
	}
	
}

