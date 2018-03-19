package com.care.sys.userlogininfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.userlogininfo.domain.UserLoginInfo;
import com.godoing.rose.lang.DataMap;

public interface UserLoginInfoDao {
	
	public List<DataMap> getUserLoginInfo(UserLoginInfo vo) throws DataAccessException;
	
	public int insertUserLoginInfo(UserLoginInfo vo) throws DataAccessException;
	
	public List<DataMap> getUserLoginInfoListByVo(UserLoginInfo vo) throws DataAccessException;
	
	public int getUserLoginInfoCount(UserLoginInfo vo) throws DataAccessException;

}
