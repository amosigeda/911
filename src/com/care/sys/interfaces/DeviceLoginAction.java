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
import com.care.handler.TzHandler;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DeviceLoginAction extends BaseAction {
	Log logger = LogFactory.getLog(DeviceLoginAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");		
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String belongProject = request.getParameter("b_g");
		PhoneInfo vo = new PhoneInfo();
		StringBuffer sb = new StringBuffer();
		if(serieNo != null && !"".equals(serieNo)){
			sb.append("serie_no = '"+serieNo+"'");
		}
		if(belongProject != null && !"".equals(belongProject)){
			if(sb.length() > 0){
				sb.append(" and ");
			}
			sb.append("belong_project='"+belongProject+"'");
		}
		vo.setCondition(sb.toString());
		DeviceActiveInfo daVo = new DeviceActiveInfo();
		daVo.setCondition(sb.toString());
		PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
		DeviceActiveInfoFacade dfacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		List<DataMap> list = facade.getPhoneInfo(vo);
		try{
			if(list.size() > 0) {
            int id = (Integer)list.get(0).getAt("id");
            vo.setId(id);
            vo.setSerieNo((String)list.get(0).getAt("serie_no"));
//            new TzHandler().TzLogin(vo);
            List<DataMap> dlist = dfacade.getDeviceActiveInfo(daVo);
            if(dlist.size() > 0) {
            	json.put("user_id", dlist.get(0).getAt("user_id"));
            }
			json.put("result", 1);
			json.put("id", id);
			}
			SettingInfo settingInfo = new SettingInfo();   //????½?,???h????
			settingInfo.setCondition("serie_no ='" + serieNo
					+ "' and belong_project = '" + belongProject + "'");
			if (ServiceBean.getInstance().getSettingInfoFacade()
					.getSettingInfoCount(settingInfo) <= 0) {
				settingInfo.setSerieNo(serieNo);
				settingInfo.setVolume(30);
				settingInfo.setLight("0");
				settingInfo.setMap("0");
				settingInfo.setFallOn("0");
				settingInfo.setGps_on("0");   //I????
				settingInfo.setHeart("0");
				settingInfo.setBelongProject(belongProject);
				ServiceBean.getInstance().getSettingInfoFacade()
						.insertSettingInfo(settingInfo);
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
