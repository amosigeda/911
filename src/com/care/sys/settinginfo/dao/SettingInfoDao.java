package com.care.sys.settinginfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataMap;

public interface SettingInfoDao {
	
	public List<DataMap> getSettingInfo(SettingInfo vo) throws DataAccessException;
	
	public int insertSettingInfo(SettingInfo vo) throws DataAccessException;

	public int updateSettingInfo(SettingInfo vo) throws DataAccessException;
	
	public int deleteSettingInfo(SettingInfo vo) throws DataAccessException;
	
	public List<DataMap> getSettingInfoListByVo(SettingInfo vo) throws DataAccessException;
	
	public int getSettingInfoCount(SettingInfo vo) throws DataAccessException;
	
	public int getSettingInfoCountByVo(SettingInfo vo) throws DataAccessException;
}
