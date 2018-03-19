package com.care.sys.appinterfaces;

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
import org.apache.mina.core.session.IoSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.care.app.MessageManager;
import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class AddDeviceFamilyAction extends BaseAction{
	
	Log logger = LogFactory.getLog(AddDeviceFamilyAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		IoSession to_session = null;
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
			user_id = object.getString("user_id");
			String serieNo = object.getString("device_imei");
			String phoneNumber = object.getString("device_family_number");
			String nickName = object.getString("device_family_name");	
			String relationType = object.getString("relation_type");
			belongProject = object.getString("belong_project");
			
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
			vo.setUserId(user_id);
			vo.setSerieNo(serieNo);
			vo.setNickName(nickName);			
			vo.setRelativeType(relationType);
			vo.setUserId(user_id);
			vo.setStatus("1");
			vo.setAddTime(new Date());
			vo.setBelongProject(belongProject);
			vo.setCondition("user_id ='"+user_id+"' and serie_no ='"+serieNo+"' and belong_project='"+belongProject+"' and phone_number ='"+vo.getPhoneNumber()+"'");
			List<DataMap> relativeList = ServiceBean.getInstance().getRelativeCallInfoFacade().getRelativeCallInfo(vo);
			if(relativeList.size() <= 0){
				result = Constant.SUCCESS_CODE;
				ServiceBean.getInstance().getRelativeCallInfoFacade().insertRelativeCallInfo(vo);			
				
				vo.setCondition("user_id ='"+user_id+"' and serie_no ='"+serieNo+"' and belong_project='"+belongProject+"'");
				ServiceBean.getInstance().getRelativeCallInfoFacade().updateRelativeCallInfoStatus(vo);   //把状态设为,只要有变化就变为1
				
				int maxCount = ServiceBean.getInstance().getRelativeCallInfoFacade().getMaxCountRelativeCallInfo(vo);
				json.put("relative_id", String.valueOf(maxCount));
			}else{
				result = Constant.FAIL_CODE;
			}
			json.put("request", href);
			
			
			to_session = MessageManager.snSet.containsKey(serieNo)?
					 MessageManager.snSet.get(serieNo):null;
			if(to_session!=null){
				logger.info("Session is OK：ZST");
				to_session.write("SG*"+serieNo+"*0010*SOS1"+","+phoneNumber);
			}else{
				logger.info("ZST@20151231");
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
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.getWriter().write(json.toString());	
		return null;		
	}
}
