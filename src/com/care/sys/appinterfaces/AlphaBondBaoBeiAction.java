package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class AlphaBondBaoBeiAction extends BaseAction {

	Log logger = LogFactory.getLog(AlphaBondBaoBeiAction.class);

	private final static int BOND_FAIL_NO = -2; // �豸���Ϸ�

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		Date start = new Date();
		String href = request.getServletPath();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
			PhoneInfo phoneInfo = new PhoneInfo(); // �����豸����Ϣ��
			DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();

			PhoneInfoFacade mPhoneInfoFacade = ServiceBean.getInstance()
					.getPhoneInfoFacade();
			DeviceActiveInfoFacade mDeviceActiveInfoFacade = ServiceBean
					.getInstance().getDeviceActiveInfoFacade();

			String device_imei = object.getString("serie_no");
			/*
			 * String device_name = object.getString("device_name"); String
			 * device_phone = object.getString("device_phone"); String
			 * device_head = object.getString("device_head"); String device_sex
			 * = object.getString("device_sex"); String device_age =
			 * object.getString("device_age"); String device_height =
			 * object.getString("device_height"); String device_weight =
			 * object.getString("device_weight");
			 */
			belongProject = object.getString("belong_project");

			if (device_imei != null && belongProject != null) {
				deviceActiveInfo.setCondition("device_imei ='" + device_imei
						+ "' and belong_project='" + belongProject + "'");
				List<DataMap> deviceListFirst = mDeviceActiveInfoFacade
						.getDeviceActiveInfo(deviceActiveInfo);
				if (deviceListFirst.size() <= 0) {

					phoneInfo.setCondition("serie_no = '" + device_imei
							+ "'and belong_project='" + belongProject + "'");
					List<DataMap> phoneList = mPhoneInfoFacade
							.getPhoneInfo(phoneInfo);

					if (phoneList.size() > 0) {
						AppUserInfo vo = new AppUserInfo();
						vo.setCondition("user_name = '" + device_imei
								+ "'and belong_project='" + belongProject + "'");
						List<DataMap> aLista = ServiceBean.getInstance()
								.getAppUserInfoFacade().getAppUserInfo(vo);
						if (aLista.size() <= 0) {
							vo.setUserName(device_imei);
							vo.setBelongProject(belongProject);
							vo.setCreateTime(new Date());
							int a = ServiceBean.getInstance()
									.getAppUserInfoFacade()
									.insertAppUserInfo(vo);
							if (a > 0) {
								vo.setCondition("user_name = '" + device_imei
										+ "'and belong_project='"
										+ belongProject + "'");
								List<DataMap> aList = ServiceBean.getInstance()
										.getAppUserInfoFacade()
										.getAppUserInfo(vo);
								if (aList.size() > 0) {
									String id = aList.get(0).getAt("id") + "";
									// json.put("user_id", id);
									deviceActiveInfo
											.setCondition("device_imei ='"
													+ device_imei
													+ "' and belong_project='"
													+ belongProject + "'");
									List<DataMap> deviceList = mDeviceActiveInfoFacade
											.getDeviceActiveInfo(deviceActiveInfo);
									if (deviceList.size() <= 0) {
										deviceActiveInfo
												.setDeviceAge("2015-12-12");
										deviceActiveInfo.setDeviceDisable("1");
										deviceActiveInfo.setCount("1");
										deviceActiveInfo.setDeviceHead("0");
										deviceActiveInfo.setDeviceHeight("170");
										deviceActiveInfo
												.setDeviceImei(device_imei);
										deviceActiveInfo.setDeviceName("0");
										deviceActiveInfo.setDevicePhone("0");
										deviceActiveInfo.setDeviceSex("0");
										deviceActiveInfo
												.setDeviceUpdateTime(new Date());
										deviceActiveInfo.setFirst(new Date());
										deviceActiveInfo.setDeviceWeight("170");
										deviceActiveInfo.setUserId(id);
										// deviceActiveInfo.setFirm(firmwareEdition);
										deviceActiveInfo
												.setBelongProject(belongProject);
										deviceActiveInfo.setDeviceStatus("0");
										mDeviceActiveInfoFacade
												.insertDeviceActiveInfo(deviceActiveInfo);

										deviceActiveInfo
												.setCondition("device_imei ='"
														+ device_imei
														+ "' and belong_project='"
														+ belongProject + "'");
										List<DataMap> deviceListX = mDeviceActiveInfoFacade
												.getDeviceActiveInfo(deviceActiveInfo);
										if (deviceListX.size() > 0) {
											json.put("user_id", deviceListX
													.get(0).getAt("user_id")
													+ "");
											json.put("device_id", deviceListX
													.get(0).getAt("id") + "");
										}

										phoneInfo.setStatus("2"); // 2琛ㄧず婵�椿
										phoneInfo.setSerieNo(device_imei);
										ServiceBean.getInstance()
												.getPhoneInfoFacade()
												.updatePhoneInfo(phoneInfo);
										result = Constant.SUCCESS_CODE;
									} else {

									}
								}
							}
						}

					} else {
						PhoneInfo po = new PhoneInfo();
						po.setSerieNo(device_imei);
						po.setBelongProject(belongProject);
						int zengjia = ServiceBean.getInstance()
								.getPhoneInfoFacade().insertPhoneInfo(po);
						if (zengjia > 0) {

							AppUserInfo vo = new AppUserInfo();
							vo.setCondition("user_name = '" + device_imei
									+ "'and belong_project='" + belongProject
									+ "'");
							List<DataMap> aLista = ServiceBean.getInstance()
									.getAppUserInfoFacade().getAppUserInfo(vo);
							if (aLista.size() <= 0) {
								vo.setUserName(device_imei);
								vo.setBelongProject(belongProject);
								vo.setCreateTime(new Date());
								int a = ServiceBean.getInstance()
										.getAppUserInfoFacade()
										.insertAppUserInfo(vo);
								if (a > 0) {
									vo.setCondition("user_name = '"
											+ device_imei
											+ "'and belong_project='"
											+ belongProject + "'");
									List<DataMap> aList = ServiceBean
											.getInstance()
											.getAppUserInfoFacade()
											.getAppUserInfo(vo);
									if (aList.size() > 0) {
										String id = aList.get(0).getAt("id")
												+ "";
										json.put("user_id", id);
										deviceActiveInfo
												.setCondition("device_imei ='"
														+ device_imei
														+ "' and belong_project='"
														+ belongProject + "'");
										List<DataMap> deviceList = mDeviceActiveInfoFacade
												.getDeviceActiveInfo(deviceActiveInfo);
										if (deviceList.size() <= 0) {
											deviceActiveInfo
													.setDeviceAge("2015-12-12");
											deviceActiveInfo
													.setDeviceDisable("1");
											deviceActiveInfo.setCount("1");
											deviceActiveInfo.setDeviceHead("0");
											deviceActiveInfo
													.setDeviceHeight("170");
											deviceActiveInfo
													.setDeviceImei(device_imei);
											deviceActiveInfo.setDeviceName("0");
											deviceActiveInfo
													.setDevicePhone("0");
											deviceActiveInfo.setDeviceSex("0");
											deviceActiveInfo
													.setDeviceUpdateTime(new Date());
											deviceActiveInfo
													.setFirst(new Date());
											deviceActiveInfo
													.setDeviceWeight("170");
											deviceActiveInfo.setUserId(id);
											// deviceActiveInfo.setFirm(firmwareEdition);
											deviceActiveInfo
													.setBelongProject(belongProject);
											deviceActiveInfo
													.setDeviceStatus("0");
											mDeviceActiveInfoFacade
													.insertDeviceActiveInfo(deviceActiveInfo);
											deviceActiveInfo
													.setCondition("device_imei ='"
															+ device_imei
															+ "' and belong_project='"
															+ belongProject
															+ "'");
											List<DataMap> deviceListX = mDeviceActiveInfoFacade
													.getDeviceActiveInfo(deviceActiveInfo);
											if (deviceListX.size() > 0) {
												json.put("user_id", deviceListX
														.get(0)
														.getAt("user_id")
														+ "");
												json.put("device_id",
														deviceListX.get(0)
																.getAt("id")
																+ "");
											}

											phoneInfo.setStatus("2"); // 2琛ㄧず婵�椿
											phoneInfo.setSerieNo(device_imei);
											ServiceBean.getInstance()
													.getPhoneInfoFacade()
													.updatePhoneInfo(phoneInfo);
											result = Constant.SUCCESS_CODE;
										} else {
											/*
											 * String aa =
											 * deviceList.get(0).getAt
											 * ("user_id") + ""; String
											 * bb=deviceList.get(0).getAt("id")
											 * + "";
											 */
											json.put(
													"user_id",
													deviceList.get(0).getAt(
															"user_id")
															+ "");
											json.put("device_id", deviceList
													.get(0).getAt("id") + "");
										}
									}
								}
							}

						}

						// result = Constant.SUCCESS_CODE;
					}
				} else {
					String userId = deviceListFirst.get(0).getAt("user_id")
							+ "";
					String id = deviceListFirst.get(0).getAt("id") + "";
					json.put("user_id", userId);
					json.put("device_id", id);
					result = 1;
				}
			} else {
				result = -1;
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
		json.put("request", href);
		insertVisit(href, belongProject, user_id, 0, start, new Date(), json
				.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	public String getServerName() throws Exception {
		String serverName = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader()
				.getResourceAsStream("server.properties"));
		serverName = pros.getProperty("servername");
		return serverName;
	}
	/*
	 * public static void main(String[] args) { SimpleDateFormat formatter=new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * 
	 * System.out.println(formatter.format(new Date())); }
	 */
}
