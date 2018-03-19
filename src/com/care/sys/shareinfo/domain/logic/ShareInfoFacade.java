package com.care.sys.shareinfo.domain.logic;

import java.util.List;

import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface ShareInfoFacade {

    public List<DataMap> getShareInfo(ShareInfo vo)throws SystemException;
	
	public int getShareInfoMaxCount(ShareInfo vo)throws SystemException;
	
	public int insertShareInfo(ShareInfo vo)throws SystemException;
	
	public int updateShareInfo(ShareInfo vo)throws SystemException;
	
	public int deleteShareInfo(ShareInfo vo)throws SystemException;
	
	public DataList getShareInfoListByVo(ShareInfo vo) throws SystemException;
	
	public int getShareInfoCountByVo(ShareInfo vo) throws SystemException;
	
}
