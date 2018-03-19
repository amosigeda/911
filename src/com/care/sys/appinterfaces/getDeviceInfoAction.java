package com.care.sys.appinterfaces;
/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;


public class getDeviceInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(getDeviceInfoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		JSONObject json = new JSONObject();
		Date start=new Date();
		//JSONObject locationData = new JSONObject();
		//JSONArray trackData = new JSONArray();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String imei  = object.getString("serie_no");
		String  bg  = object.getString("belong_project");
		try{
			if(!imei.equals("")&&!bg.equals("")){
				SettingInfo so=new SettingInfo();
				so.setCondition("serie_no='"+imei+"'and belong_project='"+bg+"'");
				List<DataMap> list=ServiceBean.getInstance().getSettingInfoFacade().getSettingInfo(so);
				if(list.size()>0){
					json.put("high", list.get(0).get("high")+"");
					json.put("weight", list.get(0).get("weight")+"");
					json.put("sex", list.get(0).get("sex")+"");
					json.put("stepd", list.get(0).get("stepd")+"");
					json.put("phone", list.get(0).get("phone")+"");
					json.put("nick_name", list.get(0).get("nick_name")+"");
					json.put("birthday", list.get(0).get("birthday")+"");
					result=Constant.SUCCESS_CODE;
				}else{
					json.put("high", "170");
					json.put("weight", "45");
					json.put("sex", "1");
					json.put("stepd", "40");
					json.put("phone","0");
					json.put("nick_name","0");
					json.put("birthday", "2015-12-12");
					result=Constant.SUCCESS_CODE;
				}
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
		
		}
		json.put(Constant.RESULTCODE, result);
		json.put("request", href);
		insertVisit(href,bg,imei, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	
		return null;
	}

}
