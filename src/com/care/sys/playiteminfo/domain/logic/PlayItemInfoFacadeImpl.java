package com.care.sys.playiteminfo.domain.logic;

import java.util.List;

import com.care.sys.playiteminfo.dao.PlayItemInfoDao;
import com.care.sys.playiteminfo.domain.PlayItemInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class PlayItemInfoFacadeImpl implements PlayItemInfoFacade{
	
	private PlayItemInfoDao playItemInfoDao;

	public void setPlayItemInfoDao(PlayItemInfoDao playItemInfoDao) {
		this.playItemInfoDao = playItemInfoDao;
	}
	public PlayItemInfoFacadeImpl(){
		
	}
	public List<DataMap> getPlayItemInfo(PlayItemInfo vo)
			throws SystemException {
		return playItemInfoDao.getPlayItemInfo(vo);
	}
	public int insertPlayItemInfo(PlayItemInfo vo) throws SystemException {
		return playItemInfoDao.insertPlayItemInfo(vo);
	}
	public int updatePlayItemInfoControlType(PlayItemInfo vo)
			throws SystemException {
		return playItemInfoDao.updatePlayItemInfoControlType(vo);
	}

}
