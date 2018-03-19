package com.care.sys.appuserinfo.domain.logic;

import java.util.List;

import com.care.sys.appuserinfo.dao.AppUserInfoDao;
import com.care.sys.appuserinfo.domain.AppUserInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class AppUserInfoFacadeImpl implements AppUserInfoFacade{
	
	private AppUserInfoDao appUserInfoDao;
	

	public void setAppUserInfoDao(AppUserInfoDao appUserInfoDao) {
		this.appUserInfoDao = appUserInfoDao;
	}
	
	public AppUserInfoFacadeImpl(){
		
	}

	public List<DataMap> getAppUserInfo(AppUserInfo vo) throws SystemException {
		return appUserInfoDao.getAppUserInfo(vo);
	}

	public Integer insertAppUserInfo(AppUserInfo vo) throws SystemException {
		return appUserInfoDao.insertAppUserInfo(vo);
	}

	public Integer updateAppUserInfo(AppUserInfo vo) throws SystemException {
		return appUserInfoDao.updateAppUserInfo(vo);
	}

	public Integer getAppUserInfoCount(AppUserInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return appUserInfoDao.getAppUserInfoCount(vo);
	}

	public DataList getAppUserInfoListByVo(AppUserInfo vo)
			throws SystemException {
		DataList list = new DataList(appUserInfoDao.getAppUserInfoListByVo(vo));
		list.setTotalSize(appUserInfoDao.getAppUserInfoCountByVo(vo));
		return list;
	}

	public Integer getAppUserInfoCountGroupByTime(AppUserInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return appUserInfoDao.getAppUserInfoCountGroupByTime(vo);
	}

	public DataList getAppUserInfoGroupByTime(AppUserInfo vo)
			throws SystemException {
		DataList list = new DataList(appUserInfoDao.getAppUserInfoGroupByTime(vo));
		list.setTotalSize(appUserInfoDao.getAppUserInfoCountByVo(vo));
		return list;
	}

	public Integer getAppUserInfoCountByVo(AppUserInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return appUserInfoDao.getAppUserInfoCountByVo(vo);
	}

}
