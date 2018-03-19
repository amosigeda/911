package com.care.sys.sysregisterinfo.domain.logic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.sysregisterinfo.dao.RegisterInfoDao;
import com.care.sys.sysregisterinfo.domain.UserInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;

public class SysRegisterInfoFacadeImpl implements SysRegisterInfoFacade {
	private RegisterInfoDao registerInfoDao;
	

	public RegisterInfoDao getRegisterInfoDao() {
		return registerInfoDao;
	}

	public void setRegisterInfoDao(RegisterInfoDao registerInfoDao) {
		this.registerInfoDao = registerInfoDao;
	}
	
	public List<DataMap> getRegisterInfo(UserInfo vo) throws DataAccessException {
		return registerInfoDao.getRegisterInfo(vo);
	}

	public DataList getRegisterInfoListByVo(UserInfo vo)
			throws DataAccessException {
		DataList list = new DataList(registerInfoDao.getRegisterInfoListByVo(vo));
		list.setTotalSize(registerInfoDao.getRegisterInfoCount(vo));
		return list;
	}

	public Integer getRegisterUserInfoCount(UserInfo vo) throws DataAccessException {
		return registerInfoDao.getRegisterInfoCount(vo);
	}

	public Integer getRegisterInfoMaxId(UserInfo vo)
			throws DataAccessException {
		return registerInfoDao.getRegisterInfoMaxId(vo);
	}

	public Integer insertRegisterInfo(UserInfo vo)
			throws DataAccessException {
		return registerInfoDao.insertRegisterInfo(vo);
	}
		
	public Integer updateRegisterInfo(UserInfo vo) throws DataAccessException{
		return registerInfoDao.updateRegisterInfo(vo);
	}

	public Integer updateGroupCode(UserInfo vo) throws DataAccessException {
		return registerInfoDao.updateGroupCode(vo);
	}

	public Integer updateTag(UserInfo vo) throws DataAccessException {
		return registerInfoDao.updateTag(vo);
	}
}
