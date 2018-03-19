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

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class LocationAction extends BaseAction {
	
	Log logger = LogFactory.getLog(LocationAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		JSONObject locationData = new JSONObject();
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
			user_id = object.has("user_id")?object.getString("user_id"):"-1";
			String serial_number = object.getString("serial_number");
			belongProject = object.getString("belong_project");
			String map = object.has("map")?object.getString("map"):"0";   //默认是关闭
			
			if(user_id.equals("-1")){
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+serial_number+"' and belong_project='"+belongProject+"'");  //鍒ゅ畾鏉′欢
				List<DataMap> deviceList = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
				int size = deviceList.size();
				if(size > 0){
					user_id = ""+deviceList.get(0).getAt("user_id");
				}
			}
			
			LocationInfo vo = new LocationInfo();
			vo.setCondition("serie_no = '"+serial_number+"' and belong_project='"+belongProject+"' and longitude != '0.000000' and latitude != '90.000000' and s_t ='1'");
			json.put("request", href);
			vo.setOrderBy("upload_time");
			vo.setSort("1");
			vo.setFrom(0);
			vo.setPageSize(1);
			LocationInfoFacade facade = ServiceBean.getInstance().getLocationInfoFacade();
			
			List<DataMap> list = facade.getLocationInfo(vo);//�������Ƿ������
			if(list.size() > 0){
				for(int i=0;i<list.size();i++){
					DataMap locationMap=(DataMap)list.get(i);
					String lng=""+locationMap.getAt("change_longitude");
					String lat=""+locationMap.getAt("change_latitude");
					
					String no_trans_lng=""+locationMap.getAt("longitude");
					String no_trans_lat=""+locationMap.getAt("latitude");
					
					String battery =""+ locationMap.getAt("battery");
					String type = ""+locationMap.getAt("location_type");
					String time=""+locationMap.getAt("upload_time");
					String e_t = ""+locationMap.getAt("e_t");
					String accuracy = ""+locationMap.getAt("accuracy");
					locationData.put("lat", lat);
					locationData.put("lng", lng);
					locationData.put("no_trans_lat", no_trans_lat);
					locationData.put("no_trans_lng", no_trans_lng);
					locationData.put("battery", battery);
					locationData.put("type", type);
					locationData.put("time", time);
					locationData.put("e_time", e_t);
					locationData.put("accuracy", accuracy);
				}
				result = Constant.SUCCESS_CODE;
			}else {
				result = Constant.FAIL_CODE;
			}
			SettingInfo settingInfo = new SettingInfo();
			settingInfo.setCondition("serie_no = '"+serial_number+"' and belong_project ='"+belongProject+"'");
			settingInfo.setMap(map);
			ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(settingInfo);
			
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
			System.out.println("sb"+sb);
			logger.error(e);
			System.out.println("e"+e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}
		json.put(Constant.RESULTCODE, result);
		json.put("location", locationData);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());		
		return null;
	}	
}
