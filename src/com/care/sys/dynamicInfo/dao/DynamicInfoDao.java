package com.care.sys.dynamicInfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.dynamicInfo.domain.DynamicInfo;
import com.godoing.rose.lang.DataMap;

public interface DynamicInfoDao {

	public List<DataMap> getDynamicInfoListByVo(DynamicInfo vo) throws DataAccessException;

	public int getDynamicInfoListCountByVo(DynamicInfo vo)throws DataAccessException;

	public Integer insertDynamicInfo(DynamicInfo vo)throws DataAccessException;

	public List<DataMap> getDynamicInfo(DynamicInfo vo)throws DataAccessException;

	public int updateDynamicInfo(DynamicInfo vo)throws DataAccessException;

	public int deleteDynamicInfo(DynamicInfo vo)throws DataAccessException;

	public List<DataMap> getDynamicFuncInfo(DynamicInfo vo)throws DataAccessException;
	

}
