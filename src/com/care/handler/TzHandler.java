package com.care.handler;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.care.app.AbstractHandler;
import com.care.app.MessageManager;
import com.care.common.config.ServiceBean;
import com.care.common.http.HttpRequest;
import com.care.common.lang.CommUtils;
import com.care.common.lang.Constant;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.relativecallinfo.domain.logic.RelativeCallInfoFacade;
import com.care.sys.safearea.domain.SafeArea;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class TzHandler extends AbstractHandler {

	private static Logger logger = Logger.getLogger(TzHandler.class);

	public TzHandler() {
		super();
	}

	int device_id;
	int global_net_status = 2;
	static int OfflineGroupMsg = 50;
	JSONObject message = null;

	public TzHandler(IoSession session, String request) {
		super(session, request);
	}

	@Override
	public void handle() {
		if(request.trim().startsWith("[") && request.trim().endsWith("]")){
			request = request.substring(1, request.length()-1);
			switch(TzCommand.valueOf(request).toInt()){
			case TzCommand.TSTART:
				ResponseMsg();
			case TzCommand.UD:
				TzUploadLocation();
				break;
			case TzCommand.LOGIN:
				Login();
				break;
			case TzCommand.LK:
				LinkKeep();
				break;
			case TzCommand.POWEROFF:
				PowerOff();
				break;
			case TzCommand.CR:
				LocationInstruction();
				break;
			case TzCommand.VERNO:
				TzFirmQuery();
				break;	
				
			case TzCommand.SOS :
				TzMsgFamilyPhone();
				break;
				
			case TzCommand.AL :	
				TzWarn();
				break;
				
			case TzCommand.TS :
				TZ_QueryParameter();
				break;
			}						
		}else{
			message = JSONObject.fromObject(request);
			switch (TzCommand.valueOf(message.getString("request")).toInt()) {
			case TzCommand.TZ_UL_VOICE_REQ:
				TzUlVoice();
				break;
			case TzCommand.TZ_LOGIN_REQ:
				TzLogin();
				break;
			case TzCommand.TZ_CONNECT_REQ:
				TzConnect();
				break;		
			}			
		}
		
		TzSetSessionHandle(device_id, session);
	}
	
	//直接回复
	public void ResponseMsg(){
		String responseMsg = "["+request+"]";
		if(session!=null)
			session.write(responseMsg);
	}

	public void TzLogin() {
		int result = 0;
		int tid = 0;
		String serie_no = message.has("serie_no") ? message
				.getString("serie_no") : null;
		logger.info("in tzLogin serie_no is =============" + serie_no);
		PhoneInfo pi = new PhoneInfo();
		pi.setCondition("serie_no = '" + serie_no + "'");
		PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
		try {
			List<DataMap> list = facade.getPhoneInfo(pi);
			if (list.size() > 0) {
				int id = (Integer) list.get(0).getAt("id");
				tid = id;
				device_id = id;
				pi.setId(id);
				pi.setSerieNo((String) list.get(0).getAt("serie_no"));
				if (MessageManager.tzSet.containsKey(pi.getId())) {
					MessageManager.tzSet.get(pi.getId()).close(true);
					MessageManager.tzSet.remove(pi.getId());
				}
				if (pi != null) {
					MessageManager.TzMsgSet.put(pi.getId(),
							new LinkedList<String>());
					MessageManager.TzMediaSet.put(pi.getId(),
							new LinkedList<String>());
					MessageManager.tzSet.put(pi.getId(), session);
					MessageManager.TzMsgSize.put(pi.getId(), 0);
					MessageManager.TzTimeLen.put(pi.getId(), 0);
					MessageManager.TzMediaSize.put(pi.getId(), 0);
					MessageManager.TzMsgInd.put(pi.getId(), 0);
					MessageManager.TzMediaInd.put(pi.getId(), 0);
					MessageManager.TzLockMap.put(pi.getId(), 0);// 锁定方法登录时为否
				}
				session.setAttribute("device", pi);
				result = 1;
			} else {
				result = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.write("TZ_LOGIN_RSP#" + result + "#" + tid);
	}

	private void TzSetSessionHandle(int id, IoSession isession) {
		logger.info("is in tzSetSessionHandle id is ======================="
				+ id);
		if (id != 0) {
			if (!MessageManager.tzSet.containsKey(id)) {
				logger.info("is in tzSetSessionHandle tzset is not containskey==============="
						+ id);
				logger.info("is in tzSetSessionHandle tzset is not containskey session==============="
						+ isession);
				MessageManager.tzSet.put(id, isession);
				isession.setAttribute("NetStatus", global_net_status);
				PhoneInfo pi = new PhoneInfo();
				pi.setCondition("id = '" + id + "'");
				PhoneInfoFacade facade = ServiceBean.getInstance()
						.getPhoneInfoFacade();
				try {
					List<DataMap> list = facade.getPhoneInfo(pi);
					if (list.size() > 0) {
						pi.setId(id);
						pi.setSerieNo((String) list.get(0).getAt("serie_no"));
//						pi.setShutdown((String) list.get(0).getAt("shutdown"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				isession.setAttribute("device", pi);
				// sendOfferMsgAfterSessionHandle(id,MessageManager.tzSet.get(id));
			}
		}
	}

	private void TzUlVoice() {
		// synchronized(this) {
		int id = message.has("id") ? message.getInt("id") : 0;
		boolean isLock = isLockMethod(id);
		if (!isLock) {
			MessageManager.TzLockMap.put(id, 1);// 锁定
			device_id = id;
			logger.info("in tzulvoice id is =====================" + id);
			int timelen = message.has("timelen") ? message.getInt("timelen")
					: 0;
			logger.info("in tzulvoice timelen is ====================="
					+ timelen);
			int type = message.has("type") ? message.getInt("type") : 0;
			int userid = message.has("user_id") ? new Integer(
					message.getString("user_id")) : 0;
			boolean t = false;
			String result = message.has("result") ? message.getString("result")
					: null;
			List<String> msgset = MessageManager.TzMsgSet.get(id);
			int ind = 0;
			boolean isE = false;
			boolean send = true;
			if (result.contains("end")) {
				isE = true;
				ind = new Integer(result.substring(0, result.indexOf("end")));
				MessageManager.TzMsgSize.put(id, ind + 1);
				MessageManager.TzTimeLen.put(id, timelen);
			} else {
				ind = new Integer(result);
			}
			if (msgset.indexOf(message.getString("data")) == -1) {
				logger.info("in tzulvoice msgset indexof is -1=================================");
				if (msgset.size() < ind) {// 如果end的那条数据先进来
					logger.info("in tzulvoice msgset size < ind =================================");
					send = false;
					for (int msgind = msgset.size(); msgind < ind; msgind++)
						msgset.add(msgind, "xx");
				}
				MessageManager.TzMsgInd.put(id,
						MessageManager.TzMsgInd.get(id) + 1);
				msgset.add(ind, message.getString("data"));
			}
			logger.info("in tzulvoice tzmsgsize is ===================="
					+ MessageManager.TzMsgSize.get(id));
			logger.info("in tzulvoice TzMsgInd is ===================="
					+ MessageManager.TzMsgInd.get(id));
			if (send
					&& MessageManager.TzMsgSize.get(id) == MessageManager.TzMsgInd
							.get(id)) {
				if (timelen == 0) {
					timelen = MessageManager.TzTimeLen.get(id);
				}
				String from_username = "";
				String imei = "000000000100001";
				try {
					PhoneInfo pVo = new PhoneInfo();
					pVo.setCondition("id = '" + id + "'");
					PhoneInfoFacade pfacade = ServiceBean.getInstance()
							.getPhoneInfoFacade();
					List<DataMap> plist = pfacade.getPhoneInfo(pVo);
					if (plist.size() > 0) {
						imei = ""+plist.get(0).getAt("serie_no");
						DeviceActiveInfo vo = new DeviceActiveInfo();
						vo.setCondition("device_imei = '"+ imei + "'");
						logger.info("in tzulvoice to imei=================="+imei);
						DeviceActiveInfoFacade facade = ServiceBean
								.getInstance().getDeviceActiveInfoFacade();
						List<DataMap> list = facade.getDeviceActiveInfo(vo);
						if (list.size() > 0) {
							from_username = (String) list.get(0).getAt(
									"device_name");
							userid = Integer.parseInt((String) list.get(0)
									.getAt("user_id"));
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				JSONObject js = new JSONObject();
				// UserEntity ue=usi.getUserByPhonenumber(phone);
				js.put("request", "MSG_RECEIVEMSG");
				js.put("from_id", imei);
				js.put("from_type", VarTool.device_type);
				js.put("from_username", from_username);
				// if (type != 2)
				js.put("type", type == 2 ? VarTool.chat_group
						: VarTool.chat_user);
				// else
				// js.put("type", 1);

				js.put("send_type", VarTool.send_user);

				js.put("c_type", VarTool.sound);
				Timestamp times = Tools.getTimestamp();
				js.put("timesend", times.toString());

				js.put("content_name", "name");
				if (type == 2)
					js.put("chatgroupid", userid);

				js.put("timelen", timelen);
				t = true;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				for (String s : msgset)
					try {
						bos.write(Base64.decodeBase64(s.toString()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				logger.info("in tzulvoice is clearing==================");
				MessageManager.TzMsgSet.get(id).clear();
				MessageManager.TzMsgSize.put(id, 0);
				MessageManager.TzTimeLen.put(id, 0);
				MessageManager.TzMsgInd.put(id, 0);
				logger.info("in tzulvoice tzmsgsize after clear is ========================="
						+ MessageManager.TzMsgSize.get(id));
				logger.info("in tzulvoice TzMsgInd after clear is ========================="
						+ MessageManager.TzMsgInd.get(id));
				FileOutputStream os;
				int is_reply = 0;
				String path = FileTool.getDeviceVoicePath(id) + '/'
						+ Tools.getTime() + ".amr";
				try {
					js.put("content",
							new String(Base64.encodeBase64(bos.toByteArray()),
									"utf-8"));
					// os = new FileOutputStream(MessageManager.loca_path +
					// path);
					os = new FileOutputStream(MessageManager.system_path + path);
					os.write(bos.toByteArray());
					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (type != 2) {
					IoSession to_session = null;
					logger.info("in tzulvoice userid is =================="
							+ userid);
					Set<Integer> key = MessageManager.playerSet.keySet();
					for (Iterator<Integer> it = key.iterator(); it.hasNext();) {
						int s = it.next();
						logger.info("playerSet id is=============" + s + ":"
								+ MessageManager.playerSet.get(s));
					}
					to_session = MessageManager.playerSet.containsKey(userid) ? MessageManager.playerSet
							.get(userid) : null;
					logger.info("in tz_ul_voice to_session is ================="
							+ to_session);
					if (to_session != null) {
						to_session.write(js);
						is_reply = 1;
					}
					// csi.AddChatMsg(id, userid, path, VarTool.sound, times,
					// is_reply, VarTool.device_type, VarTool.user_type,
					// timelen);
				} else {
					// csi.AddGroupMsg(userid, id, VarTool.device_type, path,
					// VarTool.sound, timelen);
					// Broadcast(userid, js);
				}
			}
			MessageManager.TzLockMap.put(id, 0);// 解除锁定

			if (isE)
				session.write("TZ_UL_VOICE_RSP#E#");
			else
				session.write("TZ_UL_VOICE_RSP#" + ind + "#");
		}
	}

	private boolean isLockMethod(int id) {
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("is in lockmethod===================");
		boolean isLock = true;
		int mCount = 0;
		int lock = MessageManager.TzLockMap.get(id);
		logger.info("is in lockmethod lock===================" + lock);
		if (lock == 0) {// 第一次直接判断是否锁定,如果未锁定
			isLock = false;
		} else {
			while (isLock && mCount <= 10) {
				mCount = mCount + 1;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock = MessageManager.TzLockMap.get(id);
				logger.info("is in lockmethod else lock" + mCount
						+ "===================" + lock);
				if (lock == 0) {
					isLock = false;
					break;
				}
				if (10 == mCount) {
					isLock = false;
					mCount = 0;
					break;
				}
			}
		}
		return isLock;
	}

	private void TzConnect() {
		int id = message.has("id") ? message.getInt("id") : 0;
		logger.info("in tzconnect id is =====================" + id);
		device_id = id;
		IoSession tzSession = MessageManager.tzSet.get(id);
		logger.info("in tzconnect session is =================" + tzSession);
		StringBuffer sb = new StringBuffer("TZ_CONNECT_RSP#1");
		session.write(sb.toString());
	}
	
	private void Login(){
		
		String[] messageArray = request.split("\\*");
		logger.info("request&&&&&&&&&&&&&&&&&&&&&&" + request);
		String responseMsg ="["+request.split("\\,")[0].toString()+"]";
		String serieNo = messageArray[1];
		DeviceActiveInfo vo = new DeviceActiveInfo();
		vo.setCondition("device_imei='"+serieNo+"'");
		try {
			List<DataMap> list = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
			if(list.size() > 0){
				int id = (Integer)list.get(0).getAt("id");
				device_id = id;
				MessageManager.playerSet.put(id, session);
				MessageManager.snSet.put(serieNo, session);
				vo.setId(id);
				vo.setDeviceImei(serieNo);
				session.setAttribute("device", vo);
				session.write(responseMsg);
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}				
	}
	
	//位置数据上报
	private void TzUploadLocation(){
		int result = Constant.FAIL_CODE;
		String[] messageArray = request.split(",");		
		logger.info("request&&&&&&&&&&&&&&&&&&&&&&"+request);
		try {
			String str = messageArray[0];
			String serieNo = str.split("\\*")[1];
			Date dayTime = null;
			String longitude ="";
			String latitude ="";
			String battery ="";
			String stepNo="";
			String rollNo="";
			String bts="";
			String nearbts1="";
			String nearbts2="";
			String nearbts3="";
			String network="GSM";
			String mcc="";
			String mnc="";
			String lac="";
			String cellid="";
			String signal ="";
			String lac1="";
			String cellid1="";
			String signal1 ="";
			String lac2="";
			String cellid2="";
			String signal2 ="";
			String lac3="";
			String cellid3="";
			String signal3 ="";
			
			int s=0;
			SimpleDateFormat format = new SimpleDateFormat("ddMMyy HHmmss");
			if(messageArray.length>=3)
				dayTime= format.parse(messageArray[1] + " " + messageArray[2]);
			if(messageArray.length>=5)
				latitude = messageArray[4];
			if(messageArray.length>=7)		
				longitude =	messageArray[6];
			if(messageArray.length>=14)
				battery = messageArray[13];
			if(messageArray.length>=15)
				stepNo = messageArray[14];
			if(messageArray.length>=16)
				rollNo = messageArray[15];
			if(messageArray.length>=17)
				s = Integer.parseInt(messageArray[16]);
			int alarm = s & 0x0000ffff;
			int status = (s & 0xffff0000) >> 16;
			if(messageArray.length>=20){
				mcc = messageArray[19];
			}
			if(messageArray.length>=21)
				mnc = messageArray[20];
			if(messageArray.length>=22)
				lac = messageArray[21];
			if(messageArray.length>=23)
				cellid = messageArray[22];
			if(messageArray.length>=24)
				signal = messageArray[23];
			if(messageArray.length>=25)
				lac1 = messageArray[24];
			if(messageArray.length>=26)
				cellid1 = messageArray[25];
			if(messageArray.length>=27)
				signal1 = messageArray[26];
			if(messageArray.length>=28)
				lac2 = messageArray[27];
			if(messageArray.length>=29)
				cellid2 = messageArray[28];
			if(messageArray.length>=30)
				signal2 = messageArray[29];
			if(messageArray.length>=31)
				lac3 = messageArray[30];
			if(messageArray.length>=32)
				cellid3 = messageArray[31];
			if(messageArray.length>=33)
				signal3 = messageArray[32];
			
			LocationInfo vo = new LocationInfo();
						
			if("0".equals(longitude) || "0".equals(latitude)) {//LBS
				bts = mcc+","+mnc+","+lac+","+cellid+","+signal;
				nearbts1 = mcc+","+mcc+","+lac1+","+cellid1+","+signal1;
				nearbts2 = mcc+","+mcc+","+lac2+","+cellid2+","+signal2;
				nearbts3 = mcc+","+mcc+","+lac3+","+cellid3+","+signal3;
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

				map.put("accesstype", "0");
				map.put("network", network);
				map.put("cdma", "0");//是否为CDMA
				map.put("imei", serieNo);
				map.put("bts", bts);
				map.put("nearbts", nearbts1+"|"+nearbts2+"|"+nearbts3);
				map.put("key", Constant.KEY);
				map.put("serverip", Constant.SERVER_IP);
				
				String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_URL,map);
				logger.info("jsonToString++" + jsonToString);
				if ("-1".equals(jsonToString)){
					result = Constant.FAIL_CODE;
				}else if(!"-1".equals(jsonToString)){
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String jstatus = jsons.getString("status");
					if (jstatus.equals("1")){ 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject.fromObject(results);
						String location = jsonResult.has("location") ? jsonResult.getString("location") : null; 
						
						if (location != null){
							vo.setCondition("serie_no ='" + serieNo+ "' and belong_project='"+ 4
											+ "' and location_type ='"+ 0 + "' and s_t ='1'");
						}else{
							vo.setCondition("serie_no ='" + serieNo
									+ "' and location_type ='"
									+ 0 + "' and s_t ='1'"); 
						}
						
						logger.info("=====location====="+location);
						vo.setOrderBy("id");
						vo.setSort("1"); // 按id降序
						vo.setFrom(0);
						vo.setPageSize(1); // 0至1

						List<DataMap> locationList = ServiceBean.getInstance().getLocationInfoFacade()
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

						double	lng1 = Double.parseDouble(locations[0]);
						double	lat1 = Double.parseDouble(locations[1]);

						// System.out.println("为转换前的经纬度="+lng1+","+lat1);
						// System.out.println("为转换后的经纬度="+lng2+","+lat2);

						bool_is_update = Constant.getDistance(lat1,lng1, lat2, lng2,
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
						vo.setLocationType("0");
						vo.setUploadTime(new Date());
						vo.setStepNo(0);
						vo.setRollNo(0);
						vo.settAlarm(0);
						vo.settStatus(0);						
						vo.setBelongProject("4");
						
						if (bool_is_update || !CommUtils.isSameDay(date)) { // 说明有变化,获取到新的一天,点显示
							vo.setShowType("1");								
							vo.setEndTime(new Date());
						} else {
							vo.setShowType("0"); // 点不显示
							vo.setEndTime(null);
						}
						vo.setFall("0");
						ServiceBean.getInstance().getLocationInfoFacade()
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
					} else if (jstatus.equals("0")) {
						result = Constant.FAIL_CODE;
					} else if (jstatus.equals("-1")) {
						result = Constant.FAIL_CODE;
					}										
				}
			}else{
				vo.setSerieNo(serieNo);
				if(battery!=null && !"".equals(battery))
					vo.setBattery(Integer.parseInt(battery));
				vo.setLongitude(longitude);
				vo.setLatitude(latitude);
				vo.setUploadTime(dayTime);
				vo.setLocationType("0");
				vo.setAccuracy(0);
				vo.setChangeLatitude("0");
				vo.setChangeLongitude("0");
				vo.setBelongProject("4");
				vo.setShowType("1");
				vo.setEndTime(new Date());
				vo.setFall("0");
				if(stepNo!=null && !"".equals(stepNo))
					vo.setStepNo(Integer.parseInt(stepNo));
				if(rollNo!=null && !"".equals(rollNo))
					vo.setRollNo(Integer.parseInt(rollNo));
				vo.settAlarm(alarm);			
				vo.settStatus(status);
				ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(vo);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//链路保持
	private void LinkKeep(){		
		String[] messageArray = request.split(",");
		String serieNo = messageArray[0].split("\\*")[1];
		String responseMsg = "["+messageArray[0].toString()+"]";
		
		IoSession to_session = MessageManager.snSet.containsKey(serieNo)?
							   MessageManager.snSet.get(serieNo):null;
		if(to_session!=null){
			to_session.write(responseMsg);
		}else{
			MessageManager.snSet.put(serieNo,session);
			session.write(responseMsg);
		}
				
		try {
			List<DataMap> list = null;
			DeviceActiveInfo vo = new DeviceActiveInfo();
			vo.setCondition("device_imei='" + serieNo + "'");
			list = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(vo);
			if(list.size() > 0){
				int deviceId = (Integer)list.get(0).getAt("id");				
				to_session = MessageManager.playerSet.containsKey(deviceId)?
							 MessageManager.playerSet.get(deviceId) : null;
							 
				to_session = MessageManager.snSet.containsKey(serieNo)?
							 MessageManager.snSet.get(serieNo):null;
				
				if(to_session == null){
					MessageManager.playerSet.put(deviceId, session);
					MessageManager.snSet.put(serieNo,session);
				}				
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}				
	}
	
	private void PowerOff(){
		
//		String requestMessage = message.toString();
		String serieNo = request.split("\\*")[1];
		
		try {
			SettingInfo vo = new SettingInfo();
			vo.setShutdown("0");
			vo.setCondition("serie_no='" + serieNo + "'");
			ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(vo);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void LocationInstruction(){
//		String requestMessage = message.toString();
		String serieNo = request.split("\\*")[1];
		try {
			SettingInfo vo = new SettingInfo();
			vo.setGps_on("0");
			vo.setCondition("serie_no='" + serieNo + "'");
			ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(vo);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	private void TzFirmQuery() { //查询版本@20151211
		String firm ="";
		String[] messageArray = request.split("\\*");
		String serieNo = messageArray[1].toString();
		IoSession to_session = MessageManager.snSet.containsKey(serieNo)?
				   MessageManager.snSet.get(serieNo) : null;
				   
		DeviceActiveInfo vo= new DeviceActiveInfo();
		vo.setCondition("device_imei='"+serieNo+"'");		
		DeviceActiveInfoFacade facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		
		try {
			List<DataMap> list = facade.getDeviceActiveInfo(vo);
			if (list.size() > 0) {
				firm = (String) list.get(0).getAt("firm");
				session.setAttribute("firm", firm);
			} else {
				firm = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(to_session!=null)
			to_session.write(request+'.'+firm);
	}
	
	
	
	private void TzMsgFamilyPhone(){ //SOS号码设置
		String[] messageArray = request.split("\\*");
		String serieNo = messageArray[1].toString();
		String phoneOrder = messageArray[3].toString();
		IoSession to_session = MessageManager.snSet.containsKey(serieNo)?
				   			   MessageManager.snSet.get(serieNo) : null;
		String returnTxt ="";
		
		RelativeCallInfo vo = new RelativeCallInfo();
		RelativeCallInfoFacade facade = ServiceBean.getInstance().getRelativeCallInfoFacade();
		StringBuffer sb = new StringBuffer();
		
		if(serieNo != null && !serieNo.equals("")){
			sb.append("serie_no = '"+serieNo+"' order by add_time");
		}	
			
		vo.setCondition(sb.toString());
		List<DataMap> list;
		try {
			list = facade.getRelativeCallInfo(vo);
			if(phoneOrder.equals("SOS1") && "".equals(list.get(0).get("phone_number").toString())){
				returnTxt = request+','+list.get(0).get("phone_number");
			}else if(phoneOrder.equals("SOS2") && "".equals(list.get(1).get("phone_number").toString())){
				returnTxt = request+','+list.get(1).get("phone_number");
			}else if(phoneOrder.equals("SOS3") && "".equals(list.get(2).get("phone_number").toString())){
				returnTxt = request+','+list.get(2).get("phone_number");
			}
		} catch (SystemException e) {

			e.printStackTrace();
		}
		
		if(to_session !=null)
			to_session.write(request+","+returnTxt);	
	}
	
	private void TzWarn(){  //报警数据上报
		String[] messageArray = request.split("\\,");
		String[] headerArr = messageArray[0].toString().split("\\*");
		String serieNo = headerArr[1].toString();
		String battery = "";
		String belongProject ="";
		String location ="";
		String locationType=""; //0表示基站，1表示GPS，2wifi
		String fall = "";       //1表示戴上,0表示脱落
		String userId="";
		boolean validateImei = false;
		List<DataMap> list = null;
		List<DataMap> listPhone = null;
		double lng1 = 0;
		double lat1 = 0;
				
		DeviceActiveInfo vo = new DeviceActiveInfo();
		DeviceActiveInfoFacade  facade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		StringBuffer sb = new StringBuffer();
		
		if(headerArr.length>=2)
			serieNo = headerArr[1].toString();
		if(headerArr.length>=14)
			battery = headerArr[13].toString();
			
		sb.append("device_imei ='"+serieNo+"'");
		vo.setCondition(sb.toString());
		
		IoSession to_session = MessageManager.snSet.containsKey(serieNo)?
	   			   MessageManager.snSet.get(serieNo) : null;
		
		try {
			list = facade.getDeviceActiveInfo(vo);
			if(!list.isEmpty()){
				belongProject = (String)list.get(0).getAt("belong_project");	
				userId = (String)list.get(0).getAt("user_id");
			}
			
			//获取警报
			PhoneInfo phoneInfo = new PhoneInfo();
			PhoneInfoFacade phoneInfoFacade = ServiceBean.getInstance().getPhoneInfoFacade();
			if(belongProject !=null && !"".equals(belongProject)){
				phoneInfo.setCondition("serie_no='"+serieNo+"' and belong_project='"+belongProject+"'");
			}else
			{
				phoneInfo.setCondition("serie_no='"+serieNo+"'");
			}
			
			listPhone = phoneInfoFacade.getPhoneInfo(phoneInfo);
			if(!listPhone.isEmpty()){
				String type = (String) list.get(0).getAt("alarm_bell_type");
				if (type.equals("1")) {// 有警报
					phoneInfo.setAlarmBellType("0");
					phoneInfoFacade.updatePhoneInfo(phoneInfo);
					validateImei = true;
				} 				
			}
			
			if(validateImei){
				String network = "";
				String cdma = "";
				String smac = "";
				String bts = "";
				String nearbts = "";
				String serverip = "";

				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

				map.put("accesstype", "0");
				map.put("network", network);
				map.put("cdma", cdma);
				map.put("imei", serieNo);
				map.put("smac", smac);
				map.put("bts", bts);
				map.put("nearbts", nearbts);
				map.put("key", Constant.KEY);
				if (serverip != null && !"".equals(serverip)) {
					map.put("serverip", serverip);
				} else {
					map.put("serverip", Constant.SERVER_IP);
				}

				String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_URL, map);
				logger.info("jsonToString++" + jsonToString);
				
				if (!"-1".equals(jsonToString)){
					LocationInfo locationInfo = new LocationInfo();
					JSONObject jsons = JSONObject.fromObject(jsonToString);
					String status = jsons.getString("status");
					if (status.equals("1")) { 
						String results = jsons.getString("result");
						JSONObject jsonResult = JSONObject.fromObject(results);
						location = jsonResult.has("location") ? jsonResult.getString("location") : null; 
						if (location != null) {
							if (belongProject != null && !"".equals(belongProject)){
								locationInfo.setCondition("serie_no ='" + serieNo
										+ "' and belong_project='"
										+ belongProject
										+ "' and location_type ='"
										+ locationType + "' and s_t ='1'"); // 并且保证点是存在的
							} else {
								locationInfo.setCondition("serie_no ='" + serieNo
										+ "' and location_type ='"
										+ locationType + "' and s_t ='1'");  // and s_t ='1'
							}

							locationInfo.setOrderBy("id");
							locationInfo.setSort("1"); // 按id降序
							locationInfo.setFrom(0);
							locationInfo.setPageSize(1); // 0至1

							List<DataMap> locationList = ServiceBean.getInstance().getLocationInfoFacade().getLocationInfo(locationInfo);
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

								bool_is_update = Constant.getDistance(lat1,lng1, lat2, lng2,Constant.EFFERT_DATA); //
							}

							locationInfo.setSerieNo(serieNo);
							locationInfo.setBattery(Integer.parseInt(battery));
							locationInfo.setLongitude(locations[0]);
							locationInfo.setLatitude(locations[1]);
							locationInfo.setChangeLongitude(locations[0]);
							locationInfo.setChangeLatitude(locations[1]);
							locationInfo.setAccuracy(10);
							locationInfo.setLocationType(locationType);
							locationInfo.setUploadTime(new Date());
							locationInfo.setStepNo(0);
							locationInfo.setRollNo(0);
							locationInfo.settAlarm(0);
							locationInfo.settStatus(0);
							
							if (belongProject != null && !"".equals(belongProject)) {
								locationInfo.setBelongProject(belongProject);
							} else {
								locationInfo.setBelongProject("1");
							}
							if (bool_is_update || !CommUtils.isSameDay(date)) { // 说明有变化,获取到新的一天,点显示
								locationInfo.setShowType("1");								
								locationInfo.setEndTime(new Date());
							} else {
								locationInfo.setShowType("0"); // 点不显示
								locationInfo.setEndTime(null);
							}
							locationInfo.setFall(fall);
							ServiceBean.getInstance().getLocationInfoFacade().insertLocationInfo(locationInfo);
							
							if(locationInfo.getShowType().equals("0")){   //不显示的时候更新下时间,显示不需要更新时间
								locationInfo.setCondition("id ='" + id + "'");
								locationInfo.setUploadTime(null);
								locationInfo.setEndTime(new Date()); // 更新时间
								ServiceBean.getInstance().getLocationInfoFacade()
										.updateLocationInfo(locationInfo); // 更新经纬度
							}							
						}
					}
				}
				
				if (lat1 != 0 && lng1 != 0 && !userId.equals("0")) {
					SafeArea safeArea = new SafeArea();
					if (belongProject != null && !"".equals(belongProject)) {
						safeArea.setCondition("seri_no ='" + serieNo
								+ "' and belong_project = '" + belongProject + "'"); // 查询这个设备的所有围栏
					} else {
						safeArea.setCondition("seri_no ='" + serieNo + "'"); // 查询这个设备的所有围栏
					}

					List<DataMap> safeList = ServiceBean.getInstance().getSafeAreaFacade().getSafeArea(safeArea);
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

						boolean flag = Constant.getDistance(lat1, lng1, lat2, lng2,safe_range); // 说明超出范围
						if (flag && status.equals("1")) { // 超出这个范围,并且进入这个区域
							MsgInfo msgInfo = new MsgInfo();
							msgInfo.setToId(userId);
							msgInfo.setFromId(userId);
							msgInfo.setIsHandler("0"); // 未处理
							msgInfo.setMsgLevel("1"); // 级别较高
							msgInfo.setMsgContent("3@" + serieNo + "@" + area_name);
							msgInfo.setMsgHandlerDate(new Date());
							msgInfo.setBelongProject(belongProject);
							msgInfo.setMsgOccurDate(new Date());
							ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
							safeArea.setStatus("-1"); // 已出这个范围
							safeArea.setCondition("id='" + areaId +"'");
							ServiceBean.getInstance().getSafeAreaFacade().updateSafeArea(safeArea);
							
						} else if (!flag && !status.equals("1")) { // 表示没有超出范围,并且是正常和出去状态
							MsgInfo msgInfo = new MsgInfo();
							msgInfo.setToId(userId);
							msgInfo.setFromId(userId);
							msgInfo.setIsHandler("0"); // 未处理
							msgInfo.setMsgLevel("1"); // 级别较高
							msgInfo.setMsgContent("2@" + serieNo + "@" + area_name);
							msgInfo.setMsgHandlerDate(new Date());
							msgInfo.setBelongProject(belongProject);
							msgInfo.setMsgOccurDate(new Date());

							ServiceBean.getInstance().getMsgInfoFacade().insertMsgInfo(msgInfo);
							
							safeArea.setStatus("1"); // 已出这个范围
							safeArea.setCondition("id='" + areaId +"'");
							ServiceBean.getInstance().getSafeAreaFacade().updateSafeArea(safeArea);
						}
						
						if(to_session!=null){
							to_session.write(messageArray[0].toString());
						}
					}
				}
			}			
			
		} catch (SystemException e) {
			e.printStackTrace();
		}		
	}
	
	private void TZ_QueryParameter(){
		String[] messageArray = request.split("\\*");
		String serieNo = messageArray[1].toString();
		List<DataMap> list = null;
		List<DataMap> listActive = null;
		List<DataMap> listRelative = null;
		String deviceId = "";
		String firm = "";
		String phoneDetails = "";
		String ip ="";
	    String devicePort ="";
	    String centerNum ="";
	    String assisantNum ="";
	    String reportedTime ="";
	    String deviceLanguage ="";
	    String timeZone = "";
	    String satelliteNum ="";
	    String GSMSignal ="";
	    String LEDSwitch ="";
	    String phoneNumOne ="";
	    String phoneNumTwo ="";
	    String phoneNumThree ="";
	    
		PhoneInfo vo = new PhoneInfo();
		PhoneInfoFacade facade = ServiceBean.getInstance().getPhoneInfoFacade();
		vo.setCondition("serie_no ='"+serieNo+"'");
		
		DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
		DeviceActiveInfoFacade deviceActiveInfoFacade = ServiceBean.getInstance().getDeviceActiveInfoFacade();
		deviceActiveInfo.setCondition("device_imei ='"+serieNo+"'");
		
		RelativeCallInfo relativeCallInfo = new RelativeCallInfo();
		RelativeCallInfoFacade relativeCallInfoFacade = ServiceBean.getInstance().getRelativeCallInfoFacade();
		relativeCallInfo.setCondition("serie_no ='"+serieNo+"'");
				
		try {
			list = facade.getPhoneInfo(vo);
			
			if(!list.isEmpty()){
				ip = list.get(0).getAt("ip").toString();
				devicePort = list.get(0).getAt("devicePort").toString();
				centerNum = list.get(0).getAt("centerNum").toString();
				assisantNum = list.get(0).getAt("assisantNum").toString();
				reportedTime = list.get(0).getAt("reportedTime").toString();
				deviceLanguage = list.get(0).getAt("deviceLanguage").toString();
				timeZone = list.get(0).getAt("timeZone").toString();
				satelliteNum = list.get(0).getAt("satelliteNums").toString().toString();
				GSMSignal = list.get(0).getAt("GSMSignal").toString();
				LEDSwitch = list.get(0).getAt("LEDSwitch").toString();
			}
			
			listActive = deviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
			if(!listActive.isEmpty()){
				firm = listActive.get(0).getAt("firm").toString();
				deviceId = listActive.get(0).getAt("id").toString();
			}
			
			phoneDetails = request +",";
			if(firm!=null && !"".equals(firm)){
				phoneDetails= phoneDetails+";"+firm;
			}
			if(deviceId!=null && !"".equals(deviceId)){
				phoneDetails= phoneDetails+";"+deviceId;
			}
			if(serieNo!=null && !"".equals(serieNo)){
				phoneDetails = phoneDetails+";"+serieNo;
			}
			if(ip!=null && !"".equals(ip))
			{
				phoneDetails = phoneDetails +";"+ip;
			}
			if(devicePort!=null && !"".equals(devicePort))
			{
				phoneDetails = phoneDetails +";"+devicePort;
			}
			if(centerNum!=null && !"".equals(centerNum))
			{
				phoneDetails = phoneDetails +";"+centerNum;
			}
			if(assisantNum!=null && !"".equals(assisantNum))
			{
				phoneDetails = phoneDetails +";"+assisantNum;
			}
			
			listRelative = relativeCallInfoFacade.getRelativeCallInfo(relativeCallInfo);
			if(!listRelative.isEmpty()){
				if(listRelative.get(0).getAt("phone_number")!=null && !"".equals(listRelative.get(0).getAt("phone_number"))){
					phoneNumOne = listRelative.get(0).getAt("phone_number").toString();
					phoneDetails=phoneDetails+";"+phoneNumOne;
				}
			
				if(listRelative.get(1).getAt("phone_number")!=null && !"".equals(listRelative.get(1).getAt("phone_number"))){
					phoneNumTwo = listRelative.get(1).getAt("phone_number").toString();
					phoneDetails=phoneDetails+";"+phoneNumTwo;
				}
			
				if(listRelative.get(2).getAt("phone_number")!=null && !"".equals(listRelative.get(2).getAt("phone_number"))){
					phoneNumThree = listRelative.get(2).getAt("phone_number").toString();
					phoneDetails=phoneDetails+";"+phoneNumThree;
				}
			}
			if(reportedTime!=null && !"".equals(reportedTime))
			{
				phoneDetails = phoneDetails +";"+reportedTime;
			}
			if(deviceLanguage!=null && !"".equals(deviceLanguage))
			{
				phoneDetails = phoneDetails +";"+deviceLanguage;
			}
			if(timeZone!=null && !"".equals(timeZone))
			{
				phoneDetails = phoneDetails +";"+timeZone;
			}
			if(satelliteNum!=null && !"".equals(satelliteNum))
			{
				phoneDetails = phoneDetails +";"+satelliteNum;
			}
			if(GSMSignal!=null && !"".equals(GSMSignal))
			{
				phoneDetails = phoneDetails +";"+GSMSignal;
			}
			if(LEDSwitch!=null && !"".equals(LEDSwitch))
			{
				phoneDetails = phoneDetails +";"+LEDSwitch;
			}
						
			IoSession to_session = MessageManager.snSet.containsKey(serieNo)?
		   			   			   MessageManager.snSet.get(serieNo) : null;
            if(to_session!=null){
            	to_session.write(phoneDetails);
            }			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
	}
	
}
