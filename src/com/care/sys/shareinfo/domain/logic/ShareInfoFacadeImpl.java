package com.care.sys.shareinfo.domain.logic;

import java.util.List;

import com.care.sys.shareinfo.dao.ShareInfoDao;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class ShareInfoFacadeImpl implements ShareInfoFacade{

    private ShareInfoDao shareInfoDao;
	
	public void setShareInfoDao(ShareInfoDao shareInfoDao){
		this.shareInfoDao = shareInfoDao;
	}
	
	public List<DataMap> getShareInfo(ShareInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return shareInfoDao.getShareInfo(vo);
	}

	public int getShareInfoMaxCount(ShareInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return shareInfoDao.getShareInfoMaxCount(vo);
	}

	public int insertShareInfo(ShareInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return shareInfoDao.insertShareInfo(vo);
	}

	public int updateShareInfo(ShareInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return shareInfoDao.updateShareInfo(vo);
	}

	public int deleteShareInfo(ShareInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return shareInfoDao.deleteShareInfo(vo);
	}

	public DataList getShareInfoListByVo(ShareInfo vo) throws SystemException {
		DataList list = new DataList(shareInfoDao.getShareInfoListByVo(vo));
		list.setTotalSize(shareInfoDao.getShareInfoCountByVo(vo));
		return list;
	}

	public int getShareInfoCountByVo(ShareInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return shareInfoDao.getShareInfoCountByVo(vo);
	}

}
