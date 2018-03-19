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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DevicePowerOff extends BaseAction{

	Log logger = LogFactory.getLog(DevicePowerOff.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		try{
			String serieNo = request.getParameter("serie_no");
			String type = request.getParameter("type");
			String belongProject = request.getParameter("b_g");
			
			String user_id = "-1";			
			if(serieNo != null && type != null){
				
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei='" + serieNo
						+ "' and belong_project ='" + belongProject
						+ "' and device_disable='1'");
				List<DataMap> deviceActiveLists = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
				if(deviceActiveLists.size() > 0){
					user_id = (String)deviceActiveLists.get(0).getAt("user_id");
				}
				if(!"-1".equals(user_id)){
					String content = "8@" + serieNo + "@" + "0";
					if(type.equals("0")){
						content = "9@" + serieNo + "@" + "0";
					}else if(type.equals("1")){
						content = "10@" + serieNo + "@" + "0";
					}
					MsgInfo msgInfo = new MsgInfo();
					
					msgInfo.setToId(user_id);
					msgInfo.setFromId(user_id);
					msgInfo.setIsHandler("0"); // ��????
					msgInfo.setMsgLevel("1"); // ??????
					msgInfo.setMsgHandlerDate(new Date());
					msgInfo.setMsgContent(content);
					msgInfo.setBelongProject(belongProject);
					msgInfo.setMsgOccurDate(new Date());
					ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
					result = Constant.SUCCESS_CODE;
				}else{
					result = Constant.FAIL_CODE;
				}
			}else{
				result = Constant.FAIL_CODE;
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
			json.put(Constant.EXCEPTION, sb.toString());
		}
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
		return null;
	}
}
