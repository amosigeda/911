package com.care.sys.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class syncDeviceTimeAction extends BaseAction {
	
	Log logger = LogFactory.getLog(syncDeviceTimeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");		
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String belongProject = request.getParameter("b_g");
		
		try{
			PhoneInfoFacade phoneInfofacade = ServiceBean.getInstance()
					.getPhoneInfoFacade();
			PhoneInfo phoneInfo = new PhoneInfo();
			phoneInfo.setCondition("serie_no='" + serieNo
					+ "' and belong_project='" + belongProject + "'");
			if(phoneInfofacade.getPhoneInfoCount(phoneInfo) <= 0){
				json.put("resultCode", -1);   //不存在这个imei
			}else{
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
				String time = sdf.format(now);
				json.put("resultCode", 1);
				json.put("time", time);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			json.put("resultCode", -1);
		}	
		String href= request.getServletPath();
		insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());		
		return null;
	}

}
