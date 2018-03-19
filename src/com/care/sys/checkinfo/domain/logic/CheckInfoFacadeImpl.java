package com.care.sys.checkinfo.domain.logic;

import java.util.List;

import com.care.sys.checkinfo.dao.CheckInfoDao;
import com.care.sys.checkinfo.domain.CheckInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class CheckInfoFacadeImpl implements CheckInfoFacade{
	
private CheckInfoDao checkInfoDao;
	
    public void setCheckInfoDao(CheckInfoDao checkInfoDao) {
        this.checkInfoDao = checkInfoDao;      
    }
    public CheckInfoFacadeImpl() {
    }

	public List<DataMap> getCheckInfo(CheckInfo vo) throws SystemException {
		return checkInfoDao.getCheckInfo(vo);
	}
	public int insertCheckInfo(CheckInfo vo) throws SystemException {
		return checkInfoDao.insertCheckInfo(vo);
	}
	public int updateCheckInfo(CheckInfo vo) throws SystemException {
		return checkInfoDao.updateCheckInfo(vo);
	}
	public List<DataMap> getCheckCodeInfo(CheckInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return checkInfoDao.getCheckCodeInfo(vo);
	}
	public int getCheckInfoCount(CheckInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return checkInfoDao.getCheckInfoCount(vo);
	}
	public DataList getCheckInfoListByVo(CheckInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(checkInfoDao.getCheckInfoListByVo(vo));
		list.setTotalSize(checkInfoDao.getCheckInfoCountByVo(vo));
		return list;
	}
	public int insertCheckCodeInfo(CheckInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return checkInfoDao.insertCheckCodeInfo(vo);
	}
	public int getCheckInfoCountByVo(CheckInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return checkInfoDao.getCheckInfoCountByVo(vo);
	}
	public int deleteCheckInfo(CheckInfo del) throws SystemException {
		// TODO Auto-generated method stub
		return checkInfoDao.deleteCheckInfo(del);
	}

}
