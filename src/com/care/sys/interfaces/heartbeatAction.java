package com.care.sys.interfaces;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.care.common.config.ServiceBean;
import com.care.common.http.BaseAction;
import com.care.common.http.HttpRequest;
import com.care.common.lang.CommUtils;
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.care.sys.directiveinfo.domain.logic.DirectiveInfoFacade;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.safearea.domain.SafeArea;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.settinginfo.domain.logic.SettingInfoFacade;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class heartbeatAction extends BaseAction {

	Log logger = LogFactory.getLog(heartbeatAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Date start = new Date();
		JSONObject json = new JSONObject();
		String href = request.getServletPath();
		String serieNo = request.getParameter("no");
		String locationType = request.getParameter("type"); // 1表示GPS,0表示基站,2表示wifi
		String battery = request.getParameter("battery");
		String belongProject = request.getParameter("b_g");
        String fall = request.getParameter("f");   //1表示戴上,0表示脱落
		try {			
            
            if(fall == null){
            	fall = "0";
            }
			LocationInfo vo = new LocationInfo();
            boolean validateImei = false;   //默认不通过
			// System.out.println("serie_no="+serieNo+"' and belong_project='"+belongProject+","+locationType);
			double lng1 = 0;
			double lat1 = 0;

			// 获取警报
			PhoneInfoFacade phoneInfofacade = ServiceBean.getInstance()
					.getPhoneInfoFacade();
			PhoneInfo phoneInfo = new PhoneInfo();
			if (belongProject != null && !"".equals(belongProject)) {
				phoneInfo.setCondition("serie_no='" + serieNo
						+ "' and belong_project='" + belongProject + "'");
			} else {
				phoneInfo.setCondition("serie_no='" + serieNo + "'");
			}

			List<DataMap> list = phoneInfofacade.getPhoneInfo(phoneInfo);
			if (list.size() > 0) {
				String type = (String) list.get(0).getAt("alarm_bell_type");
				if (type.equals("1")) {// 有警报
					json.put("warn", 1);
					phoneInfo.setAlarmBellType("0");
					phoneInfofacade.updatePhoneInfo(phoneInfo);
				} else {
					json.put("warn", 0);
				}
				validateImei = true;
			} else {
//				phoneInfo.setSerieNo(serieNo);
//				phoneInfo.setStatus("1"); // 上报
//				phoneInfo.setUploadTime(new Date());
//				phoneInfo.setInputTime(new Date());
//				phoneInfo.setAlarmBellType("0"); // 没有警报
//				phoneInfo.setBelongProject(belongProject);
//
//				json.put("warn", 0);
//				phoneInfofacade.insertPhoneInfo(phoneInfo);
				result = -1;
			}
			if(validateImei){
				if ("1".equals(locationType)) {
					String longitude = request.getParameter("lon");
					String latitude = request.getParameter("lat");
					String accuracy = request.getParameter("acc");

					if (serieNo != null && !serieNo.equals("")
							&& battery != null && !battery.equals("")
							&& longitude != null && !longitude.equals("")
							&& latitude != null && !latitude.equals("")
							&& accuracy != null && !accuracy.equals("")
							&& locationType != null && !locationType.equals("")) {

						lng1 = Double.parseDouble(longitude);
						lat1 = Double.parseDouble(latitude);
						if (lng1 != 0 && lat1 != 90) { // 直接过滤
							if (belongProject != null
									&& !"".equals(belongProject)) {
								vo.setCondition("serie_no ='" + serieNo
										+ "' and belong_project='"
										+ belongProject + "'");
							} else {
								vo.setCondition("serie_no ='" + serieNo + "'");
							}

							vo.setOrderBy("id");
							vo.setSort("1"); // 按id降序
							vo.setFrom(0);
							vo.setPageSize(1); // 0至1

							List<DataMap> locationList = ServiceBean
									.getInstance().getLocationInfoFacade()
									.getLocationInfo(vo);
							boolean bool_is_update = true;
							String id = "0";
							if (locationList.size() > 0) { // 说明有数据
								id = "" + locationList.get(0).getAt("id");
								double lng2 = Double.parseDouble(""
										+ locationList.get(0)
												.getAt("longitude"));
								double lat2 = Double
										.parseDouble(""
												+ locationList.get(0).getAt(
														"latitude"));

								// System.out.println("为转换前的经纬度="+lng1+","+lat1);
								// System.out.println("为转换后的经纬度="+lng2+","+lat2);

								 bool_is_update = Constant.getDistance(lat1, lng1,
								 lat2, lng2, Constant.EFFERT_DATA); //
								// 若为true表示有效数据
							}

							vo.setSerieNo(serieNo);
							vo.setBattery(Integer.parseInt(battery));
							vo.setLongitude(longitude);
							vo.setLatitude(latitude);
							vo.setAccuracy(Integer.parseInt(accuracy));
							vo.setLocationType(locationType);
							vo.setUploadTime(new Date());
							vo.setShowType("1"); // 都是显示的
							vo.setEndTime(new Date());
							vo.setFall(fall);
							vo.setStepNo(0);
							vo.setRollNo(0);
							vo.settAlarm(0);
							vo.settStatus(0);
							if (belongProject != null
									&& !"".equals(belongProject)) {
								vo.setBelongProject(belongProject);
							} else {
								vo.setBelongProject("1");
							}
							String resultCode = HttpRequest
									.sendGet(
											"http://restapi.amap.com/v3/assistant/coordinate/convert",
											"locations="
													+ longitude
													+ ","
													+ latitude
													+ "&coordsys=gps&output=json&key=801df1e9132e2151cd9ad435ecc59858");
							if ("-1".equals(resultCode)) {
								result = Constant.FAIL_CODE;
								vo.setChangeLongitude("0");
								vo.setChangeLatitude("0");
							} else {
								JSONObject object = JSONObject
										.fromObject(resultCode);
								String location = object.getString("locations");
								String[] str = location.split(",");
								if (str.length == 2) {
									vo.setChangeLongitude(str[0]);
									vo.setChangeLatitude(str[1]);
								}
							}
							if (bool_is_update) { // 有效数据
								ServiceBean.getInstance()
										.getLocationInfoFacade()
										.insertLocationInfo(vo);
							} else {
								vo.setCondition("id ='" + id + "'");
								ServiceBean.getInstance()
										.getLocationInfoFacade()
										.updateLocationInfo(vo);
							}
							result = Constant.SUCCESS_CODE;
						} else {
							result = Constant.FAIL_CODE;
						}
					}

				} else if ("0".equals(locationType)) {//基站
					String network = request.getParameter("network");
					String cdma = request.getParameter("cdma");
					String imei = request.getParameter("imei");
					String smac = request.getParameter("smac");
					String bts = request.getParameter("bts");
					String nearbts = request.getParameter("nearbts");
					// String key = request.getParameter("key");
					String serverip = request.getParameter("serverip");

					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

					map.put("accesstype", "0");
					map.put("network", network);
					map.put("cdma", cdma);
					map.put("imei", imei);
					map.put("smac", smac);
					map.put("bts", bts);
					map.put("nearbts", nearbts);
					map.put("key", Constant.KEY);
					if (serverip != null && !"".equals(serverip)) {
						map.put("serverip", serverip);
					} else {
						map.put("serverip", Constant.SERVER_IP);
					}

					String jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);
					logger.info("jsonToString++" + jsonToString);
					if ("-1".equals(jsonToString)) {
						result = Constant.FAIL_CODE;
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status");
						if (status.equals("1")) { 
							String results = jsons.getString("result");
							JSONObject jsonResult = JSONObject
									.fromObject(results);
							String location = jsonResult.has("location") ? jsonResult
									.getString("location") : null; 
							if (location != null) {
								if (belongProject != null
										&& !"".equals(belongProject)) {
									vo.setCondition("serie_no ='" + serieNo
											+ "' and belong_project='"
											+ belongProject
											+ "' and location_type ='"
											+ locationType + "' and s_t ='1'"); // 并且保证点是存在的
								} else {
									vo.setCondition("serie_no ='" + serieNo
											+ "' and location_type ='"
											+ locationType + "' and s_t ='1'");  // and s_t ='1'
								}

								vo.setOrderBy("id");
								vo.setSort("1"); // 按id降序
								vo.setFrom(0);
								vo.setPageSize(1); // 0至1

								List<DataMap> locationList = ServiceBean
										.getInstance().getLocationInfoFacade()
										.getLocationInfo(vo);
								boolean bool_is_update = true;
								String[] locations = location.split(",");
								String id = "0";
								String date = "0";   //上一个点的定位时间
								if (locationList.size() > 0) { // 说明有数据
									id = "" + locationList.get(0).getAt("id");
									date = "" + locationList.get(0).getAt("upload_time");
									double lng2 = Double.parseDouble(""
											+ locationList.get(0).getAt(
													"longitude"));
									double lat2 = Double.parseDouble(""
											+ locationList.get(0).getAt(
													"latitude"));

									lng1 = Double.parseDouble(locations[0]);
									lat1 = Double.parseDouble(locations[1]);

									// System.out.println("为转换前的经纬度="+lng1+","+lat1);
									// System.out.println("为转换后的经纬度="+lng2+","+lat2);

									bool_is_update = Constant.getDistance(lat1,
											lng1, lat2, lng2,
											Constant.EFFERT_DATA); //
									// 若为true表示有效数据
								}

								vo.setSerieNo(serieNo);
								vo.setBattery(Integer.parseInt(battery));
								vo.setLongitude(locations[0]);
								vo.setLatitude(locations[1]);
								vo.setChangeLongitude(locations[0]);
								vo.setChangeLatitude(locations[1]);
								vo.setAccuracy(10);
								vo.setLocationType(locationType);
								vo.setUploadTime(new Date());
								vo.setStepNo(0);
								vo.setRollNo(0);
								vo.settAlarm(0);
								vo.settStatus(0);
								if (belongProject != null
										&& !"".equals(belongProject)) {
									vo.setBelongProject(belongProject);
								} else {
									vo.setBelongProject("1");
								}
								if (bool_is_update || !CommUtils.isSameDay(date)) { // 说明有变化,获取到新的一天,点显示
									vo.setShowType("1");								
									vo.setEndTime(new Date());
								} else {
									vo.setShowType("0"); // 点不显示
									vo.setEndTime(null);
								}
								vo.setFall(fall);
								ServiceBean.getInstance()
										.getLocationInfoFacade()
										.insertLocationInfo(vo);
								
								if(vo.getShowType().equals("0")){   //不显示的时候更新下时间,显示不需要更新时间
									vo.setCondition("id ='" + id + "'");
									vo.setUploadTime(null);
									vo.setEndTime(new Date()); // 更新时间
									ServiceBean.getInstance()
											.getLocationInfoFacade()
											.updateLocationInfo(vo); // 更新经纬度
								}
								
								result = Constant.SUCCESS_CODE;
							}
						} else if (status.equals("0")) {
							result = Constant.FAIL_CODE;
						} else if (status.equals("-1")) {
							result = Constant.FAIL_CODE;
						}
					}
				} else if ("2".equals(locationType)) {// wifi
					String imei = request.getParameter("imei");
					String smac = request.getParameter("smac");
					String mmac = request.getParameter("mmac");
					String macs = request.getParameter("macs");
					// String key = request.getParameter("key");
					String serverip = request.getParameter("serverip");

					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

					map.put("accesstype", "1");
					map.put("imei", imei);
					map.put("smac", smac);
					map.put("mmac", mmac);
					map.put("macs", macs);
					map.put("key", Constant.KEY);
					if (serverip != null && !"".equals(serverip)) {
						map.put("serverip", serverip);
					} else {
						map.put("serverip", Constant.SERVER_IP);
					}

					String jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);
					// System.out.println("jsonToString++"+jsonToString);
					if ("-1".equals(jsonToString)) {
						result = Constant.FAIL_CODE;
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); 
						if (status.equals("1")) { 
							String results = jsons.getString("result");
							JSONObject jsonResult = JSONObject
									.fromObject(results);
							String location = jsonResult.getString("location"); 
							if (location != null) {
								if (belongProject != null
										&& !"".equals(belongProject)) {
									vo.setCondition("serie_no ='" + serieNo
											+ "' and belong_project='"
											+ belongProject
											+ "' and location_type ='"
											+ locationType + "'");
								} else {
									vo.setCondition("serie_no ='" + serieNo
											+ "' and location_type ='"
											+ locationType + "'");
								}
								vo.setOrderBy("id");
								vo.setSort("1"); // 按id降序
								vo.setFrom(0);
								vo.setPageSize(1); // 0至1

								List<DataMap> locationList = ServiceBean
										.getInstance().getLocationInfoFacade()
										.getLocationInfo(vo);
								boolean bool_is_update = true;
								String[] locations = location.split(",");
								String id = "0";
								if (locationList.size() > 0) { // 说明有数据
									id = "" + locationList.get(0).getAt("id");
									double lng2 = Double.parseDouble(""
											+ locationList.get(0).getAt(
													"longitude"));
									double lat2 = Double.parseDouble(""
											+ locationList.get(0).getAt(
													"latitude"));

									lng1 = Double.parseDouble(locations[0]);
									lat1 = Double.parseDouble(locations[1]);

									// System.out.println("为转换前的经纬度="+lng1+","+lat1);
									// System.out.println("为转换后的经纬度="+lng2+","+lat2);

									bool_is_update = Constant.getDistance(lat1,
											lng1, lat2, lng2,
											Constant.EFFERT_DATA); // 若为true表示有效数据
								}

								vo.setSerieNo(serieNo);
								vo.setBattery(Integer.parseInt(battery));
								vo.setLongitude(locations[0]);
								vo.setLatitude(locations[1]);
								vo.setChangeLongitude(locations[0]);
								vo.setChangeLatitude(locations[1]);
								vo.setAccuracy(10);
								vo.setLocationType(locationType);
								vo.setUploadTime(new Date());
								vo.setStepNo(0);
								vo.setRollNo(0);
								vo.settAlarm(0);
								vo.settStatus(0);
								if (belongProject != null
										&& !"".equals(belongProject)) {
									vo.setBelongProject(belongProject);
								} else {
									vo.setBelongProject("1");
								}
								vo.setFall(fall);
								if (bool_is_update) {
									ServiceBean.getInstance()
											.getLocationInfoFacade()
											.insertLocationInfo(vo);
								} else {
									vo.setCondition("id ='" + id + "'");
									ServiceBean.getInstance()
											.getLocationInfoFacade()
											.updateLocationInfo(vo);
								}
								result = Constant.SUCCESS_CODE;
							}
						} else if (status.equals("0")) {
							result = Constant.FAIL_CODE;
						} else if (status.equals("-1")) {
							result = Constant.FAIL_CODE;
						}
					}
				}

				DirectiveInfo direInfo = new DirectiveInfo();
				direInfo.setCondition("serie_no = '" + serieNo + "'");
				DirectiveInfoFacade direFacade = ServiceBean.getInstance()
						.getDirectiveInfoFacade();
				List<DataMap> direList = direFacade.getDirectiveInfo(direInfo);
				if (direList.size() > 0) {
					String distrubChange = (String) direList.get(0).getAt(
							"distrubChange");
					String alarmChange = (String) direList.get(0).getAt(
							"alarmChange");
					String sleepChange = (String) direList.get(0).getAt(
							"sleepChange");
					json.put("isSleep", sleepChange);
					direInfo.setSleepChange("0");
					json.put("alarm", alarmChange);
					direInfo.setAlarmChange("0");
					json.put("disturb", distrubChange);
					direInfo.setDistrubChange("0");
					direFacade.updateDirectiveInfo(direInfo);
				}
				SettingInfo svo = new SettingInfo();
				if (belongProject != null && !"".equals(belongProject)) {
					svo.setCondition("serie_no = '" + serieNo
							+ "' and belong_project ='" + belongProject + "'");
				} else {
					svo.setCondition("serie_no = '" + serieNo + "'");
				}
				String user_id = "0";
			
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				DeviceActiveInfoFacade facade = ServiceBean.getInstance()
						.getDeviceActiveInfoFacade();
				if (belongProject != null && !"".equals(belongProject)) {
					deviceActiveInfo.setCondition("device_imei='" + serieNo
							+ "' and belong_project ='" + belongProject
							+ "' and listen_type='1' and device_disable='1'");
				} else {
					deviceActiveInfo.setCondition("device_imei='" + serieNo
							+ "' and listen_type='1' and device_disable='1'");
				}
				boolean isBond = true; // 默认绑定
				List<DataMap> deviceActiveList = facade
						.getDeviceActiveInfo(deviceActiveInfo);
				if (deviceActiveList.size() > 0) {
					user_id = (String) deviceActiveList.get(0).getAt("user_id");
					AppUserInfo appUser = new AppUserInfo();
					appUser.setCondition("id='" + user_id + "'");
					List<DataMap> appUserList = ServiceBean.getInstance()
							.getAppUserInfoFacade().getAppUserInfo(appUser);
					if (appUserList.size() > 0) {
						String phoneNumber = (String) appUserList.get(0).getAt(
								"user_name");
						json.put("listen", phoneNumber);

						
						deviceActiveInfo.setListenType("0");
						facade.updateDeviceActiveInfo(deviceActiveInfo);
					}
				} else {					
					json.put("listen", "0");
				}

				if (belongProject != null && !"".equals(belongProject)) {
					deviceActiveInfo.setCondition("device_imei='" + serieNo
							+ "' and belong_project ='" + belongProject
							+ "' and device_disable='1'");
				} else {
					deviceActiveInfo.setCondition("device_imei='" + serieNo
							+ "' and device_disable='1'");
				}
				List<DataMap> deviceActiveLists = facade.getDeviceActiveInfo(deviceActiveInfo);
				if(deviceActiveLists.size() > 0){
					user_id = (String)deviceActiveLists.get(0).getAt("user_id");
				}else{
					isBond = false; // 解绑了
				}
				SettingInfoFacade sFacade = ServiceBean.getInstance()
						.getSettingInfoFacade();
				List<DataMap> sList = sFacade.getSettingInfo(svo);
				if (sList.size() > 0) {
					String gps_on = (String) sList.get(0).getAt("gps_on");
					String light = (String) sList.get(0).getAt("light"); // 频率
//					String fm = "" + sList.get(0).getAt("light_sensor"); //解绑后,亲情号码是否只上传一次,0表示已下发,就不需要再下发了
					String map = "" + sList.get(0).getAt("map");
					String shutdown = (String) sList.get(0).getAt("shutdown");
					String repellent = (String) sList.get(0).getAt("repellent");
					String heart = (String) sList.get(0).getAt("heart");
					
					if(shutdown.equals("1")){    //表示关闭
						MsgInfo msgInfo = new MsgInfo();
						msgInfo.setToId(user_id);
						msgInfo.setFromId(user_id);
						msgInfo.setIsHandler("0"); // 未处理
						msgInfo.setMsgLevel("1"); // 级别较高
						msgInfo.setMsgContent("8@" + serieNo + "@0");
						msgInfo.setMsgHandlerDate(new Date());
						msgInfo.setBelongProject(belongProject);
						msgInfo.setMsgOccurDate(new Date());

						ServiceBean.getInstance().getMsgInfoFacade()
								.insertMsgInfo(msgInfo); 
					}
					if(repellent.equals("1")){    //表示关闭
						MsgInfo msgInfo = new MsgInfo();
						msgInfo.setToId(user_id);
						msgInfo.setFromId(user_id);
						msgInfo.setIsHandler("0"); // 未处理
						msgInfo.setMsgLevel("1"); // 级别较高
						msgInfo.setMsgContent("11@" + serieNo + "@0");
						msgInfo.setMsgHandlerDate(new Date());
						msgInfo.setBelongProject(belongProject);
						msgInfo.setMsgOccurDate(new Date());

						ServiceBean.getInstance().getMsgInfoFacade()
								.insertMsgInfo(msgInfo); 
					}
					if(!heart.equals("0") && !heart.equals("-1")){
						MsgInfo msgInfo = new MsgInfo();
						msgInfo.setToId(user_id);
						msgInfo.setFromId(user_id);
						msgInfo.setIsHandler("0"); // 未处理
						msgInfo.setMsgLevel("1"); // 级别较高
						msgInfo.setMsgContent("12@" + serieNo + "@" +heart);
						msgInfo.setMsgHandlerDate(new Date());
						msgInfo.setBelongProject(belongProject);
						msgInfo.setMsgOccurDate(new Date());

						ServiceBean.getInstance().getMsgInfoFacade()
								.insertMsgInfo(msgInfo); 
						json.put("heart", heart);
						
						svo.setHeart("-1");
					}else{
						json.put("heart", "");
					}
					json.put("shutdown", shutdown);
					json.put("repellent", repellent);					
					
					if (!"".equals(gps_on) && gps_on != null) {
						json.put("GPS_ON", gps_on);
					} else {
						json.put("GPS_ON", "0"); // 默认关闭
					}
					if (!light.equals("") && light != null && !light.equals("-2")) { // 手动状态
						svo.setLight("-2");
						MsgInfo msgInfo = new MsgInfo();
						msgInfo.setToId(user_id);
						msgInfo.setFromId(user_id);
						msgInfo.setIsHandler("0"); // 未处理
						msgInfo.setMsgLevel("1"); // 级别较高
						msgInfo.setMsgContent("13@" + serieNo + "@" +light);
						msgInfo.setMsgHandlerDate(new Date());
						msgInfo.setBelongProject(belongProject);
						msgInfo.setMsgOccurDate(new Date());

						ServiceBean.getInstance().getMsgInfoFacade()
								.insertMsgInfo(msgInfo); 
					} else {
						light = "-2";
					}
					json.put("p_l", light); // 默认关闭
					if (shutdown.equals("1")) {
						svo.setShutdown("0");
					}
					if (repellent.equals("1")) {
						svo.setRepellent("2");
					}
//					if(fm != null && !fm.equals("")){
//						json.put("f_m", fm); // 默认关闭
//					}else{
//						json.put("f_m", "1");
//					}
					json.put("fall", "1");
					svo.setFall(fall);     //重置防脱落状态
					sFacade.updateSettingInfo(svo);
				}
				
				if(!user_id.equals("-1")){
					boolean isFall = false;
					String msgContent = "6@" + serieNo + "@" + "0";
					StringBuffer condition = new StringBuffer();
					condition.append("(msg_content ='5@"+serieNo+"@0'");
					condition.append(" or ");
					condition.append("msg_content ='6@"+serieNo+"@0')");
					MsgInfo msgInfo = new MsgInfo();
					
					msgInfo.setToId(user_id);
					msgInfo.setFromId(user_id);
					msgInfo.setIsHandler("0");
					msgInfo.setMsgLevel("1");
					msgInfo.setMsgHandlerDate(new Date());
					msgInfo.setBelongProject(belongProject);
					if(fall.equals("1")){  			
						msgContent = "5@" + serieNo + "@" + "0"; 
					}
					msgInfo.setCondition(condition.toString()+" and to_id ='"+user_id+"' and is_handler ='0' "); 
					msgInfo.setOrderBy("id");
					msgInfo.setSort("1");
					msgInfo.setFrom(0);
					msgInfo.setPageSize(1);
					msgInfo.setMsgContent(msgContent);
					msgInfo.setMsgOccurDate(new Date());
					List<DataMap> temp = ServiceBean.getInstance().getMsgInfoFacade().getMsgInfo(msgInfo);
					if(temp.size() > 0){
						String tempContent = ""+temp.get(0).getAt("msg_content"); 
						if(!msgContent.equals(tempContent)){
							isFall = true;
						}			
					}else{
						ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);   //说明没有值
					}				
					if(isFall){
						ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
					}			
				}
				if (lat1 != 0 && lng1 != 0 && !user_id.equals("0")) {
					SafeArea safeArea = new SafeArea();
					if (belongProject != null && !"".equals(belongProject)) {
						safeArea.setCondition("seri_no ='" + serieNo
								+ "' and belong_project = '" + belongProject + "'"); // 查询这个设备的所有围栏
					} else {
						safeArea.setCondition("seri_no ='" + serieNo + "'"); // 查询这个设备的所有围栏
					}

					List<DataMap> safeList = ServiceBean.getInstance()
							.getSafeAreaFacade().getSafeArea(safeArea);
					int length = safeList.size(); // 电子围栏个数
					for (int i = 0; i < length; i++) {
						double lng2 = Double.parseDouble(""
								+ safeList.get(i).getAt("longitude"));
						double lat2 = Double.parseDouble(""
								+ safeList.get(i).getAt("latitude"));
						double safe_range = Double.parseDouble(""
								+ safeList.get(i).getAt("safe_range"));
						String status = "" + safeList.get(i).getAt("status"); // 1表示进入,0表示正常,-1表示出去
						String area_name = "" + safeList.get(i).getAt("area_name");
						String areaId = "" + safeList.get(i).getAt("id");

						boolean flag = Constant.getDistance(lat1, lng1, lat2, lng2,
								safe_range); // 说明超出范围
						if (flag && status.equals("1")) { // 超出这个范围,并且进入这个区域
							MsgInfo msgInfo = new MsgInfo();
							msgInfo.setToId(user_id);
							msgInfo.setFromId(user_id);
							msgInfo.setIsHandler("0"); // 未处理
							msgInfo.setMsgLevel("1"); // 级别较高
							msgInfo.setMsgContent("3@" + serieNo + "@" + area_name);
							msgInfo.setMsgHandlerDate(new Date());
							msgInfo.setBelongProject(belongProject);
							msgInfo.setMsgOccurDate(new Date());

							ServiceBean.getInstance().getMsgInfoFacade()
									.insertMsgInfo(msgInfo);

							safeArea.setStatus("-1"); // 已出这个范围
							safeArea.setCondition("id='" + areaId +"'");
							ServiceBean.getInstance().getSafeAreaFacade()
									.updateSafeArea(safeArea);
						} else if (!flag && !status.equals("1")) { // 表示没有超出范围,并且是正常和出去状态
							MsgInfo msgInfo = new MsgInfo();
							msgInfo.setToId(user_id);
							msgInfo.setFromId(user_id);
							msgInfo.setIsHandler("0"); // 未处理
							msgInfo.setMsgLevel("1"); // 级别较高
							msgInfo.setMsgContent("2@" + serieNo + "@" + area_name);
							msgInfo.setMsgHandlerDate(new Date());
							msgInfo.setBelongProject(belongProject);
							msgInfo.setMsgOccurDate(new Date());

							ServiceBean.getInstance().getMsgInfoFacade()
									.insertMsgInfo(msgInfo);
							
							safeArea.setStatus("1"); // 已出这个范围
							safeArea.setCondition("id='" + areaId +"'");
							ServiceBean.getInstance().getSafeAreaFacade()
									.updateSafeArea(safeArea);
						}
					}
				}

				DirectiveInfo directiveInfo = new DirectiveInfo();
				directiveInfo.setCondition("serie_no ='" + serieNo
						+ "' and belong_project = '" + belongProject + "'");
				directiveInfo.setLowelectricity(battery);
				if (Integer.valueOf(battery) <= Constant.LOW) {
					directiveInfo.setIsLow("1");

					MsgInfo msgInfo = new MsgInfo();
					
					msgInfo.setCondition("to_id ='"+user_id+"' and belong_project ='"+belongProject+"' and msg_content = '4@"+serieNo+"@0'");
					msgInfo.setOrderBy("id");
					msgInfo.setSort("1");
					msgInfo.setFrom(0);
					msgInfo.setPageSize(1);
					
					msgInfo.setToId(user_id);
					msgInfo.setFromId(user_id);
					msgInfo.setIsHandler("0"); // 未处理
					msgInfo.setMsgLevel("1"); // 级别较高
					msgInfo.setMsgContent("4@" + serieNo + "@" + "0");
					msgInfo.setMsgHandlerDate(new Date());
					msgInfo.setBelongProject(belongProject);
					msgInfo.setMsgOccurDate(new Date());
					
					List<DataMap> msgList = ServiceBean.getInstance().getMsgInfoFacade().getMsgInfo(msgInfo);
					boolean isJiange = false;
					if(msgList.size() > 0){
						String msgHandleTime = ""+msgList.get(0).getAt("msg_handler_date");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						isJiange = CommUtils.isTimeJianGe(msgHandleTime, df.format(new Date()), Constant.DATE_JIANGE);
					}else{
						isJiange = true;   //第一条
					}
					if(isJiange){
						ServiceBean.getInstance().getMsgInfoFacade()
						.insertMsgInfo(msgInfo);
					}
				} else {
					directiveInfo.setIsLow("0");
				}
				if (ServiceBean.getInstance().getDirectiveInfoFacade()
						.getDirectiveInfoCount(directiveInfo) <= 0) {
					directiveInfo.setBelongProject(belongProject);
					directiveInfo.setSerie_no(serieNo);

					ServiceBean.getInstance().getDirectiveInfoFacade()
							.insertDirectiveInfo(directiveInfo);
				} else {
					ServiceBean.getInstance().getDirectiveInfoFacade()
							.updateDirectiveInfo(directiveInfo);
				}
				RelativeCallInfo relativeCallInfo = new RelativeCallInfo();
				relativeCallInfo.setCondition("serie_no ='" + serieNo
						+ "' and belong_project = '" + belongProject
						+ "' and status ='1'"); // 表示有变化
				if (ServiceBean.getInstance().getRelativeCallInfoFacade()
						.getRelativeCallInfoCount(relativeCallInfo) <= 0) {
					if (!isBond) {
						json.put("f_m", "1");
					} else {
						json.put("f_m", "0");
					}
				} else {
					json.put("f_m", "1");
				}
				logger.info(serieNo + "json下发数据='" + json.toString());				
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
		}
		json.put("location", result);
		insertVisit(href, belongProject, serieNo, 1, start, new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

		return null;
	}

}
