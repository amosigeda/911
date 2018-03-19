package com.care.sys.dynamicInfo.domain.logic;

import java.util.List;

import com.care.sys.dynamicInfo.domain.DynamicInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface  DynamicInfoFacade {

	public DataList getDynamicInfoListByVo(DynamicInfo vo)throws SystemException;

	public Integer insertDynamicInfo(DynamicInfo vo)throws SystemException;

	public List<DataMap> getDynamicInfo(DynamicInfo vo)throws SystemException;

	public int updateDynamicInfo(DynamicInfo vo)throws SystemException;

	public int deleteDynamicInfo(DynamicInfo vo)throws SystemException;
	

}
