package com.care.sys.shareinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataMap;

public interface ShareInfoDao {

    public int insertShareInfo(ShareInfo vo) throws DataAccessException;
	
	public List<DataMap> getShareInfo(ShareInfo vo) throws DataAccessException;
	
	public int updateShareInfo(ShareInfo vo) throws DataAccessException;

	public int getShareInfoMaxCount(ShareInfo vo) throws DataAccessException;
	
	public int deleteShareInfo(ShareInfo vo) throws DataAccessException;
	
	public List<DataMap> getShareInfoListByVo(ShareInfo vo) throws DataAccessException;
	
	public int getShareInfoCountByVo(ShareInfo vo) throws DataAccessException; 
}
