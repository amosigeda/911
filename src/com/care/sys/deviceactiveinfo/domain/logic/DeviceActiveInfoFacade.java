package com.care.sys.deviceactiveinfo.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface DeviceActiveInfoFacade {

	public List<DataMap> getDeviceActiveInfo(DeviceActiveInfo vo)throws SystemException;
	
	public List<DataMap> getDisturbInfo(DeviceActiveInfo vo)throws SystemException;
	
	public int getDeviceActiveInfoCount(DeviceActiveInfo vo)throws SystemException;
	
	public int insertDeviceActiveInfo(DeviceActiveInfo vo)throws SystemException;
	
	public int updateDeviceActiveInfo(DeviceActiveInfo vo)throws SystemException;
	
	public int deleteDeviceActiveInfo(DeviceActiveInfo vo)throws SystemException;
	
	public DataList getDeviceActiveInfoListByVo(DeviceActiveInfo vo) throws SystemException;
	
	
	public DataList setClock(DeviceActiveInfo vo) throws SystemException;
	
	public DataList getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo) throws SystemException;
	
	public int getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo) throws SystemException;
	
	public int insertDeviceActiveHistory(DeviceActiveInfo vo) throws SystemException;
	
	public List<DataMap> getDeviceActiveHistory(DeviceActiveInfo vo) throws SystemException;
	
	public DataList getDeviceActiveHistoryListByVo(DeviceActiveInfo vo) throws SystemException;
	
	public int getDeviceActiveHistoryCount(DeviceActiveInfo vo) throws SystemException;
	
	public int updateDeviceActiveHistory(DeviceActiveInfo vo) throws SystemException;
	
	//ZST@20151104
	public List<DataMap> getDeviceActiveLocationInfo(DeviceActiveInfo vo) throws SystemException;

	public int insertSportInfo(DeviceActiveInfo vo)throws SystemException;

	public int insertCallInfo(DeviceActiveInfo vo)throws SystemException;

	public List<DataMap> getDeviceAddFriendInfo(DeviceActiveInfo vo)throws SystemException;

	public int insertAddFriendInfo(DeviceActiveInfo vo)throws SystemException;
	
	public DataList getAddFriends(DeviceActiveInfo vo) throws DataAccessException;
	
	public DataList getCallInfo(DeviceActiveInfo vo) throws DataAccessException;
	
	public DataList getTalkGroup(DeviceActiveInfo vo) throws DataAccessException;
	
	public DataList getSportInfo(DeviceActiveInfo vo) throws DataAccessException;
	
	public DataList getMediaInfo(DeviceActiveInfo vo) throws DataAccessException;

	public List<DataMap> getSportTypeInfo(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getAllCallInfo(DeviceActiveInfo vo)throws DataAccessException;

	public List<DataMap> getCityWeatherInfo(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getmaxtime(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getminitime(DeviceActiveInfo vo)throws DataAccessException;
	
	public List<DataMap> getlal(DeviceActiveInfo vo)throws DataAccessException;

	public int insertWeatherInfo(DeviceActiveInfo vo)throws DataAccessException;

	public int updateWeatherInfo(DeviceActiveInfo vo)throws DataAccessException;
}
