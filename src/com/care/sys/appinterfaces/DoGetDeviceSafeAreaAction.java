package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.safearea.domain.SafeArea;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DoGetDeviceSafeAreaAction extends BaseAction{

	Log logger = LogFactory.getLog(DoGetDeviceSafeAreaAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			String user_id = object.getString("user_id");
			String device_imei = object.getString("device_imei");
			String belongProject = object.has("belong_project")?object.getString("belong_project"):"1";
//			String user_id = request.getParameter("user_id");
//			String device_imei = request.getParameter("device_imei");
			if(user_id != null && device_imei != null){
				SafeArea safeArea = new SafeArea();
				safeArea.setCondition("seri_no ='"+device_imei+"'");
				List<DataMap> safeList = ServiceBean.getInstance().getSafeAreaFacade().getSafeArea(safeArea);
				int count = safeList.size();
				for(int i=0;i<count;i++){		
					JSONObject jsonObject = new JSONObject();
					String longitude = ""+ safeList.get(i).getAt("longitude");
					String latitude = ""+ safeList.get(i).getAt("latitude");
					String safe_range = ""+ safeList.get(i).getAt("safe_range");
					String area_name = ""+ safeList.get(i).getAt("area_name");
					String area_effect_time = ""+ safeList.get(i).getAt("area_effect_time");
					String safe_address = ""+ safeList.get(i).getAt("safe_address");
					String safe_id = ""+ safeList.get(i).getAt("id");
					
					jsonObject.put("longitude", longitude);
					jsonObject.put("latitude", latitude);
					jsonObject.put("safe_range", safe_range);
					jsonObject.put("area_name", area_name);
					jsonObject.put("area_effect_time", area_effect_time);
					jsonObject.put("safe_address", safe_address);
					jsonObject.put("safe_id", safe_id);
					
					jsonArray.add(i, jsonObject);
				}
				json.put("safe_count", count);
				json.put("safe_message", jsonArray);
				
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}
			json.put("request", href);
			insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
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
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());	
		return null;
	}
}
