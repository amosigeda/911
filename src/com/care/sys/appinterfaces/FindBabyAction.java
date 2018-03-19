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
import org.apache.mina.core.session.IoSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.app.MessageManager;
import com.care.app.ServerHandler;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class FindBabyAction extends BaseAction{

	Log logger = LogFactory.getLog(FindBabyAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
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
			belongProject = object.has("belong_project")?object.getString("belong_project"):"1";
			
			IoSession to_session = null;						
			DeviceActiveInfo dai = new DeviceActiveInfo();
			dai.setCondition("device_imei ='"+serial_number+"' and belong_project='"+belongProject+"'");
			List<DataMap> deviceList = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(dai);
			if(deviceList.size() > 0){
				int deviceId = (Integer)deviceList.get(0).getAt("id");
				to_session = MessageManager.playerSet.containsKey(deviceId) ? MessageManager.playerSet.get(serial_number) : null;
				
				if (to_session != null) {
					to_session.write("CS*" + serial_number + "*0000*FIND");
					
					PhoneInfo vo = new PhoneInfo();
					vo.setCondition("serie_no = '"+serial_number+"' and belong_project='"+belongProject+"'");
					json.put("request", href);
					
					PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
					
					List<DataMap> list = facade.getPhoneInfo(vo);//锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷锟斤拷锟�
					if(list.size() > 0){
						vo.setAlarmBellType("1");
						facade.updatePhoneInfo(vo);
						result = Constant.SUCCESS_CODE;
					}else {
						result = Constant.FAIL_CODE;
					}
				}										
			}
			
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
