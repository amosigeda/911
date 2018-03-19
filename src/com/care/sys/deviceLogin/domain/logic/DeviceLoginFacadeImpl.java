package com.care.sys.deviceLogin.domain.logic;

import java.util.List;

import com.care.sys.deviceLogin.dao.DeviceLoginDao;
import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class DeviceLoginFacadeImpl implements DeviceLoginFacade{

	private DeviceLoginDao deviceLoginDao;
	
	public void setDeviceLoginDao(DeviceLoginDao deviceLoginDao){
		this.deviceLoginDao = deviceLoginDao;
	}

	public Integer getDeviceLoginCount(DeviceLogin vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceLoginDao.getDeviceLoginCount(vo);
	}

	public List<DataMap> getDeviceLogin(DeviceLogin vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceLoginDao.getDeviceLogin(vo);
	}

	public DataList getDataDeviceLoginListByVo(DeviceLogin vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(getDeviceLogin(vo));
		list.setTotalSize(getDeviceLoginCount(vo));
		
		return list;
	}

	public int updateDeviceLogin(DeviceLogin vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceLoginDao.updateDeviceLogin(vo);
	}

	public int insertDeviceLogin(DeviceLogin vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceLoginDao.insertDeviceLogin(vo);
	}

	public int deleteDeviceLogin(DeviceLogin vo) throws SystemException {
		// TODO Auto-generated method stub
		return deviceLoginDao.deleteDeviceLogin(vo);
	}

	public DataList getDeviceLoginGroupByProject(DeviceLogin vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(deviceLoginDao.getDeviceLoginGroupByProject(vo));
		list.setTotalSize(deviceLoginDao.getDeviceLoginCountGroupByProject(vo));
		return list;
	}

	public int getDeviceLoginCountGroupByProject(DeviceLogin vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return deviceLoginDao.getDeviceLoginCountGroupByProject(vo);
	}
	
	

}
