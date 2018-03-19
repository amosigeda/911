package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
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
import com.care.sys.LocRadiusPoiResponse.domain.Cells;
import com.care.sys.LocRadiusPoiResponse.domain.JsonUtil;
import com.care.sys.LocRadiusPoiResponse.domain.RemoteUtil;
import com.care.sys.LocRadiusPoiResponse.domain.WI;
import com.care.sys.LocRadiusPoiResponse.domain.WifiList;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.godoing.rose.log.LogFactory;

public class LocRadiusPoiResponse  extends BaseAction {
	Log logger = LogFactory.getLog(LocRadiusPoiResponse.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		LocRadiusPoiResponse loc = null;
		String result = null;
		String mt = null;
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
     		String	x = object.getString("x");			
	     	String	w = object.getString("ws");			
	     	String	IMEI = object.getString("imei");			
	     	String	url = "http://minigps.net/cw?"+object.getString("url_data");
	     	// p=1&needaddress=1&mt=2
			if (x != null) {
				url = url + "&x=" + x;
			}
			if (w != null) {
				result = RemoteUtil.request(url, "POST",
						"application/json;charset=utf-8", w);
			} else {
				result = RemoteUtil.request(url, "GET",
						"application/json;charset=utf-8", null);
			}
			
			if (result != null) {
				System.out.print(result);
				loc = (LocRadiusPoiResponse) JsonUtil.fromJson(
						LocRadiusPoiResponse.class, result);
			}
			JSONObject object1 = JSONObject.fromObject(loc.toString());
			String address=object1.getString("address");
			//Double lat=Double.parseDouble(object.getString("lat"));
			//Double lon=Double.parseDouble(object.getString("lon"));
			String lat=object1.getString("lat");
			String lon=object1.getString("lon");
			String Cause=object1.getString("cause");
			String Map=object1.getString("map");
			Integer Status=Integer.parseInt(object1.getString("status"));
			Integer Radius=Integer.parseInt(object1.getString("radius"));
			
		    json.accumulate("cause", Cause);
			json.accumulate("address", address);
			json.accumulate("map", Map);
			json.accumulate("status",Status);
			json.accumulate("lat",lat);
			json.accumulate("lon",lon);
			
             LocationInfo vo=new LocationInfo();
             vo.setCause(Cause);
             vo.setAddress(address);
             vo.setMap(Map);
             vo.settStatus(Status);
             vo.setLatitude(lat);
             vo.setLongitude(lon);
             vo.setRadius(Radius);
             vo.setImei(IMEI);
			ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(vo);
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
			json.put(Constant.EXCEPTION, sb.toString());
		
			
		}
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());	
		return null;
		
	}
/*	private String getWParameter(List<WI> ws) {
		String w = null;
		if (ws != null && ws.size() > 0) {
			WifiList wlist = new WifiList();
			wlist.setWs(ws);
			w = JsonUtil.toJson(wlist);
		}
		return w;
	}*/
/*	private String getXParameter(List<Cells> cells) {
		String x = null;
		if (cells != null && cells.size() > 0) {
			// mcc-mnc
			x = "%x-%x";
			Cells c = cells.get(0);
			x = String.format(x, c.getMcc(), c.getMnc());
			for (int i = 0; i < cells.size(); ++i) {
				c = cells.get(i);
				x += "-%x-%x-%x";
				x = String.format(x, c.getLac(), c.getCellid(), c.getSdb());
			}
		}
		return x;
	}*/

}
