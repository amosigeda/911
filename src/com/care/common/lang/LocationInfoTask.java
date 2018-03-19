package com.care.common.lang;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.care.common.config.ServiceBean;
import com.care.handler.TzHandler;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.care.sys.deviceactiveinfo.domain.logic.DeviceActiveInfoFacade;
import com.care.sys.msginfo.domain.MsgInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class LocationInfoTask extends TimerTask {
	
	private static Logger logger = Logger.getLogger(LocationInfoTask.class);
	
	@Override
	public void run() {				
	 	DeviceActiveInfoFacade info = ServiceBean.getInstance().getDeviceActiveInfoFacade();
	 	int num = 0;	 	
	 	try {
		 		DeviceActiveInfo vo0 = new DeviceActiveInfo();
		 		vo0.setDeviceStatus("0");
		 		num = info.updateDeviceActiveInfo(vo0);
		 		
	 			DeviceActiveInfo vo1 = new DeviceActiveInfo();
	 			vo1.setCondition("l.upload_time >= DATE_SUB(NOW(),INTERVAL 10 MINUTE) group"
	 					+ " by d.device_imei,d.belong_project");
	 			List<DataMap> list = info.getDeviceActiveLocationInfo(vo1);
	 			
	 			for(int i=0;i<list.size();i++){
	 				DeviceActiveInfo activeInfo = new DeviceActiveInfo();
	 				int id = (Integer)list.get(i).getAt("id");
	 				String deviceImei = (String)list.get(i).getAt("device_imei");
	 				int belong_Project = (Integer)list.get(i).getAt("belong_project");
	 				String userId = (String)list.get(i).getAt("user_id");
	 				String beglongProject = String.valueOf(belong_Project);

	 				activeInfo.setId(id);
	 				activeInfo.setDeviceImei(deviceImei); 				
	 				activeInfo.setDeviceStatus("1");
	 				activeInfo.setBelongProject(beglongProject);
	 				
	 				DeviceActiveInfo vo2 = new DeviceActiveInfo();
	 				vo2.setDeviceStatus("1");
	 				vo2.setCondition("device_active_info.belong_project="+beglongProject+
						  " AND device_active_info.device_imei="+deviceImei);
	 				num = info.updateDeviceActiveInfo(vo2);
	 				
	 				if(!userId.equals("0")){
	 					MsgInfo msgInfo = new MsgInfo();
						msgInfo.setToId(userId);
						msgInfo.setFromId(userId);
						msgInfo.setIsHandler("0"); // ��????
						msgInfo.setMsgLevel("1"); // ??????
						msgInfo.setMsgContent("14@" + deviceImei + "@0");
						msgInfo.setMsgHandlerDate(new Date());
						msgInfo.setBelongProject(beglongProject);

						ServiceBean.getInstance().getMsgInfoFacade()
								.insertMsgInfo(msgInfo);
	 				}		
	 			}
	 			
	 		} catch (SystemException e) {
	 		  // TODO Auto-generated catch block
	 			e.printStackTrace();

	 		}
	}
}
