package com.care.sys.relativecallinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface RelativeCallInfoDao {
	
	public List<DataMap> getRelativeCallInfo(RelativeCallInfo vo) throws DataAccessException;
	
	public int insertRelativeCallInfo(RelativeCallInfo vo) throws DataAccessException;
	
	public int getMaxCountRelativeCallInfo(RelativeCallInfo vo) throws DataAccessException;
	
	public int updateRelativeCallInfoStatus(RelativeCallInfo vo) throws DataAccessException;
	
	public int updateRelativeCallInfo(RelativeCallInfo vo) throws DataAccessException;

	public int deleteRelativeCallInfo(RelativeCallInfo vo)throws DataAccessException;
	
	public List<DataMap> getRelativeCallInfoListByVo(RelativeCallInfo vo) throws DataAccessException;
	
	public int getRelativeCallInfoCount(RelativeCallInfo vo) throws DataAccessException;
	
	public int getRelativeCallInfoCountByVo(RelativeCallInfo vo) throws DataAccessException;
}
