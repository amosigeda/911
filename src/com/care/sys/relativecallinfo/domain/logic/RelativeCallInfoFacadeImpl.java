package com.care.sys.relativecallinfo.domain.logic;

import java.util.List;

import com.care.sys.locationinfo.domain.LocationInfo;
import com.care.sys.relativecallinfo.dao.RelativeCallInfoDao;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class RelativeCallInfoFacadeImpl implements RelativeCallInfoFacade{
	
	private RelativeCallInfoDao relativeCallInfoDao;

	public void setRelativeCallInfoDao(RelativeCallInfoDao relativeCallInfoDao) {
		this.relativeCallInfoDao = relativeCallInfoDao;
	}
	public RelativeCallInfoFacadeImpl(){
		
	}
	public List<DataMap> getRelativeCallInfo(RelativeCallInfo vo)
			throws SystemException {
		return relativeCallInfoDao.getRelativeCallInfo(vo);
	}
	public int insertRelativeCallInfo(RelativeCallInfo vo)
			throws SystemException {
		return relativeCallInfoDao.insertRelativeCallInfo(vo);
	}
	public int updateRelativeCallInfoStatus(RelativeCallInfo vo)
			throws SystemException {
		return relativeCallInfoDao.updateRelativeCallInfoStatus(vo);
	}
	public int updateRelativeCallInfo(RelativeCallInfo vo)
			throws SystemException {
		return relativeCallInfoDao.updateRelativeCallInfo(vo);
	}
	public int deleteRelativeCallInfo(RelativeCallInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return relativeCallInfoDao.deleteRelativeCallInfo(vo);
	}
	public int getMaxCountRelativeCallInfo(RelativeCallInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return relativeCallInfoDao.getMaxCountRelativeCallInfo(vo);
	}
	public int getRelativeCallInfoCount(RelativeCallInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return relativeCallInfoDao.getRelativeCallInfoCount(vo);
	}
	public DataList getRelativeCallInfoListByVo(RelativeCallInfo vo)
			throws SystemException {
		DataList list = new DataList(relativeCallInfoDao.getRelativeCallInfoListByVo(vo));
		list.setTotalSize(relativeCallInfoDao.getRelativeCallInfoCountByVo(vo));
		return list;
	}
	public int getRelativeCallInfoCountByVo(RelativeCallInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return relativeCallInfoDao.getRelativeCallInfoCountByVo(vo);
	}
}
