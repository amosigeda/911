package com.care.sys.interfaces;

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
import com.care.common.lang.CommUtils;
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.care.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DeviceDisturbAction extends BaseAction {
Log logger = LogFactory.getLog(DeviceDisturbAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");		
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String belongProject = request.getParameter("b_g");
		DirectiveInfo vo = new DirectiveInfo();
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
		DirectiveInfoFacade facade = ServiceBean.getInstance().getDirectiveInfoFacade();
		try{
		    List<DataMap> list = facade.getDirectiveInfo(vo);
			if(list.size() > 0) {
				String time1 = (String)list.get(0).getAt("mdistime");
				String time2 = (String)list.get(0).getAt("tdistime");
				String time3 = (String)list.get(0).getAt("wdistime");
				String time4 = (String)list.get(0).getAt("thdistime");
				String time5 = (String)list.get(0).getAt("fdistime");
				String time6 = (String)list.get(0).getAt("sdistime");
				String time7 = (String)list.get(0).getAt("sudistime");
				String distrub = (String)list.get(0).getAt("distrub");
				json.put("disturb", distrub);
				String week = "";
				if(belongProject.equals("2")){
					int day = CommUtils.getDay();
					switch(day){
					case 0:
						if(time7 != null && !"".equals(time7)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("sunday"+i, dataSpilt[i]);
							}
						}else {
							json.put("sunday0", "-1");
						}
						break;
					case 1:
						if(time1 != null && !"".equals(time1)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("monday"+i, dataSpilt[i]);
							}
						}else {
							json.put("monday0", "-1");
						}
						break;
					case 2:
						if(time2 != null && !"".equals(time2)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("tuesday"+i, dataSpilt[i]);
							}
						}else {
							json.put("tuesday0", "-1");
						}
						break;
					case 3:
						if(time3 != null && !"".equals(time3)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("wednesday"+i, dataSpilt[i]);
							}
						}else {
							json.put("wednesday0", "-1");
						}
						break;
					case 4:
						if(time4 != null && !"".equals(time4)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("thursday"+i, dataSpilt[i]);
							}
						}else {
							json.put("thursday0", "-1");
						}
						break;
					case 5:
						if(time5 != null && !"".equals(time5)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("friday"+i, dataSpilt[i]);
							}
						}else {
							json.put("friday0", "-1");
						}
						break;
					case 6:
						if(time6 != null && !"".equals(time6)) {
							String[] dataSpilt = time1.split(";");
							for(int i=0;i<dataSpilt.length;i++){
								json.put("saturday"+i, dataSpilt[i]);
							}
						}else {
							json.put("saturday0", "-1");
						}
						break;				
					}
				}else{
					if(time7 != null && !"".equals(time7)) {
						week = "sunday".concat("-").concat(time7);
					}else {
						week = "sunday".concat("-").concat("");
					}
					if(time1 != null && !"".equals(time1)) {
						week = week.concat("#").concat("monday").concat("-").concat(time1);
					}else {
						week = week.concat("#").concat("monday").concat("-").concat("");
					}
					if(time2 != null && !"".equals(time2)) {
				         week = week.concat("#").concat("tuesday").concat("-").concat(time2);
					}else {
						 week = week.concat("#").concat("tuesday").concat("-").concat("");
					}
					if(time3 != null && !"".equals(time3)) {
						week = week.concat("#").concat("wednesday").concat("-").concat(time3);
					}else {
						week = week.concat("#").concat("wednesday").concat("-").concat("");
					}
					if(time4 != null && !"".equals(time4)) {
						week = week.concat("#").concat("thursday").concat("-").concat(time4);
					}else {
						week = week.concat("#").concat("thursday").concat("-").concat("");
					}
					if(time5 != null && !"".equals(time5)) {
						week = week.concat("#").concat("friday").concat("-").concat(time5);
					}else {
						week = week.concat("#").concat("friday").concat("-").concat("");
					}
					if(time6 != null && !"".equals(time6)) {
						week = week.concat("#").concat("saturday").concat("-").concat(time6);
					}else {
						week = week.concat("#").concat("saturday").concat("-").concat("");
					}
					json.put("week", week);
				}
				
			   json.put("resultCode", 1);
			}else {
				json.put("resultCode", 0);
			}
			String href= request.getServletPath();
			insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		}catch(Exception e){
			e.printStackTrace();
			json.put("resultCode", -1);
		}	
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	
		return null;
	}
}
