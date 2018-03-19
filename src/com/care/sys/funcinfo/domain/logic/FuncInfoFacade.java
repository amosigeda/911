package com.care.sys.funcinfo.domain.logic;

import java.util.List;

import com.care.sys.funcinfo.domain.FuncInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface FuncInfoFacade {

	public String getFuncInfoPK() throws SystemException;

	public Integer getFuncInfoCount(FuncInfo vo) throws SystemException;

	public Integer getFuncInfoCount(DataParamMap dmap) throws SystemException;

	public List<DataMap> getFuncInfo(FuncInfo vo) throws SystemException;

	public DataList getDataFuncInfoListByVo(FuncInfo vo) throws SystemException;

	public DataList getDataFuncInfoListByMap(DataParamMap dmap) throws SystemException;

	public int updateFuncInfo(FuncInfo vo) throws SystemException;

	public int insertFuncInfo(FuncInfo vo) throws SystemException;

	public int deleteFuncInfo(FuncInfo vo) throws SystemException;

    public String getAllFuncTree() throws SystemException;

}
