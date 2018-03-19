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
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class QueryDeviceTrackAction extends BaseAction {

	
	Log logger = LogFactory.getLog(QueryDeviceTrackAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		JSONObject locationData = new JSONObject();
		JSONArray trackData = new JSONArray();
		String user_id = "";
		String belongProject = "";
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			user_id = object.getString("user_id");
			String device_id = object.getString("device_id");
			String start_time = object.getString("start_time");
			String end_time = object.getString("end_time");
			belongProject = object.getString("belong_project");
			
			LocationInfo vo = new LocationInfo();
			vo.setCondition("serie_no = '"+device_id+"' and belong_project='"+belongProject+"' and upload_time >= '"+start_time+"' and upload_time <= '"+end_time+"' and longitude != '0.000000' and latitude != '90.000000' and s_t = '1'");
			vo.setOrderBy("upload_time");
			json.put("request", href);
			LocationInfoFacade facade = ServiceBean.getInstance().getLocationInfoFacade();
			
			List<DataMap> list = facade.getLocationListInfo(vo);//锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷锟斤拷锟�
			if(list.size() > 0){
				int size = list.size();
				for(int i=0;i<size;i++){
					DataMap locationMap=(DataMap)list.get(i);
					String lng=(String)locationMap.getAt("change_longitude");
					String lat=(String)locationMap.getAt("change_latitude");
					String no_trans_lng=(String)locationMap.getAt("longitude");
					String no_trans_lat=(String)locationMap.getAt("latitude");
					
					int battery = (Integer)locationMap.getAt("battery");
					String type = (String)locationMap.getAt("location_type");
					String time=locationMap.getAt("upload_time").toString();
					String e_t = ""+locationMap.getAt("e_t");
					String fall = ""+locationMap.getAt("fall");
					
					int accuracy = (Integer)locationMap.getAt("accuracy");
					locationData.put("lat", lat);
					locationData.put("lng", lng);
					locationData.put("no_trans_lat", no_trans_lat);
					locationData.put("no_trans_lng", no_trans_lng);
					locationData.put("battery", battery);
					locationData.put("type", type);
					locationData.put("time", time);
					locationData.put("e_time", e_t);
					locationData.put("accuracy", accuracy);
					locationData.put("fall", fall);
					
					trackData.add(i,locationData);
				}
				result = Constant.SUCCESS_CODE;
			}else {
				result = Constant.FAIL_CODE;
			}
			getMessageFrom(json, user_id, belongProject);
			
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
		json.put("track", trackData);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());	
		return null;
	}	

}
