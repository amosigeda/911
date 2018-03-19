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
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.msginfo.domain.MsgInfo;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class DownloadDeviceDataAction extends BaseAction{

	Log logger = LogFactory.getLog(DownloadDeviceDataAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		JSONArray downloadData = new JSONArray();
		String user_id = "";
		String belong_project = "";
		ServiceBean instance = ServiceBean.getInstance();
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
			belong_project = object.has("belong_project")?object.getString("belong_project"):"0";
//			String user_id = request.getParameter("user_id");
			int shareCount = 0;
			
			if(!"".equals(user_id) && user_id != null){
				AppUserInfo appUserInfo = new AppUserInfo();  //锟斤拷锟斤拷锟斤拷锟角革拷锟矫伙拷锟斤拷
				DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
				SettingInfo settingInfo = new SettingInfo();
				RelativeCallInfo relativeCallInfo = new RelativeCallInfo();
				ShareInfo shareInfo = new ShareInfo();
				
				appUserInfo.setCondition("id ='"+user_id+"'");
				List<DataMap> temp = ServiceBean.getInstance().getAppUserInfoFacade().getAppUserInfo(appUserInfo);
				if(temp.size() > 0){
					belong_project = ""+temp.get(0).getAt("belong_project");
				}
				shareInfo.setCondition("to_user_id ='"+user_id+"' and belong_project ='"+belong_project+"'");  //锟斤拷锟斤拷没锟斤拷锟斤拷锟斤拷璞革拷捅锟斤拷朔锟斤拷锟斤拷锟斤拷锟斤拷璞革拷锟斤拷锟叫�
				List<DataMap> shareList = instance.getShareInfoFacade().getShareInfo(shareInfo);
				shareCount = shareList.size();
				for(int i=0;i<shareCount;i++){
					JSONObject device = new JSONObject();
					String device_imei = ""+ shareList.get(i).getAt("device_imei");
					String is_priority = ""+ shareList.get(i).getAt("is_priority");  //1锟斤拷示锟斤拷锟借备,0锟斤拷示锟斤拷锟斤拷锟借备
					String belongProject = ""+ shareList.get(i).getAt("belong_project");
					
				    if(is_priority.equals("1")){  //锟斤拷锟借备	
				    	JSONArray shareUserArray = new JSONArray();
				    	shareInfo.setCondition("user_id ='"+user_id+"' and is_priority ='0' and device_imei ='"+device_imei+"' and belong_project='"+belongProject+"'"); //锟斤拷锟借备.锟斤拷锟斤拷锟叫凤拷锟斤拷锟斤拷锟斤拷
				    	List<DataMap> userShareList = instance.getShareInfoFacade().getShareInfo(shareInfo);
				    	int shareUserCount = userShareList.size();
				    	for(int m=0;m<shareUserCount;m++){
				    		JSONObject user = new JSONObject();
				    		
				    		String to_user_id = ""+ userShareList.get(m).getAt("to_user_id");
				    		appUserInfo.setCondition("id ='"+to_user_id+"'");
				    		List<DataMap> userList = instance.getAppUserInfoFacade().getAppUserInfo(appUserInfo);
				    		String user_head = ""+userList.get(0).getAt("head");
				    		String user_nick = ""+userList.get(0).getAt("nick_name");
				    		String id = ""+userList.get(0).getAt("id");
				    		String user_name = ""+userList.get(0).getAt("user_name");
				    		
				    		user.put("user_head", user_head);
				    		user.put("user_nick", user_nick);
				    		user.put("user_id", id);
				    		user.put("user_name", user_name);
				    		
				    		shareUserArray.add(m, user);
				    	}
				    	device.put("share_user_count", shareUserCount);
				    	device.put("share_user", shareUserArray);
				    }
					deviceActiveInfo.setCondition("device_imei ='"+device_imei+"' and belong_project='"+belongProject+"' and device_disable = '1'");  //每锟轿撅拷一锟斤拷
					List<DataMap> deviceActiveInfoList = instance.getDeviceActiveInfoFacade().getDeviceActiveInfo(deviceActiveInfo);
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
						String device_id = ""+ deviceActiveInfoList.get(0).getAt("id");
						System.out.println("++++++++++++++++++++++++++++++++"+device_id);
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
						device.put("device_id", device_id);
						
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
					List<DataMap> settingList = instance.getSettingInfoFacade().getSettingInfo(settingInfo);
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
					List<DataMap> familyList = instance.getRelativeCallInfoFacade().getRelativeCallInfo(relativeCallInfo);
					int familySize = familyList.size();			
					JSONArray familyArray = new JSONArray();
					for(int j = 0;j<familySize;j++){
						JSONObject family = new JSONObject();
						String product_model = ""+ familyList.get(j).getAt("product_model");  //锟酵猴拷
						String id = ""+ familyList.get(j).getAt("id");  //id
						String phone_number = ""+ familyList.get(j).getAt("phone_number");  //锟界话锟斤拷锟斤拷
						String short_number = ""+ familyList.get(j).getAt("short_number");  //锟界话锟斤拷锟斤拷
						String relative_type = ""+ familyList.get(j).getAt("relative_type");  //0锟斤拷锟斤拷,1锟斤拷锟斤拷
						String nick_name = ""+ familyList.get(j).getAt("nick_name");  //锟角筹拷
						
						family.put("family_id", id);
						family.put("family_phone", phone_number);
						family.put("short_number", short_number);
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
				result = Constant.SUCCESS_CODE;
			}else{
				result = Constant.FAIL_CODE;
			}		
			
			json.put("device_count",shareCount);
			json.put("device_message", downloadData);
			json.put("belong_project", belong_project);
			json.put("request", href);
			getMessageFrom(json, user_id, belong_project);
			
			
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
		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		insertVisit(href,belong_project,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());		
		return null;
	}
}
