package com.care.sys.deviceactiveinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface DeviceActiveInfoDao {

	public List<DataMap> getDeviceActiveInfo(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getDisturbInfo(DeviceActiveInfo vo)throws SystemException;
	
	public int getDeviceActiveInfoCount(DeviceActiveInfo vo)throws DataAccessException;
	
	public int insertDeviceActiveInfo(DeviceActiveInfo vo)throws DataAccessException;
	
	public int updateDeviceActiveInfo(DeviceActiveInfo vo)throws DataAccessException;
	
	public int deleteDeviceActiveInfo(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getDeviceActiveInfoListByVo(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> setClock(DeviceActiveInfo vo) throws SystemException;
	
	public List<DataMap> getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo) throws DataAccessException;
	
	public int getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo) throws DataAccessException;
	
	public int insertDeviceActiveHistory(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getDeviceActiveHistory(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getDeviceActiveHistoryListByVo(DeviceActiveInfo vo) throws DataAccessException;
	
	public int getDeviceActiveHistoryCount(DeviceActiveInfo vo) throws DataAccessException;
	
	public int updateDeviceActiveHistory(DeviceActiveInfo vo) throws DataAccessException;
	
	//ZST@20151104
	public List<DataMap> getDeviceActiveLocationInfo(DeviceActiveInfo vo) throws DataAccessException;

	public int insertSportInfo(DeviceActiveInfo vo)throws DataAccessException;

	public int insertCallInfo(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getDeviceAddFriendInfo(DeviceActiveInfo vo)throws DataAccessException;

	public int insertAddFriendInfo(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getAddFriends(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getCallInfo(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getTalkGroup(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getSportInfo(DeviceActiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getMediaInfo(DeviceActiveInfo vo) throws DataAccessException;

	public List<DataMap> getSportTypeInfo(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getAllCallInfo(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getCityWeatherInfo(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getmaxtime(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getminitime(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getlal(DeviceActiveInfo vo)throws DataAccessException;
	
	public int insertWeatherInfo(DeviceActiveInfo vo)throws DataAccessException;

	public int updateWeatherInfo(DeviceActiveInfo vo)throws DataAccessException;
}
