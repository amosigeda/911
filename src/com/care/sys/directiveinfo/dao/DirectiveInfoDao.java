package com.care.sys.directiveinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.godoing.rose.lang.DataMap;

public interface DirectiveInfoDao {
	
	public List<DataMap> getDirectiveInfo(DirectiveInfo vo) throws DataAccessException;
	
	public Integer insertDirectiveInfo(DirectiveInfo vo) throws DataAccessException;
	
	public Integer updateDirectiveInfo(DirectiveInfo vo) throws DataAccessException;

	public Integer getDirectiveInfoCount(DirectiveInfo vo) throws DataAccessException;
	
	public List<DataMap> getDirectiveInfoListByVo(DirectiveInfo vo) throws DataAccessException;

}
