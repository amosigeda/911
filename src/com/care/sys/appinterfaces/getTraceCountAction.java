package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.godoing.rose.lang.DataMap;

public class getTraceCountAction extends BaseAction{
	
	Log logger = LogFactory.getLog(getTraceCountAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
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
			String serieNo = object.getString("serie_no");
			belongProject = object.has("belong_project")?object.getString("belong_project"):"1";
/*			
			user_id=request.getParameter("user_id");
			String serieNo=request.getParameter("serie_no");
			belongProject=request.getParameter("belong_project");*/
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -3);
			String time = format.format(c.getTime());
			LocationInfo vo = new LocationInfo();
			vo.setCondition("serie_no = '" + serieNo 
					+ "' and belong_project = '" + belongProject 
					+ "' and substring(upload_time,1,10) >= '" + time + "'");
			List<DataMap> list = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfoGroupByTime(vo);
			JSONObject dateTimeJson = new JSONObject();
			if(list.size() > 0){
				for(int i=0;i<list.size(); i++){
					Date d = (Date)list.get(i).getAt("upload_time");
					dateTimeJson.accumulate("d"+i, format.format(d));
					jsonArray.add(dateTimeJson);
					dateTimeJson.clear();
				}
				
			}
			result = Constant.SUCCESS_CODE;
			json.put("date_time", jsonArray);
			
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
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());	
		return null;
	}

}
