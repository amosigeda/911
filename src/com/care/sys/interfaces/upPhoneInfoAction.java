package com.care.sys.interfaces;
/**
 * �ֻ��ϴ���Ϣ�ӿ�
 */
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
import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.care.sys.deviceLogin.domain.logic.DeviceLoginFacade;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;


public class upPhoneInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(upPhoneInfoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date start = new Date();
		JSONObject json = new JSONObject();
		String serieNo = request.getParameter("serie_no");
		String productModel = request.getParameter("device_product_model");
		String firmwareEdition = request.getParameter("device_firmware_edition");
		String phone = request.getParameter("device_phone");
		String belongProject = request.getParameter("b_g");
		try{			
			DeviceLogin deviceLogin = new DeviceLogin();
			
			PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
			DeviceLoginFacade deviceLoginFacade = ServiceBean.getInstance().getDeviceLoginFacade();
			
			if(serieNo != null && !serieNo.equals("") && productModel != null && !productModel.equals("")
					&& firmwareEdition != null && !firmwareEdition.equals("") && phone != null && !phone.equals("")){
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
				List<DataMap> list = facade.getPhoneInfo(vo);
				String phone_string = "13524854512";
				String status = "1";
				if(list.size() <= 0){   //说明后台没有记录
//					vo.setInputTime(new Date());
//					vo.setStatus("1");
//					facade.insertPhoneInfo(vo);
					json.put("result", -1);  //记录-1
				}else{
					phone_string = ""+ list.get(0).getAt("phone");
					status = ""+ list.get(0).getAt("status");
					deviceLogin.setBelongProject(belongProject);
					deviceLogin.setDateTime(new Date());
					deviceLogin.setDeivceImei(serieNo);
					deviceLogin.setDevicePhone(phone_string);
					deviceLogin.setDeviceVersion(firmwareEdition);
					deviceLogin.setDeviceStatus(status);
					deviceLoginFacade.insertDeviceLogin(deviceLogin);
					
					vo.setFirmwareEdition(firmwareEdition);
					facade.updatePhoneInfo(vo);
					
					json.put("result", 1);
				}				
			}else{
				json.put("result", 0);
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
