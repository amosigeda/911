package com.care.sys.appinterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
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
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.care.sys.devicephoneinfo.domain.logic.DevicePhoneInfoFacade;
import com.care.sys.phonecountryinfo.domain.PhoneCountryInfo;
import com.care.sys.phonecountryinfo.domain.logic.PhoneCountryInfoFacade;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.care.sys.phoneinfo.domain.logic.PhoneInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class VerifyDeviceAction extends BaseAction{

	Log logger = LogFactory.getLog(VerifyDeviceAction.class);
	
	private final static int BOND_FAIL_NO = -2;  //锟借备锟斤拷锟较凤拷
	private final static int BOND_FAIL_ALREADY = -3;  //锟借备锟窖憋拷锟斤拷
	
	@SuppressWarnings("finally")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String href = request.getServletPath();
		Date start = new Date();
		JSONObject json = new JSONObject();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServiceBean mIntances = ServiceBean.getInstance();
		String user_id = "";
		String belongProject = "";
		String firmwareEdition ="";
		String device_phone="";
		String user_imsi="";//手机imsi号
		DevicePhoneInfo devicePhoneInfo = new DevicePhoneInfo();
		PhoneCountryInfo phoneCountryInfo = new PhoneCountryInfo();
		
		DevicePhoneInfoFacade devicePhoneFacade = ServiceBean.getInstance().getDevicePhoneInfoFacade();
		PhoneCountryInfoFacade phoneCountryInfoFacade = ServiceBean.getInstance().getPhoneCountryInfoFacade();
		List<DataMap> listMisi = null;
		List<DataMap> listCountry = null;
		String mcc = "";
		String country = "";
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}
			JSONObject object = JSONObject.fromObject(sb.toString());
			
			user_id = object.getString("user_id");  //鐢ㄦ埛id
			String device_imei = object.getString("device_imei");
			belongProject = object.getString("belong_project");
			if(object.containsKey("user_imsi")){
				user_imsi = object.getString("user_imsi");
			}else{
				user_imsi="";
			}
			
			//get娴嬭瘯
