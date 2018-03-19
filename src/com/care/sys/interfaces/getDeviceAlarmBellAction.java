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
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class getDeviceAlarmBellAction extends BaseAction {

	Log logger = LogFactory.getLog(getDeviceAlarmBellAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String belongProject = request.getParameter("b_g");
		PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
		try {
			if (serieNo != null && !serieNo.equals("")) {
				PhoneInfo vo = new PhoneInfo();
				StringBuffer sb = new StringBuffer();				
				sb.append("serie_no = '"+serieNo+"'");				
				if(belongProject != null && !"".equals(belongProject)){					
					sb.append("and belong_project='"+belongProject+"'");
				}
				vo.setCondition(sb.toString());
				List<DataMap> list = facade.getPhoneInfo(vo);
				if (list.size() > 0) {
					String type = (String) list.get(0).getAt("alarm_bell_type");
					if (type.equals("1")) {// 璇存槑鏈夎鎶�
						json.put("result", 1);
						vo.setAlarmBellType("0");
						vo.setSerieNo(serieNo);
						facade.updatePhoneInfo(vo);
					} else {
						json.put("result", 0);
					}
				}
			}

		} catch (Exception e) {
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
