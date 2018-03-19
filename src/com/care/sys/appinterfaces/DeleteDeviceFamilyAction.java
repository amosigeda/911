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
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.log.LogFactory;

public class DeleteDeviceFamilyAction extends BaseAction{
	
	Log logger = LogFactory.getLog(DeleteDeviceFamilyAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href= request.getServletPath();
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
			user_id = object.has("user_id")?object.getString("user_id"):"-1";  //一定要获得设备id
			String serieNo = object.getString("device_imei");
			String phoneNumber = object.getString("device_family_number");		
			String relative_id = object.getString("relative_id");	
			belongProject = object.getString("belong_project");
			
			//get方法
//			String user_id = request.getParameter("user_id");
//			String serieNo = request.getParameter("device_imei");
//			String phoneNumber = request.getParameter("device_family_number");
//			String relative_id = request.getParameter("relative_id");	
//			String belongProject = request.getParameter("belong_project");
			
			RelativeCallInfo vo = new RelativeCallInfo();
//			vo.setStatus("2");//��ʾAPP����ӣ�2��ʾAPP��ɾ��3APP���޸ģ�0�豸����ɾ��ձ�ʾ�豸�����޸Ļ�ɾ��
//			vo.setSerieNo(serieNo);
			vo.setCondition("serie_no ='"+serieNo+"' and belong_project='"+belongProject+"' and phone_number='"+phoneNumber+"' and id ='"+relative_id+"'");
			int num = ServiceBean.getInstance().getRelativeCallInfoFacade().deleteRelativeCallInfo(vo);
			
			vo.setStatus("1");
			vo.setCondition("user_id ='"+user_id+"' and serie_no ='"+serieNo+"' and belong_project='"+belongProject+"'");
			ServiceBean.getInstance().getRelativeCallInfoFacade().updateRelativeCallInfoStatus(vo);   //把状态设为,只要有变化就变为1
			
			if(num > 0){
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}		
			json.put("request", href);
			SettingInfo setting = new SettingInfo();
			setting.setCondition("serie_no = '" + serieNo
					+ "' and belong_project ='" + belongProject + "'");
//			setting.setLight_sensor("1");
//			ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(setting);
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
		insertVisit(href, belongProject, user_id, 0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
		
		return null;
		
	}

}
