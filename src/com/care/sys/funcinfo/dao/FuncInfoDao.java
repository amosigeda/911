package com.care.sys.funcinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.funcinfo.domain.FuncInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface FuncInfoDao{

	public String getFuncInfoPK()throws DataAccessException;

	public Integer getFuncInfoCount(FuncInfo vo)throws DataAccessException;

	public Integer getFuncInfoCount(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getFuncInfo(FuncInfo vo)throws DataAccessException;

	public List<DataMap> getFuncInfoListByMap(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getFuncInfoListByVo(FuncInfo vo)throws DataAccessException;

	public int updateFuncInfo(FuncInfo vo)throws DataAccessException;

	public int insertFuncInfo(FuncInfo vo)throws DataAccessException;

	public int deleteFuncInfo(FuncInfo vo)throws DataAccessException;
}
