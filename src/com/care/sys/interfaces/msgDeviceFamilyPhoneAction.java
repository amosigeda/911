package com.care.sys.interfaces;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

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
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.relativecallinfo.domain.logic.RelativeCallInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class msgDeviceFamilyPhoneAction extends BaseAction{
	
	Log logger = LogFactory.getLog(msgDeviceFamilyPhoneAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date start = new Date();
		String href= request.getServletPath();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String belongProject = request.getParameter("b_g");
		RelativeCallInfo vo = new RelativeCallInfo();
		RelativeCallInfoFacade facade = ServiceBean.getInstance().getRelativeCallInfoFacade();
		
		String resultType = request.getParameter("result_type");//????豸????????????????????
		try{
			if(serieNo != null && !serieNo.equals("")){
				StringBuffer sb = new StringBuffer();
				sb.append("serie_no = '"+serieNo+"'");				
				if(belongProject != null && !"".equals(belongProject)){					
					sb.append(" and belong_project='"+belongProject+"'");
				}
				vo.setCondition(sb.toString());
				List<DataMap> list = facade.getRelativeCallInfo(vo);
				JSONArray arr = new JSONArray();
				JSONObject object = new JSONObject();
				int length = list.size();
				for(int i=0;i<length;i++){
					String phone_number = ""+list.get(i).getAt("phone_number");
					String short_number = ""+list.get(i).getAt("short_number");
					if(!short_number.equals("0") && short_number != null){
						phone_number = short_number;  //取短号
					}
					object.accumulate("type", list.get(i).getAt("status"));
					object.accumulate("family_id", list.get(i).getAt("id"));
					object.accumulate("family_type", list.get(i).getAt("relative_type"));
					object.accumulate("family_name", list.get(i).getAt("nick_name"));
					object.accumulate("family_phone", phone_number);
					arr.add(object);
					object.clear();
				}
				vo.setStatus("0");
				ServiceBean.getInstance().getRelativeCallInfoFacade()
						.updateRelativeCallInfoStatus(vo);
				json.put("family_group", arr);
				
				json.put("result", 1);
			}
			SettingInfo setting = new SettingInfo();
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
			DeviceActiveInfoFacade dFacade = ServiceBean.getInstance()
					.getDeviceActiveInfoFacade();
			
			deviceActiveInfo.setCondition("device_imei='" + serieNo
					+ "' and belong_project ='" + belongProject
					+ "' and device_disable='1'");
			List<DataMap> deviceActiveLists = dFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(deviceActiveLists.size() <= 0){
				setting.setCondition("serie_no = '" + serieNo
						+ "' and belong_project ='" + belongProject + "'");
//				setting.setLight_sensor("0");
//				ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(setting);
			}
			logger.info(serieNo+"=亲情号码="+json.toString());
			
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
			json.put("result", -1);
		}
		insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
	}

}
