package com.care.sys.settinginfo.domain.logic;

import java.util.List;

import com.care.sys.settinginfo.domain.SettingInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface SettingInfoFacade {
	
	public List<DataMap> getSettingInfo(SettingInfo vo) throws SystemException;
	
	public int insertSettingInfo(SettingInfo vo) throws SystemException;

	public int updateSettingInfo(SettingInfo vo) throws SystemException;
	
	public int deleteSettingInfo(SettingInfo vo) throws SystemException;
	
	public DataList getSettingInfoListByVo(SettingInfo vo) throws SystemException;
	
	public int getSettingInfoCount(SettingInfo vo) throws SystemException;
	
	public int getSettingInfoCountByVo(SettingInfo vo) throws SystemException;
}
