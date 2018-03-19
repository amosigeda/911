package com.care.sys.dynamicInfo.domain.logic;

import java.util.List;

import com.care.sys.dynamicInfo.dao.DynamicInfoDao;
import com.care.sys.dynamicInfo.domain.DynamicInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;


public class DynamicInfoFacadeImpl implements DynamicInfoFacade{
	private DynamicInfoDao dynamicInfoDao;

	public DynamicInfoDao getDynamicInfoDao() {
		return dynamicInfoDao;
	}

	public void setDynamicInfoDao(DynamicInfoDao dynamicInfoDao) {
		this.dynamicInfoDao = dynamicInfoDao;
	}

	public DataList getDynamicInfoListByVo(DynamicInfo vo)
			throws SystemException {
		DataList list = new DataList(dynamicInfoDao.getDynamicInfoListByVo(vo));
		list.setTotalSize(dynamicInfoDao.getDynamicInfoListCountByVo(vo));
		return list;
	}

	public Integer insertDynamicInfo(DynamicInfo vo) throws SystemException {
		return dynamicInfoDao.insertDynamicInfo(vo);
	}

	public List<DataMap> getDynamicInfo(DynamicInfo vo) throws SystemException {
		return dynamicInfoDao.getDynamicInfo(vo);
	}

	public int updateDynamicInfo(DynamicInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return dynamicInfoDao.updateDynamicInfo(vo);
	}

	public int deleteDynamicInfo(DynamicInfo vo) throws SystemException {
		
		return dynamicInfoDao.deleteDynamicInfo(vo);
	}
	

}
