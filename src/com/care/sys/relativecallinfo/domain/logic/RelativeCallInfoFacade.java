package com.care.sys.relativecallinfo.domain.logic;

import java.util.List;

import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface RelativeCallInfoFacade {
	
	public List<DataMap> getRelativeCallInfo(RelativeCallInfo vo) throws SystemException;
	
	public int insertRelativeCallInfo(RelativeCallInfo vo) throws SystemException;
	
	public int getMaxCountRelativeCallInfo(RelativeCallInfo vo) throws SystemException;
	
	public int updateRelativeCallInfoStatus(RelativeCallInfo vo) throws SystemException;
	
	public int updateRelativeCallInfo(RelativeCallInfo vo) throws SystemException;

	public int deleteRelativeCallInfo(RelativeCallInfo vo)throws SystemException;
	
	public DataList getRelativeCallInfoListByVo(RelativeCallInfo vo) throws SystemException;
	
	public int getRelativeCallInfoCount(RelativeCallInfo vo) throws SystemException;
	
	public int getRelativeCallInfoCountByVo(RelativeCallInfo vo) throws SystemException;
}
