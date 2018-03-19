package com.care.sys.interfaces;
/**
 * �ֻ��������ò���ӿ�
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
import com.care.sys.playiteminfo.domain.PlayItemInfo;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class lowVoltageAction extends BaseAction{
	
	Log logger = LogFactory.getLog(lowVoltageAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href= request.getServletPath();
		String serieNo = request.getParameter("device");
		String degree = request.getParameter("degree");
		String belongProject = request.getParameter("b_g");
		try{
			
			if(serieNo != null && !serieNo.equals("")){
				SettingInfo settingVo = new SettingInfo();
				StringBuffer sb = new StringBuffer();
				sb.append("serie_no = '"+serieNo+"'");
				if(belongProject != null && !"".equals(belongProject)){					
					sb.append(" and belong_project='"+belongProject+"'");
				}
				settingVo.setCondition(sb.toString());
				List<DataMap> settingList = ServiceBean.getInstance().getSettingInfoFacade().getSettingInfo(settingVo);
				if(settingList.size() > 0){
					
					//settingVo.setLevels(leavels);
					settingVo.setDegree(degree);
					settingVo.setCondition("serie_no='"+serieNo+"'");
					Integer up=ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(settingVo);
					if(up>0){
						result = Constant.SUCCESS_CODE;
					}else{
						result=Constant.FAIL_CODE;
					}
				}else{
					settingVo.setBelongProject(belongProject);
					settingVo.setSerieNo(serieNo);
					settingVo.setDegree(degree);
					int zengjia=ServiceBean.getInstance().getSettingInfoFacade().insertSettingInfo(settingVo);
					if(zengjia>0){
						result = Constant.SUCCESS_CODE;
					}else{
						result = Constant.FAIL_CODE;
					}
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
		}
		json.put("resultCode", result);
		insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
	}

}
