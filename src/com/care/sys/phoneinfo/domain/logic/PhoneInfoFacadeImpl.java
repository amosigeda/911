package com.care.sys.phoneinfo.domain.logic;

import java.util.List;

import com.care.sys.phoneinfo.dao.PhoneInfoDao;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class PhoneInfoFacadeImpl implements PhoneInfoFacade{
	private PhoneInfoDao phoneInfoDao;

	public void setPhoneInfoDao(PhoneInfoDao phoneInfoDao) {
		this.phoneInfoDao = phoneInfoDao;
	}
	public PhoneInfoFacadeImpl(){
		
	}
	public List<DataMap> getPhoneInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.getPhoneInfo(vo);
	}
	public int insertPhoneInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.insertPhoneInfo(vo);
	}
	public int updatePhoneInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.updatePhoneInfo(vo);
	}
	public int getPhoneInfoCount(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneInfoCount(vo);
	}
	public DataList getPhoneInfoListByVo(PhoneInfo vo) throws SystemException {
		DataList list = new DataList(phoneInfoDao.getPhoneInfoListByVo(vo));
		list.setTotalSize(phoneInfoDao.getPhoneInfoCountByVo(vo));
		return list;
	}
	public DataList getPhoneManageListByVo(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(phoneInfoDao.getPhoneManageListByVo(vo));
		list.setTotalSize(phoneInfoDao.getPhoneManageCount(vo));
		return list;
	}
	public List<DataMap> getPhoneManagerInfo(PhoneInfo vo) throws SystemException {
		return phoneInfoDao.getPhoneManageListByVo(vo);
	}
	public int insertPhoneManage(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.insertPhoneManage(vo);
	}
	public int getPhoneManageCount(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneManageCount(vo);
	}
	public Integer getPhoneManageMaxId(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneManageMaxId(vo);
	}
	public int getPhoneInfoCountByVo(PhoneInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return phoneInfoDao.getPhoneInfoCountByVo(vo);
	}

}
