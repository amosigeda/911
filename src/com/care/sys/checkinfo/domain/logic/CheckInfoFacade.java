package com.care.sys.checkinfo.domain.logic;

import java.util.List;

import com.care.sys.checkinfo.domain.CheckInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface CheckInfoFacade {

	
	public List<DataMap> getCheckInfo(CheckInfo vo) throws SystemException;
	
	public int updateCheckInfo(CheckInfo vo)throws SystemException;

	public int insertCheckInfo(CheckInfo vo)throws SystemException;
	
	public DataList getCheckInfoListByVo(CheckInfo vo) throws SystemException;
	
	public int getCheckInfoCount(CheckInfo vo) throws SystemException;
	
	public int insertCheckCodeInfo(CheckInfo vo) throws SystemException;
	
	public List<DataMap> getCheckCodeInfo(CheckInfo vo) throws SystemException;
	
	public int getCheckInfoCountByVo(CheckInfo vo) throws SystemException;

	public int deleteCheckInfo(CheckInfo del)throws SystemException;

}
