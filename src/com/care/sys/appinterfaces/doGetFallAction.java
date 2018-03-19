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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.care.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.settinginfo.domain.logic.SettingInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class doGetFallAction extends BaseAction {
	Log logger = LogFactory.getLog(ListenAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		String user_id = "";
		String belongProject = "";
		try {
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			user_id = object.has("user_id") ? object
					.getString("user_id") : "-1";
			String serial_number = object.getString("serie_no");
			belongProject = object.getString("belong_project");
			String fallOn = object.has("fallOn") ? object.getString("fallOn") : "0"; // 之前版本兼容,0表示关闭,1表示打开

			if (user_id.equals("-1")) {
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				deviceActiveInfo.setCondition("device_imei ='" + serial_number
						+ "' and belong_project='" + belongProject + "'"); // 鍒ゅ畾鏉′欢
				List<DataMap> deviceList = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade()
						.getDeviceActiveInfo(deviceActiveInfo);
				int size = deviceList.size();
				if (size > 0) {
					user_id = "" + deviceList.get(0).getAt("user_id");
				}
			}
			if(!user_id.equals("-1")){
				SettingInfo vo = new SettingInfo();
				vo.setCondition("serie_no = '" + serial_number
						+ "' and belong_project='" + belongProject + "'");
				DirectiveInfo dvo = new DirectiveInfo();
				dvo.setCondition("serie_no = '" + serial_number
						+ "' and belong_project='" + belongProject + "'");
				PhoneInfo pvo = new PhoneInfo();
				pvo.setCondition("serie_no = '" + serial_number
						+ "' and belong_project='" + belongProject + "'");
				json.put("request", href);

				SettingInfoFacade facade = ServiceBean.getInstance()
						.getSettingInfoFacade();
				DirectiveInfoFacade dfacade = ServiceBean.getInstance()
						.getDirectiveInfoFacade();
				PhoneInfoFacade pfacade = ServiceBean.getInstance()
						.getPhoneInfoFacade();
				
				List<DataMap> list = facade.getSettingInfo(vo);// �������Ƿ������
				if (list.size() > 0) {
					vo.setFallOn(fallOn);
					facade.updateSettingInfo(vo);
					
					String fall = (String) list.get(0).getAt("fall");
					String gps_on = (String) list.get(0).getAt("gps_on");
					json.put("fall", fall); // 1表示戴上,0表示脱落
					json.put("gps_on", gps_on);
					List<DataMap> dlist = dfacade.getDirectiveInfo(dvo);
					if (dlist.size() > 0) {
						String isLow = (String) dlist.get(0).getAt("isLow");
						String lowelectricity = (String) dlist.get(0).getAt(
								"lowelectricity");
						json.put("lowelectricity", lowelectricity);
						json.put("isLow", isLow);
					}
					List<DataMap> plist = pfacade.getPhoneInfo(pvo);
					if (plist.size() > 0) {
						String repellent = (String) plist.get(0).getAt(
								"repellent");
						json.put("repellent", repellent);
					}
					result = Constant.SUCCESS_CODE;
				} else {
					result = Constant.SUCCESS_CODE;
				}
				StringBuffer condition = new StringBuffer();
				condition.append("(msg_content ='5@"+serial_number+"@0'");
				condition.append(" or ");
				condition.append("msg_content ='6@"+serial_number+"@0')");
				
				MsgInfo msgInfo = new MsgInfo();
				
				msgInfo.setToId(user_id);
				msgInfo.setFromId(user_id);				
				msgInfo.setMsgLevel("1"); // ??????
				msgInfo.setMsgHandlerDate(new Date());
				msgInfo.setBelongProject(belongProject);
				
				msgInfo.setOrderBy("id");
				msgInfo.setSort("1");
				msgInfo.setFrom(0);
				msgInfo.setPageSize(1);
				msgInfo.setCondition(condition.toString()+" and to_id ='"+user_id+"' and is_handler ='0' ");   //找到最后一条防脱乱的消息
				msgInfo.setMsgOccurDate(new Date());		
				
				List<DataMap> temp = ServiceBean.getInstance().getMsgInfoFacade().getMsgInfo(msgInfo);
				String id = "-1";
				if(temp.size() > 0){
					id = ""+temp.get(0).getAt("id");
				}
				if(!"-1".equals(id)){
					msgInfo.setCondition(condition.toString()+" and to_id ='"+user_id+"' and id ='"+id+"'");
					ServiceBean.getInstance().getMsgInfoFacade().updateMsgInfo(msgInfo);
				}else{
					msgInfo.setMsgContent("5@"+serial_number+"@0");
					msgInfo.setIsHandler("0"); // ��????
					ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
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
			json.put(Constant.EXCEPTION, sb.toString());
		}
		json.put(Constant.RESULTCODE, result);
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
