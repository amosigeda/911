package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

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
import com.care.common.http.HttpRequest;
import com.care.common.lang.Constant;
import com.care.sys.LocRadiusPoiResponse.domain.JsonUtil;
import com.care.sys.LocRadiusPoiResponse.domain.RemoteUtil;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.godoing.rose.log.LogFactory;

public class UploadGpsAction extends BaseAction {
	Log logger = LogFactory.getLog(UploadGpsAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		LocRadiusPoiResponse loc = null;
		String resultsf = null;
		String mt = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
			String mcc = object.getString("mcc");// mcc460就是中国
			String imei = object.getString("imei");// 设备的imei
			String device = object.getString("device");
			String belongProject = object.getString("belong_project");
			if (!mcc.equals("460")) {// 460是中国的---外国
				String data = object.getString("data");
				if (!"".equals(data)) {
					JSONArray listData = new JSONArray().fromObject(data);// 将结果转换为JSONArray对象的形式
					for (int i = 0; i < listData.size(); i++) {
						JSONObject lj = listData.getJSONObject(i);
						String isGps = lj.getString("isGps");// 1为Gps0为基站
						String altitude = lj.getString("altitude");// 海拔
						String satellite = lj.getString("satellite");// 卫星数
						String speed = lj.getString("speed");// 速度
						String createDate = lj.getString("createDate");// 定位时间
						String angle = lj.getString("angle");// 方位角
						String deviceStatus = lj.getString("device_status");// 汽车状态
						String deviceCount = lj.getString("device_count");// 次数
						String accesstype = lj.getString("accesstype");

						LocationInfo vo = new LocationInfo();
						if ("1".equals(isGps)) {
							String lat = lj.getString("latitude");// 纬度
							String lon = lj.getString("longitude");// 经度

							/*
							 * String accesstype=lj.getString("accesstype");
							 * String mnc=lj.getString("mnc"); String
							 * lac=lj.getString("lac");
							 */

							vo.setSerieNo(imei);
							vo.setDevice(device);
							vo.setBelongProject(belongProject);
							vo.setIsGps(isGps);
							vo.setLatitude(lat);
							vo.setLongitude(lon);
							vo.setAltitude(altitude);
							vo.setSatellite(satellite);
							vo.setSpeed(speed);
							vo.setUploadTime(sf.parse(createDate));
							vo.setAngle(angle);
							vo.setDriverStatus(deviceStatus);
							vo.setDriverCount(deviceCount);
							Integer is = ServiceBean.getInstance()
									.getLocationInfoFacade()
									.insertLocationInfo(vo);
							if (is > 0) {
								result = Constant.SUCCESS_CODE;
							} else {
								result = Constant.FAIL_CODE;
							}

						} else {

							String lac = lj.getString("lac");
							String cellid = lj.getString("cellid");
							String signal = lj.getString("signal");
							String serverip = lj.getString("serverip");
							String cdma = lj.getString("cdma");
							String mnc = lj.getString("mnc");
							String x = "";
							if (!"".equals(mcc) && !"".equals(mnc)) {
								x = mcc + "-" + mnc + "-" + lac + "-" + cellid
										+ "-" + signal;
							} else {
								x = lac + "-" + cellid + "-" + signal;
							}
							String url = "http://minigps.net/cw?p=1&needaddress=1&mt=2";
							// p=1&needaddress=1&mt=2
							if (x != null) {
								url = url + "&x=" + x;
							}
							resultsf = RemoteUtil.request(url, "GET",
									"application/json;charset=utf-8", null);

							if (resultsf != null) {
								System.out.print(result);
								loc = (LocRadiusPoiResponse) JsonUtil.fromJson(
										LocRadiusPoiResponse.class, resultsf);
							}
							JSONObject object1 = JSONObject.fromObject(loc
									.toString());
							String address = object1.getString("address");
							String lat = object1.getString("lat");
							String lon = object1.getString("lon");
							String Cause = object1.getString("cause");
							String Map = object1.getString("map");
							Integer Status = Integer.parseInt(object1
									.getString("status"));
							Integer Radius = Integer.parseInt(object1
									.getString("radius"));

							vo.setCause(Cause);
							vo.setAddress(address);
							vo.setMap(Map);
							vo.settStatus(Status);
							vo.setLatitude(lat);
							vo.setLongitude(lon);
							vo.setRadius(Radius);
							vo.setSerieNo(imei);
							vo.setDevice(device);
							vo.setBelongProject(belongProject);
							vo.setIsGps(isGps);
							vo.setAltitude(altitude);
							vo.setSatellite(satellite);
							vo.setSpeed(speed);
							vo.setUploadTime(sf.parse(createDate));
							vo.setAngle(angle);
							vo.setDriverStatus(deviceStatus);
							vo.setDriverCount(deviceCount);
							int is = ServiceBean.getInstance()
									.getLocationInfoFacade()
									.insertLocationInfo(vo);
							if (is > 0) {
								result = Constant.SUCCESS_CODE;
							} else {
								result = Constant.FAIL_CODE;
							}
						}

					}

				} else {
					result = 2;
				}
			} else {
				String data = object.getString("data");
				if (!"".equals(data)) {
					JSONArray listData = new JSONArray().fromObject(data);// 将结果转换为JSONArray对象的形式
					for (int i = 0; i < listData.size(); i++) {
						JSONObject lj = listData.getJSONObject(i);
						String isGps = lj.getString("isGps");// 1为Gps0为基站
						String altitude = lj.getString("altitude");// 海拔
						String satellite = lj.getString("satellite");// 卫星数
						String speed = lj.getString("speed");// 速度
						String createDate = lj.getString("createDate");// 定位时间
						String angle = lj.getString("angle");// 方位角
						String deviceStatus = lj.getString("device_status");// 汽车状态
						String deviceCount = lj.getString("device_count");// 次数

						LocationInfo vo = new LocationInfo();
						if ("1".equals(isGps)) {// 0就是gps
							String lat = lj.getString("latitude");// 纬度
							String lon = lj.getString("longitude");// 经度

							/*
							 * String accesstype=lj.getString("accesstype");
							 * String mnc=lj.getString("mnc"); String
							 * lac=lj.getString("lac");
							 */

							vo.setSerieNo(imei);
							vo.setDevice(device);
							vo.setBelongProject(belongProject);
							vo.setIsGps(isGps);
							// vo.setLatitude(lat);
							// vo.setLongitude(lon);
							vo.setAltitude(altitude);
							vo.setSatellite(satellite);
							vo.setSpeed(speed);
							vo.setUploadTime(sf.parse(createDate));
							vo.setAngle(angle);
							vo.setDriverStatus(deviceStatus);
							vo.setDriverCount(deviceCount);

							String resultCode = HttpRequest
									.sendGet(
											"http://restapi.amap.com/v3/assistant/coordinate/convert",
											"locations="
													+ lon
													+ ","
													+ lat
													+ "&coordsys=gps&output=json&key=c8e48d2232ba30747d1cccf3b3db77b8");
							if ("-1".equals(resultCode)) {
								result = Constant.FAIL_CODE;
								vo.setChangeLongitude("0");
								vo.setChangeLatitude("0");
							} else {
								JSONObject objectC = JSONObject
										.fromObject(resultCode);
								String location = objectC
										.getString("locations");
								String[] str = location.split(",");
								if (str.length == 2) {
									vo.setLatitude(str[0]);
									vo.setLongitude(str[1]);
									// vo.setChangeLongitude(str[0]);
									// vo.setChangeLatitude(str[1]);
								}
							}

							/*
							 * ServiceBean.getInstance().getLocationInfoFacade()
							 * .insertLocationInfo(vo);
							 */

							Integer is = ServiceBean.getInstance()
									.getLocationInfoFacade()
									.insertLocationInfo(vo);
							if (is > 0) {
								result = Constant.SUCCESS_CODE;
							} else {
								result = Constant.FAIL_CODE;
							}

						} else {// 国内基站定位-高德
							String accesstype = lj.getString("accesstype");
							String mnc = lj.getString("mnc");
							String lac = lj.getString("lac");
							String cellid = lj.getString("cellid");
							String signal = lj.getString("signal");
							String serverip = lj.getString("serverip");
							String cdma = lj.getString("cdma");
							String network = lj.getString("network");
							String smac = lj.getString("smac");
							String nearbts = lj.getString("nearbts");
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
							String bts = "";
							if (!"".equals(mcc) && !"".equals(mnc)
									&& !"".equals(lac) && !"".equals(cellid)
									&& !"".equals(signal)) {
								bts = mcc + "," + mnc + "," + lac + ","
										+ cellid + "," + signal;
							}
							map.put("accesstype", "0");
							map.put("network", network);
							map.put("cdma", "0");
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
								JSONObject jsons = JSONObject
										.fromObject(jsonToString);
								String status = jsons.getString("status");
								String address = jsons.getString("desc");
								if (status.equals("1")) {
									String results = jsons.getString("result");
									JSONObject jsonResult = JSONObject
											.fromObject(results);
									String location = jsonResult
											.has("location") ? jsonResult
											.getString("location") : null;
									if (location != null) {
										String[] locations = location
												.split(",");

										String lng = locations[0];
										String lat = locations[1];

										vo.setSerieNo(imei);
										vo.setDevice(device);
										vo.setBelongProject(belongProject);
										vo.setIsGps(isGps);
										vo.setAddress(address);
										vo.setLatitude(lat);
										vo.setLongitude(lng);
										vo.setAltitude(altitude);
										vo.setSatellite(satellite);
										vo.setSpeed(speed);
										vo.setUploadTime(sf.parse(createDate));
										vo.setAngle(angle);
										vo.setDriverStatus(deviceStatus);
										vo.setDriverCount(deviceCount);
										Integer is = ServiceBean.getInstance()
												.getLocationInfoFacade()
												.insertLocationInfo(vo);
										if (is > 0) {
											result = Constant.SUCCESS_CODE;
										} else {
											result = Constant.FAIL_CODE;
										}

									}
								}else if(status.equals("0")){
									result=Constant.SUCCESS_CODE;
								}
							}
						}

					}

				} else {
					json.put("resultCode", "2");
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
			json.put(Constant.EXCEPTION, sb.toString());

		}
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		return null;

	}

	/*
	 * private String getWParameter(List<WI> ws) { String w = null; if (ws !=
	 * null && ws.size() > 0) { WifiList wlist = new WifiList();
	 * wlist.setWs(ws); w = JsonUtil.toJson(wlist); } return w; }
	 */
	/*
	 * private String getXParameter(List<Cells> cells) { String x = null; if
	 * (cells != null && cells.size() > 0) { // mcc-mnc x = "%x-%x"; Cells c =
	 * cells.get(0); x = String.format(x, c.getMcc(), c.getMnc()); for (int i =
	 * 0; i < cells.size(); ++i) { c = cells.get(i); x += "-%x-%x-%x"; x =
	 * String.format(x, c.getLac(), c.getCellid(), c.getSdb()); } } return x; }
	 */
	public static void main(String[] args) {
		String str = "{\"aa\":23,\"dad\":49}";
	}
}
