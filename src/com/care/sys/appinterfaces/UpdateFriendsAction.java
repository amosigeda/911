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
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class UpdateFriendsAction extends BaseAction {
	Log logger = LogFactory.getLog(doDisturbAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();

		String belongProject = "";
		
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while ((online = reader.readLine()) != null) {
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			String imei = object.getString("imei");
			String mImei = object.getString("m_imei");
			String updateTime = object.getString("update_time");
			belongProject = object.getString("belong_project");
			String name = object.getString("name");
			String mName = object.getString("m_name");
			String phone = object.getString("phone");
			String mPhone = object.getString("m_phone");
			try {
			if (!imei.equals("") && !mImei.equals("")
					&& !belongProject.equals("")) {
				DeviceActiveInfo vo = new DeviceActiveInfo();
				vo.setCondition("imei ='" + imei + "'and belong_project='"
						+ belongProject + "'and m_imei='" + mImei + "'");
				List<DataMap> deviceList = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade().getDeviceAddFriendInfo(vo);

				if (deviceList.size() < 0) {// 说明还没有
					vo.setImei(imei);
					vo.setmImei(mImei);
					vo.setUploadTime(updateTime);
					vo.setBelongProject(belongProject);
					int a = ServiceBean.getInstance()
							.getDeviceActiveInfoFacade()
							.insertAddFriendInfo(vo);// 向临时表里添加信息
					if (a > 0) {// 已经将要添加的信息录入数据库
						vo.setCondition("imei ='" + mImei
								+ "' and belong_project='" + belongProject
								+ "'and m_imei='" + imei + "'");
						List<DataMap> deviceListQ = ServiceBean.getInstance()
								.getDeviceActiveInfoFacade()
								.getDeviceAddFriendInfo(vo);// 再查找相反的imei
						if (deviceListQ.size() > 0) {
							AppUserInfo ao = new AppUserInfo();
							ao.setUserName(phone);
							List<DataMap> apList = ServiceBean.getInstance()
									.getAppUserInfoFacade().getAppUserInfo(ao);
							if (apList.size() > 0) {
								String id = apList.get(0).getAt("id") + "";
								RelativeCallInfo ro = new RelativeCallInfo();
								ro.setSerieNo(mImei);
								ro.setUserId(id);
								ro.setRelativeType("1");
								ro.setBelongProject(belongProject);
								List<DataMap> roList = ServiceBean
										.getInstance()
										.getRelativeCallInfoFacade()
										.getRelativeCallInfo(ro);
								if (roList.size() < 0) {
									ro.setSerieNo(mImei);
									ro.setUserId(id);
									ro.setRelativeType("1");
									ro.setBelongProject(belongProject);
									ro.setAddTime(new Date());
									ro.setNickName(mName);
									ro.setPhoneNumber(mPhone);
									int tian = ServiceBean.getInstance()
											.getRelativeCallInfoFacade()
											.insertRelativeCallInfo(ro);
								}

								ao.setUserName(mPhone);
								List<DataMap> apListM = ServiceBean
										.getInstance().getAppUserInfoFacade()
										.getAppUserInfo(ao);
								if (apListM.size() > 0) {
									String idM = apList.get(0).getAt("id") + "";
									// RelativeCallInfo roM=new
									// RelativeCallInfo();

									ro.setSerieNo(imei);
									ro.setBelongProject(belongProject);
									ro.setUserId(idM);
									ro.setRelativeType("1");
									List<DataMap> roListM = ServiceBean
											.getInstance()
											.getRelativeCallInfoFacade()
											.getRelativeCallInfo(ro);
									if (roListM.size() < 0) {
										ro.setSerieNo(imei);
										ro.setBelongProject(belongProject);
										ro.setUserId(idM);
										ro.setRelativeType("1");
										ro.setAddTime(new Date());
										ro.setNickName(name);
										ro.setPhoneNumber(phone);
										int tianM = ServiceBean.getInstance()
												.getRelativeCallInfoFacade()
												.insertRelativeCallInfo(ro);
									}

									result = 0;

								}

							}

						}
						result = -4;
					} else {
						result = -1;
					}
				}
			} else {
				result = -3;// 说明数据库里面有了
			}

		} catch (Exception e) {
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
			result = -2;
			json.put(-2, sb1.toString());
		}
		json.put(Constant.RESULTCODE, result);
		json.put("cmd","rUpdateFriends");
		json.put("imei",imei );
		insertVisit(href, belongProject, "0", 0, start, new Date(), json
				.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

}
