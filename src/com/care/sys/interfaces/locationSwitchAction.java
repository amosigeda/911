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
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.playiteminfo.domain.PlayItemInfo;
import com.care.sys.projectimage.domain.logic.ProjectImageFacade;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.care.sys.projectinfo.domain.logic.ProjectInfoFacade;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class locationSwitchAction extends BaseAction{
	
	Log logger = LogFactory.getLog(locationSwitchAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		
		String projectName = request.getParameter("project_name");
		//String status = request.getParameter("status");
		try{
			ProjectInfoFacade pfa=ServiceBean.getInstance().getProjectInfoFacade();
			if(projectName != null && !projectName.equals("")){
				ProjectInfo vo=new ProjectInfo();
			/*	vo.setProjectName(projectName);
				vo.setStatus(status);*/
				vo.setCondition("project_name='"+projectName+"'and location_switch='"+1+"'");
				
				List<DataMap> listPi=pfa.getProjectLocationSwitchInfo(vo);
				if(listPi.size()>0){
					result = Constant.SUCCESS_CODE;
				}else{
					result = Constant.FAIL_CODE;
				}
				
				
			}	else{
				result = Constant.EXCEPTION_CODE;
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
		json.put("result", result);
		//insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		return null;
	}

}
