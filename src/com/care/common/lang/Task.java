package com.care.common.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.care.common.config.ServiceBean;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.locationinfo.domain.logic.LocationInfoFacade;
import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.care.sys.monitorinfo.domain.logic.MonitorInfoFacade;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class Task  extends TimerTask{
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		try {
			SettingInfo vo = new SettingInfo();
			vo.setHeart("0");
			ServiceBean.getInstance().getSettingInfoFacade().updateSettingInfo(vo);
			
	} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	monitorBeiFenInfo();
		
		backupLocationInfo();
		
		backupVisit();
		
	}
	
	private void monitorBeiFenInfo() {
		MonitorInfo vo = new MonitorInfo();
		MonitorInfoFacade info = ServiceBean.getInstance().getMonitorInfoFacade();
		try{
			vo.setCondition("monitorinfo.start_time <=DATE_SUB(NOW(),INTERVAL 2 MONTH)");
			info.insertMonitorInfoBf(vo);
			info.deleteMonitorInfo(vo);
		}catch(SystemException e){
			e.printStackTrace();
		}
		
	}
	private static void backupLocationInfo(){
		LocationInfo vo = new LocationInfo();
		LocationInfoFacade facade = ServiceBean.getInstance().getLocationInfoFacade();		
		try {			
				vo.setCondition("locationinfo.upload_time <=DATE_SUB(NOW(),INTERVAL 2 MONTH)");
				facade.insertLocationInfoBackup(vo);
				facade.deleteLocationInfo(vo);
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	private static void backupVisit(){
		MonitorInfo vo = new MonitorInfo();
		MonitorInfoFacade facade = ServiceBean.getInstance().getMonitorInfoFacade();
		
		try {
			vo.setCondition("visit.start_time <=DATE_SUB(NOW(),INTERVAL 2 MONTH)");
			facade.insertVisitBackup(vo);
			facade.deleteVisit(vo);
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
}
