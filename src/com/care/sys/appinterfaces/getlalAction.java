package com.care.sys.appinterfaces;
/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;


public class getlalAction extends BaseAction{
	Log logger = LogFactory.getLog(getlalAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		JSONObject json = new JSONObject();
		JSONObject locationData = new JSONObject();
		JSONArray trackData = new JSONArray();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String serial_number  = object.getString("serial_number");
		String sport_type  = object.getString("sport_type");
		try{			
			json.put("request", href);
			DeviceActiveInfoFacade facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
			DeviceActiveInfo vo = new DeviceActiveInfo();
		    vo.setCondition("a.serie_no = '"+serial_number+"'and b.sport_type = '"+sport_type+"' group by a.upload_time");
	
				List<DataMap> list =facade.getlal(vo);
				int size =list.size();
				if(size <= 0){  
					result = Constant.SUCCESS_CODE;
					json.put("resultCode", result);
				}else{
					for(int i=0;i<size;i++){
						DataMap locationMap=(DataMap)list.get(i);
						locationData.put("longitude", locationMap.getAt("longitude")+"");
						locationData.put("latitude", locationMap.getAt("latitude")+"");
						locationData.put("upload_time", locationMap.getAt("upload_time")+"");
						trackData.add(i,locationData);	
					}
					result = Constant.SUCCESS_CODE;
					json.put("resultCode", result);
				}	
				
		}catch(Exception e){
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
			json.put("resultCode", result);
		
		}
		json.put("track", trackData);
		//insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	
		return null;
	}

}
