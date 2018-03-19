package com.care.app;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.ecs.xhtml.bdo;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.care.common.config.ServiceBean;
import com.care.common.http.HttpRequest;
import com.care.common.lang.Constant;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class ServerHandler extends IoHandlerAdapter {

	private final static Logger logger = Logger.getLogger(ServerHandler.class);
	private final static String login = "sl";// 登录
	private final static String RN = "\r\n";
	private final static String uplocation = "slc";// 定位
	private final static String notice = "swr";// 当设备被强制关机、SIM卡状态、电量过低的时候，发送此通知
	private final static String bind = "bd";// 当设备被强制关机、SIM卡状态、电量过低的时候，发送此通知
	private final static String sensor = "sensor";// 气压计的测试
	private final static String recf = "recf";// 设备端每次开机时从服务器获取用户在app上设置的状态，对自己的状态进行修改

	// private static final String KEYS = "bcfbf9ebf25a4d0bf86fe9f416b62264";
	private static final String KEYS = "1c1c642bd81c287fd16239ddc0eb85c6";

	/*
	 * private final Set<IoSession> sessions = Collections .synchronizedSet(new
	 * HashSet<IoSession>()); private final Set<String> users = Collections
	 * .synchronizedSet(new HashSet<String>()); MsgServer ms = new MsgServer();
	 * Map<IoSession, MINAClientHandler> clientMap = new HashMap<IoSession,
	 * MINAClientHandler>();
	 */

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		MessageManager.sessionSet.remove(session.getId());
		logger.info(session.getId() + "error");
		logger.error("ServerHandler exception caught:", cause);
		;
		cause.printStackTrace();

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {

		String ip = ((InetSocketAddress) session.getRemoteAddress())
				.getAddress().getHostAddress();
		int host = ((InetSocketAddress) session.getRemoteAddress()).getPort();
		logger.info(ip + ":" + host + "sessionId:" + session.getId() + "加入");
		System.out.println(ip + ":" + host + "--------sessionId:"
				+ session.getId());
		session.setAttribute("ip", ip);
		MessageManager.sessionSet.put(session.getId(), session);

		// ConcurrentHashMap chm = new ConcurrentHashMap();

		// MessageManager.waitingSessions.add(session);
		// Thread th = new Thread(st);
		// sh.start();
		// session.setAttribute("st", st);

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		if (!session.isConnected()) {
			logger.info(session.getId() + ",," + "not isConnected");
		}

		PhoneInfo de = (PhoneInfo) session.getAttribute("device");
		if (de != null) {
			logger.info("sessionIdle de is ===================" + de);
			// DeviceServiceI dsi = new DeviceServiceImpl();
			// DeviceBindEntity bde = dsi.getBindByDeviceId(de.getDevice_id());
			// if (bde != null
			// && MessageManager.playerSet.containsKey(bde
			// .getUser_id())) {
			// JSONObject jsd = new JSONObject();
			// jsd.put("request", "SERVER_DEVICE_NETWORKSTATUS");
			// jsd.put("status", 2);
			// jsd.put("device_id", de.getId());
			// MessageManager.playerSet.get(bde.getUser_id()).write(jsd);
			// }
			session.setAttribute("NetStatus", null);
			session.setAttribute("device", null);
			MessageManager.tzSet.remove(de.getId());
			session.close(true);
		} else {
			AppUserInfo ue = (AppUserInfo) session.getAttribute("user");
			logger.info("sessionIdle ue is ===================" + ue);
			if (ue == null)
				session.close(true);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws SystemException {
		// MessageAdapter.appAdapter();
		logger.info(session.getId() + ",," + message);
		logger.debug(session.getId() + ",," + message);
		logger.info("thread is in messagereceived is ==============="
				+ Thread.currentThread().getId());

		// logger.error("error");

		// JSONObject j=JSONObject.fromObject(message.toString());
		// byte b=(Byte) j.get("b");
		// session.write(message);
		String resp = "";
		LocationInfo vo = new LocationInfo();
		DeviceActiveInfo dvo = new DeviceActiveInfo();
		try {
			if (message != null && !("").equals(message)) {
				System.out.println("message====================" + message);
				logger.info("接收到的消息为=" + message);
				JSONObject object = JSONObject.fromObject(message);
				JSONObject json = new JSONObject();
				String t = object.getString("t");// 交易吗
				if (login.equals(t)) {
					// 设备登录
					String u = object.getString("u");// 设备序列号
					Integer bat = object.getInt("bat");// 电量
					String bts = object.getString("bts");// 当前基站数据
					String s = object.getString("s");// 时间戳

					String btss[] = bts.split(",");// mcc,mnc,lac,cellid,signal
					String mcc = btss[0];
					String mnc = btss[1];
					String lac = btss[2];
					String cellId = btss[3];
					String signal = btss[4];
					StringBuffer lbs = new StringBuffer();
					lbs.append(mcc).append(",").append(mnc).append(",")
							.append(lac).append(",").append(cellId).append(",")
							.append(signal);

					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("accesstype", "0");
					map.put("network", "GSM/EDGE");
					map.put("cdma", "0"); // 非cdma卡
					map.put("imei", u); // 手机imei号
					// map.put("smac", smac);
					map.put("bts", lbs.toString());

					map.put("nearbts", lbs.toString());
					map.put("key", KEYS);
					String jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);
					String lon = "";
					String lat = "";
					logger.info("jsonToString=" + jsonToString);
					System.out.println("jsonToString=" + jsonToString);
					if ("-1".equals(jsonToString)) {
						logger.info("定位未成功----------");
					} else {
						JSONObject jsons = JSONObject.fromObject(jsonToString);
						String status = jsons.getString("status"); // 回传状态,1表示成功
						if (status.equals("1")) {
							String results = jsons.getString("result"); // 结果级
							JSONObject jsonResult = JSONObject
									.fromObject(results);
							String locationJson = jsonResult
									.containsKey("location") ? jsonResult
									.getString("location") : null; // 经纬度
							String address = jsonResult.getString("desc");
							if (locationJson != null) {
								logger.info("locationJson++" + locationJson);
								String str[] = locationJson.split(",");
								lon = str[0];
								lat = str[1];

								/*
								 * {"status":"1",
								 * "info":"OK","infocode":"10000", "result":
								 * {"type"
								 * :"4","location":"116.4540679,39.9156521",
								 * "radius":"550",
								 * "desc":"北京市 朝阳区 金桐西路 靠近中国民生银行(北京光华支行)",
								 * "country":"中国","province":"北京市","city":"北京市",
								 * "citycode"
								 * :"010","adcode":"110105","road":"金桐西路",
								 * "street":"金桐西路", "poi":"中国民生银行(北京光华支行)"}}
								 */
								vo.setBelongProject(t);
								vo.setSerieNo(u);
								vo.setLatitude(lat);
								vo.setLongitude(lon);
								vo.setBattery(bat);
								vo.setEndTime(new Date());
								vo.setCause(s);
								vo.setAddress(address);
								ServiceBean.getInstance()
										.getLocationInfoFacade()
										.insertNineOneOneLocationInfo(vo);
							}
						} else {
							logger.info("定位不成功status不为1.");
						}
					}

					LinkedHashMap<String, String> mapp = new LinkedHashMap<String, String>();

					mapp.put("accesstype", "0");
					mapp.put("network", "GSM");
					mapp.put("cdma", "0");// 是否为CDMA
					mapp.put("imei", u);
					mapp.put("bts", bts);
					// mapp.put("nearbts", nearbts1+"|"+nearbts2+"|"+nearbts3);
					mapp.put("key", Constant.KEY);
					mapp.put("serverip", ((InetSocketAddress) session
							.getRemoteAddress()).getAddress().getHostAddress());

					String jsonToStringg = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_URL, map);
					logger.info("定位解析=" + jsonToStringg);
					System.out.println("定位解析=" + jsonToStringg);
					json.put("t", t);
					json.put("r", "0000");
					json.put("lon", lon);
					json.put("lat", lat);
					json.put("s1", s);
					json.put("s2", System.currentTimeMillis());
					json.put("s3", System.currentTimeMillis());
					json.put("i", "121.196.232.11");
					json.put("p", 7099);

				} else if (uplocation.equals(t)) {

					String deviceSerialNumber = object.getString("u");// 设备序列号
					String l1 = object.getString("l1");// 经度
					String l2 = object.getString("l2");// 纬度
					String gs = object.getString("gs");// 速度
					String a = object.getString("a");// 海拔
					String d = object.getString("d");// 方向
					Integer e1 = object.getInt("e1");// 电量使用状态
					Integer e2 = object.getInt("e2");// 电量
					Integer f = object.getInt("f");// 是否飞行
					String s = object.getString("s");// 时间戳

					vo.setBelongProject(t);
					vo.setSerieNo(deviceSerialNumber);
					vo.setLatitude(l2);
					vo.setLongitude(l1);
					vo.setBattery(e2);
					vo.setEndTime(new Date());
					vo.setCause(s);
					vo.setAltitude(a);
					vo.setSpeed(gs);
					vo.setAngle(d);
					vo.setStepNo(e1);
					vo.setRollNo(f);
					// vo.setIsGps("1");

					ServiceBean.getInstance().getLocationInfoFacade()
							.insertNineOneOneLocationInfo(vo);

					json.put("t", t);
					json.put("r", "0000");
					json.put("s", System.currentTimeMillis());
					json.put("p", 1);
					json.put("f", 1);
					json.put("o", System.currentTimeMillis());

				} else if (notice.equals(t)) {
					String u = object.getString("u");// 设备序列号
					Integer w = object.getInt("w");// 预警类型
					String s = object.getString("s");// 时间戳

					vo.setAddress("1");
					vo.setCellId(t);
					vo.setAltitude(u);
					vo.setAccuracy(w);
					vo.setCause(s);
					vo.setEndTime(new Date());

					ServiceBean.getInstance().getLocationInfoFacade()
							.insertNineOneOneBindInfo(vo);

					json.put("t", t);
					json.put("w", w);
					json.put("r", "0000");
					json.put("s", System.currentTimeMillis());

				} else if (bind.equals(t)) {

					String u = object.getString("u");// 设备序列号
					String s = object.getString("s");// 时间戳

					vo.setAddress("1");
					vo.setCellId(t);
					vo.setAltitude(u);
					vo.setCause(s);
					vo.setEndTime(new Date());

					ServiceBean.getInstance().getLocationInfoFacade()
							.insertNineOneOneWarnInfo(vo);

					json.put("t", t);
					json.put("r", "0000");
					json.put("s", System.currentTimeMillis());

				} else if (sensor.equals(t)) {

					String u = object.getString("u");// 设备序列号
					Integer mode = object.getInt("mode");// 飞行状态
					String s = object.getString("s");// 时间戳

					vo.setAddress("1");
					vo.setCellId(t);
					vo.setAccuracy(mode);
					vo.setAltitude(u);
					vo.setCause(s);
					vo.setEndTime(new Date());
					ServiceBean.getInstance().getLocationInfoFacade()
							.insertNineOneOneQiyaInfo(vo);

					json.put("t", t);
					json.put("r", "0000");
					json.put("s", System.currentTimeMillis());
				} else if (recf.equals(t)) {

					String u = object.getString("u");// 设备序列号
					String s = object.getString("s");// 时间戳
                   
					vo.setCondition("DEVICE_SERIAL_NUMBER='"+u+"' and GET_STATUS='0' limit 1");
					
				List<DataMap> list=	ServiceBean.getInstance().getLocationInfoFacade().insertNineOneOneGetSetInfo(vo);
				
				if(list.size()>0){
					json.put("p", Integer.valueOf(list.get(0).getAt("LOCATION_TYPE")+""));
					json.put("f", Integer.valueOf(list.get(0).getAt("POINT_FREQUENCY")+""));
					json.put("m", Integer.valueOf(list.get(0).getAt("FLY_MODEL")+""));
					String id=list.get(0).get("ID")+"";
					System.out.println(id);
					vo.setCondition("ID='"+id+"'");
					vo.setEndTime(new Date());
					vo.setAngle("1");
					ServiceBean.getInstance().getLocationInfoFacade().updateSetInfoStatus(vo);	
				}else{
					json.put("p", "");
					json.put("f","");
					json.put("m", "");
				}

					json.put("t", t);
					json.put("r", "0000");
					json.put("s", System.currentTimeMillis());
				
				}
				resp = json.toString();
				// new MessageManager(session,
				// message.toString()).msgTransfer();
				session.write(resp + RN);

			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Logger log = Logger.getLogger(ServerHandler.class);
		 * log.info("received: " + message); JSONObject
		 * js=JSONObject.fromObject(message.toString()); // if(message
		 * instanceof JSONObject) logger.info(js.get("fsdf"));
		 * logger.info(js.get("11")); String theMessage="";
		 * if(js.get("11")!=null) theMessage = js.get("11").toString(); String[]
		 * result = theMessage.split(" ", 2); String theCommand = result[0];
		 */

		/*
		 * MINAClientHandler ch = clientMap.get(session); try { ch.dispatch(js);
		 * }catch (Exception e) { e.printStackTrace(); }
		 */
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("关闭=" + session.getRemoteAddress());
		session.close(true);
		/*
		 * String ip = ((InetSocketAddress) session.getRemoteAddress())
		 * .getAddress().getHostAddress(); int host = ((InetSocketAddress)
		 * session.getRemoteAddress()).getPort(); logger.info(ip+":"+host+"离开");
		 * MessageManager.sessionSet.remove(session.getId());
		 * 
		 * logger.info("in sessionClosed session ===================="+session);
		 * logger
		 * .info("in sessionClosed isConnected==================="+session.
		 * isConnected());
		 * logger.info("in sessionClosed remoteip======================"
		 * +session.getRemoteAddress());
		 * logger.info("in sessionClosed localip======================="
		 * +session.getLocalAddress()); AppUserInfo us = (AppUserInfo)
		 * session.getAttribute("user"); if (us != null) { IoSession use =
		 * MessageManager.playerSet.get(us.getId());
		 * logger.info("in sessionClosed use is==================="+use);
		 * IoSession ust = MessageManager.sessionSet.get(us.getId());
		 * logger.info("in sessionClosed ust is==================="+ust);
		 * IoSession usid = MessageManager.sessionSet.get(session.getId());
		 * logger.info("in sessionClosed usid is==================="+usid);
		 * String ip1 = (String) session.getAttribute("ip");
		 * logger.info("sessionClosed ip is ============"+ip1);
		 * MessageManager.playerSet.remove(us.getId()); //
		 * MessageManager.sessionSet.remove(us.getUser_id());
		 * MessageManager.sessionSet.remove(session.getId()); //
		 * usi.AddLoginInfo(us.getUser_id(), ip1, 1, 2); //
		 * MessageManager.playerSet
		 * .remove((String)session.getAttribute("roleName")); } else {
		 * DeviceActiveInfo da = (DeviceActiveInfo)
		 * session.getAttribute("device");
		 * logger.info("sessionClosed de is ==================="+da); if (da !=
		 * null) {
		 * logger.info("sessionClosed device_id is ========================"
		 * +da.getId()); // DeviceServiceI dsi = new DeviceServiceImpl(); //
		 * DeviceBindEntity bde = dsi.getBindByDeviceId(de.getDevice_id()); //
		 * if (bde != null // && MessageManager.playerSet.containsKey(bde //
		 * .getUser_id())) { // JSONObject jsd = new JSONObject(); //
		 * jsd.put("request", "SERVER_DEVICE_NETWORKSTATUS"); //
		 * jsd.put("status", 2); // jsd.put("device_id", de.getDevice_id()); //
		 * MessageManager.playerSet.get(bde.getUser_id()).write(jsd); // }
		 * session.setAttribute("NetStatus", null);
		 * session.setAttribute("device", null);
		 * MessageManager.playerSet.remove(da.getId());
		 * MessageManager.tzSet.remove(da.getId()); } } session.close(true);
		 * logger.info(session.getId() + "closed");
		 */}

	@Override
	public void messageSent(IoSession session, Object message) {
		System.out.println("SERVER send:" + session.getId() + ","
				+ message.toString());

	}

	public static boolean setMessage(IoSession session, String msg) {
		if (session != null) {
			WriteFuture wf = session.write(msg);
			wf.join();
			if (wf.isWritten()) {
				return true;
			}
		}
		return false;
	}

}
