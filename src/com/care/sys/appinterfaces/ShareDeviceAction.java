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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class ShareDeviceAction extends BaseAction{

	Log logger = LogFactory.getLog(ShareDeviceAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		ServiceBean instance = ServiceBean.getInstance();
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
			user_id = object.getString("user_id");   //Ҫ������û�id
			String device_imei = object.getString("device_imei");   //Ҫ������豸��imei
			String to_user_phone = object.getString("to_user_phone");   //��������û���
			String to_message = object.getString("to_message");       //����Ļ���
			String message_level = object.getString("message_level");       //���ȼ�
			belongProject = object.getString("belong_project");
			//get����
//			String user_id = request.getParameter("user_id");   //Ҫ������û�id
//			String device_imei = request.getParameter("device_imei");   //Ҫ������豸��imei
//			String to_user_phone = request.getParameter("to_user_phone");   //��������û���
//			String to_message = request.getParameter("to_message");       //����Ļ���
//			String message_level = request.getParameter("message_level");       //���ȼ�
			
			to_message = Constant.transCodingToUtf(to_message);
			if(user_id != null && device_imei != null && to_user_phone != null && to_message != null){
				AppUserInfo appUserInfo = new AppUserInfo();
				MsgInfo msgInfo = new MsgInfo();
				ShareInfo shareInfo = new ShareInfo();
				
				appUserInfo.setCondition("user_name ='"+to_user_phone+"' and belong_project = '"+belongProject+"'");  //�û�����ǵ绰����
				List<DataMap> userList = instance.getAppUserInfoFacade().getAppUserInfo(appUserInfo);
				if(userList.size() <= 0){
					result = Constant.FAIL_CODE;
				}else{
					String to_user_id = ""+ userList.get(0).getAt("id");    //�û�id
					String nick_name = ""+ userList.get(0).getAt("nick_name");  //�û��ǳ�
					String user_head = ""+ userList.get(0).getAt("head");
					String user_name = ""+ userList.get(0).getAt("user_name");
					
					if(to_user_id.equals(user_id)){
						result = -2;
					}else{
						shareInfo.setCondition("user_id ='"+user_id+"' and to_user_id ='"+to_user_id+"' and device_imei ='"+device_imei+"' and belong_project='"+belongProject+"'");
						int count = instance.getShareInfoFacade().getShareInfoMaxCount(shareInfo);
						if(count <= 0){
							shareInfo.setUserId(user_id);      //�Ǹ��û������
							shareInfo.setToUserId(to_user_id); //������ĸ��û�
							shareInfo.setDeviceId(device_imei);//������豸
							shareInfo.setIsPriority("0");      //�����豸
							shareInfo.setShareDate(new Date());//�����ʱ��					
							shareInfo.setBelongProject(belongProject);
							instance.getShareInfoFacade().insertShareInfo(shareInfo);
							
							msgInfo.setFromId(user_id);
							msgInfo.setToId(to_user_id);
							msgInfo.setIsHandler("0");  //Ĭ��û�д���
							msgInfo.setMsgLevel(message_level);   //�������
							msgInfo.setMsgContent("1@"+device_imei+"@0");    //��Ϣ����
							msgInfo.setMsgHandlerDate(new Date());//��Ϣ����ʱ��(��ʵ�����ϴ�ʱ��)
							msgInfo.setBelongProject(belongProject);
							msgInfo.setMsgOccurDate(new Date());
							instance.getMsgInfoFacade().insertMsgInfo(msgInfo);
							
							result = Constant.SUCCESS_CODE;
							
							json.put("user_id", to_user_id);
							json.put("nick_name", nick_name);
							json.put("user_head", user_head);
							json.put("user_name", user_name);
							
						}else{			
							result = -3;
						}
					}				
				}
			}else{
				result = Constant.FAIL_CODE;
			}
			
		}catch (Exception e) {
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
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
}
