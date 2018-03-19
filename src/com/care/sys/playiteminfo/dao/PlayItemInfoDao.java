package com.care.sys.playiteminfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.playiteminfo.domain.PlayItemInfo;
import com.godoing.rose.lang.DataMap;

public interface PlayItemInfoDao {
	
	public List<DataMap> getPlayItemInfo(PlayItemInfo vo) throws DataAccessException;
	
	public int insertPlayItemInfo(PlayItemInfo vo) throws DataAccessException;
	
	public int updatePlayItemInfoControlType(PlayItemInfo vo) throws DataAccessException;

}
