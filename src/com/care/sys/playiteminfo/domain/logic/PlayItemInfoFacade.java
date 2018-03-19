package com.care.sys.playiteminfo.domain.logic;

import java.util.List;

import com.care.sys.playiteminfo.domain.PlayItemInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface PlayItemInfoFacade {
	
	public List<DataMap> getPlayItemInfo(PlayItemInfo vo) throws SystemException;
	
	public int insertPlayItemInfo(PlayItemInfo vo) throws SystemException;
	
	public int updatePlayItemInfoControlType(PlayItemInfo vo) throws SystemException;

}
