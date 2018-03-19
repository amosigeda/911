package com.care.sys.interfaces;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class getDeviceListenAction extends BaseAction{
	
	Log logger = LogFactory.getLog(getDeviceListenAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");		
		JSONObject json = new JSONObject();
		Date start = new Date();
		String serieNo = request.getParameter("serie_no");
		String belongProject = request.getParameter("b_g");
		DeviceActiveInfo vo = new DeviceActiveInfo();
		DeviceActiveInfoFacade facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		try{
			if(serieNo != null && !serieNo.equals("")){
				StringBuffer sb = new StringBuffer();				
				sb.append("device_imei = '"+serieNo+"'");				
				if(belongProject != null && !"".equals(belongProject)){					
					sb.append("and belong_project='"+belongProject+"'");
				}
				sb.append(" and listen_type='1' and device_disable='1'");
				vo.setCondition(sb.toString());
				List<DataMap> list = facade.getDeviceActiveInfo(vo);
				if(list.size() > 0){
					String userId = (String)list.get(0).getAt("user_id");
					AppUserInfo appUser = new AppUserInfo();
					appUser.setCondition("id='"+userId+"'");
					List<DataMap> appUserList = ServiceBean.getInstance().getAppUserInfoFacade().getAppUserInfo(appUser);
					if(appUserList.size() > 0){
						String phoneNumber = (String)appUserList.get(0).getAt("user_name");
						json.put("phone_number", phoneNumber);
						
						//�ظ�������е�type��Ϊ0
						vo.setListenType("0");
						facade.updateDeviceActiveInfo(vo);
					}															
				}else{
					json.put("result", 0);
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
			json.put("result", -1);
		}
		String href= request.getServletPath();
		insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
	}
}
