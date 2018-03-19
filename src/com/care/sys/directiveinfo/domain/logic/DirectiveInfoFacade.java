package com.care.sys.directiveinfo.domain.logic;

import java.util.List;

import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface DirectiveInfoFacade {

	public List<DataMap> getDirectiveInfo(DirectiveInfo vo) throws SystemException;

	public Integer insertDirectiveInfo(DirectiveInfo vo) throws SystemException;

	public Integer updateDirectiveInfo(DirectiveInfo vo) throws SystemException;

	public Integer getDirectiveInfoCount(DirectiveInfo vo) throws SystemException;
	
	public DataList getDirectiveInfoListByVo(DirectiveInfo vo) throws SystemException;
}
