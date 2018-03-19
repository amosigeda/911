package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

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
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.log.LogFactory;

public class ModifyDeviceFamilyAction extends BaseAction{
	
	Log logger = LogFactory.getLog(ModifyDeviceFamilyAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href= request.getServletPath();
		String user_id = "";
		String belongProject = "";
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			String device_imei = object.getString("device_imei");
			user_id = object.getString("user_id");
			String phoneNumber = object.getString("device_family_number");
			String nickName = object.getString("device_family_name");
			String relative_id = object.getString("relative_id");
			String relationType = object.getString("relation_type");
			belongProject = object.has("belong_project")?object.getString("belong_project"):"1";
			
			RelativeCallInfo vo = new RelativeCallInfo();
			if(phoneNumber.contains("@")){  //包含@
				String[] phone = phoneNumber.split("@");
				phoneNumber = phone[0];
				vo.setPhoneNumber(phoneNumber);
				phoneNumber = phone[1];
				vo.setShortNumber(phoneNumber);
				
			}else{
				vo.setPhoneNumber(phoneNumber);
			}
			
			nickName = Constant.transCodingToUtf(nickName);
			
			vo.setNickName(nickName);
			vo.setRelativeType(relationType);
			vo.setStatus("1");
//			vo.setStatus("3");
			vo.setCondition("user_id ='"+user_id+"' and serie_no ='"+device_imei+"' and belong_project='"+belongProject+"'");
			ServiceBean.getInstance().getRelativeCallInfoFacade().updateRelativeCallInfoStatus(vo);   //把状态设为,只要有变化就变为1
			
			vo.setCondition("id ='"+relative_id+"'");
			int num = ServiceBean.getInstance().getRelativeCallInfoFacade().updateRelativeCallInfo(vo);
			
			if(num > 0){
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}						
			json.put("request", href);
			
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
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
		
		return null;
		
	}

}
