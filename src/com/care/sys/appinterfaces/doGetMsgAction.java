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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class doGetMsgAction extends BaseAction{

	Log logger = LogFactory.getLog(doGetMsgAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		ServiceBean instance = ServiceBean.getInstance();
		JSONObject json = new JSONObject();
		JSONArray msgData = new JSONArray();
		String user_id = "";
		String belongProject = "";
		
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			user_id = object.getString("user_id");
			belongProject = object.getString("belong_project");
			
			try{		//get����
//			String user_id = request.getParameter("user_id");
			MsgInfo msgInfo = new MsgInfo();
			String condition = Constant.getDaysAgoCondition("msg_handler_date", -7);
			msgInfo.setCondition("to_id ='"+user_id+"' and belong_project='"+belongProject+"' and is_handler ='0' and "+condition);
			msgInfo.setOrderBy("id");
			msgInfo.setSort("1");   //��id����
			msgInfo.setFrom(0);
    		msgInfo.setPageSize(100);
    		
			List<DataMap> msgList = instance.getMsgInfoFacade().getMsgInfo(msgInfo);
			int msgCount = msgList.size();
			for(int j=0;j<msgCount;j++){
				JSONObject msgJson = new JSONObject();
				String msg_id = ""+ msgList.get(j).getAt("id");
				String msg_content = ""+ msgList.get(j).getAt("msg_content");
				String msg_date = "" + msgList.get(j).getAt("msg_handler_date");
				
				msgJson.put("msg_id", msg_id);
				msgJson.put("msg_content", msg_content);
				msgJson.put("msg_date", msg_date);
				
				msgData.add(j,msgJson);
			}
			
			json.put("msg_count", msgCount);  //��Ϣ����
			json.put("msg_array", msgData); 
			result = Constant.SUCCESS_CODE;
			
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
			json.put(Constant.EXCEPTION, sb1.toString());
		}
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());		
		return null;
	}
}
