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
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.care.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DeviceLowAction extends BaseAction {

	Log logger = LogFactory.getLog(DeviceLowAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");		
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String electricity = request.getParameter("electricity");
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
				vo.setLowelectricity(electricity);
				vo.setIsLow("1");
				facade.updateDirectiveInfo(vo);
			   json.put("resultCode", 1);
			}else {
				vo.setSerie_no(serieNo);
				vo.setLowelectricity(electricity);
				vo.setIsLow("1");
				vo.setBelongProject(belongProject);
				facade.insertDirectiveInfo(vo);
				json.put("resultCode", 1);
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