//			String user_id = request.getParameter("user_id");  //鐢ㄦ埛id
//			String device_imei = request.getParameter("device_imei");
//			String belongProject = request.getParameter("belong_project");
			
			if(user_id != null && !"".equals(user_id) && device_imei != null && !"".equals(device_imei)){
				
				if(device_imei.contains("@")){
					String[] temp = device_imei.split("@");
					device_imei = temp[0];
					belongProject = temp[1];
				}
				AppUserInfo appUser = new AppUserInfo();
				appUser.setCondition("id='"+user_id+"'");
				List<DataMap> appUserList = ServiceBean.getInstance().getAppUserInfoFacade().getAppUserInfo(appUser);
				if(appUserList.size() > 0){
					PhoneInfo phoneInfo = new PhoneInfo(); // 鎬昏〃绫�
					DeviceActiveInfo deviceActiveInfo = new DeviceActiveInfo();
					ShareInfo shareInfo = new ShareInfo();  //鍒嗕韩绫�
					
					PhoneInfoFacade mPhoneInfoFacade = mIntances.getPhoneInfoFacade();
					DeviceActiveInfoFacade mDeviceActiveInfoFacade = mIntances.getDeviceActiveInfoFacade();
					
					phoneInfo.setCondition("serie_no = '"+device_imei+"' and belong_project='"+belongProject+"'");
					List<DataMap> phoneList = mPhoneInfoFacade.getPhoneInfo(phoneInfo);
					
					deviceActiveInfo.setCondition("device_imei ='"+device_imei+"' and belong_project='"+belongProject+"'");  //鍒ゅ畾鏉′欢
					List<DataMap> deviceList = mDeviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
					int size = deviceList.size();
					if(phoneList.size() <= 0){  //缁戝畾澶辫触
						result = BOND_FAIL_NO;  
					}else if(size > 0){  //宸茶缁戝畾
						String device_disable = ""+ deviceList.get(0).getAt("device_disable");
					    firmwareEdition = ""+phoneList.get(0).getAt("firmware_edition");
						device_phone = ""+ phoneList.get(0).getAt("phone");
						String count = ""+ deviceList.get(0).getAt("count");
						belongProject=""+ deviceList.get(0).getAt("belong_project");;
						
						if(device_disable.equals("1")){  //璇存槑鏄縺娲荤姸鎬�
							result = BOND_FAIL_ALREADY;  
						}else if(device_disable.equals("0")){  //璇存槑鏈縺娲荤姸鎬�
							deviceActiveInfo.setCount(String.valueOf(Integer.valueOf(count) + 1));
							deviceActiveInfo.setDeviceDisable("1");
							deviceActiveInfo.setUserId(user_id);	
							deviceActiveInfo.setFirm(firmwareEdition);
							//deviceActiveInfo.setDeviceUpdateTime(formatter.format(new Date()));
							deviceActiveInfo.setDeviceUpdateTime(new Date());
							mDeviceActiveInfoFacade.updateDeviceActiveInfo(deviceActiveInfo);
							
							phoneInfo.setStatus("2"); //2琛ㄧず婵�椿
							phoneInfo.setSerieNo(device_imei);
							mIntances.getPhoneInfoFacade().updatePhoneInfo(phoneInfo);
							
							shareInfo.setDeviceId(device_imei);
							shareInfo.setUserId(user_id);
							shareInfo.setToUserId(user_id);  //锟斤拷锟借备锟斤拷实锟斤拷锟斤拷锟侥撅拷锟斤拷锟皆硷拷
							shareInfo.setIsPriority("1");    //1锟斤拷示锟皆硷拷锟斤拷
							shareInfo.setShareDate(new Date());  //锟斤拷锟斤拷锟斤拷约锟绞憋拷锟�
							shareInfo.setBelongProject(belongProject);
							mIntances.getShareInfoFacade().insertShareInfo(shareInfo);
							
							json.put("device_phone", device_phone);         //锟借备锟界话锟斤拷锟斤拷
							json.put("belong_project", belongProject
									);
							SettingInfo settingInfo = new SettingInfo();
							settingInfo.setSerieNo(device_imei);
							settingInfo.setVolume(30);
							settingInfo.setLight("0");
							settingInfo.setMap("0");
							settingInfo.setFallOn("0");
							settingInfo.setGps_on("0");   //默认关闭
							settingInfo.setHeart("0");
							settingInfo.setBelongProject(belongProject);
							ServiceBean.getInstance().getSettingInfoFacade().insertSettingInfo(settingInfo);
						
							result = Constant.SUCCESS_CODE;
						}			
					}else{
						device_phone = ""+ phoneList.get(0).getAt("phone");
						firmwareEdition = ""+phoneList.get(0).getAt("firmware_edition");
						belongProject=""+phoneList.get(0).getAt("belong_project");
						deviceActiveInfo.setDeviceAge("2015-12-12");
						deviceActiveInfo.setDeviceDisable("1");
						deviceActiveInfo.setCount("1");
						deviceActiveInfo.setDeviceHead("0");
						deviceActiveInfo.setDeviceHeight("170");
						deviceActiveInfo.setDeviceImei(device_imei);
						deviceActiveInfo.setDeviceName("0");
						deviceActiveInfo.setDevicePhone(device_phone);
						deviceActiveInfo.setDeviceSex("0");
						//deviceActiveInfo.setDeviceUpdateTime(formatter.format(new Date()));
					deviceActiveInfo.setDeviceUpdateTime(new Date());
						deviceActiveInfo.setFirst(new Date());
						deviceActiveInfo.setDeviceWeight("170");
						deviceActiveInfo.setUserId(user_id);
						deviceActiveInfo.setFirm(firmwareEdition);
						deviceActiveInfo.setBelongProject(belongProject);
						deviceActiveInfo.setDeviceStatus("0");
						mDeviceActiveInfoFacade.insertDeviceActiveInfo(deviceActiveInfo);			
						
						phoneInfo.setStatus("2"); //2琛ㄧず婵�椿
						phoneInfo.setSerieNo(device_imei);
						mIntances.getPhoneInfoFacade().updatePhoneInfo(phoneInfo);
//						List<DataMap> deviceList2 = mDeviceActiveInfoFacade.getDeviceActiveInfo(deviceActiveInfo);
//						if(deviceList2.size() > 0){
//							String device_id = ""+deviceList2.get(0).getAt("id");  
//							json.put("device_id", device_id);         //
//						}
						
						SettingInfo settingInfo = new SettingInfo();
						settingInfo.setSerieNo(device_imei);
						settingInfo.setVolume(30);
						settingInfo.setLight("0");
						settingInfo.setMap("0");
						settingInfo.setFallOn("0");
						settingInfo.setGps_on("0");   //默认关闭
						settingInfo.setBelongProject(belongProject);
						settingInfo.setHeart("0");
						ServiceBean.getInstance().getSettingInfoFacade().insertSettingInfo(settingInfo);
						
						shareInfo.setDeviceId(device_imei);
						shareInfo.setUserId(user_id);
						shareInfo.setToUserId(user_id);  //锟斤拷锟借备锟斤拷实锟斤拷锟斤拷锟侥撅拷锟斤拷锟皆硷拷
						shareInfo.setIsPriority("1");    //1锟斤拷示锟皆硷拷锟斤拷
						shareInfo.setShareDate(new Date());  //锟斤拷锟斤拷锟斤拷约锟绞憋拷锟�
						shareInfo.setBelongProject(belongProject);
						mIntances.getShareInfoFacade().insertShareInfo(shareInfo);
						
						json.put("device_phone", device_phone);         //锟借备锟界话锟斤拷锟斤拷
						json.put("belong_project",belongProject); 
						
						result = Constant.SUCCESS_CODE;
					}
					
					if(user_imsi!=null && !"".equals(user_imsi)){
						devicePhoneInfo.setCondition("device_imsi='"+user_imsi+"'");
						listMisi = devicePhoneFacade.getDevicePhoneInfo(devicePhoneInfo);
						if(listMisi.isEmpty()){						
							mcc = user_imsi.substring(0,3);
							if(mcc!=null && !"".equals(mcc)){
								phoneCountryInfo.setCondition("MCC ='"+mcc+"'");
								listCountry = phoneCountryInfoFacade.getPhoneCountryInfo(phoneCountryInfo);
								if(!listCountry.isEmpty()){
									country = listCountry.get(0).getAt("Country").toString();
									devicePhoneInfo = new DevicePhoneInfo();
									devicePhoneInfo.setDevicePhone(device_phone);
									devicePhoneInfo.setDeviceImsi(user_imsi);
									devicePhoneInfo.setDeviceName("0");
									if(belongProject!=null && !"".equals(belongProject)){
										devicePhoneInfo.setBelongProject(Integer.parseInt(belongProject));
									}	
									devicePhoneInfo.setCountry(country);
									devicePhoneInfo.setFirm(firmwareEdition);
									devicePhoneFacade.insertDevicePhoneInfo(devicePhoneInfo);
								}
							}
						}
					}
					if(result == 1){
						if(appUserList.size() > 0){
							String bindCount = (String)appUserList.get(0).getAt("bind_count");
							appUser.setBindCount(String.valueOf(Integer.parseInt(bindCount) + 1));
							appUser.setBelongProject(belongProject);  //更新项目
							ServiceBean.getInstance().getAppUserInfoFacade().updateAppUserInfo(appUser);
						}
						
						DeviceActiveInfo da = new DeviceActiveInfo();
						da.setDeviceImei(device_imei);
						da.setBelongProject(belongProject);
						da.setUserId(user_id);
						da.setDeviceDisable("1");
					//	da.setDeviceUpdateTime(formatter.format(new Date()));
						da.setDeviceUpdateTime(new Date());
						ServiceBean.getInstance().getDeviceActiveInfoFacade().insertDeviceActiveHistory(da);
						
						json.put("b_g", belongProject);
					}
				}else{
					result = -4;
				}			
			}
			
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
		insertVisit(href,belongProject,user_id,0,start,new Date(),json.toString().getBytes("utf-8").length);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());		
		return null;
	}
}
