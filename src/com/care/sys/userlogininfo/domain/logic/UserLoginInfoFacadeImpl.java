package com.care.sys.userlogininfo.domain.logic;

import java.util.List;

import com.care.sys.userlogininfo.dao.UserLoginInfoDao;
import com.care.sys.userlogininfo.domain.UserLoginInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class UserLoginInfoFacadeImpl implements UserLoginInfoFacade {
	
	private UserLoginInfoDao userLoginInfoDao;

	public UserLoginInfoFacadeImpl(){
		
	}
	
	public void setUserLoginInfoDao(UserLoginInfoDao userLoginInfoDao) {
		this.userLoginInfoDao = userLoginInfoDao;
	}

	public List<DataMap> getUserLoginInfo(UserLoginInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return userLoginInfoDao.getUserLoginInfo(vo);
	}

	public int getUserLoginInfoCount(UserLoginInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return userLoginInfoDao.getUserLoginInfoCount(vo);
	}

	public DataList getUserLoginInfoListByVo(UserLoginInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(userLoginInfoDao.getUserLoginInfoListByVo(vo));
		list.setTotalSize(userLoginInfoDao.getUserLoginInfoCount(vo));
		return list;
	}

	public int insertUserLoginInfo(UserLoginInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return userLoginInfoDao.insertUserLoginInfo(vo);
	}

}
