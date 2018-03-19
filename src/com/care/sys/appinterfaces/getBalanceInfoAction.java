package com.care.sys.appinterfaces;
/**
 * �ֻ��ϴ���Ϣ�ӿ�
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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.http.common.PagePys;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;


public class getBalanceInfoAction extends BaseAction{
	Log logger = LogFactory.getLog(getBalanceInfoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		JSONObject object = JSONObject.fromObject(sb.toString());
		String serieNo = object.getString("serie_no");
		String bg = object.getString("b_g");
		try{			
			
			DeviceActiveInfoFacade facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
			
			if(serieNo != null && !serieNo.equals("") && bg != null && !bg.equals("")
					&& bg != null && !bg.equals("") ){
				DeviceActiveInfo vo = new DeviceActiveInfo();
				vo.setCondition("device_imei = '"+serieNo+"' and belong_project='"+bg+"'");
				//vo.setOrderBy("upload_time");
				List<DataMap> list = facade.getDeviceActiveInfo(vo);///////      getSportTypeInfo(vo);
				if(list.size() <= 0){   //说明后台没有记录
					json.put("resultCode", 0);  //记录-1
				}else{
					json.put("phoneBalance", list.get(0).getAt("phone_balance")+"");
					json.put("busBalance",list.get(0).getAt("balance")+"");
					json.put("resultCode", 1);
				}				
			}else{
				json.put("resultCode", -1);
			}					
			
		}catch(Exception e){
			e.printStackTrace();	
			StringBuffer sb1 = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();		
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb1.append(resultSb);
			
			logger.error(e);
			result = Constant.EXCEPTION_CODE;
			json.put("result", -1);
		}
		String href= request.getServletPath();
		//insertVisit(href, belongProject, serieNo, 1,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());	
		return null;
	}

}
