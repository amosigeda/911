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
import com.care.sys.appuserinfo.domain.logic.AppUserInfoFacade;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.care.sys.userlogininfo.domain.UserLoginInfo;
import com.care.utils.IPSeeker;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class LoginAction extends BaseAction {

	Log logger = LogFactory.getLog(LoginAction.class);
	String loginout = "{\"request\":\"SERVER_LOGINOUT_RE\"}";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
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
			String user_name = object.getString("user_name");
			String user_password = object.getString("user_password");
			String belongProject = object.has("belong_project")?object.getString("belong_project"):"0";
			String imei = object.has("user_imei") ? object
					.getString("user_imei") : "0";
			String imsi = object.has("user_imsi") ? object
					.getString("user_imsi") : "0";
			String phoneModel = object.has("phone_model") ? object
					.getString("phone_model") : "0";
			String phoneVersion = object.has("phone_version") ? object
					.getString("phone_version") : "0";
			String appVersion = object.has("app_version") ? object
					.getString("app_version") : "0";

			AppUserInfo vo = new AppUserInfo();
			AppUserInfoFacade facade = ServiceBean.getInstance()
					.getAppUserInfoFacade();
			// vo.setUserName(user_name);
			// vo.setPassword(user_password);
			if(belongProject.equals("0")){
				vo.setCondition("user_name ='" + user_name +"' and password ='"+user_password+"'");
			}else{
				vo.setCondition("user_name ='" + user_name
						+ "' and password ='" + user_password
						+ "' and belong_project ='" + belongProject + "'");
			}
			List<DataMap> list = facade.getAppUserInfo(vo);
			if (list.size() > 0) {
				result = Constant.SUCCESS_CODE;
				String user_id = "" + list.get(0).getAt("id");
				json.put("user_height", list.get(0).getAt("height"));
				json.put("user_weight", list.get(0).getAt("weight"));
				json.put("user_age", list.get(0).getAt("age"));
				json.put("user_nick", list.get(0).getAt("nick_name"));
				json.put("user_head", list.get(0).getAt("head"));
				json.put("user_sex", list.get(0).getAt("sex"));
				json.put("user_id", user_id);
				json.put("user_name", user_name);
				json.put("user_password", user_password);

				insertVisit(href, belongProject, user_id, 0, start,
						new Date(),
						json.toString().getBytes("utf-8").length);

				String dir = request.getSession(true).getServletContext()
						.getRealPath("/qqrwy"); // 找到文件夹
				String fileName = request.getSession(true)
						.getServletContext().getRealPath("QQWry.Dat"); // 找到文件的名字
				IPSeeker ipSeeker = new IPSeeker(fileName, dir); // ip数据库的地址和要存储的地址
				String ip = request.getRemoteAddr();
				String province = ipSeeker.getIPLocation(ip).getCountry();

				UserLoginInfo userLogin = new UserLoginInfo();
				userLogin.setUserId(Integer.parseInt(user_id));
				userLogin.setLoginTime(new Date());
				userLogin.setImei(imei);
				userLogin.setImsi(imsi);
				userLogin.setPhoneModel(phoneModel);
				userLogin.setPhoneVersion(phoneVersion);
				userLogin.setAppVersion(appVersion);
				userLogin.setIp(ip);
				userLogin.setProvince(province);
				userLogin.setBelongProject(belongProject);
				ServiceBean.getInstance().getUserLoginInfoFacade()
						.insertUserLoginInfo(userLogin);
				
				AppUserInfo appUserInfo = new AppUserInfo();  //锟斤拷锟斤拷锟斤拷锟角革拷锟矫伙拷锟斤拷
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				SettingInfo settingInfo = new SettingInfo();
				RelativeCallInfo relativeCallInfo = new RelativeCallInfo();
				JSONArray downloadData = new JSONArray();
				
				ShareInfo shareInfo = new ShareInfo();
				shareInfo.setCondition("to_user_id ='"+user_id+"' and belong_project ='"+belongProject+"'");  //锟斤拷锟斤拷没锟斤拷锟斤拷锟斤拷璞革拷捅锟斤拷朔锟斤拷锟斤拷锟斤拷锟斤拷璞革拷锟斤拷锟叫�
				List<DataMap> shareList = ServiceBean.getInstance().getShareInfoFacade().getShareInfo(shareInfo);
				int shareCount = shareList.size();
				for(int i=0;i<shareCount;i++){
					JSONObject device = new JSONObject();
					String device_imei = ""+ shareList.get(i).getAt("device_imei");
					String is_priority = ""+ shareList.get(i).getAt("is_priority");  //1锟斤拷示锟斤拷锟借备,0锟斤拷示锟斤拷锟斤拷锟借备
					
				    if(is_priority.equals("1")){  //锟斤拷锟借备	
				    	JSONArray shareUserArray = new JSONArray();
				    	shareInfo.setCondition("user_id ='"+user_id+"' and is_priority ='0' and device_imei ='"+device_imei+"' and belong_project='"+belongProject+"'"); //锟斤拷锟借备.锟斤拷锟斤拷锟叫凤拷锟斤拷锟斤拷锟斤拷
				    	List<DataMap> userShareList = ServiceBean.getInstance().getShareInfoFacade().getShareInfo(shareInfo);
				    	int shareUserCount = userShareList.size();
				    	for(int m=0;m<shareUserCount;m++){
				    		JSONObject user = new JSONObject();
				    		
				    		String to_user_id = ""+ userShareList.get(m).getAt("to_user_id");
				    		appUserInfo.setCondition("id ='"+to_user_id+"'");
				    		List<DataMap> userList = ServiceBean.getInstance().getAppUserInfoFacade().getAppUserInfo(appUserInfo);
				    		String user_head = ""+userList.get(0).getAt("head");
				    		String user_nick = ""+userList.get(0).getAt("nick_name");
				    		String id = ""+userList.get(0).getAt("id");
				    		String user_names = ""+userList.get(0).getAt("user_name");
				    		
				    		user.put("user_head", user_head);
				    		user.put("user_nick", user_nick);
				    		user.put("user_id", id);
				    		user.put("user_name", user_names);
				    		
				    		shareUserArray.add(m, user);
				    	}
				    	device.put("share_user_count", shareUserCount);
				    	device.put("share_user", shareUserArray);
				    }
					deviceActiveInfo.setCondition("device_imei ='"+device_imei+"' and belong_project='"+belongProject+"' and device_disable = '1'");  //每锟轿撅拷一锟斤拷
					List<DataMap> deviceActiveInfoList = ServiceBean.getInstance().getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
					if(deviceActiveInfoList.size() > 0){
						String device_phone = "" + deviceActiveInfoList.get(0).getAt("device_phone"); //锟借备锟侥电话锟斤拷
						String device_name = "" + deviceActiveInfoList.get(0).getAt("device_name"); //锟借备锟斤拷锟�
						String device_head = "" + deviceActiveInfoList.get(0).getAt("device_head"); //锟借备锟斤拷imei锟斤拷
						String device_sex = "" + deviceActiveInfoList.get(0).getAt("device_sex"); //锟借备锟斤拷锟皆憋拷
						String device_age = "" + deviceActiveInfoList.get(0).getAt("device_age"); //锟借备锟斤拷锟斤拷锟斤拷
						String device_height = "" + deviceActiveInfoList.get(0).getAt("device_height"); //锟借备锟斤拷锟斤拷锟�
						String device_weight = "" + deviceActiveInfoList.get(0).getAt("device_weight"); //锟借备锟斤拷锟斤拷锟斤拷
						String listen_type = "" + deviceActiveInfoList.get(0).getAt("listen_type"); //锟借备锟角凤拷锟斤拷锟�
						String to_user_id = "" + deviceActiveInfoList.get(0).getAt("user_id"); //锟斤拷锟斤拷璞革拷锟斤拷锟斤拷母锟斤拷没锟�
						String bong_time = "" + deviceActiveInfoList.get(0).getAt("device_update_time");
						String device_status = ""+ deviceActiveInfoList.get(0).getAt("device_status");  //0表示掉线,1表示在线
						String b_g = ""+ deviceActiveInfoList.get(0).getAt("belong_project");
						
						device.put("device_phone", device_phone);
						device.put("device_name", device_name);
						device.put("device_head", device_head);
						device.put("device_sex", device_sex);
						device.put("device_age", device_age);
						device.put("device_height", device_height);
						device.put("device_weight", device_weight);
						device.put("listen_type", listen_type);
						device.put("device_imei", device_imei);
						device.put("to_user_id", to_user_id);
						device.put("b_t", bong_time);
						device.put("device_status", device_status);
						device.put("b_g", b_g);
						
						AppUserInfo aui = new AppUserInfo();
						aui.setCondition("id='"+to_user_id+"'");
						List<DataMap> auiList = ServiceBean.getInstance().getAppUserInfoFacade().getAppUserInfo(aui);
						if(auiList.size() > 0){
							String bindUserName = (String)auiList.get(0).getAt("user_name");
							String nickName = (String)auiList.get(0).getAt("nick_name");
							String userHead = (String)auiList.get(0).getAt("head");
							
							device.put("bind_user_name", bindUserName);
							device.put("bind_nick_name", nickName);
							device.put("bind_user_head", userHead);
						}
					}
					
					//远锟斤拷锟斤拷锟斤拷
					settingInfo.setCondition("serie_no ='"+device_imei+"' and belong_project='"+belongProject+"'");  //锟斤拷一锟斤拷
					List<DataMap> settingList = ServiceBean.getInstance().getSettingInfoFacade().getSettingInfo(settingInfo);
					String volume = "50";
					String auto_mute = "0";
					String power_off = "1";
					String light = "0";
					String shutdown = "0";
					String repellent = "0";
					String heart = "0";
					if(settingList.size() > 0){
						volume = ""+ settingList.get(0).getAt("volume");
						auto_mute = ""+ settingList.get(0).getAt("auto_mute");
						power_off = ""+ settingList.get(0).getAt("power_off");
						light = ""+ settingList.get(0).getAt("light");
						shutdown = ""+ settingList.get(0).getAt("shutdown");
						repellent = ""+ settingList.get(0).getAt("repellent");
						heart = ""+ settingList.get(0).getAt("heart");
					}			
					
					device.put("device_data_volume", volume);
					device.put("device_data_mute", auto_mute);
					device.put("device_data_power", power_off);
					device.put("device_data_light", light);
					device.put("shutdown", shutdown);
					
					if(repellent.equals("2")){
						device.put("repellent", "1");
					}else{
						device.put("repellent", repellent);
					}
					if(heart.equals("2")){
						device.put("heart", "1");
					}else{
						device.put("heart", heart);
					}
					//锟斤拷锟斤拷锟斤拷锟�
					relativeCallInfo.setCondition("serie_no ='"+device_imei+"' and belong_project='"+belongProject+"'");
					List<DataMap> familyList = ServiceBean.getInstance().getRelativeCallInfoFacade().getRelativeCallInfo(relativeCallInfo);
					int familySize = familyList.size();			
					JSONArray familyArray = new JSONArray();
					for(int j = 0;j<familySize;j++){
						JSONObject family = new JSONObject();
						String product_model = ""+ familyList.get(j).getAt("product_model");  //锟酵猴拷
						String id = ""+ familyList.get(j).getAt("id");  //id
						String phone_number = ""+ familyList.get(j).getAt("phone_number");  //锟界话锟斤拷锟斤拷
						String relative_type = ""+ familyList.get(j).getAt("relative_type");  //0锟斤拷锟斤拷,1锟斤拷锟斤拷
						String nick_name = ""+ familyList.get(j).getAt("nick_name");  //锟角筹拷
						
						family.put("family_id", id);
						family.put("family_phone", phone_number);
						family.put("family_relative", relative_type);
						family.put("family_nick", nick_name);
						
						familyArray.add(j, family);
					}
					if(familySize > 0){
						device.put("device_family_group_message", familyArray);
					}
					device.put("device_share", is_priority); //0锟斤拷示锟斤拷锟斤拷锟斤拷璞�1锟斤拷示锟皆硷拷锟斤拷锟借备
					device.put("device_family_group_count", familySize);
					downloadData.add(i,device);
				}
				json.put("device_count",shareCount);
				json.put("device_message", downloadData);
			} else {
				result = 2;
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
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

		return null;
	}
}
