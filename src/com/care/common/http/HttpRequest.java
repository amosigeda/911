package com.care.common.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class HttpRequest {
	
	Log logger = LogFactory.getLog(HttpRequest.class);
	
	
	public static String sendGet(String url, String param){
		String urlNameString = url + "?" + param;
		String returnParams = urlReturnParams(urlNameString);
		
		return returnParams;
	}


	public static String sendGetToGaoDe(String url, LinkedHashMap<String,String> map){		
		String urlNameString = url + paramsTransToUrl(map);
		LogFactory.getLog(HttpRequest.class).info("urlNameString="+urlNameString);
		String returnParams = urlReturnParams(urlNameString);
		
		return returnParams;
	}
	
	public static String paramsTransToUrl(LinkedHashMap<String,String> map){
        StringBuffer params = new StringBuffer("?");
		
		for(String key : map.keySet()){
			if(!params.toString().equals("?")){
				params.append("&");
			}
			params.append(key).append("=").append(map.get(key));
		}
		return params.toString();
	}
	@SuppressWarnings("finally")
	public static String urlReturnParams(String urlNameString){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			HttpURLConnection httpConnect = (HttpURLConnection)connection;
			httpConnect.setRequestProperty("accept", "*/*");
			httpConnect.setRequestProperty("connection", "Keep-Alive");
			httpConnect.setRequestProperty("user-agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConnect.connect();

			int code = httpConnect.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}
				in.close();
			}else{
				sb.append("-1");
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			sb.append("-1");
//			e.printStackTrace();
		}finally{
			return sb.toString();
		}	
	}
}
