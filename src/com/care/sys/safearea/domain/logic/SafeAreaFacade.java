package com.care.sys.safearea.domain.logic;

import java.util.List;

import com.care.sys.safearea.domain.SafeArea;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface SafeAreaFacade {
	
	public List<DataMap> getSafeArea(SafeArea vo) throws SystemException;
	
	public int insertSafeArea(SafeArea vo) throws SystemException;
	
	public int updateSafeArea(SafeArea vo) throws SystemException;

	public int getSafeAreaMaxCount(SafeArea vo) throws SystemException;
	
	public int deleteSafeArea(SafeArea vo) throws SystemException;
	
	public DataList getSafeAreaListByVo(SafeArea vo) throws SystemException;
	
	public int getSafeAreaCount(SafeArea vo) throws SystemException;
}
