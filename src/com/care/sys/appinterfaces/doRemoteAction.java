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
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.settinginfo.domain.logic.SettingInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class doRemoteAction extends BaseAction {
	Log logger = LogFactory.getLog(doRemoteAction.class);	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		String user_id = "";
		String belongProject = "";
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			user_id = object.has("user_id")?object.getString("user_id"):"-1";
			String serial_number = object.getString("no");
			String type = object.getString("type");
			belongProject = object.getString("belong_project");
			int deviceId = 0;
			if(user_id.equals("-1")){
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='"+serial_number+"' and belong_project='"+belongProject+"'");  //é’ã‚…ç•¾é‰â€²æ¬¢
				List<DataMap> deviceList = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
				int size = deviceList.size();
				if(size > 0){
					user_id = ""+deviceList.get(0).getAt("user_id");
					deviceId = (Integer)deviceList.get(0).getAt("id");
				}
			}

			json.put("request", href);
			SettingInfo svo = new SettingInfo();
			svo.setCondition("serie_no = '" + serial_number
					+ "' and belong_project='" + belongProject + "'");
			SettingInfoFacade sFacade = ServiceBean.getInstance()
					.getSettingInfoFacade();
			if (type.equals("3")) {//GPS¿ª¹Ø
				String gps_on = object.getString("gps_on");							
				svo.setGps_on(gps_on);
				
				IoSession to_session = MessageManager.playerSet.containsKey(deviceId)?
						MessageManager.playerSet.get(deviceId) : null;
				
				if(to_session != null){
					to_session.write("CS*" + deviceId + "*0000*CR");
				}
			}else if(type.equals("4")){
				String light = object.has("p_l")?object.getString("p_l"):"0";
				String gps_on = "0"; 
				if(!light.equals("0")){
					gps_on = "1"; 
				}				
				svo.setGps_on(gps_on);
				svo.setLight(light);
			}else if (type.equals("0")) {//Ô¶³Ì¹Ø»ú
				svo.setShutdown("1");
				
				IoSession to_session = MessageManager.playerSet.containsKey(deviceId)?
						MessageManager.playerSet.get(deviceId) : null;
				
				if(to_session != null){
					to_session.write("CS*" + deviceId + "*0000*POWEROFF");
				}
			}else if (type.equals("1")) {
				String repellent = object.getString("repellent");
				svo.setRepellent(repellent);
			}else if (type.equals("2")) {
				String heart = object.getString("heart");
				svo.setHeart(heart);
			}
			
			result = Constant.SUCCESS_CODE;
			sFacade.updateSettingInfo(svo);
		} catch (Exception e) {
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
