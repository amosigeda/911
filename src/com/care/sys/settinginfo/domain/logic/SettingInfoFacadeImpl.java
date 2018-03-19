package com.care.sys.settinginfo.domain.logic;

import java.util.List;

import com.care.sys.settinginfo.dao.SettingInfoDao;
import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class SettingInfoFacadeImpl implements SettingInfoFacade{
	
	private SettingInfoDao settingInfoDao;

	public void setSettingInfoDao(SettingInfoDao settingInfoDao) {
		this.settingInfoDao = settingInfoDao;
	}
	public SettingInfoFacadeImpl(){
		
	}
	public List<DataMap> getSettingInfo(SettingInfo vo) throws SystemException {
		return settingInfoDao.getSettingInfo(vo);
	}
	public int insertSettingInfo(SettingInfo vo) throws SystemException {
		return settingInfoDao.insertSettingInfo(vo);
	}
	public int updateSettingInfo(SettingInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return settingInfoDao.updateSettingInfo(vo);
	}
	public int deleteSettingInfo(SettingInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return settingInfoDao.deleteSettingInfo(vo);
	}
	public int getSettingInfoCount(SettingInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return settingInfoDao.getSettingInfoCount(vo);
	}
	public DataList getSettingInfoListByVo(SettingInfo vo)
			throws SystemException {
		DataList list = new DataList(settingInfoDao.getSettingInfoListByVo(vo));
		list.setTotalSize(settingInfoDao.getSettingInfoCountByVo(vo));
		return list;
	}
	public int getSettingInfoCountByVo(SettingInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return settingInfoDao.getSettingInfoCountByVo(vo);
	}
}
