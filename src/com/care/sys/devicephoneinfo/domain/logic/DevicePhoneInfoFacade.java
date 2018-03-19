package com.care.sys.devicephoneinfo.domain.logic;

import java.util.List;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface DevicePhoneInfoFacade {
	public List<DataMap> getDevicePhoneInfo(DevicePhoneInfo vo) throws SystemException;
	
	public List<DataMap> getDevicePhoneInfoListByVo(DevicePhoneInfo vo) throws SystemException;
	
	public Integer getDevicePhoneInfoListCountByVo(DevicePhoneInfo vo) throws SystemException;
	
	public Integer insertDevicePhoneInfo(DevicePhoneInfo vo) throws SystemException;
}
