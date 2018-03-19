package com.care.sys.interfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.http.BaseAction;
import com.care.common.http.HttpRequest;
import com.care.common.lang.Constant;
import com.godoing.rose.log.LogFactory;

public class GetDeviceLocationForLBS extends BaseAction{

	Log logger = LogFactory.getLog(GetDeviceLocationForLBS.class);
	
	LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		try{
//			ServletInputStream input = request.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//			StringBuffer sb = new StringBuffer();
//			String online = "";
//			while((online = reader.readLine()) != null){
//				sb.append(online);
//			}
//			JSONObject object = JSONObject.fromObject(sb.toString());
//			
//			String accesstype = object.getString("accesstype");  //0��ʾ��վ����GPRS��1��ʾwifi
//			String imei = object.getString("imei");
//			String imsi = object.getString("imsi");
//			String key = object.getString("key");
//			
//			map.put("accesstype", accesstype);
//			map.put("imei", imei);
//			map.put("imsi", imsi);
//			map.put("output", "json");
//			map.put("serverip", Constant.SERVER_IP);
//			map.put("key", key);
//			
//			if("0".equals(accesstype)){
//				String network = object.getString("network"); //��������GSM/GPRS/EDGE/HSUPA/HSDPA/WCDMA
//				String cdma = object.getString("cdma");  //0��ʾ��cdma,1��ʾcdmn
//				String signal = object.getString("signal");    //�ź�ǿ��
//	        	//bts��Ϊ����,cdma����ºͷ�cdma���
//				String bts = "";
//	        	if("0".equals(cdma)){  //��cdma���
//	        		String cellid = object.getString("cellid");    //��վС����
//	        		String lac = object.getString("lac");          //λ��������	 
//	        		String mcc = imsi.substring(0,3);              //imsi��ǰ��λ
//	        		String mnc = imsi.substring(3,5);              //�ƶ����        		       		
//	        		
//	        		bts = mcc+","+mnc+","+lac+","+cellid+","+signal;
//	        	}else if("1".equals(cdma)){
//	        		String sid = object.getString("sid");          //cdmaϵͳʶ����
//	        		String nid = object.getString("nid");          //cdma����ʶ����
//	        		String bid = object.getString("bid");          //cdmaС��Ψһʶ����
//	        		
//	        		bts = sid+","+nid+","+bid+","+signal;
//	        	}
//	        	map.put("bts", bts);
//			}else if("1".equals(accesstype)){  //wifi��λ
//				String macs = object.getString("macs");       //�����mac��ַ
//				String mac = object.getString("mac");         //�ֻ�mac��ַ
//				String smac = object.getString("smac");        //�ֻ��mac��ַ
//				
//				map.put("macs", macs);
//				map.put("mac", mac);
//				map.put("smac", smac);
//			}
//			
//			String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_URL, map);
//			System.out.println("jsonToString="+jsonToString);
			
			//get��������
			String accesstype = request.getParameter("accesstype"); //�ƶ���������(0),wifi(1)
			String imei = request.getParameter("imei");  //�ֻ��imei��
			String imsi = request.getParameter("imsi");  //�ֻ��imsi��
			String key = request.getParameter("key");    //�û���key
			String belongProject = request.getParameter("b_g");
			
	        if("0".equals(accesstype)){
	        	String network = request.getParameter("network"); //��������GSM/GPRS/EDGE/HSUPA/HSDPA/WCDMA
	        	String cdma = request.getParameter("cdma");  //0��ʾ��cdma,1��ʾcdmn
	        	String signal = request.getParameter("signal");//�ź�ǿ��
	        	//bts��Ϊ����,cdma����ºͷ�cdma���
	        	String bts = "";
	        	if("0".equals(cdma)){  //��cdma���
	        		String mcc = imsi.substring(0,3);              //imsi��ǰ��λ
	        		String mnc = imsi.substring(3,5);              //�ƶ����
	        		String lac = request.getParameter("lac");      //λ��������
	        		String cellid = request.getParameter("cellid");//��վС����      		
			
			        bts = mcc+","+mnc+","+lac+","+cellid+","+signal;
	        	}else if("1".equals(cdma)){
	        		String sid = request.getParameter("sid");      //cdmaϵͳʶ����
	        		String nid = request.getParameter("nid");      //cdma����ʶ����
	        		String bid = request.getParameter("bid");      //cdmaС��Ψһʶ����
			
			        bts = sid+","+nid+","+bid+","+signal;
	        	}
	        	map.put("bts", bts);
			}else if("1".equals(accesstype)){  //wifi��λ
				String macs = request.getParameter("macs");        //�����mac��ַ
				String mac = request.getParameter("mac");          //�ֻ�mac��ַ
				String smac = request.getParameter("smac");        //�ֻ��mac��ַ
				map.put("macs", macs);
				map.put("mac", mac);
				map.put("smac", smac);
				
			}
			map.put("accesstype", accesstype);
			map.put("imei", imei);
			map.put("imsi", imsi);
			map.put("output", "json");
			map.put("serverip", Constant.SERVER_IP);
			map.put("key", key);
			
			String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_URL, map);
			System.out.println("jsonToString="+jsonToString);
			
			result = Constant.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();	
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();		
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);
			
			logger.error(e);
			
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}
		
//		String accesstype = request.getParameter("accesstype"); //�ƶ���������(0),wifi(1)
//		String imei = request.getParameter("imei");  //�ֻ��imei��
//		String imsi = request.getParameter("imsi");  //�ֻ��imsi��
//		String key = request.getParameter("key");    //�û���key
//		
//        if("0".equals(accesstype)){
//        	String network = request.getParameter("network"); //��������GSM/GPRS/EDGE/HSUPA/HSDPA/WCDMA
//        	String cdma = request.getParameter("cdma");  //0��ʾ��cdma,1��ʾcdmn
//        	//bts��Ϊ����,cdma����ºͷ�cdma���
//        	String bts = request.getParameter("bts");
//        	if("0".equals(cdma)){  //��cdma���
//        		String mcc = imsi.substring(0,3);              //imsi��ǰ��λ
//        		String mnc = imsi.substring(3,5);              //�ƶ����
//        		String lac = request.getParameter("lac");      //λ��������
//        		String cellid = request.getParameter("cellid");//��վС����
//        		String signal = request.getParameter("signal");//�ź�ǿ��
//        	}else if("1".equals(cdma)){
//        		String sid = request.getParameter("sid");      //cdmaϵͳʶ����
//        		String nid = request.getParameter("nid");      //cdma����ʶ����
//        		String bid = request.getParameter("bid");      //cdmaС��Ψһʶ����
//        	}
//		}else if("1".equals(accesstype)){  //wifi��λ
//			String macs = request.getParameter("macs");        //�����mac��ַ
//			String mac = request.getParameter("mac");          //�ֻ�mac��ַ
//			String smac = request.getParameter("smac");        //�ֻ��mac��ַ
//		}
//		map.put("accesstype", accesstype);
//		map.put("imei", imei);
//		map.put("imsi", imsi);
//		map.put("output", "json");
//		map.put("serverip", Constant.SERVER_IP);
//		map.put("key", key);
//		
//		String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_URL, map);
		return null;
	}
}
