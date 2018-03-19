package com.care.sys.devicephoneinfo.domain.logic;

import java.util.List;
import com.care.sys.devicephoneinfo.dao.DevicePhoneInfoDao;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class DevicePhoneInfoFacadeImpl implements DevicePhoneInfoFacade {
	
	DevicePhoneInfoDao devicePhoneInfoDao;

	public void setDevicePhoneInfoDao(DevicePhoneInfoDao devicePhoneInfoDao) {
		this.devicePhoneInfoDao = devicePhoneInfoDao;
	}

	public List<DataMap> getDevicePhoneInfo(DevicePhoneInfo vo)
			throws SystemException {
		
		return devicePhoneInfoDao.getDevicePhoneInfo(vo);
	}
	
	public List<DataMap> getDevicePhoneInfoListByVo(DevicePhoneInfo vo) throws SystemException{
		DataList list = new DataList(devicePhoneInfoDao.getDevicePhoneInfoListByVo(vo));
		list.setTotalSize(devicePhoneInfoDao.getDevicePhoneInfoListCountByVo(vo));
		return list;
		
	}
	
	public Integer getDevicePhoneInfoListCountByVo(DevicePhoneInfo vo)
			throws SystemException {
		return devicePhoneInfoDao.getDevicePhoneInfoListCountByVo(vo);
	}

	public Integer insertDevicePhoneInfo(DevicePhoneInfo vo)
			throws SystemException {
		return devicePhoneInfoDao.insertDevicePhoneInfo(vo);
	}

}
