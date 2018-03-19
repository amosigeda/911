package com.care.sys.deviceLogin.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;

public interface DeviceLoginDao {

	public Integer getDeviceLoginCount(DeviceLogin vo) throws DataAccessException;
	
	public List<DataMap> getDeviceLogin(DeviceLogin vo) throws DataAccessException;

	public List<DataMap> getDataDeviceLoginListByVo(DeviceLogin vo) throws DataAccessException;

	public int updateDeviceLogin(DeviceLogin vo) throws DataAccessException;

	public int insertDeviceLogin(DeviceLogin vo) throws DataAccessException;

	public int deleteDeviceLogin(DeviceLogin vo) throws DataAccessException;
	
	public List<DataMap> getDeviceLoginGroupByProject(DeviceLogin vo) throws DataAccessException;
	
	public int getDeviceLoginCountGroupByProject(DeviceLogin vo) throws DataAccessException;
}
