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
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class ListenAction extends BaseAction{

	
	Log logger = LogFactory.getLog(ListenAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		IoSession to_session = null;
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		String user_id = "";
		String belongProject = "";
		String serial_number ="";
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String phone = object.getString("phone");
			user_id = object.has("user_id")?object.getString("user_id"):"-1";
			serial_number = object.getString("serial_number");
			belongProject = object.getString("belong_project");
			
			DeviceActiveInfo vo = new DeviceActiveInfo();
			vo.setCondition("device_imei = '"+serial_number+"' and belong_project='"+belongProject+"'");
			json.put("request", href);
			
			DeviceActiveInfoFacade facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
			
			List<DataMap> list = facade.getDeviceActiveInfo(vo);//�������Ƿ������
			if(list.size() > 0){
				user_id = ""+list.get(0).getAt("user_id");
				vo.setListenType("1");
				facade.updateDeviceActiveInfo(vo);
				result = Constant.SUCCESS_CODE;

				//ZST@20151214 
				to_session = MessageManager.snSet.containsKey(serial_number)?
						MessageManager.snSet.get(serial_number):null;
								     
				if(to_session!=null){		     
					to_session.write("SG*"+serial_number+"*0007*MONITOR");
				}
			}else {
				result = Constant.FAIL_CODE;
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
