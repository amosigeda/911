package com.care.sys.devicephoneinfo.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.godoing.rose.lang.DataMap;

public interface DevicePhoneInfoDao {
	
	public List<DataMap> getDevicePhoneInfo(DevicePhoneInfo vo) throws DataAccessException;
	
	public List<DataMap> getDevicePhoneInfoListByVo(DevicePhoneInfo vo) throws DataAccessException;
	
	public Integer getDevicePhoneInfoListCountByVo(DevicePhoneInfo vo) throws DataAccessException;
	
	public int insertDevicePhoneInfo(DevicePhoneInfo vo) throws DataAccessException;
}
