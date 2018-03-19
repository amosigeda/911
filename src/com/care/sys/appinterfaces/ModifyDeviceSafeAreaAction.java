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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.safearea.domain.SafeArea;
import com.care.sys.safearea.domain.logic.SafeAreaFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class ModifyDeviceSafeAreaAction extends BaseAction {
	
	Log logger = LogFactory.getLog(ModifyDeviceSafeAreaAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		JSONArray trackData = new JSONArray();
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
			int type = Integer.parseInt(object.getString("type"));
			user_id = object.getString("user_id");
			String device_id = object.getString("device_imei");
			String device_safe_id = object.getString("device_safe_id");   //û�е�ʱ��"0"
			String lat = object.getString("lat");
			String lng = object.getString("lng");
			String device_safe_range = object.getString("device_safe_range");
			String device_safe_name = object.getString("device_safe_name");
			String device_safe_addr = object.getString("device_safe_addr");
			String device_safe_effect_time = object.getString("device_safe_effect_time");
			belongProject = object.getString("belong_project");
			
			device_safe_name = Constant.transCodingToUtf(device_safe_name);
			device_safe_addr = Constant.transCodingToUtf(device_safe_addr);
			
			SafeArea vo = new SafeArea();
			SafeAreaFacade facade = ServiceBean.getInstance().getSafeAreaFacade();
			if(type == 1) {//����
				vo.setArea_effect_time(device_safe_effect_time);
				vo.setStatus("0");
				vo.setArea_name(device_safe_name);
				vo.setLatitude(lat);
				vo.setLongitude(lng);
				vo.setSafe_range(Integer.parseInt(device_safe_range));
				vo.setSeriNo(device_id);
				vo.setUserId(user_id);
				vo.setCreate_time(new Date());
				vo.setSafeAddress(device_safe_addr);
				vo.setBelongProject(belongProject);
				facade.insertSafeArea(vo);
				
				int countMax = facade.getSafeAreaMaxCount(vo);  //�Ѿ�������,����������id
				json.put("device_safe_id", String.valueOf(countMax));   //��ȫ����id
				
				result = Constant.SUCCESS_CODE;
			}else {//�޸�
				vo.setCondition("seri_no = '"+device_id+"' and belong_project='"+belongProject+"' and id = '"+device_safe_id+"'");
				List<DataMap> list = facade.getSafeArea(vo);
				if(list.size() > 0) {
					result = Constant.SUCCESS_CODE;
					vo.setArea_effect_time(device_safe_effect_time);
					vo.setArea_name(device_safe_name);
					vo.setLatitude(lat);
					vo.setLongitude(lng);
					vo.setSafe_range(Integer.parseInt(device_safe_range));
					vo.setStatus("0");
					vo.setUpdate_time(new Date());
					
					facade.updateSafeArea(vo);
				}else {
					result = Constant.FAIL_CODE;
				}
			}
			json.put("request", href);
			
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
		json.put("track", trackData);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());	
		return null;
	}	
}
