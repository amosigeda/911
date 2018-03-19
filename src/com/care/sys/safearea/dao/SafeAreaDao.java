package com.care.sys.safearea.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.care.sys.safearea.domain.SafeArea;
import com.godoing.rose.lang.DataMap;

public interface SafeAreaDao {
	
	public int insertSafeArea(SafeArea vo) throws DataAccessException;
	
	public List<DataMap> getSafeArea(SafeArea vo) throws DataAccessException;
	
	public int updateSafeArea(SafeArea vo) throws DataAccessException;

	public int getSafeAreaMaxCount(SafeArea vo) throws DataAccessException;
	
	public int deleteSafeArea(SafeArea vo) throws DataAccessException;
	
	public List<DataMap> getSafeAreaListByVo(SafeArea vo) throws DataAccessException;
	
	public int getSafeAreaCount(SafeArea vo) throws DataAccessException;
}
