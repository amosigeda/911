package com.care.sys.safearea.domain.logic;

import java.util.List;

import com.care.sys.safearea.dao.SafeAreaDao;
import com.care.sys.safearea.domain.SafeArea;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class SafeAreaFacadeImpl implements SafeAreaFacade{
	
	private SafeAreaDao safeAreaDao;

	public void setSafeAreaDao(SafeAreaDao safeAreaDao) {
		this.safeAreaDao = safeAreaDao;
	}
	public SafeAreaFacadeImpl(){
		
	}
	public List<DataMap> getSafeArea(SafeArea vo) throws SystemException {
		return safeAreaDao.getSafeArea(vo);
	}
	public int insertSafeArea(SafeArea vo) throws SystemException {
		return safeAreaDao.insertSafeArea(vo);
	}
	
	public int updateSafeArea(SafeArea vo) throws SystemException {
		return safeAreaDao.updateSafeArea(vo);
	}
	public int getSafeAreaMaxCount(SafeArea vo) throws SystemException {
		// TODO Auto-generated method stub
		return safeAreaDao.getSafeAreaMaxCount(vo);
	}
	public int deleteSafeArea(SafeArea vo) throws SystemException {
		// TODO Auto-generated method stub
		return safeAreaDao.deleteSafeArea(vo);
	}
	public int getSafeAreaCount(SafeArea vo) throws SystemException {
		// TODO Auto-generated method stub
		return safeAreaDao.getSafeAreaCount(vo);
	}
	public DataList getSafeAreaListByVo(SafeArea vo) throws SystemException {
		DataList list = new DataList(safeAreaDao.getSafeAreaListByVo(vo));
		list.setTotalSize(safeAreaDao.getSafeAreaCount(vo));
		return list;
	}
}
