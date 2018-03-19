package com.care.sys.nineoneoneinterfaces;

/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
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
import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.care.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class GetLocationAction extends BaseAction {
	Log logger = LogFactory.getLog(GetLocationAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href = request.getServletPath();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		JSONObject locationData = new JSONObject();
		JSONArray trackData = new JSONArray();
		String imei = object.getString("serie_no");
		String type = object.getString("type");//1获取最新点2获取时间段
		String startTime = object.getString("start_time");
		String endTime = object.getString("end_time");
		try {
			LocationInfoFacade info = ServiceBean.getInstance().getLocationInfoFacade();
			LocationInfo vo = new LocationInfo(); 
			
			if("1".equals(type)){
				vo.setCondition("DEVICE_SERIAL_NUMBER='"+imei+"'order by OBJ_ID desc limit 1");
                List<DataMap> list=info.getNineOneOneLocationInfoList(vo);   
                if(list.size()>0){
                	json.put("lat", list.get(0).get("LAT")+"");
                	json.put("lon", list.get(0).get("LON")+"");
                	json.put("speed", list.get(0).get("SPEED")+"");
                	json.put("elevation", list.get(0).get("ELEVATION")+"");
                	json.put("direction", list.get(0).get("DIRECTION")+"");
                	json.put("bat_status", list.get(0).get("BAT_STATUS")+"");
                	json.put("electric_quantity", list.get(0).get("ELECTRIC_QUANTITY")+"");
                	json.put("fly", list.get(0).get("FLY")+"");
                	json.put("update_time", list.get(0).get("INSERT_TIME")+"");
                	
                	result = Constant.SUCCESS_CODE;
                }else{
                	result = Constant.FAIL_CODE;
                }
			}else if("2".equals(type)){
				vo.setCondition("DEVICE_SERIAL_NUMBER='"+imei+"' and INSERT_TIME>='"+startTime+"' and INSERT_TIME<='"+endTime+"'");
				List<DataMap> list=info.getNineOneOneLocationInfoList(vo);   
			    if(list.size()>0){
			    	for(int i=0;i<list.size();i++){
			    		locationData.put("lat", list.get(i).get("LAT")+"");
			    		locationData.put("lon", list.get(i).get("LON")+"");
			    		locationData.put("speed", list.get(i).get("SPEED")+"");
			    		locationData.put("elevation", list.get(i).get("ELEVATION")+"");
			    		locationData.put("direction", list.get(i).get("DIRECTION")+"");
			    		locationData.put("bat_status", list.get(i).get("BAT_STATUS")+"");
			    		locationData.put("electric_quantity", list.get(i).get("ELECTRIC_QUANTITY")+"");
			    		locationData.put("fly", list.get(i).get("FLY")+"");
			    		locationData.put("update_time", list.get(i).get("INSERT_TIME")+"");
			    		trackData.add(i,locationData);
			    	}
			    	json.put("track", trackData);
			    	result = Constant.SUCCESS_CODE;
			    }else{
			    	result = Constant.FAIL_CODE;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb1 = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb1.append(resultSb);
			logger.error(e);
			result = Constant.EXCEPTION_CODE;
		}
		json.put(Constant.RESULTCODE, result);
		json.put("request", href);
		
		/*insertVisit(href, bg, imei, 1, start, new Date(), json.toString()
				.getBytes("utf-8").length);*/
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	// double 相加
	public double jia(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}

}
