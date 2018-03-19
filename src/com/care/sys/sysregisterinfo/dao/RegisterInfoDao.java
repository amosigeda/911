package com.care.sys.sysregisterinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.sysregisterinfo.domain.UserInfo;
import com.godoing.rose.lang.DataMap;

public interface RegisterInfoDao {
	public List<DataMap> getRegisterInfo(UserInfo vo) throws DataAccessException;
	public List<DataMap> getRegisterInfoListByVo(UserInfo vo) throws DataAccessException;
	public Integer getRegisterInfoCount(UserInfo vo) throws DataAccessException;
	public Integer getRegisterInfoMaxId(UserInfo vo) throws DataAccessException;
	public Integer insertRegisterInfo(UserInfo vo) throws DataAccessException;
	public Integer updateRegisterInfo(UserInfo vo) throws DataAccessException;
	public Integer updateGroupCode(UserInfo vo) throws DataAccessException;
	public Integer updateTag(UserInfo vo) throws DataAccessException;
}
