package com.care.sys.userlogininfo.domain.logic;

import java.util.List;

import com.care.sys.userlogininfo.domain.UserLoginInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface UserLoginInfoFacade {
	
	public List<DataMap> getUserLoginInfo(UserLoginInfo vo) throws SystemException;
	
	public int insertUserLoginInfo(UserLoginInfo vo) throws SystemException;
	
	public DataList getUserLoginInfoListByVo(UserLoginInfo vo) throws SystemException;
	
	public int getUserLoginInfoCount(UserLoginInfo vo) throws SystemException;

}
