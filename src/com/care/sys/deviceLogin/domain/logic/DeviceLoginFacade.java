package com.care.sys.deviceLogin.domain.logic;

import java.util.List;

import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface DeviceLoginFacade {

	public Integer getDeviceLoginCount(DeviceLogin vo) throws SystemException;
	
	public List<DataMap> getDeviceLogin(DeviceLogin vo) throws SystemException;

	public DataList getDataDeviceLoginListByVo(DeviceLogin vo) throws SystemException;

	public int updateDeviceLogin(DeviceLogin vo) throws SystemException;

	public int insertDeviceLogin(DeviceLogin vo) throws SystemException;

	public int deleteDeviceLogin(DeviceLogin vo) throws SystemException;
	
	public DataList getDeviceLoginGroupByProject(DeviceLogin vo) throws SystemException;
	
	public int getDeviceLoginCountGroupByProject(DeviceLogin vo) throws SystemException;
}
