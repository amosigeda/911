package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

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
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.log.LogFactory;

public class DoDeleteShareDeviceAction extends BaseAction{

	Log logger = LogFactory.getLog(DoDeleteShareDeviceAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		JSONObject json = new JSONObject();
		ServiceBean instance = ServiceBean.getInstance();
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
			String to_user_id = object.getString("to_user_id"); 
			String message_level = object.getString("message_level");       //优先级
			String belongProject = object.getString("belong_project");
			
			ShareInfo shareInfo = new ShareInfo();
			MsgInfo msgInfo = new MsgInfo();
			
			if(to_user_id.equals("0")){
				shareInfo.setCondition("user_id ='"+user_id+"' and device_imei ='"+device_imei+"' and is_priority='0' and belong_project='" + belongProject + "'");
			}else{
				shareInfo.setCondition("to_user_id ='"+to_user_id+"' and device_imei ='"+device_imei+"' and is_priority='0' and belong_project='" + belongProject + "'");
			}
			
			int num = instance.getShareInfoFacade().deleteShareInfo(shareInfo);
			if(num > 0){
				result = Constant.SUCCESS_CODE;
			}
			msgInfo.setFromId(user_id);
			msgInfo.setToId(to_user_id);
			msgInfo.setMsgContent("13@"+device_imei+"@0");
			msgInfo.setMsgHandlerDate(new Date());
			msgInfo.setMsgLevel(message_level);
			msgInfo.setIsHandler("0");
			msgInfo.setBelongProject(belongProject);
			msgInfo.setMsgOccurDate(new Date());
			instance.getMsgInfoFacade().insertMsgInfo(msgInfo);
			
		}catch (Exception e) {
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
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
}
