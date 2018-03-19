package com.care.sys.deviceactiveinfo.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.deviceactiveinfo.dao.DeviceActiveInfoDao;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class DeviceActiveInfoFacadeImpl implements DeviceActiveInfoFacade{

	private DeviceActiveInfoDao deviceActiveInfoDao;
	
	public void setDeviceActiveInfoDao(DeviceActiveInfoDao deviceActiveInfoDao){
		this.deviceActiveInfoDao = deviceActiveInfoDao;
	}
	
	public List<DataMap> getDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveInfo(vo);
	}
	
	public List<DataMap> getDisturbInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDisturbInfo(vo);
	}

	public int getDeviceActiveInfoCount(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveInfoCount(vo);
	}

	public int insertDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.insertDeviceActiveInfo(vo);
	}

	public int updateDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.updateDeviceActiveInfo(vo);
	}

	public int deleteDeviceActiveInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.deleteDeviceActiveInfo(vo);
	}

	public DataList getDeviceActiveInfoListByVo(DeviceActiveInfo vo)
			throws SystemException {
		DataList list = new DataList(deviceActiveInfoDao.getDeviceActiveInfoListByVo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveInfoCount(vo));
		return list;
	}
	
	public DataList setClock(DeviceActiveInfo vo)
			throws SystemException {
		DataList list = new DataList(deviceActiveInfoDao.setClock(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveInfoCount(vo));
		return list;
	}

	public int getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveInfoCountGroupByTime(vo);
	}

	public DataList getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo)
			throws SystemException {
		DataList list = new DataList(deviceActiveInfoDao.getDeviceActiveInfoGroupByTime(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveInfoCountGroupByTime(vo));
		return list;
	}

	public List<DataMap> getDeviceActiveHistory(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveHistory(vo);
	}

	public int getDeviceActiveHistoryCount(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveHistoryCount(vo);
	}

	public DataList getDeviceActiveHistoryListByVo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(deviceActiveInfoDao.getDeviceActiveHistoryListByVo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}

	
	
	public int insertDeviceActiveHistory(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.insertDeviceActiveHistory(vo);
	}

	public int updateDeviceActiveHistory(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.updateDeviceActiveHistory(vo);
	}

	//ZST@20151104
	public List<DataMap> getDeviceActiveLocationInfo(DeviceActiveInfo vo) 
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceActiveLocationInfo(vo);
	}

	public int insertSportInfo(DeviceActiveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.insertSportInfo(vo);
	}

	public int insertCallInfo(DeviceActiveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.insertCallInfo(vo);
	}

	public List<DataMap> getDeviceAddFriendInfo(DeviceActiveInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getDeviceAddFriendInfo(vo);
	}

	public int insertAddFriendInfo(DeviceActiveInfo vo) throws SystemException {
		return deviceActiveInfoDao.insertAddFriendInfo(vo);
	}	
	
	public DataList getAddFriends(DeviceActiveInfo vo) {
		DataList list = new DataList(deviceActiveInfoDao.getAddFriends(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}	
	
	public DataList getCallInfo(DeviceActiveInfo vo) {
		DataList list = new DataList(deviceActiveInfoDao.getCallInfo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}	
	public DataList getTalkGroup(DeviceActiveInfo vo) {
		DataList list = new DataList(deviceActiveInfoDao.getTalkGroup(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}	
	
	public DataList getSportInfo(DeviceActiveInfo vo) {
		DataList list = new DataList(deviceActiveInfoDao.getSportInfo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}
	
	public DataList getMediaInfo(DeviceActiveInfo vo) {
		DataList list = new DataList(deviceActiveInfoDao.getMediaInfo(vo));
		list.setTotalSize(deviceActiveInfoDao.getDeviceActiveHistoryCount(vo));
		return list;
	}

	public List<DataMap> getSportTypeInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.getSportTypeInfo(vo);
	}

	public List<DataMap> getAllCallInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return deviceActiveInfoDao.getAllCallInfo(vo);
	}

	public List<DataMap> getCityWeatherInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.getCityWeatherInfo(vo);
	}
	
	public List<DataMap> getmaxtime(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.getmaxtime(vo);
	}
	
	
	public List<DataMap> getlal(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.getlal(vo);
	}
	
	public List<DataMap> getminitime(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.getminitime(vo);
	}

	public int insertWeatherInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.insertWeatherInfo(vo);
	}

	public int updateWeatherInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		return deviceActiveInfoDao.updateWeatherInfo(vo);
	}	
}
