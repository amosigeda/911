package com.care.sys.phoneinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.godoing.rose.lang.DataMap;

public interface PhoneInfoDao {
	
	public int insertPhoneInfo(PhoneInfo vo) throws DataAccessException;
	
	public List<DataMap> getPhoneInfo(PhoneInfo vo) throws DataAccessException;
	
	public int updatePhoneInfo(PhoneInfo vo) throws DataAccessException;
	
	public List<DataMap> getPhoneInfoListByVo(PhoneInfo vo) throws DataAccessException;
	
	public int getPhoneInfoCount(PhoneInfo vo) throws DataAccessException;
	
	public int insertPhoneManage(PhoneInfo vo) throws DataAccessException;
	
	public List<DataMap> getPhoneManageListByVo(PhoneInfo vo) throws DataAccessException;
	
	public int getPhoneManageCount(PhoneInfo vo) throws DataAccessException;
	
	public Integer getPhoneManageMaxId(PhoneInfo vo) throws DataAccessException;
	
	public int getPhoneInfoCountByVo(PhoneInfo vo) throws DataAccessException;

}
